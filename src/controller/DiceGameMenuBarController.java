package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import diceGame.view.MainFrame;
import model.interfaces.GameEngine;
import model.interfaces.Player;

public class DiceGameMenuBarController implements ActionListener {

	private GameEngine gameEngine;
	private MainFrame mainFrame;
	
	/*
	 * Parameterized constructor which sets the value of the gameEngine and mainFrame
	 * */
	public DiceGameMenuBarController(MainFrame mainFrame, GameEngine gameEngine) {
		this.mainFrame = mainFrame;
		this.gameEngine = gameEngine;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch(e.getActionCommand()) {
		case "removePlayerFromGUI":
			  removePlayerFromGUI();
			  break;
	     
		case "removeAllPlayersFromGUI":
			  removeAllPlayersFromGUI();
			  break;
		}
	}
	
	/*
	 * Responsible for removing the player from the model after getting a confirmation from user
	 * Updates the GUI accordingly
	 * */
	private void removePlayerFromGUI() {
		 
		int i = mainFrame.getDiceGameSummaryPanel().getSelectedIndex();
		List<Player> playerList = new ArrayList<>(gameEngine.getAllPlayers());
		
		Player player = playerList.get(i);
		//basically checking for empty list and if not only a single player is added
		if(playerList.size() >0 && gameEngine.getAllPlayers().size()!=i) {
						int x = JOptionPane.showConfirmDialog(null, "Are you sure about removing the selected player?", "Confirm Removal", JOptionPane.YES_NO_OPTION);
						if(x==JOptionPane.YES_OPTION) {
							gameEngine.removePlayer(player);
							mainFrame.getDiceGameSummaryPanel().refreshPlayerList(gameEngine);
							mainFrame.getDiceGameAddPlayerDialog().getChoosenPlayer().removeItem(player.getPlayerName());
						}					
						}
		else {
            JOptionPane.showMessageDialog(null, "There are currently no players to remove from the player list.");
             return;
        }
		}
		
	/*
	 * Responsible for removing all the players from the model 
	 * Updates the GUI accordingly
	 * */
	private void removeAllPlayersFromGUI() {
		
		for(Player player: new ArrayList<>(gameEngine.getAllPlayers())) {
			gameEngine.removePlayer(player);
			mainFrame.getDiceGameAddPlayerDialog().getChoosenPlayer().removeItem(player.getPlayerName());
		}
		mainFrame.getDiceGameAddPlayerDialog().clearPlayerDetails();
		mainFrame.getDiceGameSummaryPanel().refreshPlayerList(gameEngine);
		
		
	}

	
	
}
