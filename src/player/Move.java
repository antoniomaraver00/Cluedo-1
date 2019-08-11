package player;

public class Move {
	

	public Move() {

	}

	public void apply(Position p, int newY, int newX, char boardChar,Grid g) {
		g.setGridChar(p.getY(), p.getX(), newY, newX, boardChar);
		p.setY(newY);
		p.setX(newX);
		g.display();
	}

	public void moveSpawnPos(int row, int col, char boardChar,Grid g) {
		g.setGridChar(row, col, boardChar);
	}

	public boolean isMoveValid(int row, int col,Grid g) {// if the desired coord is occupied by a underscore, its okay to move
													// there

		if (row < 0 || row > 24 || col < 3 || col > 48) {
			return false;
		}
		if (g.getGridChar(row, col) == '_') {
			return true;
		}
		return false;
	}

	
}
