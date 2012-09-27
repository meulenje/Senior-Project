package rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
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
public class GameEngine implements ActionListener, FocusListener {
	
	protected final int X_DIM = 600; // default window size
	protected final int Y_DIM = 600;
    protected final int BCOLS = 30; // board default column size
    protected final int BROWS = 20; // board default row size
	protected final int C_WIDTH = 25; // circle size
	protected final int C_HEIGHT = 25; // circle size
	protected final int G_X_DIM = BCOLS * (C_WIDTH + 3); // grid pane dimensions
	protected final int G_Y_DIM = BROWS * (C_HEIGHT); // grid pane dimensions
    protected int prow = 0; // player's row position
    protected int pcol = 0; // player's col position
    protected int wins = 0;
    protected GridObject[][] board; // the maximum board size
    
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
    protected Color playerColor = Color.RED;
    protected Color groundColor = Color.GREEN;
    protected Color holeColor = Color.BLACK;
    protected Color rockColor = Color.GRAY;
    protected Color doorColor = Color.YELLOW;
    protected String temp; // for catching input
    
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
    private JMenuItem resetItem;
    private JMenuItem newItem;
    private JMenuItem saveItem;
    private JMenuItem loadItem;
    protected JTabbedPane tabs;
    protected JPanel map;
    protected JPanel combat;

    
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
        JMenu viewMenu = new JMenu("View");
        quitItem = new JMenuItem("Quit");
        resetItem = new JMenuItem("Teleport");
        newItem = new JMenuItem("New Game");
        saveItem = new JMenuItem("Save Game");
        loadItem = new JMenuItem("Load Game");
        
        // add listeners
        newItem.addActionListener(this);
        fileMenu.add(newItem);
        loadItem.addActionListener(this);
        fileMenu.add(loadItem);
        saveItem.addActionListener(this);
        fileMenu.add(saveItem);
        quitItem.addActionListener(this);
        fileMenu.add(quitItem);
        resetItem.addActionListener(this);
        actionsMenu.add(resetItem);
        menubar.add(fileMenu);
        menubar.add(actionsMenu);
        menubar.add(viewMenu);
        
        // place the menu bar
        window.setJMenuBar(menubar);

        // --------------------------------------------------------       
        // create the GridGui map panel
        map = new GridGUI(this);
        // --------------------------------------------------------
        
        // --------------------------------------------------------
        // create the CombatGUI combat panel
        //combat = new CombatGUI(this);
        // --------------------------------------------------------

        // --------------------------------------------------------
        // create the InventoryGUI inventory panel
        // inventory = new InventoryGUI(this);
        // --------------------------------------------------------
        
        // create the TabbedPane
        tabs = new JTabbedPane();
        tabs.addTab("Map", map);
        tabs.addTab("Inventory", new JLabel("Inventory Panel"));
        tabs.addTab("Combat", combat);
        tabs.addFocusListener(this);
        // focus is needed to detect if a JPanel is active (in view) or not.
        // that way, the KeyListener can function properly for certain JPanels.
        
        // add the tab pane to the window
        window.add(tabs, BorderLayout.CENTER);
               
        // show window in default size dimensions
        window.setSize(new Dimension( X_DIM , Y_DIM + 50));
        window.setResizable(false);
        window.pack(); 
        window.setVisible(true);
        map.requestFocusInWindow();
       
    }

	/**
     * Display an error
     * @param e
     */
    public void printError(String e)
    {
        // found an error and is printing it according to the view
    	JOptionPane.showMessageDialog(window,e,"Error Message",JOptionPane.ERROR_MESSAGE);
    }
    
	/**
     * Display some information
     * @param s
     */
    public void printInfo(String s)
    {
        // found some information to print
    	JOptionPane.showMessageDialog(window,s,"Information Message",JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Prompt a Question
     * @param t
     * @return String
     */
    public String printQuestion(String t)
    {
        return JOptionPane.showInputDialog(window,t,"Input Message");
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
        return JOptionPane.showConfirmDialog(window,b,"Question Message",JOptionPane.YES_NO_OPTION);
    }


	/**
	 * A function to end the game
	 */
	public void endGame()
	{
		System.exit(1);
	} 
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// if the user clicks on a menu button
		
        if(arg0.getSource() == quitItem)
        {
            endGame();
        }
        else if(arg0.getSource() == resetItem)
        {
        	// erase the player
            board[prow][pcol].setEntity(0);
                    	
            // place the player, at the bottom middle of the board
            prow = BROWS-1;
            pcol = 3;
            board[prow][pcol].setEntity(1);
        }
	}

	@Override
	public void focusGained(FocusEvent e)
	{
		if(e.getSource() == tabs)
		{
			int tab = tabs.getSelectedIndex();
			if(tab == 0)
				map.requestFocus();
			else if(tab == 1)
				printInfo("Inventory Focus Gained.");
			else if(tab == 2)
				printInfo("Combat Focus Gained.");
				
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
	}
}
