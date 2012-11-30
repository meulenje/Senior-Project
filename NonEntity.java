package rpg;

import javax.swing.ImageIcon;

/**
 * NonEntity class
 * 
 * Represents objects like, rocks, trees, walls, doors, etc.
 * @author Austin
 * @version 10/28/2012
 */
public class NonEntity extends RPGObject implements Comparable<NonEntity>{

	private static final long serialVersionUID = 1L;
	protected boolean pushable;
	protected boolean trap;
	protected boolean hole;
	protected boolean sign;
	
	public NonEntity(int id, ImageIcon image, boolean p, boolean t, boolean h, boolean s)
	{
		this.id = id;
    	this.image = image;
		pushable = p;
		trap = t;
		hole = h;
		sign = s;
	}

	@Override
	public int compareTo(NonEntity o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public boolean isPushable()
	{
		return pushable;
	}
	
	public boolean isTrap()
	{
		return trap;
	}
	
	public boolean isHole()
	{
		return hole;
	}
	
	public boolean isSignPost()
	{
		return sign;
	}
}
