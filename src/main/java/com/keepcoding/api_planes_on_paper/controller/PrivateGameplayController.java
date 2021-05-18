package com.keepcoding.api_planes_on_paper.controller;

import com.keepcoding.api_planes_on_paper.controller.requests.private_game_requests.JoinPrivateGameRequest;
import com.keepcoding.api_planes_on_paper.exceptions.GameNotFoundException;
import com.keepcoding.api_planes_on_paper.models.private_game.PrivateGameplay;
import com.keepcoding.api_planes_on_paper.service.PrivateGameplayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/game")
public class PrivateGameplayController {
	private final PrivateGameplayService privateGamePlayService;

	@Autowired
	public PrivateGameplayController(PrivateGameplayService privateGamePlayService) {
		this.privateGamePlayService = privateGamePlayService;
	}

	// get a list with all private games
	@GetMapping(path = "/private-game/list")
	public ResponseEntity<List<PrivateGameplay>> getAllPrivateGames() {
		return ResponseEntity.ok(privateGamePlayService.getAllPrivateGames());
	}

	// get a specific private game
	@PostMapping(path = "/private-game/data")
	public ResponseEntity<PrivateGameplay> getPrivateGame(@RequestBody String gameID) throws GameNotFoundException {
		return ResponseEntity.ok(privateGamePlayService.getPrivateGame(gameID));
	}

	// create new private game
	@PostMapping(path = "/private-game/create-private-game")
	public ResponseEntity<PrivateGameplay> createNewPrivateGame(@RequestBody String playerNickname) {
		return ResponseEntity.ok(privateGamePlayService.createNewPrivateGame(playerNickname));
	}

	// join private game
	@PostMapping(path = "/private-game/join-private-game")
	public ResponseEntity<PrivateGameplay> joinPrivateGame(@RequestBody JoinPrivateGameRequest request) throws GameNotFoundException {
		return ResponseEntity.ok(privateGamePlayService.joinPrivateGame(request));
	}
}
