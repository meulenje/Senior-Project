package rpg;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JFrame;

/**
 * Grid Engine class
 * 
 * Holds the default attributes and loads
 * the default images/colors for the map.
 * @author Austin Delamar
 * @version 9/20/2012
 *
 */
public class GameEngine implements ActionListener, FocusListener, ClockListener {
	
	protected final int X_DIM = 600; // default window size
	protected final int Y_DIM = 600;
    protected final int BCOLS = 30; // board default column size
    protected final int BROWS = 30; // board default row size
	protected final int C_WIDTH = 25; // circle size
	protected final int C_HEIGHT = 25; // circle size
	protected final int G_X_DIM = BCOLS * (C_WIDTH ); // grid panel dimensions
	protected final int G_Y_DIM = BROWS * (C_HEIGHT); // grid panel dimensions
    protected int prow = 0; // player's row position
    protected int pcol = 0; // player's col position
    protected GridObject[][] board; // the maximum board size
    protected Clock klok; // a timer to control other settings
    
    /** Reference Grid
     *         BCOLS   -   X_DIM
     *      |-----------------------|
     * BROWS|                       |
     *      |                       |
     *      |                       |
     *   |  |                       |
     *      |                       |
     *      |                       |
     * Y_DIM|                       | 
     *      |                       |
     *      |-----------------------|
     */
    protected int[][] grid; // for loading maps
    
    // special map features
    protected boolean fogOfWar = false;
    protected boolean mappingEnabled = false;
    protected int playerVisionRange = 4;
    protected boolean warpingEnabled = false;
    protected boolean fadeOnExit = false;
    protected boolean clearStatsPerLevel = false;
    protected int monsterGridSpeed = 8; // monster moves after X seconds
    
    // default images
    protected ImageIcon Empty = new ImageIcon("Emtpy.png");
    protected ImageIcon Player = new ImageIcon("Player.png");
	protected ImageIcon PlayerFront = new ImageIcon("PlayerFront.png");
	protected ImageIcon PlayerBack = new ImageIcon("PlayerBack.png");
	protected ImageIcon PlayerLeft = new ImageIcon("PlayerLeft.png");
	protected ImageIcon PlayerRight = new ImageIcon("PlayerRight.png");
	protected ImageIcon PlayerGrassFront = new ImageIcon("PlayerGrassFront.gif");
	protected ImageIcon PlayerGrassBack = new ImageIcon("PlayerGrassBack.gif");
	protected ImageIcon PlayerGrassLeft = new ImageIcon("PlayerGrassLeft.gif");
	protected ImageIcon PlayerGrassRight = new ImageIcon("PlayerGrassRight.gif");
	protected ImageIcon PlayerDirtFront = new ImageIcon("PlayerGrassFront.gif");
	protected ImageIcon PlayerDirtBack = new ImageIcon("PlayerGrassFront.gif");
	protected ImageIcon PlayerDirtLeft = new ImageIcon("PlayerGrassLeft.gif");
	protected ImageIcon PlayerDirtRight = new ImageIcon("PlayerGrassRight.gif");
	protected ImageIcon GlowingGem = new ImageIcon("GlowingGem.gif");
	protected ImageIcon LavaMonster = new ImageIcon("LavaMonster.gif");
	protected ImageIcon Dirt = new ImageIcon("Dirt.png");
	protected ImageIcon Grass = new ImageIcon("Grass.png");
	protected ImageIcon Rock = new ImageIcon("Rock.png");
	protected ImageIcon DirtRock = new ImageIcon("DirtRock.png");
	protected ImageIcon GrassRock = new ImageIcon("GrassRock.png");
	protected ImageIcon DirtHole = new ImageIcon("DirtHole.png");
	protected ImageIcon GrassHole = new ImageIcon("GrassHole.png");
	protected ImageIcon Wall = new ImageIcon("Wall.png");
	protected ImageIcon Wall2 = new ImageIcon("Wall2.png");
	protected ImageIcon DirtBush = new ImageIcon("DirtBush.gif");
	
    
    // default gui parts
    private JFrame window;
    private JMenuItem quitItem;
    private JMenuItem teleportItem;
    private JMenuItem statsItem;
    private JMenuItem resetStatsItem;
    protected JMenuItem time;
    private JCheckBoxMenuItem enableFogItem;
    private JCheckBoxMenuItem enableFadeItem;
    private JCheckBoxMenuItem enableWarpItem;
    private JCheckBoxMenuItem enableAutoResetStatsItem;
    private JCheckBoxMenuItem enableMappingItem;
    private JMenuItem newItem;
    private JMenuItem saveItem;
    private JMenuItem loadItem;
    protected JTabbedPane tabs;
    protected JPanel map;
    protected JPanel combat;
    protected JPanel inventory;
    protected JPanel clockBar;

    
    /**
     * Constructor for GameEngine
     */
    public GameEngine()
    {                 
        // Create the JWindow and Frame to hold the Panels
    	
        // program window
        window = new JFrame("GVSU RPG Senior Project");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.isAlwaysOnTop();

        // First menu contains "Quit" and "Reset"
        JMenuBar menubar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu actionsMenu = new JMenu("Actions");
        JMenu settingsMenu = new JMenu("Settings");
        
        quitItem = new JMenuItem("Quit");
        newItem = new JMenuItem("New Game");
        saveItem = new JMenuItem("Save Game");
        loadItem = new JMenuItem("Load Game");
        teleportItem = new JMenuItem("Teleport to Start");
        statsItem = new JMenuItem("Report Stats");
        resetStatsItem = new JMenuItem("Reset Stats");
        enableFogItem = new JCheckBoxMenuItem("Fog of War");
        enableMappingItem = new JCheckBoxMenuItem("Show Visited");
        enableFadeItem = new JCheckBoxMenuItem("Fade Loading");
        enableWarpItem = new JCheckBoxMenuItem("Warps");
        enableAutoResetStatsItem = new JCheckBoxMenuItem("Auto Reset Stats");

        
        // add listeners
        newItem.addActionListener(this);
        fileMenu.add(newItem);
        loadItem.addActionListener(this);
        fileMenu.add(loadItem);
        saveItem.addActionListener(this);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        quitItem.addActionListener(this);
        fileMenu.add(quitItem);

        teleportItem.addActionListener(this);
        actionsMenu.add(teleportItem);
        statsItem.addActionListener(this);
        actionsMenu.add(statsItem);
        actionsMenu.addSeparator();
        resetStatsItem.addActionListener(this);
        actionsMenu.add(resetStatsItem);
        
        enableFogItem.addActionListener(this);
        enableFadeItem.addActionListener(this);
        enableWarpItem.addActionListener(this);
        enableAutoResetStatsItem.addActionListener(this);
        enableMappingItem.addActionListener(this);
        settingsMenu.add(enableFogItem);
        settingsMenu.add(enableMappingItem);
        settingsMenu.add(enableFadeItem);
        settingsMenu.add(enableWarpItem);
        settingsMenu.add(enableAutoResetStatsItem);
        
        enableFogItem.setState(fogOfWar);
        enableFadeItem.setState(fadeOnExit);
        enableAutoResetStatsItem.setState(clearStatsPerLevel);
        enableWarpItem.setState(warpingEnabled);
        enableMappingItem.setState(mappingEnabled); 
        // disable unnecessary options
        if(!fogOfWar) 
        	enableMappingItem.setEnabled(false); // mapping is useless unless you have fogOfWar enabled

        // show what time it is
        time = new JMenuItem("Time: X", JLabel.RIGHT);
        time.setForeground(Color.BLUE);
        
        menubar.add(fileMenu);
        menubar.add(settingsMenu);
        menubar.add(actionsMenu);
        menubar.add(Box.createHorizontalStrut(350));
        menubar.add(time);
        // place the menu bar
        window.setJMenuBar(menubar);

        // --------------------------------------------------------       
        // create the GridGui map panel
        map = new GridGUI(this);
        // --------------------------------------------------------
        
        // --------------------------------------------------------
        // create the CombatGUI combat panel
        combat = new JPanel();
        // --------------------------------------------------------

        // --------------------------------------------------------
        // create the InventoryGUI inventory panel
        inventory = new JPanel();
        // --------------------------------------------------------
        
        // create the TabbedPane
        tabs = new JTabbedPane();
        tabs.addTab("Map", map);
        tabs.addTab("Inventory", inventory);
        tabs.addTab("Combat", combat);
        tabs.addFocusListener(this);
        // focus is needed to detect if a JPanel is active (in view) or not.
        // that way, the KeyListener can function properly for certain JPanels.
        
        // add the tab pane to the window
        window.add(tabs, BorderLayout.CENTER);
        
        // show window in default size dimensions
        window.setSize(new Dimension( X_DIM , Y_DIM));
        window.setResizable(false);
        window.pack(); 
        window.setVisible(true);
        map.requestFocusInWindow();
 
        // create the clock  and clock status bar at the bottom
        // listen to the timer,
        // on every tick, event() will be called.
        klok = new Clock();
        klok.register(this); // The Engine listens to the Clock
        klok.register((GridGUI) map); // the Grid listens to the Clock
        klok.run(Clock.FOREVER);
       
    }

	/**
     * Display an error
     * @param e
     */
    public void printError(String e)
    {
        // found an error and is printing it according to the view
    	klok.pause();
    	JOptionPane.showMessageDialog(window,e,"Error",JOptionPane.ERROR_MESSAGE);
    	klok.run(Clock.FOREVER);
    }
    
	/**
     * Display some information
     * @param s
     */
    public void printInfo(String s)
    {
        // found some information to print
    	klok.pause();
    	JOptionPane.showMessageDialog(window,s,"Information",JOptionPane.INFORMATION_MESSAGE);
    	klok.run(Clock.FOREVER);
    }
    
    /**
     * Prompt a Question
     * @param t
     * @return String
     */
    public String printQuestion(String t)
    {
    	klok.pause();
        String result = JOptionPane.showInputDialog(window,t,"Question",JOptionPane.QUESTION_MESSAGE);
        klok.run(Clock.FOREVER);
        return result;
    }
    
    /**
     * Prompt a Yes/No Question
     * @param b
     * @return int
     *  0 means YES
     *  1 means NO
     *  -1 means they clicked "X" close
     */
    public int printYesNoQuestion(String b)
    {
    	klok.pause();
        int result = JOptionPane.showConfirmDialog(window,b,"Question",JOptionPane.YES_NO_OPTION);
        klok.run(Clock.FOREVER);
        return result;
    }
    
    
    /**
     * Prompt a Custom Question
     * @param String question
     * @param Object[] choices
     * @return int
     *  0 means YES
     *  1 means NO
     *  -1 means they clicked "X" close
     */
    public int printCustomQuestion(String question, Object[] choices)
    {
    	klok.pause();
    	int result = JOptionPane.showOptionDialog(window,question,"Question",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,choices,choices[1]);
    	klok.run(Clock.FOREVER);
    	return result;
    }


	/**
	 * A function to end the game
	 */
	public void endGame()
	{
		klok.pause();
		System.exit(1);
	} 
	
	@Override
	public void actionPerformed(ActionEvent Trigger)
	{
		// if the user clicks on a menu button
		klok.pause();
		
		if(Trigger.getSource() == newItem)
		{
			int answer = printYesNoQuestion("Are you sure you want\nto start a new map?");
			if(answer==0)
			{
				// load a new map
				((GridGUI) map).randomizeBoard();
				((GridGUI) map).repositionPlayer();
				((GridGUI) map).repositionScrollBar();
				((GridGUI) map).movePlayerVision();
				
				// reset statistics if they wanted to
				if(clearStatsPerLevel)
					((GridGUI) map).resetStatistics();
			}
		}
		else if(Trigger.getSource() == saveItem)
		{
			// save the game
			printInfo("Save successful.");
		}
		else if(Trigger.getSource() == quitItem)
        {
			int answer = printYesNoQuestion("Are you sure you want\nto quit?");
			if(answer==0)
				endGame();
        }
        else if(Trigger.getSource() == teleportItem)
        {
        	((GridGUI) map).repositionPlayer();
        	((GridGUI) map).repositionScrollBar();
        }
        else if(Trigger.getSource() == statsItem)
        {
        	printInfo(((GridGUI) map).getStatistics());
        }
        else if(Trigger.getSource() == resetStatsItem)
        {
        	int answer = printYesNoQuestion("Are you sure you want\nto clear your progress?");
        	if(answer==0)
        	{
        		((GridGUI) map).resetStatistics();
        		printInfo("Player Statistics have been deleted.");
        	}
        }
        else if(Trigger.getSource() == enableFogItem)
        {
        	if(fogOfWar)
        	{
        		fogOfWar = false;
        		((GridGUI) map).removeFog();
        		mappingEnabled = false; // mapping is useless without fog
        		enableMappingItem.setEnabled(false);
        	}
        	else
        	{
        		fogOfWar = true;
        		((GridGUI) map).addFog();
        		((GridGUI) map).movePlayerVision();
        		enableMappingItem.setEnabled(true);
        	}
        }
        else if(Trigger.getSource() == enableFadeItem)
        {
        	if(fadeOnExit)
        		fadeOnExit = false;
        	else
        		fadeOnExit = true;
        }
        else if(Trigger.getSource() == enableWarpItem)
        {
        	if(warpingEnabled)
        	{
        		warpingEnabled = false;
        	}
        	else
        	{
        		warpingEnabled = true;
        		printInfo("Warping will be enabled on the next level.");
        	}
        }
        else if(Trigger.getSource() == enableAutoResetStatsItem)
        {
        	if(clearStatsPerLevel)
        	{
        		clearStatsPerLevel = false;
        	}
        	else
        	{
        		clearStatsPerLevel = true;
        		printInfo("Player Statistics will be erased per level.");
        	}
        }
        else if(Trigger.getSource() == enableMappingItem)
        {	
        	if(mappingEnabled)
        	{
        		mappingEnabled = false;
        	}
        	else
        	{
        		mappingEnabled = true;
        	}
        }
        klok.run(Clock.FOREVER);
	}

	@Override
	public void focusGained(FocusEvent e)
	{
		if(e.getSource() == tabs)
		{
			int tab = tabs.getSelectedIndex();
			if(tab == 0)
			{
				map.requestFocus();
				klok.run(Clock.FOREVER);
			}
			else if(tab == 1)
			{
				klok.pause();
				inventory.requestFocus();
			}
			else if(tab == 2)
			{
				klok.pause();
				combat.requestFocus();
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean event(int tick)
	{
		// update the clock time on the JMenu
		time.setText("Time: "+klok.currentTime);
		
		return false;
	}
}
