package players;

import java.util.ArrayList;

import cards.Card;
import player.Player;
import player.Position;

public class MrsPeacock extends Player {
	ArrayList<Card> cards;
	
	public MrsPeacock(ArrayList<Card> cards) {
		super(cards);
	}
	
	@Override
	public char getBoardName() {
		return 'p';
	}
	
	@Override
	public String toString() {
		return "Mrs. Peacock";
	}
}
