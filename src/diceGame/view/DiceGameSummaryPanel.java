package diceGame.view;

import java.awt.Dimension;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import controller.DiceGameSummaryPanelController;

import java.util.ArrayList;
import java.util.List;

import model.GameEngineImpl;
import model.interfaces.DicePair;
import model.interfaces.GameEngine;
import model.interfaces.Player;

public class DiceGameSummaryPanel extends JSplitPane {
private static final long serialVersionUID = 1L;
	
	private DefaultListModel<String> textArea;
	private JList<String> playerList;
	private JTextArea gameDetails;
	GameEngine game = new GameEngineImpl();
	
	
	/*
	 * Paramterized constructor which sets the orientation of the JSPlitPane used in the summary panel
	 * Used a scroll bar to display the results of the players
	 * */
	public DiceGameSummaryPanel(MainFrame mainFrame, GameEngine gameEngine) {
		this.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.setMinimumSize(new Dimension(175,0));
		
		textArea = new DefaultListModel<>();
		playerList = new JList<>(textArea);
		playerList.setVisibleRowCount(5);
		playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playerList.setFont(new Font(null, Font.ITALIC, 16));
		playerList.requestFocus();
		playerList.setFocusTraversalKeysEnabled(false);
		
		gameDetails = new JTextArea();
		gameDetails.setEditable(false);
		gameDetails.setFocusable(false);
		gameDetails.setFont(new Font(null, Font.PLAIN, 14));
		
		/* Disable focus */
		this.setFocusable(false);
		
		this.add(playerList, JSplitPane.TOP);
		this.add(new JScrollPane(gameDetails), JSplitPane.BOTTOM);
		addListeners(mainFrame,gameEngine);
		
		/*House already added to the list*/
		textArea.addElement("House");
		playerList.setSelectedIndex(0);
	}
	
	//adds listener to the player List
	void addListeners(MainFrame mainFrame, GameEngine gameEngine) {
		DiceGameSummaryPanelController listener = new DiceGameSummaryPanelController(mainFrame, gameEngine);
		playerList.addListSelectionListener(listener);
		playerList.addKeyListener(listener);
	}
	
	/* Updates player list in side bar */
	public void refreshPlayerList(GameEngine gameEngine) {
		textArea.removeAllElements();
		addPlayersToList(gameEngine);
		setSelectedPlayer(0);
		resetToPreferredSizes();
	}
	
	/* Updates game details panel in summary Panel */
	public void updateSummaryPanel(MainFrame mainFrame, GameEngine gameEngine, int houseResult, Player player) {
		/* If player is selected */
		if (playerIsSelected(gameEngine.getAllPlayers().size())) {
			//Details printed using the callbackGUI
		/* If house is selected */
		} else {
			printHouseDetails(mainFrame, gameEngine);
			gameDetails.append("\n");
		}
		printResults(mainFrame, gameEngine,player, houseResult);
	}
	
	//clears the summary panel
	//basically used for every new round
	private void clearGameDetails() {
		gameDetails.setText("");
	}
	
	//sets the index of the selectedplayer
	public void setSelectedPlayer(int index) {
		playerList.setSelectedIndex(index);
	}
	
	//returns the index of the selected player
	public int getSelectedIndex() {
		return playerList.getSelectedIndex();
	}
	
	/* Player is selected when the last index is not selected (house)
	 * and when there is at least one player (not -1) */
	public boolean playerIsSelected(int playerCount) {
		return playerList.getSelectedIndex() != playerCount
				&& playerList.getSelectedIndex() != -1;
	}
	
	/*Returns the player at the selected index*/
	public Player getSelectedPlayer(GameEngine gameEngine) {
		return gameEngine.getPlayer(playerList.getSelectedValue());
	}
	
	/* Checks if all players present in game have rolled
	 * is used to let the house know when to roll
	 *  */
	public boolean allPlayersRolled(GameEngine gameEngine) {
		for (Player search : gameEngine.getAllPlayers()) {
			if(search.getResult() ==null) {
				return false;
			}
			else {
				return true;
			}
		}
		return true;	
	}
	
	/* Prints house details to text area in UI when a round is completed*/
	/* After round end results printed through callbackGUI*/
	private void printHouseDetails(MainFrame mainFrame, GameEngine gameEngine) {
		clearGameDetails();
		String [] lines = {
			"Name: House"
		};
		appendSummaryPanel(lines);
	}
	
	/* Prints results after house has rolled 
	 * Final results of win/loss displayed for all the players
	 */
	private void printResults(MainFrame mainFrame, GameEngine gameEngine, Player player, int houseResult) {
		String [] lines;
		
		for (Player players : gameEngine.getAllPlayers()) {
			String result;
			int playerRoll = mainFrame.getDiceGamePanel().getDiceTotal(players.getResult());
			if(playerRoll>houseResult) {
				result = "Won The Game!";
			}
			else if(playerRoll==houseResult) {
				result = "Game Drawn!";
			}
			else {
				result = "Lost The Game!";
			}
			lines = new String[] {
				"Player " + players.getPlayerId() + ": " + players.getPlayerName()
				+ "\n" + "Dice Total: " +  playerRoll + "\nBet: " + players.getBet()
				+ "\nPoints: " + players.getPoints() + "\n" + result + "\n"};
			
			appendSummaryPanel(lines);
		}
		
}
	
	/* Write string in array to Game Details 
	 * to update the summary panel */
	private void appendSummaryPanel(String [] lines) {
		for (String line : lines) {
			gameDetails.append(line + "\n");
		}
	}
	
	/* Appends players to player selection list */
	private void addPlayersToList(GameEngine gameEngine) {
		for (Player player : gameEngine.getAllPlayers()) {
			String details = "Player " + player.getPlayerId() 
					+ " - " + player.getPlayerName();
			textArea.addElement(details);		
		}
		/*Adds the house to the panel on the list*/
		textArea.addElement("House");
	}
	
	/* Set focus on player JList */
	public void askPlayerFocus() {
		playerList.requestFocus();
	}
	
	/*prints the details to the summary panel*/
	public void printDetails(String text) {
		String[] details = {text + "\n"};
		appendSummaryPanel(details);
	}
		
}

	
