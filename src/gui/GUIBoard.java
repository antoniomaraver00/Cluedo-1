package gui;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.*;

import cards.Weapon;
import game.Board;
import player.Position;
import weapons.CandleStick;

public class GUIBoard extends JPanel {

	Dimension screenSize;
	int width, height;
	int rectSize = 12;
	int boardOffsetX = 20, boardOffsetY = 20;
	int totalBoardWidth;
	int totalBoardHeight;
	char grid[][];
	private Weapon[] weapons;

	private Board board;

	public GUIBoard(Board board) {
		this.board = board;
		setup();
	}

	private void setup() {
		grid = board.get2dGrid();
		// set x,y fields the value of the total distance the boards edges are from the
		// top left hand corner.
		totalBoardWidth = boardOffsetX + (((grid[0].length / 2) - 1) * rectSize);
		totalBoardHeight = boardOffsetY + ((grid.length) * rectSize);

		weapons = board.getWeapons();
		Collections.shuffle(Arrays.asList(weapons));

		// this.add(guiBoardLowerPanel);
	}

	private void addWeapons() {
		int index = 0;

		// get the grid
		char grid[][] = board.get2dGrid();

		outerloop: for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (board.getRoom(index).within(new Position(i, j))) {
					JLabel weapon = new JLabel(board.getWeapons()[index].toString());
					weapon.setLocation(i, j);
					add(weapon);
					index++;

					if (index == 6) {
						break outerloop;
					}
				}
			}
		}
	}

	/*
	 * draw the board on the canvas
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// draw the grid
		char grid[][] = board.get2dGrid();
		drawCheckeredGrid(grid, g);

		int index = 0;

		// draw the characters and walls
		int wallCount = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == '#') {

					g.setColor(Color.DARK_GRAY);

					g.fillRect((wallCount * rectSize) + boardOffsetX, (i * rectSize) + boardOffsetX, rectSize,
							rectSize);
					wallCount++;
				} else if (grid[i][j] == '_') {
					// check if inde is not out of bounds of the weapons array
					if (index < board.getWeapons().length) {
						// check if the current position inside a room
						if (board.getRoom(index).within(new Position(j, i))) {
							// set the colour and font of the string
							g.setColor(Color.DARK_GRAY);
							g.setFont(new Font("Arial", Font.BOLD, 10));
							// draw the weapon's name as a string inside the current room
							g.drawString(board.getWeapons()[index].toString(),
									(wallCount * rectSize) + boardOffsetX + 4,
									(i * rectSize) + boardOffsetY + rectSize);
							// move on to the next room and weapon
							index++;
						}
					}

					wallCount++;
				} else if (grid[i][j] == '+') {
					g.fillRect((wallCount * rectSize) + boardOffsetX, (i * rectSize) + boardOffsetX, rectSize,
							rectSize);
					wallCount++;
				} else if (Character.isLetter(grid[i][j])) {// if the current character is a letter
					// draw it as a string
					g.setColor(Color.BLUE);
					g.setFont(new Font("Arial", Font.BOLD, 12));
					g.drawString("" + grid[i][j], (wallCount * rectSize) + boardOffsetX + 4,
							(i * rectSize) + boardOffsetY + rectSize);
					wallCount++;
				}
			}
			wallCount = 0;
		}

		repaint();

	}

	public void drawCheckeredGrid(char grid[][], Graphics g) {// draw the checkered cells of the board
		int boardWidth = (((grid[0].length / 2) - 1) * rectSize);// width of the back-end board (including all
																	// characters) [50]. true value = [24], (n/2-1).
		g.drawLine(boardOffsetX, boardOffsetY + ((grid.length) * rectSize),
				boardOffsetX + (((grid[0].length / 2) - 1) * rectSize), boardOffsetY + ((grid.length) * rectSize));
		g.drawLine(boardOffsetX + boardWidth, boardOffsetY, boardOffsetX + boardWidth,
				boardOffsetY + ((grid.length) * rectSize));
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < (grid[i].length / 2) - 1; j++) {
				g.drawLine(boardOffsetX, boardOffsetY + (rectSize * i), boardOffsetX + boardWidth,
						boardOffsetY + (rectSize * i));// draw horiz lines
				g.drawLine(boardOffsetX + (rectSize * j), boardOffsetY, boardOffsetX + (rectSize * j),
						boardOffsetY + ((grid.length) * rectSize));// draw vert lines
			}
		}
	}

}
