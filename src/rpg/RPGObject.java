package rpg;

import javax.swing.ImageIcon;

/**
 * RPGObject class
 * 
 * An abstract class that is meant to be extended.
 * 
 * Here is a list of Extending Classes:
 * - Terrain
 * - Entity
 * - Item
 * - NonEntity
 * 
 * @author Austin
 *
 */
public abstract class RPGObject {

	protected int id;
	protected ImageIcon image;
	
	public String toString()
	{
		return "#"+id;
	}
	
	public int getID()
	{
		return id;
	}
	
	public ImageIcon getImage()
	{
		return image;
	}
}
