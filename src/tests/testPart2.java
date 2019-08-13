package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import cards.*;
import game.*;
import player.*;
import players.*;
import rooms.*;
import weapons.*;

public class testPart2 {
	private Room[] rooms = { new Study(), new Hall(), new Lounge(), new DiningRoom(), new Kitchen(), new Ballaroom(),
			new Conservatory(), new BilliardRoom(), new Library() };
	private Weapon[] weapons = { new Dagger(), new LeadPipe(), new Revolver(), new Spanner(), new CandleStick(),
			new Rope() };
	private final Player[] allPlayers = { new MissScarlett(null), new ColonelMustard(null), new MrsWhite(null),
			new MrGreen(null), new MrsPeacock(null), new ProfessorPlum(null) };

	@Test
	public void playerMovement() {
		Board board = createBoard(3);

		board.spawnPlayers();
		board.setCurrentPlayer(board.getPlayers().get(0));

		// the first player's movement into the closest room
		String p1Movement = "sss";

		// apply the movement
		for (int i = 0; i < p1Movement.length(); i++) {
			board.activeMove(p1Movement.substring(i, i + 1), 1);
		}

		board.setCurrentPlayer(board.getPlayers().get(1));

		// the second player's movement into the closest room
		String p2Movement = "ssa";

		// apply the movement
		for (int i = 0; i < p2Movement.length(); i++) {
			board.activeMove(p2Movement.substring(i, i + 1), 1);
		}

		board.setCurrentPlayer(board.getPlayers().get(2));

		// the fourth player's movement into the closest room
		String p3Movement = "wd";

		// apply the movement
		for (int i = 0; i < p3Movement.length(); i++) {
			board.activeMove(p3Movement.substring(i, i + 1), 1);
		}

		String expectedGrid = "|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|\n"
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
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|W|#|_|_|_|_|#|\n"
				+ "|#|#|#|#|#|#|#|+|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|\n";

		assertEquals(expectedGrid, board.getGrid());
	}
	
	@Test
	public void enteringStudyRoom() {
		Board board = createBoard(3);
		board.spawnPlayers();
		board.setCurrentPlayer(board.getPlayers().get(0));

		// the first player's movement
		String p1Movement = "sssaaa";

		// apply the player's movement to the board
		for (int i = 0; i < p1Movement.length(); i++) {
			 board.activeMove(p1Movement.substring(i, i + 1), 1);
		}
		
		Room currentRoom = board.getRoom(board.getCurrentPlayer());
		
		assertTrue(currentRoom instanceof Study);
	}
	
	@Test
	public void enteringHallRoom() {
		Board board = createBoard(3);
		board.spawnPlayers();
		board.setCurrentPlayer(board.getPlayers().get(0));

		// the first player's movement
		String p1Movement = "ssssssdddww";

		// apply the player's movement to the board
		for (int i = 0; i < p1Movement.length(); i++) {
			 board.activeMove(p1Movement.substring(i, i + 1), 1);
		}
		
		Room currentRoom = board.getRoom(board.getCurrentPlayer());
		
		assertTrue(currentRoom instanceof Hall);
	}
	
	@Test
	public void enteringLoungeRoom() {
		Board board = createBoard(3);
		board.spawnPlayers();
		board.setCurrentPlayer(board.getPlayers().get(1));

		// the second player's movement
		String p1Movement = "sssddd";

		// apply the player's movement to the board
		for (int i = 0; i < p1Movement.length(); i++) {
			 board.activeMove(p1Movement.substring(i, i + 1), 1);
		}
		
		Room currentRoom = board.getRoom(board.getCurrentPlayer());


		assertTrue(currentRoom instanceof Lounge);
	}
	
	
	@Test
	public void enteringLibraryRoom() {
		Board board = createBoard(3);
		board.spawnPlayers();
		board.setCurrentPlayer(board.getPlayers().get(0));

		// the first player's movement
		String p1Movement = "ssssssssssssaaa";

		// apply the player's movement to the board
		for (int i = 0; i < p1Movement.length(); i++) {
			 board.activeMove(p1Movement.substring(i, i + 1), 1);
		}
		
		Room currentRoom = board.getRoom(board.getCurrentPlayer());
		
		assertTrue(currentRoom instanceof Library);
	}
	
	@Test
	public void enteringDiningRoom() {
		Board board = createBoard(3);
		board.spawnPlayers();
		board.setCurrentPlayer(board.getPlayers().get(1));

		// the second player's movement
		String p1Movement = "sssssssssddd";

		// apply the player's movement to the board
		for (int i = 0; i < p1Movement.length(); i++) {
			 board.activeMove(p1Movement.substring(i, i + 1), 1);
		}
		
		Room currentRoom = board.getRoom(board.getCurrentPlayer());
		
		assertTrue(currentRoom instanceof DiningRoom);
	}
	
	@Test
	public void enteringBallRoom() {
		Board board = createBoard(3);
		board.spawnPlayers();
		board.setCurrentPlayer(board.getPlayers().get(2));

		// the third player's movement
		String p1Movement = "wwwwddd";

		// apply the player's movement to the board
		for (int i = 0; i < p1Movement.length(); i++) {
			 board.activeMove(p1Movement.substring(i, i + 1), 1);
		}
		
		Room currentRoom = board.getRoom(board.getCurrentPlayer());
		
		assertTrue(currentRoom instanceof Ballaroom);
	}
	
	@Test
	public void enteringConservatoryRoom() {
		Board board = createBoard(3);
		board.spawnPlayers();
		board.setCurrentPlayer(board.getPlayers().get(2));

		// the third player's movement
		String p1Movement = "wwwwwwaaaaaass";

		// apply the player's movement to the board
		for (int i = 0; i < p1Movement.length(); i++) {
			 board.activeMove(p1Movement.substring(i, i + 1), 1);
		}
		
		Room currentRoom = board.getRoom(board.getCurrentPlayer());
		
		assertTrue(currentRoom instanceof Conservatory);
	}
	
	@Test
	public void enteringBilliardRoom() {
		Board board = createBoard(3);
		board.spawnPlayers();
		board.setCurrentPlayer(board.getPlayers().get(2));

		// the third player's
		String p1Movement = "wwwwwwaaaaaaaaaassaa";

		// apply the player's movement to the board
		for (int i = 0; i < p1Movement.length(); i++) {
			 board.activeMove(p1Movement.substring(i, i + 1), 1);
		}
		
		Room currentRoom = board.getRoom(board.getCurrentPlayer());
		
		assertTrue(currentRoom instanceof BilliardRoom);
	}
	
	@Test
	public void enteringKitchen() {
		Board board = createBoard(3);
		board.spawnPlayers();
		board.setCurrentPlayer(board.getPlayers().get(2));

		// the third player's movement
		String p1Movement = "wwwwwwwwwddd";

		// apply the player's movement to the board
		for (int i = 0; i < p1Movement.length(); i++) {
			 board.activeMove(p1Movement.substring(i, i + 1), 1);
		}
		
		Room currentRoom = board.getRoom(board.getCurrentPlayer());
		
		assertTrue(currentRoom instanceof Kitchen);
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
