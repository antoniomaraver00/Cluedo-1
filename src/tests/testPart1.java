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

		assertEquals(excpectedGrid, board.getGrid());
	}

	/*
	 * test a false accusation
	 */
	@Test
	void falseAccusationResult() {
		Board board = createBoard(3);

		board.spawnPlayers();
		
		Player currentPlayer = board.getPlayers().get(0);
		board.setCurrentPlayer(currentPlayer);

		int diceRoll = 1;
		// the first player's movement into the closest room
		String movement = "sssaaa";

		int result = -1;

		// start the game
		for (int i = 0; i < movement.length(); i++) {
			result = board.activeMove(movement.substring(i, i + 1), diceRoll);
		}
		
		Room currentRoom = board.getRoom(board.getCurrentPlayer());
		assertTrue(currentRoom != null);
		
		Card[][] availableCards = board.getCurrentPlayer().chooseCards(board.getCurrentPlayer().getCurrentRoom(),
				board.getWeapons(), board.getSuspects());
		
		Card[] chosenCards = new Card[3];
		int  index = 0;
		
		Player player2 = board.getPlayers().get(1);
		Player player3 = board.getPlayers().get(2);
		
		if(!currentPlayer.getHand().contains(currentRoom)) {
			chosenCards[index] = currentRoom;
			index++;
		}
		
		for(int i = 0; i < availableCards.length; i++) {
			for(int j = 0; j < availableCards[i].length; j++) {
				
				if(index > 3) {
					break;
				}
				
				for(Card c : player2.getHand()) {
					if(c.toString().equals(availableCards[i][j].toString())) {
						chosenCards[index] = availableCards[i][j];
					}
				}
				
				for(Card c : player3.getHand()) {
					if(c.toString().equals(availableCards[i][j].toString())) {
						chosenCards[index] = availableCards[i][j];
					}
				}
			}
		}
		
		System.out.println(Arrays.toString(chosenCards));
		
		board.doAccusation(currentPlayer, chosenCards);

		// the first player; ie, Miss Scarlett shouldn't be on the board
		String excpectedGrid = 
				"|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|M|#|#|#|#|#|#|#|\n"
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
	 * test if the suggestion system works properly
	 */

	@Test
	void suggestionResult() {
		Board board = createBoard(3);

		board.spawnPlayers();
		board.setCurrentPlayer(board.getPlayers().get(0));

		int diceRoll = 12;
		// the first player's movement into the closest room
		String movement = "sssaaa";
		// the player's suggestions
		String accusations = "1 1 1";

		int result = -1;

		// start the game
		for (int i = 0; i < movement.length(); i++) {
			result = board.activeMove(movement.substring(i, i + 1), diceRoll);
		}

		// test the result
		//assertTrue(
			//	result == "none of the players have any of the cards you suggested" || result.contains("has the card"));

		// check if Miss Scarlett is in the right position after the suggestion
		String expectedGrid = "|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|M|#|#|#|#|#|#|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
				+ "|#|_|_|_|S|_|_|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|#|\n"
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

		assertEquals(expectedGrid, board.getGrid());
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
