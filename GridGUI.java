package rpg;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JPanel;

/**
 * GridGUI class
 * 
 * Used for showing the game board and using
 * the arrow keys to move the player around
 * @author Austin Delamar
 * @version 9/20/2012
 * 
 */
@SuppressWarnings("serial")
public class GridGUI extends JPanel implements KeyListener {

	private GridEngine GE; // link back to Engine
	private boolean up = false; // 38
	private boolean down = false; // 40
	private boolean left = false; // 37
	private boolean right = false; // 39
	private boolean leftShift = false; // 16
	private boolean spaceBar = false; // 32
	
	public GridGUI(GridEngine tempEngine)
	{
		// link back to JFramel
		GE = tempEngine;
		
        this.addKeyListener(this);  // This class has its own key listeners.
        this.setFocusable(true);    // Allow panel to get focus
		
		// Build the JPanel
        GridLayout grid = new GridLayout(GE.BROWS, GE.BCOLS, 0, 0);
        this.setLayout(grid);
        this.setPreferredSize(new Dimension( GE.G_X_DIM , GE.G_Y_DIM ));
        this.setBackground(GE.holeColor);
        
        populateRandomBoard();

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
                
                this.add(GE.board[i][j]); // place each location on the JPanel
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
        
        // replace the player, at the bottom left
        GE.prow = GE.BROWS-1;
        GE.pcol = 0;
        GE.board[GE.prow][GE.pcol].setEntity(1);
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
        }
        else // bumped or stuck
        	;
        
		// did they stumble upon the exit door?
		if(GE.board[GE.prow][GE.pcol].getID() == -10)
		{
			GE.wins++;
			
			if(GE.wins == 3)
			{
				String temp = GE.throwPrint("Highscore! \n\nEnter your name:");
				GE.infoPrint("Way to go "+temp+"! You're a Laker for a Lifetime!");
			}
			
			int temp = GE.yesNoPrint("Congrats! You found the exit!"+"\n\nPlay again?");
			
			if(temp==0)
				randomizeBoard();
			else
				GE.endGame();
		}
        return;
	} // end of movePlayer()	
	
} // end of GridGUI
