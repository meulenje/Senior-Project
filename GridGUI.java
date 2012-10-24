package rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * GridGUI class
 * 
 * Used for showing the game board and using
 * the arrow keys to move the player around.
 * @author Austin Delamar
 * @version 10/11/2012
 * 
 */
@SuppressWarnings("serial")
public class GridGUI extends JPanel implements KeyListener, ActionListener, ClockListener {

	private GameEngine GE; // link back to Engine

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
    private JButton inventoryButton;
    private JButton questsButton;
	
	public GridGUI(GameEngine tempEngine)
	{
		// link back to JFramel
		GE = tempEngine;
		
		// Build the Grid to hold the Objects
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(GE.BROWS, GE.BCOLS, 0, 0));
        gridPanel.setPreferredSize(new Dimension( GE.G_X_DIM , GE.G_Y_DIM ));
        gridPanel.setBackground(Color.BLACK);
        
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
        buttonPanel.setPreferredSize(new Dimension( GE.X_DIM - 10, 50 ));
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
        healthBar.setBackground(Color.WHITE);
        healthBar.setBorderPainted(true);
        healthBar.setValue(50);
        
        // magic bar for the player
        magicBar = new JProgressBar(0);
        magicBar.setForeground(Color.BLUE);
        magicBar.setBackground(Color.WHITE);
        magicBar.setBorderPainted(true);
        magicBar.setValue(90);
        
        // experience bar for the player
        expBar = new JProgressBar(0);
        expBar.setForeground(Color.GREEN);
        expBar.setBackground(Color.WHITE);
        expBar.setBorderPainted(true);
        expBar.setValue(50);
        
        // Build HP/Mag/Exp Bar Area
        statsPanel.add(nameField);
        JLabel hp = new JLabel("HP:", JLabel.RIGHT);
        hp.setForeground(Color.RED);
        statsPanel.add(hp);
        statsPanel.add(healthBar);
        statsPanel.add(new JLabel(""));
        JLabel mag = new JLabel("Magic:", JLabel.RIGHT);
        mag.setForeground(Color.BLUE);
        statsPanel.add(mag);
        statsPanel.add(magicBar);
        statsPanel.add(new JLabel(""));
        JLabel exp = new JLabel("EXP:", JLabel.RIGHT);
        exp.setForeground(Color.GREEN);
        statsPanel.add(exp);
        statsPanel.add(expBar);
        
        // Build bag and journal shortcut buttons (inventory and quests)
        JPanel bagPanel = new JPanel();
        bagPanel.setLayout(new GridLayout(2,3));
        
        bagPanel.add(new JLabel(""));
        inventoryButton = new JButton();
        inventoryButton.addActionListener(this);
        inventoryButton.setIcon(GE.InventoryIcon);
        bagPanel.add(inventoryButton);
        bagPanel.add(new JLabel(""));
        bagPanel.add(new JLabel(""));
        questsButton = new JButton();
        questsButton.addActionListener(this);
        questsButton.setIcon(GE.ListIcon);
        bagPanel.add(questsButton);
        bagPanel.add(new JLabel(""));
        
        // Build arrow button Area
        arrowPanel.add(new JLabel(""));
        arrowPanel.add(upButton);
        arrowPanel.add(new JLabel(""));
        arrowPanel.add(leftButton);
        arrowPanel.add(downButton);
        arrowPanel.add(rightButton);
        
        // group the HUD display, with health bars, and buttons
        buttonPanel.add(statsPanel, BorderLayout.WEST);
        buttonPanel.add(bagPanel); // empty panel in between
        buttonPanel.add(arrowPanel, BorderLayout.EAST);
        
        
        // group the map pieces together
        this.add(scrollPanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setPreferredSize(new Dimension(GE.X_DIM, GE.Y_DIM + 30));
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
	public void keyPressed(KeyEvent k)
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
        	// shortcut to "Combat Tab"
        	GE.viewCombatPanel();
        }
        else if (key == 81) // 'q'
        {
        	// shortcut to "Quest Tab"
        	GE.viewQuestPanel();
        }
        else if (key == 40 || key == 83) // arrow down or 's'
		{
        	GE.down = true;
        	GE.movePlayer(1,0);
		}
		else if (key == 38 || key == 87) // arrow up or 'w'
		{
			GE.up = true;
			GE.movePlayer(-1,0);
		}
		else if (key == 37 || key == 65) // arrow left or 'a'
		{
			GE.left = true;
			GE.movePlayer(0,-1);
		}
		else if (key == 39 || key == 68) // arrow right or 'd'
		{
			GE.right = true;
			GE.movePlayer(0,1);
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
	public boolean event(int tick) 
	{
		// This function will be called, every clock tick.
		
		// Scan for monsters, if you find one, make him move.
		if(GE.numOfMonsters > 0 && GE.klok.currentTime != 0
				&& GE.klok.currentTime % GE.monsterGridSpeed == 0)
		{
	        for (int i=0; i<GE.BROWS; i++)
	        {
	            for (int j=0; j<GE.BCOLS; j++)
	            {
	            	// find a monster
	            	if(GE.klok.currentTime>0 && GE.board[i][j].isMonster())
	            	{
	            		// move the monster left, if possible
	            		if(j!=0 && GE.board[i][j-1].isEmptySpace())
	            		{
	            			// move left
	            			GE.board[i][j-1].setEntity(GE.board[i][j].getEntity());
	            			GE.board[i][j].setEntity(GE.EmptyID);
	            		}
	            		else if(i!=GE.BROWS-1 && GE.board[i+1][j].isEmptySpace())
	            		{
	            			// move down
	            			GE.board[i+1][j].setEntity(GE.board[i][j].getEntity());
	            			GE.board[i][j].setEntity(GE.EmptyID); 
	            		}
	            	}
	            }
	        }
	        // check around for enemies again
	        GE.checkForEnemies();
	        
	        return true;
		}
		return false;
	}
} // end of GridGUI