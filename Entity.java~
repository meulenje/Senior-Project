package rpg;

import java.util.*;
/*
* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.ImageIcon;

/**
 *
 * @author Jeff
 */
public class Entity extends RPGObject implements Comparable<Entity>{

    private int maxHealth;
    private int currentHealth;
    private int attack;
    private int defense;
    private int speed;
    private String name;
    private Random RNG = new Random();
    //is this entity object a player
    public boolean isPlayer = false;
    //experience value for monsters, accumulated experience for players
    private int exp;
    ArrayList<Ability> abilities = new ArrayList<Ability>();
 
    private Item equippedItem; //for now they may only have one equipped item at a time

    public Entity(int id, ImageIcon image, String name, boolean isPlayer, Item equippedItem,
    		int currentHealth, int maxHealth, int attack, int defense, int speed) {
        
    	this.id = id;
    	this.image = image;
    	this.equippedItem = equippedItem;
    	this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.name = name;
        this.isPlayer = isPlayer;
    }

    //monster combat AI.
    public String monsterTurn(){
     	if (this.hasHealingAbility()==true && this.getCurrentHealth() < (this.getMaxHealth()*.5) && RNG.nextInt(10) > 7){
 	return "heal";
 	}
     	else{
     	return "attack";
     	}
     }
    
    private boolean hasHealingAbility(){
    boolean returnVal = false;
    if(!this.abilities.isEmpty()){
    for(Ability a : this.abilities){
    if (a.getType() == 1){
    returnVal = true;
    }
    }
    }
    return returnVal;
    }
    
    public boolean alive() {
        if (this.getCurrentHealth() > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public int compareTo(Entity other){
    return (this.speed - other.getSpeed());
    }

    /**
     * @return the attack
     */
    public int getAttack() {
        return attack;
    }
    
    /**
     * @param equipt item the item equipped
     * 
     */
    public void setEquippedItem(Item item){
    	this.equippedItem = item; 
    }

    /**
     * @param attack the attack to set
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }

    /**
     * @return the defense
     */
    public int getDefense() {
        return defense;
    }

    /**
     * @param defense the defense to set
     */
    public void setDefense(int defense) {
        this.defense = defense;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * @return the maxHealth
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * @param maxHealth the maxHealth to set
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * @return the currentHealth
     */
    public int getCurrentHealth() {
        return currentHealth;
    }
    
    /**
     * @return the equipped item
     */
    public Item getEquipped()
    {
    	return this.equippedItem;
    }

    /**
     * @param currentHealth the currentHealth to set
     */
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }
    
    public Ability getAbilityByName(String name){
    Ability returnVal = null;
    for(Ability a : abilities){
    if (a.getName().equals(name)){
    returnVal = a;
    }
    }
    return returnVal;
   
    }

public int getExp() {
return exp;
}

public void setExp(int exp) {
this.exp = exp;
}
}