package cards;

import player.Position;

public interface Room extends Card {
	public boolean within(Position p);
}