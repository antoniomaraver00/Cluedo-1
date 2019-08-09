package players;

import java.util.ArrayList;

import cards.Card;
import player.Player;

public class MrGreen extends Player {
	ArrayList<Card> cards;
	
	public MrGreen(ArrayList<Card> cards) {
		super(cards);
	}

	@Override
	public char getBoardName() {
		return 'G';
	}
	
	@Override
	public String toString() {
		return "Mr. Green";
	}
}
