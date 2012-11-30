package rpg;

import javax.swing.ImageIcon;

/**
 * Terrain class
 * 
 * Represents the floor/background like, grass, dirt, tile, etc.
 * @author Austin
 * @version 10/28/2012
 */
public class Terrain extends RPGObject implements Comparable<Terrain>{

	private static final long serialVersionUID = 1L;
	protected boolean exit;
	protected boolean walkable;
	protected boolean encounter;
	
	public Terrain(int id, ImageIcon image, boolean x, boolean w, boolean e)
	{
		this.id = id;
    	this.image = image;
		exit = x;
		walkable = w;
		encounter = e;
	}

	@Override
	public int compareTo(Terrain arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public boolean isExit()
	{
		return exit;
	}
	
	public boolean isWalkable()
	{
		return walkable;
	}
	
	public boolean isRandomEncounter()
	{
		return encounter;
	}
}
