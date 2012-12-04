package rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;
import javax.sound.midi.*;
import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Game Engine class
 * 
 * The brains behind the game's functionality and features.
 * 
 * @author Austin Delamar
 * @version 10/11/2012
 * 
 */
public class GameEngine implements ActionListener, FocusListener,
		ClockListener, ListSelectionListener {

	// Game Objects
	private GameObjects GO;
	
	// Game Engine specific variables
	protected final int MAXIMUMSIZE = 100; // Max board size for rows and
											// columns
	protected int Window_Width = 600; // window size
	protected int Window_Height = 600;
	protected int X_DIM = 600; // default frame size
	protected int Y_DIM = 600;
	protected int Panel_X = 0; // default panel location
	protected int Panel_Y = 0;
	protected final int C_WIDTH = 25; // object image size
	protected final int C_HEIGHT = 25; // object image size
	protected GridObject[][] board; // the maximum board size
	protected ArrayList<QuestObject> quests; // list of quest messages
	protected Clock klok; // a timer to control other settings
	protected Sequencer musicStream; // a sequencer to play background music
	protected Sequencer soundStream; // a sequencer to play sound effects
	protected int clockSpeed = 1000; // time between ticks in milliseconds
	protected boolean gameStarted = false;
	protected boolean viewingMap = false;

	// GridGUI specific variables
	protected int BCOLS = 40; // board default column size
	protected int BROWS = 30; // board default row size
	protected final int G_X_DIM = BCOLS * (C_WIDTH); // grid panel dimensions
	protected final int G_Y_DIM = BROWS * (C_HEIGHT); // grid panel dimensions
	protected int prow = 0; // player's row position
	protected int pcol = 0; // player's col position
	protected int erow = 0;
	protected int ecol = 0;
	protected int prowStart = BROWS - 1; // initial start position
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
	protected int PlayerID = 1024;
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
	protected int BagID = 6;
	protected int ChestID = 66;
	protected int SpikeID = 7;
	protected int MushroomID = 8;
	protected int BeartrapID = 9;
	protected int SpiralID = 10;
	protected int SignID = 11;

	// special Frame features
	protected boolean musicEnabled = true;
	protected boolean soundEnabled = true;
	protected boolean fullScreenMode = true;
	protected int opacityLevel = 0; 
	protected Color backgroundColor = Color.black;
	protected Color foregroundColor = Color.white;
	protected Color highlightColor = Color.blue;

	// special mapPanel features
	protected boolean showHintsEnabled = true;
	protected boolean fogOfWar = true;
	protected boolean mappingEnabled = true;
	protected boolean exitGlowEnabled = true;
	protected int playerVisionRange = 5;
	protected boolean warpingEnabled = true;
	protected boolean clearStatsPerLevel = false;
	protected int monsterGridSpeed = 2; // monster moves after X seconds
	protected double percentChanceOfEncounter = 0.0;

	// special Grid variables
	protected int hops = 0; // # of jumps taken
	protected int steps = 0; // # of steps taken
	protected int encounters = 0; // # of battle encounters
	protected int itemsCollected = 0; // # of items found on map
	protected int traps = 0; // # of traps triggered
	protected int objectsPushed = 0; // # of rock pushes
	protected int objectsPushedInHoles = 0; // # of rocks pushed into holes
	protected int warps = 0; // # of times they warped
	protected int keysPushed = 0; // # of keyboard buttons pushed
	protected int numOfMonsters = 0; // keep track of how many monsters there
										// are

	// special Combat variables
	protected int kills = 0; // # of enemies killed
	protected int deaths = 0; // # of times a character died
	protected int fightsWon = 0; // # of battles won
	protected int fightsLost = 0; // # of fights lost
	protected int fightsFled = 0; // # of fights fled from
	protected int damageDealt = 0; // # of damage dealt in battle
	protected int damageTaken = 0; // # of damage taken in battle
	protected int damageHealed = 0; // # of damage healed in battle

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
	protected ImageIcon Spike = new ImageIcon("images/Spike.png");
	protected ImageIcon Beartrap = new ImageIcon("images/Beartrap.png");
	protected ImageIcon Spiral = new ImageIcon("images/Spiral.gif");
	protected ImageIcon Mushroom = new ImageIcon("images/Mushroom.png");
	protected ImageIcon Hideout = new ImageIcon("images/Hideout.png");
	protected ImageIcon Sign = new ImageIcon("images/Sign.png");
	protected ImageIcon Bag = new ImageIcon("images/Bag.png");
	protected ImageIcon Chest = new ImageIcon("images/TreasureChest.png");
	protected ImageIcon Backpack = new ImageIcon("images/Backpack.png");
	protected ImageIcon Book = new ImageIcon("images/Book.png");
	protected ImageIcon InventoryIcon = new ImageIcon("images/InventoryIcon.jpg");
	protected ImageIcon ListIcon = new ImageIcon("images/ListIcon.jpg");
	protected ImageIcon MailIcon = new ImageIcon("images/MailIcon.jpg");
	protected ImageIcon DrawIcon = new ImageIcon("images/DrawIcon.jpg");
	protected ImageIcon RPGLogo = new ImageIcon("images/RPGLogo.png");
	protected ImageIcon GameOverImage = new ImageIcon("images/GameOver.png");
	protected ImageIcon FightImage = new ImageIcon("images/fight.gif");

	// sound files
	protected String menuTheme = "sounds/Golden Sun Menu Screen.mid";
	protected String overworldTheme = "sounds/Golden Sun Over World.mid";
	protected String encounter = "sounds/Golden Sun Trouble Encounter.mid";
	protected String battleTheme = "sounds/Golden Sun Battle Theme.mid";
	protected String bossBattle = "sounds/Golden Sun Boss Battle.mid";
	protected String battleStart = "sounds/Golden Sun Trouble Encounter.mid";
	protected String victory = "sounds/Golden Sun Victory Fanfare.mid";
	protected String gameOver = "sounds/Golden Sun Game Over.mid";
	protected String revival = "sounds/Golden Sun PartyMemberRevival.mid";

	// default gui parts
	private JFrame window;
	private JMenuBar menubar;
	private JMenuItem quitItem;
	private JMenuItem teleportItem;
	private JMenuItem statsItem;
	private JMenuItem newItem;
	private JMenuItem saveItem;
	protected JMenuItem time;
	private JCheckBoxMenuItem enableMusicItem;
	private JCheckBoxMenuItem enableSoundItem;
	private JCheckBoxMenuItem enableHintsItem;
	private JCheckBoxMenuItem enableFogItem;
	private JCheckBoxMenuItem enableWarpItem;
	private JCheckBoxMenuItem enableMappingItem;
	protected JLayeredPane layers;
	protected JPanel loadingScreen;
	protected JPanel opacityScreen;
	protected JPanel ingameMenu;
	protected JPanel gameover;
	protected JPanel mainmenu;
	protected JPanel options;
	protected JPanel mapPanel;
	protected JPanel combatPanel;
	protected JTabbedPane tabMenu;
	protected JPanel inventoryPanel;
	protected JPanel statsPanel;
	protected JPanel questPanel;

	// keep track of keyboard keys being held down
	protected boolean up = false; // 38
	protected boolean down = false; // 40
	protected boolean left = false; // 37
	protected boolean right = false; // 39
	protected boolean leftShift = false; // 16
	protected boolean spaceBar = false; // 32

	// Combat Engine Fields
	private Random randomNumberGenerator = new Random();
	int accumulatedExp = 0;
	Entity actor;
	ArrayList<Entity> enemies;
	String event;
	boolean combatOver;
	Stack<Entity> turnStack;
	int combatResult;
	ArrayList<Entity> combatants;
	ArrayList<Entity> characters;
	// end combatPanel engine fields

	// InventoryGUI variables	
	protected DefaultListModel<Item> itemListModel = new DefaultListModel<Item>();
	protected JList<Item> itemList = new JList<Item>(itemListModel); // list to h		
	private int characterIndex = 0; // index to track selected character
	private int itemIndex = 0; 

	// Level up variables
	protected int base = 12;
	protected int factor = 3;
	protected int pointsPerLevel = 3;
	protected boolean hasLeveledUp = false;

	/**
	 * Constructor for GameEngine
	 */
	public GameEngine() {
		// Create the JWindow and Frame to hold the Panels

		// program window
		window = new JFrame("GVSU RPG Senior Project");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// show window in full screen mode, if enabled
		window.setLocation(0,0);
		window.setResizable(false);
		if(fullScreenMode)
		{
			window.setExtendedState(JFrame.MAXIMIZED_BOTH);
			Window_Width = Toolkit.getDefaultToolkit().getScreenSize().width;
			Window_Height = Toolkit.getDefaultToolkit().getScreenSize().height -90;
			Panel_X = (Window_Width/2) - (X_DIM/2);
			Panel_Y = 10;
		}
		else
		{
			window.setSize(new Dimension(X_DIM, Y_DIM));
			Window_Width = X_DIM;
			Window_Height = Y_DIM;
			Panel_X = 0;
			Panel_Y = 0;
		}
		
		
		// First menu contains "Quit" and "Reset"
		menubar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu actionsMenu = new JMenu("Actions");
		JMenu optionsMenu = new JMenu("Options");

		quitItem = new JMenuItem("Quit");
		newItem = new JMenuItem("New Game");
		saveItem = new JMenuItem("Save Game");
		teleportItem = new JMenuItem("Teleport to Start");
		statsItem = new JMenuItem("Report Stats");
		enableMusicItem = new JCheckBoxMenuItem("Play Music");
		enableSoundItem = new JCheckBoxMenuItem("Play SFX");
		enableHintsItem = new JCheckBoxMenuItem("Show Hints");
		enableFogItem = new JCheckBoxMenuItem("Fog of War");
		enableMappingItem = new JCheckBoxMenuItem("Show Visited");
		enableWarpItem = new JCheckBoxMenuItem("Warps");

		// add listeners
		newItem.addActionListener(this);
		fileMenu.add(newItem);
		saveItem.addActionListener(this);
		fileMenu.add(saveItem);
		fileMenu.addSeparator();
		quitItem.addActionListener(this);
		fileMenu.add(quitItem);

		teleportItem.addActionListener(this);
		actionsMenu.add(teleportItem);
		statsItem.addActionListener(this);
		actionsMenu.add(statsItem);

		enableMusicItem.addActionListener(this);
		enableSoundItem.addActionListener(this);
		enableHintsItem.addActionListener(this);
		enableFogItem.addActionListener(this);
		enableWarpItem.addActionListener(this);
		enableMappingItem.addActionListener(this);
		optionsMenu.add(enableMusicItem);
		optionsMenu.add(enableSoundItem);
		optionsMenu.add(enableHintsItem);
		optionsMenu.add(enableFogItem);
		optionsMenu.add(enableMappingItem);
		optionsMenu.add(enableWarpItem);

		enableMusicItem.setState(musicEnabled);
		enableSoundItem.setState(soundEnabled);
		enableHintsItem.setState(showHintsEnabled);
		enableFogItem.setState(fogOfWar);
		enableWarpItem.setState(warpingEnabled);
		enableMappingItem.setState(mappingEnabled);
		// disable unnecessary options
		if (!fogOfWar) {
			enableMappingItem.setEnabled(false); // mapping is useless unless
													// you have fogOfWar enabled
			mappingEnabled = false;
		}

		// show what time it is
		time = new JMenuItem("Time: X", JLabel.RIGHT);
		time.setForeground(highlightColor);

		menubar.add(fileMenu);
		menubar.add(optionsMenu);
		menubar.add(actionsMenu);
		menubar.add(Box.createHorizontalStrut(100));
		menubar.add(time);
		// place the menu bar
		window.setJMenuBar(menubar);

		// --------------------------------------------------------
		// make the main menu gui
		mainmenu = new MainMenuGUI(this);
		mainmenu.setLocation(0,0);
		mainmenu.setSize(Window_Width, Window_Height);
		// --------------------------------------------------------

		// --------------------------------------------------------
		// make the options gui
		options = new OptionsGUI(this);
		options.setLocation(0, 0);
		options.setSize(Window_Width, Window_Height);
		// --------------------------------------------------------

		// --------------------------------------------------------
		// make the game over gui
		gameover = new GameOverGUI(this);
		gameover.setLocation(Panel_X, Panel_Y);
		gameover.setSize(X_DIM, Y_DIM +60);
		// --------------------------------------------------------
		
		// --------------------------------------------------------
		// make an opacityScreen (translucent) screen
		opacityScreen = new JPanel(new BorderLayout());
		opacityScreen.setBackground(new Color(0,0,0,opacityLevel));
		opacityScreen.setLocation(0, 0);
		opacityScreen.setPreferredSize(new Dimension(Window_Width, Window_Height));
		opacityScreen.setSize(Window_Width, Window_Height);
		// --------------------------------------------------------
		
		// --------------------------------------------------------
		// make a loading screen
		loadingScreen = new JPanel();
		loadingScreen.setLayout(new BorderLayout());
		loadingScreen.setBackground(backgroundColor);
		JLabel loadingText = new JLabel("Loading", JLabel.CENTER);
		loadingText.setIcon(new ImageIcon("images/loading.gif"));
		loadingText.setHorizontalTextPosition(JLabel.CENTER);
		loadingText.setVerticalTextPosition(JLabel.BOTTOM);
		loadingScreen.add(loadingText, BorderLayout.CENTER);
		loadingScreen.setLocation(Panel_X, Panel_Y);
		loadingScreen.setPreferredSize(new Dimension(X_DIM, Y_DIM + 60));
		loadingScreen.setSize(X_DIM, Y_DIM +60);
		// --------------------------------------------------------

		// create layers panel
		// 0 -- is the map
		// 10 -- is the combat
		// 25 -- is the tabMenu
		// 50 -- is the in-game menu
		// 100 -- is for the MainMenu
		// 125 -- is for the Options Menu
		// 140 -- is for the GameOver Screen
		// 190 -- is for the Opacity Screen
		// 200 -- is for the Loading Screen
		layers = new JLayeredPane();
		layers.setLayout(new BorderLayout());
		layers.add(mainmenu);
		layers.add(options);
		layers.add(gameover);
		//layers.add(opacityScreen);
		layers.add(loadingScreen);
		layers.setLayer(mainmenu, 100);
		layers.setLayer(options, 125);
		layers.setLayer(gameover, 140);
		//layers.setLayer(opacityScreen, 190);
		layers.setLayer(loadingScreen, 200);

		// add the layers to the window
		window.add(layers, BorderLayout.CENTER);

		// pack
		window.pack();
		window.setVisible(true);

		// create the clock and clock status bar at the bottom
		// listen to the timer,
		// on every tick, event() will be called.
		klok = new Clock();
		klok.register(this); // The Engine listens to the Clock
		klok.setRate(clockSpeed); // time passes per tick by this speed in
									// milliseconds

		// creates two Sequencers and initializes them.
		// this is used to play music and sound effects
		initializeAudioStreams();

		// view main menu (also plays music)
		viewMainMenu();
	}
	
	/**
	 * A constructor for creating all the layers(tabs) of the game.
	 * Map, Combat, Stats, Inventory, and Quest panels are
	 * create here and added to the JFrame.
	 */
	public void createGameLayers()
	{
		// --------------------------------------------------------
		// create the GridGui mapPanel tab
		mapPanel = new GridGUI(this);
		mapPanel.setLocation(0,0);
        mapPanel.setPreferredSize(new Dimension(Window_Width, Window_Height));
		mapPanel.setSize(Window_Width, Window_Height);
		// --------------------------------------------------------

		// --------------------------------------------------------
		// create the CombatGUI combatPanel tab
		combatPanel = new CombatGUI(this);
		combatPanel.setLocation(Panel_X,Panel_Y);
		combatPanel.setSize(X_DIM, Y_DIM);
		// --------------------------------------------------------

		// --------------------------------------------------------
		// create the InventoryGUI inventoryPanel tab
		inventoryPanel = new InventoryGUI(this);
		inventoryPanel.setLocation(Panel_X,Panel_Y);
		inventoryPanel.setSize(X_DIM, Y_DIM);
		// --------------------------------------------------------

		// --------------------------------------------------------
		// create the StatsGUI statsPanel tab
		statsPanel = new StatsGUI(this);
		statsPanel.setLocation(Panel_X,Panel_Y);
		statsPanel.setSize(X_DIM, Y_DIM);
		// --------------------------------------------------------

		// --------------------------------------------------------
		// create the QuestGUI questPanel tab
		questPanel = new QuestGUI(this);
		questPanel.setLocation(Panel_X,Panel_Y);
		questPanel.setSize(X_DIM, Y_DIM);
		// --------------------------------------------------------
		
		// --------------------------------------------------------
		// make the in-game menu screen
		ingameMenu = new SubMenuGUI(this);
		ingameMenu.setLocation((Window_Width/2)-100, (Window_Height/2)-200);
		ingameMenu.setSize(200, 400);
		// --------------------------------------------------------
		
		JLabel iLabel = new JLabel("Items");
		iLabel.setIcon(InventoryIcon);
		iLabel.setIconTextGap(5);
		iLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		JLabel sLabel = new JLabel("Stats");
		sLabel.setIcon(ListIcon);
		sLabel.setIconTextGap(5);
		sLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		JLabel qLabel = new JLabel("Quests");
		qLabel.setIcon(MailIcon);
		qLabel.setIconTextGap(5);
		qLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		
		// --------------------------------------------------------
		// make the tabs menu screen
		tabMenu = new JTabbedPane();
		tabMenu.setLocation(Panel_X, Panel_Y);
		tabMenu.setSize(X_DIM, Y_DIM);
		tabMenu.addTab("Items",inventoryPanel);
		tabMenu.addTab("Stats",statsPanel);
		tabMenu.addTab("Quests",questPanel);
		tabMenu.setTabComponentAt(0, iLabel);
		tabMenu.setTabComponentAt(1, sLabel);
		tabMenu.setTabComponentAt(2, qLabel);
		// --------------------------------------------------------
				
		layers.add(mapPanel);
		layers.add(tabMenu);
		layers.add(ingameMenu);
		layers.add(combatPanel);
		layers.setLayer(mapPanel, 0);
		layers.setLayer(combatPanel, 10);
		layers.setLayer(tabMenu, 25);
		layers.setLayer(ingameMenu, 50);
	}
	
	public void deleteGameLayers() {
		// delete all components to the in-game (tabs)
		layers.remove(mapPanel);
		layers.remove(combatPanel);
		tabMenu.removeTabAt(0);
		tabMenu.removeTabAt(0);
		tabMenu.removeTabAt(0);
		layers.remove(tabMenu);
		layers.remove(ingameMenu);
		tabMenu = null;
		ingameMenu = null;
		mapPanel = null;
		combatPanel = null;
		inventoryPanel = null;
		statsPanel = null;
		questPanel = null;
	}
	
	/**
	 * Creates the GameObjects class that holds all necessary information
	 * for serialization of the game.
	 */
	public void createGameObjects()
	{
		GO = new GameObjects();
	}

	/**
	 * Creates each GridObject according to the number of rows and columns in
	 * BROWS and BCOLS.
	 */
	public void createGridObjects() {
		// create the board
		board = new GridObject[BROWS][BCOLS];

		for (int i = 0; i < BROWS; i++) {
			for (int j = 0; j < BCOLS; j++) {
				board[i][j] = new GridObject(this, null, null, null);
				((GridGUI) mapPanel).addGridObject(i, j); // place on grid panel
			}
		}
	}

	/**
	 * Deletes each GridObject, in order to restart a new board.
	 */
	public void deleteGridObjects() {
		board = null;
		// remove each GridObject reference from the grid panel
		((GridGUI) mapPanel).removeAllGridObjects();
	}

	/**
	 * Creates a list of empty quests
	 */
	public void createQuestList() {
		// create list of quests
		quests = new ArrayList<QuestObject>();
	}

	/**
	 * Deletes the list of quests
	 */
	public void deleteQuestList() {
		quests = null;
		// remove each of the quests
		((QuestGUI) questPanel).removeAllQuests();
	}
	
	/**
	 * Creates a demo list of characters
	 */
	public void createCharacters() {
		// combatPanel constructor
		characters = new ArrayList<Entity>(); // currentHealth, totalHealth,
												// attack, defense, speed
		Entity mario = new Entity(PlayerID, Player, "Mario", "", true, null,
				30, 30, 30, 30, 12, 10, 10, 5, 1);
		Entity luigi = new Entity(PlayerID, PlayerOutline, "Luigi", "", true,
				null, 30, 30, 30, 30, 10, 11, 9, 5, 1);
		Entity toad = new Entity(PlayerID, Mushroom, "Toad", "", true, null,
				15, 15, 50, 50, 10, 10, 15, 5, 1);
		Ability healingShroom = new Ability("Healing Mushroom", 1, 0, 20, 8);
		Ability fireball = new Ability("Fireball", 0, 1, 5, 10);
		Ability jump = new Ability("Jump", 0, 0, 5, 10);
		fireball.setLevel(2);
		healingShroom.setLevel(2);
		jump.setLevel(1);
		luigi.addAbility(fireball);
		mario.addAbility(fireball);
		mario.addAbility(jump);
		toad.addAbility(healingShroom);
		characters.add(mario);
		characters.add(luigi);
		characters.add(toad);
	}
	
	public void deleteCharacters() {
		characters = null;
	}
	
	/**
	 * Creates a demo list of items
	 */
	public void createItemBackpack() {
		// initialize array list of items

		addItem(new Item(MushroomID, Mushroom, "Mushroom",
				"Gives you 30 HP!", true, 0, 30, 0, 0, 0, 0, 0));
		addItem(new Item(RockID, Rock, "Rock",
				"Increases defense by 10", false, 0, 0, 0, 0, 0, 10, 0));
		addItem(new Item(RockID, Bag, "Magic Powder",
				"Gives you 30 MP!", true, 0, 0, 0, 30, 0, 0, 0));
		addItem(new Item(RockID, GlowingGem, "Gem",
				"Increases Max Mana by 20 MP", false, 0, 0, 20, 0, 0, 0, 0));
		addItem(new Item(SpikeID, Spike, "Spike Shield",
				"Increases attack by 5 and defense by 5", false, 0, 0, 0, 0, 5,
				5, 0));

		// initailize the list			    
		itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		itemList.setSelectedIndex(0);
		itemList.addListSelectionListener((ListSelectionListener) this);
	}
	
	public void deleteItemBackpack() {
		//itemListModel.clear();
	}
	
	public void getGameObjects()
	{
		
	}
	
	public void setGameObjects()
	{
		
	}

	/**
	 * A function to start a new game. This is not the function to start the
	 * next level!
	 */
	public void newGame() {
		// temporarily view the loading screen
		viewLoadingScreen();
		gameStarted = true;
		
		// choose player
		// TODO
		
		// load demo characters and items
		deleteCharacters();
		createCharacters();
		deleteItemBackpack();
		createItemBackpack();

		// restore all player's health and magic
		restoreAllCharacters();
		
		// create the game's layers (map, combat, stats...)
		createGameLayers();

		// delete old mapPanel and make a new empty one
		deleteGridObjects();
		createGridObjects();

		// delete old quests and make a new empty one
		deleteQuestList();
		createQuestList();

		// clear game variables
		resetStatistics();
		questsCompleted = 0;
		questsTotal = 0;
		questsActive = 0;
		questsFailed = 0;

		// choose maps/levels
		// TODO
		
		// load a new map
		loadMap();

		// reset clock
		klok.pause();
		klok.currentTime = 0;
		klok.run(Clock.FOREVER);
	}

	/**
	 * A function to end the game. This is not the function to end the current
	 * level!
	 */
	public void endGame() {
		// reset clock
		klok.pause();
		gameStarted = false;
		
		// temporarily view the loading screen
		viewLoadingScreen();

		// delete board, quests, characters, and items
		deleteGridObjects();
		deleteQuestList();
		deleteItemBackpack();
		deleteCharacters();
		deleteGameLayers();

		// clear game variables
		resetStatistics();
		questsCompleted = 0;
		questsTotal = 0;
		questsActive = 0;
		questsFailed = 0;

		// go to Main Menu
		viewMainMenu();
	}

	/**
	 * WARNING! Closes this Java Application! This is the same as clicking "X"
	 * on the program when its running.
	 */
	public void quitProgram() {
		// gracefully exit the entire game
		System.exit(0);
	}

	/**
	 * Pauses the game to quickly resume playing.
	 */
	public void pauseGame() {
		// pauses the clock
		klok.pause();

		// pauses Music
		stopMusic();

		// show fade grid
		setGridFade(true);

		// displays that the game is paused, click to resume
		JOptionPane.showMessageDialog(window,
				"Game is paused.\nClick OK to resume playing.", "Pause",
				JOptionPane.INFORMATION_MESSAGE);

		// resumes Music
		startMusic();

		// hide grid fade
		setGridFade(false);

		// resume clock ticking if they were viewing the Map
		if (viewingMap)
			klok.run(Clock.FOREVER);
	}
	
	/**
	 * Saves to file, the current game in progress.
	 */
	public void saveGame() {
		// pauses the clock
		klok.pause();

		// pauses Music
		stopMusic();

		// show fade grid
		setGridFade(true);
		
		// name a file to save to
		// open file chooser and load file
		JFileChooser jfc = new JFileChooser();
		jfc.setMultiSelectionEnabled(false);
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.setSelectedFile(new File("MySavedGame.rpg"));
		int path = jfc.showSaveDialog(window);
		
        if (path == JFileChooser.APPROVE_OPTION)
        {        	
        	File file = jfc.getSelectedFile();
        	if(file.getName().endsWith(".rpg"))
        	{
        		// create GO
        		createGameObjects();
        		
        		// give all serializable objects to GO
        		GO.setBoard(board);
        		GO.setCharacters(characters);
        		GO.setItems((Item[]) itemListModel.toArray());
        		GO.setQuests(quests);
        		
        		// save the GO object
        		FileOutputStream fos = null;
        		ObjectOutputStream oos = null;
        		try {
        			fos = new FileOutputStream(file);
					oos = new ObjectOutputStream(fos);
					oos.writeObject(GO);
					
					// save successful
					printInfo("The game was saved.");
					
				} catch (IOException e) 
				{
					printError(e.getMessage());
					e.printStackTrace();
				}
        		finally
        		{
        			try {
        				if(fos != null)
        					fos.close();
        				
        				if(oos != null)
        					oos.close();
					} catch (IOException e) {
						printError(e.getMessage());
						e.printStackTrace();
					}
        			
        		}
        	}
        	else if(!file.getName().endsWith(".rpg"))
        	{
        		// wrong file extension
        		printError("The file must end with '.rpg'");
        	}
        }
        else
        {
        	// bad selection
        	printError("The game was not saved.");
        }
		
        // resumes Music
        startMusic();

        // hide grid fade
        setGridFade(false);

        // resume clock ticking if they were viewing the Map
        if (viewingMap)
        	klok.run(Clock.FOREVER);
	}

	/**
	 * Loads from file, a game in progress.
	 */
	public void loadGame() {
		boolean loaded = false;
		
		// open file chooser and load file
		JFileChooser jfc = new JFileChooser();
		jfc.setMultiSelectionEnabled(false);
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		// temporarily view the loading screen
		viewLoadingScreen();
		
		int path = jfc.showOpenDialog(window);		
		
        if (path == JFileChooser.APPROVE_OPTION)
        {
        	File file = jfc.getSelectedFile();
        	if(file.getName().endsWith(".rpg"))
        	{
        		// read the GO object
        		FileInputStream fis = null;
        		ObjectInputStream ois = null;
        		try {
        			fis = new FileInputStream(file);
					ois = new ObjectInputStream(fis);
					GO = (GameObjects) ois.readObject();
					
					// load successful
					loaded = true;
					
				} catch (Exception e) 
				{
					printError(e.getMessage());
					e.printStackTrace();
				}
        		finally
        		{
        			try {
        				if(fis != null)
        					fis.close();
        				
        				if(ois != null)
        					ois.close();
					} catch (IOException e) {
						printError(e.getMessage());
						e.printStackTrace();
					}
        			
        		}
        	}
        	else if(!file.getName().endsWith(".rpg"))
        	{
        		// wrong file extension
        		printError("The file must end with '.rpg'");
        	}
        }
        
        if(loaded)
        {
        	// load all parameters and objects
        	gameStarted = true;
        	board = GO.getBoard();
        	quests = GO.getQuests();
        	characters = GO.getCharacters();
        	Item [] toItemList = GO.getItems();
        	int z = 0;
        	while(toItemList[z] != null){
        		addItem(toItemList[z]);
        		z++;
        	}
        	
        	BROWS = board.length;
        	BCOLS = board[0].length;
        	
        	// create the game's layers (map, combat, stats...)
    		createGameLayers();
        	
        	// search for player's position and exit position
        	// add gridobjects to gridgui
        	for(int i=0; i<BROWS; i++)
        		for(int j=0; j<BCOLS; j++)
        		{
        			if(board[i][j].isExit())
        			{
        				erow = i;
        				ecol = j;
        			}
        			if(board[i][j].isPlayer())
        			{
        				prow = i;
        				pcol = j;
        			}
        			((GridGUI) mapPanel).addGridObject(i, j); // place on grid panel
        		}    
        	
        	// place quests on the panel
        	for(QuestObject q : quests)
        		((QuestGUI) questPanel).addQuest(q);
        	questsCompleted = quests.size() - 1;
        	questsActive = 1;
        	questsFailed = 0;
        	questsTotal = quests.size();
    		
        	// game loaded successfully!
        	viewMapPanel();
        	
        	// update map view
        	movePlayerVision();
        }
        else
        {
        	// go back to the menu
        	viewMainMenu();
        }
	}

	/**
	 * Loads from file, the next map for this game.
	 */
	public void loadMap() {
		// temporarily view loading screen
		viewLoadingScreen();
		
		// delete old map and make a new empty one
		deleteGridObjects();
		createGridObjects();

		// load a new map
		randomizeBoard();
		repositionPlayer(prowStart, pcolStart);
		((GridGUI) mapPanel).repositionScrollBarsToPlayer();
		movePlayerVision(); // for fog, if enabled

		// update the old quest status, if there was one
		if (questsCompleted != 0) {
			updateQuestStatus(questsTotal - 1, "Complete!", highlightColor);
		}

		// reset level statistics
		resetStatistics();
		
		// view map
		viewMapPanel();
		
		// add new quest to messageGUI
		addQuest(
				questsTotal,
				GlowingGem,
				"Find the Gem!",
				"Try to find the rare glowing gem in this level. Watch out for lava monsters!",
				"Started");

		questsTotal++;
	}

	/**
	 * Display an error
	 * 
	 * @param e
	 */
	public void printError(String e) {
		// pause time if on the map
		if (viewingMap)
			klok.pause();

		// found an error and is printing it according to the view
		JOptionPane.showMessageDialog(window, e, "ERROR",
				JOptionPane.ERROR_MESSAGE);

		// resume time if on the map
		if (viewingMap)
			klok.run(Clock.FOREVER);
	}

	/**
	 * Display some information
	 * 
	 * @param s
	 */
	public void printInfo(String s) {
		// pause time if on the map
		if (viewingMap)
			klok.pause();

		// found some information to print
		JOptionPane.showMessageDialog(window, s, "Information",
				JOptionPane.INFORMATION_MESSAGE);

		// resume time if on the map
		if (viewingMap)
			klok.run(Clock.FOREVER);
	}

	/**
	 * Prompt a Question
	 * 
	 * @param t
	 * @return String
	 */
	public String printQuestion(String t) {
		// pause time if on the map
		if (viewingMap)
			klok.pause();

		String r = JOptionPane.showInputDialog(window, t, "Question",
				JOptionPane.QUESTION_MESSAGE);

		// resume time if on the map
		if (viewingMap)
			klok.run(Clock.FOREVER);

		return r;
	}

	/**
	 * Prompt a Yes/No Question
	 * 
	 * @param String
	 *            b
	 * @return int 0 means YES, 1 means NO, -1 means they clicked "X" close
	 */
	public int printYesNoQuestion(String b) {
		// pause time if on the map
		if (viewingMap)
			klok.pause();

		int r = JOptionPane.showConfirmDialog(window, b, "Question",
				JOptionPane.YES_NO_OPTION);

		// resume time if on the map
		if (viewingMap)
			klok.run(Clock.FOREVER);

		return r;
	}

	/**
	 * Prompt a Custom Question
	 * 
	 * @param String
	 *            question
	 * @param Object
	 *            [] choices
	 * @return int 0 means YES, 1 means NO, -1 means they clicked "X" close
	 */
	public int printCustomQuestion(String question, Object[] choices) {
		// pause time if on the map
		if (viewingMap)
			klok.pause();

		int r = JOptionPane.showOptionDialog(window, question, "Question",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, choices, choices[1]);

		// resume time if on the map
		if (viewingMap)
			klok.run(Clock.FOREVER);

		return r;
	}

	/**
	 * Forces player to view Quests
	 */
	public void viewQuestPanel() {
		loadingScreen.setVisible(false);
		gameover.setVisible(false);
		mainmenu.setVisible(false);
		options.setVisible(false);
		
		if(gameStarted)
		{
			tabMenu.setSelectedIndex(2);
			ingameMenu.setVisible(false);
			tabMenu.setVisible(true);
			combatPanel.setVisible(false);
		}
		
		viewingMap = false;
		klok.pause();
	}

	/**
	 * Forces player to view Combat
	 */
	public void viewCombatPanel() {
		loadingScreen.setVisible(false);
		gameover.setVisible(false);
		mainmenu.setVisible(false);
		options.setVisible(false);
		
		if(gameStarted)
		{
			mapPanel.setVisible(false);
			ingameMenu.setVisible(false);
			tabMenu.setVisible(false);
			combatPanel.setVisible(true);
		}
		
		combatPanel.requestFocus();
		viewingMap = false;
		klok.pause();
	}

	/**
	 * Forces player to view Inventory
	 */
	public void viewInventoryPanel() {
		loadingScreen.setVisible(false);
		gameover.setVisible(false);
		mainmenu.setVisible(false);
		options.setVisible(false);
		
		if(gameStarted)
		{
			tabMenu.setSelectedIndex(0);
			ingameMenu.setVisible(false);
			tabMenu.setVisible(true);
			combatPanel.setVisible(false);
		}
		
		viewingMap = false;
		klok.pause();

		// update the view
		((InventoryGUI) inventoryPanel).update();
	}

	public void viewStatsPanel() {
		loadingScreen.setVisible(false);
		gameover.setVisible(false);
		mainmenu.setVisible(false);
		options.setVisible(false);
		
		if(gameStarted)
		{
			tabMenu.setSelectedIndex(1);
			ingameMenu.setVisible(false);
			tabMenu.setVisible(true);
			combatPanel.setVisible(false);
		}
		
		viewingMap = false;
		klok.pause();

		// update the view
		((StatsGUI) statsPanel).reset();
		((StatsGUI) statsPanel).update();
		((StatsGUI) statsPanel).cycle(1);
		((StatsGUI) statsPanel).cycle(-1);
	}

	/**
	 * Forces player to view Map
	 */
	public void viewMapPanel() {
		loadingScreen.setVisible(false);
		gameover.setVisible(false);
		mainmenu.setVisible(false);
		options.setVisible(false);
		
		if(gameStarted)
		{
			mapPanel.setVisible(true);
			ingameMenu.setVisible(false);
			tabMenu.setVisible(false);
			combatPanel.setVisible(false);
		}
		
		mapPanel.requestFocus();
		viewingMap = true;
		klok.run(Clock.FOREVER);

		// play overworld music
		playMusic(overworldTheme, true);
	}

	/**
	 * Forces player to view the Main Menu
	 */
	public void viewMainMenu() {
		loadingScreen.setVisible(false);
		gameover.setVisible(false);
		mainmenu.setVisible(true);
		options.setVisible(false);
		
		if(gameStarted)
		{
			ingameMenu.setVisible(false);
			tabMenu.setVisible(false);
			combatPanel.setVisible(false);
		}
		
		viewingMap = false;
		klok.pause();
		mainmenu.requestFocus();

		// play menu music
		playMusic(menuTheme, true);
	}
	
	public void viewSubMenu() {
		loadingScreen.setVisible(false);
		gameover.setVisible(false);
		mainmenu.setVisible(false);
		options.setVisible(false);
		
		if(gameStarted)
		{
			ingameMenu.setVisible(true);
			tabMenu.setVisible(false);
			combatPanel.setVisible(false);
		}
		
		viewingMap = false;
		klok.pause();
	}

	public void viewOptions() {
		loadingScreen.setVisible(false);
		gameover.setVisible(false);
		mainmenu.setVisible(false);
		options.setVisible(true);
		
		if(gameStarted)
		{
			ingameMenu.setVisible(false);
			tabMenu.setVisible(false);
			combatPanel.setVisible(false);
		}
		
		viewingMap = false;
		klok.pause();
		options.requestFocus();
	}

	public void viewGameOverScreen() {
		loadingScreen.setVisible(false);
		gameover.setVisible(true);
		mainmenu.setVisible(false);
		options.setVisible(false);
		
		if(gameStarted)
		{
			ingameMenu.setVisible(false);
			tabMenu.setVisible(false);
			combatPanel.setVisible(false);
		}
		
		gameover.requestFocus();
		viewingMap = false;
		klok.pause();
	}

	/**
	 * Forces player to view the Loading Screen
	 */
	public void viewLoadingScreen() {
		loadingScreen.setVisible(true);
		gameover.setVisible(false);
		mainmenu.setVisible(false);
		options.setVisible(false);
		
		if(gameStarted)
		{
			ingameMenu.setVisible(false);
			tabMenu.setVisible(false);
			combatPanel.setVisible(false);
		}
		
		viewingMap = false;
		klok.pause();
	}

	/**
	 * Resets the statistics for the player
	 * 
	 * @return void
	 */
	public void resetStatistics() {
		hops = 0; // # of jumps taken
		steps = 0; // # of steps taken
		encounters = 0; // # of encounters
		itemsCollected = 0; // # of items collected on map
		traps = 0; // # of traps triggered
		objectsPushed = 0; // # of rock pushes
		objectsPushedInHoles = 0; // # of rocks pushed into holes
		warps = 0; // # of times they warped
		keysPushed = 0; // # of keyboard buttons pushed
	}

	/**
	 * Returns the current statistics for the player
	 * 
	 * @return String
	 */
	public String getStatistics() {
		return questsCompleted + " Quests Completed\n" + steps + " Steps\n"
				+ hops + " Hops\n" + encounters + " Battles\n" + itemsCollected
				+ " Items Found\n" + traps + " Traps Triggered\n"
				+ objectsPushed + " Objects Pushed\n" + objectsPushedInHoles
				+ " Objects Pushed into Holes\n" + warps + " Times Warped\n"
				+ keysPushed + " Keys Pushed\n";
	}

	/**
	 * Add another quest message to the MessageGUI unique id number imageicon
	 * name title message description (list of tasks) status description
	 * (started or complete)
	 * 
	 * @return void
	 */
	public void addQuest(int id, ImageIcon i, String t, String m, String s) {
		questsActive++;

		// create new jpanel to add to array list
		QuestObject q = new QuestObject(this, id, i, t, m, s);
		quests.add(q);

		// add new quest to QuestGUI
		((QuestGUI) questPanel).addQuest(q);

		// popup a hint for the player if they need a reminder
		if (showHintsEnabled) {
			Object options[] = { "View Now", "Close" };
			int result = printCustomQuestion("New Quest available!", options);
			if (result == 0)
				viewQuestPanel();
		} else
			viewMapPanel();
	}

	/**
	 * Updates the status of a Quest by its id. The color is optional, so you
	 * could make the letters appear BLUE when the quest is completed, or just
	 * make the letters appear BLACK if its not important.
	 * 
	 * @param id
	 * @param s
	 * @param c
	 */
	public void updateQuestStatus(int id, String s, Color c) {
		quests.get(id).setStatus(s, c);
		quests.get(id).setStatistics(
				"This Quests statistics:\n\n" + getStatistics());
	}

	/**
	 * Delete a message box. Throws an exception if the id is invalid
	 */
	public void removeQuest(int id) {
		try {
			quests.remove(id);
			((QuestGUI) questPanel).removeQuest(id);
		} catch (Exception e) {
			printError("Whoops!\n\n" + e.getMessage());
		}
	}

	/**
	 * Removes all warps from the mapPanel
	 * 
	 * @return void
	 */
	public void removeWarps() {
		// We must find the other warp, according to our current warp id
		for (int i = 0; i < BROWS; i++) {
			for (int j = 0; j < BCOLS; j++) {
				// find the other warp
				if ((board[i][j].terrain).id == WarpAID
						|| board[i][j].terrain.id == WarpBID) {
					// replace with null
					board[i][j].setTerrain(null);
				}
			}
		}
		return;
	}

	/**
	 * Removes enemies that are next to the player. Also morphs the enemies into
	 * the ID of the parameter. (Eg. 0=nothing, BagID=purple bag drop)
	 * 
	 * This allows some monsters to turn into various things after they "die".
	 * 
	 * This function should be called after combat is finished and the player
	 * has won in combat. Not if they fled or died.
	 */
	public void removeDefeatedEnemies(RPGObject i) {
		// remove any enemy objects that are NSWE from the player's current
		// position
		if (prow != BROWS - 1 && board[prow + 1][pcol].isMonster())
			board[prow + 1][pcol].setObject(i);

		if (prow != 0 && board[prow - 1][pcol].isMonster())
			board[prow - 1][pcol].setObject(i);

		if (pcol != BCOLS - 1 && board[prow][pcol + 1].isMonster())
			board[prow][pcol + 1].setObject(i);

		if (pcol != 0 && board[prow][pcol - 1].isMonster())
			board[prow][pcol - 1].setObject(i);
		return;
	}

	/**
	 * Removes all enemies from the game board.
	 * 
	 * This function should not be called. It could be used in a cheat code or
	 * some kind of bomb.
	 */
	public void removeAllEnemies() {
		for (int i = 0; i < BROWS; i++)
			for (int j = 0; j < BCOLS; j++)
				if (board[i][j].isMonster())
					board[i][j].setObject(null);
		return;
	}

	/**
	 * Check if the player's current position is over the exit portal for the
	 * level. If true, then they beat the level!
	 */
	public void checkForExit() {
		// did they stumble upon the exit door?
		if (board[prow][pcol].isExit()) {
			
			// play revival
			playMusic(revival, false);
			
			// add a fade over the grid
			setGridFade(true);

			questsCompleted++;

			if (questsCompleted % 3 == 0) // every three wins, print stats
			{
				printInfo("Highscore!\n\n" + getStatistics());
				printInfo("Way to go! You're a Laker for a Lifetime!");
			}

			printInfo("Congrats! You found the exit!");

			// start new map
			// replace with next level finder
			loadMap();
			setGridFade(false);
		}
		return;
	}

	/**
	 * check for enemies Scans the four surrounding locations, NSWE, for a
	 * monster. Also checks if the location the player is on, has a chance of
	 * spawning a random encounter. (Eg. Tall Grass)
	 * 
	 * If an enemy is found, this function starts combat!
	 * 
	 * @return void
	 */
	public void checkForEnemies() {
		int monsterCount = 0;
		boolean north=false,south=false,east=false,west=false;
		// keep track of Entity, not count

		// check NSWE of player position for enemies
		if (prow != BROWS - 1 && board[prow + 1][pcol].isMonster())
		{
			monsterCount++;
			south = true;
		}

		if (prow != 0 && board[prow - 1][pcol].isMonster())
		{
			monsterCount++;
			north = true;
		}

		if (pcol != BCOLS - 1 && board[prow][pcol + 1].isMonster())
		{
			monsterCount++;
			east = true;
		}

		if (pcol != 0 && board[prow][pcol - 1].isMonster())
		{
			monsterCount++;
			west = true;
		}

		// check for chance of random encounter on certain terrain
		if (board[prow][pcol].isRandomEncounter()) {
			Random rand = new Random();
			double r = (((double)rand.nextInt(100))+0.01) / 100.0; // 0.01 to 1.00
			if (r <= percentChanceOfEncounter) // % chance of random encounter
			{
				// A surprise encounter happens!
				monsterCount++;
			}
		}

		encounters += monsterCount; // count the number

		if (monsterCount > 0) {
			// battle animation
			
			try {
				playMusic(battleTheme, false);
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// Auto-generated catch block
				printError(""+e.getMessage());
			}
			
			// populate enemies
			if(north)
				enemies = initializeEnemies((Entity)board[prow-1][pcol].object);
			else if(south)
				enemies = initializeEnemies((Entity)board[prow+1][pcol].object);
			else if(west)
				enemies = initializeEnemies((Entity)board[prow][pcol-1].object);
			else if(east)
				enemies = initializeEnemies((Entity)board[prow][pcol+1].object);
			else
				enemies = initializeEnemies(null);
			
			// reset gui
			((CombatGUI) combatPanel).resetCombatGUI();
			
			// start fight
			initializeCombat();

			// go to combatPanel tab
			viewCombatPanel();
		}
	}

	/**
	 * Checks the player's current position to see if they are standing over any
	 * traps.
	 */
	public void checkForTrap(RPGObject i) {
		if (i != null && board[prow][pcol].isTrap()) {
			// check for beartrap
			if (i.id == BeartrapID) {
				// keep track of how many
				traps++;

				// inflict 10% damage of totalHealth to all players
				for (Entity e : characters)
					e.setCurrentHealth(e.getCurrentHealth()
							- (int) ((double) e.getMaxHealth() * 0.1));
			}
			// check for whirlwind
			else if (i.id == SpiralID) {
				// keep track of how many
				traps++;

				// sends the player to a random location on the map!
				boolean playerWasWarped = false;

				while (!playerWasWarped) {
					Random r = new Random();
					int x = r.nextInt(BROWS); // 0 to MAX row
					int y = r.nextInt(BCOLS); // 0 to MAX column

					// make sure its a valid location. (empty!)
					if (board[x][y].isEmptySpace()) {
						repositionPlayer(x, y);
						movePlayerVision();
						((GridGUI) mapPanel).repositionScrollBarsToPlayer();
						playerWasWarped = true;
					}
				}
			}
		}
		return;
	}

	/**
	 * Warps the player's position to the twin warp on the mapPanel. -8 and -9
	 * represent the two warps. Entering either warp, will send the player to
	 * the other end. (Two-Way)
	 * 
	 */
	public void checkForWarp() {
		int a = board[prow][pcol].terrain.id;

		if (warpingEnabled == true && (a == WarpAID || a == WarpBID)) {
			// We must find the other warp, according to our current warp id
			int b = WarpAID;
			if (a == WarpAID)
				b = WarpBID;

			for (int i = 0; i < BROWS; i++) {
				for (int j = 0; j < BCOLS; j++) {
					// find the other warp
					if (board[i][j].terrain.id == b) {
						// warp the player
						// delay here
						repositionPlayer(i, j);
						((GridGUI) mapPanel).repositionScrollBarsToPlayer();
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
	 * Check if the player stepped on an item. If so, it will
	 * add the item to their inventory and give them a message.
	 * @param i
	 */
	public void checkForItem(RPGObject i) {
		if(i != null && i instanceof Item) {
			
			if(i.getID() == BagID || i.getID() == ChestID)
			{
				// get a random item from the master list
				// TODO
			}
			else
			{
				// add item to inventory
				addItem((Item)i);
				itemsCollected++;
				printInfo("You found a(n) "+((Item)i).getItemName()+"!\nIt was placed in your inventory.");
			}
		}
		return;
	}

	/**
	 * This method will scan the player's health bar. If all players are at <=0
	 * health points, then its game over!
	 */
	public void checkForGameOver() {
		boolean allDead = true;

		for (Entity c : characters) {
			if (c.alive()) // anybody alive?
			{
				allDead = false;
				break;
			}
		}

		// if all are dead, then show game over
		if (allDead) {
			// play music
			playMusic(gameOver, false);

			// display a warning
			printInfo("Oh no!\n\nYour last player has died!");

			// GAME OVER
			viewGameOverScreen();
		}

	}

	/**
	 * Places the player to (x,y) (Row,Col) coordinate, and overwrites whatever
	 * is in the way.
	 * 
	 * This function is best used, for warping, or quick setting the player's
	 * position. It does not check for valid moves, according to the style of
	 * the game.
	 * 
	 * Use movePlayer(x,y) to perform logical, game style movement.
	 * 
	 * @return void Throws Exception if x and y are not valid locations.
	 */
	public void repositionPlayer(int x, int y) {
		// erase the player
		board[prow][pcol].setObject(null);

		try {
			// try to place the player, at the (x,y) coordinate
			prow = x;
			pcol = y;
			board[prow][pcol].setObject((RPGObject) characters.get(0));
		} catch (Exception e) {
			printError("Whoops!\n\n" + e.getMessage());
		}

		return;
	}

	/**
	 * Moves the player to the (x,y) coordinate, if possible. This function will
	 * follow game-style logic when moving the player, and will not just "warp"
	 * the player to the x,y coordinate.
	 * 
	 * Use repositionPlayer(x,y) if you need to immediately move the player, and
	 * replace whatever object is in the way.
	 * 
	 * @param x
	 * @param y
	 */
	public void movePlayer(int x, int y) {
		boolean player_has_moved = false;
		RPGObject pickup = null;

		// check for boundaries
		boolean edgeOfMap = ((prow == BROWS - 1 && x > 0)
				|| (pcol == BCOLS - 1 && y > 0) || (prow == 0 && x < 0) || (pcol == 0 && y < 0));
		boolean nearEdgeOfMap = ((prow + x == BROWS - 1 && x > 0)
				|| (pcol + y == BCOLS - 1 && y > 0) || (prow + x == 0 && x < 0) || (pcol
				+ y == 0 && y < 0));

		// quick exit, if they try to move out of bounds
		if (edgeOfMap || (nearEdgeOfMap && leftShift)) {
			if (showHintsEnabled) // show popup hint
				printInfo("You cannot move off the map!");
			return; // exit
		}

		// check if the next spot is empty, <0.
		if (!leftShift && board[prow + x][pcol + y].isEmptySpace()) {
			// move the player
			repositionPlayer(prow + x, pcol + y);
			player_has_moved = true;
			steps++;
		}
		// check if the next spot has a consumable object on it,
		// if so, pick up the item and add it to inventory
		else if (!leftShift
				&& (board[prow + x][pcol + y].isConsumable() || board[prow + x][pcol
						+ y].isTrap())) {
			// remember what the pickup was
			pickup = board[prow + x][pcol + y].getObject();

			// move the player
			repositionPlayer(prow + x, pcol + y);
			player_has_moved = true;
			steps++;
		}
		// check if the next spot is a sign post to read
		else if (!leftShift && board[prow + x][pcol + y].isSignPost()) {
			// don't move the player

			// read the sign
			Random rand = new Random();
			switch (rand.nextInt(4)) {
			case 0:
				printInfo("The sign reads...\n\n\'Run from battle if your party is weakened!\'");
				break;
			case 1:
				printInfo("The sign reads...\n\n\'Use the arrow keys to move faster!\nHold left-shift to jump!'");
				break;
			case 2:
				printInfo("The sign reads...\n\n\'Use your attribute points when you level up!\'");
				break;
			case 3:
				printInfo("The sign reads...\n\n\'Equip items to increase your combat strength!'");
				break;
			}

		}
		// check if the next spot is pushable like a rock
		else if (!leftShift && board[prow + x][pcol + y].isPushable()) {
			// is the next location behind the rock, an empty space?
			if (!nearEdgeOfMap
					&& board[prow + x + x][pcol + y + y].isEmptySpace()) {
				// if so, then move the pushable object forward one space
				board[prow + x + x][pcol + y + y]
						.setObject(board[prow + x][pcol + y].getObject());

				// remove the old rock
				board[prow + x][pcol + y].setObject(null);

				// move the player
				repositionPlayer(prow + x, pcol + y);
				player_has_moved = true;
				steps++;
				objectsPushed++;
			}
			// is the next location behind the rock, a hole?
			else if (!nearEdgeOfMap
					&& board[prow + x + x][pcol + y + y].isHole()) {
				// if so, then move the rock into the hole
				// leaving just normal ground where the rock was

				// move the player
				repositionPlayer(prow + x, pcol + y);
				player_has_moved = true;
				steps++;
				objectsPushed++;
				objectsPushedInHoles++;
			}
		}
		// check if the player can jump over spot to an empty spot
		else if (leftShift
				&& board[prow + x + x][pcol + y + y].isEmptySpace()
				&& (board[prow + x][pcol + y].isHole() || board[prow + x][pcol
						+ y].isEmptySpace())) {
			// if so, we can jump over the hole or ground
			// move the player two spaces
			repositionPlayer(prow + x + x, pcol + y + y);
			player_has_moved = true;
			hops++;
		}
		// check if the player can jump over spot to a consumable item
		else if (leftShift
				&& (board[prow + x + x][pcol + y + y].isConsumable() || board[prow
						+ x + x][pcol + y + y].isTrap())
				&& (board[prow + x][pcol + y].isHole() || board[prow + x][pcol
						+ y].isEmptySpace())) {
			// if so, we can jump and pick up the item

			// remember the pickup that we picked up
			pickup = board[prow + x + x][pcol + y + y].getObject();

			repositionPlayer(prow + x + x, pcol + y + y);
			player_has_moved = true;
			hops++;
		}

		// reposition the "camera" if they moved
		if (player_has_moved) {
			((GridGUI) mapPanel).repositionScrollBarsToPlayer();
			movePlayerVision();
			checkForItem(pickup);
			checkForTrap(pickup);
			checkForWarp();
			checkForExit();
		} else // bumped into something
		{
			checkForEnemies();
			
			// show hint if they bumped a hole
			if (showHintsEnabled && board[prow + x][pcol + y].isHole())
				printInfo("You can hop over holes by\nholding down the left-shift key.");
		}

		// are they dead yet?
		checkForGameOver();

		return;
	} // end of movePlayer()

	/**
	 * Sets the area around the player visible according to how far the player's
	 * vision reaches, if fogOfWar is enabled.
	 * 
	 * @return void
	 */
	public void movePlayerVision() {

		if (fogOfWar) // only works on if fog is enabled
		{
			for (int i = 0; i < BROWS; i++) {
				for (int j = 0; j < BCOLS; j++) {
					// if its inside of the player's vision range, then set
					// visible
					if (i < prow + playerVisionRange
							&& i > prow - playerVisionRange
							&& j < pcol + playerVisionRange
							&& j > pcol - playerVisionRange) {
						// inside vision range
						board[i][j].setFog(null);
						board[i][j].setVisible(true);
					} else if (i == prow + playerVisionRange
							&& j < pcol + playerVisionRange
							&& j > pcol - playerVisionRange) {
						// edge of lower vision
						board[i][j].setFog(FogBottom);
						board[i][j].setVisible(true);
					} else if (i == prow - playerVisionRange
							&& j < pcol + playerVisionRange
							&& j > pcol - playerVisionRange) {
						// edge of upper vision
						board[i][j].setFog(FogTop);
						board[i][j].setVisible(true);
					} else if (j == pcol + playerVisionRange
							&& i < prow + playerVisionRange
							&& i > prow - playerVisionRange) {
						// edge of right-side vision
						board[i][j].setFog(FogRight);
						board[i][j].setVisible(true);
					} else if (j == pcol - playerVisionRange
							&& i < prow + playerVisionRange
							&& i > prow - playerVisionRange) {
						// edge of left-side vision
						board[i][j].setFog(FogLeft);
						board[i][j].setVisible(true);
					} else if (mappingEnabled && board[i][j].isVisible()) {
						// was previously seen, but now out of range
						board[i][j].setFog(FogCenter);
						board[i][j].setVisible(true);
					} else // out of vision range
					{
						board[i][j].setFog(null);
						board[i][j].setVisible(false);
					}
				}
			}
		}
        makeExitGlow();
	} // end of movePlayerVision()

	public void moveEnemies() {
		// Scan for enemies, if you find one, make him move.
		if (numOfMonsters > 0 && klok.currentTime != 0
				&& klok.currentTime % monsterGridSpeed == 0) {
			// mark all enemies as, "has not moved", before we begin
			for (int i = 0; i < BROWS; i++)
				for (int j = 0; j < BCOLS; j++)
					if (board[i][j].isMonster()
							&& ((Entity) board[i][j].object).hasMoved)
						((Entity) board[i][j].object).hasMoved = false;

			// check for unmoved enemies, then start to move them, and mark them
			// "has moved" true.
			for (int i = 0; i < BROWS; i++) {
				for (int j = 0; j < BCOLS; j++) {
					// find an enemy
					if (klok.currentTime > 0 && board[i][j].isMonster()
							&& !((Entity) board[i][j].object).hasMoved) {
						// get their behavior type
						String type = ((Entity) board[i][j].object)
								.getBehaviorType();
// if Random, then assign a permanent Behavior for that object
	            		if( type.equalsIgnoreCase("Random"))
	            		{
	            			Random rand = new Random();
	            			switch(rand.nextInt(5))
	            			{
	            			case 0: type = "Aggressive"; break;
	            			case 1: type = "Defensive"; break;
	            			case 2: type = "Speedy"; break;
	            			case 3: type = "Coward"; break;
	            			case 4: type = "Tricky"; break;
	            			default: type = ""; break;
	            			}
	            			((Entity)board[i][j].object).setBehaviorType(type);
	            		}

						if (type.equalsIgnoreCase("Aggressive")) // chase player
																	// on sight,
																	// attacks
																	// and use
																	// abilities
						{
							if (withinPlayerSight(i, j)) {
								int[] direction = getDirectionToLocation(i, j,
										prow, pcol);
								int x = direction[0];
								int y = direction[1];
								if (!((i == BROWS - 1 && x > 0)
										|| (j == BCOLS - 1 && y > 0)
										|| (i == 0 && x < 0) || (j == 0 && y < 0))
										&& board[i + x][j + y].isEmptySpace()) {
									// move in direction
									((Entity) board[i][j].object).hasMoved = true;
									board[i + x][j + y].setObject(board[i][j]
											.getObject());
									board[i][j].setObject(null);
								}
							}
						} else if (type.equals("Defensive")) // stands ground,
																// attacks and
																// use abilities
						{
							// No movement
						} else if (type.equals("Speedy")
								|| type.equals("Coward")) {
							// Speedy: run from player, gang on single target
							// until dead
							// Coward: run from player, flee from fights
							if (withinPlayerSight(i, j)) {
								int[] direction = getDirectionToLocation(i, j,
										prow, pcol);
								int x = -direction[0];
								int y = -direction[1];
								if (!((i == BROWS - 1 && x > 0)
										|| (j == BCOLS - 1 && y > 0)
										|| (i == 0 && x < 0) || (j == 0 && y < 0))
										&& board[i + x][j + y].isEmptySpace()) {
									// move in direction
									((Entity) board[i][j].object).hasMoved = true;
									board[i + x][j + y].setObject(board[i][j]
											.getObject());
									board[i][j].setObject(null);
								}
							}
						} else if (type.equals("Tricky")) // random movement,
															// random actions
						{
							Random rand = new Random();
							int direction = rand.nextInt(4);
							int x = 0, y = 0;

							if (direction == 0)
								y--;
							else if (direction == 1)
								x++;
							else if (direction == 2)
								y++;
							else
								x--;

							// try to move in the random direction
							if (!((i == BROWS - 1 && x > 0)
									|| (j == BCOLS - 1 && y > 0)
									|| (i == 0 && x < 0) || (j == 0 && y < 0))
									&& board[i + x][j + y].isEmptySpace()) {
								// move randomly
								((Entity) board[i][j].object).hasMoved = true;
								board[i + x][j + y].setObject(board[i][j]
										.getObject());
								board[i][j].setObject(null);
							}
						} else if (type.equals("Boss")) // stands by exit,
														// splash abilities,
														// heals self
						{
							// TODO
						} else // no preference
						{
							// move the monster left, if possible
							if (j != 0 && board[i][j - 1].isEmptySpace()) {
								// move left
								((Entity) board[i][j].object).hasMoved = true;
								board[i][j - 1].setObject(board[i][j]
										.getObject());
								board[i][j].setObject(null);
							} else if (i != BROWS - 1
									&& board[i][j + 1].isEmptySpace()) {
								// else move right
								((Entity) board[i][j].object).hasMoved = true;
								board[i][j + 1].setObject(board[i][j]
										.getObject());
								board[i][j].setObject(null);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Tells you wether or not the location given is within the bounds of the
	 * player's sight in fog of war.
	 * 
	 * @param x
	 * @param y
	 * @return boolean
	 */
	public boolean withinPlayerSight(int x, int y) {
		if (x < prow + playerVisionRange && x > prow - playerVisionRange
				&& y < pcol + playerVisionRange && y > pcol - playerVisionRange) {
			// inside vision range
			return true;
		} else // out of vision range
		{
			return false;
		}
	}

	/**
	 * Gives you the coordinate direction (Eg [-1,0]) that the current location
	 * would have to move in order to get closer to the target location.
	 * @param currentRow
	 * @param currentCol
	 * @param targetRow
	 * @param targetCol
	 * @return
	 */
	public int[] getDirectionToLocation(int currentRow, int currentCol,
			int targetRow, int targetCol) {
		int[] direction = { 0, 0 };

		boolean xneg = false;
		boolean yneg = false;
		int y = targetRow - currentRow;
		int x = targetCol - currentCol;

		if (x < 0) {
			x = -x;
			xneg = true;
		}
		if (y < 0) {
			y = -y;
			yneg = true;
		}

		if (x >= y && !xneg)
			direction[1] = 1; // right
		else if (x >= y && xneg)
			direction[1] = -1; // left
		else if (y >= x && !yneg)
			direction[0] = 1; // down
		else if (y >= x && yneg)
			direction[0] = -1; // up

		return direction;
	}

	/**
	 * Sets all of the mapPanel's tiles visible or invisible
	 * 
	 * @return void
	 */
	public void setFog(boolean f) {
		if (fogOfWar) {
			for (int i = 0; i < BROWS; i++) {
				for (int j = 0; j < BCOLS; j++) {
					board[i][j].setVisible(!f);
					board[i][j].setFog(null);
				}
			}
		}
	}

	/**
	 * Makes all of the GridObjects appear hazy or dark.
	 * 
	 * This is not compatable with fogOfWar enabled.
	 * 
	 * @param onOff
	 */
	public void setGridFade(boolean onOff) {
		if (!fogOfWar) {
			for (int i = 0; i < BROWS; i++) {
				for (int j = 0; j < BCOLS; j++) {
					if (!onOff)
						board[i][j].setFog(null);
					else
						board[i][j].setFog(FogCenter);
				}
			}
		}
	}
	
	/**
	 * Adds a simple line border to the grid object at the given
	 * location, of [row,col], if possible.
	 * @param border
	 * @param x
	 * @param y
	 */
	public void setBorderToGridObject(boolean border, int r, int c)
	{
		if(board != null && c < BCOLS && r < BROWS)
		{
			if(border)
				board[r][c].setBorder(BorderFactory.createLineBorder(highlightColor, 4));
			else
				board[r][c].setBorder(null);
		}
	}

	public void makeExitGlow() {
		if (fogOfWar && exitGlowEnabled) // only works on if fog & exitGlow is enabled
		{
			for (int i = 0; i < BROWS; i++) {
				for (int j = 0; j < BCOLS; j++) {
					// if its inside of the  range, then set visible
					if (i <= erow + 1 && i >= erow - 1 && j <= ecol + 1 && j >= ecol - 1) {
						// inside vision range
						board[i][j].setFog(null);
						board[i][j].setVisible(true);
					}
				}
			}
		}
	}

	/**
	 * Randomizes the current board with new images and ids
	 * 
	 * This function is for testing purposes only. Official game levels should
	 * not use this function, since the levels generated may be impossible for
	 * the player to complete.
	 */
	public void randomizeBoard() {
		// randomly build a testing grid with rocks, holes, grass, and monsters
		Random rand = new Random();
		boolean doorPlaced = false;
		boolean warpAPlaced = false;
		boolean warpBPlaced = false;
		boolean spiralPlaced = true;
		int signs = 0;
		int temp;
		numOfMonsters = 0;

		for (int i = 0; i < BROWS; i++) {
			for (int j = 0; j < BCOLS; j++) {
				temp = rand.nextInt(51) - 2; // random -2 to 48

				// randomly scatter "holes"
				if (temp == 1) {
					board[i][j].resetObject(new Terrain(DirtID, Dirt, false,
							true, false), new NonEntity(HoleID, Hole, false,
							false, true, false), null);
				}
				// randomly scatter "rocks"
				else if (temp == -1 || temp == -2) {
					board[i][j].resetObject(new Terrain(DirtID, Dirt, false,
							true, false), new NonEntity(RockID, Rock, true,
							false, false, false), null);
				}
				// randomly make "monsters"
				else if (temp == 7) {
					board[i][j].resetObject(new Terrain(DirtID, Dirt, false,
							true, false), new Entity(LavaMonsterID,
							LavaMonster, "LavaMonster", "Random", false,
							null, 10, 10, 10, 10, 5, 5, 5, 5, 1), null);
					((Entity) board[i][j].object).setExp(5);
					((Entity) board[i][j].object).abilities.add(new Ability("Cure", 1, 0, 5, 10));
					((Entity) board[i][j].object).abilities.add(new Ability("Smack", 0, 0, 5, 10));
					numOfMonsters++;
				}
				// randomly make "beartraps"
				else if (temp == 12) {
					board[i][j]
							.resetObject(new Terrain(DirtID, Dirt, false, true,
									false), new NonEntity(BeartrapID, Beartrap,
									false, true, false, false), new NonEntity(
									TallGrassID, TallGrass, false, false,
									false, false));
				}
				// randomly make "sign posts"
				else if (temp == 11 && signs < 3) // only allow 3 per map
				{
					board[i][j].resetObject(new Terrain(GrassID, Grass, false,
							true, false), new NonEntity(SignID, Sign, false,
							false, false, true), null);
					signs++;
				}
				// place warp 'a'
				else if (warpingEnabled && !warpAPlaced && temp == 8) {
					board[i][j].resetObject(new Terrain(WarpAID, Spiral, false,
							true, false), null, null);
					warpAPlaced = true;
				}
				// place warp 'b'
				else if (warpingEnabled && !warpBPlaced && temp == 9
						&& (i >= BROWS / 2)) {
					board[i][j].resetObject(new Terrain(WarpBID, Spiral, false,
							true, false), null, null);
					warpBPlaced = true;
				}
				// randomly place one exit per map
				else if (!doorPlaced && (temp == 10 || (i == 3 && j == 3))) {
					board[i][j].resetObject(new Terrain(GrassID, Grass, true,
							true, false), null, new NonEntity(ExitID,
							GlowingGem, false, false, false, false));
					doorPlaced = true;
					erow = i;
					ecol = j;
				}
				// randomly place one spiral, if necessary
				else if (!spiralPlaced && temp == 23) {
					board[i][j].resetObject(new Terrain(DirtID, Dirt, false,
							true, false), new NonEntity(SpiralID, Spiral,
							false, true, false, false), null);
					spiralPlaced = true;
				} else {
					// everything else is dirt, grass, or tall grass
					temp = rand.nextInt(6); // 0 - 5
					if (temp >= 4)
						board[i][j].resetObject(new Terrain(GrassID, Grass,
								false, true, true), null, new NonEntity(
								TallGrassID, TallGrass, false, false, false,
								false));
					else if (temp == 3)
						board[i][j].resetObject(new Terrain(DirtID, Dirt,
								false, true, false), null, null);
					else
						board[i][j].resetObject(new Terrain(GrassID, Grass,
								false, true, false), null, null);
				}
			}
		}

		if (fogOfWar)
			setFog(true);

		return;
	}

	/**
	 * Sets all the characters' health and magic to their max.
	 */
	public void restoreAllCharacters() {
		for (Entity i : characters) {
			i.setCurrentHealth(i.getMaxHealth());
			i.setCurrentMana(i.getMaxMana());
		}
	}

	/**
	 * Sets a single character's health and magic to their max.
	 * 
	 * @param name
	 */
	public void restoreCharacter(String name) {
		for (Entity i : characters) {
			if (i.getName().equals(name)) {
				i.setCurrentHealth(i.getMaxHealth());
				i.setCurrentMana(i.getMaxMana());
				break;
			}
		}
	}

	@Override
	public void actionPerformed(final ActionEvent Trigger) {
		// if the user clicks on a menu button	     
		   
		if (Trigger.getSource() == newItem) {
			int answer = printYesNoQuestion("Are you sure you want\nto start a new game?");
			if (answer == 0)
				newGame();
		} else if (Trigger.getSource() == saveItem) {
			// save the game
			saveGame();
		} else if (Trigger.getSource() == quitItem) {
			int answer = printYesNoQuestion("Are you sure you want\nto quit? Any progress made\nbe lost.");
			if (answer == 0)
				endGame();
		} else if (Trigger.getSource() == teleportItem) {
			repositionPlayer(prowStart, pcolStart);
			((GridGUI) mapPanel).repositionScrollBarsToPlayer();
			movePlayerVision();
		} else if (Trigger.getSource() == statsItem) {
			printInfo(getStatistics());
		} else if (Trigger.getSource() == enableMusicItem) {
			if (musicEnabled) {
				musicEnabled = false;
				stopMusic();
			} else {
				musicEnabled = true;
				startMusic();
			}
		} else if (Trigger.getSource() == enableSoundItem) {
			if (soundEnabled) {
				soundEnabled = false;
				stopSound();
			} else {
				soundEnabled = true;
				startSound();
			}
		} else if (Trigger.getSource() == enableFogItem) {
			if (fogOfWar) {
				setFog(false);
				fogOfWar = false;
				mappingEnabled = false; // mapping is useless without fog
				enableMappingItem.setEnabled(false);
			} else {
				setFog(true);
				fogOfWar = true;
				movePlayerVision();
				enableMappingItem.setEnabled(true);
			}
		} else if (Trigger.getSource() == enableWarpItem) {
			if (warpingEnabled) {
				warpingEnabled = false;
				removeWarps();
			} else {
				warpingEnabled = true;
				printInfo("Warping will be enabled on the next level.");
			}
		} else if (Trigger.getSource() == enableMappingItem) {
			if (mappingEnabled)
				mappingEnabled = false;
			else
				mappingEnabled = true;
		} else if (Trigger.getSource() == enableHintsItem) {
			if (showHintsEnabled)
				showHintsEnabled = false;
			else
				showHintsEnabled = true;
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		/*
		 if (e.getSource() == tabs) {
			int tab = tabs.getSelectedIndex();
			if (tab == 0) {
				mapPanel.requestFocus();
				klok.run(Clock.FOREVER);
			} else if (tab == 1) {
				klok.pause();
				inventoryPanel.requestFocus();
				((InventoryGUI) inventoryPanel).update();
			} else if (tab == 2) {
				klok.pause();
				statsPanel.requestFocus();
				((StatsGUI) statsPanel).reset();
				((StatsGUI) statsPanel).update();
				((StatsGUI) statsPanel).cycle(1);
				((StatsGUI) statsPanel).cycle(-1);
			} else if (tab == 3) {
				klok.pause();
				combatPanel.requestFocus();
			} else if (tab == 4) {
				klok.pause();
				questPanel.requestFocus();
			}
		} else
			klok.pause();
		*/
	}

	@Override
	public void focusLost(FocusEvent e) {
		// Auto-generated method stub
	}

	@Override
	public boolean event(int tick) {
		// update the clock time on the JMenu
		time.setText("Time: " + klok.currentTime);

		// move enemies on the map if possible
		moveEnemies();
		
		// check around for enemies again
		checkForEnemies();

		return false;
	}

	/**
	 * Initializes the Sequencer to play MIDI files This function throws an
	 * Exception
	 */
	public void initializeAudioStreams() {
		try {
			// music sequencer
			musicStream = MidiSystem.getSequencer();
			musicStream.open();

			// sound effect sequencer
			soundStream = MidiSystem.getSequencer();
			soundStream.open();
		} catch (MidiUnavailableException e) {
			// Unexpected Error
			printError("Whoops!\n\n" + e.getMessage());
		}
	}

	/**
	 * Plays the sound file using a Sequencer. It plays background music. This
	 * function throws an Exception.
	 * 
	 * @param soundFile
	 */
	public void playMusic(final String soundFile, final boolean loop) {
		if (musicEnabled) {
			try {
				// set the loop
				if (loop)
					musicStream.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
				else
					musicStream.setLoopCount(0);

				File sfx = new File(soundFile);
				Sequence sfxSequence = MidiSystem.getSequence(sfx);
				musicStream.setSequence(sfxSequence);
				musicStream.start();
			} catch (Exception e) {
				printError("Whoops!\n\n" + e.getMessage());
			}
		}
	}

	/**
	 * Plays the sound file using a Sequencer. It plays as a sound effect. This
	 * function throws an Exception.
	 * 
	 * @param soundFile
	 * @param loop
	 */
	public void playSound(final String soundFile, final boolean loop) {
		if (soundEnabled) {
			try {
				// set the loop
				if (loop)
					soundStream.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
				else
					soundStream.setLoopCount(0);

				File sfx = new File(soundFile);
				Sequence sfxSequence = MidiSystem.getSequence(sfx);
				soundStream.setSequence(sfxSequence);
				soundStream.start();
			} catch (Exception e) {
				printError("Whoops!\n\n" + e.getMessage());
			}
		}
	}

	/**
	 * Resumes playing music.
	 */
	public void startMusic() {
		musicStream.start();
	}

	/**
	 * Resumes playing sounds.
	 */
	public void startSound() {
		soundStream.start();
	}

	/**
	 * Stops Music from playing permanently.
	 */
	public void stopMusic() {
		musicStream.stop();
	}

	/**
	 * Stops Sound Effects from playing permanently.
	 */
	public void stopSound() {
		soundStream.stop();
	}

	/**
	 * Resets the current board size.
	 * 
	 * WARNING! You should clear the current board with deleteGridObjects()
	 * before you use this function!
	 * 
	 * @param rows
	 * @param columns
	 */
	public void setBoardSize(int rows, int columns) {
		if (rows > 1 && columns > 1 && rows < MAXIMUMSIZE
				&& columns < MAXIMUMSIZE) {
			BROWS = rows;
			BCOLS = columns;
		} else
			printError("Oops!\n\nsetBoardSize(x,y) cannot take values\nlower than 2 or greater than "
					+ MAXIMUMSIZE + "!");
	}

	

	public void initializeCombat() {

		actor = null;
		event = null;
		combatOver = false;
		turnStack = new Stack<Entity>();
		combatResult = 0;
		combatants = new ArrayList<Entity>();
		accumulatedExp = 0;
		

		for (Entity c : characters) {
			combatants.add(c);
		}
		for (Entity m : enemies) {
			combatants.add(m);
		}

		// determine turn order
		combatants = sortTurnOrderArray(combatants);

		// populate turn stack for first round
		for (Entity e : combatants) {
			turnStack.push(e);
		}

		// keep popping dead combatants
		while (!turnStack.peek().alive()) {
			turnStack.pop();
		}

		Entity a = turnStack.peek();

		setupTurn(a, combatants);

	}

	public void setupTurn(Entity character, ArrayList<Entity> targets) {
		if (!combatOver) {
			((CombatGUI) combatPanel).attackTargets.removeAllItems(); // attacks
			((CombatGUI) combatPanel).abilityTargets.removeAllItems(); // abilities
			((CombatGUI) combatPanel).itemTargets.removeAllItems(); // items
			((CombatGUI) combatPanel).abilities.removeAllItems();
			((CombatGUI) combatPanel).items.removeAllItems();

			for (CombatObject obj : ((CombatGUI) combatPanel).enemies) {
				if (obj.entity.equals(character)) {
					obj.setCurrentTurn(true);
				} else {
					obj.setCurrentTurn(false);
				}
			}

			for (CombatObject obj : ((CombatGUI) combatPanel).characters) {
				if (obj.entity.equals(character)) {
					obj.setCurrentTurn(true);
				} else {
					obj.setCurrentTurn(false);
				}
			}

			if (!character.isPlayer) {
				monsterTurn(character);
				((CombatGUI) combatPanel).viewStatusPanel();
			}

			for (Ability ability : character.abilities) {
				((CombatGUI) combatPanel).abilities.addItem(ability.getName());
			}
			for (Entity target : targets) {
				((CombatGUI) combatPanel).attackTargets.addItem(target
						.getName());
				((CombatGUI) combatPanel).abilityTargets.addItem(target
						.getName());
				((CombatGUI) combatPanel).itemTargets.addItem(target.getName());
			}
			
			((CombatGUI) combatPanel).loadItems();
			
			if(((CombatGUI)combatPanel).items.getItemCount() == 0){
				((CombatGUI)combatPanel).itemButton.setEnabled(false);
			}else{
				((CombatGUI)combatPanel).itemButton.setEnabled(true);
			}
			
		} else {
			((CombatGUI) combatPanel).update();
			((CombatGUI) combatPanel).endCombat(combatResult, accumulatedExp);
		}

	}

	public void endTurn() {

		// clean out dead enemies
		enemies = cleanMonsterList(enemies);
		combatants = mergeCombatantArrays(enemies, characters);

		if (enemies.size() < 1) {
			combatOver = true;
			combatResult = 1;
		}

		// accumulator to keep track of how many characters are dead
		int deathAccum = 0;

		// walk through characters, if dead increase accum
		for (Entity c : characters) {

			if (!c.alive()) {
				deathAccum++;
			}
		}

		// if all characters are dead, combat is over
		if (deathAccum == characters.size()) {
			combatOver = true;
			combatResult = -1;
		}

		// peek at next Entity in stack to make sure they are alive
		while (!turnStack.empty() && !turnStack.peek().alive()) {
			turnStack.pop();
		}

		// if stack is empty, refill it
		if (turnStack.empty()) {
			for (Entity e : combatants) {
				turnStack.push(e);
			}
		}

		// peek at next Entity in stack to make sure they are alive
		while (!turnStack.empty() && !turnStack.peek().alive()) {
			turnStack.pop();
		}

		((CombatGUI) combatPanel).update();
		checkForGameOver();
	}
	
	public String randomBehavior(){
		ArrayList<String> behaviors = new ArrayList<String>();
		
		behaviors.add("Aggressive");
		behaviors.add("Defensive");
		behaviors.add("Speedy");
		behaviors.add("Coward");
		behaviors.add("Tricky");
		behaviors.add("Boss");
		
		int random = randomNumberGenerator.nextInt(6);
		
		return behaviors.get(random);
	}

	public void monsterTurn(Entity m) {

		String behavior = m.getBehaviorType();
		Entity attackTarget = null;
		int healTarget;
		
		//Random
		if (behavior.equals("Random")){
			behavior = randomBehavior();
		}
		
		//Aggressive
		if (behavior.equals("Aggressive")) {
			attackTarget = findWeakestCharacter();
			executeAttack(m, attackTarget);
		
		//Defensive
		} else if (behavior.equals("Defensive")) {
			attackTarget = findRandomCharacter();
			healTarget = enemies.indexOf(findWeakestEnemy());
			
			int action = randomNumberGenerator.nextInt(2);
			
			//heal
			if(action == 0 && shouldHeal(m, enemies)){
				executeAbility(m, healTarget, characters, enemies, m.findHealingAbility());
			
			//attack
			}else{
				executeAttack(m, attackTarget);
			}

		//Speedy
		} else if (behavior.equals("Speedy")) {
			attackTarget = findWeakestCharacter();
			executeAttack(m, attackTarget);

		//Coward
		} else if (behavior.equals("Coward")) {
			int action = randomNumberGenerator.nextInt(3);
			
			//heal
			if(action == 0 && shouldHeal(m, enemies)){
				healTarget = enemies.indexOf(findWeakestEnemy());
				executeAbility(m, healTarget, characters, enemies, m.findHealingAbility());
			
			//flee
			}else if(action == 1){
				//monster flee
				executeFlee(m);
				
			//attack
			}else{
				attackTarget = findRandomCharacter();
				executeAttack(m, attackTarget);
			}
			
		//Tricky
		} else if (behavior.equals("Tricky")) {
			attackTarget = findRandomCharacter();
			healTarget = enemies.indexOf(findRandomEnemy());
			int action = randomNumberGenerator.nextInt(3);
			
			//heal
			if(action == 0 && shouldHeal(m, enemies)){
				healTarget = enemies.indexOf(findWeakestEnemy());
				executeAbility(m, healTarget, characters, enemies, m.findHealingAbility());
			
			//ability
			}else if(action == 1){
				executeAbility(m, characters.indexOf(attackTarget), characters, enemies, m.findDamagingAbility());
			
			//attack
			}else{
				executeAttack(m, attackTarget);
			}

		//Boss
		} else if (behavior.equals("Boss")) {
			attackTarget = findWeakestCharacter();
			healTarget = enemies.indexOf(m);
			int action = randomNumberGenerator.nextInt(3);
			
			//heal
			if(action == 0 && shouldHeal(m, enemies)){
				healTarget = enemies.indexOf(findWeakestEnemy());
				executeAbility(m, healTarget, characters, enemies, m.findHealingAbility());
			
			//ability
			}else if(action == 1){
				executeAbility(m, characters.indexOf(attackTarget), characters, enemies, m.findDamagingAbility());
			
			//attack
			}else{
				executeAttack(m, attackTarget);
			}
		}

		turnStack.pop();
		endTurn();
	}

	// find character with the lowest health
	private Entity findWeakestCharacter() {
		Entity target = characters.get(0);

		for (Entity e : characters) {
			if (e.getCurrentHealth() < target.getCurrentHealth()) {
				if (e.alive()) {
					target = e;
				}
			}
		}

		return target;
	}
	
	//returns true if the entity has a healing ability and there are targets to heal
	private boolean shouldHeal(Entity monster, ArrayList<Entity> collection){
		boolean returnVal = false;
		
		if(healableTargetExists(collection) && monster.hasHealingAbility()){
			returnVal = true;
		}
		
		return returnVal;
	}
	
	//check if there are any entities in this collection that are hurt
	private boolean healableTargetExists(ArrayList<Entity> collection){
		boolean returnVal = false;
		for(Entity e : collection){
			if(e.getCurrentHealth() < e.getMaxHealth()){
				returnVal = true;
			}
		}
		return returnVal;
	}

	// find enemy with the lowest health
	private Entity findWeakestEnemy() {
		Entity target = enemies.get(0);

		for (Entity e : enemies) {
			if (e.getCurrentHealth() < target.getCurrentHealth()) {
				if (e.alive()) {
					target = e;
				}
			}
		}

		return target;
	}

	// find random character
	private Entity findRandomCharacter() {
		int attackTargets = characters.size();
		int attackTargetIndex = randomNumberGenerator.nextInt(attackTargets);
		Entity attackTarget = characters.get(attackTargetIndex);

		while (!attackTarget.alive()) {
			attackTargetIndex = randomNumberGenerator.nextInt(attackTargets);
			attackTarget = characters.get(attackTargetIndex);
		}

		return attackTarget;
	}
	
	// find random enemy
		private Entity findRandomEnemy() {
			int attackTargets = enemies.size();
			int attackTargetIndex = randomNumberGenerator.nextInt(attackTargets);
			Entity attackTarget = enemies.get(attackTargetIndex);

			while (!attackTarget.alive()) {
				attackTargetIndex = randomNumberGenerator.nextInt(attackTargets);
				attackTarget = enemies.get(attackTargetIndex);
			}

			return attackTarget;
		}

	public void playerTurn(String action, int target, Item item) {

		actor = turnStack.pop();

		// start character turn

		// actions for character
		if (action.equals("Attack")) {
			executeAttack(actor, combatants.get(target));
		}

		else if (action.equals("Item")) {
			executeItem(actor, combatants.get(target), item);
		}

		else if (action.equals("Flee")) {
			if (attemptToFlee()) {
				((CombatGUI) combatPanel).fled = true;
				((CombatGUI) combatPanel)
						.appendStatus("You have fled successfully!");
				combatOver = true;
				combatResult = 0;
			} else {
				((CombatGUI) combatPanel)
						.appendStatus("Your attempt to flee failed!");
			}
		}

		else {
			Ability activeAbility = actor.getAbilityByName(action);
			if (activeAbility.friendly()) {
				executeAbility(actor, target, enemies, characters,
						activeAbility);
			} else {
				executeAbility(actor, target, enemies, characters,
						activeAbility);
			}
		}

		endTurn();

	}

	private void executeFlee(Entity a){
		a.setExp(0);
		String s = (a.getName() + " has fled from battle!");
		enemies.remove(a);
		((CombatGUI) combatPanel).appendStatus(s);
	}
	
	public void executeAttack(Entity source, Entity target) {
		int calculatedDamage = 0;
		int attackVal = source.getAttack() + randomNumberGenerator.nextInt(6);
		int defenseVal = target.getDefense() + randomNumberGenerator.nextInt(6);

		calculatedDamage = attackVal - (defenseVal / 2);
		if (calculatedDamage < 0) {
			calculatedDamage = 0;
		}

		int netHealth = target.getCurrentHealth() - calculatedDamage;

		if (netHealth < 0) {
			netHealth = 0;
		}
		target.setCurrentHealth(netHealth);
		String s = (source.getName() + "'s attack hit " + target.getName()
				+ " for " + calculatedDamage + " damage!");
		((CombatGUI) combatPanel).appendStatus(s);
		
		//update cumulative global variables
		if(source.isPlayer){
			damageDealt += calculatedDamage;
		}else{
			damageTaken += calculatedDamage;
		}
	}
	
	public void executeItem(Entity source, Entity target, Item item){
		target.useItem(item);
		String s = source.getName() + " used " + item.getItemName() + " on " + target.getName() + "!";
		((CombatGUI) combatPanel).appendStatus(s);
	}

	/*
	 * TYPE 0 -- damage 1 -- heal 2 -- statusmod
	 * 
	 * SCOPE 0 -- single target 1 -- splash
	 */
	public void executeAbility(Entity source, int targetID,
			ArrayList<? extends Entity> enemies,
			ArrayList<? extends Entity> allies, Ability ability) {

		String result = null;

		switch (ability.getType()) {

		// damage ability
		case 0:

			// single target
			if (ability.getScope() == 0) {
				int calculatedDamage = 0;
				int attackVal = source.getAttack()
						+ randomNumberGenerator.nextInt(6) + ability.getModifier();
				int defenseVal = enemies.get(targetID).getDefense()
						+ randomNumberGenerator.nextInt(6);

				calculatedDamage = attackVal - (defenseVal / 2);
				if (calculatedDamage < 0) {
					calculatedDamage = 0;
				}

				enemies.get(targetID).setCurrentHealth(
						enemies.get(targetID).getCurrentHealth()
								- calculatedDamage);
				result = (source.getName() + "'s " + ability.getName()
						+ " hit " + enemies.get(targetID).getName() + " for "
						+ calculatedDamage + " damage!");
				

				//update cumulative global variables
				if(source.isPlayer){
					damageDealt += calculatedDamage;
				}else{
					damageTaken += calculatedDamage;
				}
			}

			// splash
			else {

				int avgDefense = 0;
				// average enemies defense values
				for (Entity e : enemies) {
					avgDefense += e.getDefense();
				}

				avgDefense /= enemies.size();

				int calculatedDamage = 0;
				int attackVal = source.getAttack()
						+ randomNumberGenerator.nextInt(6) + ability.getModifier();
				int defenseVal = avgDefense + randomNumberGenerator.nextInt(6);

				calculatedDamage = attackVal - (defenseVal / 2);
				if (calculatedDamage < 0) {
					calculatedDamage = 0;
				}

				for (Entity e : enemies) {
					e.setCurrentHealth(e.getCurrentHealth() - calculatedDamage);
					result = (source.getName() + "'s " + ability.getName()
							+ " hit all enemies for " + calculatedDamage + " damage!");
					

					//update cumulative global variables
					if(source.isPlayer){
						damageDealt += calculatedDamage;
					}else{
						damageTaken += calculatedDamage;
					}
				}
			}

			break;

		// healing ability
		case 1:
			int amountHealed = source.getAttack()
					+ randomNumberGenerator.nextInt(6) + ability.getModifier();
			combatants.get(targetID).setCurrentHealth(
					combatants.get(targetID).getCurrentHealth() + amountHealed);
			if (combatants.get(targetID).getCurrentHealth() > combatants.get(
					targetID).getMaxHealth()) {
				combatants.get(targetID).setCurrentHealth(
						combatants.get(targetID).getMaxHealth());
				result = (combatants.get(targetID).getName()
						+ " was fully healed by " + source.getName() + "'s "
						+ ability.getName() + "!");
			} else {
				result = (combatants.get(targetID).getName()
						+ " was healed for " + amountHealed + " damage by "
						+ source.getName() + "'s " + ability.getName() + "!");
				
			}
			
			//update cumulative global variables
			if(source.isPlayer){
				damageHealed += amountHealed;
			}
		}

		source.setCurrentMana(source.getCurrentMana() - ability.getCost());
		((CombatGUI) combatPanel).appendStatus(result);

	}

	public boolean attemptToFlee() {
		boolean result;

		int roll = randomNumberGenerator.nextInt(3); // 0,1,2
		if (roll != 2) {
			result = true;
		} else {
			result = false;
		}

		return result;
	}

	// returns array of type Entity sorted by speed. Lowest speed is first.
	public ArrayList<Entity> sortTurnOrderArray(ArrayList<Entity> combatants) {
		Collections.sort(combatants);

		return combatants;
	}

	public ArrayList<Entity> initializeEnemies(Entity enemy) {
		
		ArrayList<Entity> monsterArray = new ArrayList<Entity>();
		int numberOfEnemies = randomNumberGenerator.nextInt(8) + 1;
		Entity m;
		
		if(enemy != null)
		{
			for (int i = 1; i <= numberOfEnemies; i++) {
				m = new Entity(-555, null, "enemy","", false, null,1,1,1,1,1,1,1,1,1);
				m.setAttack(enemy.getAttack());
				m.setDefense(enemy.getDefense());
				m.setSpeed(enemy.getSpeed());
				m.setMaxHealth(enemy.getMaxHealth());
				m.setMaxMana(enemy.getMaxMana());
				m.setCurrentHealth(enemy.getCurrentHealth());
				m.setCurrentMana(enemy.getCurrentMana());
				m.image = enemy.image;
				m.id = enemy.id;
				m.setName(enemy.getName());
				m.setBehaviorType(enemy.getBehaviorType());
				m.abilities = enemy.abilities;
				m.setExp(enemy.getExp());
				m.setName(m.getName() + " " + i);
				
				monsterArray.add(m);
			}
		}
		else
		{
			// randomly make a monster team with random behaviors TODO
			// TODO
			printInfo("A monster would have appeared\nbut its not programmed yet.");
		}
		
		return monsterArray;
	}

	public boolean endCombat(ArrayList<Entity> characters, int experience,
			int result) {

		boolean returnVal = false;
		if (result == 0) {
			// code to handle running away
			// remove defeated enemies from grid
			fightsFled++;
			removeDefeatedEnemies(null);
			returnVal = true;
		} else {
			if (result == 1) {
				playMusic(victory, false); // fanfare
				fightsWon++;
				for (Entity character : characters) {
					if (character.alive()) {
						character.setExp(character.getExp() + experience);
						((CombatGUI) combatPanel).appendStatus(character
								.getName()
								+ " has earned "
								+ experience
								+ " experience!");

						// character has leveled up
						if (character.getExp() >= getExpNeeded(character)) {
							levelUpCharacter(character);
							hasLeveledUp = true;
							((CombatGUI) combatPanel).appendStatus(character
									.getName()
									+ " has reached level "
									+ character.getLevel() + "!");
							ArrayList<String> learnedAbilities = character.unlockAbilities();
							if(learnedAbilities.size() > 0){
								for(String name : learnedAbilities){
									((CombatGUI) combatPanel).appendStatus(character
											.getName()
											+ " has learned "
											+ name + "!");
								}
							}
						}
					}
				}

				// remove defeated enemies from grid
				removeDefeatedEnemies(null);

				returnVal = true;
			} else {
				fightsLost++;
				returnVal = false;
			}
		}
		// viewMapPanel();
		return returnVal;
	}

	private ArrayList<Entity> cleanMonsterList(ArrayList<Entity> entities) {
		ArrayList<Entity> newArray = new ArrayList<Entity>();

		for (Entity e : entities) {
			if (e.alive() && (e.getExp() != 0)) {
				newArray.add(e);
			} else if(!e.alive()){
				((CombatGUI) combatPanel).appendStatus(e.getName()
						+ " was slain!");
				kills++;
				accumulatedExp += e.getExp();
			}
		}
		return newArray;
	}

	private ArrayList<Entity> mergeCombatantArrays(
			ArrayList<? extends Entity> a, ArrayList<? extends Entity> b) {
		ArrayList<Entity> newArray = new ArrayList<Entity>();
		for (Entity e : a) {
			newArray.add(e);
		}
		for (Entity e : b) {
			newArray.add(e);
		}

		return newArray;
	}
	
	public Item getItemByName(String name){
		Item returnVal = null;
		
		for(int i=0; i<itemListModel.size(); i++){
			if(itemListModel.get(i).getItemName().equals(name)){
				returnVal = itemListModel.get(i);
			}
		}
		
		return returnVal;
	}

	// inventory GUI methods
		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			((InventoryGUI) (inventoryPanel)).update();
		}

		// return the string of the character that is selected
		public Entity getSelectedCharacter() {
			return characters.get(characterIndex);
		}
		
		// return the selected item
		public Item getSelectedItem()
		{			
			int selectedItem = itemList.getSelectedIndex();
			
			//checks if item has recently been removed.
			if(selectedItem == -1 && itemListModel.size() == 0 ){			
				
				return null;			
			}
			
			else if(selectedItem == -1 && itemListModel.size() > 0 ){
				itemList.setSelectedIndex(0);
				return itemListModel.getElementAt(0);
			}
			else{
				return itemListModel.getElementAt(itemList.getSelectedIndex());
			}
		}

		// navigates to the previous character selection
		public void navigateCharacter(String pNav) {
			int size = characters.size();
			// if navigating to previous character
			if (pNav == "previous") {
				characterIndex -= 1;
				// if trying to navigate before first character, go to end
				if (characterIndex < 0) {
					characterIndex = size - 1;
				}
			}
			// if navigating to next character
			if (pNav == "next") {
				characterIndex += 1;
				// if no more characters in list, go to beginning
				if (characterIndex >= size) {
					characterIndex = 0;
				}
			}

		}

		// returns the item list
		public JList<Item> getItemList() {
			return this.itemList;
		}
			
		//adds an item to the 
		public void addItem(Item pItem)
		{
			itemListModel.add(itemIndex, pItem);
			itemIndex ++;
		}
		//removes item from list once equipped
		public void removeItem(int pIndex)
		{		
				itemListModel.remove(pIndex);
				itemList.setSelectedIndex(0);
				itemIndex --;						
		}

	// base = amount of experience required to level the first time
	// factor = factor by with required exp will grow (multipication)
	public int getExpNeeded(Entity entity) {

		int returnVal = base;

		for (int i = 1; i < entity.getLevel(); i++) {
			returnVal *= factor;
		}

		return returnVal;
	}

	public void levelUpCharacter(Entity entity) {
		entity.setExp(entity.getExp() - getExpNeeded(entity));
		entity.incLevel();
		entity.setLevelUpPoints(entity.getLevelUpPoints() + pointsPerLevel);
	}

} // end of GameEngine
// --------------------