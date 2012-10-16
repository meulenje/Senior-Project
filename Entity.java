package rpg;

import java.util.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/**
 * Entity class
 * 
 * Represents the Player, Enemies, GridObjects, and more. It builds
 * a 4-layered image for displaying itself, and contains relevant
 * getter and setter functions for data fetching.
 * 
 * @author Jeff, Austin
 * @version 10/15/2012
 */
@SuppressWarnings("serial")
public class Entity extends JLayeredPane implements Comparable<Entity>{

	private GameEngine GE; // link back to Engine
	
	// GUI specific variables
    private int terrain; // identifies what type of cell the image displays
    private int entity; // is there an object here? 0=no
    private int accessory; // identifies what extra layer is on top
    private int fog; // a special variable only for fog purposes
    protected ImageIcon terrainImage; // grass, dirt, floor, etc...
    protected ImageIcon entityImage; // player, enemy, rock, etc...
    protected ImageIcon accessoryImage; // tall grass, roofs, extra effects, etc...
    protected ImageIcon fogImage; // partially visible layer for fog effect
    protected JLabel background; // container to hold terrain image
    protected JLabel foreground; // container to hold entity image
    protected JLabel highground; // container to hold accessory image
    protected JLabel fogLayer; // only holds fog for blurry vision
    
    // Entity specific variables
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

    /**
     *  public constructor to build the image and store data
     * @param tempEngine
     * @param terrain
     * @param entity
     * @param accessory
     * @param name
     * @param isPlayer
     * @param currentHealth
     * @param maxHealth
     * @param attack
     * @param defense
     * @param speed
     */
    public Entity(GameEngine tempEngine, int terrain, int entity, int accessory, String name, 
    		boolean isPlayer, int currentHealth, int maxHealth, int attack, int defense, int speed)
    {
    	GE = tempEngine; // link back to GameEngine
    	
    	this.terrain = terrain;
    	this.entity = entity;
    	this.accessory = accessory;
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.name = name;
        this.isPlayer = isPlayer;
        
        // begin building the 4-layered image
    	// Lay a background, foreground, and highground JLabel
    	background = new JLabel();
    	background.setSize(GE.C_WIDTH, GE.C_HEIGHT);
    	background.setLocation(0,0);
    	foreground = new JLabel();
    	foreground.setSize(GE.C_WIDTH, GE.C_HEIGHT);
    	foreground.setLocation(0,0);
    	highground = new JLabel();
    	highground.setSize(GE.C_WIDTH, GE.C_HEIGHT);
    	highground.setLocation(0,0);
    	fogLayer = new JLabel();
    	fogLayer.setSize(GE.C_WIDTH, GE.C_HEIGHT);
    	fogLayer.setLocation(0,0);
    	
    	// set components
    	this.add(background, JLayeredPane.DEFAULT_LAYER); // 0
    	this.add(foreground, JLayeredPane.PALETTE_LAYER); // 100 (above 0)
    	this.add(highground, JLayeredPane.MODAL_LAYER);   // 200 (above 100)
    	this.add(fogLayer, JLayeredPane.POPUP_LAYER); // 300 (above 200)
    	this.setLayout(new BorderLayout());
    	this.setSize(new Dimension(GE.C_WIDTH, GE.C_HEIGHT));
    	
    	// set the images according to the id's given
    	setTerrain(terrain);
    	setEntity(entity);
    	setAccessory(accessory);
    	setFog(GE.EmptyID);
    }
    

    /**
     * A simple way to re-construct a GridObject
     * without having to call multiple functions.
     * @param int id
     * @param int entity
     * @param int accessory
     */
    public void resetObject(int t, int e, int a)
    {
    	setTerrain(t);
    	setEntity(e);
    	setAccessory(a);
    }
    
    /**
     * isEmptySpace
     * Returns true, if the location is walkable, and
     * there is no Entities on the location.
     * @return boolean
     */
    public boolean isEmptySpace()
    {
    	return (terrain<0 && entity==GE.EmptyID);	
    }
    
    /**
     * isHole
     * Returns true, if the location is a hole.
     * @return boolean
     */
    public boolean isHole()
    {
    	return (entity==GE.HoleID);
    }
    
    /**
     * Returns true, if the location is pushable, like a rock.
     * @return boolean
     */
    public boolean isPushable()
    {
    	return (entity==GE.RockID);
    }
    
    /**
     * Returns true, if the location has a consumable object
     * on it. Like food, coins, or points.
     * @return
     */
    public boolean isConsumable()
    {
    	return false;
    }
    
    /**
     * isMonster
     * Returns true if the location is a monster
     * @return boolean
     */
    public boolean isMonster()
    {
    	return (entity==GE.LavaMonsterID || entity==GE.PirateID);
    }
    
    /**
     * isExit
     * Returns true if the location is the finish/exit
     * @return
     */
    public boolean isExit()
    {
    	return (accessory==GE.ExitID);
    }
    
    /**
     * isEncountersEnabled
     * Returns true if the location has a chance of
     * spawning a random encounter with a monster.
     * (Eg. Tall Grass can produce surprise attacks)
     * @return
     */
    public boolean isEncountersEnabled()
    {
    	return (accessory==GE.TallGrassID);
    }
    
    
    /**
     * Sets the id of the entity layer and adjusts
     * the image accordingly
     * @param int p
     */
    public void setEntity(int p)
    {    	
    	// set the foreground image
    	if(p == GE.EmptyID)
    	{
    		entityImage=GE.Empty;
    	}
    	else if(p == GE.PlayerID)
    	{
    		entityImage=GE.Player;
    	}
    	else if(p == GE.RockID)
    	{
    		entityImage=GE.Rock;
    	}
    	else if(p == GE.HoleID)
    	{
    		entityImage=GE.Hole;
    	}
    	else if(p == GE.LavaMonsterID)
    	{
    		entityImage=GE.LavaMonster;
    	}
    	else if(p == GE.PirateID)
    	{
    		entityImage=GE.Pirate;
    	}
    	else
    		GE.printError("Error!\nNo image found for entity id="+p);
        
    	entity = p;
        foreground.setIcon(entityImage);
    }
    
    /**
     * Sets the id of the terrain layer and adjusts
     * the image accordingly.
     * @param int i
     */
    public void setTerrain(int i)
    {
    	// set the background image
    	if(i == GE.EmptyID)
    	{
    		this.setVisible(false);
    	}
    	else if(i == GE.WarpAID || i == GE.WarpBID)
    	{
    		terrainImage=GE.XSpace;
    	}
    	else if(i == GE.WaterID)
    	{
    		terrainImage=GE.Water;
    	}
    	else if(i == GE.GrassID)
    	{
    		terrainImage=GE.Grass;
    	}
    	else if(i == GE.DirtID)
    	{
    		terrainImage=GE.Dirt;
    	}
    	else
    		GE.printError("Error!\nNo image found for terrain id="+i);
    	
    	terrain = i;
    	background.setIcon(terrainImage);
    }
    
    /**
     * Sets the top layer's image and stores the new id for
     * the accessory layer.
     * @param int a
     */
    public void setAccessory(int a)
    {    	
    	// set the highground image
    	if(a == GE.EmptyID)
    	{
    		accessoryImage=GE.Empty;
    	}
    	else if(a == GE.TallGrassID)
    	{
    		accessoryImage=GE.TallGrass;
    	}
    	else if(a == GE.ExitID)
    	{
    		accessoryImage=GE.Hideout;
    	}
    	else	
    		GE.printError("Error!\nNo image found for accessory id="+a);
        
    	accessory = a;
        highground.setIcon(accessoryImage);
    }
    
    /**
     * Determines what fog piece is displayed on the fourth layer
     * @param f
     */
    public void setFog(int f)
    {
    	if(f==GE.FogCenterID)
    	{
    		fogImage = GE.FogCenter;
    	}
    	else if(f==GE.FogTopID)
    	{
    		fogImage = GE.FogTop;
    	}
    	else if(f==GE.FogBottomID)
    	{
    		fogImage = GE.FogBottom;
    	}
    	else if(f==GE.FogLeftID)
    	{
    		fogImage = GE.FogLeft;
    	}
    	else if(f==GE.FogRightID)
    	{
    		fogImage = GE.FogRight;
    	}
    	else if(f==GE.EmptyID)
    	{
    		fogImage = GE.Empty;
    	}
    	else
    		GE.printError("Error!\nNo image found for fog id="+f);
    	
    	fog = f;
    	fogLayer.setIcon(fogImage);
    }
    
    /**
     * Returns the id of the Entity
     * that is on middle layer of the GridObject
     * @return int entity
     */
    public int getEntity()
    {
        return entity;
    }
    
    /**
     * Returns the id of the GridOjbect
     * that is the lowest layer of the GridObject
     * @return int terrain
     */
    public int getTerrain()
    {
        return terrain;
    }
    
    /**
     * Returns the id of the AccessoryLayer
     * that is the highest layer of the GridObject
     * @return int accessory
     */
    public int getAccessory()
    {
    	return accessory;
    }
    
    /**
     * Returns the id of the fog layer
     */
    public int getFog()
    {
    	return fog;
    }

    /**
     * Returns the move type that the monster uses
     * @return string "heal" or "attack"
     */
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
