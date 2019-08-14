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

	public GUIPlayerCardsPanel(Board board) {
		this.board = board;

		setup();
	}

	private void setup() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(new LineBorder(Color.RED, 5));
		setPreferredSize(new Dimension(285, 0));

		createShowCardsButton();
		// drawCards();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// draw the card in the center of the panel
		this.setBackground(Color.GREEN);
		g.setColor(Color.WHITE);
		g.fillRect((getWidth() / 2) - (cardWidth / 2), (getHeight() / 2) - (cardHeight / 2), cardWidth, cardHeight);
		g.setColor(Color.BLACK);
		g.drawRect((getWidth() / 2) - (cardWidth / 2), (getHeight() / 2) - (cardHeight / 2), cardWidth, cardHeight);

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
		add(jb);

	}

	public void drawCards() {

		// write current player name on top of card
		if (board.getCurrentPlayer() != null && board.getCurrentPlayer().getHand() != null) {

			for (int i = 0; i < board.getCurrentPlayer().getHand().size(); i++) {
				JLabel label = new JLabel(board.getCurrentPlayer().getHand().get(i).toString());

				add(label);
			}
		} else {
			JLabel label = new JLabel("player hand");
			// label.setHorizontalAlignment(JLabel.CENTER);

			add(label);
			
		}

		// add(label);

	}
}
