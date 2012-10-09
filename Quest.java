package rpg;

import javax.swing.ImageIcon;

/**
 * Quest class
 * 
 * A simple object to hold quest like informatio together
 * 
 * @author Austin
 * @version 10/8/2012
 */
public class Quest {

	protected ImageIcon image;
	protected String title;
	protected String message;
	
	public Quest(ImageIcon i, String t, String m)
	{
		image = i;
		title = t;
		message = m;
	}
	
	
}
