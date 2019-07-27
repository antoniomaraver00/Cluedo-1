package player;
import java.util.*;

import cards.Card;

public class Player {
	private String name;
	private char boardName; 
	private Position positon;
	private ArrayList<Card> cards;

	public Player(String name, Position position, ArrayList<Card> cards) {
		this.name = name;
		this.positon = position;
		this.cards = cards;
		this.boardName=generateBoardName();
	}
	private char generateBoardName(){
		char correctChar='x';
		switch (name) {
		case "Miss Scarlett" :
			correctChar='S';
			break;
		case "Colonel Mustard" :
			correctChar='M';
			break;
		case "Mrs. White" :
			correctChar='W';
			break;
		case "Mr. Green" :
			correctChar='G';
			break;
		case "Mrs. Peacock" :
			correctChar='p';
			break;
		case "Professor Plum" :
			correctChar='P';
			break;
			
		}
		return correctChar;
	}
	public char getBoardChar() {return boardName;}// get the board name of player, e.g; Mr. Green = G.
	public String getName() {
		return name;
	}

	public Position getPositon() {
		return positon;
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
	
	public String toString() {
		return getName() + " : " + getPositon();
	}
}