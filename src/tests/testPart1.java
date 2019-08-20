package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import cards.*;
import game.*;
import player.*;
import players.*;
import rooms.*;
import weapons.*;

class testPart1 {

	private Room[] rooms = { new Study(), new Hall(), new Lounge(), new DiningRoom(), new Kitchen(), new Ballaroom(),
			new Conservatory(), new BilliardRoom(), new Library() };
	private Weapon[] weapons = { new Dagger(), new LeadPipe(), new Revolver(), new Spanner(), new CandleStick(),
			new Rope() };
	private final Player[] allPlayers = { new MissScarlett(null), new ColonelMustard(null), new MrsWhite(null),
			new MrGreen(null), new MrsPeacock(null), new ProfessorPlum(null) };

	/*
	 * test the number of hidden cards, and their types
	 */
	@Test
	void hiddenCards() {
		Board board = createBoard(3);

		// check that there are 3 hidden cards
		assert (board.getHiddenCards().size() == 3);

		// count each card type
		int numOfSuspects = 0;
		int numOfWeapons = 0;
		int numOfRooms = 0;

		for (int i = 0; i < board.getHiddenCards().size(); i++) {
			if (board.getHiddenCards().get(i) instanceof Suspect) {
				numOfSuspects++;
			} else if (board.getHiddenCards().get(i) instanceof Weapon) {
				numOfWeapons++;
			} else if (board.getHiddenCards().get(i) instanceof Room) {
				numOfRooms++;
			}
		}

		// check that there is exactly one of each card type
		assertFalse(numOfRooms != 1 || numOfWeapons != 1 || numOfSuspects != 1);
	}

	/*
	 * test cards in each player's hands
	 */

	@Test
	void playerHands() {
		Board board = createBoard(6);

		Player[] players = { board.getPlayers().get(0), board.getPlayers().get(1), board.getPlayers().get(2) };

		for (Player p : players) {
			assertTrue(p.getHand().size() == 3);
		}
	}

	/*
	 * test if the grid works properly
	 */
	void grid() {
		Board board = createBoard(3);

		board.spawnPlayers();

		String excpectedGrid = "|#|#|#|#|#|#|#|S|#|#|#|#|#|#|#|#|M|#|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|#|#|#|#|#|_|_|_|#|_|#|#|#|#|_|_|_|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|\n"
				+ "|#|#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|_|#|#|#|#|_|_|_|_|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|_|#|*|*|#|_|_|_|_|_|_|_|_|_|+|\n"
				+ "|#|_|_|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|_|_|_|_|_|_|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|_|_|_|_|#|\n"
				+ "|+|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|\n"
				+ "|#|#|#|#|#|#|_|_|_|#|_|#|#|#|#|_|_|_|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|#|#|#|#|#|#|+|#|#|#|#|#|#|#|#|W|#|#|#|#|#|#|#|\n";

		assertEquals(excpectedGrid, board.getGrid());
	}

	/*
	 * test a false accusation
	 */
	@Test
	void falseAccusation() {
		Board board = createBoard(3);

		board.spawnPlayers();

		Player currentPlayer = board.getPlayers().get(0);
		board.setCurrentPlayer(currentPlayer);

		int diceRoll = 1;
		// the first player's movement into the closest room
		String movement = "sssaaa";

		// start the game
		for (int i = 0; i < movement.length(); i++) {
			board.activeMove(movement.substring(i, i + 1), diceRoll);
		}

		// get the current room the player is in, which should be the study room
		Room currentRoom = board.getRoom(board.getCurrentPlayer());

		// get the cards that the player can accuse
		Card[][] availableCards = board.getCurrentPlayer().chooseCards(board.getCurrentPlayer().getCurrentRoom(),
				board.getWeapons(), board.getSuspects());

		// cards chosen from the list above
		Card[] chosenCards = new Card[2];
		// index of the last element in chosenCards
		int index = 0;

		// get the other two players
		Player player2 = board.getPlayers().get(1);
		Player player3 = board.getPlayers().get(2);

		// check if the current player doesn't have the study card in their hand
		if (!currentPlayer.getHand().contains(currentRoom)) {
			chosenCards[index] = currentRoom;
			index++;
		}

		// the outer loop loops through the two available card types; ie, weapon, and
		// suspect
		outerloop: for (int i = 0; i < availableCards.length; i++) {
			// the inner loop loops through the actual cards within the current card type
			for (int j = 0; j < availableCards[i].length; j++) {

				// break the outer loop if two cards were added to chosenCards
				if (index > 1) {
					break outerloop;
				}

				// get one card of the current type from the second player's hand
				for (Card c : player2.getHand()) {
					// if a card is found
					if (c.toString().equals(availableCards[i][j].toString())) {
						// add it to the list
						chosenCards[index] = availableCards[i][j];
						index++;
						// break the inner loop and move on to the next card type
						break outerloop;
					}
				}

				// get one card of the current type from the third player's hand
				for (Card c : player3.getHand()) {
					// if a card is found
					if (c.toString().equals(availableCards[i][j].toString())) {
						// add it to the list
						chosenCards[index] = availableCards[i][j];
						index++;
						// break the inner loop and move on to the next card type
						break outerloop;
					}
				}
			}
		}

		// do an accusation with the cards we got
		board.doAccusation(currentPlayer, chosenCards);

		// the first player; ie, Miss Scarlet should be eliminated from the game, and
		// therefore not on the board
		String excpectedGrid = "|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|M|#|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|#|#|#|#|#|_|_|_|#|_|#|#|#|#|_|_|_|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|\n"
				+ "|#|#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|_|#|#|#|#|_|_|_|_|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|_|#|#|#|#|_|_|_|_|_|_|_|_|_|+|\n"
				+ "|#|_|_|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|_|_|_|_|_|_|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|_|_|_|_|#|\n"
				+ "|+|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|\n"
				+ "|#|#|#|#|#|#|_|_|_|#|_|#|#|#|#|_|_|_|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|_|_|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|#|#|#|#|#|#|+|#|#|#|#|#|#|#|#|W|#|#|#|#|#|#|#|\n";

		assertEquals(excpectedGrid, board.getGrid());
	}

	/*
	 * creates a new board with the specified number of players
	 */
	Board createBoard(int numOfPlayers) {
		Board board = new Board();
		board.setCards(getCards());

		for (int i = 0; i < numOfPlayers; i++) {
			board.addPlayer(allPlayers[i]);
		}

		board.distributeCards(numOfPlayers);
		board.spawnPlayers();

		return board;
	}

	/*
	 * return cards to be added to the board
	 */
	ArrayList<Card> getCards() {
		ArrayList<Card> cards = new ArrayList<>();
		for (int i = 0; i < allPlayers.length; i++) {
			cards.add(new Suspect(allPlayers[i].toString()));
		}

		for (int i = 0; i < weapons.length; i++) {
			cards.add(weapons[i]);
		}

		for (int i = 0; i < rooms.length; i++) {
			cards.add(rooms[i]);
		}

		return cards;
	}
}
