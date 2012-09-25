import java.util.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jeff
 */
public class Monster extends Entity{
    private int expValue;
    private int combatID;
    private Random RNG = new Random();
    
     public Monster(String name, int currentHealth, int maxHealth, int attack,
            int defense, int speed, int expValue){
        super(name, currentHealth, maxHealth, attack, defense, speed);
        this.setExpValue(expValue);
    }

     public String takeTurn(){
     	if (this.getHasHeal()==true && this.getCurrentHealth() < (this.getMaxHealth()*.5) && RNG.nextInt(10) > 7){
 			return "heal";
 		}
     	else{
     		return "attack";
     	}
     }

	public int getExpValue() {
		return expValue;
	}

	public void setExpValue(int expValue) {
		this.expValue = expValue;
	}
	
	public void setCombatID(int id){
		this.combatID = id;
	}
	
	public int getCombatID(){
		return combatID;
	}
}
