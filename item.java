package rpg;

public class item {
	
	private int id; // identifies what type of item it is;		
	private String name; //name of item
	private String description; //description of item
	private boolean consumable; // if it is used, does it get thrown out
	
	//default constructor
	public item()
	{
		id = -1;		
	}
	
	//constructor to initialize item id, and whether it is consumable
	public item(int pId, boolean pCon)
	{
		this.id = pId;
		this.consumable = pCon;
		
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
	
	public String toString()
	{
		return "Name: " + name + "\n" + "Description: " + description;
	}
	

}




 
