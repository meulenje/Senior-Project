package rpg;

import javax.swing.ImageIcon;

public class Item extends RPGObject implements Comparable<Item>{
		
	private static final long serialVersionUID = 1L;
	private String name; //name of item
	private String description; //description of item
	private boolean consumable; // if it is used, does it get thrown out
	private int maxHealth;
	private int currentHealth;
	private int maxMana;
	private int currentMana;
	private int attack;
	private int defense;
	private int speed;
	
	//constructor to initialize item id, and whether it is consumable
	public Item(int pId, ImageIcon image, String pName, String pDes, boolean pCon, int maxHealth, int currentHealth, int maxMana, int currentMana, int attack, int defense, int speed)
	{
		this.id = pId;
		this.image = image;
		this.name = pName;
		this.description = pDes;
		this.consumable = pCon;
		this.setMaxHealth(maxHealth);
		this.setCurrentHealth(currentHealth);
		this.setMaxMana(maxMana);
		this.setCurrentMana(currentMana);
		this.setAttack(attack);
		this.setDefense(defense);
		this.setSpeed(speed);
		
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


	public int getMaxHealth() {
		return maxHealth;
	}


	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}


	public int getCurrentHealth() {
		return currentHealth;
	}


	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}


	public int getMaxMana() {
		return maxMana;
	}


	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}


	public int getCurrentMana() {
		return currentMana;
	}


	public void setCurrentMana(int currentMana) {
		this.currentMana = currentMana;
	}


	public int getAttack() {
		return attack;
	}


	public void setAttack(int attack) {
		this.attack = attack;
	}


	public int getDefense() {
		return defense;
	}


	public void setDefense(int defense) {
		this.defense = defense;
	}


	public int getSpeed() {
		return speed;
	}


	public void setSpeed(int speed) {
		this.speed = speed;
	}
}




 
