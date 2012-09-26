package rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
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
public class GridEngine implements ActionListener {
	
	protected final int X_DIM = 600; // default window size
	protected final int Y_DIM = 600;
    protected final int BCOLS = 30; // board default column size
    protected final int BROWS = 20; // board default row size
	protected final int C_WIDTH = 25; // circle size
	protected final int C_HEIGHT = 25; // circle size
	protected final int G_X_DIM = BCOLS * (C_WIDTH ); // grid pane dimensions
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
	protected Image PlayerFront = new ImageIcon("PlayerFront.png").getImage();
	protected Image PlayerBack = new ImageIcon("PlayerBack.png").getImage();
	protected Image PlayerLeft = new ImageIcon("PlayerLeft.png").getImage();
	protected Image PlayerRight = new ImageIcon("PlayerRight.png").getImage();
	protected Image PlayerGrassFront = new ImageIcon("PlayerGrassFront.gif").getImage();
	protected Image PlayerGrassBack = new ImageIcon("PlayerGrassBack.gif").getImage();
	protected Image PlayerGrassLeft = new ImageIcon("PlayerGrassLeft.gif").getImage();
	protected Image PlayerGrassRight = new ImageIcon("PlayerGrassRight.gif").getImage();
	protected Image PlayerDirtFront = new ImageIcon("PlayerGrassFront.gif").getImage();
	protected Image PlayerDirtBack = new ImageIcon("PlayerGrassFront.gif").getImage();
	protected Image PlayerDirtLeft = new ImageIcon("PlayerGrassLeft.gif").getImage();
	protected Image PlayerDirtRight = new ImageIcon("PlayerGrassRight.gif").getImage();
	protected Image GlowingGem = new ImageIcon("GlowingGem.gif").getImage();
	protected Image Dirt = new ImageIcon("Dirt.png").getImage();
	protected Image Grass = new ImageIcon("Grass.png").getImage();
	protected Image DirtRock = new ImageIcon("DirtRock.png").getImage();
	protected Image GrassRock = new ImageIcon("GrassRock.png").getImage();
	protected Image DirtHole = new ImageIcon("DirtHole.png").getImage();
	protected Image GrassHole = new ImageIcon("GrassHole.png").getImage();
	protected Image Wall = new ImageIcon("Wall.png").getImage();
	protected Image Wall2 = new ImageIcon("Wall2.png").getImage();
	protected Image DirtBush = new ImageIcon("DirtBush.gif").getImage();
	
    
    // default gui parts
    private JFrame window;
    private JMenuItem quitItem;
    private JMenuItem resetItem;
    private JMenuItem newItem;
    private JMenuItem saveItem;
    private JMenuItem loadItem;
    protected JTabbedPane tabs;
    private JProgressBar healthBar;
    private JProgressBar magicBar;
    private JProgressBar expBar;
    private JButton resetButton;
    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;
    private JTextField nameField;
    private JPanel buttonPanel;
    private JScrollPane scrollPanel;
    private JPanel gridPanel;

    
    public GridEngine()
    {                 
        // Create the JWindow and Frame to hold the Grid panel
    	
    	

    	
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
        
        
        // create the button panel
        buttonPanel = new JPanel(new GridLayout());
        buttonPanel.setPreferredSize(new Dimension( X_DIM , 50 ));
        buttonPanel.setBackground(Color.YELLOW);
        JPanel arrowPanel = new JPanel(new GridLayout(2,3,0,0));
        JPanel statsPanel = new JPanel(new GridLayout(3,3,0,0));

        //JButtons and listeners
        nameField = new JTextField("Player1");
        resetButton = new JButton("Teleport");
        resetButton.addActionListener(this);
        leftButton = new JButton("<");
        leftButton.addActionListener(this);
        downButton = new JButton("v");
        downButton.addActionListener(this);
        upButton = new JButton("^");
        upButton.addActionListener(this);
        rightButton = new JButton(">");
        rightButton.addActionListener(this);
        
        // health bar for the player
        healthBar = new JProgressBar(0);
        healthBar.setForeground(Color.RED);
        healthBar.setBorderPainted(false);
        healthBar.setValue(100);
        
        // magic bar for the player
        magicBar = new JProgressBar(0);
        magicBar.setForeground(Color.BLUE);
        magicBar.setBorderPainted(false);
        magicBar.setValue(100);
        
        // experience bar for the player
        expBar = new JProgressBar(0);
        expBar.setForeground(Color.GREEN);
        expBar.setBorderPainted(false);
        expBar.setValue(15);
        
        statsPanel.add(nameField);
        statsPanel.add(new JLabel("HP:",JLabel.RIGHT));
        statsPanel.add(healthBar);
        statsPanel.add(new JLabel(""));
        statsPanel.add(new JLabel("Magic:",JLabel.RIGHT));
        statsPanel.add(magicBar);
        statsPanel.add(new JLabel(""));
        statsPanel.add(new JLabel("EXP:",JLabel.RIGHT));
        statsPanel.add(expBar);
        
        arrowPanel.add(new JLabel(""));
        arrowPanel.add(upButton);
        arrowPanel.add(new JLabel(""));
        arrowPanel.add(leftButton);
        arrowPanel.add(downButton);
        arrowPanel.add(rightButton);
        
        buttonPanel.add(statsPanel, BorderLayout.WEST);
        buttonPanel.add(new JPanel()); // empty panel
        buttonPanel.add(arrowPanel, BorderLayout.EAST);
        
        
        
        // Grid Constructed by GridGUI.java
        board = new GridObject[BROWS][BCOLS];
        gridPanel = new GridGUI(this);
        
        // place the player, at the bottom left
        prow = BROWS-1;
        pcol = 3;
        board[prow][pcol].setEntity(1);
        
        // Build the ScrollPanel
        // Scroll Pane for GridGUI
        scrollPanel = new JScrollPane();
        scrollPanel.setViewportView(gridPanel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
       	scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setPreferredSize(new Dimension( X_DIM, Y_DIM - 30 ));
        
        // create a simple panel to group the map pieces together
        JPanel map = new JPanel();
        map.add(scrollPanel);
        map.add(buttonPanel, BorderLayout.SOUTH);
        map.setPreferredSize(new Dimension( X_DIM, Y_DIM + 35));
        
        // create the TabbedPane
        tabs = new JTabbedPane();
        tabs.addTab("Map", map);
        tabs.addTab("Inventory", new JButton("Inventory Panel"));
        tabs.addTab("Combat", new JButton("Combat Panel"));
        
        // add the tab pane to the window
        window.add(tabs, BorderLayout.CENTER);
      
        // compile components into the JFrame
        //window.add(scrollPanel);
        //window.add(buttonPanel, BorderLayout.SOUTH);
               
        // show window in default size dimensions
        window.setSize(new Dimension( X_DIM , Y_DIM + 50));
        window.setResizable(false);
        window.pack(); 
        window.setVisible(true);
        gridPanel.requestFocus(); // needed for key listener
       
    }

	/**
     * Display an error
     * @param e
     */
    public void errorPrint(String e)
    {
        // found an error and is printing it according to the view
    	JOptionPane.showMessageDialog(window,e,"Error Message",JOptionPane.ERROR_MESSAGE);
    }
    
	/**
     * Display some information
     * @param s
     */
    public void infoPrint(String s)
    {
        // found some information to print
    	JOptionPane.showMessageDialog(window,s,"Information Message",JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Prompt a Question
     * @param t
     * @return String
     */
    public String throwPrint(String t)
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
    public int yesNoPrint(String b)
    {
        return JOptionPane.showConfirmDialog(window,b,"Question Message",JOptionPane.YES_NO_OPTION);
    }

    
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// if the user clicks on a button
		
        if(arg0.getSource() == quitItem)
        {
            System.exit(0);
        }
        else if(arg0.getSource() == resetButton || arg0.getSource() == resetItem)
        {
        	// erase the player
            board[prow][pcol].setEntity(0);
                    	
            // place the player, at the bottom middle of the board
            prow = BROWS-1;
            pcol = 3;
            board[prow][pcol].setEntity(1);
        }
        else if(arg0.getSource() == null)
        {
        	errorPrint("What did you do?!");
        }
        else if(arg0.getSource() == upButton)
        {
        	// increment the players coordinates
        	((GridGUI) gridPanel).movePlayer(-1,0);
        }
        else if(arg0.getSource() == downButton)
        {
        	// increment the players coordinates
        	((GridGUI) gridPanel).movePlayer(1,0);
        }
        else if(arg0.getSource() == leftButton)
        {
        	// increment the players coordinates
        	((GridGUI) gridPanel).movePlayer(0,-1);
        }
        else if(arg0.getSource() == rightButton)
        {
        	// increment the players coordinates
        	((GridGUI) gridPanel).movePlayer(0,1);
        }
              
        gridPanel.requestFocus();
	}

	/**
	 * A function to end the game
	 */
	public void endGame()
	{
		System.exit(1);
	}  
}
