package rooms;

import cards.Card;
import cards.Room;
import player.Position;

public class Lounge implements Room {

	private final int x = 39,
	y = 1,
	width = 45,
	height = 4;

	public boolean within(Position p) {
		return (p.getX() >= x && p.getX() <= width) && (p.getY() >= y && p.getY() <= height);
	}
	
	public String toString() {
		return "Lounge";
	}
	@Override
	public boolean equals(Card c) {
		if (this.toString()==c.toString()) {return true;}
		return false;
	}
}
