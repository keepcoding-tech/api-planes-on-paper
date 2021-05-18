package com.keepcoding.api_planes_on_paper.service;

import com.keepcoding.api_planes_on_paper.controller.requests.private_game_requests.JoinPrivateGameRequest;
import com.keepcoding.api_planes_on_paper.exceptions.GameNotFoundException;
import com.keepcoding.api_planes_on_paper.models.GameplayStatus;
import com.keepcoding.api_planes_on_paper.models.private_game.PrivateGameplay;
import com.keepcoding.api_planes_on_paper.models.private_game.PrivateGamePlayer;
import com.keepcoding.api_planes_on_paper.storage.PrivateGameplayRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

@Service
@EnableScheduling
public class PrivateGameplayService {
	private final PrivateGameplayRepository privateGameplayRepository;

	@Autowired
	public PrivateGameplayService(PrivateGameplayRepository privateGameplayRepository) {
		this.privateGameplayRepository = privateGameplayRepository;
	}

	// SELECT * FROM private_gameplay_repository
	public List<PrivateGameplay> getAllPrivateGames() {
		return privateGameplayRepository.findAll();
	}

	// SELECT private_gameplay FROM private_gameplay_repository WHERE gameID = {gameID}
	public PrivateGameplay getPrivateGame(String gameID) throws GameNotFoundException {
		final String game = gameID.replaceAll("\"", "");

		return privateGameplayRepository.findById(Long.parseLong(game)).orElseThrow(() ->
				new GameNotFoundException("game with id " + gameID + " doesn't exists!"));
	}

	// POST private_gameplay TO private_gameplay_repository
	public PrivateGameplay createNewPrivateGame(String playerNickname) {
		// create a new private game
		final String nickname = playerNickname.replaceAll("\"", "");
		final PrivateGamePlayer playerOne = new PrivateGamePlayer(nickname, true, false, false, 0);
		final PrivateGamePlayer playerTow = new PrivateGamePlayer("-", true, false, false, 0);
		PrivateGameplay newPrivateGame = new PrivateGameplay(accessTokenGenerator(), GameplayStatus.WAITING, playerOne, playerTow);

		// make sure that the access token is unique
		while (privateGameplayRepository.findPrivateGameByAccessToken(newPrivateGame.getAccessToken()).isPresent()) {
			newPrivateGame.setAccessToken(accessTokenGenerator());
		}

		// save the game to the database
		privateGameplayRepository.save(newPrivateGame);
		return newPrivateGame;
	}

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
	public PrivateGameplay joinPrivateGame(JoinPrivateGameRequest request) throws GameNotFoundException {
		final String accessToken = request.getAccessToken().replaceAll("\"", "");
		final String playerNickname = request.getPlayerTwoNickname().replaceAll("\"", "");
		final PrivateGamePlayer playerTow = new PrivateGamePlayer(playerNickname, true, false, false, 0);

		PrivateGameplay privateGamePlay = privateGameplayRepository.findAll().stream()
				.filter(pgm -> pgm.getGameplayStatus().equals(GameplayStatus.WAITING) && pgm.getAccessToken().equals(accessToken)).findFirst()
				.orElseThrow(() -> new GameNotFoundException("game with access token: " + request.getAccessToken() + " doesn't exists!"));

		privateGamePlay.setPlayerTwo(playerTow);
		privateGamePlay.setGameplayStatus(GameplayStatus.IN_PROGRESS);

		return privateGamePlay;
	}
}
