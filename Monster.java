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
     public Monster(String name, int currentHealth, int maxHealth, int attack,
            int defense, int speed, int expValue){
        super(name, currentHealth, maxHealth, attack, defense, speed);
        this.setExpValue(expValue);
    }

    public String takeTurn(){
        return "attack";
    }

	public int getExpValue() {
		return expValue;
	}

	public void setExpValue(int expValue) {
		this.expValue = expValue;
	}
}
