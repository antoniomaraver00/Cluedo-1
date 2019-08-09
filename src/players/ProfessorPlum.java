package players;

import java.util.ArrayList;

import cards.Card;
import player.Player;
import player.Position;

public class ProfessorPlum extends Player {
	ArrayList<Card> cards;
	
	public ProfessorPlum(ArrayList<Card> cards) {
		super(cards);
	}

	@Override
	public char getBoardName() {
		return 'G';
	}
	
	@Override
	public String toString() {
		return "Professor Plum";
	}
}
