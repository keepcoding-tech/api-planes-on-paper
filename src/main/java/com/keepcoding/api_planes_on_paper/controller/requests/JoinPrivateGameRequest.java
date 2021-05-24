package com.keepcoding.api_planes_on_paper.controller.requests;

public class JoinPrivateGameRequest {
	private String playerNickname;
	private String accessToken;

	// constructors
	public JoinPrivateGameRequest() {}

	public JoinPrivateGameRequest(String playerNickname, String accessToken) {
		this.playerNickname = playerNickname;
		this.accessToken = accessToken;
	}

	// setters
	public void setPlayerNickname(String playerNickname) {
		this.playerNickname = playerNickname;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	// getters
	public String getPlayerNickname() {
		return playerNickname;
	}

	public String getAccessToken() {
		return accessToken;
	}
}
