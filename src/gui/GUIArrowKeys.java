package gui;

import java.awt.Color;
import java.awt.Dimension;
import game.Board;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class GUIArrowKeys extends JPanel {
	private Board board;
	private Dimension screenSize;
	
	
	public GUIArrowKeys(Board board) {
		this.board=board;
		setup();
	}
	public void setup() {
		setBorder(new LineBorder(Color.GREEN,5));
		
		
	}
	
}
