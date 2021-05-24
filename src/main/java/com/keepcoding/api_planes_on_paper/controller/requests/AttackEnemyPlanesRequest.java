package com.keepcoding.api_planes_on_paper.controller.requests;

public class AttackEnemyPlanesRequest {
	private Long gameID;
	private String player;
	private int posX;
	private int posY;

	// constructors
	public AttackEnemyPlanesRequest() {}

	public AttackEnemyPlanesRequest(Long gameID, String player, int posX, int posY) {
		this.gameID = gameID;
		this.player = player;
		this.posX = posX;
		this.posY = posY;
	}

	// setters
	public void setGameID(Long gameID) {
		this.gameID = gameID;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	// getters
	public Long getGameID() {
		return gameID;
	}

	public String getPlayer() {
		return player;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}
}
