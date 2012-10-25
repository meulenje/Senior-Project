package rpg;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Inventory Engine class
 * 
 * This class holds all of the important information for the InventoryGUI to display
 * eventually this class my need to be merged with Game Engine class
 * @author Corey Jones
 * @version 10/00/2012
 *
 */

public class InventoryEngine implements ListSelectionListener{
		
	private InventoryGUI IG; // link to the InventoryGUI
	
	private JList<item> itemList = new JList<item>(); // list to hold values of items 
	private ArrayList<item> itemsInBackpack; //characters inventory items
	private String equipped; //evenually each character will have equipped, in need of Enitiy class
	private ArrayList<String> charactersInParty; //character names
	private int index = 0; //index of selected character
	

	public InventoryEngine(InventoryGUI tempGUI)
	{
		//link to inventoryGUI 
		IG = tempGUI; 
		
		//initialize array list of characters
		charactersInParty = new ArrayList<String>();				
		charactersInParty.add("stitch");
		charactersInParty.add("squid");
		charactersInParty.add("frog");
		charactersInParty.add("frank");
		charactersInParty.add("blue");
		charactersInParty.add("eyes");
		
		//initialize array list of items
		itemsInBackpack = new ArrayList<item>();
		itemsInBackpack.add(new item("scroll", "A piece of paper that you can read"));
		itemsInBackpack.add(new item("paperClip", "Hold paper, or scrolls together"));
		itemsInBackpack.add(new item("tent", "Cover for you to sleep on your journey"));
		itemsInBackpack.add(new item("rpg", "To blow shit up"));
		itemsInBackpack.add(new item("hammer", "To smash rocks...."));
		itemsInBackpack.add(new item("shovel","To dig to China....."));
		itemsInBackpack.add(new item("revolver", "A clue to who murdered someone"));
		itemsInBackpack.add(new item("hat","Goes on the head for stylish purposes"));
		itemsInBackpack.add(new item("sword","When guns are not present"));
		itemsInBackpack.add(new item("egg", "Breakfast food stolen from a nest you found"));		
		itemsInBackpack.add(new item("diamondPickaxe","For mining blocks faster"));
		
		
		//initailize the list
		itemList = new JList<item>(getItemArray());  //JList that displays the items
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setSelectedIndex(0);
        itemList.addListSelectionListener((ListSelectionListener) this);
		
		
	}
	
	//return the string of the character that is selected
	public String getSelectedCharacter()
	{
		return charactersInParty.get(index);
	}
	
//return the string of the item that is selected
	public item getSelectedItem(int pIndex)
	{
		return itemsInBackpack.get(pIndex);
	}
	
	//remember last selected item ( for now its just grabbing the first item!)
	public item getInitialSelectedItem()
	{
		return itemsInBackpack.get(0);
	}
	
	//Just one equipped item so far, until more of character entity equip options are defined
	public void setEquipped(String pEquip)
	{
		equipped = pEquip;
	}
	
	// what is the string of the equipped item
	public String getEquipped()
	{
		return equipped; 
	}
	
	//return the string of the name of the item selected
	public String getItemName()
	{
		return itemsInBackpack.get(itemList.getSelectedIndex()).getItemName();
	}
	
	//return the string of the description of the item selected
	public String getItemDescription()
	{
		return itemsInBackpack.get(itemList.getSelectedIndex()).getItemDescription();
	}
	
	//navigates to the previous character selection
	public void  navigateCharacter(String pNav)
	{ 	
		int size = charactersInParty.size(); 
		//if navigating to previous character
		if(pNav == "previous")
		{			
			index -= 1;
			//if trying to navigate before first character, go to end
			if(index < 0)
			{
				index = size-1;
			}
		}
		//if navigating to next character
		if(pNav == "next")
		{
			index += 1;
			//if no more characters in list, go to beginning 
			if(index >= size)
			{
				index = 0;
			}
		}
			
	}
	
	//returns the item list
	public JList<item> getItemList()
	{
		return this.itemList;
	}
	
	//returns an array of the list
	public item[] getItemArray()
	{
		item[] array = new item[itemsInBackpack.size()];
		itemsInBackpack.toArray(array);		
		return array; 
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) 
	{
		
        IG.updateBackpackLabel(getSelectedItem(itemList.getSelectedIndex()));	
		
	}
	

}