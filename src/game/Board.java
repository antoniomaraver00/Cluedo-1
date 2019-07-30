package game;

import java.util.*;

import cards.Card;
import cards.Room;
import cards.Suspect;
import cards.Weapon;
import player.Player;
import player.Position;

public class Board {
	private Grid boardGrid;// active grid of the board
	private ArrayList<Player> players = new ArrayList<>(); // players in the current game
	private Dice dice;
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
		cards = new ArrayList<Card>();
		createBoardCells();
		dice = new Dice();
		for (int i = 0; i < playerNames.length; i++) {
			cards.add(new Suspect(playerNames[i]));
		}

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
		while (true) {
			try {
				// get the number of players
				System.out.println("How many players are going to play?");
				int numOfPlayers = scn.nextInt();
				scn.reset();

				if (numOfPlayers > 6 || numOfPlayers < 3) {
					throw new IllegalArgumentException("invalid number of characters");
				}

				// distribute cards between players
				distributeCards(numOfPlayers);

				String[] unavailablePlayers = new String[numOfPlayers];

				// initialize player objects
				for (int i = 0; i < numOfPlayers; i++) {
					// ask player to choose a character
					System.out.println("Player " + (i + 1) + " Choose a character: ");
					String[] p = availablePlayers(unavailablePlayers, playerNames.length - i);

					// print list of available characters
					printArray(p);
					int index = scn.nextInt();

					// add the chosen character to the unavailablePlayers array, so it can be
					// excluded in the next iteration
					String playerName = p[index - 1];
					unavailablePlayers[i] = playerName;

					// add a new player object to the list of players
					Player player = createPlayer(playerName);
					players.add(player);
				}

				break;
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Please type a valid number");
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}

//////////////////////tyson edit from here////////////////
		createBoardCells();// generate board
		spawnPlayers();// allocate player spawn positions
		for (Player p : players) {
			boardGrid.setGridChar(p.getPositon().getY(), p.getPositon().getX(), p.getBoardChar(), this);
		} // set position
		boardGrid.display();
		activeRound();
	}
	private void activeRound() {
		int highestRoll =findWhoGoesFirst();	
		while (true) {
			
			for (Player currentPlayerTurn : players) {
				
			}
		}
	}
	public int findWhoGoesFirst(){
		ArrayList<Integer> playerRolls = new ArrayList<>();
		Dice die = new Dice();
		formatPrint("each player role the dice to decide who goes first");
		for (int i = 0; i<players.size(); i++) {
			Scanner sc = new Scanner(System.in);
			
			formatPrint(players.get(i).getName()+"'s next to roll");
			formatPrint("press r to roll die");
			
			String r;
			r= sc.next();
			if (r.equalsIgnoreCase("r")) {
				int diceRoll=die.roll();
				playerRolls.add(diceRoll);
			}
			else {
				formatPrint("remember, press r to roll die. try again");
				i--;
			}
			
			
		}
		return Collections.max(playerRolls);
	}
	public void formatPrint(String s) {
		System.out.println("-------------------------------------------------");
		System.out.println(s);
		
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////// board setup ///////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	private void spawnPlayers() {
		int[] spawnPos = { 0, 15, 0, 33, 24, 33, 24, 15, 17, 1, 12, 47 };// possible x,y spawn positions in subsequent
																			// order (row,col,row..)
		int count = 0;
		for (Player p : players) {
			p.getPositon().setY(spawnPos[count++]);
			p.getPositon().setX(spawnPos[count++]);
		}
	}

	private void printArray(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.println((i + 1) + "- " + arr[i]);
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

	private void hideCards() {
		Random rand = new Random();

		int index = rand.nextInt(cards.size());
		Card c = cards.get(index);

		// hide one random suspect
		while (!(c instanceof Suspect)) {
			index = rand.nextInt(cards.size());
			c = cards.get(index);
		}

		hiddenCards.add(c);
		removeCard(c);

		// hide one random room
		while (!(c instanceof Room)) {
			index = rand.nextInt(cards.size());
			c = cards.get(index);
		}

		hiddenCards.add(c);
		removeCard(c);

		// hide one random weapon
		while (!(c instanceof Weapon)) {
			index = rand.nextInt(cards.size());
			c = cards.get(index);
		}

		hiddenCards.add(c);
		removeCard(c);
	}

	private void distributeCards(int numOfPlayers) {
		// shuffle cards
		Collections.shuffle(cards);

		// hide three random cards
		hideCards();

		// get number of cards per player
		int cardsPerPlayer = (cards.size() - 1) / numOfPlayers;

		// distribute hands
		for (int i = 0; i < numOfPlayers; i++) {
			ArrayList<Card> currHand = new ArrayList<>();
			for (int j = 0; j < cardsPerPlayer && cards.size() > 0; j++) {
				if (j < cards.size()) {
					Card card = cards.get(j);
					currHand.add(card);
					removeCard(card);
				}
			}
			
			hands.add(currHand);
		}
	}

	private Player createPlayer(String name) {
		return new Player(name, new Position(0, 0), getRandomHand());
	}

	private ArrayList<Card> getRandomHand() {
		Random rand = new Random();
		int index = rand.nextInt(hands.size());
		ArrayList<Card> h = hands.get(index);
		hands.remove(index);

		return h;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////// getters and setters ///////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
		return cards.get(index);
	}

	public ArrayList<Card> getCards() {
		ArrayList<Card> newCards = (ArrayList<Card>) Collections.unmodifiableList(cards);
		return newCards;
	}

	public boolean hasCards() {
		boolean has = cards.size() > 0;
		return has;
	}

	public void createBoardCells() {
		boardGrid = new Grid();
		// boardGrid.setGridChar(1,3,3,3,'P', this);
	}

	public void applyToGrid() {

	}

	public String toString() {
		return super.toString() + "[" + "]";
	}
}
