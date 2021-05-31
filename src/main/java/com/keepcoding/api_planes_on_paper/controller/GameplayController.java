package com.keepcoding.api_planes_on_paper.controller;

import com.keepcoding.api_planes_on_paper.controller.requests.ConnectToGameRequest;
import com.keepcoding.api_planes_on_paper.controller.requests.AttackEnemyRequest;
import com.keepcoding.api_planes_on_paper.controller.requests.SetReadyRequest;
import com.keepcoding.api_planes_on_paper.controller.requests.SetSurrenderedRequest;
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
    public ResponseEntity<List<GameplayModel>> getAllGameplayRooms() {
        return ResponseEntity.ok(gameplayService.getAllGameplayRooms());
    }

    // get a specific random game
    @PostMapping(path = "/data")
    public ResponseEntity<GameplayModel> getGameplayRoom(@RequestBody String gameID) throws GameNotFoundException {
        return ResponseEntity.ok(gameplayService.getGameplayRoom(gameID));
    }

    // connect to a random or private game
    @PostMapping(path = "/connect-to-game")
    public ResponseEntity<GameplayModel> connectToGameplayRoom(
            @RequestBody ConnectToGameRequest request) throws GameNotFoundException {
        return  ResponseEntity.ok(gameplayService.connectToGameplayRoom(request));
    }

    // create new private game
    @PostMapping(path = "/create-game")
    public ResponseEntity<GameplayModel> createNewGameplayRoom(@RequestBody String playerNickname) {
        return ResponseEntity.ok(gameplayService.createNewGameplayRoom(playerNickname));
    }

    // the player is ready to start the game
    @PutMapping(path = "/set-ready")
    public void setReady(@RequestBody SetReadyRequest request)
            throws GameNotFoundException, InvalidBorderException {
        gameplayService.setReady(request);
    }

    // modify a specific element from planesBorder matrix
    @PutMapping(path = "/attack")
    public void attackEnemy(@RequestBody AttackEnemyRequest request) throws GameNotFoundException {
        gameplayService.attackEnemy(request);
    }

    // the player has surrendered
    @PutMapping(path = "/surrender")
    public void setSurrendered(@RequestBody SetSurrenderedRequest request) throws GameNotFoundException {
        gameplayService.setSurrendered(request);
    }

    // delete a specific random game
    @DeleteMapping(path = "/delete")
    public void deleteGameplayRoom(@RequestBody String gameID) throws GameNotFoundException {
        gameplayService.deleteGameplayRoom(gameID);
    }
}
