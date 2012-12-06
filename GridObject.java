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
 * @version 10/11/2012
 * 
 */

public class GridObject extends JLayeredPane {

	private static final long serialVersionUID = 1L;
	
	protected Terrain terrain; // background object
	protected RPGObject object; // Object on this locaiton
	protected RPGObject accessory; // highground object
    
    protected ImageIcon terrainImage; // grass, dirt, floor, etc...
    protected ImageIcon objectImage; // player, enemy, rock, etc...
    protected ImageIcon accessoryImage; // tall grass, roofs, extra effects, etc...
    protected ImageIcon fogImage; // partially visible layer for fogID effect
    
    protected JLabel background; // container to hold terrainID image
    protected JLabel foreground; // container to hold objectID image
    protected JLabel highground; // container to hold accessoryID image
    protected JLabel fogLayer; // only holds fogID for blurry vision
    
    // GridObject Constructor
    public GridObject(GameEngine GE, Terrain t, RPGObject o, RPGObject a)
    {    	
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
    	setTerrain(t);
    	setObject(o);
    	setAccessory(a);
    	setFog(null);
    	
    } // end of constructor
    
    /**
     * A simple way to re-construct a GridObject
     * without having to call multiple functions.
     * @param int terrainID
     * @param int objectID
     * @param int accessoryID
     */
    public void resetObject(Terrain t, RPGObject o, RPGObject a)
    {
    	setTerrain(t);
    	setObject(o);
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
    	return (terrain.isWalkable() && object == null);	
    }
    
    /**
     * isHole
     * Returns true, if the location is a hole.
     * @return boolean
     */
    public boolean isHole()
    {
    	if(object!=null && object instanceof NonEntity)
    		if(((NonEntity) object).isHole())
    			return true;
    	return false;
    }
    
    /**
     * Returns true, if the location is pushable, like a rock.
     * @return boolean
     */
    public boolean isPushable()
    {
    	if(object!=null && object instanceof NonEntity)
    		if(((NonEntity)object).isPushable())
    			return true;
    	return false;
    }
    
    /**
     * Returns true, if the location has a consumable object
     * on it. Like food, coins, or points.
     * @return
     */
    public boolean isConsumable()
    {
    	if(object!=null && object instanceof Item)
    			return true;
    	return false;
    }
    
    /**
     * isMonster
     * Returns true if the location is a monster
     * @return boolean
     */
    public boolean isMonster()
    {
    	if(object!=null && object instanceof Entity)
    		if(!((Entity)object).isPlayer)
    			return true;
    	return false;
    }
    
    /**
     * isPlayer
     * Returns true if the location contains the player.
     * @return
     */
    public boolean isPlayer()
    {
    	if(object!=null && object instanceof Entity)
    		if(((Entity)object).isPlayer)
    			return true;
    	return false;
    }
    
    /**
     * isExit
     * Returns true if the location is the finish/exit
     * @return
     */
    public boolean isExit()
    {
    	return (terrain.isExit());
    }
    
    /**
     * isRandomEncounter
     * Returns true if the location has a chance of
     * spawning a random encounter with a monster.
     * (Eg. Tall Grass can produce surprise attacks)
     * @return
     */
    public boolean isRandomEncounter()
    {
    	return (terrain.isRandomEncounter());
    }
    
    /**
     * isTrap
     * Returns true if the location contains a
     * trap that performs a specific behavior when
     * stepped on. (Eg. Spikes hurt player, switch
     * unlocks door, warp teleports player)
     * @return
     */
    public boolean isTrap()
    {
    	if(object!=null && object instanceof NonEntity)
    		if(((NonEntity)object).isTrap())
    			return true;
    	
    	if(accessory!=null && accessory instanceof NonEntity)
    		if(((NonEntity)accessory).isTrap())
    			return true;
    	
    	return false;
    }
    
    /**
     * isSign
     * Returns true if the location is a sign post.
     * @return
     */
    public boolean isSignPost()
    {
    	if(object!=null && object instanceof NonEntity)
    		if(((NonEntity)object).isSignPost())
    			return true;
    	return false;
    }
    
    /**
     * Sets the id of the objectID layer and adjusts
     * the image accordingly
     * @param int p
     */
    public void setObject(RPGObject o)
    {    	
    	object = o;
    	
    	// set the foreground image
    	
    	// grab the image from the entity
    	if(object == null)
    		objectImage=null;
    	else
    		objectImage = object.getImage();
    	
        foreground.setIcon(objectImage);
    }
    
    /**
     * Sets the id of the terrainID layer and adjusts
     * the image accordingly.
     * @param int i
     */
    public void setTerrain(Terrain i)
    {
    	// set the background image
    	terrain = i;
    	
    	if(terrain == null)
    		terrainImage=null;
    	else
    		terrainImage = terrain.getImage();
    	
    	background.setIcon(terrainImage);
    }
    
    /**
     * Sets the top layer's image and stores the new id for
     * the accessoryID layer.
     * @param int a
     */
    public void setAccessory(RPGObject a)
    {    	
    	// set the highground image
    	accessory = a;
    	
    	if(accessory == null)
    		accessoryImage = null;
    	else
    		accessoryImage = accessory.getImage();
        
        highground.setIcon(accessoryImage);
    }
    
    /**
     * Determines what fogID piece is displayed on the fourth layer
     * @param f
     */
    public void setFog(ImageIcon fog)
    {    	
    	fogImage = fog;
    	fogLayer.setIcon(fog);
    }
    
    /**
     * Returns the RPGObject
     * that is on middle layer of the GridObject
     * @return int objectID
     */
    public RPGObject getObject()
    {
        return object;
    }
    
    /**
     * Returns the id of the GridOjbect
     * that is the lowest layer of the GridObject
     * @return int terrainID
     */
    public Terrain getTerrain()
    {
        return terrain;
    }
    
    /**
     * Returns the id of the AccessoryLayer
     * that is the highest layer of the GridObject
     * @return int accessoryID
     */
    public RPGObject getAccessory()
    {
    	return accessory;
    }
    
    /**
     * Returns the image of the fogID layer
     */
    public ImageIcon getFog()
    {
    	return fogImage;
    }
    
} // end of GridObject