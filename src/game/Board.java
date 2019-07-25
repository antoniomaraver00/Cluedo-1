package game;

import java.util.*;

import cards.Card;
import cards.Room;
import cards.Weapon;
import player.Player;
import player.Position;

public class Board {
	private ArrayList<Player> players = new ArrayList<>(); // players in the current game
	private Room[] rooms;
	private Weapon[] weapons;
	private ArrayList<Card> hiddenCards = new ArrayList<>(); // cards to be guessed
	private ArrayList<Card> cards = new ArrayList<>();
	private ArrayList<ArrayList<Card>> hands = new ArrayList<>(); // hands of shuffled cards distributed randomly
																	// between players

	private final String[] playerNames = { "Miss Scarlett", "Colonel Mustard", "Mrs. White", "Mr. Green",
			"Mrs. Peacock", "Professor Plum" };

	private final String[] roomNames = { "Study", "Hall", "Lounge", "Dining Room", "Kitchen", "Ballroom",
			"Conservatory", "Billiard Room", "Library" };

	private final String[] weaponNames = { "Dagger", "Lead Pipe", "Spanner", "Candlestick", "Revolver", "Rope" };

	public Board() {
		// initialize rooms, then add them to the cards list
		rooms = new Room[9];

		for (int i = 0; i < rooms.length; i++) {
			rooms[i] = new Room(roomNames[i]);
			addCard(rooms[i]);
		}

		// initialize weapons, then add them to the cards list
		weapons = new Weapon[6];

		for (int i = 0; i < weapons.length; i++) {
			weapons[i] = new Weapon(weaponNames[i]);
			addCard(weapons[i]);
		}
	}

	public void setup() {
		Scanner scn = new Scanner(System.in);
		String s = ""; // available characters to be chosen

		for (int i = 0; i < playerNames.length; i++) {
			s += i + 1 + "- " + playerNames[i];
			s += "\n";
		}

		while (true) {
			try {
				// get the number of players
				System.out.println("How many players are going to play?");
				int numOfPlayers = scn.nextInt();
				scn.reset();

				if (numOfPlayers > 6 || numOfPlayers < 3) {
					throw new IllegalArgumentException("");
				}

				// distribute cards between players
				distributeCards(numOfPlayers);

				String[] unavailablePlayers = new String[numOfPlayers];

				// initialize player objects
				for (int i = 0; i < numOfPlayers; i++) {
					// ask player to choose a character
					System.out.println("Choose a character: ");
					String[] p = availablePlayers(unavailablePlayers, playerNames.length - i);

					// print list of available characters
					System.out.println(Arrays.toString(p));
					int index = scn.nextInt();

					// add the chosen character to the unavailablePlayers array, so it can be
					// excluded in the next iteration
					String playerName = p[index - 1];
					unavailablePlayers[i] = playerName;

					// add a new player object to the list of players
					players.add(createPlayer(playerName));
				}

				return;
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Please type a valid number");
			} catch (IllegalArgumentException e) {
				System.out.println("Please type a valid number");
			}
		}
	}

	private String[] availablePlayers(String[] unavailablePlayers, int size) {
		String[] availablePlayers = new String[size];
		int index = 0;

		for (int i = 0; i < playerNames.length; i++) {
			if (!Arrays.asList(unavailablePlayers).contains(playerNames[i])) {
				availablePlayers[index] = playerNames[i];
				index++;
			}
		}

		return availablePlayers;
	}

	private void distributeCards(int numOfPlayers) {
		// shuffle cards
		Collections.shuffle(cards);
		Random rand = new Random();

		// hide 3 random cards
		for (int i = 0; i < 3; i++) {
			int index = rand.nextInt(cards.size());
			Card c = cards.get(index);
			hiddenCards.add(c);
			removeCard(c);
		}

		// get number of cards per player
		int cardsPerPlayer = (cards.size() - 1) / numOfPlayers;

		// distribute hands
		for (int i = 0; i < numOfPlayers; i++) {
			ArrayList<Card> currHand = new ArrayList<>();
			for (int j = 0; j < cardsPerPlayer; j++) {
				Card c = cards.get(j);
				currHand.add(c);
				removeCard(c);

				// make sure there are remaining cards to be distributed
				if (cards.size() == 0) {
					break;
				}
			}
			hands.add(currHand);
		}
	}

	private Player createPlayer(String name) {
		return new Player(name, getRandomPosition(), getRandomHand());
	}

	private ArrayList<Card> getRandomHand() {
		Random rand = new Random();
		int index = rand.nextInt(hands.size());
		ArrayList<Card> h = hands.get(index);
		hands.remove(index);

		return h;
	}

	private Position getRandomPosition() {
		Random rand = new Random();
		int x = rand.nextInt(24);
		int y = rand.nextInt(25);

		return new Position(x, y);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	private boolean addCard(Card aCard) {
		boolean wasAdded = false;
		wasAdded = cards.add(aCard);
		return wasAdded;
	}

	private boolean removeCard(Card aCard) {
		boolean wasRemoved = false;
		wasRemoved = cards.remove(aCard);
		return wasRemoved;
	}

	public Room getRoom(int index) {
		return rooms[index];
	}

	public Room[] getRooms() {
		return rooms;
	}

	public Card getCard(int index) {
		Card aCard = cards.get(index);
		return aCard;
	}

	public ArrayList<Card> getCards() {
		ArrayList<Card> newCards = (ArrayList<Card>) Collections.unmodifiableList(cards);
		return newCards;
	}
}