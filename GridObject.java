package rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/**
 * GridObject class
 * 
 * Displays an image to represent one space on the map
 * @author Austin Delamar
 * @version 9/20/2012
 * 
 */
@SuppressWarnings("serial")
public class GridObject extends JLayeredPane {
	
	private GridEngine GE; // link back to Engine
    private Color color; // visual use for prototypes, before images
    private int id; // identifies what type of cell the image displays
    private int entity; // is there an object here? 0=no
    private ImageIcon image; // visual image, for improved game looks
    
    protected JLabel background;
    protected JLabel foreground;
    
    // GridObject Constructor
    public GridObject(GridEngine tempEngine, int id, int p)
    {
    	// link to back Engine
    	GE = tempEngine;
    	
    	// gui attributes
    	
    	background = new JLabel();
    	background.setIcon(image);
    	
    	this.add(background, JLayeredPane.DEFAULT_LAYER);
    	this.setLayout(new BorderLayout());
    	this.setSize(GE.C_WIDTH, GE.C_HEIGHT);
        //this.setBorder(BorderFactory.createMatteBorder(0,0,0,0,Color.WHITE));
    	//this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    	//this.setIconTextGap(0);
    	
        // set attributes
        setID(id);
        setEntity(p);
    }
    
    public void resetObject(int id, int p)
    {
    	this.id = id;
    	entity = p;
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
    
    public boolean isHole()
    {
    	return (id==1 || id==2 || id==3);
    }
    
    public boolean isWall()
    {
    	return (id==11 || id==10 || id==9 || id==8 || id==7 || id==6);
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
        // change color according to player's position
        if(p==0)
        {
        	// change the terrain color/image according to id's
        	switch(id)
        	{
        		case -10: color=GE.doorColor; image=GE.GlowingGem; break;
        		case -2:  color=GE.groundColor; image=GE.Grass; break;
        		case -1:  color=GE.groundColor; image=GE.Dirt; break;
        		case 0: this.setVisible(false); break;
        		case 1: color=GE.holeColor; image=GE.DirtHole; break;
        		case 2: color=GE.holeColor; image=GE.GrassHole; break;
        		
        		case 4: color=GE.groundColor; image=GE.Dirt; break; // rocks are an entity
        		case 5: color=GE.groundColor; image=GE.Grass; break; // they appear after you setEntity(-1)
        		
        		default: GE.errorPrint("setEntity(int id)\nerror on id="+id); break;
        	}
        }
        else if(p==1)
        {
        	// player 1 is on this location
        	// depending what the player is on, it can be surrounded by grass, dirt, or other types
        	switch(id)
        	{
        		case -2: color=GE.playerColor; image=GE.PlayerGrassFront; break;
        		case -1: color=GE.playerColor; image=GE.PlayerFront; break;
        	}
        }
        else if(p==-1)
        {
        	// move-able rock is on this location
        	// depending where the rock is, it can be surrounded by grass, dirt, or other types
        	switch(id)
        	{
    			case -2:  color=GE.rockColor; image=GE.GrassRock; break;
    			case -1:  color=GE.rockColor; image=GE.DirtRock; break;
    			
    			default: GE.errorPrint("setEntity(int id)\nerror on id="+id); break;
        	}
        }
        
        // reset (re-paint)
    	entity = p;
    	repaint();
        //this.setIcon(image);
    	this.setVisible(true);
    }

    /**
     * Super Paint Component for GridObject
     * @param Graphics g
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        g.drawImage(image, 0, 0, null);
    }
    
    public Color getColor()
    {
        return color;
    }
    
    public void setColor(Color c)
    {
        color = c;
    }
    
    public int getID()
    {
        return id;
    }
    
    public void setID(int a)
    {
        id = a;
        setEntity(entity);
    }
    
    public int getEntity()
    {
        return entity;
    }
    
} // end of GridObject
