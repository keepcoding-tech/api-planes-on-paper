package com.keepcoding.api_planes_on_paper.service;

import com.keepcoding.api_planes_on_paper.controller.requests.JoinPrivateGameRequest;
import com.keepcoding.api_planes_on_paper.controller.requests.AttackEnemyPlanesRequest;
import com.keepcoding.api_planes_on_paper.controller.requests.PlayerIsReadyRequest;
import com.keepcoding.api_planes_on_paper.controller.requests.PlayerHasSurrenderedRequest;
import com.keepcoding.api_planes_on_paper.exceptions.GameNotFoundException;
import com.keepcoding.api_planes_on_paper.exceptions.InvalidBorderException;
import com.keepcoding.api_planes_on_paper.models.GameplayStatus;
import com.keepcoding.api_planes_on_paper.models.GameplayModel;
import com.keepcoding.api_planes_on_paper.models.PlayerModel;
import com.keepcoding.api_planes_on_paper.service.util.VerifyBorder;
import com.keepcoding.api_planes_on_paper.storage.GameplayRepository;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import javax.transaction.Transactional;

@Service
@EnableScheduling
public class GameplayService {
    private final GameplayRepository gameplayRepository;

    @Autowired
    public GameplayService(GameplayRepository gameplayRepository) {
        this.gameplayRepository = gameplayRepository;
    }

    // SELECT * FROM random_gameplay_repository
    public List<GameplayModel> getAllRandomGames() {
        return gameplayRepository.findAll();
    }

    // SELECT random_gameplay FROM random_gameplay_repository WHERE gameID = {gameID}
    public GameplayModel getRandomGameplay(String gameID) throws GameNotFoundException {
        final String game = gameID.replaceAll("\"", "");

        return gameplayRepository.findById(Long.parseLong(game)).orElseThrow(() ->
                new GameNotFoundException("game with id " + gameID + " doesn't exists!"));
    }

    // POST random_gameplay TO random_gameplay_repository
    @Transactional
    public GameplayModel connectToRandomGameplay(String playerNickname) {
        // create a default new random gameplay	
        final String nickname = playerNickname.replaceAll("\"", "");
        final PlayerModel playerOne = new PlayerModel(nickname, true, false, false, true, 0);
        final PlayerModel playerTwo = new PlayerModel("-", false, false, false, false, 0);
        final GameplayModel newGameplayModel = new GameplayModel(GameplayStatus.WAITING, playerOne, playerTwo);

        // search for an open random gameplay to join, if not games are found create one
	    GameplayModel gamePlayModel = gameplayRepository.findAll().stream()
			    .filter(rgp -> rgp.getGameplayStatus().equals(GameplayStatus.WAITING)
			    && rgp.getAccessToken() == null).findFirst().orElse(newGameplayModel);

	    // if a new random gameplay was created, save it to the database
	    if (gamePlayModel == newGameplayModel) {
	    	gameplayRepository.save(gamePlayModel);
	    } else {
	    	// else join random gameplay and change status
		    playerTwo.setPlayerNickname(nickname);
		    playerTwo.setIsConnected(true);

		    gamePlayModel.setPlayerTwo(playerTwo);
		    gamePlayModel.setGameplayStatus(GameplayStatus.PREPARING);
	    }
	    return gamePlayModel;
    }

	// POST private_gameplay TO private_gameplay_repository
	public GameplayModel createNewPrivateGame(String playerNickname) {
		// create a new private game
		final String nickname = playerNickname.replaceAll("\"", "");
		final PlayerModel playerOne = new PlayerModel(nickname, true, false, false, true, 0);
		final PlayerModel playerTwo = new PlayerModel("-", false, false, false, false, 0);
		final GameplayModel newGameplayModel = new GameplayModel(GameplayStatus.WAITING, accessTokenGenerator(), playerOne, playerTwo);

		// make sure that the access token is unique
		while (gameplayRepository.findPrivateGameByAccessToken(newGameplayModel.getAccessToken()).isPresent()) {
			newGameplayModel.setAccessToken(accessTokenGenerator());
		}

		// save the game to the database
		gameplayRepository.save(newGameplayModel);
		return newGameplayModel;
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

	// PUT
	@Transactional
	public GameplayModel joinPrivateGame(JoinPrivateGameRequest request) throws GameNotFoundException {
		final String accessToken = request.getAccessToken().replaceAll("\"", "");
		final String playerNickname = request.getPlayerNickname().replaceAll("\"", "");
		final PlayerModel playerTow = new PlayerModel(playerNickname, true, false, false, false, 0);

		GameplayModel gameplayModel = gameplayRepository.findAll().stream()
				.filter(rgp -> rgp.getGameplayStatus().equals(GameplayStatus.WAITING) && rgp.getAccessToken().equals(accessToken)).findFirst()
				.orElseThrow(() -> new GameNotFoundException("game with access token: " + request.getAccessToken() + " doesn't exists!"));

		gameplayModel.setPlayerTwo(playerTow);
		gameplayModel.setGameplayStatus(GameplayStatus.PREPARING);

		return gameplayModel;
	}
     
    // PUT player_one.isReady && player_two.isReady TO random_gameplay FROM random_gameplay_repository
    @Transactional
    public void playerIsReady(PlayerIsReadyRequest playerIsReadyRequest) throws GameNotFoundException, InvalidBorderException {
	    final VerifyBorder verifyBorder = new VerifyBorder(playerIsReadyRequest.getPlanesBorder());

	    // verify planes border and notify the player
	    // if the planes border is not good
    	if (verifyBorder.verifyBorder()) {
		    // update isReady data to 'true'
		    GameplayModel gamePlayModel = gameplayRepository.findById(playerIsReadyRequest.getGameID())
				    .orElseThrow(() -> new GameNotFoundException("room with id: " + playerIsReadyRequest.getGameID() + " doesn't exists!"));

		    // check where the request is coming from,
		    // change the status and planes border
		    if(playerIsReadyRequest.getPlayer().equals("playerOne")) {
			    gamePlayModel.getPlayerOne().setIsReady(true);
			    gamePlayModel.getPlayerOne().setPlanesBorder(playerIsReadyRequest.getPlanesBorder());
		    } else {
			    gamePlayModel.getPlayerTwo().setIsReady(true);
			    gamePlayModel.getPlayerTwo().setPlanesBorder(playerIsReadyRequest.getPlanesBorder());
		    }
	    } else {
    		throw new InvalidBorderException("the planes border is not valid");
	    }
    }

    // PUT
    @Transactional
    public void attackPlanes(AttackEnemyPlanesRequest attackEnemyPlanesRequest) throws GameNotFoundException {
    	final Long gameID = attackEnemyPlanesRequest.getGameID();
    	final String player = attackEnemyPlanesRequest.getPlayer();
    	final int posX = attackEnemyPlanesRequest.getPosX();
    	final int posY = attackEnemyPlanesRequest.getPosY();

    	GameplayModel gameplayModel = gameplayRepository.findById(gameID)
			    .orElseThrow(() -> new GameNotFoundException("game with id: " + gameID + " doesn't exists!"));

	    if (player.equals("playerOne")) {
    		gameplayModel.getPlayerTwo().setPlanesBorderValue(posX, posY);
		    gameplayModel.getPlayerTwo().setPlayerTurn(true);
		    gameplayModel.getPlayerOne().setPlayerTurn(false);
	    } else {
		    gameplayModel.getPlayerOne().setPlanesBorderValue(posX, posY);
    		gameplayModel.getPlayerOne().setPlayerTurn(true);
    		gameplayModel.getPlayerTwo().setPlayerTurn(false);
	    }
    }

    // PUT player_one.hasSurrendered && player_two.hasSurrendered TO random_gameplay FROM random_game_repository
    @Transactional
    public void playerHasSurrendered(PlayerHasSurrenderedRequest request) throws GameNotFoundException {
        GameplayModel gamePlayModel = gameplayRepository.findById(request.getGameID())
		        .orElseThrow(() -> new GameNotFoundException("game with id: " + request.getGameID() + " doesn't exists!"));

        if(request.getPlayer().equals("playerOne")) {
            gamePlayModel.getPlayerOne().setHasSurrendered(true);
            gamePlayModel.getPlayerOne().setIsConnected(false);
        } else {
            gamePlayModel.getPlayerTwo().setHasSurrendered(true);
            gamePlayModel.getPlayerTwo().setIsConnected(false);
        }
    }

    // DELETE random_gameplay FROM random_gameplay_repository WHERE gameID = {gameID}
	public void deleteGameplay(String gameID) throws GameNotFoundException {
		final String game = gameID.replaceAll("\"", "");
		final boolean exists = gameplayRepository.existsById(Long.parseLong(game));

		if (!exists) {
			throw new GameNotFoundException("game with id: " + game + " doesn't exists!");
		}
		gameplayRepository.deleteById(Long.parseLong(game));
    }

	// check all the gameplay rooms and if any of them have both players ready
	// change the gameplay status to 'IN_PROGRESS'
	@Scheduled(fixedRate = 5000)
	@Transactional
	public void checkGameplayStatus() {
		gameplayRepository.findAll().stream().filter(gameplayRoom -> {
			final boolean playerOneIsReady = gameplayRoom.getPlayerOne().getIsReady();
			final boolean playerTwoIsReady = gameplayRoom.getPlayerTwo().getIsReady();
			final GameplayStatus gameplayStatus = gameplayRoom.getGameplayStatus();

			// check if both players are connected and if both players are ready
			if (playerOneIsReady && playerTwoIsReady && gameplayStatus.equals(GameplayStatus.PREPARING)) {
				gameplayRoom.setGameplayStatus(GameplayStatus.IN_PROGRESS);

				return true;
			}
			return false;
		}).findFirst();
	}
}
