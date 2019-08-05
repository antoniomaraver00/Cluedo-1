package player;

import java.lang.reflect.Array;
import java.util.*;

import cards.Card;
import cards.Room;
import cards.Suspect;
import cards.Weapon;
import game.Board;

public class Player {
	private String name;
	private char boardName;
	private Position position;
	private boolean playerAlive = true;
	private Move move;
	private Room previousRoom;
	private ArrayList<Card> cards;
	private ArrayList<Card> excludedCards = new ArrayList<>();
	private Scanner scan = new Scanner(System.in);
	private int SUGGESTION = 1, ACCUSATION = 2;

	public Player(String name, Position position, ArrayList<Card> cards) {
		this.name = name;
		this.position = position;
		this.cards = cards;
		this.boardName = generateBoardName();
		this.move = new Move();
	}

	private char generateBoardName() {
		char correctChar = 'x';
		switch (name) {
		case "Miss Scarlett":
			correctChar = 'S';
			break;
		case "Colonel Mustard":
			correctChar = 'M';
			break;
		case "Mrs. White":
			correctChar = 'W';
			break;
		case "Mr. Green":
			correctChar = 'G';
			break;
		case "Mrs. Peacock":
			correctChar = 'p';
			break;
		case "Professor Plum":
			correctChar = 'P';
			break;

		}
		return correctChar;
	}

	public void playerMove(int newY, int newX) {
		// need to check if move is valid (todo)

		move.apply(position, newY, newX, boardName);

	}

	public void spawnMove(int row, int col) {
		position.setY(row);
		position.setX(col);
		move.moveSpawnPos(row, col, boardName);
	}

	public boolean isValid(int row, int col) {
		return move.isMoveValid(position.getY() + row, position.getX() + col);
	}
	public ArrayList<Card> getExcludedCards(){ 
		return excludedCards;
	} 
	public char getBoardChar() {
		return boardName;
	}// get the board name of player, e.g; Mr. Green = G.

	public String getName() {
		return name;
	}

	public Move getMove() {
		return move;
	}

	public Position getPositon() {
		return position;
	}

	/*
	 * asks the player if they want to make a suggestion, or an accusation
	 */

	public int acusationOrSuggestion() {
		// assume the player's choice is invalid
		int choice = -1;

		// keep looping until the player makes a valid choice
		while (choice != SUGGESTION && choice != ACCUSATION) {
			System.out.println("Make a chioce:");
			System.out.println("1- Suggestion\n" + "2- Accusation");

			try {
				choice = scan.nextInt();

				if (choice != SUGGESTION && choice != ACCUSATION) {
					throw new IllegalArgumentException();
				}

			} catch (IllegalArgumentException e) {
				System.out.println("Please enter a valid number");
				scan.reset();
			}
		}

		scan.reset();
		// return the choice so the board can decide what to do next
		return choice;
	}

	/*
	 * asks the player to suggest, or accuse cards
	 */
	public Card[] chooseCards(Room room, Weapon[] weapons, Suspect[] suspects) {
		// remove elements that will cannot be chosen by this player
		weapons = removeWeapons(weapons);
		suspects = removeSuspects(suspects);
		
		// first ask the player to choose a weapon
		System.out.println("Choose a weapon:");

		for (int i = 0; i < weapons.length; i++) {
			// check if the weapon is not in the player's deck, or hasn't been revealed to
			// them by another player in a previous turn
			System.out.println(i + 1 + "- " + weapons[i].toString());
		}

		// get the weapon of choice
		Weapon murderWeapon = weapons[scan.nextInt() - 1];
		scan.reset();

		for (int i = 0; i < suspects.length; i++) {
			// check if the suspect is not in the player's deck, or hasn't been revealed to
			// them by another player in a previous turn
				System.out.println(i + 1 + "- " + suspects[i].toString());
		}

		// get the suspect of choice
		Suspect murderSuspect = suspects[scan.nextInt() - 1];

		scan.reset();
		// return the cards to the board so it can decide on what to do next
		return new Card[] { room, murderWeapon, murderSuspect };
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
	public Room getPreviousRoom() {
		return previousRoom;
	}
	public void setPreviousRoom(Room r) {
		previousRoom=r;
	}
	public boolean getStillInGame() {
		return playerAlive;
	}
	public void removeFromGame() {
		playerAlive=false;
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

	public ArrayList<Card> getCards() {
		//ArrayList<Card> newCards = (ArrayList<Card>) Collections.unmodifiableList(cards);
		return cards;
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
		return getName();
	}
}