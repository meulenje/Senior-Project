package rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * GridGUI class
 * 
 * Used for showing the game board and using
 * the arrow keys to move the player around
 * @author Austin Delamar
 * @version 9/25/2012
 * 
 */
@SuppressWarnings("serial")
public class GridGUI extends JPanel implements KeyListener, ActionListener {

	private GameEngine GE; // link back to Engine
	private boolean up = false; // 38
	private boolean down = false; // 40
	private boolean left = false; // 37
	private boolean right = false; // 39
	private boolean leftShift = false; // 16
	private boolean spaceBar = false; // 32
	private int hops = 0; // # of jumps taken
	private int steps = 0; // # of steps taken
	private int encounters = 0; // # of encounters
	private int rocksPushed = 0; // # of rock pushes
	private int rocksInHoles = 0; // # of rocks pushed into holes
	private int keysPushed = 0; // # of keyboard buttons pushed
	
	// gui parts
	private JPanel gridPanel;
    private JPanel buttonPanel;
    private JScrollPane scrollPanel;
    private JProgressBar healthBar;
    private JProgressBar magicBar;
    private JProgressBar expBar;
    private JButton resetButton;
    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;
    private JTextField nameField;

	
	public GridGUI(GameEngine tempEngine)
	{
		// link back to JFramel
		GE = tempEngine;
		
		// create the board
		GE.board = new GridObject[GE.BROWS][GE.BCOLS];
		
		// Build the Grid to hold the Objects
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(GE.BROWS, GE.BCOLS, 0, 0));
        gridPanel.setPreferredSize(new Dimension( GE.G_X_DIM , GE.G_Y_DIM ));
        
        // Build the Glue Panel that fills in empty space
        JPanel extraPanel = new JPanel();
        extraPanel.setBorder(BorderFactory.createEmptyBorder());
        extraPanel.setBackground(Color.BLACK);
        extraPanel.add(Box.createHorizontalGlue());
        extraPanel.add(gridPanel);
        extraPanel.add(Box.createHorizontalGlue());
        
        // Build the ScrollPanel to allow scrolling
        scrollPanel = new JScrollPane();
        scrollPanel.setViewportView(extraPanel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
       	scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setPreferredSize(new Dimension( GE.X_DIM, GE.Y_DIM - 30));
        
        
        // Build the button panel
        buttonPanel = new JPanel(new GridLayout());
        buttonPanel.setPreferredSize(new Dimension( GE.X_DIM , 50 ));
        buttonPanel.setBackground(Color.RED);
        JPanel arrowPanel = new JPanel(new GridLayout(2,3,0,0));
        JPanel statsPanel = new JPanel(new GridLayout(3,3,0,0));

        //JButtons and listeners
        nameField = new JTextField("Louie");
        nameField.setSize(20, 10);
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
        healthBar.setBorderPainted(true);
        healthBar.setValue(100);
        
        // magic bar for the player
        magicBar = new JProgressBar(0);
        magicBar.setForeground(Color.BLUE);
        magicBar.setBorderPainted(true);
        magicBar.setValue(90);
        
        // experience bar for the player
        expBar = new JProgressBar(0);
        expBar.setForeground(Color.GREEN);
        expBar.setBorderPainted(true);
        expBar.setValue(50);
        
        // Build Health Bar Area
        statsPanel.add(nameField);
        statsPanel.add(new JLabel("HP:",JLabel.RIGHT));
        statsPanel.add(healthBar);
        statsPanel.add(new JLabel(""));
        statsPanel.add(new JLabel("Magic:",JLabel.RIGHT));
        statsPanel.add(magicBar);
        statsPanel.add(new JLabel(""));
        statsPanel.add(new JLabel("EXP:",JLabel.RIGHT));
        statsPanel.add(expBar);
        
        // Build arrow button Area
        arrowPanel.add(new JLabel(""));
        arrowPanel.add(upButton);
        arrowPanel.add(new JLabel(""));
        arrowPanel.add(leftButton);
        arrowPanel.add(downButton);
        arrowPanel.add(rightButton);
        
        // group the HUD display, with health bars, and buttons
        buttonPanel.add(statsPanel, BorderLayout.WEST);
        buttonPanel.add(new JPanel()); // empty panel in between
        buttonPanel.add(arrowPanel, BorderLayout.EAST);
        
        
        // group the map pieces together
        this.add(scrollPanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setPreferredSize(new Dimension(GE.X_DIM, GE.Y_DIM + 30));
        this.addKeyListener(this);  // This class has its own key listeners.
        this.setFocusable(true);    // Allow panel to get focus
        
        // populate the first board
        populateRandomBoard();
        
        // place the player, at the bottom left
        repositionPlayer();
        
        // position camera on player
        repositionScrollBar();

	} // end of GridGUI constructor

	
	public void populateRandomBoard()
	{
        // randomly build a testing grid with rocks and holes
        Random rand = new Random();
        boolean doorPlaced = false;
        int temp;
        
        for (int i=0; i<GE.BROWS; i++)
        {
            for (int j=0; j<GE.BCOLS; j++)
            {
            	// randomly place circles
                temp = rand.nextInt(21); // random 0-20
                
                // randomly scatter "holes"
                if(temp==1 || temp==2)
                {
                	GE.board[i][j] = new GridObject(GE,temp,0);
                }
                // randomly scatter "rocks"
                else if(temp==4 || temp==5)
                {
                	GE.board[i][j] = new GridObject(GE,temp-6,-1);
                }
                // randomly make "traps"
                else if(temp==7)
                {
                	GE.board[i][j] = new GridObject(GE,7,0);
                }
                // randomly place one door per map
                else if(!doorPlaced && (temp==10 || (i==3 && j==3)))
                {
                	GE.board[i][j] = new GridObject(GE,-10,0);
                	doorPlaced = true;
                }
                else
                {
                	temp = rand.nextInt(2) - 2;
                	GE.board[i][j] = new GridObject(GE,temp,0);
                }
                
                gridPanel.add(GE.board[i][j]); // place each location on the JPanel
            }
        }
	}
	
	public void randomizeBoard()
	{
        // randomly build a testing grid with rocks and holes
        Random rand = new Random();
        boolean doorPlaced = false;
        int temp;
        
        for (int i=0; i<GE.BROWS; i++)
        {
            for (int j=0; j<GE.BCOLS; j++)
            {
            	// randomly place circles
                temp = rand.nextInt(21) - 2; // random -2 to 18
                
                // randomly scatter "holes"
                if(temp==1 || temp==2 )
                {
                	GE.board[i][j].resetObject(temp,0);
                }
                // randomly scatter "rocks"
                else if(temp==-1 || temp==-2)
                {
                	GE.board[i][j].resetObject(temp,-1);
                }
                // randomly make "traps"
                else if(temp==7)
                {
                	GE.board[i][j].resetObject(7,0);
                }
                // randomly place one door per map
                else if(!doorPlaced && (temp==10 || (i==3 && j==3)))
                {
                	GE.board[i][j].resetObject(-10,0);
                	doorPlaced = true;
                }
                else
                {
                	// 50/50 dirt or grass
                	temp = rand.nextInt(2) - 2;
                	GE.board[i][j].resetObject(temp,0);
                }
            }
        }
        return;
	}
	
	
	@Override
	public void keyPressed(KeyEvent k)
	{
		int key = k.getKeyCode();
		
		// if they hit "I" go to Inventory etc
		
        if (key == 16 ) // left shift
        	leftShift = true;
        
        if (key == 32 ) // space bar
        	spaceBar = true;

        if (key == 73 ) // 'i'
        {
        	// shortcut to "Inventory Tab"
        	GE.tabs.setSelectedIndex(1);
        }
        else if (key == 67 ) // 'c'
        {
        	// shortcut to "Combat Tab"
        	GE.tabs.setSelectedIndex(2);
        }
        else if (key == 40 || key == 83) // arrow down or 's'
		{
			down = true;
			movePlayer(1,0);
		}
		else if (key == 38 || key == 87) // arrow up or 'w'
		{
			up = true;
			movePlayer(-1,0);
		}
		else if (key == 37 || key == 65) // arrow left or 'a'
		{
			left = true;
			movePlayer(0,-1);
		}
		else if (key == 39 || key == 68) // arrow right or 'd'
		{
			right = true;
			movePlayer(0,1);
		}
        keysPushed++;
	}

	@Override
	public void keyReleased(KeyEvent k)
	{
		int key = k.getKeyCode();
		
        if (key == 16 ) // left shift
        	leftShift = false;
        
        if (key == 32 ) // space bar
        	spaceBar = false;
        
		if (key == 40 || key == 83) // arrow down or 's'
		{
			down = false;
		}
		else if (key == 38 || key == 87) // arrow up or 'w'
		{
			up = false;
		}
		else if (key == 37 || key == 65) // arrow left or 'a'
		{
			left = false;
		}
		else if (key == 39 || key == 68) // arrow right or 'd'
		{
			right = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent k) {
		// a key was typed
    }
	
	// internal function to move the player's position
	// according to the new coordinate increments.
	public void movePlayer(int x, int y)
	{
		// this method checks if a move is valid. (ie, that there is nothing in the way)
		int prow = GE.prow;
		int pcol = GE.pcol;
		boolean player_has_moved = false;
		boolean edgeOfMap = ( 
				( prow==GE.BROWS-1 && x>0 ) 
				|| ( pcol==GE.BCOLS-1 && y>0 ) 
				|| ( prow==0 && x<0 ) 
				|| ( pcol==0 && y<0 ) );
		boolean nearEdgeOfMap = (
				( prow+x==GE.BROWS-1 && x>0 ) 
				|| ( pcol+y==GE.BCOLS-1 && y>0 )
				|| ( prow+x==0 && x<0 )
				|| ( pcol+y==0 && y<0 ) );
		
		// quick exit, if they try to move out of bounds
		if(edgeOfMap || (nearEdgeOfMap && leftShift))
			return;
		
		
		// check if the next spot is empty, <0.
        if(!leftShift && GE.board[prow+x][pcol+y].isEmptySpace())
        {       	
        	// move the player
        	GE.board[prow+x][pcol+y].setEntity(1);
                            
            // replace the empty spot
            GE.board[prow][pcol].setEntity(0);
            
            // adjust
            GE.prow = prow+x; 
            GE.pcol = pcol+y;
            player_has_moved = true;
            steps++;
        }
        // check if the next spot is a rock
        else if(!leftShift && GE.board[prow+x][pcol+y].getEntity()==-1)
        {
        	// is the next location behind the rock, an empty space?
        	if(!nearEdgeOfMap && GE.board[prow+x+x][pcol+y+y].isEmptySpace())
        	{
        		// if so, then move the rock forward one space
        		GE.board[prow+x+x][pcol+y+y].setEntity(-1);
        		
        		// replace the old rock with ground
        		GE.board[prow+x][pcol+y].setEntity(0);
        		
            	// move the player
            	GE.board[prow+x][pcol+y].setEntity(1);
                                
                // replace the empty spot
                GE.board[prow][pcol].setEntity(0);
                
                // adjust
                GE.prow = prow+x; 
                GE.pcol = pcol+y;
                player_has_moved = true;
                steps++;
                rocksPushed++;
        	}
        	// is the next location behind the rock, a hole?
        	else if(!nearEdgeOfMap && GE.board[prow+x+x][pcol+y+y].isHole())
        	{
        		// if so, then move the rock into the hole
        		// leaving just normal ground where the rock was
        		//GE.board[prow+x][pcol+y].setEntity(0);
        		
            	// move the player
            	GE.board[prow+x][pcol+y].setEntity(1);
                                
                // replace the empty spot
                GE.board[prow][pcol].setEntity(0);
                
                // adjust
                GE.prow = prow+x; 
                GE.pcol = pcol+y;
                player_has_moved = true;
                steps++;
                rocksPushed++;
                rocksInHoles++;
        	}
        }
        // check if the player can jump over spot
        else if(leftShift && GE.board[prow+x+x][pcol+y+y].isEmptySpace()
        		&& GE.board[prow+x][pcol+y].getID()<=3
        		&& GE.board[prow+x][pcol+y].getEntity()==0)
        {
        	// if so, we can jump over the hole or ground
        	
        	// move the player two spaces
        	GE.board[prow+x+x][pcol+y+y].setEntity(1);
                            
            // replace the empty spot
            GE.board[prow][pcol].setEntity(0);
            
            // adjust
            GE.prow = prow+x+x; 
            GE.pcol = pcol+y+y;
            player_has_moved = true;
            hops++;
        }
        else // bumped or stuck
        	;
        
        // reposition the "camera" if they moved
        if(player_has_moved)
        {
        	repositionScrollBar();
        }
        
		// did they stumble upon the exit door?
		if(player_has_moved && GE.board[GE.prow][GE.pcol].getID() == -10)
		{
			GE.wins++;
			
			if(GE.wins % 3 == 0) // every three wins, print stats
			{
				GE.printInfo("Highscore!\n\n"+getStatistics()+"\nEnter your name:");
				GE.printInfo("Way to go "+nameField.getText()+"! You're a Laker for a Lifetime!");
			}
			
			int temp = GE.printYesNoQuestion("Congrats! You found the exit!\n\nPlay again?");
			
			if(temp==0)
			{
				// start fresh again!
				randomizeBoard();
				repositionPlayer();
				repositionScrollBar();
			}
			else
				GE.endGame();
		}
		
		// did they stumble upon a monster/enemy?
		if(player_has_moved && checkForEnemies())
		{
			Object[] options = {"Fight!", "Flee!"};
			int answer= GE.printCustomQuestion("What will you do?", options);
			if(answer==0)
			{
				// go to combat tab
				GE.tabs.setSelectedIndex(2);
			}
			else
				GE.tabs.setSelectedIndex(0);
		}
		
        return;
	} // end of movePlayer()	
	
	
	/**
	 * check for enemies
	 * Scans the four surrounding locations, NSWE, for a monster.
	 * @return boolean
	 */
	public boolean checkForEnemies()
	{
		int monsterCount = 0;
		
		if(GE.prow!=GE.BROWS-1 && GE.board[GE.prow+1][GE.pcol].isMonster())
			monsterCount++;
		else if(GE.prow!=0 && GE.board[GE.prow-1][GE.pcol].isMonster())
			monsterCount++;
		else if(GE.pcol!=GE.BCOLS-1 && GE.board[GE.prow][GE.pcol+1].isMonster())
			monsterCount++;
		else if(GE.pcol!=0 && GE.board[GE.prow][GE.pcol-1].isMonster())
			monsterCount++;
		
		encounters += monsterCount;
		
		return (monsterCount>0);
	}
	
	/**
	 * Resets the scroll bars back to the position of the player.
	 * @return void
	 */
	public void repositionScrollBar()
	{
		scrollPanel.getHorizontalScrollBar().setValue((GE.pcol * GE.C_WIDTH) -250);
		scrollPanel.getVerticalScrollBar().setValue((GE.prow * GE.C_HEIGHT) -250);
	
		return;
	}
	
	/**
	 * Resets the player back to the bottom left corner.
	 * @return void
	 */
	public void repositionPlayer()
	{
    	// erase the player
        GE.board[GE.prow][GE.pcol].setEntity(0);
                	
        // place the player, at the bottom middle of the board
        GE.prow = GE.BROWS-1;
        GE.pcol = 0;
        GE.board[GE.prow][GE.pcol].setEntity(1);
        
        return;
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
		rocksPushed = 0; // # of rock pushes
		rocksInHoles = 0; // # of rocks pushed into holes
		keysPushed = 0; // # of keyboard buttons pushed
	}
	
	
	/**
	 * Returns the current statistics for the player
	 * @return String
	 */
	public String getStatistics()
	{
		return "Statistics for "+nameField.getText()+"\n\n"+
				steps+" Steps\n"+
				hops+" Hops\n"+
				encounters+" Battles\n"+
				rocksPushed+" Rocks Pushed\n"+
				rocksInHoles+" Rocks Pushed into Holes\n"+
				keysPushed+" Keys Pushed\n";
	}
	
	/**
	 * actionPerformed
	 * Performs certain actions according to what
	 * button was pressed.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// if the user clicks on a button
		
        if(arg0.getSource() == null)
        {
        	GE.printError("What did you do?!");
        }
        else if(arg0.getSource() == upButton)
        {
        	// increment the players coordinates
        	movePlayer(-1,0);
        }
        else if(arg0.getSource() == downButton)
        {
        	// increment the players coordinates
        	movePlayer(1,0);
        }
        else if(arg0.getSource() == leftButton)
        {
        	// increment the players coordinates
        	movePlayer(0,-1);
        }
        else if(arg0.getSource() == rightButton)
        {
        	// increment the players coordinates
        	movePlayer(0,1);
        }
	}
} // end of GridGUI
