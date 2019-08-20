package rooms;

import cards.Card;
import cards.Room;
import player.Position;

public class DiningRoom implements Room {

	private final int x = 39,
	y = 8,
	width = 45,
	height = 10;

	public boolean within(Position p) {
		return (p.getX() >= x && p.getX() <= width) && (p.getY() >= y && p.getY() <= height);
	}
	
	public String toString() {
		return "Dining Room";
	}
	
	@Override
	public boolean equals(Card c) {
		if (this.toString().equals(c.toString())) {
			return true;
		}
		return false;
	}
}
