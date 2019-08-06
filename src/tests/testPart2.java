package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import org.junit.Test;

import cards.Card;
import cards.Room;
import cards.Suspect;
import cards.Weapon;
import game.Board;
import rooms.Ballaroom;
import rooms.BilliardRoom;
import rooms.Conservatory;
import rooms.DiningRoom;
import rooms.Hall;
import rooms.Kitchen;
import rooms.Library;
import rooms.Lounge;
import rooms.Study;

public class testPart2 {
	private final Room[] rooms = { new Study(), new Hall(), new Lounge(), new DiningRoom(), new Kitchen(),
			new Ballaroom(), new Conservatory(), new BilliardRoom(), new Library() };

	private final String[] playerNames = { "Miss Scarlett", "Colonel Mustard", "Mrs. White", "Mr. Green",
			"Mrs. Peacock", "Professor Plum" };

	private final String[] weaponNames = { "Dagger", "Lead Pipe", "Spanner", "Candlestick", "Revolver", "Rope" };
	
	
	@Test
	public void playerMovement() {
		Board board = createBoard(3);

		board.spawnPlayers();
		board.setCurrentPlayer(board.getPlayers().get(0));

		int diceRoll = 3;
		// the first player's movement into the closest room
		String movement = "s s s";

		// add input as a stream
		System.setIn(new ByteArrayInputStream(movement.getBytes()));
		// start the game
		board.activeMove(diceRoll);
		
		movement = "s s a";
		System.setIn(new ByteArrayInputStream(movement.getBytes()));
		board.setCurrentPlayer(board.getPlayers().get(1));
		board.activeMove(diceRoll);
		
		diceRoll = 4;
		movement = "w w d s";
		System.setIn(new ByteArrayInputStream(movement.getBytes()));
		board.setCurrentPlayer(board.getPlayers().get(2));
		board.activeMove(diceRoll);
		
		String expectedGrid =
				  "|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|M|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|_|_|S|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|#|\n"
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
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|W|#|_|_|_|_|#|\n"
				+ "|#|#|#|#|#|#|#|+|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|\n";
		
		assertEquals(expectedGrid, board.getPlayers().get(0).getMove().getGrid().display());
	}
	
	
	/////////////////////////////////////////////
	Board createBoard(int numOfPlayers) {
		Board board = new Board();
		board.setCards(getCards());
		board.distributeCards(numOfPlayers);
		
		for (int i = 0; i < numOfPlayers; i++) {
			board.addPlayer(board.createPlayer(playerNames[i]));
		}
		
		return board;
	}
	////////////
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


