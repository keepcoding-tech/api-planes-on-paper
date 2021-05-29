package com.keepcoding.api_planes_on_paper.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "gameplay_room")
public class GameplayModel {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "game_id", updatable = false, nullable = false)
    private String gameID = UUID.randomUUID().toString();

    @Column(name = "gameplay_status", updatable = true, nullable = false)
    private GameplayStatus gameStatus;

    @Column(name = "player_status", updatable = true, nullable = false)
    private PlayerStatus playerStatus;

    @Column(name = "access_token", updatable = false, nullable = true)
    private String accessToken;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_one", updatable = true, nullable = false)
    private PlayerModel playerOne;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_two", updatable = true, nullable = true)
    private PlayerModel playerTwo;

    // constructors
    public GameplayModel() {}

    public GameplayModel(
            GameplayStatus gameStatus,
            PlayerStatus playerStatus,
            PlayerModel playerOne
    ) {
        this.gameStatus = gameStatus;
        this.playerStatus = playerStatus;
        this.playerOne = playerOne;
    }

    public GameplayModel(
            GameplayStatus gameStatus,
            PlayerStatus playerStatus,
            String accessToken,
            PlayerModel playerOne
    ) {
        this.gameStatus = gameStatus;
        this.playerStatus = playerStatus;
        this.accessToken = accessToken;
        this.playerOne = playerOne;
    }

    // setters
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setGameStatus(GameplayStatus gamePlayStatus) {
        this.gameStatus = gamePlayStatus;
    }

    public void setPlayerStatus(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }

    public void setPlayerOne(PlayerModel playerOne) {
        this.playerOne = playerOne;
    }

    public void setPlayerTwo(PlayerModel playerTwo) {
        this.playerTwo = playerTwo;
    }

    // getters
    public String getGameID() {
        return gameID;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    public GameplayStatus getGameStatus() {
        return gameStatus;
    }

    public PlayerModel getPlayerOne() {
        return playerOne;
    }

    public PlayerModel getPlayerTwo() {
        return playerTwo;
    }
}


