package rpg;

import javax.swing.ImageIcon;

public class Item extends RPGObject implements Comparable<Item>{
		
	private String name; //name of item
	private String description; //description of item
	private boolean consumable; // if it is used, does it get thrown out
	ImageIcon image; // the picture of the item  
	
	//constructor to initialize item with only Name and Description
	public Item(String pName, String pDescription)
	{
		this.name = pName;
		this.description = pDescription;
		this.image = createImageIcon("images/"+pName+".png"); // where the itme images are
	}
	//constructor to initialize item id, and whether it is consumable
	public Item(int pId, ImageIcon image, String pName, String pDes, boolean pCon)
	{
		this.id = pId;
		this.image = createImageIcon("images/"+pName+".png"); // where the itme images are
		this.name = pName;
		this.description = pDes;
		this.consumable = pCon;
		
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
		return this.image;
	}
	
	//set item name
	public void setItemName(String pName)
	{
		this.name = pName;
	}
	
		
	//returns item String information
	public String toString()
	{
		return name;
	}
	
	//make the item icon
	protected ImageIcon createImageIcon(String path) 
    {       
        if (path != null) {        	
            return new ImageIcon(path);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

	@Override
	public int compareTo(Item o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isConsumable()
	{
		return consumable;
	}
}




 
