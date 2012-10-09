import java.util.*;

/**
 * 
 * @author Jeff
 */
public class Main {
	static Character c, d, e;
	static Monster m, n, o;
	static Ability cure;
	static Ability fireball;
	static CombatEngine engine;

	public static void main(String args[]) throws InterruptedException {
		populate();
		ArrayList<Character> characters = new ArrayList<Character>();
		characters.add(c);
		characters.add(d);
		characters.add(e);
		engine = new CombatEngine(characters);
		engine.gui = new CombatGUI(engine);
		while(!engine.combatOver){
			engine.combat();
		}
	}

	public static void populate() {
		c = new Character("Mario", 30, 40, 10, 5, 5);
		d = new Character("Luigi", 23, 40, 10, 5, 5);
		e = new Character("Yoshi", 1, 40, 10, 5, 5);
		cure = new Ability("Cure", 1, 0, 0);
		fireball = new Ability("Fireball", 0, 1, 0);
		c.abilities.add(cure);
		c.abilities.add(fireball);
		d.abilities.add(cure);
		d.abilities.add(fireball);
		e.abilities.add(cure);
		e.abilities.add(fireball);
	}
}
