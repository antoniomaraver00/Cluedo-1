package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import cards.Card;
import cards.Room;
import cards.Suspect;
import cards.Weapon;
import player.Player;

public class Game {
	private Board board = new Board();

	private void setupGame() {
		Scanner scn = new Scanner(System.in);
		// get the number of players
		System.out.println("How many players are going to play?");
		int numOfPlayers = 0;

		while (true) {
			try {
				numOfPlayers = scn.nextInt();
				break;
			} catch (NumberFormatException e) {
				System.out.println("Please type a valid number");
			}
		}

		setPlayers(numOfPlayers);
	}

	private void setPlayers(int numOfPlayers) {
		Scanner scan = new Scanner(System.in);

		// ask the players to choose characters
		for (int i = 0; i < numOfPlayers; i++) {
			Player availablePlayers[] = board.getAvailablePlayers();
			System.out.println("Player " + (i + 1) + " please choose a character:");

			for (int j = 0; j < availablePlayers.length; j++) {
				System.out.println(j + 1 + "- " + availablePlayers[j].toString());
			}

			int playerIndex = scan.nextInt();

			while (playerIndex < 1 || playerIndex > availablePlayers.length) {
				System.out.println("Please enter a valid number");
				scan.reset();
				playerIndex = scan.nextInt();
			}

			board.addPlayer(availablePlayers[playerIndex - 1]);
		}

		board.distributeCards(numOfPlayers);
		board.spawnPlayers();
		showPlayerHands();
		System.out.println(board.getGrid());

		startGame();
	}

	private void showPlayerHands() {
		for (int i = 0; i < board.getPlayers().size(); i++) {
			formatPrint("showing " + board.getPlayers().get(i).toString() + "'s hand, type c to continue...");
			Scanner sc = new Scanner(System.in);
			if (sc.next().equalsIgnoreCase("c")) {
				for (Card c : board.getPlayers().get(i).getHand()) {
					formatPrint(c.toString());
				}
			} else {
				formatPrint("press c to continue..");
				i--;
			}
		}
	}

	private void startGame() {
		int highestRoller = findWhoGoesFirst(); // players roll the dice to see who goes first
		Player currentPlayer = board.getPlayers().get(highestRoller);// assign the currentPlayer field as the player @
																		// index of highest
		board.setCurrentPlayer(currentPlayer);

		// roller (player to start)

		while (!board.gameover()) {
			for (int i = 0; i < board.getPlayers().size(); i++) {
				if (board.gameover()) {
					break;
				} // break if the game is over mid round
				if (highestRoller != 0) {// offset the index to have the player who rolled the highest initial roll go
											// first
					i = highestRoller;
					highestRoller = 0;
				}
				board.setCurrentPlayer(board.getPlayers().get(i));
				if (board.getCurrentPlayer().getStillInGame()) {
					int roll = playerRollDice();
					formatPrint("moving keys: WASD .   walk south: S   walk north: W   walk east: D   walk west: A");

					handleMoves(roll);
				}
			}
		}
	}

	private int findWhoGoesFirst() {
		ArrayList<Integer> playerRolls = new ArrayList<>();
		Dice die = new Dice();
		formatPrint("each player role the dice to decide who goes first");
		for (int i = 0; i < board.getPlayers().size(); i++) {
			Scanner sc = new Scanner(System.in);

			formatPrint(board.getPlayers().get(i).toString() + "'s next to roll");
			formatPrint("press r to roll die");

			String r;
			r = sc.next();
			if (r.equalsIgnoreCase("r")) {
				int diceRoll = board.rollDice();
				System.out.println("rolled a " + diceRoll);
				playerRolls.add(diceRoll);
			} else {
				formatPrint("remember, press r to roll die. try again");
				i--;
			}
		}
		for (int i = 0; i < playerRolls.size(); i++) {
			if (playerRolls.get(i) == Collections.max(playerRolls)) {
				formatPrint(board.getPlayers().get(i).toString() + " rolled the highest with a roll of: "
						+ playerRolls.get(i));
				return i;
			}
		}
		return 0;
	}

	private int playerRollDice() {
		int diceRoll = 0;

		while (true) {
			Scanner sc = new Scanner(System.in);

			formatPrint(board.getCurrentPlayer().toString() + "'s next to roll");
			formatPrint("press r to roll die");

			String r = sc.next();
			if (r.equalsIgnoreCase("r")) {
				diceRoll = board.rollDice();
				break;

			} else {
				formatPrint("remember, press r to roll die. try again");
			}

		}
		return diceRoll;
	}

	private void handleMoves(int numOfMoves) {
		Scanner sc = new Scanner(System.in);
		int rollCount = numOfMoves;

		while (numOfMoves > 0) {
			System.out.println(board.getGrid());
			formatPrint("roll of " + numOfMoves + ". " + rollCount + " moves remaining");
			formatPrint("enter move key");

			String r = sc.next();
			int move = board.activeMove(r, numOfMoves);

			if (move >= 0) {
				numOfMoves = move;

				Room currentRoom = board.getRoom(board.getCurrentPlayer());
				System.out.println(currentRoom);

				if (currentRoom != null) {
					// notify the player which room they are in
					board.getCurrentPlayer().setPreviousRoom(currentRoom);
					formatPrint("You are inside the " + currentRoom.toString());

					numOfMoves = 0;
					rollCount = 0;// player cannot move more steps once in room
					handleInsideRoom();
				} // proccess player if they enter room
				else {
					board.getCurrentPlayer().setPreviousRoom(null);
					// if player is not in a room, have previous room set to null
				}

			} else {
				formatPrint("incorrect move");
			}
		}
	}

	private void handleInsideRoom() {
		Scanner scan = new Scanner(System.in);
		int choice = accusationOrSuggestion();

		Card[][] availableCards = board.getCurrentPlayer().chooseCards(board.getCurrentPlayer().getCurrentRoom(),
				board.getWeapons(), board.getSuspects());

		// first ask the player to choose a weapon
		System.out.println("Choose a weapon:");

		for (int i = 0; i < availableCards[0].length; i++) {
			// check if the weapon is not in the player's deck, or hasn't been revealed to
			// them by another player in a previous turn
			System.out.println(i + 1 + "- " + availableCards[0][i].toString());
		}

		// get the weapon of choice
		Weapon murderWeapon = (Weapon) availableCards[0][scan.nextInt() - 1];
		scan.reset();

		System.out.println("Choose a murder suspect:");

		for (int i = 0; i < availableCards[1].length; i++) {
			// check if the suspect is not in the player's deck, or hasn't been revealed to
			// them by another player in a previous turn
			System.out.println(i + 1 + "- " + availableCards[1][i].toString());
		}

		// get the suspect of choice
		Suspect murderSuspect = (Suspect) availableCards[1][scan.nextInt() - 1];

		Room currentRoom = board.getCurrentPlayer().getCurrentRoom();

		ArrayList<Card> chosenCards = new ArrayList<>();

		chosenCards.add(murderWeapon);
		chosenCards.add(murderSuspect);

		if (board.getCurrentPlayer().getExcludedCards().contains(currentRoom)) {
			chosenCards.add(currentRoom);
		}

		String result;

		if (choice == board.getCurrentPlayer().accusation()) {
			result = board.doAccusation(board.getCurrentPlayer(), chosenCards.toArray(new Card[chosenCards.size()]));
		} else {
			result = board.doSuggestion(board.getCurrentPlayer(), chosenCards.toArray(new Card[chosenCards.size()]));
		}

		System.out.println(result);
	}

	/*
	 * asks the player if they want to make a suggestion, or an accusation
	 */

	private int accusationOrSuggestion() {
		Scanner scan = new Scanner(System.in);

		System.out.println("Make a chioce:");
		System.out.println("1- Suggestion\n" + "2- Accusation");

		int choice = scan.nextInt();

		// keep looping until the player makes a valid choice
		while (choice != board.getCurrentPlayer().suggestion() && choice != board.getCurrentPlayer().accusation()) {
			System.out.println("Make a chioce:");
			System.out.println("1- Suggestion\n" + "2- Accusation");

			try {
				choice = scan.nextInt();
				System.out.println(choice);

				if (choice != board.getCurrentPlayer().suggestion()
						&& choice != board.getCurrentPlayer().accusation()) {
					throw new IllegalArgumentException();
				}

			} catch (IllegalArgumentException e) {
				System.out.println("Please enter a valid number");
				scan.reset();
			}
		}

		// return the choice so the board can decide what to do next
		return choice;
	}

	private void formatPrint(String s) {
		System.out.println("-------------------------------------------------");
		System.out.println(s);
	}

	public static void main(String[] args) {
		new Game().setupGame();
	}
}