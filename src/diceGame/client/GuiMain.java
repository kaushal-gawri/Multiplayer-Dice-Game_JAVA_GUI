package diceGame.client;

import java.awt.BorderLayout;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import diceGame.view.AddPlayerToolBarArea;
import diceGame.view.DiceGameToolBar;
import model.GameEngineImpl;
import view.GameEngineCallbackImpl;
import view.callback.GameEngineCallBackGUI;
import model.interfaces.GameEngine;
import diceGame.view.MainFrame;
//import view.observers.GameEngineCallbackGUI;
//import view.observers.GameEngineCallbackImpl;

public class GuiMain {

//	public static void main(String[] args) {
//		
//		
////		JFrame frame = new JFrame("Dice GUI");
////		//frame.add(new AddPlayerToolBarArea());
////		frame.setLayout(new BorderLayout());
////		frame.add(new AddPlayerToolBarArea(), BorderLayout.EAST);
////		frame.add(new DiceGameToolBar(), BorderLayout.NORTH);
////		frame.setVisible(true);
//		
//		MainFrame mainFrame = new MainFrame();
//		mainFrame.setVisible(true);
////	//	frame.setLayout(new BorderLayout());
////		//frame.add(new DiceGameToolBar(), BorderLayout.EAST);
//////		frame.add(new DiceGameToolBar(), BorderLayout.WEST);
//////		frame.setDefaultCloseOperation(0);
//////		frame.setVisible(true);
////		//frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
////		
////		//new DiceGameToolBar();
////		frame.add(new DiceGameToolBar());
////		
////		frame.setVisible(true);
////		JSplitPane centrePane = new JSplitPane();
//		/* Model */
////		final GameEngine gameEngine = new GameEngineImpl();
////		
////		SwingUtilities.invokeLater(() -> {
////			/* View */
////			final MainFrame mainFrame = new MainFrame();
////			/* Controllers */
////		//	mainFrame.addListeners(gameEngine);
////			/* GUI Observer/Callback */
////			//gameEngine.addGameEngineCallback(new GameEngineCallbackGUI(mainFrame));
////		});
//		
//		/* Observer/Callback */
//		//gameEngine.addGameEngineCallback(new GameEngineCallbackImpl());
//	}
	public static void main(String[] args) {
		final GameEngine gameEngine = new GameEngineImpl();
		
		SwingUtilities.invokeLater(() -> {
			/* View */
			final MainFrame mainFrame = new MainFrame();
			/* Controllers */
			mainFrame.addListeners(gameEngine);
			/* GUI Observer/Callback */
			gameEngine.addGameEngineCallback(new GameEngineCallBackGUI(mainFrame));
		});
		
		/* Observer/Callback */
		gameEngine.addGameEngineCallback(new GameEngineCallbackImpl());


	

	}
	
}
