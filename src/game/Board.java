package game;

import java.util.*;

import cards.*;
import player.*;
import rooms.*;

public class Board {

	private boolean gameOver = false;
	private ArrayList<Player> players = new ArrayList<>(); // players in the current game
	private Player currentPlayer;
	private Dice dice;
	private Suspect[] suspects;
	private Room[] rooms = { new Study(), new Hall(), new Lounge(), new DiningRoom(), new Kitchen(), new Ballaroom(),
			new Conservatory(), new BilliardRoom(), new Library() };
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
		dice = new Dice();

		suspects = new Suspect[playerNames.length];

		for (int i = 0; i < playerNames.length; i++) {
			suspects[i] = new Suspect(playerNames[i]);
			cards.add(suspects[i]);
		}

		for (Room r : rooms) {
			cards.add(r);
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
			}
		}

		spawnPlayers();// allocate player spawn positions
		showPlayerHands();// show players their hands to write down
		players.get(0).getMove().getGrid().display();
		activeRound();// Begin the round
	}
//////////////////////game starts////////////////

	private void activeRound() {
		int highestRoller = findWhoGoesFirst(); // players roll the dice to see who goes first
		currentPlayer = players.get(highestRoller);// assign the currentPlayer field as the player @ index of highest
													// roller (player to start)

		while (!gameOver) {

			for (int i = 0; i < players.size(); i++) {
				if (highestRoller != 0) {// offset the index to have the player who rolled the highest initial roll go
											// first
					i = highestRoller;
					highestRoller = 0;
				}
				currentPlayer = players.get(i);
				if (currentPlayer.getStillInGame()) {
					int roll = playerRollDice();
					activeMove(roll);
				}

			}
		}
	}
//////////////////////////////////////////////////	

	public void activeMove(int roll) {
		int rollCount = roll;
		while (rollCount > 0) {
			formatPrint("moving keys: WASD .   walk south: S   walk north: W   walk east: D   walk west: A");
			players.get(0).getMove().getGrid().display();// display grid

			Scanner sc = new Scanner(System.in);
			formatPrint("roll of " + roll + ". " + rollCount + " moves remaining");
			formatPrint("enter move key");

			String r;
			r = sc.next();
			//keystrokes map to x,y coords
			//if player already in room, moves dont count towards diceroll, nor can they make suggestions or accusations untill re-entering room
			if (r.equalsIgnoreCase("w") && currentPlayer.isValid(-1, 0)) {
				currentPlayer.playerMove(currentPlayer.getPositon().getY() - 1, currentPlayer.getPositon().getX() + 0);
				if (currentPlayer.getPreviousRoom()==getRoom(currentPlayer)&&currentPlayer.getPreviousRoom()!=null) {continue;}
				else{rollCount--;}
			} else if (r.equalsIgnoreCase("d") && currentPlayer.isValid(0, 2)) {
				currentPlayer.playerMove(currentPlayer.getPositon().getY() + 0, currentPlayer.getPositon().getX() + 2);
				if (currentPlayer.getPreviousRoom()==getRoom(currentPlayer)&&currentPlayer.getPreviousRoom()!=null) {continue;}
				else{rollCount--;}
			} else if (r.equalsIgnoreCase("s") && currentPlayer.isValid(1, 0)) {
				currentPlayer.playerMove(currentPlayer.getPositon().getY() + 1, currentPlayer.getPositon().getX() + 0);
				if (currentPlayer.getPreviousRoom()==getRoom(currentPlayer)&&currentPlayer.getPreviousRoom()!=null) {continue;}
				else{rollCount--;}
			} else if (r.equalsIgnoreCase("a") && currentPlayer.isValid(0, -2)) {
				currentPlayer.playerMove(currentPlayer.getPositon().getY() + 0, currentPlayer.getPositon().getX() + -2);
				if (currentPlayer.getPreviousRoom()==getRoom(currentPlayer)&&currentPlayer.getPreviousRoom()!=null) {continue;}
				else{rollCount--;}

			} // check if the player is in a room after their turn is over
			else {
				formatPrint("incorrect input or move location, try again");
			}

			Room currentRoom = getRoom(currentPlayer);
			
			if (currentRoom != null) {
				rollCount=0;//player cannot move more steps once in room
				// notify the player which room they are in
				currentPlayer.setPreviousRoom(currentRoom);
				formatPrint("You are inside the " + currentRoom.toString());

				// check if player wants to make a suggestion, or an accusation
				int playerChoice = currentPlayer.acusationOrSuggestion();

				// get the cards the player has chosen
				Card[] chosenCards = currentPlayer.chooseCards(currentRoom, weapons, suspects);

				// if the player made an accusation
				if (playerChoice == currentPlayer.accusation()) {
					int cardFound = 0;
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < chosenCards.length; j++) {
							if (hiddenCards.get(i).equals(chosenCards[j])) {
								cardFound++;
							}
						}

					}
					if (cardFound == 3) {
						formatPrint("+++++++++++++++++++ GAME OVER " + currentPlayer.getName()
								+ " wins!! +++++++++++++++++++");
						gameOver = true;
					} else {
						// notify the player that they have been eliminated
						formatPrint(currentPlayer.getName() + " has been eliminated");
						currentPlayer.removeFromGame();
						//change eliminated players board piece to a moveable area
						currentPlayer.getMove().getGrid().setGridChar(currentPlayer.getPositon().getY(), currentPlayer.getPositon().getX(),'_');
						
						if(allPlayersEliminated()) {
							gameOver = true;
						}
					}
					// if the player made a suggestion, check if other players have one of the cards
					// the player has chosen
				} else if (playerChoice == currentPlayer.suggestion()) {
					for (int i = 0; i < chosenCards.length; i++) {
						for (Player p : players) {
							if (p != currentPlayer && p.getStillInGame()) {
								// check if the current other player has the current card
								Card revealedCard = p.getCard(chosenCards[i]);

								if (revealedCard != null && !currentPlayer.getExcludedCards().contains(revealedCard)) {
									// notify the player of who has the card they are looking for
									formatPrint(p.getName() + " has a " + chosenCards[i] + " card");

									// if they do, add it to currentPlayer's exclude list, so it won't appear on the
									// player's next turn
									currentPlayer.excludeCard(revealedCard);
									return;
								}
							}
						}
					}

					// if none of the players have any of the suggested cards, then notify the
					// current player
					System.out.println("none of the players have any of the cards you suggested");
				}
			} // proccess player if they enter room
		}
	}

	/*
	 * check if there is at most one active player
	 */
	private boolean allPlayersEliminated() {
		// the number of active players
		int playerCount = 0;

		for (Player p : players) {
			if (p.getStillInGame()) {
				playerCount++;
			}
		}

		if (playerCount > 1) {
			return false;
		}

		return true;
	}

	public int playerRollDice() {
		Dice die = new Dice();
		int diceRoll = 0;

		while (true) {
			Scanner sc = new Scanner(System.in);

			formatPrint(currentPlayer.getName() + "'s next to roll");
			formatPrint("press r to roll die");

			String r;
			r = sc.next();
			if (r.equalsIgnoreCase("r")) {
				diceRoll = die.roll();
				break;

			} else {
				formatPrint("remember, press r to roll die. try again");
			}

		}
		return diceRoll;
	}

	public int findWhoGoesFirst() {
		ArrayList<Integer> playerRolls = new ArrayList<>();
		Dice die = new Dice();
		formatPrint("each player role the dice to decide who goes first");
		for (int i = 0; i < players.size(); i++) {
			Scanner sc = new Scanner(System.in);

			formatPrint(players.get(i).getName() + "'s next to roll");
			formatPrint("press r to roll die");

			String r;
			r = sc.next();
			if (r.equalsIgnoreCase("r")) {
				int diceRoll = die.roll();
				System.out.println("rolled a " + diceRoll);
				playerRolls.add(diceRoll);
			} else {
				formatPrint("remember, press r to roll die. try again");
				i--;
			}

		}
		for (int i = 0; i < playerRolls.size(); i++) {
			if (playerRolls.get(i) == Collections.max(playerRolls)) {
				formatPrint(players.get(i).getName() + " rolled the highest with a roll of: " + playerRolls.get(i));
				return i;
			}
		}
		return 0;
	}

	// RoomDimensions[] rDimensions = {new RoomDimensions(new Position(3, 1), 9, 4),
	// new RoomDimensions(new Position(21, 1), 27, 4),
	// new RoomDimensions(new Position(39, 1), 45, 4), new RoomDimensions(new
	// Position(39, 8), 45, 10),
	// new RoomDimensions(new Position(39, 8), 45, 10), new RoomDimensions(new
	// Position(3, 1), 9, 4)};

	public Room getRoom(Player p) {

		// players x,y boundaries determines room player is in, if player is in a room
		// at all
		for (Room r : rooms) {
			if (r.within(p.getPositon())) {
				return r;
			}
		}

		return null;
	}

	public void formatPrint(String s) {
		System.out.println("-------------------------------------------------");
		System.out.println(s);

	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////// board setup ///////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void showPlayerHands() {
		for (int i = 0; i < players.size(); i++) {
			formatPrint("showing " + players.get(i).getName() + "'s hand, type c to continue...");
			Scanner sc = new Scanner(System.in);
			if (sc.next().equalsIgnoreCase("c")) {
				for (Card c : players.get(i).getCards()) {
					formatPrint(c.toString());
				}
			} else {
				formatPrint("press c to continue..");
				i--;
			}
		}

	}

	private void spawnPlayers() {
		int[] spawnPos = { 0, 15, 0, 33, 24, 33, 24, 15, 17, 1, 12, 47 };// possible x,y spawn positions in subsequent
																			// order (row,col,row..)
		int count = 0;
		for (Player p : players) {

			p.spawnMove(spawnPos[count++], spawnPos[count++]);

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
		// hide three random cards
		hideCards();

		Collections.shuffle(cards);

		// deck of cards to be distributed between player
		ArrayList<Card> distribute = new ArrayList<>(cards);

		System.out.println(distribute.toString());

		// get number of cards per player
		int cardsPerPlayer = (int) Math.round((double) distribute.size() / numOfPlayers);

		// distribute hands
		for (int i = 0; i < numOfPlayers; i++) {
			ArrayList<Card> currHand = new ArrayList<>();
			for (int j = 0; j < cardsPerPlayer; j++) {
				if (distribute.size() > 0) {
					// check if there are cards to distribute, and that j doesn't go out of bounds
					if (distribute.size() > 0) {
						// get the top card from the deck
						Card card = distribute.get(0);
						// add the card to the current hand
						currHand.add(card);
						removeCard(card);
						// remove the card from the deck
						distribute.remove(card);
					}
				}
			}

			// add current hand to the hands list
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

	public void applyToGrid() {

	}

	public String toString() {
		return super.toString() + "[" + "]";
	}
}
