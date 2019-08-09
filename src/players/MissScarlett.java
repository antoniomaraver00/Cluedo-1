package players;

import java.util.ArrayList;

import cards.Card;
import player.Player;
import player.Position;

public class MissScarlett extends Player {
	ArrayList<Card> cards;
	
	public MissScarlett(ArrayList<Card> cards) {
		super(cards);
	}

	@Override
	public char getBoardName() {
		return 'S';
	}
	
	@Override
	public String toString() {
		return "Miss Scarlett";
	}
}
