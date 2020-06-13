package diceGame.view;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import controller.DiceGameMenuBarController;
import model.interfaces.GameEngine;

public class DiceGameMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;
	private JMenu menuBar;
	private JMenuItem removePlayer,removeAllPlayers;
	
	/*
	 * Parameterized constructor responsible for instantiating different menuItems
	 * Also, adds the required components onto the menuBar
	 * */
	public DiceGameMenuBar(MainFrame mainFrame, GameEngine gameEngine) {
		menuBar = new JMenu("Options");
		removePlayer = new JMenuItem("Remove Player");
		removePlayer.setActionCommand("removePlayerFromGUI");
		removeAllPlayers = new JMenuItem("Remove All Current PLayers ");
		removeAllPlayers.setActionCommand("removeAllPlayersFromGUI");
		
		removePlayer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
		removeAllPlayers.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		
		menuBar.add(removePlayer);
		menuBar.add(removeAllPlayers);
		addListeners(mainFrame,gameEngine);
		this.add(menuBar);
		this.setFocusable(false);
	}
	

	//Add listeners to respective menuItems
	public void addListeners(MainFrame mainFrame, GameEngine gameEngine) {

		//TODO
		DiceGameMenuBarController listener = new DiceGameMenuBarController(mainFrame, gameEngine);
		removePlayer.addActionListener(listener);
		removeAllPlayers.addActionListener(listener);
	
	}
	
}
