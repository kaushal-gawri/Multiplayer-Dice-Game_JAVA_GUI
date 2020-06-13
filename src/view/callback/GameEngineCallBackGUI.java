package view.callback;

import javax.swing.SwingUtilities;

import diceGame.view.MainFrame;
import model.interfaces.DicePair;
import model.interfaces.Die;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.interfaces.GameEngineCallback;

public class GameEngineCallBackGUI implements GameEngineCallback {

	private MainFrame mainFrame;
	public GameEngineCallBackGUI(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	@Override
	public void houseDieUpdate(Die die, GameEngine gameEngine) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(() ->{
			
			if(!mainFrame.getDiceGameSummaryPanel().playerIsSelected(gameEngine.getAllPlayers().size())) {
				String houseResult = "Die" + die.getNumber() + "is Rolled to" + die.getValue();
				mainFrame.getDiceGamePanel().updateDiePanel(mainFrame, die);
			}
		});
	}

	@Override
	public void houseResult(DicePair dicePair, GameEngine gameEngine) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(() -> {
			/* Ensures no additional players have been added before reporting
			 * final results */
			int house = dicePair.getDie1().getValue() + dicePair.getDie2().getValue();
			if (mainFrame.getDiceGameSummaryPanel().allPlayersRolled(gameEngine)) {
				
				/* Update GUI is house is selected */
				if (!mainFrame.getDiceGameSummaryPanel().playerIsSelected(
						gameEngine.getAllPlayers().size())) {
					mainFrame.getDiceGamePanel().updatePanel(mainFrame, dicePair);
					mainFrame.getDiceGameStatusBar().updateStatusBar(mainFrame, gameEngine);
				}
			}
			/* Updates toolbar on any screen */
			mainFrame.getDiceGameToolBar().updateToolBarStatus(mainFrame, gameEngine);
			String houseResult = "House Result= " +"Die 1: " + dicePair.getDie1().getValue()+ " Die 2: " + dicePair.getDie2().getValue() + " Total : " + dicePair.getTotal();

			mainFrame.getDiceGameSummaryPanel().updateSummaryPanel(mainFrame, gameEngine,house,null);
			mainFrame.getDiceGameSummaryPanel().printDetails(houseResult);
		    
		
		});
		
	}

	@Override
	public void playerDieUpdate(Player player, Die die, GameEngine gameEngine) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(() -> {
			/* If a player is selected (not house) */
			if (mainFrame.getDiceGameSummaryPanel().playerIsSelected(gameEngine.getAllPlayers().size())) {
				/* If the player selected is the one rolling */
				if (player.equals(mainFrame.getDiceGameSummaryPanel().getSelectedPlayer(gameEngine))) {
			
					mainFrame.getDiceGamePanel().updateDiePanel(mainFrame, die);
					
					
				}
			}
		});
	}

	@Override
	public void playerResult(Player player, DicePair dicePair, GameEngine gameEngine) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(()->{
			if(mainFrame.getDiceGameSummaryPanel().playerIsSelected(gameEngine.getAllPlayers().size())){
				int result = dicePair.getDie1().getValue() + dicePair.getDie2().getValue();
				
			if(player.equals(mainFrame.getDiceGameSummaryPanel().getSelectedPlayer(gameEngine))) {
			mainFrame.getDiceGameSummaryPanel().updateSummaryPanel(mainFrame, gameEngine,result,player);
		}
		else {
			
		}
			} else {
				mainFrame.getDiceGameToolBar().updateToolBarStatus(mainFrame, gameEngine);
			    mainFrame.getDiceGameStatusBar().updateStatusBar(mainFrame, gameEngine);
			    
			}
			String playerResult = player.toString();
			mainFrame.getDiceGameSummaryPanel().printDetails(playerResult);
		});
	}

}
