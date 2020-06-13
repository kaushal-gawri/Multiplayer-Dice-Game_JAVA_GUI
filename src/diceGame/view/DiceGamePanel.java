package diceGame.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.DiceGamePanelController;
import model.DicePairImpl;
import model.DieImpl;
//import controller.DicePanelController;
import model.interfaces.DicePair;
import model.interfaces.Die;
import diceGame.view.MainFrame;

public class DiceGamePanel extends JPanel {
	
	
	private static final long serialVersionUID = 1L;
    private Timer timer;
	private ImageIcon diceIcon1, diceIcon2;
	//private JLabel diceLabel1, diceLabel2, diceTotal;
	int diceSide1, diceSide2, diceSide3, diceSide4, diceSide5, diceSide6;
	private final int spacing = 10;
	private final String defaultTotalText = "Waiting to Roll";
	private final String diceImages[] = {"res/dice_0.png", "res/dice_1.png", "res/dice_2.png",
		"res/dice_3.png", "res/dice_4.png", "res/dice_5.png", "res/dice_6.png"};

	int die;
	DicePair dicePair = new DicePairImpl();
	/*
	 * Parameterized constructor responsible for setting initial values of different ints
	 * Also, sets the layout for how dice should be drawn
	 * Also, adds the listeners
	 * */
	DiceGamePanel(MainFrame mainFrame) {
		
		diceSide1 = 1;
		diceSide2 = 0;
		diceSide3 = 0;
		diceSide6 = 6;
		die=0;
		/* Layout */
		this.setLayout(new GridBagLayout());
		/* Not focusable */
		this.setFocusable(false);
		addListeners(mainFrame);
	}
	
	/* Adds a listener for panel resizing */
	void addListeners(MainFrame mainFrame) {
		this.addComponentListener(new DiceGamePanelController(mainFrame));
	}
	
	/* Updates the rolling dice panel */
	public void updatePanel(MainFrame mainFrame, DicePair dicePair) {
		
		if (dicePair != null) {
			updateDice(dicePair.getDie1().getValue(), dicePair.getDie2().getValue());
			mainFrame.getDiceGameToolBar().setRollDiceButton(false);
		} else {
			updateDice(1, 6);
		}
	}
	
	public void updateDiePanel(MainFrame mainFrame, Die die) {
		
		if(die!=null) {
			updateDice(die.getValue(),die.getValue());
			mainFrame.getDiceGameToolBar().setRollDiceButton(false);
			
		}
		else {
			updateDice(0,0);
		}
		
	}
	
	public String getDieResult(Die die) {
		return "Die :" + die.getValue();
	}
	
	/* Changes the displayed dice drawin
	 * using the required timer
	 * repaints onto the panel
	 * */
	private void updateDice(int dice1, int dice2) {

		if(timer!=null) {
			return;
		}
		timer = new Timer(100,new ActionListener() {
			int i =1 ;
			public void actionPerformed(ActionEvent e) {
				diceSide2 = (int)(Math.random()*6) + 1;
				diceSide3 = (int)(Math.random()*6) + 1;
				die =1 ;
				repaint();
				i++;
				if(i==10) {
					timer.stop();
					timer = null;
					die=0;
				}
			}
		});
		timer.start();
		this.diceSide2=0;
		this.diceSide3=0;
		scaleDice();
	}
	
	/* Used it to Scaled the dice images depending on the panel size */
	/* Until we were told to draw the die*/
	/* Now it does re-draws the dice by invoking repaint()*/
	public void scaleDice() {
		this.die=0;
		repaint();
		
	}
	
	/* Calculates and returns the dice total */
	/* Not using it anymore as now the images ain't used*/
	int getDiceTotal(DicePair dicePair) {
		return dicePair.getDie1().getValue() + dicePair.getDie2().getValue();
	}
	
	 @Override
     public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 g.setColor(Color.blue);
		 		 
		 if(this.die==1) {
			 drawDie(this.diceSide2,g,30,110);
			 drawDie(this.diceSide3,g,200,110);
		 }
		 else {
		 drawDie(this.diceSide1,g,30,110);
		 drawDie(this.diceSide6,g,200,110);
		 }		 
     }
	
	/*
	 * This method is responsible for drawing teh die on the panel
	 * Different combinations of coordinates used to get the required coordinates for the rectangle(drawn as a square for die)
	 * and the small spheres
	 * The toughest part of the assignment
	 * Tried hard to achive it as preciseley as i can
	 * */
	   public void drawDie(int value, Graphics screen, int x, int y) {
	        
		     int originalwidth = 300;
		     int originalheight = 300;
		     Dimension size = this.getBounds().getSize();

		     screen.setColor(Color.green);
		     int rectWidth = 75;
		     int rectHeight = 75;
	         
	         int w = rectWidth + size.width - originalwidth;
	         int h = rectHeight + size.height - originalheight;
	         double sx = (double)w/rectWidth;
	         double sy = (double)h/rectHeight;
	         double s = Math.min(sx, sy);
	         
	         int fw = (int)(s*rectWidth);
	         int fh = (int)(s*rectHeight);
	         if((size.width>629 && size.height>379 && size.width<700 && size.height<530) || (size.width>698 && size.height>432 && size.width<700 && size.height<530)) {
	        	 screen.fillRoundRect(x*2,y,fw,fh,20,20);
	         }
	         else if(size.width>=590 && size.height>=380 && size.width<745) {
	        	 int newWidth = (x*2);
	        	 screen.fillRoundRect(newWidth, y-75, fw, fh, 20, 20);
	         }
	         else if(size.width<=581 && size.height>=393) {
	        	 int newWidth = (x*2) - 50;
	        	 screen.fillRoundRect(newWidth, y-50, fw, fh, 20, 20);
	         }
	         else if(size.width>745 && size.height>=377) {
	        	 int newWidth = (x*2) + 125;
	        	 screen.fillRoundRect(newWidth, y-50, fw, fh, 20, 20);
	         }
	         
	         else {
	         screen.fillRoundRect(x,y,fw,fh,20,20);
	         }
	         int i=2;
	         int val = (int)((int)fw*0.05);

	         screen.setColor(Color.black);
	         if((size.width>629 && size.height>379 && size.width<700 && size.height<530) || (size.width>698 && size.height>432 && size.width<700 && size.height<530)){
	        	 screen.drawRoundRect(x*2,y,fw,fh,20,20);
	         }
	         else if(size.width>=590 && size.height>=380 && size.width<745) {
	        	 int newWidth = (x*2);
	        	 screen.drawRoundRect(newWidth, y-75, fw, fh, 20, 20);
	         }
	         else if(size.width<=581 && size.height>=393) {
	        	 int newWidth = (x*2) -50;
	        	 screen.drawRoundRect(newWidth, y-50, fw, fh,20,20);
	         }
	         else if(size.width>745 && size.height>=377) {
	        	 int newWidth = (x*2) + 125;
	        	 screen.drawRoundRect(newWidth, y-50, fw, fh, 20, 20);
	         }
	         else {
	         screen.drawRoundRect(x,y,fw,fh,20,20);
	         }
	         screen.setColor(Color.white);
	         if (value > 1) {
	        	 if((size.width>629 && size.height>379 && size.width<700 && size.height<530) || (size.width>698 && size.height>432 && size.width<700 && size.height<530)) {
	        		 screen.fillOval(x*2+(int)((int)fw*0.05), y+(int)((int)fh*0.05), (int)((int)fw*0.20), (int)((int)fh*0.20));
		        	 screen.fillOval(x*2+(int)((int)fw*0.75), y+(int)((int)fh*0.75), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 }
	        	 else if(size.width>=590 && size.height>=380 && size.width<745) {
	        		 screen.fillOval((x*2)+(int)((int)fw*0.05), (y-75)+(int)((int)fh*0.05), (int)((int)fw*0.20), (int)((int)fh*0.20));
		        	 screen.fillOval((x*2)+(int)((int)fw*0.75), (y-75)+(int)((int)fh*0.75), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 }
	        	 else if(size.width>745 && size.height>=377) {
	        		 screen.fillOval((x*2+125)+(int)((int)fw*0.05), (y-50)+(int)((int)fh*0.05), (int)((int)fw*0.20), (int)((int)fh*0.20));
		        	 screen.fillOval((x*2+125)+(int)((int)fw*0.75), (y-50)+(int)((int)fh*0.75), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 }
	        	 else if(size.width<=581 && size.height>=393) {
	        		 screen.fillOval((x*2-50)+(int)((int)fw*0.05), (y-50)+(int)((int)fh*0.05), (int)((int)fw*0.20), (int)((int)fh*0.20));
		        	 screen.fillOval((x*2-50)+(int)((int)fw*0.75), (y-50)+(int)((int)fh*0.75), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 }
	        	 else {
	        	 screen.fillOval(x+(int)((int)fw*0.05), y+(int)((int)fh*0.05), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 screen.fillOval(x+(int)((int)fw*0.75), y+(int)((int)fh*0.75), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 }
	         }
	         if (value > 3) {
	        	 if((size.width>629 && size.height>379 && size.width<700 && size.height<530) || (size.width>698 && size.height>432 && size.width<700 && size.height<530)) {
	        		 screen.fillOval(x*2+(int)((int)fw*0.75), y+(int)((int)fh*0.05), (int)((int)fw*0.20), (int)((int)fh*0.20));
		        	 screen.fillOval(x*2+(int)((int)fw*0.05), y+(int)((int)fh*0.75), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 }
	        	 else if(size.width>=590 && size.height>=380 && size.width<745) {
	        		 screen.fillOval((x*2)+(int)((int)fw*0.75), (y-75)+(int)((int)fh*0.05), (int)((int)fw*0.20), (int)((int)fh*0.20));
		        	 screen.fillOval((x*2)+(int)((int)fw*0.05), (y-75)+(int)((int)fh*0.75), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 }
	        	 else if(size.width<=581 && size.height>=393) {
	        		 screen.fillOval((x*2-50)+(int)((int)fw*0.75), (y-50)+(int)((int)fh*0.05), (int)((int)fw*0.20), (int)((int)fh*0.20));
		        	 screen.fillOval((x*2-50)+(int)((int)fw*0.05), (y-50)+(int)((int)fh*0.75), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 }
	        	 else if(size.width>745 && size.height>=377) {
	        		 screen.fillOval((x*2+125)+(int)((int)fw*0.75), (y-50)+(int)((int)fh*0.05), (int)((int)fw*0.20), (int)((int)fh*0.20));
		        	 screen.fillOval((x*2+125)+(int)((int)fw*0.05), (y-50)+(int)((int)fh*0.75), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 }
	        	 
	        	 else {
	        	 screen.fillOval(x+(int)((int)fw*0.75), y+(int)((int)fh*0.05), (int)((int)fw*0.20), (int)((int)fh*0.20));
	             screen.fillOval(x+(int)((int)fw*0.05), y+(int)((int)fh*0.75), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 }
	        	 }
	         if (value == 6) {
	        	 if(size.width>629 && size.height>379 && size.width<700 && size.height<530|| (size.width>698 && size.height>432 && size.width<700 && size.height<530)) {
	        		 screen.fillOval(x*2+(int)((int)fw*0.05), y+(int)((int)fh*0.40), (int)((int)fw*0.20), (int)((int)fh*0.20));
		        	 screen.fillOval(x*2+(int)((int)fw*0.75), y+(int)((int)fh*0.40), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 }
	        	 else if(size.width>=590 && size.height>=380 && size.width<745) {
	        		 screen.fillOval((x*2)+(int)((int)fw*0.05), (y-75)+(int)((int)fh*0.40), (int)((int)fw*0.20), (int)((int)fh*0.20));
		        	 screen.fillOval((x*2)+(int)((int)fw*0.75), (y-75)+(int)((int)fh*0.40), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 }
	        	 else if(size.width<=581 && size.height>=393) {
	        		 screen.fillOval((x*2-50)+(int)((int)fw*0.05), (y-50)+(int)((int)fh*0.40), (int)((int)fw*0.20), (int)((int)fh*0.20));
		        	 screen.fillOval((x*2-50)+(int)((int)fw*0.75), (y-50)+(int)((int)fh*0.40), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 }
	        	 else if(size.width>745 && size.height>=377) {
	        		 screen.fillOval((x*2+125)+(int)((int)fw*0.05), (y-50)+(int)((int)fh*0.40), (int)((int)fw*0.20), (int)((int)fh*0.20));
		        	 screen.fillOval((x*2+125)+(int)((int)fw*0.75), (y-50)+(int)((int)fh*0.40), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 }

	        	 else {
	        	   screen.fillOval(x+(int)((int)fw*0.05), y+(int)((int)fh*0.40), (int)((int)fw*0.20), (int)((int)fh*0.20));
		             screen.fillOval(x+(int)((int)fw*0.75), y+(int)((int)fh*0.40), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 }
	         }
	         if (value % 2 == 1) {
	        	 if((size.width>629 && size.height>379 && size.width<700 && size.height<530) || (size.width>698 && size.height>432 && size.width<700 && size.height<530)) {
	        		 screen.fillOval(x*2+(int)((int)fw*0.40), y+(int)((int)fh*0.40), (int)((int)fw*0.20), (int)((int)fh*0.20));
		        	 screen.fillOval(x*2+(int)((int)fw*0.40), y+(int)((int)fh*0.40), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 }
	        	 else if(size.width>=590 && size.height>=380 && size.width<745) {
	        		 screen.fillOval((x*2)+(int)((int)fw*0.40), (y-75)+(int)((int)fh*0.40), (int)((int)fw*0.20), (int)((int)fh*0.20));
		        	 screen.fillOval((x*2)+(int)((int)fw*0.40), (y-75)+(int)((int)fh*0.40), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 }
	        	 else if(size.width>745 && size.height>=377) {
	        		 screen.fillOval((x*2+125)+(int)((int)fw*0.40), y-50+(int)((int)fh*0.40), (int)((int)fw*0.20), (int)((int)fh*0.20));
		        	 screen.fillOval((x*2+125)+(int)((int)fw*0.40), y-50+(int)((int)fh*0.40), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 }
	        	 else if(size.width<=581 && size.height>=393) {
	        		 screen.fillOval((x*2-50)+(int)((int)fw*0.40), (y-50)+(int)((int)fh*0.40), (int)((int)fw*0.20), (int)((int)fh*0.20));
		        	 screen.fillOval((x*2-50)+(int)((int)fw*0.40), (y-50)+(int)((int)fh*0.40), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 }
	        	 else {
	        	 screen.fillOval(x+(int)((int)fw*0.40), y+(int)((int)fh*0.40), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 screen.fillOval(x+(int)((int)fw*0.40), y+(int)((int)fh*0.40), (int)((int)fw*0.20), (int)((int)fh*0.20));
	        	 }
       }
	         
	  }
	         
	 
	   /*
	    * Responsible for running different threads for different dice
	    * sets the value of int variables similar to the value on the dice
	    * uses the set value to draw the dice by invoking the updateDice function which runs the timer
	    * */
	   public void roll(DicePair dicepair, int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2, int finalDelay2, int delayIncrement2)
	   {
//		   System.out.println("die 1 value" + dicepair.getDie1().getValue());
//		   System.out.println("die 2 value" + dicepair.getDie2().getValue());
//		   
		   new Thread(() -> {
			   int i = initialDelay1;
			   
			   //repaint();
			   while(i<=finalDelay1) {
				   try {
					   Thread.sleep(initialDelay1);
//					   System.out.println("Rolled Dice 1");
					   
					   if(dicepair.getDie1().getValue()==1) {
						   //change it to 2;
						   this.diceSide1=1;
						   }
					   else if(dicepair.getDie1().getValue()==2) {
						   this.diceSide1=2;
					   }
					   else if(dicepair.getDie1().getValue()==3) {
						   this.diceSide1=3;
					   }
					   else if(dicepair.getDie1().getValue()==4) {
						   this.diceSide1=4;
					   }
					   else if(dicepair.getDie1().getValue()==5) {
						   this.diceSide1=5;
					   }
					   else {
						   this.diceSide1=6;
					   }
					  // repaint();
					   updateDice(this.diceSide1,this.diceSide6);
					   i+=delayIncrement1;
					   
				   }
				   catch(InterruptedException e) {
					   e.printStackTrace();
				   }
			   }
//			   System.out.println("Value of side 1" + this.diceSide1);
		   }).start();
		   
		   new Thread(() -> {
			   int i = initialDelay2;
			   while(i<=finalDelay2) {
				   try {
					   Thread.sleep(initialDelay2);
//					   System.out.println("Rolled Dice 2");
					   if(dicepair.getDie2().getValue()==1) {
						   this.diceSide6 = 1;
					   }
					   else if(dicepair.getDie2().getValue()==2) {
						   this.diceSide6 = 2;
					   }
					   else if(dicepair.getDie2().getValue()==3) {
						   this.diceSide6 = 3;
					   }
					   else if(dicepair.getDie2().getValue()==4) {
						   this.diceSide6 = 4;
					   }
					   else if(dicepair.getDie2().getValue()==5) {
						   this.diceSide6 = 5;
					   }
					   else {
						   this.diceSide6 = 6;
					   }
					   updateDice(this.diceSide1,this.diceSide6);
					   i+=delayIncrement2;
					   
				   }
				   catch(InterruptedException e) {
					   e.printStackTrace();
				   }
			   }
			   
//			   System.out.println("Value of side 6" + this.diceSide6);
			   
		   }).start();
		   //No need to use another thread now
		   new Thread(() -> {
			   int i = initialDelay1;
			   while(i<finalDelay1) {
				   repaint();
				   try {
					   Thread.sleep(125);
				   }
				   catch(InterruptedException e) {
					   e.printStackTrace();
				   }
				   i+=delayIncrement1;
			   }
		   }).start();
	   }
	   
	

}



