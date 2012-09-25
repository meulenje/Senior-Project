import java.util.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author Jeff
 */
public class CombatEngine {
	private Random randomNumberGenerator = new Random();

	public Character combat(Character character, ArrayList<Monster> enemies)
			throws InterruptedException {

		Entity actor = null;
		System.out.println("Combat started against " + enemies.size()
				+ " enemies!");
		String action;
		String event = null;
		boolean combatOver = false;
		boolean fled = false;
		Stack<Entity> turnStack = new Stack<Entity>();
		int accumulatedExp = 0;

		// initialize array of all combatants(enemies and character)
		ArrayList<Entity> combatants = new ArrayList<Entity>();
		combatants.add(character);
		for (Monster m : enemies) {
			combatants.add(m);
		}

		// determine turn order
		combatants = sortTurnOrderArray(combatants);

		// populate turn stack for first round
		for (Entity e : combatants) {
			turnStack.push(e);
		}

		while (!combatOver) {

			// display health messages
			System.out.println();
			for (Entity e : combatants) {
				System.out.println(e.getName() + "'s health: "
						+ e.getCurrentHealth() + "/" + e.getMaxHealth());
			}
			System.out.println();
			Thread.sleep(2000);

			actor = turnStack.pop();

			// start character turn
			if (actor instanceof Character) {
				action = character.takeTurn();

				// actions for character
				if (action.equals("attack")) {
					event = executeAttack(character, enemies.get(0));
				}

				if (action.equals("flee")) {
					if (attemptToFlee(character)) {
						event = "You fled successfully!";
						combatOver = true;
						fled = true;
					} else {
						event = "Your attempt to flee failed!";
					}
				}
				
				if (action.equals("heal")){
					event = executeHeal(character);
				}
				// end character turn

				// start monster turn
			} else if (actor instanceof Monster) {
				action = ((Monster) actor).takeTurn();
				if (action.equals("attack")) {
					event = executeAttack(actor, character);
				}
				
				if (action.equals("heal")){
					event = executeHeal(actor);
				}
			}

			// end monster turn

			System.out.println(event);
			Thread.sleep(2000);

			if (character.getCurrentHealth() <= 0) {
				System.out.println(character.getName() + " was slain!");
				combatOver = true;
			}

			for (int i = 0; i < enemies.size(); i++) {
				Monster m = enemies.get(i);
				if (m.getCurrentHealth() <= 0) {
					System.out.println(m.getName() + " was slain!");
					accumulatedExp += m.getExpValue();
					combatants.remove(m);
					enemies.remove(m);
				}
			}
			if (enemies.size() < 1) {
				combatOver = true;
			}
			//peek at next Entity in stack to make sure they are alive
			if(!turnStack.empty() && !turnStack.peek().alive()){
				turnStack.pop();
			}

			// if stack is empty, refill it
			if (turnStack.empty()) {
				for (Entity e : combatants) {
					turnStack.push(e);
				}
			}
		}

		endCombat(character, accumulatedExp, fled);
		System.out.println("Combat Over");
		return character;
	}
	
	public String executeAction(int sourceID, 
								ArrayList<Entity> sources, 
								int targetID, 
								ArrayList<Entity> targets,
								int actionID){
		String event = null;
		
		switch(actionID){
		case 1:
			event = executeAttack(sources.get(sourceID), targets.get(targetID));
			break;
		case 2:
		
		
		}
		return event;
	}

	public String executeAttack(Entity source, Entity target) {
		int calculatedDamage = 0;
		int attackVal = source.getAttack() + randomNumberGenerator.nextInt(6);
		int defenseVal = target.getDefense()
				+ randomNumberGenerator.nextInt(6);

		calculatedDamage = attackVal - (defenseVal / 2);
		if (calculatedDamage < 0) {
			calculatedDamage = 0;
		}

		target.setCurrentHealth(target.getCurrentHealth()
				- calculatedDamage);
		return (source.getName() + "'s attack hit " + target.getName()
				+ " for " + calculatedDamage + " damage!");
	}
	
	public String executeHeal (Entity healer){
		int amountHealed = healer.getAttack() + randomNumberGenerator.nextInt(6);
		healer.setCurrentHealth(healer.getCurrentHealth()
				+ amountHealed);
		if (healer.getCurrentHealth() > healer.getMaxHealth()){
			healer.setCurrentHealth(healer.getMaxHealth());
			return (healer.getName() + " was fully healed!");
		}
		else {
			return (healer.getName()+ " was healed for " + amountHealed + " damage!");
		}
	}
	

	public boolean attemptToFlee(Character character) {
		boolean result;

		int roll = randomNumberGenerator.nextInt(3);
		if (roll == 2) {
			result = true;
		} else {
			result = false;
		}

		return result;
	}

	// returns array of type Entity sorted by speed. Lowest speed is first.
	public ArrayList<Entity> sortTurnOrderArray(ArrayList<Entity> combatants) {
		Collections.sort(combatants);

		return combatants;
	}

	public ArrayList<Monster> initializeEnemies(int zoneID) {
		ArrayList<Monster> monsterArray = new ArrayList<Monster>();
		int numberOfEnemies = 5;// randomNumberGenerator.nextInt(3);
		Monster m;

		for (int i = 1; i <= numberOfEnemies; i++) {
			m = new Monster("Goblin", 10, 10, 5, 5, 1, 3);
			m.setHasHeal(true);
			m.setCombatID(i);
			m.setName(m.getName() + " " + i);
			monsterArray.add(m);
		}
		return monsterArray;
	}

	private boolean endCombat(Character character, int experience, boolean fled) {
		if (fled) {
			// code to handle running away (nothing happens)
			return true;
		} else {
			if (character.getCurrentHealth() > 0) {
				character.setExp(character.getExp() + experience);
				System.out
						.println("Victory! " + character.getName()
								+ " has earned " + experience
								+ " experience!");
				return true;
			} else {
				System.out.println("Defeat!");
				return false;
			}
		}
	}

}
