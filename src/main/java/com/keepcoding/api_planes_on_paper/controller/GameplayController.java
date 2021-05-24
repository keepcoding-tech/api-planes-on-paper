package com.keepcoding.api_planes_on_paper.controller;

import com.keepcoding.api_planes_on_paper.controller.requests.JoinPrivateGameRequest;
import com.keepcoding.api_planes_on_paper.controller.requests.AttackEnemyPlanesRequest;
import com.keepcoding.api_planes_on_paper.controller.requests.PlayerIsReadyRequest;
import com.keepcoding.api_planes_on_paper.controller.requests.PlayerHasSurrenderedRequest;
import com.keepcoding.api_planes_on_paper.exceptions.GameNotFoundException;
import com.keepcoding.api_planes_on_paper.exceptions.InvalidBorderException;
import com.keepcoding.api_planes_on_paper.models.GameplayModel;
import com.keepcoding.api_planes_on_paper.service.GameplayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/game")
public class GameplayController {
    private final GameplayService gameplayService;

    @Autowired
    public GameplayController(GameplayService gameplayService) {
        this.gameplayService = gameplayService;
    }

    // get a list with all random games
    @GetMapping(path = "/list")
    public ResponseEntity<List<GameplayModel>> getAllRandomGames() {
        return ResponseEntity.ok(gameplayService.getAllRandomGames());
    }

    // get a specific random game
    @PostMapping(path = "/data")
    public ResponseEntity<GameplayModel> getRandomGame(@RequestBody String gameID) throws GameNotFoundException {
        return ResponseEntity.ok(gameplayService.getRandomGameplay(gameID));
    }

    // connect the player to a random game
    @PostMapping(path = "/connect-to-random-game")
    public ResponseEntity<GameplayModel> connectToRandomGameplay(@RequestBody String playerNickname) {
        return ResponseEntity.ok(gameplayService.connectToRandomGameplay(playerNickname));
    }

    // create new private game
    @PostMapping(path = "/create-private-game")
    public ResponseEntity<GameplayModel> createNewPrivateGame(@RequestBody String playerNickname) {
        return ResponseEntity.ok(gameplayService.createNewPrivateGame(playerNickname));
    }

    // join private game
    @PostMapping(path = "/join-private-game")
    public ResponseEntity<GameplayModel> joinPrivateGame(@RequestBody JoinPrivateGameRequest request) throws GameNotFoundException {
        return ResponseEntity.ok(gameplayService.joinPrivateGame(request));
    }

    // the player is ready to start the game
    @PutMapping(path = "/player-is-ready")
    public void playerIsReady(@RequestBody PlayerIsReadyRequest playerIsReadyRequest) throws GameNotFoundException, InvalidBorderException {
        gameplayService.playerIsReady(playerIsReadyRequest);
    }

    @PutMapping(path = "/attack")
    public void attackPlanes(@RequestBody AttackEnemyPlanesRequest attackEnemyPlanesRequest) throws GameNotFoundException {
        gameplayService.attackPlanes(attackEnemyPlanesRequest);
    }

    // the player has surrendered
    @PutMapping(path = "/surrender")
    public void playerHasSurrendered(@RequestBody PlayerHasSurrenderedRequest playerHasSurrenderedRequest) throws GameNotFoundException {
        gameplayService.playerHasSurrendered(playerHasSurrenderedRequest);
    }

    // delete a specific random game
    @DeleteMapping(path = "/delete")
    public void deleteGameplay(@RequestBody String gameID) throws GameNotFoundException {
        gameplayService.deleteGameplay(gameID);
    }
}
