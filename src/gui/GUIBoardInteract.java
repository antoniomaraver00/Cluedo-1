package gui;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.image.*;
import game.Board;




public class GUIBoardInteract extends JPanel {
	private Board board;
	Dimension screenSize;
	int gridX,gridY;
	int width, height;
	
	public GUIBoardInteract(Board board) {
		this.board = board;
		setup();
		
	}
	private void setup() {
		setBorder(new LineBorder(Color.RED,5));//red boarder around panel
		setPreferredSize(new Dimension(0,75));//size of panel to cover bottom of window
		this.setBackground(Color.LIGHT_GRAY);
		//set 2x4 button layout with gap of 2
		this.setLayout(new GridLayout(2,4,2,0));//set button layout
		//set player choice keys
		JButton rollButton = new JButton("Roll Dice");
		JButton suggest = new JButton("Make suggestion");
		JButton accuse = new JButton("Make accusation");
		JButton cardShow = new JButton("Show cards");
		
		//set arrow key buttons
		JButton arrowUP = new JButton("");
		JButton arrowDOWN = new JButton("");
		JButton arrowLEFT = new JButton("");
		JButton arrowRIGHT = new JButton("");
		//set arrow key colour
		arrowUP.setBackground(Color.WHITE);
		arrowDOWN.setBackground(Color.WHITE);
		arrowLEFT.setBackground(Color.WHITE);
		arrowRIGHT.setBackground(Color.WHITE);
		
		//create the image icon of arrow for button
		arrowUP.setIcon( new ImageIcon(makeImageIcon("arrowUP.PNG")));
		arrowDOWN.setIcon( new ImageIcon(makeImageIcon("arrowDOWN.PNG")));
		arrowLEFT.setIcon( new ImageIcon(makeImageIcon("arrowLEFT.PNG")));
		arrowRIGHT.setIcon( new ImageIcon(makeImageIcon("arrowRIGHT.PNG")));
		
		//	add buttons to panel
		add(rollButton );
		add(suggest);
		add(accuse);
		add(cardShow);
		add(arrowUP);
		add(arrowDOWN);
		add(arrowLEFT);
		add(arrowRIGHT);
		
	}
	private Image makeImageIcon(String filename) {		
		
		//create the image icon, returning null if image is invalid
		Image img = new ImageIcon(this.getClass().getResource(filename)).getImage();
		if (img==null) {return null;}
		return img;
	}
	/**
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		//this.setBounds(50, 400, 100, 100);
		
		width = (int) (300);//screenSize.width * 0.5
		height = (int) (300);//screenSize.height * 0.1
		//setSize(new Dimension(width, height));
		
		this.setBackground(Color.BLUE);
		
		repaint();
	}
	*/
	
	
}
