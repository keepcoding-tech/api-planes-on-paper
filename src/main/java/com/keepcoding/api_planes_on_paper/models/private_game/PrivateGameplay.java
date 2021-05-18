package com.keepcoding.api_planes_on_paper.models.private_game;

import com.keepcoding.api_planes_on_paper.models.GameplayStatus;

import javax.persistence.*;

@Entity
@Table(name = "private_game_model")
public class PrivateGameplay {
	@Id
	@GeneratedValue
	private Long gameID;

	@Column(name = "access_token")
	private String accessToken;

	@Column(name = "game_state")
	private GameplayStatus gamePlayStatus;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "player_one")
	private PrivateGamePlayer playerOne;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "player_two")
	private PrivateGamePlayer playerTwo;

	// constructors
	public PrivateGameplay() {}

	public PrivateGameplay(
			String accessToken,
			GameplayStatus gamePlayStatus,
			PrivateGamePlayer playerOne,
			PrivateGamePlayer playerTwo
	) {
		this.accessToken = accessToken;
		this.gamePlayStatus = gamePlayStatus;
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
		this.gamePlayStatus = gamePlayStatus;
	}

	public void setPlayerOne(PrivateGamePlayer playerOne) {
		this.playerOne = playerOne;
	}

	public void setPlayerTwo(PrivateGamePlayer playerTwo) {
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
		return gamePlayStatus;
	}

	public PrivateGamePlayer getPlayerOne() {
		return playerOne;
	}

	public PrivateGamePlayer getPlayerTwo() {
		return playerTwo;
	}
}
