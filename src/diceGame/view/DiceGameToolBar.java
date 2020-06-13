package diceGame.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import controller.DiceGameToolBarController;
import model.interfaces.GameEngine;
import model.interfaces.Player;


public class DiceGameToolBar extends JToolBar {

	private JTextField gameBetField;
	private JButton placeBetButton, rollDiceButton, nextPlayerButton, nextRoundButton, cancelBetButton;
	private final int sizeIcon = 40;
	
	
	/*
	 * Parameterized constructor used to make the JButtons for roll, bet, cancelbet, nextPlayer and nextRound
	 * Used for making the textField for the bet
	 * Sets the layout of the toolbar with particular dimension
	 * */
	public DiceGameToolBar(MainFrame mainFrame, GameEngine gameEngine) {
	JLabel title = new JLabel("My Dice Game");
	title.setFont(new Font(null, Font.BOLD, 24));
	title.setFocusable(false);
	
	/* Title */
	this.addSeparator(new Dimension(5, 0));
	this.add(title);
	
	/* Roll dice button */
	rollDiceButton = new JButton("Roll Dice");
	rollDiceButton.setActionCommand("rollTheDice");
	//makeButton(rollDiceButton,"Roll Dice", "rollDice");
	setRollDiceButton(false);
	
	/* Next player button*/
	nextPlayerButton = new JButton("Next Player");
	nextPlayerButton.setActionCommand("nextPlayerTurn");
	//makeButton(nextPlayerButton, "Next PLayer", "nextPlayer");
	setNextPlayerEnabled(false);
	
	/* Next round button*/
	nextRoundButton = new JButton("Next Round");
	nextRoundButton.setActionCommand("nextRoundTurn");
	//makeButton(nextRoundButton, "Next Round", "nextRound");
	setNextRoundEnabled(false);
	
	/* Enter bet field */
	gameBetField = new JTextField("");
	gameBetField.setEditable(false);
	gameBetField.setMinimumSize(new Dimension(40, 25));
	gameBetField.setMaximumSize(new Dimension(250, 25));
	gameBetField.setFont(new Font(null, Font.PLAIN, 14));
	gameBetField.setHorizontalAlignment(SwingConstants.CENTER);
	gameBetField.setFocusTraversalKeysEnabled(false);
//	makeBetField();
	
	
	/* Make bet button */
	placeBetButton = new JButton("Place bet");
	placeBetButton.setActionCommand("placeGameBet");
	//makeButton(placeBetButton,"Place bet", "placeBet");
	setBetInputEnabled(true);
	//setBetInputEnabled(false);
	
	cancelBetButton = new JButton("Cancel bet");
	cancelBetButton.setActionCommand("cancelCurrentBet");
	
	
	/* Disable focus */
	this.setFocusable(false);
	
	this.addSeparator(new Dimension(60, 0));
	this.add(rollDiceButton);
	this.add(nextPlayerButton);
	this.add(nextRoundButton);
	this.add(gameBetField);
	this.add(placeBetButton);
	this.add(cancelBetButton);
	
	addListeners(mainFrame,gameEngine);
	
	//addButtonsToToolBar();
	
}
	/*adds listener to specific buttons and textfield*/
	void addListeners(MainFrame mainFrame, GameEngine gameEngine) {
		//TODO
		DiceGameToolBarController listener = new DiceGameToolBarController(mainFrame,gameEngine);
		rollDiceButton.addActionListener(listener);
		nextPlayerButton.addActionListener(listener);
		nextRoundButton.addActionListener(listener);
		gameBetField.addKeyListener(listener);
		placeBetButton.addActionListener(listener);
		cancelBetButton.addActionListener(listener);
		//setActiveButton(mainFrame,gameEngine);
	}
	
	//adds button onto the toolbar
	private void addButtonsToToolBar() {
		this.add(gameBetField);
		this.add(placeBetButton);
		this.add(rollDiceButton);
		this.add(nextPlayerButton);
		this.add(nextRoundButton);
	}
	
	//used to make the betField
	private void makeBetField() {
		this.gameBetField = new JTextField("");
		this.gameBetField.setEditable(false);
		this.gameBetField.setMinimumSize(new Dimension(40, 25));
		this.gameBetField.setMaximumSize(new Dimension(250, 25));
		this.gameBetField.setFont(new Font(null, Font.PLAIN, 14));
		this.gameBetField.setHorizontalAlignment(SwingConstants.CENTER);
		this.gameBetField.setFocusTraversalKeysEnabled(false);
		this.add(gameBetField);
	}
	
	//used to make different set of buttons
	private void makeButton(JButton button,String s1, String s2) {
		button = new JButton(s1);
		button.setActionCommand(s2);
		this.add(button);
	}
	
	/*Enables the button for place bet*/
	public void setBetInputEnabled(boolean enable) {
		placeBetButton.setEnabled(enable);
		placeBetButton.setFocusable(enable);
		gameBetField.setEditable(enable);
		gameBetField.setFocusable(enable);
	}
		
	/*makes the button clickable*/
	public void clickBetBtn() {
		placeBetButton.doClick();
	}
	
	/*Enables the roll button*/
	public void setRollDiceButton(boolean enable) {
		rollDiceButton.setEnabled(enable);
		rollDiceButton.setFocusable(enable);
		
	}
	/* Enables the cancel button*/
	public void setCancelButton(boolean enable) {
		cancelBetButton.setEnabled(enable);
		cancelBetButton.setFocusable(enable);
	}
	
	/*returns the text for the bet*/
	public String getBetText() {
		return gameBetField.getText();
	}
	
	/*makes the focus on the betField*/
	public void focusBetField() {
		gameBetField.requestFocus();
	}
	
	/*Enables next Round button*/
	public void setNextRoundEnabled(boolean enable) {
		nextRoundButton.setEnabled(enable);
		nextRoundButton.setFocusable(enable);
	}

	/*Enables the next Player button*/
	public void setNextPlayerEnabled(boolean enable) {
		nextPlayerButton.setEnabled(enable);
		nextPlayerButton.setFocusable(enable);
	}
	
	/*Updates the status of the tool bar according to the players status of bet placed and result generated*/
	public void updateToolBarStatus(MainFrame mainFrame, GameEngine gameEngine) {
		int i = mainFrame.getDiceGameSummaryPanel().getSelectedIndex();
		List<Player> playerList = new ArrayList<>(gameEngine.getAllPlayers());
		Player player = playerList.get(i);
		if(mainFrame.getDiceGameSummaryPanel().playerIsSelected(gameEngine.getAllPlayers().size())) {
			if(player.getBet()==0) {
				setBetInputEnabled(true);
			}
			if(player.getBet()!=0 && player.getResult()==null) {
				setRollDiceButton(true);
			}
			if(player.getResult()!=null) {
				setNextPlayerEnabled(true);
			}
			if(player.getBet()!=0) {
				gameBetField.setText(String.valueOf(player.getBet()));
				
			}
			else {
				gameBetField.setText("100");
			}		
		}
		else {
			rollDiceButton.setEnabled(playerList.size()>0 && mainFrame.getDiceGameSummaryPanel().allPlayersRolled(gameEngine));
			setBetInputEnabled(false);
			nextPlayerButton.setEnabled(true);
			gameBetField.setText("");
		}
	}
	


	
}