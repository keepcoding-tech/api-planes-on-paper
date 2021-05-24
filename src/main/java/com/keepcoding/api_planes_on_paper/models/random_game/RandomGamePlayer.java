package com.keepcoding.api_planes_on_paper.models.random_game;

import javax.persistence.*;

@Entity
@Table(name = "random_game_player")
public class RandomGamePlayer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long playerID;

	@Column(name = "player_nickname")
	private String playerNickname;

	@Column(name = "is_connected")
	private boolean isConnected;

	@Column(name = "has_surrendered")
	private boolean hasSurrendered;

	@Column(name = "is_ready")
	private boolean isReady;

	@Column(name = "player_turn")
	private boolean playerTurn;

	@Column(name = "destroyed_planes")
	private int destroyedPlanes;

	@Column(name = "planes_border", length = 3000)
	private int[][] planesBorder;

	// constructors
	public RandomGamePlayer() {}

	public RandomGamePlayer(
			String playerNickname,
			boolean isConnected,
			boolean hasSurrendered,
			boolean isReady,
			boolean playerTurn,
			int destroyedPlanes
	) {
		this.playerNickname = playerNickname;
		this.isConnected = isConnected;
		this.hasSurrendered = hasSurrendered;
		this.isReady = isReady;
		this.playerTurn = playerTurn;
		this.destroyedPlanes = destroyedPlanes;
	}


	public RandomGamePlayer(
			String playerNickname,
			boolean isConnected,
			boolean hasSurrendered,
			boolean isReady,
			int destroyedPlanes,
			int[][] planesBorder
	) {
		this.playerNickname = playerNickname;
		this.isConnected = isConnected;
		this.hasSurrendered = hasSurrendered;
		this.isReady = isReady;
		this.destroyedPlanes = destroyedPlanes;
		this.planesBorder = planesBorder;
	}

	// setters
	public void setPlayerNickname(String playerNickname) {
		this.playerNickname = playerNickname;
	}

	public void setIsConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	public void setHasSurrendered(boolean hasSurrendered) {
		this.hasSurrendered = hasSurrendered;
	}

	public void setIsReady(boolean isReady) {
		this.isReady = isReady;
	}

	public void setPlayerTurn(boolean playerTurn) {
		this.playerTurn = playerTurn;
	}

	public void setDestroyedPlanes(int destroyedPlanes) {
		this.destroyedPlanes = destroyedPlanes;
	}

	public void setPlanesBorder(int[][] planesBorder) {
		this.planesBorder = planesBorder;
	}

	public void setPlanesBorderValue(int x, int y) {
		planesBorder[x][y] = planesBorder[x][y] == 0 ? 3 : 4;
	}

	// getters
	public String getPlayerNickname() {
		return playerNickname;
	}

	public boolean getIsConnected() {
		return isConnected;
	}

	public boolean getHasSurrendered() {
		return hasSurrendered;
	}

	public boolean getIsReady() {
		return isReady;
	}

	public boolean getPlayerTurn() {
		return playerTurn;
	}

	public int getDestroyedPlanes() {
		return destroyedPlanes;
	}

	public int[][] getPlanesBorder() {
		return planesBorder;
	}
}
