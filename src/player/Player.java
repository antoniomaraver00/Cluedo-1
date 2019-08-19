package player;

import java.util.*;

import cards.Card;
import cards.Room;
import cards.Suspect;
import cards.Weapon;

public class Player {
	private Position position;
	private boolean playerAlive = true;
	private Move move;
	private Room previousRoom, currentRoom;
	private ArrayList<Card> cards;
	private ArrayList<Card> excludedCards = new ArrayList<>();
	private int SUGGESTION = 1, ACCUSATION = 2;
	private ArrayList<String> notes = new ArrayList<String>();

	public Player(ArrayList<Card> cards) {
		this.cards = cards;
		position = new Position(0, 0);
		this.move = new Move();
	}
	
	public char getBoardName() {
		return 'A';
	}

	public void playerMove(int newY, int newX,Grid g) {//move a player to a position on the board
		
		move.apply(position, newY, newX, this.getBoardName(),g);
	}

	public void spawnMove(int row, int col,Grid g) {
		position.setY(row);
		position.setX(col);
		move.moveSpawnPos(row, col, this.getBoardName(), g);
	}

	public boolean isValid(int row, int col,Grid g) {
		return move.isMoveValid(position.getY() + row, position.getX() + col, g);
	}

	public ArrayList<Card> getExcludedCards() {
		return excludedCards;
	}

	public Move getMove() {
		return move;
	}

	public Position getPositon() {
		return position;
	}

	/*
	 * asks the player to suggest, or accuse cards
	 */
	public Card[][] chooseCards(Room room, Weapon[] weapons, Suspect[] suspects) {
		// remove elements that will cannot be chosen by this player
		weapons = removeWeapons(weapons);
		suspects = removeSuspects(suspects);

		// return the cards to the board so it can decide on what to do next
		return new Card[][] { weapons, suspects };
	}

	/*
	 * removes an element form an array if it cannot be chosen for
	 * suggestion/accusation
	 */
	private Weapon[] removeWeapons(Weapon[] weapons) {
		// create a temporary arraylist of weapons
		ArrayList<Weapon> w = new ArrayList<>();

		for (int i = 0; i < weapons.length; i++) {
			// if the current card isn't the one we want to remove
			if (!cards.contains(weapons[i]) && !excludedCards.contains(weapons[i])) {
				// add it to the new arraylist
				w.add(weapons[i]);
			}
		}

		// return the list as an array
		return w.toArray(new Weapon[w.size()]);
	}

	/*
	 * removes an element form an array if it cannot be chosen for
	 * suggestion/accusation
	 */
	private Suspect[] removeSuspects(Suspect[] suspects) {
		// create a temporary arraylist of suspects
		ArrayList<Suspect> s = new ArrayList<>();

		for (int i = 0; i < suspects.length; i++) {
			// if the current card isn't the one we want to remove
			if (!cards.contains(suspects[i]) && !excludedCards.contains(suspects[i])) {
				// add it to the new array
				s.add(suspects[i]);
			}
		}

		// return the list as an array
		return s.toArray(new Suspect[s.size()]);
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}
	
	public Room getPreviousRoom() {
		return previousRoom;
	}
	
	public void setCurrentRoom(Room r) {
		currentRoom = r;
	}

	public void setPreviousRoom(Room r) {
		previousRoom = r;
	}

	public boolean getStillInGame() {
		return playerAlive;
	}

	public void removeFromGame() {
		playerAlive = false;
	}

	public Card getCard(Card c) {
		int cardIndex = cards.indexOf(c);

		if (cardIndex >= 0) {
			return cards.get(cardIndex);
		}

		return null;
	}

	public void excludeCard(Card c) {
		excludedCards.add(c);
	}

	public int suggestion() {
		return SUGGESTION;
	}

	public int accusation() {
		return ACCUSATION;
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
			position = aNewPositon;
			wasSet = true;
		}
		return wasSet;
	}

	public void setCards(ArrayList<Card> newCards) {
		this.cards = newCards;
	}

	public ArrayList<Card> getHand() {
		
		return cards;
	}
	public ArrayList<String> getNotes(){
		return notes;
	}
	public void setNotes(String s) {
		notes.add(s);
	}
}