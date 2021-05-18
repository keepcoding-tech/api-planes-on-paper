package com.keepcoding.api_planes_on_paper.models.random_game;

import com.keepcoding.api_planes_on_paper.models.GameplayStatus;

import javax.persistence.*;

@Entity
@Table(name = "random_game_model")
public class RandomGameplay {
    @Id
    @GeneratedValue
    private Long gameID;

    @Column(name = "game_state")
    private GameplayStatus gamePlayStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_one")
    private RandomGamePlayer playerOne;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_two")
    private RandomGamePlayer playerTwo;

    // constructors
    public RandomGameplay() {}

    public RandomGameplay(
            GameplayStatus gamePlayStatus,
            RandomGamePlayer playerOne,
            RandomGamePlayer playerTwo
    ) {
        this.gamePlayStatus = gamePlayStatus;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    // setters
    public void setGameID(Long gameID) {
        this.gameID = gameID;
    }

    public void setGameplayStatus(GameplayStatus gamePlayStatus) {
        this.gamePlayStatus = gamePlayStatus;
    }

    public void setPlayerOne(RandomGamePlayer playerOne) {
        this.playerOne = playerOne;
    }

    public void setPlayerTwo(RandomGamePlayer playerTwo) {
        this.playerTwo = playerTwo;
    }

    // getters
    public Long getGameID() {
        return gameID;
    }

    public GameplayStatus getGameplayStatus() {
        return gamePlayStatus;
    }

    public RandomGamePlayer getPlayerOne() {
        return playerOne;
    }

    public RandomGamePlayer getPlayerTwo() {
        return playerTwo;
    }
}


