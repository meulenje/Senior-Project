package rpg;

import javax.swing.ImageIcon;

public class item {
	
	private int id; // identifies what type of item it is;		
	private String name; //name of item
	private String description; //description of item
	private boolean consumable; // if it is used, does it get thrown out
	ImageIcon picture;  
	
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
	}
	
	//constructor to initialize item id, and whether it is consumable
	public item(int pId, String pName, String pDes, boolean pCon, ImageIcon pPic)
	{
		this.id = pId;
		this.name = pName;
		this.description = pDes;
		this.consumable = pCon;
		this.picture = pPic; 
		
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
	
	//set item name
	private void setItemName(String pName)
	{
		this.name = pName;
	}
	//set item id
	private void setItemId(int pId)
	{
		this.id = pId;
	}
	
	//returns item String information
	public String toString()
	{
		return name;
	}
	

}




 
