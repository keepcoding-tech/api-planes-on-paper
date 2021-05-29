package com.keepcoding.api_planes_on_paper.controller.requests;

import com.keepcoding.api_planes_on_paper.models.PlayerStatus;

public class AttackEnemyRequest {
	private String gameID;
	private PlayerStatus identity;
	private int posX;
	private int posY;

	// constructors
	public AttackEnemyRequest() {}

	public AttackEnemyRequest(String gameID, PlayerStatus identity, int posX, int posY) {
		this.gameID = gameID;
		this.identity = identity;
		this.posX = posX;
		this.posY = posY;
	}

	// setters
	public void setGameID(String gameID) {
		this.gameID = gameID;
	}

	public void setIdentity(PlayerStatus identity) {
		this.identity = identity;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	// getters
	public String getGameID() {
		return gameID;
	}

	public PlayerStatus getIdentity() {
		return identity;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}
}
