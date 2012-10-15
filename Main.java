import java.util.*;

/**
 * 
 * @author Jeff
 */
public class Main {
	static Entity c, d, e;
	static Entity m, n, o;
	static Ability cure;
	static Ability fireball;
	static CombatEngine engine;

	public static void main(String args[]) throws InterruptedException {
		populate();
		ArrayList<Entity> characters = new ArrayList<Entity>();
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
		c = new Entity("Mario", true, 30, 40, 10, 5, 5);
		d = new Entity("Luigi", true, 23, 40, 10, 5, 5);
		e = new Entity("Yoshi", true, 1, 40, 10, 5, 5);
		cure = new Ability("Cure", 1, 0, 0);
		fireball = new Ability("Fireball", 0, 1, 0);
		c.abilities.add(cure);
		d.abilities.add(fireball);
		e.abilities.add(cure);
		e.abilities.add(fireball);
	}
}
