package com.keepcoding.api_planes_on_paper.controller.requests.private_game_requests;

public class JoinPrivateGameRequest {
	private String playerTwoNickname;
	private String accessToken;

	// constructors
	public JoinPrivateGameRequest() {}

	public JoinPrivateGameRequest(String playerTwoNickname, String accessToken) {
		this.playerTwoNickname = playerTwoNickname;
		this.accessToken = accessToken;
	}

	// setters
	public void setPlayerTwoNickname(String playerTwoNickname) {
		this.playerTwoNickname = playerTwoNickname;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	// getters
	public String getPlayerTwoNickname() {
		return playerTwoNickname;
	}

	public String getAccessToken() {
		return accessToken;
	}
}
