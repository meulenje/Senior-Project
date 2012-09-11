
/*
* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jeff
 */
public class Entity {

    private int maxHealth;
    private int currentHealth;
    private int attack;
    private int defense;
    private int speed;
    private String name;

    public Entity(String name, int currentHealth, int maxHealth, int attack,
            int defense, int speed) {
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.name = name;
    }

    public boolean alive() {
        if (this.getCurrentHealth() > 0) {
            return true;
        } else {
            return false;
        }
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
}
