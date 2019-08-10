package rooms;

import cards.Card;
import cards.Room;
import player.Position;

public class Library implements Room {

	private final int x = 3,
	y = 11,
	width = 9,
	height = 14;

	public boolean within(Position p) {
		return (p.getX() >= x && p.getX() <= width) && (p.getY() >= y && p.getY() <= height);
	}
	
	public String toString() {
		return "Library";
	}
	@Override
	public boolean equals(Card c) {
		if (this.toString()==c.toString()) {return true;}
		return false;
	}
}
