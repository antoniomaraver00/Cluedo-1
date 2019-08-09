package players;

import java.util.ArrayList;

import cards.Card;
import player.Player;
import player.Position;

public class MrsWhite extends Player {
	ArrayList<Card> cards;
	
	public MrsWhite(ArrayList<Card> cards) {
		super(cards);
	}

	@Override
	public char getBoardName() {
		return 'W';
	}
	
	@Override
	public String toString() {
		return "Mrs. White";
	}
}
