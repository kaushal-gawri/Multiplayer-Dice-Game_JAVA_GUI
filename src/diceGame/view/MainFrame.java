package diceGame.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import model.GameEngineImpl;
import model.interfaces.GameEngine;

public class MainFrame extends JFrame {
		
		private static final long serialVersionUID = 1L;
		
		private final DiceGameMenuBar menuBar;
		private final DiceGameToolBar toolBar;
		private final DiceGameSummaryPanel sideBar;
		private final DiceGamePanel dicePanel;
		private final DiceGameStatusBar statusBar;
		private final AddPlayerToolBarArea addPlayerDialog;
		private GameEngine engine = new GameEngineImpl();
		
		public MainFrame() {
			menuBar = new DiceGameMenuBar(this,engine);
			toolBar = new DiceGameToolBar(this,engine);
			sideBar = new DiceGameSummaryPanel(this,engine);
			dicePanel = new DiceGamePanel(this);
			statusBar = new DiceGameStatusBar();
			addPlayerDialog = new AddPlayerToolBarArea(this,engine);
			initializeMainWindow();
		}
		
		/* Initialises all frames, panels and components of the GUI */
		private void initializeMainWindow() {
			
			/* Creates menu */
			this.setJMenuBar(menuBar);
			
			/* Creates main layout */
			this.setLayout(new BorderLayout());
			
			/* Tool Bar*/
			this.add(toolBar, BorderLayout.NORTH);
			
			/* Creates main panel and layout */	
			JSplitPane centerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			//centerPane.add(sideBar, JSplitPane.LEFT);	
			centerPane.add(dicePanel, JSplitPane.RIGHT);
			
			/*Adding the addplayerBox and Summary panel to mainframe*/
			JSplitPane rightPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			rightPane.add(sideBar, JSplitPane.BOTTOM);
			rightPane.add(addPlayerDialog, JSplitPane.TOP);
			
			
			
			//Adding dicepanel to centre of frame
			this.add(centerPane, BorderLayout.CENTER);
		
			//Adding splitpane on the east
			rightPane.setPreferredSize(rightPane.getPreferredSize());
            rightPane.setPreferredSize(new Dimension(250,250));
			
			this.add(rightPane, BorderLayout.EAST);
			
			/* Status Bar */
			this.add(statusBar, BorderLayout.SOUTH);
			/* Disables focus */
			this.setFocusable(false);
			
			
			//Adds a diaglog box and asks for confirmation from user while exiting
			this.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						int x = JOptionPane.showConfirmDialog(null, "Are you sure you want to close the application?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
						if(x==JOptionPane.YES_OPTION) {
							setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						}
						else {
							setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						}
					}
			});
			
			this.setSize(800,400);
			/* Centers the JFrame */
			this.setLocationRelativeTo(null);
			this.setTitle("My Dice Game");
			this.setVisible(true);
		}
		
		/* passes gameEngine and frame reference to each components and adds the listeners.
		 * Called by main in client */
		public void addListeners(GameEngine gameEngine) {
			menuBar.addListeners(this, gameEngine);
			toolBar.addListeners(this, gameEngine);
			sideBar.addListeners(this, gameEngine);
			dicePanel.addListeners(this);
			addPlayerDialog.addListernerToFrame(this, gameEngine);
		}
		
	    /*Tried this earlier to make a dialog box 
	     * not used again*/ 
		private void createGUI(JFrame mainFrame) {
			JPanel panel = new JPanel();
			LayoutManager layout = new FlowLayout();
			panel.setLayout(layout);
			JButton button = new JButton("Click me");
			final JLabel label = new JLabel();
			button.addActionListener((ActionListener) new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String[] options = {"yes!please","No!Not now"};
					int result = JOptionPane.showOptionDialog(mainFrame, "Sure?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				   
				if(result==JOptionPane.YES_OPTION) {
					label.setText("You selected: yes");
				}
				else if(result == JOptionPane.NO_OPTION) {
					label.setText("You selected: no");
				}
				else {
					label.setText("None selected");
				}
				}
				
			});
			panel.add(button);
			panel.add(label);
			mainFrame.getContentPane().add(panel,BorderLayout.CENTER);
			
		}
		
		/* Getters for main panels */	
		public DiceGameToolBar getDiceGameToolBar() {
			return this.toolBar;
		}

		public DiceGameStatusBar getDiceGameStatusBar() {
			return this.statusBar;
		}

		public DiceGamePanel getDiceGamePanel() {
			return this.dicePanel;
		}
		
		public DiceGameSummaryPanel getDiceGameSummaryPanel() {
			return this.sideBar;
		}
		
		public AddPlayerToolBarArea getDiceGameAddPlayerDialog() {
			return this.addPlayerDialog;
		}

	}


