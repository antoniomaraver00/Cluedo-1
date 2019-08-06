package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import cards.*;
import game.*;
import player.Player;
import rooms.*;

class testPart1 {

	private final Room[] rooms = { new Study(), new Hall(), new Lounge(), new DiningRoom(), new Kitchen(),
			new Ballaroom(), new Conservatory(), new BilliardRoom(), new Library() };

	private final String[] playerNames = { "Miss Scarlett", "Colonel Mustard", "Mrs. White", "Mr. Green",
			"Mrs. Peacock", "Professor Plum" };

	private final String[] weaponNames = { "Dagger", "Lead Pipe", "Spanner", "Candlestick", "Revolver", "Rope" };

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
		
		String excpectedGrid =
				  "|#|#|#|#|#|#|#|S|#|#|#|#|#|#|#|#|M|#|#|#|#|#|#|#|\n"
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
		
		
	}
	
	/*
	 * creates a new board with the specified number of players
	 */
	Board createBoard(int numOfPlayers) {
		Board board = new Board();
		board.setCards(getCards());
		board.distributeCards(numOfPlayers);
		
		for (int i = 0; i < numOfPlayers; i++) {
			board.addPlayer(board.createPlayer(playerNames[i]));
		}
		
		return board;
	}
	
	/*
	 * return cards to be added to the board
	 */
	ArrayList<Card> getCards() {
		ArrayList<Card> cards = new ArrayList<>();
		for (int i = 0; i < playerNames.length; i++) {
			cards.add(new Suspect(playerNames[i]));
		}

		for (int i = 0; i < weaponNames.length; i++) {
			cards.add(new Weapon(weaponNames[i]));
		}

		for (int i = 0; i < rooms.length; i++) {
			cards.add(rooms[i]);
		}

		return cards;
	}
}
