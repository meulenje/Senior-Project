package rpg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/**
 * GridObject class
 * 
 * Displays an image to represent one space on the map
 * @author Austin Delamar
 * @version 10/1/2012
 * 
 */
@SuppressWarnings("serial")
public class GridObject extends JLayeredPane {
	
	private GameEngine GE; // link back to Engine
	
    private int id; // identifies what type of cell the image displays
    private int entity; // is there an object here? 0=no
    private int accessory; // identifies what extra layer is on top
    private int fog;
    protected ImageIcon terrainImage; // grass, dirt, floor, etc...
    protected ImageIcon entityImage; // player, enemy, rock, etc...
    protected ImageIcon accessoryImage; // tall grass, roofs, extra effects, etc...
    protected ImageIcon fogImage; // partially visible layer for fog effect
    protected JLabel background; // container to hold terrain image
    protected JLabel foreground; // container to hold entity image
    protected JLabel highground; // container to hold accessory image
    protected JLabel fogLayer; // only holds fog for blurry vision
    
    // GridObject Constructor
    public GridObject(GameEngine tempEngine, int i, int e, int a)
    {
    	// link to back Engine
    	GE = tempEngine;
    	
    	// Lay a background, foreground, and highground JLabel
    	background = new JLabel();
    	background.setSize(GE.C_WIDTH, GE.C_HEIGHT);
    	background.setLocation(0,0);
    	foreground = new JLabel();
    	foreground.setSize(GE.C_WIDTH, GE.C_HEIGHT);
    	foreground.setLocation(0,0);
    	highground = new JLabel();
    	highground.setSize(GE.C_WIDTH, GE.C_HEIGHT);
    	highground.setLocation(0,0);
    	fogLayer = new JLabel();
    	fogLayer.setSize(GE.C_WIDTH, GE.C_HEIGHT);
    	fogLayer.setLocation(0,0);
    	
    	// set components
    	this.add(background, JLayeredPane.DEFAULT_LAYER); // 0
    	this.add(foreground, JLayeredPane.PALETTE_LAYER); // 100 (above 0)
    	this.add(highground, JLayeredPane.MODAL_LAYER);   // 200 (above 100)
    	this.add(fogLayer, JLayeredPane.POPUP_LAYER); // 300 (above 200)
    	this.setLayout(new BorderLayout());
    	this.setOpaque(false); // non-transparent
    	this.setSize(new Dimension(GE.C_WIDTH, GE.C_HEIGHT));
    	
    	// set variables and images
    	setID(i);
    	setEntity(e);
    	setAccessory(a);
    	setFog(GE.EmptyID);
    	
    } // end of constructor
    
    /**
     * A simple way to re-construct a GridObject
     * without having to call multiple functions.
     * @param id
     * @param p
     */
    public void resetObject(int i, int p, int a)
    {
    	setID(i);
    	setEntity(p);
    	setAccessory(a);
    }
    
    /**
     * isEmptySpace
     * Returns true, if the location is walkable, and
     * there is no Entities on the location.
     * @return boolean
     */
    public boolean isEmptySpace()
    {
    	return (id<0 && entity==GE.EmptyID);	
    }
    
    /**
     * isHole
     * Returns true, if the location is a hole. This
     * could be from, grass, dirt, or water.
     * @return boolean
     */
    public boolean isHole()
    {
    	return (entity==GE.HoleID);
    }
    
    /**
     * isRock
     * Returns true, if the location is a rock.
     * @return boolean
     */
    public boolean isRock()
    {
    	return (entity==GE.RockID);
    }
    
    /**
     * isMonster
     * Returns true if the location is a monster
     * @return boolean
     */
    public boolean isMonster()
    {
    	return (entity==GE.LavaMonsterID || entity==GE.PirateID);
    }
    
    /**
     * isExit
     * Returns true if the location is the finish/exit
     * @return
     */
    public boolean isExit()
    {
    	return (accessory==2);
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
    	// set the foreground image
    	switch(p) // entity
    	{
    		case 0: entityImage=GE.Empty; break;
    		case 1: entityImage=GE.Player; break;
    		case 2: entityImage=GE.Rock; break;
    		case 3: entityImage=GE.Hole; break;
    		case 4: entityImage=GE.LavaMonster; break;
    		case 5: entityImage=GE.Pirate; break;
    		
    		default: GE.printError("Error!\nNo image found for entity id="+p); break;
    	}
        
    	entity = p;
        foreground.setIcon(entityImage);
    }
    
    /**
     * Sets the id of the terrain layer and adjusts
     * the image accordingly.
     * @param int a
     */
    public void setID(int a)
    {
    	// set the background image
    	switch(a) // id
    	{
    		case -9: terrainImage=GE.XSpace; break;
    		case -8: terrainImage=GE.XSpace; break;
    		case -3: terrainImage=GE.Water; break;
    		case -2: terrainImage=GE.Grass; break;
    		case -1: terrainImage=GE.Dirt; break;
    		case 0: this.setVisible(false); break;
    		
    		default: GE.printError("Error!\nNo image found for terrain id="+a); break;
    	}
    	
    	id = a;
    	background.setIcon(terrainImage);
    }
    
    /**
     * Sets the top layer's image and stores the new id for
     * the accessory layer.
     * @param int a
     */
    public void setAccessory(int a)
    {    	
    	// set the highground image
    	switch(a) // id
    	{ 
    		case 0: accessoryImage=GE.Empty; break;
    		case 1: accessoryImage=GE.TallGrass; break;
    		case 2: accessoryImage=GE.Hideout; break;
    		
    		default: GE.printError("Error!\nNo image found for accessory id="+a); break;
    	}
        
    	accessory = a;
        highground.setIcon(accessoryImage);
    }
    
    public void setFog(int f)
    {
    	if(f==GE.FogCenterID)
    	{
    		fogImage = GE.FogCenter;
    	}
    	else if(f==GE.FogTopID)
    	{
    		fogImage = GE.FogTop;
    	}
    	else if(f==GE.FogBottomID)
    	{
    		fogImage = GE.FogBottom;
    	}
    	else if(f==GE.FogLeftID)
    	{
    		fogImage = GE.FogLeft;
    	}
    	else if(f==GE.FogRightID)
    	{
    		fogImage = GE.FogRight;
    	}
    	else
    		fogImage = GE.Empty;
    	
    	fog = f;
    	fogLayer.setIcon(fogImage);
    }
    
    /**
     * Returns the id of the Entity
     * that is on middle layer of the GridObject
     * @return int entity
     */
    public int getEntity()
    {
        return entity;
    }
    
    /**
     * Returns the id of the GridOjbect
     * that is the lowest layer of the GridObject
     * @return int id
     */
    public int getID()
    {
        return id;
    }
    
    /**
     * Returns the id of the AccessoryLayer
     * that is the highest layer of the GridObject
     * @return int accessory
     */
    public int getAccessory()
    {
    	return accessory;
    }
    
    /**
     * Returns the id of the fog layer
     */
    public int getFog()
    {
    	return fog;
    }
    
} // end of GridObject