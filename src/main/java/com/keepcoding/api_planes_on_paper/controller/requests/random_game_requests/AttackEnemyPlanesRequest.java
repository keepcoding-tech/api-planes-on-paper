package com.keepcoding.api_planes_on_paper.controller.requests.random_game_requests;

public class AttackEnemyPlanesRequest {
	private Long gameID;
	private String player;
	private int positionX;
	private int positionY;

	// constructors
	public AttackEnemyPlanesRequest() {}

	public AttackEnemyPlanesRequest(Long gameID, String player, int positionX, int positionY) {
		this.gameID = gameID;
		this.player = player;
		this.positionX = positionX;
		this.positionY = positionY;
	}

	// setters
	public void setGameID(Long gameID) {
		this.gameID = gameID;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	// getters
	public Long getGameID() {
		return gameID;
	}

	public String getPlayer() {
		return player;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}
}
