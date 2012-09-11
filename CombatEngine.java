import java.util.Random;
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

	public Character combat(Character character, Monster enemy) {
		System.out.println("Combat started against " + enemy.getName() + "!");
		String action;
		String event = null;
		boolean playersTurn = false;
		boolean combatOver = false;
		boolean fled = false;

		Entity firstToAct = determineFirstTurn(character, enemy);
		if (firstToAct.equals(character)) {
			playersTurn = true;
		} else {
			playersTurn = false;
		}

		while (!combatOver) {
			
			//display health messages
			System.out.println();
			System.out.println(character.getName() + "'s health: " + character.getCurrentHealth() + "/" + 
					character.getMaxHealth());
			System.out.println(enemy.getName() + "'s health: " + enemy.getCurrentHealth() + "/" + 
					enemy.getMaxHealth());
			System.out.println();
			
			if (playersTurn) {
				action = character.takeTurn();
				
				//actions for character
				if (action.equals("attack")) {
					event = executeAttack(character, enemy);
				}
				
				if (action.equals("flee")){
					if(attemptToFlee(character)){
						event = "You fled successfully!";
						combatOver = true;
						fled = true;
					}else{
						event = "Your attempt to flee failed!";
					}
				}
				System.out.println(event);
				playersTurn = false;
			
			} else {
				action = enemy.takeTurn();
				if (action.equals("attack")) {
					event = executeAttack(enemy, character);
				}
				System.out.println(event);
				playersTurn = true;
			}

			if (character.getCurrentHealth() <= 0) {
				System.out.println(character.getName() + " was slain!");
				combatOver = true;
			}

			if (enemy.getCurrentHealth() <= 0) {
				System.out.println(enemy.getName() + " was slain!");
				combatOver = true;
			}
		}

		endCombat(character, enemy, fled);
		System.out.println("Combat Over");
		return character;
	}

	public String executeAttack(Entity sender, Entity receiver) {
		int calculatedDamage = 0;
		int attackVal = sender.getAttack() + randomNumberGenerator.nextInt(6);
		int defenseVal = receiver.getDefense()
				+ randomNumberGenerator.nextInt(6);

		calculatedDamage = attackVal - (defenseVal / 2);
		if (calculatedDamage < 0) {
			calculatedDamage = 0;
		}

		receiver.setCurrentHealth(receiver.getCurrentHealth()
				- calculatedDamage);
		return (sender.getName() + "'s attack hit " + receiver.getName()
				+ " for " + calculatedDamage + " damage!");
	}
	
	public boolean attemptToFlee(Character character){
		boolean result;
		
		int roll = randomNumberGenerator.nextInt(3);
		if(roll == 2){
			result = true;
		}else{
			result = false;
		}
		
		return result;
	}

	private Entity determineFirstTurn(Entity a, Entity b) {
		if (a.getSpeed() > b.getSpeed()) {
			return a;
		} else {
			return b;
		}
	}

	private boolean endCombat(Character character, Monster enemy, boolean fled) {
		if (fled) {
			//code to handle running away
			return true;
		} else {
			if (character.getCurrentHealth() > 0) {
				character.setExp(character.getExp() + enemy.getExpValue());
				System.out
						.println("Victory! " + character.getName()
								+ " has earned " + enemy.getExpValue()
								+ " experience!");
				return true;
			} else {
				System.out.println("Defeat!");
				return false;
			}
		}
	}
	 
}
