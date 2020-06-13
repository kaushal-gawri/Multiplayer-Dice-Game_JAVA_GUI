package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
//import java.util.Timer;

import javax.swing.Timer;

import diceGame.view.MainFrame;

public class DiceGamePanelController extends ComponentAdapter implements ActionListener{

	//delay initially was 125
	private Timer timer;
	private final int delay = 100;
	private MainFrame mainframe;
	
	/*
	 * Parameterized constructor which sets the value of the mainFrame
	 * */
	public DiceGamePanelController(MainFrame mainFrame) {
		this.mainframe = mainFrame;
		timer = new Timer(delay,this);
	}
	

	@Override
	public void componentResized(ComponentEvent e) {
		//It will just restart the timer if it is already running
		if(timer.isRunning())
			timer.restart();
		else
			timer.start();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		mainframe.getDiceGamePanel().scaleDice();
		timer.stop();
		
	}
	
}
