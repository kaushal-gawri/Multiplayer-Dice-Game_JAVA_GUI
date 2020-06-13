package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import diceGame.view.MainFrame;
import model.interfaces.GameEngine;

public class DiceGameSummaryPanelController extends KeyAdapter implements ListSelectionListener{

	private GameEngine gameEngine;
	private MainFrame mainFrame;
	
	/*
	 * Parameterized constructor which sets the value of the gameEngine and mainFrame
	 * */
	public DiceGameSummaryPanelController(MainFrame mainFrame, GameEngine gameEngine) {
		this.mainFrame = mainFrame;
		this.gameEngine = gameEngine;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		if(!e.getValueIsAdjusting()) {
			mainFrame.getDiceGameToolBar().focusBetField();
			mainFrame.getDiceGameToolBar().setBetInputEnabled(true);
		}
			 else {
				//mainFrame.getDiceGamePanel().updatePanel(mainFrame, gameEngine.getHouseResult());
			}
		}
		
	// sets the default action for specific key events
	public void actionOnKey(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_TAB: mainFrame.getDiceGameToolBar().focusBetField();
		}
	}
	
	
	
	
	
}
