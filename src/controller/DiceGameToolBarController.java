package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import diceGame.view.MainFrame;
import model.interfaces.GameEngine;
import model.interfaces.Player;

public class DiceGameToolBarController extends KeyAdapter implements ActionListener{
	
	private final int  initialDelay = 100;
	private  final int finalDelay = 1000;
	private final int delayIncrement = 100;
	private final int initialDelay2 = 50;
	private final int finalDelay2 = 500;
	private final int delayIncrement2 = 50;
	
	private GameEngine gameEngine;
	private MainFrame mainFrame;
	
	
	
	public DiceGameToolBarController(MainFrame mainFrame, GameEngine gameEngine) {
		this.mainFrame = mainFrame;
		this.gameEngine = gameEngine;
	}
	
	public void keyPressed (KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_ENTER: mainFrame.getDiceGameToolBar().clickBetBtn();
				break;
		case KeyEvent.VK_RIGHT: mainFrame.getDiceGameToolBar().focusBetField();
				break;
		case KeyEvent.VK_ESCAPE:
		case KeyEvent.VK_TAB: mainFrame.getDiceGameSummaryPanel().askPlayerFocus();
				
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch(e.getActionCommand()) {
		case "placeGameBet": placeGameBet();
		break;
		case "rollTheDice": rollTheDice();
		break;
		case "nextPlayerTurn": nextPlayerTurn();
		break;
		case "nextRoundTurn": nextRoundTurn();
		break;
		case "cancelCurrentBet": cancelCurrentBet();
		break;
		}
		
	}

	/*
	 * On the start of next round, result are set as null on the model(backend)
	 * updates the GUI by clearing the player details
	 * */
	private void nextRoundTurn() {
		
		mainFrame.getDiceGameSummaryPanel().updateSummaryPanel(mainFrame, gameEngine, 0, null);
		int i = mainFrame.getDiceGameSummaryPanel().getSelectedIndex();
		List <Player> playerList = new ArrayList<>(gameEngine.getAllPlayers());
		Player player = playerList.get(i);
		player.setResult(null);
		player.setBet(0);
		mainFrame.getDiceGameSummaryPanel().setSelectedPlayer(0);
		mainFrame.getDiceGameSummaryPanel().refreshPlayerList(gameEngine);
		mainFrame.getDiceGameStatusBar().updateStatusBar(mainFrame, gameEngine);
		mainFrame.getDiceGameToolBar().setNextRoundEnabled(false);
		
	}
	
	/*
	 * Places the bet for the selected player on the back end
	 * Updates the GUI and focuses on the required buttons and the selected player
	 * */
	private void placeGameBet() {
		
		int i = mainFrame.getDiceGameSummaryPanel().getSelectedIndex();
		List<Player> playerList = new ArrayList<>(gameEngine.getAllPlayers());
		Player player = playerList.get(i);
		mainFrame.getDiceGameStatusBar().updateStatusBar(mainFrame, gameEngine);
		try {
			String empty = "";
			int newBet = Integer.parseInt(mainFrame.getDiceGameToolBar().getBetText());
			gameEngine.placeBet(player, newBet);
			mainFrame.getDiceGameStatusBar().updateStatusBar(mainFrame, gameEngine);
			if(empty.equals(mainFrame.getDiceGameToolBar().getBetText())) {
				throw new IllegalArgumentException("Error: Bet cannot be empty");
			}
			
			if(player.getPoints()<1) {
				mainFrame.getDiceGameToolBar().setBetInputEnabled(true);
				throw new IllegalArgumentException("Error: Player " + player.getPlayerName() + "doesn't have any points. Please remove him/her to continue");
			}
			
			if(newBet<=0) {
				mainFrame.getDiceGameToolBar().setBetInputEnabled(true);
				mainFrame.getDiceGameToolBar().setRollDiceButton(false);
				throw new IllegalArgumentException("Error: Bet should be greater than zero");
				
			}
			if(!gameEngine.placeBet(player, newBet)) {
				mainFrame.getDiceGameToolBar().setBetInputEnabled(true);
				throw new IllegalArgumentException("Error: Player doesn't have enough points to place a bet" );
			}
			mainFrame.getDiceGameToolBar().setBetInputEnabled(false);
			mainFrame.getDiceGameToolBar().setRollDiceButton(true);
			mainFrame.getDiceGameToolBar().setCancelButton(true);
			mainFrame.getDiceGameStatusBar().updateStatusBar(mainFrame, gameEngine);
			mainFrame.getDiceGameSummaryPanel().askPlayerFocus();
			mainFrame.getDiceGameToolBar().setBetInputEnabled(false);
			
		}
		catch(Exception e){
			//TODO
		}
	}
	
	/*
	 * Shifts the focus on the next player in the list on summary panel
	 * If only one player nothing happens
	 * This selects the nextPlayer and the required bet can be placed accordingly
	 * */
	private void nextPlayerTurn() {
		try {
		int i = mainFrame.getDiceGameSummaryPanel().getSelectedIndex() + 1;
		List<Player> playerList = new ArrayList<>(gameEngine.getAllPlayers());
		Player player = playerList.get(i);
		mainFrame.getDiceGameSummaryPanel().setSelectedPlayer(i);
		mainFrame.getDiceGameStatusBar().updateStatusBar(mainFrame, gameEngine);
		if(playerList.size()==1) {
			throw new IndexOutOfBoundsException("Error: Only a single player added");
		}
		}
		catch(Exception e) {
			//TODO
		}
		
	}
	
	/*
	 * Roll the dice for the selected player on the current thread
	 * Calls the roll method in the panel which rolls 2 dices on two separate thread and calls the respective timer
	 * Repaint is done accordingly
	 * */
	private void rollTheDice() {
		
		new Thread(() -> {
			
			int i = mainFrame.getDiceGameSummaryPanel().getSelectedIndex();
			List<Player> playerList = new ArrayList<>(gameEngine.getAllPlayers());
			Player player = playerList.get(i);
		//	Player player = mainFrame.getDiceGameSummaryPanel().getSelectedPlayer(gameEngine);
			int newBet = Integer.parseInt(mainFrame.getDiceGameToolBar().getBetText());
			if(player.getBet()!=0) {
				//player.setBet(newBet);
			gameEngine.rollPlayer(player, initialDelay, finalDelay, delayIncrement, initialDelay2, finalDelay2, delayIncrement2);
			mainFrame.getDiceGamePanel().roll(player.getResult(),initialDelay, finalDelay, delayIncrement, initialDelay2, finalDelay2, delayIncrement2);
			}			
			if(mainFrame.getDiceGameSummaryPanel().allPlayersRolled(gameEngine)) {
				gameEngine.rollHouse(initialDelay, finalDelay, delayIncrement, initialDelay2, finalDelay2, delayIncrement2);
				mainFrame.getDiceGameToolBar().setBetInputEnabled(false);
			}
		    mainFrame.getDiceGameToolBar().setBetInputEnabled(false);
			mainFrame.getDiceGameToolBar().setRollDiceButton(false);
			mainFrame.getDiceGameToolBar().setNextPlayerEnabled(true);
			mainFrame.getDiceGameToolBar().setNextRoundEnabled(true);
			mainFrame.getDiceGameStatusBar().updateStatusBar(mainFrame, gameEngine);
		}).start();
		
		mainFrame.getDiceGameToolBar().setBetInputEnabled(false);
		
	}
	
	/*
	 * Sets the current bet to zero which basically is the key here
	 * */
	private void cancelCurrentBet() {
		mainFrame.getDiceGameToolBar().setRollDiceButton(false);
		int i = mainFrame.getDiceGameSummaryPanel().getSelectedIndex();
		List<Player> playerList = new ArrayList<>(gameEngine.getAllPlayers());
		Player player = playerList.get(i);
		player.resetBet();
	
		mainFrame.getDiceGameToolBar().setBetInputEnabled(true);
		mainFrame.getDiceGameToolBar().setCancelButton(false);
	}
	
}
