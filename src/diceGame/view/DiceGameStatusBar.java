package diceGame.view;

import java.awt.Font;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import model.interfaces.GameEngine;
import model.interfaces.Player;

public class DiceGameStatusBar extends JToolBar{
	
	private static final long serialVersionUID = 1L;
	private JLabel message;
	private String initializeLabel = "Please Add Players to Begin";
	private final String delimiter = ":";
	private String[] messageArray = {
			"Bet needs to be Placed for current Player :.",
			"Player : is ready to roll the Die as required.",
			"Player : has rolled the Die as required.",
			"Waiting for all the players to roll the Die.",
			"House is ready to roll the Die.",
			"Round Completed. Start the new Round."
	};
	
	/*
	 * Default constructor used to set the initial label for the status bar
	 * and the fonts
	 * adds the label on the extended ToolBar(a.k.a status bar)
	 * */
	public DiceGameStatusBar() {
		message = new JLabel(initializeLabel, JLabel.CENTER);
		message.setFont(new Font("Arial", Font.ITALIC, 16));
		this.add(message);	
	}
	
	/*
	 * Updated the status of the status bar
	 * Depending upon soem factors including if a player placed bet, if a player rolled the dice etc.
	 * */
	public void updateStatusBar(MainFrame mainFrame, GameEngine engine) {
		SwingUtilities.invokeLater(() -> {
			clearLabel();
			if(engine.getAllPlayers().size()==0) {
					message.setText(initializeLabel);
			}
			else if(mainFrame.getDiceGameSummaryPanel().playerIsSelected(engine.getAllPlayers().size())) {
				int i = mainFrame.getDiceGameSummaryPanel().getSelectedIndex();
				List<Player> pList = new ArrayList<>(engine.getAllPlayers());
				Player currentPlayer = pList.get(i);
//				System.out.println(currentPlayer.getPlayerName());
				int value =0;
				if(currentPlayer.getBet()==0 && currentPlayer.getResult()==null) {
//					System.out.println("One");
					value =0;
				}
				else if(currentPlayer.getResult()==null) {
//					System.out.println("Two");
					value =1;
				}
				else{
//					System.out.println("Three");
					value = 2;
				}
				String[] myMessage = messageArray[value].split(delimiter);
				message.setText(myMessage[0] + currentPlayer.getPlayerName() + myMessage[1]);
			}
			else
			{
				int value =0;
				if(!mainFrame.getDiceGameSummaryPanel().allPlayersRolled(engine)) {
					value = 3;
				}
				else {
					value =5;
				}
				message.setText(messageArray[value]);
			}
		});
			
	}
	
	//Clears the label on the status bar to be empty
	public void clearLabel() {
		message.setText("");
	}
	
	
}


