package rooms;

import cards.Card;
import cards.Room;
import player.Position;

public class Ballaroom implements Room {

	private final int x = 39,
	y = 20,
	width = 45,
	height = 23;

	public boolean within(Position p) {
		return (p.getX() >= x && p.getX() <= width) && (p.getY() >= y && p.getY() <= height);
	}
	
	public String toString() {
		return "Ballaroom";
	}
}
