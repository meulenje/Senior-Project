package rpg;

import java.net.URL;

import javax.swing.ImageIcon;

public class item {
	
	private int id; // identifies what type of item it is;		
	private String name; //name of item
	private String description; //description of item
	private boolean consumable; // if it is used, does it get thrown out
	ImageIcon icon; // the picture of the item  
	
	//default constructor
	public item()
	{
		id = -1;		
	}
	
	//constructor to initialize item only with Name
	public item(String pName)
	{
		this.name = pName;
	}
	
	//constructor to initialize item with only Name and Description
	public item(String pName, String pDescription)
	{
		this.name = pName;
		this.description = pDescription;
		this.icon = createImageIcon("images/"+pName+".png");
	}
	
	//constructor to initialize item id, and whether it is consumable
	public item(int pId, String pName, String pDes, boolean pCon)
	{
		this.id = pId;
		this.name = pName;
		this.description = pDes;
		this.consumable = pCon;
		this.icon = createImageIcon("images/"+pName+".png"); 
		
	}
	
	//get item id
	public int getItemId()
	{
		return this.id;
	}
	
	//get item name
	public String getItemName()
	{
		return this.name;
	}
	
	//get item description
	public String getItemDescription()
	{
		return this.description;
	}
	
	//get the item picture icon
	public ImageIcon getItemIcon()
	{
		return this.icon;
	}
	
	//set item name
	public void setItemName(String pName)
	{
		this.name = pName;
	}
	//set item id
	public void setItemId(int pId)
	{
		this.id = pId;
	}
		
	//returns item String information
	public String toString()
	{
		return name;
	}
	
	protected ImageIcon createImageIcon(String path) 
    {       
        if (path != null) {        	
            return new ImageIcon(path);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

}




 
