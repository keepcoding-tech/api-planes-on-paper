package com.keepcoding.api_planes_on_paper.controller.requests;

public class PlayerIsReadyRequest {
	private Long gameID;
	private String player;
	private int[][] planesBorder;

	// constructors
	public PlayerIsReadyRequest() {}

	public PlayerIsReadyRequest(Long gameID, String player, int[][] planesBorder) {
		this.gameID = gameID;
		this.player = player;
		this.planesBorder = planesBorder;
	}

	// setters
	public void setGameID(Long gameID) {
		this.gameID = gameID;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public void setPlanesBorder(int[][] planesBorder) {
		this.planesBorder = planesBorder;
	}

	// getters
	public Long getGameID() {
		return gameID;
	}

	public String getPlayer() {
		return player;
	}

	public int[][] getPlanesBorder() {
		return planesBorder;
	}
}
