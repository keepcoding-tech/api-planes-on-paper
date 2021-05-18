package com.keepcoding.api_planes_on_paper.models.private_game;

import javax.persistence.*;

@Entity
@Table(name = "private_game_player")
public class PrivateGamePlayer {
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

	@Column(name = "destroyed_planes")
	private int destroyedPlanes;

	@Column(name = "planes_border", length = 3000)
	private int[][] planesBorder;

	// constructors
	public PrivateGamePlayer() {}

	public PrivateGamePlayer(
			String playerNickname,
			boolean isConnected,
			boolean hasSurrendered,
			boolean isReady,
			int destroyedPlanes
	) {
		this.playerNickname = playerNickname;
		this.isConnected = isConnected;
		this.hasSurrendered = hasSurrendered;
		this.isReady = isReady;
		this.destroyedPlanes = destroyedPlanes;
	}

	public PrivateGamePlayer(
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

	public void setDestroyedPlanes(int destroyedPlanes) {
		this.destroyedPlanes = destroyedPlanes;
	}

	public void setPlanesBorder(int[][] planesBorder) {
		this.planesBorder = planesBorder;
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

	public int getDestroyedPlanes() {
		return destroyedPlanes;
	}

	public int[][] getPlanesBorder() {
		return planesBorder;
	}
}
