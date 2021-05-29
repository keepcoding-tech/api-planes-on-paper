package com.keepcoding.api_planes_on_paper.controller.requests;

import com.keepcoding.api_planes_on_paper.models.PlayerStatus;

public class SetReadyRequest {
	private String gameID;
	private PlayerStatus identity;
	private int[][] planesBorder;

	// constructors
	public SetReadyRequest() {}

	public SetReadyRequest(String gameID, PlayerStatus identity, int[][] planesBorder) {
		this.gameID = gameID;
		this.identity = identity;
		this.planesBorder = planesBorder;
	}

	// setters
	public void setGameID(String gameID) {
		this.gameID = gameID;
	}

	public void setIdentity(PlayerStatus identity) {
		this.identity = identity;
	}

	public void setPlanesBorder(int[][] planesBorder) {
		this.planesBorder = planesBorder;
	}

	// getters
	public String getGameID() {
		return gameID;
	}

	public PlayerStatus getIdentity() {
		return identity;
	}

	public int[][] getPlanesBorder() {
		return planesBorder;
	}
}
