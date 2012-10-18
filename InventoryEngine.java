package rpg;

import java.util.ArrayList;
import javax.swing.JList;
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
	
	private ArrayList<String> itemsInBackpack; //characters inventory items
	private String equipped; //evenually each character will have equipped, in need of Enitiy class
	private ArrayList<String> charactersInParty; //character names
	private int index = 0; //index of selected character

	private JList<Object> items;

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
		itemsInBackpack = new ArrayList<String>();
		itemsInBackpack.add("scroll");
		itemsInBackpack.add("paperClip");
		itemsInBackpack.add("tent");
		itemsInBackpack.add("rpg");
		itemsInBackpack.add("hammer");
		itemsInBackpack.add("shovel");
		itemsInBackpack.add("revolver");
		itemsInBackpack.add("hat");
		itemsInBackpack.add("sword");
		itemsInBackpack.add("egg");
		itemsInBackpack.add("diamondPickaxe");
		
		//initailize the list
		items = new JList<Object>(getItemArray());  //JList that displays the items
        items.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        items.setSelectedIndex(0);
        items.addListSelectionListener((ListSelectionListener) this);
		
		
	}
	
	//return the string of the character that is selected
	public String getSelectedCharacter()
	{
		return charactersInParty.get(index);
	}
	
	//return the string of the item that is selected
	public String getSelectedItem(int pIndex)
	{
		return itemsInBackpack.get(pIndex);
	}
	
	//get the first item from the list
	//public String getSelectedItem
	
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
		return itemsInBackpack.get(items.getSelectedIndex());
	}
	
	//return the string of the description of the item selected
	public String getItemDescription()
	{
		return "description: this is a description";
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
	public JList<Object> getItemList()
	{
		return this.items;
	}
	
	//returns an array of the list
	public String[] getItemArray()
	{
		String[] array = itemsInBackpack.toArray(new String[itemsInBackpack.size()]);		
		return array;
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) 
	{
		
		JList list = (JList)arg0.getSource();
        IG.updateBackpackLabel(getSelectedItem(list.getSelectedIndex()));
		
		
	}
	

}