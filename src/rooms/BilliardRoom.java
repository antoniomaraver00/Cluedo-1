package rooms;

import cards.Room;
import player.Position;

public class BilliardRoom implements Room {

	private final int x = 3,
	y = 20,
	width = 9,
	height = 23;

	public boolean within(Position p) {
		return (p.getX() >= x && p.getX() <= width) && (p.getY() >= y && p.getY() <= height);
	}
	
	public String toString() {
		return "Billiard Room";
	}
}
