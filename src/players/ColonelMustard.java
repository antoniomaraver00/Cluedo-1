package players;

import java.util.ArrayList;

import cards.Card;
import player.Player;
import player.Position;

public class ColonelMustard extends Player {
	ArrayList<Card> cards;
	
	public ColonelMustard(ArrayList<Card> cards) {
		super(cards);
	}

	@Override
	public char getBoardName() {
		return 'M';
	}
	
	@Override
	public String toString() {
		return "Colonel Mustard";
	}
}
