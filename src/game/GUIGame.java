package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIGame extends JFrame {
	JPanel panel = new JPanel();
	Board board = new Board();
	Dimension screenSize;
	int width, height;

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

		this.add(panel);
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
		// add the item to the menu
		menu.add(quit);
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

		// radio buttons for the user to choose the number of players
		JRadioButton[] radioButtons = new JRadioButton[4];
		ButtonGroup btnGroup = new ButtonGroup();

		for (int i = 0; i < radioButtons.length; i++) {
			radioButtons[i] = new JRadioButton();
			radioButtons[i].setText(Integer.toString(i + 3));
			radioButtons[i].setAlignmentX(CENTER_ALIGNMENT);

			btnGroup.add(radioButtons[i]);

			panel.add(radioButtons[i]);
		}

		// add a button to start the game
		JButton button = new JButton("Enter");
		button.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(button);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			GUIGame game = new GUIGame();
			game.setVisible(true);
		});
	}
}
