package game;

import java.util.*;

import cards.*;
import player.*;
import players.*;
import rooms.*;
import weapons.*;

public class Board {

	private boolean gameOver = false;
	private ArrayList<Player> players = new ArrayList<>(); // players in the current game
	private Player currentPlayer;
	private Suspect[] suspects;
	private Room[] rooms = { new Study(), new Hall(), new Lounge(), new DiningRoom(), new Kitchen(), new Ballaroom(),
			new Conservatory(), new BilliardRoom(), new Library() };
	private Weapon[] weapons = { new Dagger(), new LeadPipe(), new Revolver(), new Spanner(), new CandleStick(),
			new Rope() };
	private ArrayList<Card> hiddenCards = new ArrayList<>(); // cards to be guessed
	private ArrayList<Card> cards = new ArrayList<>();
	private ArrayList<ArrayList<Card>> hands = new ArrayList<>(); // hands of shuffled cards distributed randomly
																	// between players

	private final Player[] allPlayers = { new MissScarlett(null), new ColonelMustard(null), new MrsWhite(null),
			new MrGreen(null), new MrsPeacock(null), new ProfessorPlum(null) };

	public Board() {
		cards = new ArrayList<Card>();

		suspects = new Suspect[allPlayers.length];

		for (int i = 0; i < allPlayers.length; i++) {
			suspects[i] = new Suspect(allPlayers[i].toString());
			cards.add(suspects[i]);
		}

		for (Weapon w : weapons) {
			cards.add(w);
		}

		for (Room r : rooms) {
			cards.add(r);
		}
	}

	public void distributeCards(int numOfPlayers) {
		while (true) {
			try {
				if (numOfPlayers > 6 || numOfPlayers < 3) {
					throw new IllegalArgumentException("invalid number of characters");
				}

				// distribute cards between players
				shuffleHands(numOfPlayers);
				break;
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Please type a valid number");
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
	}

//////////////////////game starts////////////////
	public int activeMove(String r, int roll) {

		// keystrokes map to x,y coords
		// if player already in room, moves dont count towards diceroll, nor can they
		// make suggestions or accusations untill re-entering room
		if (r.equalsIgnoreCase("w") && currentPlayer.isValid(-1, 0)) {
			currentPlayer.playerMove(currentPlayer.getPositon().getY() - 1, currentPlayer.getPositon().getX() + 0);
			if (currentPlayer.getPreviousRoom() == getRoom(currentPlayer) && currentPlayer.getPreviousRoom() != null) {
			} else {
				return --roll;
			}
		} else if (r.equalsIgnoreCase("d") && currentPlayer.isValid(0, 2)) {
			currentPlayer.playerMove(currentPlayer.getPositon().getY() + 0, currentPlayer.getPositon().getX() + 2);
			if (currentPlayer.getPreviousRoom() == getRoom(currentPlayer) && currentPlayer.getPreviousRoom() != null) {
			} else {
				return --roll;
			}
		} else if (r.equalsIgnoreCase("s") && currentPlayer.isValid(1, 0)) {
			currentPlayer.playerMove(currentPlayer.getPositon().getY() + 1, currentPlayer.getPositon().getX() + 0);
			if (currentPlayer.getPreviousRoom() == getRoom(currentPlayer) && currentPlayer.getPreviousRoom() != null) {
			} else {
				return --roll;
			}
		} else if (r.equalsIgnoreCase("a") && currentPlayer.isValid(0, -2)) {
			currentPlayer.playerMove(currentPlayer.getPositon().getY() + 0, currentPlayer.getPositon().getX() + -2);
			if (currentPlayer.getPreviousRoom() == getRoom(currentPlayer) && currentPlayer.getPreviousRoom() != null) {
			} else {
				return --roll;
			}
		} // check if the player is in a room after their turn is over

		return -1;
	}

	public String doAccusation(Player currentPlayer, Card[] chosenCards) {
		int cardFound = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < chosenCards.length; j++) {
				if (hiddenCards.get(i).equals(chosenCards[j])) {
					cardFound++;
				}
			}
		}
		if (cardFound == 3) {
			gameOver = true;

			for (Player p : players) {
				if (p != currentPlayer) {
					p.removeFromGame();
				}
			}

			return "+++++++++++++++++++ GAME OVER " + currentPlayer.toString() + " wins!! +++++++++++++++++++";
		} else {
			// notify the player that they have been eliminated
			currentPlayer.removeFromGame();

			// change eliminated players board piece to a moveable area
			currentPlayer.getMove().getGrid().setGridChar(currentPlayer.getPositon().getY(),
					currentPlayer.getPositon().getX(), '_');

			// check if there is at most one player remaining in the game
			if (allPlayersEliminated()) {
				for (Player p : players) {
					if (p.getStillInGame()) {
						gameOver = true;
						return "+++++++++++++++++++ GAME OVER " + p.toString() + " wins!! +++++++++++++++++++";
					}
				}
			}

			return currentPlayer.toString() + " has been eliminated";
		}
	}

	public String doSuggestion(Player currentPlayer, Card[] chosenCards) {
		for (int i = 0; i < chosenCards.length; i++) {
			for (Player p : players) {
				if (p != currentPlayer && p.getStillInGame()) {
					// check if the current other player has the current card
					Card revealedCard = p.getCard(chosenCards[i]);

					if (revealedCard != null && !currentPlayer.getExcludedCards().contains(revealedCard)) {
						// if they do, add it to currentPlayer's exclude list, so it won't appear on the
						// player's next turn
						currentPlayer.excludeCard(revealedCard);

						// notify the player of who has the card they are looking for
						return p.toString() + " has the card " + chosenCards[i];
					}
				}
			}
		}

		// if none of the players have any of the suggested cards, then notify the
		// current player
		return "none of the players have any of the cards you suggested";
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

	public int rollDice() {
		Dice die = new Dice();
		return die.roll();
	}

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

	public String getGrid() {
		return players.get(0).getMove().getGrid().display();
	}

	public char[][] get2dGrid() {
		return players.get(0).getMove().getGrid().getMainGrid();
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////// board setup ///////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void spawnPlayers() {
		int[] spawnPos = { 0, 15, 0, 33, 24, 33, 24, 15, 17, 1, 12, 47 };// possible x,y spawn positions in subsequent
																			// order (row,col,row..)
		int count = 0;
		for (Player p : players) {

			p.spawnMove(spawnPos[count++], spawnPos[count++]);

		}
	}

	public Player[] getAvailablePlayers() {
		ArrayList<Player> availablePlayers = new ArrayList<>();

		for (int i = 0; i < allPlayers.length; i++) {
			if (!players.contains(allPlayers[i])) {
				availablePlayers.add(allPlayers[i]);
			}
		}

		return availablePlayers.toArray(new Player[availablePlayers.size()]);
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

	private void shuffleHands(int numOfPlayers) {
		// shuffle the cards
		Collections.shuffle(cards);
		// hide three random cards
		hideCards();

		// deck of cards to be distributed between player
		ArrayList<Card> distribute = new ArrayList<>(cards);

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

		// give each player a random hand
		for (Player p : players) {
			p.setCards(getRandomHand());
		}
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
	public boolean gameover() {
		return gameOver;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Player nextPlayer() {
		// get the index of the current player
		int index = players.indexOf(currentPlayer);

		// check if the current player's index is the last one in the list
		if (index == players.size() - 1) {
			// set index to 0
			index = 0;
		} else {
			// otherwise increase the index
			++index;
		}

		return players.get(index);
	}

	public void addPlayer(Player p) {
		players.add(p);
	}

	public void setCurrentPlayer(Player p) {
		currentPlayer = p;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
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

	public ArrayList<Card> getHiddenCards() {
		return hiddenCards;
	}

	public Room getRoom(int index) {
		return rooms[index];
	}

	public Room[] getRooms() {
		return rooms;
	}

	public Weapon[] getWeapons() {
		return weapons;
	}

	public Suspect[] getSuspects() {
		return suspects;
	}

	public void setCards(ArrayList<Card> newCards) {
		cards = newCards;
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
