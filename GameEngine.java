package rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
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

	// Game Engine specific variables
	protected final int MAXIMUMSIZE = 100; // Max board size for rows and
											// columns
	protected final int X_DIM = 600; // default frame window size
	protected final int Y_DIM = 600;
	protected final int C_WIDTH = 25; // object image size
	protected final int C_HEIGHT = 25; // object image size
	protected GridObject[][] board; // the maximum board size
	protected ArrayList<QuestObject> quests; // list of quest messages
	protected Clock klok; // a timer to control other settings
	protected Sequencer musicStream; // a sequencer to play background music
	protected Sequencer soundStream; // a sequencer to play sound effects
	protected int clockSpeed = 1000; // time between ticks in milliseconds

	/**
	 * Reference Grid BCOLS - X_DIM |-----------------------| BROWS| | | | | | |
	 * | | | | | | Y_DIM| | | | |-----------------------|
	 */
	// GridGUI specific variables
	protected int BCOLS = 25; // board default column size
	protected int BROWS = 25; // board default row size
	protected final int G_X_DIM = BCOLS * (C_WIDTH); // grid panel dimensions
	protected final int G_Y_DIM = BROWS * (C_HEIGHT); // grid panel dimensions
	protected int prow = 0; // player's row position
	protected int pcol = 0; // player's col position
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
	protected int BagID = 6;
	protected int SpikeID = 7;
	protected int MushroomID = 8;
	protected int BeartrapID = 9;
	protected int SpiralID = 10;
	protected int SignID = 11;

	// special Frame features
	protected boolean musicEnabled = true;
	protected boolean soundEnabled = true;
	protected boolean windowResizeable = true;
	protected Color backgroundColor = Color.black;
	protected Color foregroundColor = Color.white;
	protected Color highlightColor = Color.blue;

	// special mapPanel features
	protected boolean showHintsEnabled = false;
	protected boolean fogOfWar = false;
	protected boolean mappingEnabled = false;
	protected int playerVisionRange = 3;
	protected boolean warpingEnabled = true;
	protected boolean clearStatsPerLevel = false;
	protected int monsterGridSpeed = 2; // monster moves after X seconds
	protected double percentChanceOfEncounter = 0.0; // when entering a
														// Encounterable space,
														// there is a small
														// chance the player
														// will run into a
														// monster

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
	protected ImageIcon PlayerOutline = new ImageIcon(
			"images/PlayerOutline.png");
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
	protected ImageIcon Backpack = new ImageIcon("images/Backpack.png");
	protected ImageIcon Book = new ImageIcon("images/Book.png");
	protected ImageIcon InventoryIcon = new ImageIcon(
			"images/InventoryIcon.jpg");
	protected ImageIcon ListIcon = new ImageIcon("images/ListIcon.jpg");
	protected ImageIcon RPGLogo = new ImageIcon("images/RPGLogo.png");

	// sound files
	protected String menuTheme = "sounds/Golden Sun Menu Screen.mid";
	protected String overworldTheme = "sounds/Golden Sun Over World.mid";
	protected String battleTheme = "sounds/Golden Sun Battle Theme.mid";
	protected String battleStart = "sounds/Golden Sun Saturos Battle.mid";

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
	private JCheckBoxMenuItem enableMusicItem;
	private JCheckBoxMenuItem enableSoundItem;
	private JCheckBoxMenuItem enableHintsItem;
	private JCheckBoxMenuItem enableFogItem;
	private JCheckBoxMenuItem enableWarpItem;
	private JCheckBoxMenuItem enableMappingItem;
	protected JLayeredPane layers;
	protected JPanel loadingScreen;
	protected JPanel animationScreen;
	protected JPanel mainmenu;
	protected JPanel options;
	protected JTabbedPane tabs;
	protected JPanel mapPanel;
	protected JPanel combatPanel;
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
	private JList<Item> itemList = new JList<Item>(); // list to h
	private ArrayList<Item> itemsInBackpack; // array inventory items

	private int index = 0; // index to track selected character

	/**
	 * Constructor for GameEngine
	 */
	public GameEngine() {

		// combatPanel constructor
		characters = new ArrayList<Entity>(); // currentHealth, totalHealth,
												// attack, defense, speed
		Entity mario = new Entity(PlayerID, Player, "Mario", true, new Item(
				"Spike", "Mario's spike"), 30, 30, 20, 20, 11, 10, 10, 5);
		Entity luigi = new Entity(PlayerID, PlayerOutline, "Luigi", true,
				new Item("Spike", "Luigi's spike"), 30, 30, 30, 30, 10, 11, 9,
				5);
		Entity toad = new Entity(PlayerID, Mushroom, "Toad", true, new Item(
				"Spike", "Toad's spike"), 15, 15, 50, 50, 10, 10, 15, 5);
		Ability cure = new Ability("Heal", 1, 0, 20, 8);
		Ability fireball = new Ability("Super Fireball", 0, 1, 5, 10);
		luigi.abilities.add(cure);
		mario.abilities.add(fireball);
		toad.abilities.add(cure);
		characters.add(mario);
		characters.add(luigi);
		characters.add(toad);

		// inventoryPanel
		// initialize array list of items
		itemsInBackpack = new ArrayList<Item>();
		itemsInBackpack.add(new Item("Book", "Something to read"));
		itemsInBackpack.add(new Item("Gem", "A gem"));
		itemsInBackpack.add(new Item("Beartrap", "Used to break ankles"));
		itemsInBackpack.add(new Item("Bag", "Open to view items"));
		itemsInBackpack.add(new Item("Chair", "Sit on"));
		itemsInBackpack.add(new Item("Rock", "Throw at pirates"));
		itemsInBackpack.add(new Item("WhiteBoard", "Write on me"));
		itemsInBackpack.add(new Item("Creature", "Bad dude"));
		itemsInBackpack.add(new Item("BookCase", "Holds your books for you"));
		itemsInBackpack.add(new Item("Door1", "Open and walk through"));

		// initailize the list
		itemList = new JList<Item>(getItemArray()); // JList that displays the
													// items
		itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		itemList.setSelectedIndex(0);
		itemList.addListSelectionListener((ListSelectionListener) this);

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
		enableMusicItem = new JCheckBoxMenuItem("Play Music");
		enableSoundItem = new JCheckBoxMenuItem("Play SFX");
		enableHintsItem = new JCheckBoxMenuItem("Show Hints");
		enableFogItem = new JCheckBoxMenuItem("Fog of War");
		enableMappingItem = new JCheckBoxMenuItem("Show Visited");
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
		menubar.add(Box.createHorizontalStrut(350));
		menubar.add(time);
		// place the menu bar
		window.setJMenuBar(menubar);

		// TABS

		// --------------------------------------------------------
		// create the GridGui mapPanel tab
		mapPanel = new GridGUI(this);
		// --------------------------------------------------------

		// --------------------------------------------------------
		// create the CombatGUI combatPanel tab
		combatPanel = new CombatGUI(this);
		// --------------------------------------------------------

		// --------------------------------------------------------
		// create the InventoryGUI inventoryPanel tab
		inventoryPanel = new InventoryGUI(this);
		// --------------------------------------------------------

		// --------------------------------------------------------
		// create the StatsGUI statsPanel tab
		statsPanel = new StatsGUI(this);
		// --------------------------------------------------------

		// --------------------------------------------------------
		// create the QuestGUI questPanel tab
		questPanel = new QuestGUI(this);
		// --------------------------------------------------------

		// create the TabbedPane
		tabs = new JTabbedPane();
		tabs.addTab("Map", mapPanel);
		tabs.addTab("Inventory", inventoryPanel);
		tabs.addTab("Stats", statsPanel);
		tabs.addTab("Combat", combatPanel);
		tabs.addTab("Quests", questPanel);
		tabs.addFocusListener(this);
		tabs.setLocation(0, 0);
		tabs.setSize(X_DIM, Y_DIM + 60);

		// --------------------------------------------------------
		// make the main menu gui
		mainmenu = new MainMenuGUI(this);
		mainmenu.setLocation(0, 0);
		mainmenu.setSize(X_DIM, Y_DIM + 60);
		// --------------------------------------------------------

		// --------------------------------------------------------
		// make the options gui
		options = new OptionsGUI(this);
		options.setLocation(0, 0);
		options.setSize(X_DIM, Y_DIM + 60);
		// --------------------------------------------------------

		// --------------------------------------------------------
		// make the animation screen
		animationScreen = new AnimationGUI(this);
		animationScreen.setLocation(0, 0);
		animationScreen.setSize(X_DIM, Y_DIM + 60);
		// --------------------------------------------------------

		// --------------------------------------------------------
		// make a loading screen
		loadingScreen = new JPanel();
		loadingScreen.setLayout(new BorderLayout());
		loadingScreen.setBackground(backgroundColor);
		JLabel loadingText = new JLabel("", JLabel.CENTER);
		loadingText.setIcon(new ImageIcon("images/loading.gif"));
		loadingScreen.add(loadingText, BorderLayout.CENTER);
		loadingScreen.setLocation(0, 0);
		loadingScreen.setPreferredSize(new Dimension(X_DIM, Y_DIM + 60));
		loadingScreen.setSize(X_DIM, Y_DIM + 60);
		// --------------------------------------------------------

		// create layers panel
		// 0 -- is for the GamePanels (Grid, Combat, etc)
		// 100 -- is for the MainMenu
		// 125 -- is for the Options Menu
		// 150 -- is for the Animation Screen
		// 200 -- is for the Loading Screen
		layers = new JLayeredPane();
		layers.setLayout(new BorderLayout());
		layers.add(tabs);
		layers.add(mainmenu);
		layers.add(options);
		layers.add(animationScreen);
		layers.add(loadingScreen);
		layers.setLayer(tabs, 0);
		layers.setLayer(mainmenu, 100);
		layers.setLayer(options, 120);
		layers.setLayer(animationScreen, 150);
		layers.setLayer(loadingScreen, 200);

		// add the layers to the window
		window.add(layers, BorderLayout.CENTER);

		// show window in default size dimensions
		window.setSize(new Dimension(X_DIM, Y_DIM));
		window.setResizable(windowResizeable);
		window.pack();
		window.setVisible(true);

		// create the clock and clock status bar at the bottom
		// listen to the timer,
		// on every tick, event() will be called.
		klok = new Clock();
		klok.register(this); // The Engine listens to the Clock
		klok.register((GridGUI) mapPanel); // the Grid listens to the Clock
		klok.setRate(clockSpeed); // time passes per tick by this speed in
									// milliseconds

		// creates two Sequencers and initializes them.
		// this is used to play music and sound effects
		initializeAudioStreams();

		// view main menu (also plays music)
		viewMainMenu();
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
	 * A function to start a new game. This is not the function to start the
	 * next level!
	 */
	public void newGame() {
		// choose player
		// TODO

		// choose maps/levels
		// TODO

		// temporarily view the loading screen
		viewLoadingScreen();

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

		// load a new map
		loadMap();

		// reset clock
		klok.pause();
		klok.currentTime = 0;

		// view the mapPanel
		viewMapPanel();
	}

	/**
	 * A function to end the game. This is not the function to end the current
	 * level!
	 */
	public void endGame() {
		// reset clock
		klok.pause();

		// delete board, quests
		deleteGridObjects();
		deleteQuestList();

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
		System.exit(1);
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
		if (tabs.getSelectedIndex() == 0)
			klok.run(Clock.FOREVER);
	}

	/**
	 * Display an error
	 * 
	 * @param e
	 */
	public void printError(String e) {
		// pause time if on the map
		if (tabs.getSelectedIndex() == 0)
			klok.pause();

		// found an error and is printing it according to the view
		JOptionPane.showMessageDialog(window, e, "ERROR",
				JOptionPane.ERROR_MESSAGE);

		// resume time if on the map
		if (tabs.getSelectedIndex() == 0)
			klok.run(Clock.FOREVER);
	}

	/**
	 * Display some information
	 * 
	 * @param s
	 */
	public void printInfo(String s) {
		// pause time if on the map
		if (tabs.getSelectedIndex() == 0)
			klok.pause();

		// found some information to print
		JOptionPane.showMessageDialog(window, s, "Information",
				JOptionPane.INFORMATION_MESSAGE);

		// resume time if on the map
		if (tabs.getSelectedIndex() == 0)
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
		if (tabs.getSelectedIndex() == 0)
			klok.pause();

		String r = JOptionPane.showInputDialog(window, t, "Question",
				JOptionPane.QUESTION_MESSAGE);

		// resume time if on the map
		if (tabs.getSelectedIndex() == 0)
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
		if (tabs.getSelectedIndex() == 0)
			klok.pause();

		int r = JOptionPane.showConfirmDialog(window, b, "Question",
				JOptionPane.YES_NO_OPTION);

		// resume time if on the map
		if (tabs.getSelectedIndex() == 0)
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
		if (tabs.getSelectedIndex() == 0)
			klok.pause();

		int r = JOptionPane.showOptionDialog(window, question, "Question",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, choices, choices[1]);

		// resume time if on the map
		if (tabs.getSelectedIndex() == 0)
			klok.run(Clock.FOREVER);

		return r;
	}

	/**
	 * Forces player to view Quests
	 */
	public void viewQuestPanel() {
		loadingScreen.setVisible(false);
		animationScreen.setVisible(false);
		mainmenu.setVisible(false);
		options.setVisible(false);
		tabs.setVisible(true);
		tabs.setSelectedIndex(4);
		questPanel.requestFocus();
		klok.pause();
	}

	/**
	 * Forces player to view Combat
	 */
	public void viewCombatPanel() {
		loadingScreen.setVisible(false);
		animationScreen.setVisible(false);
		mainmenu.setVisible(false);
		options.setVisible(false);
		tabs.setVisible(true);
		tabs.setSelectedIndex(3);
		combatPanel.requestFocus();
		klok.pause();

		// play battle music
		playMusic(battleTheme, true);
	}

	/**
	 * Forces player to view Inventory
	 */
	public void viewInventoryPanel() {
		loadingScreen.setVisible(false);
		animationScreen.setVisible(false);
		mainmenu.setVisible(false);
		options.setVisible(false);
		tabs.setVisible(true);
		tabs.setSelectedIndex(1);
		inventoryPanel.requestFocus();
		klok.pause();

		// TODO : call .update()
	}

	public void viewStatsPanel() {
		loadingScreen.setVisible(false);
		animationScreen.setVisible(false);
		mainmenu.setVisible(false);
		options.setVisible(false);
		tabs.setVisible(true);
		tabs.setSelectedIndex(2);
		statsPanel.requestFocus();
		klok.pause();

		// update the view
		((StatsGUI) statsPanel).update();
	}

	/**
	 * Forces player to view Map
	 */
	public void viewMapPanel() {
		loadingScreen.setVisible(false);
		animationScreen.setVisible(false);
		mainmenu.setVisible(false);
		options.setVisible(false);
		tabs.setVisible(true);
		tabs.setSelectedIndex(0);
		mapPanel.requestFocus();
		klok.run(Clock.FOREVER);

		// play overworld music
		playMusic(overworldTheme, true);
	}

	/**
	 * Forces player to view the Main Menu
	 */
	public void viewMainMenu() {
		loadingScreen.setVisible(false);
		animationScreen.setVisible(false);
		mainmenu.setVisible(true);
		options.setVisible(false);
		tabs.setVisible(false);
		mainmenu.requestFocus();

		// play menu music
		playMusic(menuTheme, true);
	}

	public void viewOptions() {
		loadingScreen.setVisible(false);
		animationScreen.setVisible(false);
		mainmenu.setVisible(false);
		options.setVisible(true);
		tabs.setVisible(false);
		options.requestFocus();
	}

	/**
	 * Forces player to view the Loading Screen
	 */
	public void viewLoadingScreen() {
		loadingScreen.setVisible(true);
		animationScreen.setVisible(false);
		mainmenu.setVisible(false);
		options.setVisible(false);
		tabs.setVisible(false);
	}

	/**
	 * Forces the player to see the Animation
	 * 
	 * This function will turn the Animation Screen back to being invisible once
	 * the animation is done playing.
	 * 
	 * Do not set animationScreen to invisible, elsewhere.
	 * 
	 * @param view
	 */
	public void viewAnimationScreen() {
		animationScreen.setVisible(true);
		((AnimationGUI) animationScreen).startAnimation();
		animationScreen.setVisible(true);
		// wait until the animation is finished before returning
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
		}
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
	 * Removes all warps from the mapPanel, and replaces them grass
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
	public void removeDefeatedEnemies(Item i) {
		// remove any enemy objects that are NSWE from the player's current
		// position
		if (prow != BROWS - 1 && board[prow + 1][pcol].isMonster())
			board[prow + 1][pcol].setObject(i);

		if (prow != 0 && board[prow - 1][pcol].isMonster())
			board[prow - 1][pcol].setObject(i);

		if (pcol != BCOLS && board[prow][pcol + 1].isMonster())
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
			// replace with next level finder TODO
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

		// check NSWE of player position for enemies
		if (prow != BROWS - 1 && board[prow + 1][pcol].isMonster())
			monsterCount++;

		if (prow != 0 && board[prow - 1][pcol].isMonster())
			monsterCount++;

		if (pcol != BCOLS - 1 && board[prow][pcol + 1].isMonster())
			monsterCount++;

		if (pcol != 0 && board[prow][pcol - 1].isMonster())
			monsterCount++;

		// check for chance of random encounter on certain terrain (Eg. Tall
		// Grass)
		if (board[prow][pcol].isRandomEncounter()) {
			Random rand = new Random();
			double r = rand.nextDouble(); // 0.0 to 1.0
			if (r <= percentChanceOfEncounter) // % chance of random encounter
			{
				// A surprise encounter happens!
				monsterCount++;
			}
		}

		encounters += monsterCount; // count the number

		if (monsterCount > 0) {
			// populate enemies team with the number of monsters we ran into
			initializeCombat();

			// go to combatPanel tab
			viewCombatPanel();

		}

		return;
	}

	/**
	 * Checks the player's current position to see if they are standing over any
	 * traps.
	 */
	public void checkForTrap(RPGObject i) {
		if (i != null) {
			// check for beartrap
			if (i.id == BeartrapID) {
				// keep track of how many
				traps++;

				// inflict 10% damage of totalHealth to first player
				characters.get(0).setCurrentHealth(
						characters.get(0).getCurrentHealth()
								- (int) ((double) characters.get(0)
										.getMaxHealth() * 0.1));
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
	 * Check if the player stepped on an item.
	 * 
	 * @param i
	 */
	public void checkForItem(RPGObject i) {
		// This function should be improved to look at Entity attributes,
		// and not just an id number. TODO

		if (i != null) {
			itemsCollected++;

			// Is it a special item? (When picked up, it pops an information
			// box.)
			if (i.id == BagID) {
				// show a popup
				((GridGUI) mapPanel).setPointsLabel("Bags: " + itemsCollected);
			}

			// add item to inventory
			// TODO
		}
		return;
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
			printInfo("The sign reads...\n\n\'Hold \'A\' to charge your laser.\'");
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
			checkForEnemies(); // starts combat if needed
		} else // bumped into something
		{
			checkForEnemies();// starts combat if needed

			// show hint if they bumped a hole
			if (showHintsEnabled && board[prow + x][pcol + y].isHole())
				printInfo("You can hop over holes by\nholding down the left-shift key.");
		}

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
						board[i][j].setFog(EmptyID);
						board[i][j].setVisible(true);
					} else if (i == prow + playerVisionRange
							&& j < pcol + playerVisionRange
							&& j > pcol - playerVisionRange) {
						// edge of lower vision
						board[i][j].setFog(FogBottomID);
						board[i][j].setVisible(true);
					} else if (i == prow - playerVisionRange
							&& j < pcol + playerVisionRange
							&& j > pcol - playerVisionRange) {
						// edge of upper vision
						board[i][j].setFog(FogTopID);
						board[i][j].setVisible(true);
					} else if (j == pcol + playerVisionRange
							&& i < prow + playerVisionRange
							&& i > prow - playerVisionRange) {
						// edge of right-side vision
						board[i][j].setFog(FogRightID);
						board[i][j].setVisible(true);
					} else if (j == pcol - playerVisionRange
							&& i < prow + playerVisionRange
							&& i > prow - playerVisionRange) {
						// edge of left-side vision
						board[i][j].setFog(FogLeftID);
						board[i][j].setVisible(true);
					} else if (mappingEnabled && board[i][j].isVisible()) {
						// was previously seen, but now out of range
						board[i][j].setFog(FogCenterID);
						board[i][j].setVisible(true);
					} else // out of vision range
					{
						board[i][j].setFog(EmptyID);
						board[i][j].setVisible(false);
					}
				}
			}
		}
	} // end of movePlayerVision()

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
					board[i][j].setFog(0);
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
						board[i][j].setFog(0);
					else
						board[i][j].setFog(FogCenterID);
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
		boolean spiralPlaced = false;
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
							LavaMonster, "LavaMonster", false, null, 10, 10,
							10, 10, 5, 5, 5, 5), null);
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
					board[i][j].resetObject(new Terrain(WarpAID, XSpace, false,
							true, false), null, null);
					warpAPlaced = true;
				}
				// place warp 'b'
				else if (warpingEnabled && !warpBPlaced && temp == 9
						&& (i >= BROWS / 2)) {
					board[i][j].resetObject(new Terrain(WarpBID, XSpace, false,
							true, false), null, null);
					warpBPlaced = true;
				}
				// randomly place one exit per map
				else if (!doorPlaced && (temp == 10 || (i == 3 && j == 3))) {
					board[i][j].resetObject(new Terrain(GrassID, Grass, true,
							true, false), null, new NonEntity(ExitID, Hideout,
							false, false, false, false));
					doorPlaced = true;
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
								false, true, false), null, new NonEntity(
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

	@Override
	public void actionPerformed(ActionEvent Trigger) {
		// if the user clicks on a menu button

		if (Trigger.getSource() == newItem) {
			int answer = printYesNoQuestion("Are you sure you want\nto start a new mapPanel?");
			if (answer == 0)
				newGame();
		} else if (Trigger.getSource() == saveItem) {
			// save the game
			printError("Save unsuccessful!");
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
				mappingEnabled = false; // mapPanelping is useless without fog
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
		if (e.getSource() == tabs) {
			int tab = tabs.getSelectedIndex();
			if (tab == 0) {
				mapPanel.requestFocus();
				klok.run(Clock.FOREVER);
			} else if (tab == 1) {
				klok.pause();
				inventoryPanel.requestFocus();
			} else if (tab == 2) {
				klok.pause();
				statsPanel.requestFocus();
			} else if (tab == 3) {
				klok.pause();
				combatPanel.requestFocus();
			} else if (tab == 4) {
				klok.pause();
				questPanel.requestFocus();
			}
		} else
			klok.pause();
	}

	@Override
	public void focusLost(FocusEvent e) {
		// Auto-generated method stub
	}

	@Override
	public boolean event(int tick) {
		// update the clock time on the JMenu
		time.setText("Time: " + klok.currentTime);

		// update grid gui health bar and name
		((GridGUI) mapPanel).updatePlayerName(characters.get(0).getName());
		int hp = (int) (((double) characters.get(0).getCurrentHealth() / (double) characters
				.get(0).getMaxHealth()) * 100);
		((GridGUI) mapPanel).updateHealthBar(hp);
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

	/**
	 * Saves to file, the current game in progress.
	 */
	public void saveGame() {
		// TODO
	}

	/**
	 * Loads from file, a game in progress.
	 */
	public void loadGame() {
		// TODO
	}

	/**
	 * Loads from file, the next map for this game.
	 */
	public void loadMap() {
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

		// add new quest to messageGUI
		addQuest(questsTotal, Hideout, "Find the Exit",
				"Try to find the exit in this level.", "Started");

		questsTotal++;
	}

	public void initializeCombat() {
		enemies = initializeEnemies(0);
		actor = null;
		event = null;
		combatOver = false;
		turnStack = new Stack<Entity>();
		combatResult = 0;
		combatants = new ArrayList<Entity>();
		accumulatedExp = 0;
		((CombatGUI) combatPanel).resetCombatGUI();

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
		} else {
			((CombatGUI) combatPanel).update();
			System.out.println("akdbnasdjknaskdjasd");
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

	}

	public void monsterTurn(Entity m) {

		String action = m.monsterTurn();
		if (action.equals("attack")) {
			int attackTargets = characters.size();
			int attackTargetIndex = randomNumberGenerator
					.nextInt(attackTargets);
			Entity attackTarget = characters.get(attackTargetIndex);

			while (!attackTarget.alive()) {
				attackTargetIndex = randomNumberGenerator
						.nextInt(attackTargets);
				attackTarget = characters.get(attackTargetIndex);
			}

			executeAttack(m, attackTarget);

		} else {
			/**
			 * event = executeAbility(m, 0, enemies, characters,
			 * actor.abilities.get(0));
			 **/
		}

		turnStack.pop();
		endTurn();
	}

	public void playerTurn(String action, int target) {

		actor = turnStack.pop();

		// start character turn

		// actions for character
		if (action.equals("Attack")) {
			executeAttack(actor, combatants.get(target));
		}

		else if (action.equals("Wait")) {
			event = actor.getName() + " waits.";
		}

		else if (action.equals("Flee")) {
			if (attemptToFlee()) {
				((CombatGUI) combatPanel).appendStatus("You have fled successfully!");
				combatOver = true;
				combatResult = 0;
			} else {
				((CombatGUI) combatPanel).appendStatus("Your attempt to flee failed!");
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
						+ randomNumberGenerator.nextInt(6);
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
						+ randomNumberGenerator.nextInt(6);
				int defenseVal = avgDefense + randomNumberGenerator.nextInt(6);

				calculatedDamage = attackVal - (defenseVal / 2);
				if (calculatedDamage < 0) {
					calculatedDamage = 0;
				}

				for (Entity e : enemies) {
					e.setCurrentHealth(e.getCurrentHealth() - calculatedDamage);
					result = (source.getName() + "'s " + ability.getName()
							+ " hit all enemies for " + calculatedDamage + " damage!");
				}
			}

			break;

		// healing ability
		case 1:
			int amountHealed = source.getAttack()
					+ randomNumberGenerator.nextInt(6);
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

		}

		source.setCurrentMana(source.getCurrentMana() - ability.getCost());
		((CombatGUI) combatPanel).appendStatus(result);

	}

	public boolean attemptToFlee() {
		boolean result;

		int roll = randomNumberGenerator.nextInt(3);
		if (roll == 2) {
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

	public ArrayList<Entity> initializeEnemies(int zoneID) {
		ArrayList<Entity> monsterArray = new ArrayList<Entity>();
		// int numberOfEnemies = randomNumberGenerator.nextInt(5) + 1;
		int numberOfEnemies = 8;
		Entity m;

		for (int i = 1; i <= numberOfEnemies; i++) {
			m = new Entity(LavaMonsterID, LavaMonster, "LM", false, null, 10,
					10, 10, 10, 10, 10, 1, 5);
			m.setExp(5);
			Ability cure = new Ability("heal", 1, 0, 0, 5);
			m.abilities.add(cure);
			m.setName(m.getName() + " " + i);
			monsterArray.add(m);
		}
		return monsterArray;
	}

	public boolean endCombat(ArrayList<Entity> characters, int experience,
			int result) {

		boolean returnVal = false;
		if (result == 0) {
			// code to handle running away (nothing happens)
			returnVal = true;
		} else {
			if (result == 1) {
				for (Entity character : characters) {
					character.setExp(character.getExp() + experience);
					((CombatGUI) combatPanel).appendStatus(character.getName()
							+ " has earned " + experience + " experience!");
				}
				returnVal = true;
			} else {
				returnVal = false;
			}
		}
		// viewMapPanel();
		return returnVal;
	}

	private ArrayList<Entity> cleanMonsterList(ArrayList<Entity> entities) {
		ArrayList<Entity> newArray = new ArrayList<Entity>();

		for (Entity e : entities) {
			if (e.alive()) {
				newArray.add(e);
			} else {
				((CombatGUI) combatPanel).appendStatus(e.getName()
						+ " was slain!");
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

	// inventory GUI methods
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		((InventoryGUI) (inventoryPanel))
				.updateBackpackLabel(getSelectedItem());
	}

	// return the string of the character that is selected
	public Entity getSelectedCharacter() {
		return characters.get(index);
	}

	// return Item at index
	public Item getItem(int pIndex) {
		return itemsInBackpack.get(pIndex);

	}

	// return the string of the item that is selected
	public Item getSelectedItem() {
		return getItem(itemList.getSelectedIndex());
	}

	// navigates to the previous character selection
	public void navigateCharacter(String pNav) {
		int size = characters.size();
		// if navigating to previous character
		if (pNav == "previous") {
			index -= 1;
			// if trying to navigate before first character, go to end
			if (index < 0) {
				index = size - 1;
			}
		}
		// if navigating to next character
		if (pNav == "next") {
			index += 1;
			// if no more characters in list, go to beginning
			if (index >= size) {
				index = 0;
			}
		}

	}

	// returns the item list
	public JList<Item> getItemList() {
		return this.itemList;
	}

	// returns an array of the list
	public Item[] getItemArray() {
		Item[] array = new Item[itemsInBackpack.size()];
		itemsInBackpack.toArray(array);
		return array;
	}

} // end of GameEngine
// --------------------
