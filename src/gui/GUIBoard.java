package gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

import game.Board;

public class GUIBoard extends JPanel {
	private Board board;

	public GUIBoard(Board board) {
		this.board = board;
	}

	/*
	 * draw the board on the canvas
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		this.setLayout(null);
		// get the grid
		char grid[][] = board.get2dGrid();

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				// if the current character is a wall
				if (grid[i][j] == '#') {
					// draw it as a rect
					g.setColor(Color.DARK_GRAY);
					g.fillRect((j * 10) + 10, (i * 10) + 10, 10, 10);
					// if the current character is a letter
				} else if (Character.isLetter(grid[i][j])) {
					// draw it as a string
					g.setColor(Color.BLUE);
					g.setFont(new Font("Arial", Font.BOLD, 12));
					g.drawString("" + grid[i][j], (j * 10) + 10, (i * 10) + 18);
				}
			}
		}

		repaint();
	}
}
