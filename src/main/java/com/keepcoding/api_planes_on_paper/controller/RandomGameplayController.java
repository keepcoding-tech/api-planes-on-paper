package com.keepcoding.api_planes_on_paper.controller;

import com.keepcoding.api_planes_on_paper.controller.requests.random_game_requests.AttackEnemyPlanesRequest;
import com.keepcoding.api_planes_on_paper.controller.requests.random_game_requests.PlayerIsReadyRequest;
import com.keepcoding.api_planes_on_paper.controller.requests.random_game_requests.PlayerHasSurrenderedRequest;
import com.keepcoding.api_planes_on_paper.exceptions.GameNotFoundException;
import com.keepcoding.api_planes_on_paper.exceptions.InvalidPlanesBorderException;
import com.keepcoding.api_planes_on_paper.models.random_game.RandomGameplay;
import com.keepcoding.api_planes_on_paper.service.RandomGameplayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/game")
public class RandomGameplayController {
    private final RandomGameplayService randomGameplayService;

    @Autowired
    public RandomGameplayController(RandomGameplayService randomGameplayService) {
        this.randomGameplayService = randomGameplayService;
    }

    // get a list with all random games
    @GetMapping(path = "/random-game/list")
    public ResponseEntity<List<RandomGameplay>> getAllRandomGames() {
        return ResponseEntity.ok(randomGameplayService.getAllRandomGames());
    }

    // get a specific random game
    @PostMapping(path = "/random-game/data")
    public ResponseEntity<RandomGameplay> getRandomGame(@RequestBody String gameID) throws GameNotFoundException {
        return ResponseEntity.ok(randomGameplayService.getRandomGameplay(gameID));
    }

    // connect the player to a random game
    @PostMapping(path = "/random-game/connect-to-random-game")
    public ResponseEntity<RandomGameplay> connectToRandomGameplay(@RequestBody String playerNickname) {
        return ResponseEntity.ok(randomGameplayService.connectToRandomGameplay(playerNickname));
    }

    // the player is ready to start the game
    @PutMapping(path = "/random-game/player-is-ready")
    public void playerIsReady(@RequestBody PlayerIsReadyRequest playerIsReadyRequest) throws GameNotFoundException, InvalidPlanesBorderException {
        randomGameplayService.playerIsReady(playerIsReadyRequest);
    }

    @PutMapping(path = "/random-game/attack")
    public void attackPlanes(@RequestBody AttackEnemyPlanesRequest attackEnemyPlanesRequest) throws GameNotFoundException {
        randomGameplayService.attackPlanes(attackEnemyPlanesRequest);
    }

    // the player has surrendered
    @PutMapping(path = "/random-game/surrender")
    public void playerHasSurrendered(@RequestBody PlayerHasSurrenderedRequest playerHasSurrenderedRequest) throws GameNotFoundException {
        randomGameplayService.playerHasSurrendered(playerHasSurrenderedRequest);
    }

    // delete a specific random game
    @DeleteMapping(path = "/random-game/delete")
    public void deleteGameplay(@RequestBody String gameID) throws GameNotFoundException {
        randomGameplayService.deleteGameplay(gameID);
    }
}
