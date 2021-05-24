package com.keepcoding.api_planes_on_paper.models;

import javax.persistence.*;

@Entity
@Table(name = "random_game_model")
public class GameplayModel {
    @Id
    @GeneratedValue
    private Long gameID;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "game_state")
    private GameplayStatus gameplayStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_one")
    private PlayerModel playerOne;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_two")
    private PlayerModel playerTwo;

    // constructors
    public GameplayModel() {}

    public GameplayModel(
            GameplayStatus gameplayStatus,
            PlayerModel playerOne,
            PlayerModel playerTwo
    ) {
        this.gameplayStatus = gameplayStatus;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public GameplayModel(
            GameplayStatus gameplayStatus,
            String accessToken,
            PlayerModel playerOne,
            PlayerModel playerTwo
    ) {
        this.gameplayStatus = gameplayStatus;
        this.accessToken = accessToken;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    // setters
    public void setGameID(Long gameID) {
        this.gameID = gameID;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setGameplayStatus(GameplayStatus gamePlayStatus) {
        this.gameplayStatus = gamePlayStatus;
    }

    public void setPlayerOne(PlayerModel playerOne) {
        this.playerOne = playerOne;
    }

    public void setPlayerTwo(PlayerModel playerTwo) {
        this.playerTwo = playerTwo;
    }

    // getters
    public Long getGameID() {
        return gameID;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public GameplayStatus getGameplayStatus() {
        return gameplayStatus;
    }

    public PlayerModel getPlayerOne() {
        return playerOne;
    }

    public PlayerModel getPlayerTwo() {
        return playerTwo;
    }
}


