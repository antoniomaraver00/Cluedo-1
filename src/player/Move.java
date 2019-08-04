package player;



public class Move {
	private static Grid boardGrid = new Grid();

	public Move() {
		
	}

	

	public void apply(Position p,int newY, int newX, char boardChar) {
		boardGrid.setGridChar(p.getY(), p.getX(),newY,newX, boardChar);
		p.setY(newY);
		p.setX(newX);
		boardGrid.display();
	}
	public void moveSpawnPos(int row,int col,char boardChar)  {
		boardGrid.setGridChar(row, col, boardChar);
	}
	public boolean isMoveValid(int row, int col) {//if the desired coord is occupied by a underscore, its okay to move there
		if (row<0 || row>24 || col<3 || col>48) {return false;}
		if (boardGrid.getGridChar(row, col)=='_') {return true;}
		return false;
	}
	public Grid getGrid() {
		return boardGrid;
	}
	
	/**
	public String toString() {
		return super.toString() + "[" + "]" + System.getProperties().getProperty("line.separator") + "  " + "dice" + "="
				+ (getDice() != null ? !getDice().equals(this) ? getDice().toString().replaceAll("  ", "    ") : "this"
						: "null");
	}
	*/
}