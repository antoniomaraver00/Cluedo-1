package player;
import java.util.*;

import cards.Card;

public class Player {
	private String name;
	private Position positon;
	private Move move;
	private ArrayList<Card> cards;

	public Player(String name, Position position, Move move, ArrayList<Card> cards) {
		this.name = name;
		this.positon = position;
		this.move = move;
		this.cards = cards;
	}
	
	public String getName() {
		return name;
	}

	public Position getPositon() {
		return positon;
	}

	public Move getMove() {
		return move;
	}

	public Card getCard(int index) {
		Card aCard = cards.get(index);
		return aCard;
	}

	public ArrayList<Card> getCards() {
		ArrayList<Card> newCards = (ArrayList<Card>) Collections.unmodifiableList(cards);
		return newCards;
	}

	public int numberOfCards() {
		int number = cards.size();
		return number;
	}

	public boolean hasCards() {
		boolean has = cards.size() > 0;
		return has;
	}

	public int indexOfCard(Card aCard) {
		int index = cards.indexOf(aCard);
		return index;
	}

	public boolean setPositon(Position aNewPositon) {
		boolean wasSet = false;
		if (aNewPositon != null) {
			positon = aNewPositon;
			wasSet = true;
		}
		return wasSet;
	}

	public boolean setMove(Move aNewMove) {
		boolean wasSet = false;
		if (aNewMove != null) {
			move = aNewMove;
			wasSet = true;
		}
		return wasSet;
	}

	public boolean setCards(Card... newCards) {
		boolean wasSet = false;
		ArrayList<Card> verifiedCards = new ArrayList<Card>();
		for (Card aCard : newCards) {
			if (verifiedCards.contains(aCard)) {
				continue;
			}
			verifiedCards.add(aCard);
		}

		if (verifiedCards.size() != newCards.length || verifiedCards.size() != 6) {
			return wasSet;
		}

		cards.clear();
		cards.addAll(verifiedCards);
		wasSet = true;
		return wasSet;
	}
}