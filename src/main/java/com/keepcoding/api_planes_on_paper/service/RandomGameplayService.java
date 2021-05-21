package com.keepcoding.api_planes_on_paper.service;

import com.keepcoding.api_planes_on_paper.controller.requests.random_game_requests.PlayerIsReadyRequest;
import com.keepcoding.api_planes_on_paper.controller.requests.random_game_requests.PlayerHasSurrenderedRequest;
import com.keepcoding.api_planes_on_paper.exceptions.GameNotFoundException;
import com.keepcoding.api_planes_on_paper.exceptions.InvalidPlanesBorderException;
import com.keepcoding.api_planes_on_paper.models.GameplayStatus;
import com.keepcoding.api_planes_on_paper.models.random_game.RandomGameplay;
import com.keepcoding.api_planes_on_paper.models.random_game.RandomGamePlayer;
import com.keepcoding.api_planes_on_paper.service.util.VerifyBorder;
import com.keepcoding.api_planes_on_paper.storage.RandomGameplayRepository;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.transaction.Transactional;

@Service
@EnableScheduling
public class RandomGameplayService {
    private final RandomGameplayRepository randomGameplayRepository;

    @Autowired
    public RandomGameplayService(RandomGameplayRepository randomGameplayRepository) {
        this.randomGameplayRepository = randomGameplayRepository;
    }

    // SELECT * FROM random_gameplay_repository
    public List<RandomGameplay> getAllRandomGames() {
        return randomGameplayRepository.findAll();
    }

    // SELECT random_gameplay FROM random_gameplay_repository WHERE gameID = {gameID}
    public RandomGameplay getRandomGameplay(String gameID) throws GameNotFoundException {
        final String game = gameID.replaceAll("\"", "");

        return randomGameplayRepository.findById(Long.parseLong(game)).orElseThrow(() ->
                new GameNotFoundException("game with id " + gameID + " doesn't exists!"));
    }


    // POST random_gameplay TO random_gameplay_repository
    @Transactional
    public RandomGameplay connectToRandomGameplay(String playerNickname) {
        // create a default new random gameplay	
        final String nickname = playerNickname.replaceAll("\"", "");
        final RandomGamePlayer playerOne = new RandomGamePlayer(nickname, true, false, false, 0);
        final RandomGamePlayer playerTwo = new RandomGamePlayer("-", false, false, false, 0);
        final RandomGameplay newRandomGameplay = new RandomGameplay(GameplayStatus.WAITING, playerOne, playerTwo);

        // search for an open random gameplay to join, if not games are found create one
	    RandomGameplay randomGamePlay = randomGameplayRepository.findAll()
			    .stream().filter(rgp -> rgp.getGameplayStatus().equals(GameplayStatus.WAITING))
			    .findFirst().orElse(newRandomGameplay);

	    // if a new random gameplay was created, save it to the database
	    if (randomGamePlay == newRandomGameplay) {
	    	randomGameplayRepository.save(randomGamePlay);
	    } else {
	    	// else join random gameplay and change status
		    playerTwo.setPlayerNickname(nickname);
		    playerTwo.setIsConnected(true);

		    randomGamePlay.setPlayerTwo(playerTwo);
		    randomGamePlay.setGameplayStatus(GameplayStatus.PREPARING);
	    }
	    return randomGamePlay;
    }

    // check all the gameplay rooms and if any of them have both players ready 
    // change the gameplay status to 'IN_PROGRESS'
    @Scheduled(fixedRate = 5000)
    @Transactional
    public void checkGameplayStatus() {
        randomGameplayRepository.findAll().stream().filter(gameplayRoom -> {
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
     
    // PUT player_one.isReady && player_two.isReady TO random_gameplay FROM random_gameplay_repository
    @Transactional
    public void playerIsReady(PlayerIsReadyRequest playerIsReadyRequest) throws GameNotFoundException, InvalidPlanesBorderException {
	    final VerifyBorder verifyBorder = new VerifyBorder(playerIsReadyRequest.getPlanesBorder());

	    // verify planes border and notify the player
	    // if the planes border is not good
    	if (verifyBorder.verifyBorder()) {
		    // update isReady data to 'true'
		    RandomGameplay randomGamePlay = randomGameplayRepository.findById(playerIsReadyRequest.getGameID())
				    .orElseThrow(() -> new GameNotFoundException("room with id: " + playerIsReadyRequest.getGameID() + " doesn't exists!"));

		    // check where the request is coming from,
		    // change the status and planes border
		    if(playerIsReadyRequest.getPlayer().equals("playerOne")) {
			    randomGamePlay.getPlayerOne().setIsReady(true);
			    randomGamePlay.getPlayerOne().setPlanesBorder(playerIsReadyRequest.getPlanesBorder());
		    } else {
			    randomGamePlay.getPlayerTwo().setIsReady(true);
			    randomGamePlay.getPlayerTwo().setPlanesBorder(playerIsReadyRequest.getPlanesBorder());
		    }
	    } else {
    		throw new InvalidPlanesBorderException("the planes border is not valid");
	    }
    }

    // PUT player_one.hasSurrendered && player_two.hasSurrendered TO random_gameplay FROM random_game_repository
    @Transactional
    public void playerHasSurrendered(PlayerHasSurrenderedRequest request) throws GameNotFoundException {
        RandomGameplay randomGamePlay = randomGameplayRepository.findById(request.getGameID())
		        .orElseThrow(() -> new GameNotFoundException("game with id: " + request.getGameID() + " doesn't exists!"));

        if(request.getPlayer().equals("playerOne")) {
            randomGamePlay.getPlayerOne().setHasSurrendered(true);
            randomGamePlay.getPlayerOne().setIsConnected(false);
        } else {
            randomGamePlay.getPlayerTwo().setHasSurrendered(true);
            randomGamePlay.getPlayerTwo().setIsConnected(false);
        }
    }

    // DELETE random_gameplay FROM random_gameplay_repository WHERE gameID = {gameID}
	public void deleteGameplay(String gameID) throws GameNotFoundException {
		final String game = gameID.replaceAll("\"", "");
		final boolean exists = randomGameplayRepository.existsById(Long.parseLong(game));

		if (!exists) {
			throw new GameNotFoundException("game with id: " + game + " doesn't exists!");
		}
		randomGameplayRepository.deleteById(Long.parseLong(game));
    }
}
