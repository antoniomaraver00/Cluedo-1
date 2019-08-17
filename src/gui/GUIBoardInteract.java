package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.image.*;
import game.Board;

public class GUIBoardInteract extends JPanel {
	private GUIGame gg;
	private Board board;
	Dimension screenSize;
	int gridX, gridY;
	int width, height;
	private int rollValue;
	private int rollCount = 0;
	private JPanel dpanel;// jpanel which displays value of dice roll

	public GUIBoardInteract(Board board, GUIGame gg) {
		this.gg = gg;
		this.board = board;
		setup();

	}

	private void setup() {
		// create panel for dice roll value
		createDicePanel();

		setBorder(new LineBorder(Color.RED, 5));// red boarder around panel
		setPreferredSize(new Dimension(0, 75));// size of panel to cover bottom of window
		this.setBackground(Color.LIGHT_GRAY);
		// set 2x4 button layout with gap of 2
		this.setLayout(new GridLayout(2, 4, 2, 0));// set button layout
		// set player choice keys
		JButton rollButton = new JButton(new AbstractAction("roll") {
			@Override
			public void actionPerformed(ActionEvent e) {
				int roll = board.getDice().roll();

				changeDiceValue(roll);// change the dice value on screen
				rollCount++;// incrememnt the total number of rolls in the game so far
			}
		});
		
		rollButton.setToolTipText("Roll the Dice");
		
		JButton suggest = new JButton("Make suggestion");
		JButton accuse = new JButton("Make accusation");

		// set arrow key buttons
		JButton arrowUP = new JButton("");
		arrowUP.setToolTipText("Move Up");
		
		JButton arrowDOWN = new JButton("");
		arrowDOWN.setToolTipText("Move Down");
		
		JButton arrowLEFT = new JButton("");
		arrowLEFT.setToolTipText("Move Left");
		
		JButton arrowRIGHT = new JButton("");
		arrowRIGHT.setToolTipText("Move Right");

		// set button to non focusable
		rollButton.setFocusable(false);
		suggest.setFocusable(false);
		accuse.setFocusable(false);
		arrowUP.setFocusable(false);
		arrowDOWN.setFocusable(false);
		arrowLEFT.setFocusable(false);
		arrowRIGHT.setFocusable(false);
		// set arrow key colour
		arrowUP.setBackground(Color.WHITE);
		arrowDOWN.setBackground(Color.WHITE);
		arrowLEFT.setBackground(Color.WHITE);
		arrowRIGHT.setBackground(Color.WHITE);

		// create the image icon of arrow for button
		arrowUP.setIcon(new ImageIcon(makeImageIcon("arrowUP.PNG")));
		arrowDOWN.setIcon(new ImageIcon(makeImageIcon("arrowDOWN.PNG")));
		arrowLEFT.setIcon(new ImageIcon(makeImageIcon("arrowLEFT.PNG")));
		arrowRIGHT.setIcon(new ImageIcon(makeImageIcon("arrowRIGHT.PNG")));

		// add buttons to panel
		add(rollButton);
		add(suggest);
		add(accuse);
		add(arrowUP);
		add(arrowDOWN);
		add(arrowLEFT);
		add(arrowRIGHT);
		
	}

	public void changeDiceValue(int roll) {
		dpanel.removeAll();
		gg.setDiceRoll(roll);// send diceValue to game
		gg.setMoveable();// let game know that dice is rolled and player is now moveable

		// create label showing number rolled
		JLabel dvl = new JLabel();
		dvl.setText(String.valueOf(roll));
		dpanel.add(dvl);

		updateUI();
	}

	private Image makeImageIcon(String filename) {// create the arrow key icons

		// create the image icon, returning null if image is invalid
		Image img = new ImageIcon(this.getClass().getResource(filename)).getImage();
		if (img == null) {
			return null;
		}
		return img;
	}

	private void createDicePanel() {
		dpanel = new JPanel();
		dpanel.setBorder(new LineBorder(Color.blue, 5));
		this.add(dpanel);

	}

	public int getRollCount() {
		return rollCount;
	}

	public void setRollCount() {
		rollCount++;
	}

}
