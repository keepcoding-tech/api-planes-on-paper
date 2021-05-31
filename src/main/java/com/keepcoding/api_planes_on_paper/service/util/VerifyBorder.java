package com.keepcoding.api_planes_on_paper.service.util;

import java.util.Arrays;

public class VerifyBorder {
	private final int[][] planesBorder;
	private int[][] border = new int[10][10];

	public VerifyBorder(int[][] planesBorder) {
		this.planesBorder = planesBorder;
	}

	public boolean verifyBorder() {
		int countHeads = 0;

		for(int i = 0; i < planesBorder.length; i++) {
			for(int j = 0; j < planesBorder.length; j++) {
				if (countHeads > 3)
					return false;

				if (planesBorder[i][j] == 2) {
					if (checkLeft(i, j)) {
						countHeads++;
						continue;
					}

					if (checkUp(i, j)) {
						countHeads++;
						continue;
					}

					if (checkRight(i, j)) {
						countHeads++;
						continue;
					}

					if (checkDown(i, j)) {
						countHeads++;
					}
				}
			}
		}

		return countHeads == 3 && Arrays.deepEquals(border, planesBorder);
	}

	private boolean checkLeft(int x, int y) {
		if (x - 2 >= 0 && x + 2 <= 9 && y >= 0 && y + 3 <= 9) {
			for (int i = x - 2; i <= x + 2; i++) {
				if (planesBorder[i][y + 1] != 1) {
					return false;
				}
			}

			if (planesBorder[x][y + 2] != 1) {
				return false;
			}

			for (int i = x - 1; i <= x + 1; i++) {
				if (planesBorder[i][y + 3] != 1){
					return false;
				}
			}

			border[x][y] = 2;
			border[x][y + 2] = 1;

			for (int i = x - 2; i <= x + 2; i++) {
				border[i][y + 1] = 1;
			}

			for (int i = x - 1; i <= x + 1; i++) {
				border[i][y + 3] = 1;
			}

			return true;
		}

		return false;
	}

	private boolean checkUp(int x, int y) {
		if (x >= 0 && x + 2 <= 9 && y - 2 >= 0 && y + 2 <= 9) {
			for (int j = y - 2; j <= y + 2; j++) {
				if (planesBorder[x + 1][j] != 1) {
					return false;
				}
			}

			if (planesBorder[x + 2][y] != 1) {
				return false;
			}

			for (int j = y - 1; j <= y + 1; j++) {
				if (planesBorder[x + 3][j] != 1) {
					return false;
				}
			}

			border[x][y] = 2;
			border[x + 2][y] = 1;

			for (int j = y - 2; j <= y + 2; j++) {
				border[x + 1][j] = 1;
			}

			for (int j = y - 1; j <= y + 1; j++) {
				border[x + 3][j] = 1;
			}

			return true;
		}

		return false;
	}

	private boolean checkRight(int x, int y) {
		if (x - 2 >= 0 && x + 2 <= 9 && y - 3 >= 0 && y <= 9) {
			for (int i = x - 2; i <= x + 2; i++) {
				if (planesBorder[i][y - 1] != 1) {
					return false;
				}
			}

			if (planesBorder[x][y - 2] != 1) {
				return false;
			}

			for (int i = x - 1; i <= x + 1; i++) {
				if (planesBorder[i][y - 3] != 1) {
					return false;
				}
			}

			border[x][y] = 2;
			border[x][y - 2] = 1;

			for (int i = x - 2; i <= x + 2; i++) {
				border[i][y - 1] = 1;
			}

			for (int i = x - 1; i <= x + 1; i++) {
				border[i][y - 3] = 1;
			}

			return true;
		}

		return false;
	}

	private boolean checkDown(int x, int y) {
		if (x - 3 >= 0 && x <= 9 && y - 2 >= 0 && y + 2 <= 9) {
			for (int j = y - 2; j <= y + 2; j++) {
				if (planesBorder[x - 1][j] != 1) {
					return false;
				}
			}

			if (planesBorder[x - 2][y] != 1) {
				return false;
			}

			for (int j = y - 1; j <= y + 1; j++) {
				if (planesBorder[x - 3][j] != 1) {
					return false;
				}
			}

			border[x][y] = 2;
			border[x - 2][y] = 1;

			for (int j = y - 2; j <= y + 2; j++) {
				border[x - 1][j] = 1;
			}

			for (int j = y - 1; j <= y + 1; j++) {
				border[x - 3][j] = 1;
			}

			return true;
		}

		return false;
	}
}
