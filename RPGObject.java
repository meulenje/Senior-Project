package rpg;

import java.io.Serializable;

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
public abstract class RPGObject implements Serializable {

	private static final long serialVersionUID = 1L;
	protected int id;
	protected ImageIcon image;
	protected String triggertype;
	protected boolean onTopTrigger;
	protected int triggerState;
	
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
