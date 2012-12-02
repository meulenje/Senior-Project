package rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * GridGUI class
 * 
 * Used for showing the game board and using
 * the arrow keys to move the player around.
 * @author Austin Delamar
 * @version 10/11/2012
 * 
 */

public class GridGUI extends JPanel implements KeyListener, ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private GameEngine GE; // link back to Engine

	// gui parts
	private JPanel gridPanel;
    private JPanel buttonPanel;
    private JScrollPane scrollPanel;
    private JButton resetButton;
    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton mapButton;
    private JButton inventoryButton;
    private JButton questsButton;
    private JButton statsButton;
	
	public GridGUI(GameEngine tempEngine)
	{
		// link back to JFramel
		GE = tempEngine;
		
		// Build the Grid to hold the Objects
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(GE.BROWS, GE.BCOLS, 0, 0));
        gridPanel.setPreferredSize(new Dimension( GE.G_X_DIM, GE.G_Y_DIM));
        gridPanel.setBackground(Color.BLACK);
        
        // Build the Glue Panel that fills in empty space
        JPanel extraPanel = new JPanel();
        extraPanel.setBorder(BorderFactory.createEmptyBorder());
        extraPanel.setBackground(Color.BLACK);
        extraPanel.add(Box.createVerticalGlue());
        extraPanel.add(gridPanel);
        extraPanel.add(Box.createHorizontalGlue());
        
        // Build the ScrollPanel to allow scrolling
        scrollPanel = new JScrollPane(extraPanel);
        scrollPanel.setViewportView(extraPanel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
       	scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setPreferredSize(new Dimension( GE.Window_Width, GE.Window_Height));
        scrollPanel.addMouseListener(this); // listen for mouse clicks
        
        // Build the button panel
        buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setPreferredSize(new Dimension( GE.Window_Width - 10, 50 ));       

        //JButtons and listeners
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
               
        // Build menu bar area
        JPanel menuPanel = new JPanel(new GridLayout(0,4,0,0));
        menuPanel.setPreferredSize(new Dimension(400,50));
        
        mapButton = new JButton("Map");
        mapButton.addActionListener(this);
        // mapButton.setIcon(GE.MapIcon);
        menuPanel.add(mapButton);
        inventoryButton = new JButton("Items");
        inventoryButton.addActionListener(this);
        inventoryButton.setIcon(GE.InventoryIcon);
        menuPanel.add(inventoryButton);
        statsButton = new JButton("Stats");
        statsButton.addActionListener(this);
        statsButton.setIcon(GE.ListIcon);
        menuPanel.add(statsButton);
        questsButton = new JButton("Quests");
        questsButton.addActionListener(this);
        questsButton.setIcon(GE.MailIcon);
        menuPanel.add(questsButton);
        
        // Build arrow button Area
        JPanel arrowPanel = new JPanel(new GridLayout(2,3,0,0));
        arrowPanel.setPreferredSize(new Dimension(200,50));
        arrowPanel.add(new JLabel(""));
        arrowPanel.add(upButton);
        arrowPanel.add(new JLabel(""));
        arrowPanel.add(leftButton);
        arrowPanel.add(downButton);
        arrowPanel.add(rightButton);
        
        // group the HUD display, with health bars, and buttons
        buttonPanel.add(menuPanel, BorderLayout.WEST);
        buttonPanel.add(new JPanel(), BorderLayout.CENTER); // empty panel in between
        buttonPanel.add(arrowPanel, BorderLayout.EAST);
        
        
        // group the map pieces together
        this.add(scrollPanel, BorderLayout.CENTER);
        //this.add(buttonPanel, BorderLayout.SOUTH);
        this.addKeyListener(this);  // This class has its own key listeners.
        this.setFocusable(true);    // Allow panel to get focus

	} // end of GridGUI constructor

	
	/**
	 * Places each GridObject into the GridGUI
	 * 
	 */
	public void addAllGridObjects()
	{
        for (int i=0; i<GE.BROWS; i++)
        {
            for (int j=0; j<GE.BCOLS; j++)
            {
                gridPanel.add(GE.board[i][j]); // place each location on the JPanel
            }
        }
	}
	
	/**
	 * Places one GridOjbect into the GridGUI
	 */
	public void addGridObject(int x, int y)
	{
        gridPanel.add(GE.board[x][y]); // place object at its location given
	}
	
	/**
	 * Removes all component references. Effectively removing
	 * all grid objects from the GridGUI view, allowing for the
	 * recreation of a new set of GridObjects.
	 */
	public void removeAllGridObjects()
	{
		gridPanel.removeAll();
	}
	
	/**
	 * Resets the scroll bars back to the position of the player.
	 * @return void
	 */
	public void repositionScrollBarsToPlayer()
	{
		scrollPanel.getHorizontalScrollBar().setValue((GE.pcol * GE.C_WIDTH) -250);
		scrollPanel.getVerticalScrollBar().setValue((GE.prow * GE.C_HEIGHT) -250);
	}
	
	/**
	 * Resets the scroll bars to the new (x,y) position IN PIXELS,
	 * not in (rows,columns).
	 * @param x
	 * @param y
	 */
	public void repositionScrollBars(int x, int y)
	{
		scrollPanel.getHorizontalScrollBar().setValue(y);
		scrollPanel.getVerticalScrollBar().setValue(x);
	}
	
	@Override
	public void keyPressed(final KeyEvent k)
	{
		int key = k.getKeyCode();
		
		// if they hit "I" go to Inventory etc
		
        if (key == 16 ) // left shift
        	GE.leftShift = true;
        
        if (key == 32 ) // space bar
        	GE.spaceBar = true;

        if (key == 73 ) // 'i'
        {
        	// shortcut to "Inventory Tab"
        	GE.viewInventoryPanel();
        }
        else if (key == 67 ) // 'c'
        {
        	
        }
        else if (key == 81) // 'q'
        {
        	// shortcut to "Quest Tab"
        	GE.viewQuestPanel();
        }
        else if (key == 83) // 's'
        {
        	// shortcut to stats tab
        	GE.viewStatsPanel();
        }
        else if (key == 40) // arrow down
		{
        	GE.down = true;
        	GE.movePlayer(1,0);
		}
		else if (key == 38) // arrow up
		{
			GE.up = true;
			GE.movePlayer(-1,0);
		}
		else if (key == 37) // arrow left
		{
			GE.left = true;
			GE.movePlayer(0,-1);
		}
		else if (key == 39) // arrow right
		{
			GE.right = true;
			GE.movePlayer(0,1);
		}
		else if(key == 27) // ESC to open in-game menu
		{
			// in game menu
			GE.viewSubMenu();			
		}
        
        GE.keysPushed++;
	}

	@Override
	public void keyReleased(KeyEvent k)
	{
		int key = k.getKeyCode();
		
        if (key == 16 ) // left shift
        	GE.leftShift = false;
        
        if (key == 32 ) // space bar
        	GE.spaceBar = false;
        
		if (key == 40 || key == 83) // arrow down or 's'
		{
			GE.down = false;
		}
		else if (key == 38 || key == 87) // arrow up or 'w'
		{
			GE.up = false;
		}
		else if (key == 37 || key == 65) // arrow left or 'a'
		{
			GE.left = false;
		}
		else if (key == 39 || key == 68) // arrow right or 'd'
		{
			GE.right = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent k) {
		// a key was typed
    }
	
	/**
	 * actionPerformed
	 * Performs certain actions according to what
	 * button was pressed.
	 */
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		// if the user clicks on a button
		
        if(ae.getSource() == null)
        {
        	GE.printError("What did you do?!");
        }
        else if(ae.getSource() == mapButton)
        {
        	// return to map
        	GE.viewMapPanel();
        }
        else if(ae.getSource() == inventoryButton)
        {
        	// jump to inventory panel
        	GE.viewInventoryPanel();
        }
        else if(ae.getSource() == questsButton)
        {
        	// jump to quests panel
        	GE.viewQuestPanel();
        }
        else if(ae.getSource() == statsButton)
        {
        	// jump to stats panel
        	GE.viewStatsPanel();
        }
        
        else if(ae.getSource() == upButton)
        {
        	// increment the players coordinates
        	GE.movePlayer(-1,0);
        }
        else if(ae.getSource() == downButton)
        {
        	// increment the players coordinates
        	GE.movePlayer(1,0);
        }
        else if(ae.getSource() == leftButton)
        {
        	// increment the players coordinates
        	GE.movePlayer(0,-1);
        }
        else if(ae.getSource() == rightButton)
        {
        	// increment the players coordinates
        	GE.movePlayer(0,1);
        }
	}


	@Override
	public void mouseClicked(MouseEvent m) {
		// view map if they click on it.
		if(GE.viewingMap != true)
			GE.viewMapPanel();		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		//  Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		//  Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		//  Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		//  Auto-generated method stub
		
	}
} // end of GridGUI