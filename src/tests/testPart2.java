//package tests;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.io.ByteArrayInputStream;
//import java.util.ArrayList;
//
//import org.junit.Test;
//
//import cards.Card;
//import cards.Room;
//import cards.Suspect;
//import cards.Weapon;
//import game.Board;
//import rooms.Ballaroom;
//import rooms.BilliardRoom;
//import rooms.Conservatory;
//import rooms.DiningRoom;
//import rooms.Hall;
//import rooms.Kitchen;
//import rooms.Library;
//import rooms.Lounge;
//import rooms.Study;
//import weapons.CandleStick;
//import weapons.Dagger;
//import weapons.LeadPipe;
//import weapons.Revolver;
//import weapons.Rope;
//import weapons.Spanner;
//
//public class testPart2 {
//	private final Room[] rooms = { new Study(), new Hall(), new Lounge(), new DiningRoom(), new Kitchen(),
//			new Ballaroom(), new Conservatory(), new BilliardRoom(), new Library() };
//
//	private final String[] playerNames = { "Miss Scarlett", "Colonel Mustard", "Mrs. White", "Mr. Green",
//			"Mrs. Peacock", "Professor Plum" };
//
//	private Weapon[] weapons = {new Dagger(), new LeadPipe(), new Revolver(), new Spanner(), new CandleStick(), new Rope()};
//	
//	
//	@Test
//	public void playerMovement() {
//		Board board = createBoard(3);
//
//		board.spawnPlayers();
//		board.setCurrentPlayer(board.getPlayers().get(0));
//
//		int diceRoll = 3;
//		// the first player's movement into the closest room
//		String movement = "sss";
//
//		String result = "";
//
//		// start the game
//		for (int i = 0; i < movement.length(); i++) {
//			result = board.activeMove(movement.substring(i, i + 1));
//		}
//		
//		String expectedGrid =
//				  "|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|\n"
//				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
//				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|M|_|_|#|_|_|_|_|#|\n"
//				+ "|#|_|_|_|_|_|_|S|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|#|\n"
//				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
//				+ "|#|#|#|#|#|#|_|_|_|#|_|#|#|#|#|_|_|_|#|#|#|#|#|#|\n"
//				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|\n"
//				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|\n"
//				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|_|_|_|_|#|\n"
//				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|\n"
//				+ "|#|#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|#|_|_|_|_|#|\n"
//				+ "|#|_|_|_|_|#|_|_|_|_|#|#|#|#|_|_|_|_|#|#|#|#|#|#|\n"
//				+ "|#|_|_|_|_|_|_|_|_|_|#|*|*|#|_|_|_|_|_|_|_|_|_|+|\n"
//				+ "|#|_|_|_|_|#|_|_|_|_|#|_|_|#|_|_|_|_|#|#|#|#|#|#|\n"
//				+ "|#|_|_|_|_|#|_|_|_|_|_|_|_|_|_|_|_|_|#|_|_|_|_|#|\n"
//				+ "|#|#|#|#|#|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|\n"
//				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|_|_|_|_|#|\n"
//				+ "|+|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|#|#|#|#|#|\n"
//				+ "|#|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|#|\n"
//				+ "|#|#|#|#|#|#|_|_|_|#|_|#|#|#|#|_|_|_|#|#|#|#|#|#|\n"
//				+ "|#|_|_|_|_|_|_|_|_|#|_|_|_|_|#|_|_|_|_|_|_|_|_|#|\n"
//				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
//				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|\n"
//				+ "|#|_|_|_|_|#|_|_|_|#|_|_|_|_|#|_|_|W|#|_|_|_|_|#|\n"
//				+ "|#|#|#|#|#|#|#|+|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|#|\n";
//		
//		assertEquals(expectedGrid, board.getPlayers().get(0).getMove().getGrid().display());
//	}
//	
//	
//	/////////////////////////////////////////////
//	Board createBoard(int numOfPlayers) {
//		Board board = new Board();
//		board.setCards(getCards());
//		board.distributeCards(numOfPlayers);
//		
//		for (int i = 0; i < numOfPlayers; i++) {
//			//board.addPlayer(board.createPlayer(playerNames[i]));
//		}
//		
//		return board;
//	}
//	////////////
//	ArrayList<Card> getCards() {
//		ArrayList<Card> cards = new ArrayList<>();
//		for (int i = 0; i < playerNames.length; i++) {
//			cards.add(new Suspect(playerNames[i]));
//		}
//
//		for (int i = 0; i < weapons.length; i++) {
//			cards.add(weapons[i]);
//		}
//
//		for (int i = 0; i < rooms.length; i++) {
//			cards.add(rooms[i]);
//		}
//
//		return cards;
//	}
//
//}
//
//
