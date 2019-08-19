package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import cards.Card;
import cards.Room;
import game.Board;
import game.Dice;
import player.Player;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GUIGame extends JFrame {
	private JPanel panel = new JPanel();
	private Board board = new Board();
	private GUIBoard guiBoard = new GUIBoard(board);
	private GUIBoardInteract guiBoardLowerPanel = new GUIBoardInteract(board, this);
	private GUIPlayerCardsPanel guiPlayerCardsPanel = new GUIPlayerCardsPanel(board);
	private GUIArrowKeys guiArrowKeys = new GUIArrowKeys(board);
	private Dimension screenSize;
	private int width, height;
	private boolean canMove; // checks if the current player can move
	private int diceRoll = 0;
	private ArrayList<Integer> playerRolls = new ArrayList<>();
	private GameSounds sounds = new GameSounds();

	public GUIGame() {
		setTitle("Cluedo");
		// get the size of the screen
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// make the window's width half of the screen's width
		width = (int) (screenSize.width * 0.5);
		// make the window's height 70% of the screen's height
		height = (int) (screenSize.height * 0.7);
		// set the JFrame's size
		setSize(new Dimension(width, height));
		// make the window at the centre of the screen
		setLocationRelativeTo(null);
		// set the JFrame's background colour
		panel.setBackground(Color.WHITE);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// add a menu bar to the top
		this.addMenuBar();
		this.setupGame();

		// initialize the keyboard listener
		addKeyListener(new KeyboardListener());
		setFocusable(true);

		addMouseListener(new MouseListner());

		this.add(panel);

		sounds.backgroundMusic();
	}

	private void addMenuBar() {
		// create the JMenuBar object first
		JMenuBar menuBar = new JMenuBar();
		// create a JMenu
		JMenu menu = new JMenu("File");
		// create a menu item
		JMenuItem quit = new JMenuItem(new AbstractAction("Quit") {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		// create a restart item
		JMenuItem restart = new JMenuItem(new AbstractAction("Restart") {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO
			}
		});
		// add the items to the menu
		menu.add(quit);
		menu.add(restart);
		// add the menu to the menu bar
		menuBar.add(menu);
		// set the JFrame's menu bar to the one we just created
		setJMenuBar(menuBar);
	}

	private void setupGame() {
		// add the label first
		JLabel label = new JLabel("How many Players are going to play?");
		label.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(label);

		// buttons for the user to choose the number of players
		JButton[] buttons = new JButton[4];

		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton();
			// number of players is from 3 to 6
			buttons[i].setText(Integer.toString(i + 3));
			// align buttons to the center of the window
			buttons[i].setAlignmentX(CENTER_ALIGNMENT);

			buttons[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// get the number on the button
					int numOfPlayers = Integer.parseInt(e.getActionCommand());
					// clear the graphics, and update the JFrame
					panel.removeAll();
					panel.updateUI();
					// go to the character selection
					characterSelection(numOfPlayers, 1);
				}
			});

			panel.add(buttons[i]);
		}

	}

	private void characterSelection(int numOfPlayers, final int currentPlayer) {
		// check if all players have chosen a character
		if (numOfPlayers == 0) {
			// remove everything on the panel
			panel.removeAll();
			panel.updateUI();
			// distribute cards between the players
			distributeCards();

			// exit this method
			return;
		}

		// one radio button for each available character
		ArrayList<JRadioButton> radioButtons = new ArrayList<>();
		ButtonGroup group = new ButtonGroup();

		// ask the current player to choose a character
		String labelTxt = "Player " + currentPlayer + " please choose a character";
		JLabel label = new JLabel(labelTxt);

		// align the label to the center of the window and add it to the panel
		label.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(label);

		// get list of available characters
		Player[] availablePlayers = board.getAvailablePlayers();

		for (int j = 0; j < availablePlayers.length; j++) {
			// add a radio button for each character
			JRadioButton currentButton = new JRadioButton(board.getAvailablePlayers()[j].toString());

			// align buttons to the center of the window, and them it to the panel
			currentButton.setAlignmentX(CENTER_ALIGNMENT);
			radioButtons.add(currentButton);
			group.add(currentButton);
			panel.add(currentButton);
		}

		// button to confirm character selection
		JButton enter = new JButton("enter");
		enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < radioButtons.size(); i++) {
					// get the selected button
					if (radioButtons.get(i).isSelected()) {
						// add the character that is at the same index as the selected button
						board.addPlayer(availablePlayers[i]);

						// update graphics
						panel.removeAll();
						panel.updateUI();

						// rerun the method for the next player to choose a character
						int remaining = numOfPlayers - 1;
						int nextPlayer = currentPlayer + 1;
						characterSelection(remaining, nextPlayer);
					}
				}
			}
		});

		// align the button to the center of the window
		enter.setAlignmentX(CENTER_ALIGNMENT);

		// add the button to the panel and update graphics
		panel.add(enter);

		panel.updateUI();
	}

	private void distributeCards() {
		board.distributeCards(board.getPlayers().size());
		board.spawnPlayers();
		drawBoard();
	}

	private void drawBoard() {
		panel.add(guiBoard);

		bottomPane();// initialize the bottom interaction pane
		cardPane();// initialise right hand player card pane
		board.setCurrentPlayer(board.getPlayers().get(0));

		startGame();// start rounds
	}

	public void startGame() {
		// start by rolling off

		// int firstPlayer = findWhoGoesFirst();

	}

	public void bottomPane() {
		add(guiBoardLowerPanel, BorderLayout.SOUTH);
	}

	public void cardPane() {
		add(guiPlayerCardsPanel, BorderLayout.EAST);
	}

	private void handleInsideRoom(Room currentRoom) {
		board.getCurrentPlayer().setPreviousRoom(currentRoom);

		// options the player has to make inside the room
		String[] options = { "Suggestion", "Accusation" };
		// show a dialog box for the player to make a choice
		String choice = "";

		// make sure that the player makes a choice
		while (choice == null || choice.equals("")) {
			choice = (String) JOptionPane.showInputDialog(null,
					board.getCurrentPlayer() + " has entered the " + currentRoom + ". Please choose an option:",
					"Inside a room", JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		}

		// get the cards that the player can suggest/accuse
		Card[][] availableCards = board.getCurrentPlayer().chooseCards(board.getCurrentPlayer().getCurrentRoom(),
				board.getWeapons(), board.getSuspects());

		Card murderWeapon = null;

		// make sure that the player chooses a weapon
		while (murderWeapon == null) {
			murderWeapon = (Card) JOptionPane.showInputDialog(null, "Please select a murder weapon", "Choose a card",
					JOptionPane.INFORMATION_MESSAGE, null, availableCards[0], availableCards[0][0]);
		}

		Card suspect = null;

		// make sure that the player chooses a suspect
		while (suspect == null) {
			suspect = (Card) JOptionPane.showInputDialog(null, "Please select a suspect", "Choose a card",
					JOptionPane.INFORMATION_MESSAGE, null, availableCards[1], availableCards[1][0]);
		}

		ArrayList<Card> chosenCards = new ArrayList<>();

		if (board.getCurrentPlayer().getExcludedCards().contains(currentRoom)) {
			chosenCards.add(currentRoom);
		}

		chosenCards.add(murderWeapon);
		chosenCards.add(suspect);

		String result = "";

		// check the player's choice
		if (choice.equals(options[0])) {
			result = board.doSuggestion(board.getCurrentPlayer(), chosenCards.toArray(new Card[chosenCards.size()]));
		} else if (choice.equals(options[1])) {
			result = board.doAccusation(board.getCurrentPlayer(), chosenCards.toArray(new Card[chosenCards.size()]));
		}

		// show the result of the suggestion/accusation
		JOptionPane.showMessageDialog(null, result);
	}

	public void setDiceRoll(int roll) {
		diceRoll = roll;
	}

	public void setMoveable() {
		canMove = true;
	}

	/*
	 * keyboard listener for game short cuts
	 */
	private class KeyboardListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
			guiPlayerCardsPanel.setShowHandButton();// make the show hand button display current players name

			guiPlayerCardsPanel.setShowHandButton();// make the show hand button display current players name

			// if the r key is pressed
			if (e.getKeyChar() == 'r' && !canMove) {

				// roll the dice, and allow the player move
				diceRoll = board.rollDice();

				guiBoardLowerPanel.setRollCount();// Increment the total roll count
				guiBoardLowerPanel.changeDiceValue(diceRoll);

				canMove = true;
			} else {
				// check if the game isn't over, and that the player can move
				if (!board.gameover() && canMove) {
					// move the player
					int move = board.activeMove("" + e.getKeyChar(), diceRoll);
					// check if the player has entered a room
					Room currentRoom = board.getRoom(board.getCurrentPlayer());
					if (currentRoom == null) {
						board.getCurrentPlayer().setPreviousRoom(null);
					}
					// check if the move is valid
					if (move >= 0 && currentRoom == null) {
						diceRoll = move;
					}

					// player has entered room from hallway
					if (currentRoom != null && board.getCurrentPlayer().getPreviousRoom() == null) {
						// don't allow the player to move anymore
						diceRoll = 0;
						// set the player's previous room
						board.getCurrentPlayer().setPreviousRoom(currentRoom);

						// do a suggestion/accusation
						handleInsideRoom(currentRoom);

						// if the player has entered to a new room
						if (board.getCurrentPlayer().getPreviousRoom() == null) {
							// don't allow them to make anymore moves
							diceRoll = 0;
							// ask them to make a suggestion/accusation
							handleInsideRoom(currentRoom);
						}
					}
					// if the move count is less than 1
					if (diceRoll <= 0 && canMove) {
						// disable player's movement
						canMove = false;
						// allow the next player to move
						board.setCurrentPlayer(((Board) board).nextPlayer());
					}
				}
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {

		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}

	/*
	 * mouse listener to change the cursor
	 */

	private class MouseListner extends MouseAdapter {
		private BufferedImage mCursor; // the custom cursor's image
		private Cursor cursor;

		public MouseListner() {
			try {
				// get the image
				mCursor = ImageIO.read(new File("src/gui/cursor.png"));

				// initialize the cursor
				cursor = Toolkit.getDefaultToolkit().createCustomCursor(mCursor,
						new Point(getContentPane().getX(), getContentPane().getY()), "custom cursor");

				// add it to the JFrame
				setCursor(cursor);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void mouseClicked(MouseEvent event) {
			sounds.mouseClickSound();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			GUIGame game = new GUIGame();
			game.setVisible(true);
			game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		});
	}
}
