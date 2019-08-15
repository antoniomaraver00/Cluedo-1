package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import game.Board;
import player.Player;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class GUIPlayerCardsPanel extends JPanel {
	int cardWidth = 150;
	int cardHeight = 200;
	Dimension screenSize;
	Board board;
	int width, height;
	ArrayList<JLabel> shownLabels = new ArrayList<JLabel>();//list of labels currently being shown on screen
	JButton showHand;
	
	public GUIPlayerCardsPanel(Board board) {
		this.board = board;

		setup();
	}

	private void setup() {
		
		setBorder(new LineBorder(Color.RED, 5));
		setPreferredSize(new Dimension(285, 0));
		setLayout(new GridLayout(0,1,0,0));
		
		createShowCardsButton();
		

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		this.setBackground(Color.GREEN);
		
	}

	public void createShowCardsButton() {

		JButton jb = new JButton(new AbstractAction("show hand") {
			@Override
			public void actionPerformed(ActionEvent e) {
				drawCards();
				updateUI();
				
			}
		});

		
		jb.setFocusable(false);
		jb.setBackground(Color.ORANGE);
	    jb.setForeground(Color.BLACK);
		add(jb);

	}

	public void drawCards() {

		// write current player name on top of card
		if (board.getCurrentPlayer() != null && board.getCurrentPlayer().getHand() != null) {
			String buffer = "                                    ";			
			//shows cards of current player
			if (shownLabels.isEmpty()) {
				for (int i = 0; i < board.getCurrentPlayer().getHand().size(); i++) {
					JLabel label = new JLabel(buffer+board.getCurrentPlayer().getHand().get(i).toString());
					add(label);
					shownLabels.add(label);
				}
				
			}
			else {//if the panel is already displaying cards, remove the cards from screen if "showcards" button reclicked
				for (int i = 0; i < shownLabels.size(); i++) {
					remove(shownLabels.get(i));
				}
				shownLabels.clear();
				revalidate();
				repaint();
			}
		} else {
			JLabel label = new JLabel("player hand");
			

			add(label);
			
		}

		// add(label);

	}
}
