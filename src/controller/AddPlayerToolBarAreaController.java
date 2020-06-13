package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import diceGame.view.MainFrame;
import model.SimplePlayer;
import model.interfaces.GameEngine;

public class AddPlayerToolBarAreaController implements ActionListener {

	private GameEngine gameEngine;
	private MainFrame mainFrame;
	
	/*
	 * Parameterized constructor which sets the value of the gameEngine
	 * */
	public AddPlayerToolBarAreaController(MainFrame mainFrame, GameEngine gameEngine) {
		this.mainFrame = mainFrame;
		this.gameEngine = gameEngine;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("addPlayer")) {
			addPLayerToBackend();
			
		}
	}
	
	/*
	 * Adds player on the backend of our model
	 * updates the GUI accordingly using specific update method
	 * */
	private void addPLayerToBackend() {
		try {
			String playerId = mainFrame.getDiceGameAddPlayerDialog().getPlayerIdTextField();
			String playerName = mainFrame.getDiceGameAddPlayerDialog().getPlayerNameTextField();
			String playerPoints = mainFrame.getDiceGameAddPlayerDialog().getPlayerPointsTextField();
			int points = Integer.parseInt(playerPoints);
			

			if("".equals(playerId)) {
				throw new IllegalArgumentException("Please enter valid player ID");
			}
			if("".equals(playerName)) {
				throw new IllegalArgumentException("Please enter valid player name");
			}
			if(points == 0) {
				throw new IllegalArgumentException("Invalid Points, Should be greater than zero");
				
			}
			gameEngine.addPlayer(new SimplePlayer(playerId,playerName,points));
			mainFrame.getDiceGameAddPlayerDialog().clearPlayerDetails();
			
			//Add to jcombo
			mainFrame.getDiceGameAddPlayerDialog().getChoosenPlayer().addItem(playerName);
			
			mainFrame.getDiceGameSummaryPanel().refreshPlayerList(gameEngine);
			mainFrame.getDiceGameStatusBar().updateStatusBar(mainFrame, gameEngine);
		}
		catch(IllegalArgumentException e) {
		   //JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
		}
	}
	
}








