package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;

import game.Board;
import player.Player;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class GUIPlayerCardsPanel extends JPanel {
	int cardWidth = 150;
	int cardHeight = 200;
	Dimension screenSize;
	Board board;
	int width, height;

	public GUIPlayerCardsPanel(Board board) {
		this.board = board;
		
		setup();
	}

	private void setup() {
		setBorder(new LineBorder(Color.RED, 5));
		setPreferredSize(new Dimension(285, 0));
		drawCards();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.setLayout(null);
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// draw the card in the center of the panel
		this.setBackground(Color.GREEN);
		g.setColor(Color.WHITE);
		g.fillRect((getWidth() / 2) - (cardWidth / 2), (getHeight() / 2) - (cardHeight / 2), cardWidth, cardHeight);
		g.setColor(Color.BLACK);
		g.drawRect((getWidth() / 2) - (cardWidth / 2), (getHeight() / 2) - (cardHeight / 2), cardWidth, cardHeight);

	}

	public void drawCards() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		// write current player name on top of card
		if (board.getCurrentPlayer()!=null && board.getCurrentPlayer().getHand()!=null) {
			
			for (int i = 0; i<board.getCurrentPlayer().getHand().size(); i++) {
				JLabel label = new JLabel(board.getCurrentPlayer().getHand().toString());
				add(label);
			}
		}else {
			JLabel label = new JLabel("player hand");
			add(label);
		}
		
		
		
		
		
		
		
	    
		//add(label);
		
		
		
		
	}
}
