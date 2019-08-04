package rooms;

import cards.Card;
import cards.Room;
import player.Position;

public class Kitchen implements Room {

	private final int x = 39,
	y = 14,
	width = 45,
	height = 16;

	public boolean within(Position p) {
		return (p.getX() >= x && p.getX() <= width) && (p.getY() >= y && p.getY() <= height);
	}
	
	public String toString() {
		return "Kitchen";
	}
	@Override
	public boolean equals(Card c) {
		if (this.toString()==c.toString()) {return true;}
		return false;
	}
	
}
