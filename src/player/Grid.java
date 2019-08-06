package player;

public class Grid {
	private char[][] mainGrid = new char[25][50];// 2d array of the entire board grid

	public Grid() {
		parseGrid();
	}
	
	public void parseGrid() {

		String ss = "|#|#|#|#|#|#|#|+|#|#|#|#|#|#|#|#|+|#|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|#|#|#|#|#|_|_|_|#|_|#|#|#|#|_|_|_|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|\n"
				+ "|#|#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|_|#|#|#|#|_|_|_|_|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|_|#|*|*|#|_|_|_|_|_|_|_|_|_|+|\n"
				+ "|#|_|_|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|_|_|_|_|_|_|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|_|_|_|_|#|\n"
				+ "|+|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|\n"
				+ "|#|#|#|#|#|#|_|_|_|#|_|#|#|#|#|_|_|_|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|#|#|#|#|#|#|+|#|#|#|#|#|#|#|#|+|#|#|#|#|#|#|#|\n";

		// initialize the 2d array
		
		int count = 0;
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 50; j++) {
				mainGrid[i][j] = ss.charAt(count++);
			}
		}
	}

	// interaction methods

	public char getGridChar(int row, int col) {// get char @ x,y position (x must be * 2)
		return mainGrid[row][col];
	}

	public String display() {// display the current board
		String grid = "";

		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 49; j++) {
				grid += mainGrid[i][j];
			}
			grid += "\n";
		}

		return grid;
	}

	public void setGridChar(int oldRow, int oldCol, int newRow, int newCol, char playerIcon) {
		// todo code to check if the move is valid(desired location is unoccupied, valid
		// cell, within dice number range)
		if (isAtSpawn(oldRow, oldCol)) {
			mainGrid[oldRow][oldCol] = '#';
		} // if the players are leaving spawn, their spawn spot is no longer reachable
		else {
			mainGrid[oldRow][oldCol] = '_';
		}
		mainGrid[newRow][newCol] = playerIcon;
	}

	public void setGridChar(int newRow, int newCol, char playerIcon) {// set new pos without changing old position
		mainGrid[newRow][newCol] = playerIcon;
	}

	public boolean isAtSpawn(int row, int col) {
		int[] spawnPos = { 0, 15, 0, 33, 24, 33, 24, 15, 17, 1, 12, 47 };
		for (int i = 0; i < spawnPos.length; i++) {
			if (row == spawnPos[i] && col == spawnPos[i + 1]) {
				return true;
			}
		}
		return false;
	}

}// end
