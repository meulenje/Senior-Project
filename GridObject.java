package rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/**
 * GridObject class
 * 
 * Displays an image to represent one space on the map
 * @author Austin Delamar
 * @version 9/25/2012
 * 
 */
@SuppressWarnings("serial")
public class GridObject extends JLayeredPane {
	
	private GameEngine GE; // link back to Engine
    private int id; // identifies what type of cell the image displays
    private int entity; // is there an object here? 0=no
    protected boolean hasMoved = false;
    protected ImageIcon bgimage; // grass, dirt, floor, etc...
    protected ImageIcon fgimage; // player, enemy, rock, etc...
    protected JLabel background; // container to hold image
    protected JLabel foreground; // container to hold image
    
    // GridObject Constructor
    public GridObject(GameEngine tempEngine, int i, int e)
    {
    	// link to back Engine
    	GE = tempEngine;
    	
    	// Lay a background and foreground JLabel
    	background = new JLabel();
    	background.setSize(GE.C_WIDTH, GE.C_HEIGHT);
    	background.setLocation(0,0);
    	foreground = new JLabel();
    	foreground.setSize(GE.C_WIDTH, GE.C_HEIGHT);
    	foreground.setLocation(0,0);
    	
    	// set components
    	this.add(background, JLayeredPane.DEFAULT_LAYER); // 0
    	this.add(foreground, JLayeredPane.PALETTE_LAYER); // 1 (on top)
    	this.setOpaque(false); // non-transparent
    	this.setLayout(new BorderLayout());
    	this.setBackground(Color.BLACK);
    	this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    	this.setSize(new Dimension(GE.C_WIDTH, GE.C_HEIGHT));
    	
    	// set variables and images
    	setID(i);
    	setEntity(e);
    	
    } // end of constructor
    
    /**
     * A simple way to re-construct a GridObject
     * without having to call two functions.
     * @param id
     * @param p
     */
    public void resetObject(int i, int p)
    {
    	setID(i);
    	setEntity(p);
    }
    
    /**
     * isEmptySpace
     * Returns true, if the location is walkable, and
     * there is no Entities on the location.
     * @return boolean
     */
    public boolean isEmptySpace()
    {
    	return (id<0 && entity==0);	
    }
    
    /**
     * isHole
     * Returns true, if the location is a hole. This
     * could be from, grass, dirt, or water.
     * @return
     */
    public boolean isHole()
    {
    	return (id==1 || id==2 || id==3);
    }
    
    /**
     * isMonster
     * Returns true if the location is a monster
     * @return boolean
     */
    public boolean isMonster()
    {
    	return (id==7);
    }
    
    
    /**
     * Places/Removes a player from the grid object by
     * using the player id as a placeholder for its image.
     * If 0 (zero) is passed, it effectively removes the
     * image of the player, and defaults back to normal.
     * If -1 is passed, it identifies a special object, like
     * a rock, which is move-able over terrain
     * @param int p
     */
    public void setEntity(int p)
    {    	
        if(p==0)
        {        	
        	// set foreground to null
        	fgimage = GE.Empty;
        }
        else if(p==1)
        {
        	// player 1 is on this location
        	fgimage = GE.Player;
        }
        else if(p==-1)
        {
        	// rock is on this location
        	fgimage = GE.Rock;
        }
        
    	entity = p;
        foreground.setIcon(fgimage);
    }
    
    /**
     * Returns the id of the GridObject
     * @return int id
     */
    public int getID()
    {
        return id;
    }
    
    /**
     * Sets the id of the GridObject
     * @param int a
     */
    public void setID(int a)
    {
    	// set the background image
    	switch(a) // id
    	{
    		case -10: bgimage=GE.GlowingGem; break;
    		case -9: bgimage=GE.Wall; break;
    		case -8: bgimage=GE.Wall; break;
    		case -2: bgimage=GE.Grass; break;
    		case -1: bgimage=GE.Dirt; break;
    		case 0: this.setVisible(false); break;
    		case 1: bgimage=GE.DirtHole; break;
    		case 2: bgimage=GE.GrassHole; break;
    		// deep water
    		case 4: bgimage=GE.Dirt; break; // Dirt with Rock
    		case 5: bgimage=GE.Grass; break; // Grass with Rock
    		case 7: bgimage=GE.LavaMonster; break;
    		
    		default: GE.printError("Error!\nNo image found for id="+a); break;
    	}
    	
    	id = a;
    	background.setIcon(bgimage);
    }
    
    /**
     * Returns the id of the Entity
     * that is on top of the GridObject
     * @return int entity
     */
    public int getEntity()
    {
        return entity;
    }
    
} // end of GridObject
