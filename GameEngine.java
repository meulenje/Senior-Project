package rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.AbstractListModel;
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
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 * Grid Engine class
 * 
 * Holds the default attributes and loads
 * the default images/colors for the map.
 * @author Austin Delamar
 * @version 10/1/2012
 *
 */
public class GameEngine extends AbstractListModel<JPanel> implements ActionListener, FocusListener, ClockListener {
	
	private static final long serialVersionUID = 1L;
	protected final int X_DIM = 600; // default window size
	protected final int Y_DIM = 600;
    protected final int BCOLS = 25; // board default column size
    protected final int BROWS = 25; // board default row size
	protected final int C_WIDTH = 25; // circle size
	protected final int C_HEIGHT = 25; // circle size
	protected final int G_X_DIM = BCOLS * (C_WIDTH ); // grid panel dimensions
	protected final int G_Y_DIM = BROWS * (C_HEIGHT); // grid panel dimensions
    protected int prow = 0; // player's row position
    protected int pcol = 0; // player's col position
    protected int prowStart = BROWS-1; // initial start position
    protected int pcolStart = 0; // initial start position
    protected GridObject[][] board; // the maximum board size
    protected ArrayList<Quest> quests; // list of quest messages
    protected Clock klok; // a timer to control other settings
    protected int clockSpeed = 1000; // time between ticks in milliseconds
    
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
    protected int[][] gridLayer1; // Terrain layer
    protected int[][] gridLayer2; // Entity Layer
    protected int[][] gridLayer3; // Accessory Layer
    
    // default grid id numbers
    protected int EmptyID = 0;
    protected int FogCenterID = -1;
    protected int FogTopID = 1;
    protected int FogBottomID = 2;
    protected int FogLeftID = 3;
    protected int FogRightID = 4;
    protected int PlayerID = 1;
    protected int ExitID = 2;
    protected int WarpAID = -8;
    protected int WarpBID = -9;
    protected int LavaMonsterID = 4;
    protected int PirateID = 5;
    protected int RockID = 2;
    protected int HoleID = 3;
    protected int DirtID = -1;
    protected int GrassID = -2;
    protected int TallGrassID = 1;
    protected int WaterID = -3;
    
    // special map features
    protected boolean showHintsEnabled = true;
    protected boolean fogOfWar = false;
    protected boolean mappingEnabled = false;
    protected int playerVisionRange = 3;
    protected boolean warpingEnabled = true;
    protected boolean blinkOnExit = false;
    protected boolean clearStatsPerLevel = false;
    protected int monsterGridSpeed = 6; // monster moves after X seconds
    protected double percentChanceOfEncounter = 0.10; // when entering a Encounterable space, there is a small chance the player will run into a monster
    
    // default images
    protected ImageIcon Empty = new ImageIcon("Emtpy.png");
    protected ImageIcon FogCenter = new ImageIcon("Fog.png");
    protected ImageIcon FogLeft = new ImageIcon("FogLeft.png");
    protected ImageIcon FogTop = new ImageIcon("FogTop.png");
    protected ImageIcon FogRight = new ImageIcon("FogRight.png");
    protected ImageIcon FogBottom = new ImageIcon("FogBottom.png");
    protected ImageIcon Player = new ImageIcon("Player.png");
	protected ImageIcon PlayerFront = new ImageIcon("PlayerFront.png");
	protected ImageIcon PlayerBack = new ImageIcon("PlayerBack.png");
	protected ImageIcon PlayerLeft = new ImageIcon("PlayerLeft.png");
	protected ImageIcon PlayerRight = new ImageIcon("PlayerRight.png");
	protected ImageIcon GlowingGem = new ImageIcon("Gem.gif");
	protected ImageIcon LavaMonster = new ImageIcon("LavaMonster.gif");
	protected ImageIcon Pirate = new ImageIcon("Pirate.gif");
	protected ImageIcon Water = new ImageIcon("Water.png");
	protected ImageIcon Dirt = new ImageIcon("Dirt.png");
	protected ImageIcon Grass = new ImageIcon("Grass.png");
	protected ImageIcon TallGrass = new ImageIcon("TallGrass.png");
	protected ImageIcon Rock = new ImageIcon("Rock.png");
	protected ImageIcon Hole = new ImageIcon("Hole.png");
	protected ImageIcon XSpace = new ImageIcon("XSpace.png");
	protected ImageIcon Hideout = new ImageIcon("Hideout.png");
	
    
    // default gui parts
    private JFrame window;
    private JMenuBar menubar;
    private JMenuItem quitItem;
    private JMenuItem teleportItem;
    private JMenuItem statsItem;
    private JMenuItem resetStatsItem;
    private JMenuItem newItem;
    private JMenuItem saveItem;
    private JMenuItem loadItem;
    protected JMenuItem time;
    private JCheckBoxMenuItem enableHintsItem;
    private JCheckBoxMenuItem enableFogItem;
    private JCheckBoxMenuItem enableBlinkItem;
    private JCheckBoxMenuItem enableWarpItem;
    private JCheckBoxMenuItem enableAutoResetStatsItem;
    private JCheckBoxMenuItem enableMappingItem;
    protected JTabbedPane tabs;
    protected JPanel map;
    protected JPanel combat;
    protected JPanel inventory;
    protected JPanel questPanel;

    
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
        menubar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu actionsMenu = new JMenu("Actions");
        JMenu optionsMenu = new JMenu("Options");
        
        quitItem = new JMenuItem("Quit");
        newItem = new JMenuItem("New Game");
        saveItem = new JMenuItem("Save Game");
        loadItem = new JMenuItem("Load Game");
        teleportItem = new JMenuItem("Teleport to Start");
        statsItem = new JMenuItem("Report Stats");
        resetStatsItem = new JMenuItem("Reset Stats");
        enableHintsItem = new JCheckBoxMenuItem("Show Hints");
        enableFogItem = new JCheckBoxMenuItem("Fog of War");
        enableMappingItem = new JCheckBoxMenuItem("Show Visited");
        enableBlinkItem = new JCheckBoxMenuItem("Blink Loading");
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
        
        enableHintsItem.addActionListener(this);
        enableFogItem.addActionListener(this);
        enableBlinkItem.addActionListener(this);
        enableWarpItem.addActionListener(this);
        enableAutoResetStatsItem.addActionListener(this);
        enableMappingItem.addActionListener(this);
        optionsMenu.add(enableHintsItem);
        optionsMenu.add(enableFogItem);
        optionsMenu.add(enableMappingItem);
        optionsMenu.add(enableBlinkItem);
        optionsMenu.add(enableWarpItem);
        optionsMenu.add(enableAutoResetStatsItem);
        
        enableHintsItem.setState(showHintsEnabled);
        enableFogItem.setState(fogOfWar);
        enableBlinkItem.setState(blinkOnExit);
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
        menubar.add(optionsMenu);
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
        combat.add(new JLabel("Hello Jeff."));
        // --------------------------------------------------------

        // --------------------------------------------------------
        // create the InventoryGUI inventory panel
        inventory = new JPanel();
        // --------------------------------------------------------
        
        // --------------------------------------------------------
        // create the MessageGUI quest panel
        questPanel = new MessageGUI(this);
        // --------------------------------------------------------
        
        // create the TabbedPane
        tabs = new JTabbedPane();
        tabs.addTab("Map", map);
        tabs.addTab("Inventory", inventory);
        tabs.addTab("Combat", combat);
        tabs.addTab("Quests", questPanel);
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
        klok.setRate(clockSpeed); // time passes per tick by this speed in milliseconds
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
     * Forces player to view Quests
     */
    public void showQuestPanel()
    {  	
    	// force the player to see Message
    	tabs.setSelectedIndex(3);
    }
    
    /**
     * Forces player to view Combat
     */
    public void showCombatPanel()
    {
    	tabs.setSelectedIndex(2);
    }
    
    /**
     * Forces player to view Inventory
     */
    public void showInventoryPanel()
    {
    	tabs.setSelectedIndex(1);
    }
    
    /**
     * Forces player to view Map
     */
    public void showMapPanel()
    {
    	tabs.setSelectedIndex(0);
    }
    
	/**
	 * Add another quest message
	 * @return void
	 */
	public void addQuest(ImageIcon i, String t, String m)
	{
		// create new jpanel to add to array list
		Quest q = new Quest(i,t,m);
		quests.add(q);
		((MessageGUI) questPanel).addQuest(q);
		fireIntervalAdded(this, quests.size()-1, quests.size() - 1);
		
		// popup a hint for the player if they need a reminder
		if(showHintsEnabled)
			printInfo("New Quest:\n\n"+t+"\n\nDisable hints in the Options Menu.");
	}
	
	/**
	 * Delete a message box.
	 * Throws an exception if the id is invalid
	 */
	public void removeQuest(int id)
	{
		try
		{	
			quests.remove(id);
			fireIntervalRemoved(this, quests.size(), quests.size());
		}
		catch(Exception e)
		{
			printError("Whoops!\n\n"+e.getMessage());
		}
	}

	@Override
	public JPanel getElementAt(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSize() {
		return quests.size();
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
				((GridGUI) map).repositionPlayer(prowStart,pcolStart);
				((GridGUI) map).repositionScrollBar();
				((GridGUI) map).movePlayerVision();
				
				// reset statistics if they wanted to
				if(clearStatsPerLevel)
					((GridGUI) map).resetStatistics();
				
				// add new quest to messageGUI
				addQuest(Hideout,"Find the Exit","Try to find the exit in this level.");
				
				// reset clock
				klok.pause();
				klok.currentTime = 0;
				klok.run(Clock.FOREVER);
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
        	((GridGUI) map).repositionPlayer(prowStart,pcolStart);
        	((GridGUI) map).repositionScrollBar();
        	((GridGUI) map).movePlayerVision();
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
        		((GridGUI) map).setFog(false);
        		fogOfWar = false;
        		mappingEnabled = false; // mapping is useless without fog
        		enableMappingItem.setEnabled(false);
        	}
        	else
        	{
        		((GridGUI) map).setFog(true);
        		fogOfWar = true;
        		((GridGUI) map).movePlayerVision();
        		enableMappingItem.setEnabled(true);
        	}
        }
        else if(Trigger.getSource() == enableBlinkItem)
        {
        	if(blinkOnExit)
        		blinkOnExit = false;
        	else
        		blinkOnExit = true;
        }
        else if(Trigger.getSource() == enableWarpItem)
        {
        	if(warpingEnabled)
        	{
        		warpingEnabled = false;
        		((GridGUI) map).removeWarps();
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
        else if(Trigger.getSource() == enableHintsItem)
        {
        	if(showHintsEnabled)
        	{
        		showHintsEnabled = false;
        	}
        	else
        	{
        		showHintsEnabled = true;
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
			else if(tab == 3)
			{
				klok.pause();
				questPanel.requestFocus();
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
	
	public synchronized void playSound(final String soundFile)
	{
		new Thread(new Runnable() {
			public void run() {
				try
			    {
			        Clip clip = AudioSystem.getClip();
			        clip.open(AudioSystem.getAudioInputStream(new File(soundFile)));
			        clip.start();
			    }
			    catch (Exception ex)
			    {
			        printError("Whoops!\nThere was a problem when trying to play "+soundFile+".\n\n"+ex.getMessage());
			    }
			}
		}).start();
	}
	

	/**
	 * A function to end the game
	 */
	public void endGame()
	{
		klok.pause();
		System.exit(1);
	}
} // end of GameEngine