package com.keepcoding.api_planes_on_paper.service;

import com.keepcoding.api_planes_on_paper.controller.requests.ConnectToGameRequest;
import com.keepcoding.api_planes_on_paper.controller.requests.AttackEnemyRequest;
import com.keepcoding.api_planes_on_paper.controller.requests.SetReadyRequest;
import com.keepcoding.api_planes_on_paper.controller.requests.SetSurrenderedRequest;
import com.keepcoding.api_planes_on_paper.exceptions.GameNotFoundException;
import com.keepcoding.api_planes_on_paper.exceptions.InvalidBorderException;
import com.keepcoding.api_planes_on_paper.models.GameplayStatus;
import com.keepcoding.api_planes_on_paper.models.GameplayModel;
import com.keepcoding.api_planes_on_paper.models.PlayerModel;
import com.keepcoding.api_planes_on_paper.models.PlayerStatus;
import com.keepcoding.api_planes_on_paper.service.util.VerifyBorder;
import com.keepcoding.api_planes_on_paper.storage.GameplayRepository;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

@Service
@EnableScheduling
@Transactional
@EnableTransactionManagement
public class GameplayService {
    private final GameplayRepository gameplayRepository;

    @Autowired
    public GameplayService(GameplayRepository gameplayRepository) {
        this.gameplayRepository = gameplayRepository;
    }

    // SELECT * FROM gameplay_repository
    public List<GameplayModel> getAllGameplayRooms() {
        return gameplayRepository.findAll();
    }

    // SELECT gameplay_room FROM gameplay_repository WHERE gameID = {gameID}
    public GameplayModel getGameplayRoom(String uuid) throws GameNotFoundException {
    	final String gameID = uuid.replaceAll("\"", "");
        return gameplayRepository.findGameplayByGameID(gameID).orElseThrow(() -> new GameNotFoundException(gameID));
    }

    // check if the request was made for a random or private game
	public GameplayModel connectToGameplayRoom(ConnectToGameRequest request) throws GameNotFoundException {
		final String nickname = request.getPlayerNickname().replaceAll("\"", "");

		if (request.getAccessToken() != null) {
			final String accessToken = request.getAccessToken().replaceAll("\"", "");
			return joinPrivateGameplay(nickname, accessToken);
		}

		return joinRandomGameplay(nickname);
	}

    // SELECT gameplay_room FROM gameplay_repository gp
    // WHERE gameplay_room.accessToken = :accessToken
    // AND gameplay_room.gameplayStatus = WAITING
    @Transactional
    public GameplayModel joinRandomGameplay(String nickname) {
        // create a default new gameplay model
        final PlayerModel playerModel = new PlayerModel(nickname, false, false, 0);
        final GameplayModel newGameplayModel = new GameplayModel(
        		GameplayStatus.WAITING, PlayerStatus.PLAYER_ONE, playerModel);

        // search for an open gameplay model to join, if no games are found create one
	    GameplayModel gameplayModel = gameplayRepository.joinRandomGame(GameplayStatus.WAITING).orElse(newGameplayModel);

	    // if a new gameplay model was created, save it to the database
	    if (gameplayModel.getGameID().equals(newGameplayModel.getGameID())) {
	    	gameplayRepository.save(gameplayModel);
	    } else {
	    	// else join gameplay room and change status
		    gameplayModel.setPlayerTwo(playerModel);
		    gameplayModel.setGameStatus(GameplayStatus.PREPARING);
	    }

	    return gameplayModel;
    }

	// UPDATE gameplay_room
	// SET player_two = :request.playerTwo, gameplay_status = PREPARING
	// WHERE access_token = :request.accessToken
	@Transactional
	public GameplayModel joinPrivateGameplay(String nickname, String accessToken) throws GameNotFoundException {
		// initializing second player
		final PlayerModel playerTow = new PlayerModel(nickname, false, false, 0);

		// search for the private gameplay room and check the accessToken
		GameplayModel gameplayModel = gameplayRepository.joinPrivateGame(accessToken, GameplayStatus.WAITING)
				.orElseThrow(() -> new GameNotFoundException(accessToken));

		// set the second player and change the state of the gameplay
		gameplayModel.setPlayerTwo(playerTow);
		gameplayModel.setGameStatus(GameplayStatus.PREPARING);

		return gameplayModel;
	}

	// INSERT INTO gameplay_repository VALUES gameplay_room
	public GameplayModel createNewGameplayRoom(String playerNickname) {
		// create a new gameplay model
		final String nickname = playerNickname.replaceAll("\"", "");
		final PlayerModel playerOne = new PlayerModel(nickname, false, false, 0);
		final GameplayModel gameplayModel = new GameplayModel(
				GameplayStatus.WAITING, PlayerStatus.PLAYER_ONE, accessTokenGenerator(), playerOne);

		// make sure that the access token is unique
		while (gameplayRepository.findExistentToken(gameplayModel.getAccessToken()).isPresent()) {
			gameplayModel.setAccessToken(accessTokenGenerator());
		}

		// save the game to the database
		gameplayRepository.save(gameplayModel);
		return gameplayModel;
	}

	// returns a unique string representing the access token for a private gameplay room
	private String accessTokenGenerator() {
		final String characters = "0AaBbCc1DdEeF2fGgHh3IiJjK4kLlMmN5nOoPpQqRr6SsTtU7uVvWw8XxYyZz9";
		final int tokenLength = 6;

		String accessToken = "";
		Random random = new Random();

		for (int i = 0; i < tokenLength; i++) {
			final char aux = characters.charAt(random.nextInt(characters.length()));
			accessToken = accessToken + aux;
		}

		return accessToken;
	}

	// UPDATE gameplay_room
	// IF(:request.identity = "PLAYER_ONE",
	// SET player_one.is_ready = true, player_one.planes_border = :request.planesBorder,
	// SET player_two.is_ready = true, player_tow.planes_border = :request.planesBorder)
	@Transactional
    public void setReady(SetReadyRequest request) throws GameNotFoundException, InvalidBorderException {
	    final VerifyBorder verifyBorder = new VerifyBorder(request.getPlanesBorder());
	    final String gameID = request.getGameID().replaceAll("\"", "");
	    final PlayerStatus identity = request.getIdentity();
	    final int[][] planesBorder = request.getPlanesBorder();

	    // verify planes border and notify the player
	    // if the planes border is not valid
    	if (verifyBorder.verifyBorder()) {
		    // search for a gameplay room based on the gameID
		    GameplayModel gamePlayModel = gameplayRepository.findGameplayByGameID(gameID)
				    .orElseThrow(() -> new GameNotFoundException(gameID));

		    // check where the request is coming from (playerOne or playerTow),
		    // change the status of the game and the planes border of the player
		    if(identity.equals(PlayerStatus.PLAYER_ONE)) {
			    gamePlayModel.getPlayerOne().setIsReady(true);
			    gamePlayModel.getPlayerOne().setPlanesBorder(planesBorder);
		    } else {
			    gamePlayModel.getPlayerTwo().setIsReady(true);
			    gamePlayModel.getPlayerTwo().setPlanesBorder(planesBorder);
		    }
	    } else {
    		throw new InvalidBorderException();
	    }
    }

	// UPDATE gameplay_room
	// IF(:request.identity = "PLAYER_ONE",
	// SET player_tow.planes_border = :request.planesBorder,
	// SET player_one.planes_border = :request.planesBorder)
	@Transactional
	public void attackEnemy(AttackEnemyRequest request) throws GameNotFoundException {
		final String gameID = request.getGameID().replaceAll("\"", "");
		final PlayerStatus identity = request.getIdentity();
		final int posX = request.getPosX();
		final int posY = request.getPosY();

		// search for a gameplay room based on the gameID
		GameplayModel gameplayModel = gameplayRepository.findGameplayByGameID(gameID)
				.orElseThrow(() -> new GameNotFoundException(gameID));

		// check if the game is still in progress
		if (gameplayModel.getGameStatus().equals(GameplayStatus.IN_PROGRESS)) {
			if (identity.equals(PlayerStatus.PLAYER_ONE)) {
				if (gameplayModel.getPlayerTwo().setPlanesBorderValue(posX, posY)) {
					gameplayModel.getPlayerOne().incrementDestroyedPlanes();
				}
				gameplayModel.setPlayerStatus(PlayerStatus.PLAYER_TWO);
			} else {
				if (gameplayModel.getPlayerOne().setPlanesBorderValue(posX, posY)) {
					gameplayModel.getPlayerTwo().incrementDestroyedPlanes();
				}
				gameplayModel.setPlayerStatus(PlayerStatus.PLAYER_ONE);
			}
		}
	}

    // UPDATE gameplay_room
    // IF(:request.identity = "PLAYER_ONE",
    // SET player_one.hasSurrendered = FALSE,
    // SET player_two.hasSurrendered = FALSE)
    @Transactional
    public void setSurrendered(SetSurrenderedRequest request) throws GameNotFoundException {
	    final String gameID = request.getGameID().replaceAll("\"", "");
	    final PlayerStatus identity = request.getIdentity();

	    // search for a gameplay room based on the gameID
	    GameplayModel gamePlayModel = gameplayRepository.findGameplayByGameID(gameID)
		        .orElseThrow(() -> new GameNotFoundException(gameID));

	    // check where the request is coming from (playerOne or playerTow ?),
	    // change the status of "hasSurrendered" to true and "isConnected" to false
        if(identity.equals(PlayerStatus.PLAYER_ONE)) {
            gamePlayModel.getPlayerOne().setHasSurrendered(true);
        } else {
            gamePlayModel.getPlayerTwo().setHasSurrendered(true);
        }
    }

    // DELETE gameplay_room FROM gameplay_repository WHERE gameplay_room.gameID = :gameID
	@Transactional
	public void deleteGameplayRoom(String uuid) throws GameNotFoundException {
		final String gameID = uuid.replaceAll("\"", "");
		final Optional<GameplayModel> exists = gameplayRepository.findGameplayByGameID(gameID);

		// check if the game exists in the data base and delete it
		if (!exists.isPresent()) {
			throw new GameNotFoundException(gameID);
		}

		gameplayRepository.deleteGameplayByGameID(gameID);
    }

	@Scheduled(fixedRate = 5000)
	@Transactional
	public void scheduledTask() {
    	gameplayRepository.findAll().stream().filter(gameplayModel -> {
		    final PlayerModel playerOne = gameplayModel.getPlayerOne();
		    final PlayerModel playerTwo = gameplayModel.getPlayerTwo();
		    final GameplayStatus gameStatus = gameplayModel.getGameStatus();

		    // check all the gameplay rooms and if any of them have both players ready
		    // change the gameplay status to 'IN_PROGRESS'
		    if (gameplayModel.getGameStatus().equals(GameplayStatus.PREPARING)) {
			    // check if both players are connected and ready to start the game
			    if (playerOne.getIsReady() && playerTwo.getIsReady()
					    && gameStatus.equals(GameplayStatus.PREPARING)) {
				    gameplayModel.setGameStatus(GameplayStatus.IN_PROGRESS);
				    return true;
			    }
		    }

		    if (gameplayModel.getGameStatus().equals(GameplayStatus.IN_PROGRESS)) {
			    // check if any player has destroyed all enemy's planes
			    if (playerOne.getDestroyedPlanes() == 3) {
				    gameplayModel.setGameStatus(GameplayStatus.FINISHED);
				    gameplayModel.setPlayerStatus(PlayerStatus.PLAYER_ONE);
				    return true;
			    } else if (playerTwo.getDestroyedPlanes() == 3) {
				    gameplayModel.setGameStatus(GameplayStatus.FINISHED);
				    gameplayModel.setPlayerStatus(PlayerStatus.PLAYER_TWO);
				    return true;
			    }
		    }

		    if (gameplayModel.getGameStatus().equals(GameplayStatus.FINISHED)) {
		    	// check if the game has finished
			    if (playerOne.getHasSurrendered() && playerTwo.getHasSurrendered()) {
			    	gameplayRepository.delete(gameplayModel);
			    }
		    }

		    return false;
	    }).findFirst();
	}
}
