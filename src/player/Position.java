package player;

public class Position {

	private int x;
	private int y;

	public Position(int aX, int aY) {
		x = aX;
		y = aY;
	}

	public boolean setX(int aX) {
		boolean wasSet = false;
		x = aX;
		wasSet = true;
		return wasSet;
	}

	public boolean setY(int aY) {
		boolean wasSet = false;
		y = aY;
		wasSet = true;
		return wasSet;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void translateToBoard() {//EDIT
		
	}

	public String toString() {
		return "x" + ": " + getX() + ", " + "y" + ": " + getY();
	}
}