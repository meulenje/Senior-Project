package rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
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
import java.util.Random;
import javax.swing.JFrame;

/**
 * Game Engine class
 * 
 * The brains behind the game's functionality and features.
 * 
 * @author Austin Delamar
 * @version 10/11/2012
 *
 */
public class GameEngine implements ActionListener, FocusListener, ClockListener {
	
	// Game Engine specific variables
	protected final int MAXIMUMSIZE = 100; // Max board size for rows and columns
	protected final int X_DIM = 600; // default frame window size
	protected final int Y_DIM = 600;
	protected final int C_WIDTH = 25; // object image size
	protected final int C_HEIGHT = 25; // object image size
    protected GridObject[][] board; // the maximum board size
    protected ArrayList<QuestGUI> quests; // list of quest messages
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
    // GridGUI specific variables
    protected int BCOLS = 25; // board default column size
    protected int BROWS = 25; // board default row size
	protected final int G_X_DIM = BCOLS * (C_WIDTH ); // grid panel dimensions
	protected final int G_Y_DIM = BROWS * (C_HEIGHT); // grid panel dimensions
    protected int prow = 0; // player's row position
    protected int pcol = 0; // player's col position
    protected int prowStart = BROWS-1; // initial start position
    protected int pcolStart = 0; // initial start position
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
    
    // special Frame features
    protected boolean windowResizeable = true;
    
    // special map features
    protected boolean showHintsEnabled = true;
    protected boolean fogOfWar = false;
    protected boolean mappingEnabled = false;
    protected int playerVisionRange = 3;
    protected boolean warpingEnabled = true;
    protected boolean blinkOnExit = false;
    protected boolean clearStatsPerLevel = false;
    protected int monsterGridSpeed = 6; // monster moves after X seconds
    protected double percentChanceOfEncounter = 0.1; // when entering a Encounterable space, there is a small chance the player will run into a monster
    
    // special Grid variables
	protected int hops = 0; // # of jumps taken
	protected int steps = 0; // # of steps taken
	protected int encounters = 0; // # of encounters
	protected int objectsPushed = 0; // # of rock pushes
	protected int objectsPushedInHoles = 0; // # of rocks pushed into holes
	protected int warps = 0; // # of times they warped
	protected int keysPushed = 0; // # of keyboard buttons pushed
	protected int numOfMonsters = 0; // keep track of how many monsters there are
	
	// special Quest variables
	protected int questsCompleted = 0;
	protected int questsActive = 0;
	protected int questsFailed = 0;
	protected int questsTotal = 0;
    
    // default images
    protected ImageIcon Empty = new ImageIcon("images/Empty.png");
    protected ImageIcon FogCenter = new ImageIcon("images/Fog.png");
    protected ImageIcon FogLeft = new ImageIcon("images/FogLeft.png");
    protected ImageIcon FogTop = new ImageIcon("images/FogTop.png");
    protected ImageIcon FogRight = new ImageIcon("images/FogRight.png");
    protected ImageIcon FogBottom = new ImageIcon("images/FogBottom.png");
    protected ImageIcon Player = new ImageIcon("images/PlayerFront.gif");
    protected ImageIcon PlayerOutline = new ImageIcon("images/PlayerOutline.png");
	protected ImageIcon GlowingGem = new ImageIcon("images/Gem.gif");
	protected ImageIcon LavaMonster = new ImageIcon("images/LavaMonster.gif");
	protected ImageIcon Pirate = new ImageIcon("images/Pirate.gif");
	protected ImageIcon Water = new ImageIcon("images/Water.png");
	protected ImageIcon Dirt = new ImageIcon("images/Dirt.png");
	protected ImageIcon Grass = new ImageIcon("images/Grass.png");
	protected ImageIcon TallGrass = new ImageIcon("images/TallGrass.png");
	protected ImageIcon Rock = new ImageIcon("images/Rock.png");
	protected ImageIcon Hole = new ImageIcon("images/Hole.png");
	protected ImageIcon XSpace = new ImageIcon("images/XSpace.png");
	protected ImageIcon Hideout = new ImageIcon("images/Hideout.png");
	
    
    // default gui parts
    private JFrame window;
    private JMenuBar menubar;
    private JMenuItem quitItem;
    private JMenuItem teleportItem;
    private JMenuItem statsItem;
    private JMenuItem newItem;
    private JMenuItem saveItem;
    private JMenuItem loadItem;
    protected JMenuItem time;
    private JCheckBoxMenuItem enableHintsItem;
    private JCheckBoxMenuItem enableFogItem;
    private JCheckBoxMenuItem enableBlinkItem;
    private JCheckBoxMenuItem enableWarpItem;
    private JCheckBoxMenuItem enableMappingItem;
    private JLayeredPane layers;
    protected JTabbedPane tabs;
    protected JPanel mainmenu;
    protected JPanel map;
    protected JPanel combat;
    protected JPanel inventory;
    protected JPanel questPanel;
    
	// keep track of keyboard keys being held down
	protected boolean up = false; // 38
	protected boolean down = false; // 40
	protected boolean left = false; // 37
	protected boolean right = false; // 39
	protected boolean leftShift = false; // 16
	protected boolean spaceBar = false; // 32

    
    /**
     * Constructor for GameEngine
     */
    public GameEngine()
    {                 
        // Create the JWindow and Frame to hold the Panels
    	
        // program window
        window = new JFrame("GVSU RPG Senior Project");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        enableHintsItem = new JCheckBoxMenuItem("Show Hints");
        enableFogItem = new JCheckBoxMenuItem("Fog of War");
        enableMappingItem = new JCheckBoxMenuItem("Show Visited");
        enableBlinkItem = new JCheckBoxMenuItem("Blink Loading");
        enableWarpItem = new JCheckBoxMenuItem("Warps");
        
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
        
        enableHintsItem.addActionListener(this);
        enableFogItem.addActionListener(this);
        enableBlinkItem.addActionListener(this);
        enableWarpItem.addActionListener(this);
        enableMappingItem.addActionListener(this);
        optionsMenu.add(enableHintsItem);
        optionsMenu.add(enableFogItem);
        optionsMenu.add(enableMappingItem);
        optionsMenu.add(enableBlinkItem);
        optionsMenu.add(enableWarpItem);
        
        enableHintsItem.setState(showHintsEnabled);
        enableFogItem.setState(fogOfWar);
        enableBlinkItem.setState(blinkOnExit);
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
        combat = new CombatGUI2(this);
        // --------------------------------------------------------

        // --------------------------------------------------------
        // create the InventoryGUI inventory panel
        inventory = new JPanel();
        inventory.add(new JLabel("Sorry!\n\nThis part of the game is unfinished."));
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
        tabs.setLocation(0,0);
        tabs.setSize(X_DIM,Y_DIM + 60);
        
        // --------------------------------------------------------
        // make the main menu gui
        mainmenu = new MainMenuGUI(this);
        mainmenu.setLocation(0,0);
        mainmenu.setSize(X_DIM,Y_DIM + 60);
        // --------------------------------------------------------

        // create two layers
        // 0 -- is for the GamePanels (Grid, Combat, etc)
        // 100 -- is for the MainMenu
        layers = new JLayeredPane();
        layers.setLayout(new BorderLayout());
        layers.add(tabs);
        layers.add(mainmenu);
        layers.setLayer(tabs, 0);
        layers.setLayer(mainmenu, 100);
        
        // add the layers to the window
        window.add(layers, BorderLayout.CENTER);
        
        // show window in default size dimensions
        window.setSize(new Dimension( X_DIM , Y_DIM));
        window.setResizable(windowResizeable);
        window.pack(); 
        window.setVisible(true);
 
        // create the clock  and clock status bar at the bottom
        // listen to the timer,
        // on every tick, event() will be called.
        klok = new Clock();
        klok.register(this); // The Engine listens to the Clock
        klok.register((GridGUI) map); // the Grid listens to the Clock
        klok.setRate(clockSpeed); // time passes per tick by this speed in milliseconds
    }
    
    /**
     * Creates each GridObject according to the number of 
     * rows and columns in BROWS and BCOLS.
     */
	public void createGridObjects()
	{
		// create the board
		board = new GridObject[BROWS][BCOLS];
		
        for (int i=0; i<BROWS; i++)
        {
            for (int j=0; j<BCOLS; j++)
            {
            	board[i][j] = new GridObject(this,GrassID,EmptyID,EmptyID);
            	((GridGUI) map).addGridObject(i,j); // place on grid panel
            }
        }
	}
	
	/**
	 * Deletes each GridObject, in order to restart a new board.
	 */
	public void deleteGridObjects()
	{
		board = null;
		// remove each GridObject reference from the grid panel
		((GridGUI) map).removeAllGridObjects();
	}
	
	/**
	 * Creates a list of empty quests
	 */
	public void createQuestList()
	{
		// create list of quests
		quests = new ArrayList<QuestGUI>();
	}
	
	/**
	 * Deletes the list of quests
	 */
	public void deleteQuestList()
	{
		quests = null;
		// remove each of the quests
		((MessageGUI) questPanel).removeAllQuests();
	}
    
    /**
     * A function to start a new game. This is not the function
     * to start the next level!
     */
    public void newGame()
    {
    	// choose player
    	// TODO
    	
    	// delete old map and make a new empty one
    	deleteGridObjects();
    	createGridObjects();
    	
    	// delete old quests and make a new empty one
    	deleteQuestList();
    	createQuestList();
    	    	
    	// clear game variables
    	resetStatistics();
		questsCompleted=0;
		questsTotal=0;
		questsActive=0;
		questsFailed=0;
		
		// load a new map
    	loadMap();
    	
		// reset clock
		klok.pause();
		klok.currentTime = 0;
    }
    
	/**
	 * A function to end the game. This is not the function
	 * to end the current level!
	 */
	public void endGame()
	{
		// reset clock
		klok.pause();
		
		// delete board, quests
		deleteGridObjects();
		deleteQuestList();
		
		// clear game variables
		resetStatistics();
		questsCompleted=0;
		questsTotal=0;
		questsActive=0;
		questsFailed=0;
		
		// go to Main Menu
		viewMainMenu();
	}
	
	/**
	 * WARNING!
	 * Closes this Java Application!
	 * This is the same as clicking "X" on the
	 * program when its running.
	 */
	public void quitProgram()
	{
		// gracefully exit the entire game
		System.exit(1);
	}

	/**
     * Display an error
     * @param e
     */
    public void printError(String e)
    {
        // found an error and is printing it according to the view
    	JOptionPane.showMessageDialog(window,e,"ERROR",JOptionPane.ERROR_MESSAGE);
    }
    
	/**
     * Display some information
     * @param s
     */
    public void printInfo(String s)
    {
        // found some information to print
    	JOptionPane.showMessageDialog(window,s,"Information",JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Prompt a Question
     * @param t
     * @return String
     */
    public String printQuestion(String t)
    {
        return JOptionPane.showInputDialog(window,t,"Question",JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Prompt a Yes/No Question
     * @param String b
     * @return int
     *  0 means YES,
     *  1 means NO,
     *  -1 means they clicked "X" close
     */
    public int printYesNoQuestion(String b)
    {
        return JOptionPane.showConfirmDialog(window,b,"Question",JOptionPane.YES_NO_OPTION);
    }
    
    
    /**
     * Prompt a Custom Question
     * @param String question
     * @param Object[] choices
     * @return int
     *  0 means YES,
     *  1 means NO,
     *  -1 means they clicked "X" close
     */
    public int printCustomQuestion(String question, Object[] choices)
    {
    	return JOptionPane.showOptionDialog(window,question,"Question",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,choices,choices[1]);
    }
    
    /**
     * Forces player to view Quests
     */
    public void viewQuestPanel()
    {  	
    	tabs.setSelectedIndex(3);
    	klok.pause();
    }
    
    /**
     * Forces player to view Combat
     */
    public void viewCombatPanel()
    {
    	tabs.setSelectedIndex(2);
    	klok.pause();
    }
    
    /**
     * Forces player to view Inventory
     */
    public void viewInventoryPanel()
    {
    	tabs.setSelectedIndex(1);
    	klok.pause();
    }
    
    /**
     * Forces player to view Map
     */
    public void viewMapPanel()
    {
    	tabs.setSelectedIndex(0);
    	klok.run(Clock.FOREVER);
    }
    
    /**
     * Forces player to view the Main Menu
     */
    public void viewMainMenu()
    {
    	mainmenu.setVisible(true);
    }
    
	/**
	 * Resets the statistics for the player
	 * @return void
	 */
	public void resetStatistics()
	{
		hops = 0; // # of jumps taken
		steps = 0; // # of steps taken
		encounters = 0; // # of encounters
		objectsPushed = 0; // # of rock pushes
		objectsPushedInHoles = 0; // # of rocks pushed into holes
		warps = 0; // # of times they warped
		keysPushed = 0; // # of keyboard buttons pushed
	}
	
	/**
	 * Returns the current statistics for the player
	 * @return String
	 */
	public String getStatistics()
	{
		return  questsCompleted+" Quests Completed\n"+
				steps+" Steps\n"+
				hops+" Hops\n"+
				encounters+" Battles\n"+
				objectsPushed+" Objects Pushed\n"+
				objectsPushedInHoles+" Objects Pushed into Holes\n"+
				warps+" Times Warped\n"+
				keysPushed+" Keys Pushed\n";
	}
    
	/**
	 * Add another quest message to the MessageGUI
	 * unique id number
	 * imageicon name
	 * title
	 * message description (list of tasks)
	 * status description (started or complete)
	 * @return void
	 */
	public void addQuest(int id, ImageIcon i, String t, String m, String s)
	{
		questsActive++;
		
		// create new jpanel to add to array list
		QuestGUI q = new QuestGUI(this,id,i,t,m,s);
		quests.add(q);
		
		// add new quest to MessageGUI
		((MessageGUI) questPanel).addQuest(q);
		
		// popup a hint for the player if they need a reminder
		if(showHintsEnabled)
		{
			Object options[] = {"View Now","Close"};
			int result = printCustomQuestion("New Quest available!",options);
			if(result == 0)
				viewQuestPanel();
		}
	}
	
	/**
	 * Updates the status of a Quest by its id. The color is optional, so
	 * you could make the letters appear BLUE when the quest is completed,
	 * or just make the letters appear BLACK if its not important.
	 * @param id
	 * @param s
	 * @param c
	 */
	public void updateQuestStatus(int id, String s, Color c)
	{
		quests.get(id).setStatus(s,c);
		quests.get(id).setStatistics("This Quests statistics:\n\n"+getStatistics());
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
			((MessageGUI) questPanel).removeQuest(id);
		}
		catch(Exception e)
		{
			printError("Whoops!\n\n"+e.getMessage());
		}
	}
	
	/**
	 * Removes all warps from the map, and replaces them grass
	 * @return void
	 */
	public void removeWarps()
	{
		// We must find the other warp, according to our current warp id
        for (int i=0; i<BROWS; i++)
        {
            for (int j=0; j<BCOLS; j++)
            {
            	// find the other warp
            	if(board[i][j].getTerrain() == WarpAID 
            			|| board[i][j].getTerrain() == WarpBID)
            	{
            		// replace with grass
            		board[i][j].setTerrain(GrassID);
            	}
            }
        }
		return;
	}
	
	/**
	 * Check if the player's current position is over the
	 * exit portal for the level. If true, then they beat the level!
	 */
	public void checkForExit()
	{
		// did they stumble upon the exit door?
		if(board[prow][pcol].isExit())
		{
			questsCompleted++;
			
			if(questsCompleted % 3 == 0) // every three wins, print stats
			{
				printInfo("Highscore!\n\n"+getStatistics());
				printInfo("Way to go! You're a Laker for a Lifetime!");
			}
			
			int temp = printYesNoQuestion("Congrats! You found the exit!\n\nPlay again?");
			
			if(temp==0)
			{
				// start fresh again! Load a new map
				loadMap();
			}
			else // 1 or -1
			{
				// they chose to quit
				endGame();
			}
		}
		return;
	}
	
	/**
	 * check for enemies
	 * Scans the four surrounding locations, NSWE, for a monster.
	 * Also checks if the location the player is on, has a chance
	 * of spawning a random encounter. (Eg. Tall Grass)
	 * @return boolean
	 */
	public void checkForEnemies()
	{
		int monsterCount = 0;
		
		// check NSWE of player position for enemies
		if(prow!=BROWS-1 && board[prow+1][pcol].isMonster())
			monsterCount++;
		else if(prow!=0 && board[prow-1][pcol].isMonster())
			monsterCount++;
		else if(pcol!=BCOLS-1 && board[prow][pcol+1].isMonster())
			monsterCount++;
		else if(pcol!=0 && board[prow][pcol-1].isMonster())
			monsterCount++;
		
		// check for chance of random encounter on certain terrain (Eg. Tall Grass)
		if(board[prow][pcol].isRandomEncounter())
		{
			Random rand = new Random();
			double r = rand.nextDouble(); // 0.0 to 1.0
			if(r <= percentChanceOfEncounter) // % chance of random encounter
			{
				// A surprise encounter happens!
				monsterCount++;
				playSound("Hit.wav");
			}
		}
		
		encounters += monsterCount; // count the number
		
		if(monsterCount>0)
		{
			Object[] options = {"Fight!", "Flee!"};
			int answer= printCustomQuestion("What will you do?", options);
			if(answer==0)
			{
				// go to combat tab
				viewCombatPanel();
			}
			else
			{
				// stay on the Map tab
				viewMapPanel();
			}
		}
		
		return;
	}
	
	/**
	 * Warps the player's position to the twin warp on the map.
	 * -8 and -9 represent the two warps. Entering either warp,
	 * will send the player to the other end. (Two-Way)
	 * 
	 */
	public void steppedOnWarp()
	{
		int a = board[prow][pcol].getTerrain();
		
		if(warpingEnabled==true && (a==WarpAID || a==WarpBID))
		{
			// We must find the other warp, according to our current warp id
			int b = WarpAID;
			if(a == WarpAID)
				b = WarpBID;
			
	        for (int i=0; i<BROWS; i++)
	        {
	            for (int j=0; j<BCOLS; j++)
	            {
	            	// find the other warp
	            	if(board[i][j].getTerrain() == b)
	            	{
	            		// warp the player
	            		// delay here
	            		repositionPlayer(i,j);
	            		((GridGUI) map).repositionScrollBarsToPlayer();
	            		movePlayerVision();
	            		warps++;
	            		break;
	            	}
	            }
	        }
		}
		return;
	}
	
	/**
	 * Places the player to (x,y) (Row,Col) coordinate, and overwrites
	 * whatever is in the way.
	 * 
	 * This function is best used, for warping, or quick setting the
	 * player's position. It does not check for valid moves, according
	 * to the style of the game.
	 * 
	 * Use movePlayer(x,y) to perform logical, game style movement.
	 * 
	 * @return void
	 * Throws Exception if x and y are not valid locations.
	 */
	public void repositionPlayer(int x, int y)
	{
    	// erase the player
        board[prow][pcol].setEntity(EmptyID);
        
        try
        {
        	// try to place the player, at the (x,y) coordinate
        	prow = x;
        	pcol = y;
        	board[prow][pcol].setEntity(PlayerID);
        }
        catch(Exception e)
        {
        	printError("Whoops!\n\n"+e.getMessage());
        }
        
        return;
	}
	
	/**
	 * Moves the player to the (x,y) coordinate, if possible. This
	 * function will follow game-style logic when moving the player,
	 * and will not just "warp" the player to the x,y coordinate.
	 * 
	 * Use repositionPlayer(x,y) if you need to immediately move
	 * the player, and replace whatever object is in the way.
	 * @param x
	 * @param y
	 */
	public void movePlayer(int x, int y)
	{
		boolean player_has_moved = false;
		
		// check for boundaries
		boolean edgeOfMap = ( 
				( prow==BROWS-1 && x>0 ) 
				|| ( pcol==BCOLS-1 && y>0 ) 
				|| ( prow==0 && x<0 ) 
				|| ( pcol==0 && y<0 ) );
		boolean nearEdgeOfMap = (
				( prow+x==BROWS-1 && x>0 ) 
				|| ( pcol+y==BCOLS-1 && y>0 )
				|| ( prow+x==0 && x<0 )
				|| ( pcol+y==0 && y<0 ) );
		
		// quick exit, if they try to move out of bounds
		if(edgeOfMap || (nearEdgeOfMap && leftShift))
		{
			if(showHintsEnabled) // show popup hint
				printInfo("You cannot move off the map!");
			return;
		}
		
		// check if the next spot is empty, <0.
        if(!leftShift && board[prow+x][pcol+y].isEmptySpace())
        {       	
        	// move the player
        	repositionPlayer(prow+x,pcol+y);
            player_has_moved = true;
            steps++;
        }
        // check if the next spot is pushable like a rock
        else if(!leftShift && board[prow+x][pcol+y].isPushable())
        {
        	// is the next location behind the rock, an empty space?
        	if(!nearEdgeOfMap && board[prow+x+x][pcol+y+y].isEmptySpace())
        	{
        		// if so, then move the pushable object forward one space
        		board[prow+x+x][pcol+y+y].setEntity(board[prow+x][pcol+y].getEntity());
        		
        		// remove the old rock
        		board[prow+x][pcol+y].setEntity(EmptyID);
        		
            	// move the player
        		repositionPlayer(prow+x,pcol+y);
                player_has_moved = true;
                steps++;
                objectsPushed++;
        	}
        	// is the next location behind the rock, a hole?
        	else if(!nearEdgeOfMap && board[prow+x+x][pcol+y+y].isHole())
        	{
        		// if so, then move the rock into the hole
        		// leaving just normal ground where the rock was
        		
            	// move the player
        		repositionPlayer(prow+x,pcol+y);
                player_has_moved = true;
                steps++;
                objectsPushed++;
                objectsPushedInHoles++;
        	}
        }
        // check if the player can jump over spot
        else if(leftShift && board[prow+x+x][pcol+y+y].isEmptySpace()
        		&& ( board[prow+x][pcol+y].isHole()
        		|| board[prow+x][pcol+y].isEmptySpace()))
        {
        	// if so, we can jump over the hole or ground
        	// move the player two spaces
        	repositionPlayer(prow+x+x,pcol+y+y);
            player_has_moved = true;
            hops++;
        }
        
        
        // reposition the "camera" if they moved
        if(player_has_moved)
        {
        	((GridGUI) map).repositionScrollBarsToPlayer();
			movePlayerVision();
			steppedOnWarp();
			checkForExit();
			checkForEnemies();
        } 
        else
        {
        	// bumped into something
        	// reply by playing a sound file
        	playSound("Hit.wav");
        	checkForEnemies();
        	
        	// show hint if they bumped a hole
        	if(showHintsEnabled && board[prow+x][pcol+y].isHole())
        		printInfo("You can hop over holes by\nholding down the left-shift key.");
        }
		
        return;
	} // end of movePlayer()
	
	/**
	 * Sets the area around the player visible according to how
	 * far the player's vision reaches, if fogOfWar is enabled.
	 * @return void
	 */
	public void movePlayerVision()
	{
		if(fogOfWar) // only works on if fog is enabled
		{
	        for (int i=0; i<BROWS; i++)
	        {
	            for (int j=0; j<BCOLS; j++)
	            {
	            	// if its inside of the player's vision range, then set visible
	            	if(i < prow + playerVisionRange && i > prow - playerVisionRange &&
	            			j < pcol + playerVisionRange && j > pcol - playerVisionRange)
	            	{
	            		// inside vision range
	            		board[i][j].setFog(EmptyID);
	            		board[i][j].setVisible(true);
	            	}
	            	else if(i == prow + playerVisionRange
	            			&& j < pcol + playerVisionRange
	            			&& j > pcol - playerVisionRange)
	            	{
	            		// edge of lower vision
	            		board[i][j].setFog(FogBottomID);
	            		board[i][j].setVisible(true);
	            	}
	            	else if(i == prow - playerVisionRange
	            			&& j < pcol + playerVisionRange
	            			&& j > pcol - playerVisionRange)
	            	{
	            		// edge of upper vision
	            		board[i][j].setFog(FogTopID);
	            		board[i][j].setVisible(true);
	            	}
	            	else if(j == pcol + playerVisionRange
	            			&& i < prow + playerVisionRange
	            			&& i > prow - playerVisionRange)
	            	{
	            		// edge of right-side vision
	            		board[i][j].setFog(FogRightID);
	            		board[i][j].setVisible(true);
	            	}
	            	else if(j == pcol - playerVisionRange
	            			&& i < prow + playerVisionRange
	            			&& i > prow - playerVisionRange)
	            	{
	            		// edge of left-side vision
	            		board[i][j].setFog(FogLeftID);
	            		board[i][j].setVisible(true);
	            	}
	            	else if(mappingEnabled && board[i][j].isVisible())
	            	{
	            		// was previously seen, but now out of range
	            		board[i][j].setFog(FogCenterID);
	            		board[i][j].setVisible(true);
	            	}
	            	else // out of vision range
	            	{
	            		board[i][j].setFog(EmptyID);
	            		board[i][j].setVisible(false);
	            	}
	            }
	        }
		}
	} // end of movePlayerVision()
	
	/**
	 * Sets all of the map's tiles visible or invisible
	 * @return void
	 */
	public void setFog(boolean f)
	{
		if(fogOfWar)
		{
	        for (int i=0; i<BROWS; i++)
	        {
	            for (int j=0; j<BCOLS; j++)
	            {
	            	board[i][j].setVisible(!f);
	            	board[i][j].setFog(0);
	            }
	        }
		}
	}
	
	/**
	 * Randomizes the current board with new images and ids
	 * 
	 * This function is for testing purposes only. Official game levels
	 * should not use this function, since the levels generated may be
	 * impossible for the player to complete.
	 */
	public void randomizeBoard()
	{
        // randomly build a testing grid with rocks, holes, grass, and monsters
        Random rand = new Random();
        boolean doorPlaced = false;
        boolean warpAPlaced = false;
        boolean warpBPlaced = false;
        int temp;
        numOfMonsters = 0;
        
        if(blinkOnExit) // turn off display briefly
        	((GridGUI) map).setVisible(false);
        
        for (int i=0; i<BROWS; i++)
        {
            for (int j=0; j<BCOLS; j++)
            {
            	// randomly place circles
                temp = rand.nextInt(51) - 2; // random -2 to 48
                
                // randomly scatter "holes"
                if(temp==1)
                {
                	board[i][j].resetObject(DirtID,HoleID,EmptyID);
                }
                // randomly scatter "rocks"
                else if(temp==-1 || temp==-2)
                {
                	board[i][j].resetObject(DirtID,RockID,EmptyID);
                }
                // randomly make "monsters"
                else if(temp==7)
                {
                	board[i][j].resetObject(DirtID,LavaMonsterID,EmptyID);
                	numOfMonsters++;
                }
                // place warp 'a'
                else if(warpingEnabled && !warpAPlaced && temp==8)
                {
                	board[i][j].resetObject(WarpAID,EmptyID,EmptyID);
                	warpAPlaced = true;
                }
                // place warp 'b'
                else if(warpingEnabled && !warpBPlaced && temp==9 && (i >= BROWS/2))
                {
                	board[i][j].resetObject(WarpBID,EmptyID,EmptyID);
                	warpBPlaced = true;
                }
                // randomly place one door per map
                else if(!doorPlaced && (temp==10 || (i==3 && j==3)))
                {
                	board[i][j].resetObject(GrassID,EmptyID,ExitID);
                	doorPlaced = true;
                }
                else
                {
                	// everything else is dirt, grass, or tall grass
                	temp = rand.nextInt(6); // 0 - 5
                	if(temp>=4)
                		board[i][j].resetObject(GrassID,EmptyID,TallGrassID);
                	else if(temp==3)
                		board[i][j].resetObject(DirtID,EmptyID,EmptyID);
                	else
                		board[i][j].resetObject(GrassID,EmptyID,EmptyID);
                }
            }
        }
        
        if(blinkOnExit) // show display
        	((GridGUI) map).setVisible(true);
        
        if(fogOfWar)
        	setFog(true);
        
        return;
	}
	
	@Override
	public void actionPerformed(ActionEvent Trigger)
	{
		// if the user clicks on a menu button
		
		if(Trigger.getSource() == newItem)
		{
			int answer = printYesNoQuestion("Are you sure you want\nto start a new map?");
			if(answer==0)
				newGame();
		}
		else if(Trigger.getSource() == saveItem)
		{
			// save the game
			printError("Save unsuccessful!");
		}
		else if(Trigger.getSource() == quitItem)
        {
			int answer = printYesNoQuestion("Are you sure you want\nto quit? Any progress made\nbe lost.");
			if(answer==0)
				endGame();
        }
        else if(Trigger.getSource() == teleportItem)
        {
        	repositionPlayer(prowStart,pcolStart);
        	((GridGUI) map).repositionScrollBarsToPlayer();
        	movePlayerVision();
        }
        else if(Trigger.getSource() == statsItem)
        {
        	printInfo(getStatistics());
        }
        else if(Trigger.getSource() == enableFogItem)
        {
        	if(fogOfWar)
        	{
        		setFog(false);
        		fogOfWar = false;
        		mappingEnabled = false; // mapping is useless without fog
        		enableMappingItem.setEnabled(false);
        	}
        	else
        	{
        		setFog(true);
        		fogOfWar = true;
        		movePlayerVision();
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
        		removeWarps();
        	}
        	else
        	{
        		warpingEnabled = true;
        		printInfo("Warping will be enabled on the next level.");
        	}
        }
        else if(Trigger.getSource() == enableMappingItem)
        {	
        	if(mappingEnabled)
        		mappingEnabled = false;
        	else
        		mappingEnabled = true;
        }
        else if(Trigger.getSource() == enableHintsItem)
        {
        	if(showHintsEnabled)
        		showHintsEnabled = false;
        	else
        		showHintsEnabled = true;
        }
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
		else
			klok.pause();
	}

	@Override
	public void focusLost(FocusEvent e) {
		// Auto-generated method stub
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
	 * Resets the current board size.
	 * 
	 * WARNING! You should clear the current board with
	 * deleteGridObjects() before you use this function!
	 * 
	 * @param rows
	 * @param columns
	 */
	public void setBoardSize(int rows, int columns)
	{
		if(rows>1 && columns>1 && rows<MAXIMUMSIZE && columns < MAXIMUMSIZE)
		{
			BROWS = rows;
			BCOLS = columns;
		}
		else
			printError("Oops!\n\nsetBoardSize(x,y) cannot take values\nlower than 2 or greater than "+MAXIMUMSIZE+"!");
	}
	
	/**
	 * Saves to file, the current game in progress.
	 */
	public void saveGame()
	{
		// TODO
	}
	
	/**
	 * Loads from file, a game in progress.
	 */
	public void loadGame()
	{
		// TODO
	}
	
	/**
	 * Loads from file, the next map for this game.
	 */
	public void loadMap()
	{
    	// delete old map and make a new empty one
    	deleteGridObjects();
    	createGridObjects();
    	
		// load a new map
		randomizeBoard();
		repositionPlayer(prowStart,pcolStart);
		((GridGUI) map).repositionScrollBarsToPlayer();
		movePlayerVision(); // for fog, if enabled
		
		// update the old quest status, if there was one
		if(questsCompleted!=0)
		{
			updateQuestStatus(questsTotal-1, "Complete!", Color.BLUE);
		}
		
		// reset level statistics
		resetStatistics();

		// add new quest to messageGUI
		addQuest(questsTotal,Hideout,"Find the Exit","Try to find the exit in this level.","Started");
		
		questsTotal++;
	}
	
} // end of GameEngine
// --------------------
