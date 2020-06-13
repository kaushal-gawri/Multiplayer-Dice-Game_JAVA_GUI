package diceGame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import controller.AddPlayerToolBarAreaController;
import model.interfaces.GameEngine;

public class AddPlayerToolBarArea extends JPanel {
	
	private JButton addPlayerButton;
	private JTextField playerNameField;
	private JTextField playerIdField;
	private JTextField playerPointsField;
	private JComboBox <String> choosePlayer;
	private final String defaultPoints = "1000";
	
	/*
	 * Parameterized constructor responsible for instantiating different buttons, textfiled and JComboBox
	 * Also, adds the required components onto the panel
	 * */
	public AddPlayerToolBarArea(MainFrame mainFrame, GameEngine gameEngine) {
		choosePlayer = new JComboBox<>();
		this.setLayout(new FlowLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black,5));
		this.setPreferredSize(new Dimension(250,250));
		JPanel addPlayerPanel = new JPanel();

		GridLayout layout1 = new GridLayout(4,2);
		addPlayerPanel.setLayout(layout1);
		
		//Player name and nameFiled
		JLabel playerName = new JLabel("Enter Name:");
		playerNameField = new JTextField(12);
		addPlayerPanel.add(playerName);
		addPlayerPanel.add(playerNameField);
		
		//Player Id and IdField
		JLabel playerId = new JLabel("Enter Id: ");
		playerIdField = new JTextField(3);
		addPlayerPanel.add(playerId);
		addPlayerPanel.add(playerIdField);
		
		
		//Player points and points field
		JLabel initialPoints = new JLabel("Enter Initial Points: ");
		playerPointsField = new JTextField(defaultPoints,5);
		addPlayerPanel.add(initialPoints);
		addPlayerPanel.add(playerPointsField);
		
		
		//Button to add a player 
		addPlayerButton = new JButton("Add Player");
		addPlayerButton.setActionCommand("addPlayer"); 
		addListernerToFrame(mainFrame,gameEngine);
		addPlayerPanel.add(addPlayerButton);
		
		//combobox to display current player
		addPlayerPanel.add(choosePlayer);
		
		this.add(addPlayerPanel);
		addPlayerPanel.setVisible(true);
		
	}
	
	//Add listener to the Add PLayer Button 
	void addListernerToFrame(MainFrame mainFrame, GameEngine gameEngine) {
		addPlayerButton.addActionListener(new AddPlayerToolBarAreaController(mainFrame,gameEngine));
	}
	
	//Returns the text in the player name text field
	public String getPlayerNameTextField() {
		return this.playerNameField.getText();
	}
	
	//Returns the text in the player points text field
	 public String getPlayerPointsTextField() {
		 return this.playerPointsField.getText();	 
		 }
	 
	//Returns the text in the player id text field
	 public String getPlayerIdTextField() {
		 return this.playerIdField.getText();
	 }
	 
	//Sets the default points to 100 and name field to empty
	 public void deletePlayerValues() {
		 this.playerPointsField.setText("1000");
		 this.playerNameField.setText("");
	 }
	 
	 //clears all the text in the required fields and set initial points to be 1000
	 public void clearPlayerDetails() {
		 this.playerNameField.setText("");
		 this.playerPointsField.setText(defaultPoints);
		 this.playerIdField.setText("");
	 }

	 //returns the comboBox containing different players
	 public JComboBox<String> getChoosenPlayer(){
		 return choosePlayer;
	 }
}
