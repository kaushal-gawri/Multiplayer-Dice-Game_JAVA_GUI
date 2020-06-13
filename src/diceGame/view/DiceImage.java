package diceGame.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.DieImpl;
import model.GameEngineImpl;
import model.SimplePlayer;
import model.interfaces.Die;
import model.interfaces.GameEngine;
import model.interfaces.Player;

public class DiceImage extends JPanel {
	
	private static final long serialVersionUID = 1L;
		public int value;
	 
	      public DiceImage() {
	    	  Die die1 = new DieImpl(1);
	    	  GameEngine engine = new GameEngineImpl();
	    	  Player player = new SimplePlayer("1","kaushal",1000);
	    	  value = 5;
	    	  System.out.println(value);
	          repaint();
	      }
	      
	      @Override
	      public void paintComponent(Graphics g) {
	    	  drawDie(g,10,10);
	    	  drawDie(g,150,150);
	      }
	 
	     public void rollValue(int maxValue) {
	         double tempValue = Math.random() * maxValue;
	         value = (int) Math.floor( tempValue ) + 1;
	     }
	
	     public void drawDie(Graphics screen, int x, int y) {
	         screen.setColor(Color.red);
	         screen.fillRoundRect(x, y, 100, 100, 20, 20);
	         screen.setColor(Color.black);
	         screen.drawRoundRect(x, y, 100, 100, 20, 20);
	         screen.setColor(Color.white);
	         if (value > 1) {
	             screen.fillOval(x+5, y+5, 20, 20);
	             screen.fillOval(x+75, y+75, 20, 20);
	         }
	         if (value > 3) {
	             screen.fillOval(x+75, y+5, 20, 20);
	             screen.fillOval(x+5, y+75, 20, 20);
	         }
	         if (value == 6) {
	             screen.fillOval(x+5, y+40, 20, 20);
	             screen.fillOval(x+75, y+40, 20, 20);
	         }
	         if (value % 2 == 1) {
	             screen.fillOval(x+40, y+40, 20, 20);
         }
	     }
	     
	     public static void main(String[] args) {
	    	 JFrame newFrame = new JFrame();
	    	 newFrame.add(new DiceImage());
	    	 newFrame.setVisible(true);
	    	 
	     }
	     

}


