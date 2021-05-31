package com.keepcoding.api_planes_on_paper.models;

import javax.persistence.*;

@Entity
@Table(name = "game_player")
public class PlayerModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long playerID;

	@Column(name = "player_nickname", updatable = false, nullable = false)
	private String playerNickname;

	@Column(name = "has_surrendered", updatable = true, nullable = false)
	private boolean hasSurrendered;

	@Column(name = "is_ready", updatable = true, nullable = false)
	private boolean isReady;

	@Column(name = "destroyed_planes", updatable = true, nullable = false)
	private int destroyedPlanes;

	@Column(name = "planes_border", length = 3000, updatable = true, nullable = true)
	private int[][] planesBorder;

	// constructors
	public PlayerModel() {}

	public PlayerModel(
			String playerNickname,
			boolean hasSurrendered,
			boolean isReady,
			int destroyedPlanes
	) {
		this.playerNickname = playerNickname;
		this.hasSurrendered = hasSurrendered;
		this.isReady = isReady;
		this.destroyedPlanes = destroyedPlanes;
	}

	// setters
	public void setPlayerNickname(String playerNickname) {
		this.playerNickname = playerNickname;
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

	public void incrementDestroyedPlanes() {destroyedPlanes++;}

	public void setPlanesBorder(int[][] planesBorder) {
		this.planesBorder = planesBorder;
	}

	public boolean setPlanesBorderValue(int x, int y) {
		final int hitPlaneHead = planesBorder[x][y];
		planesBorder[x][y] = planesBorder[x][y] == 0 ? 3 : 4;
		return hitPlaneHead == 2;
	}

	// getters
	public String getPlayerNickname() {
		return playerNickname;
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
