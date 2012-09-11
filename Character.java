import java.io.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author Jeff
 */
public class Character extends Entity {
	private int exp;

	public Character(String name, int currentHealth, int maxHealth, int attack,
			int defense, int speed) {
		super(name, currentHealth, maxHealth, attack, defense, speed);
		this.exp = 0;
	}

	public String takeTurn() {
		String action = null;

		// temporary command line interface
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean reading = true;
		while (reading) {
			System.out.println("What should " + this.getName() + " do?");
			try {
				action = br.readLine();
				reading = false;
			} catch (IOException ioe) {
				System.out.println("IO error trying to read action!");
				System.exit(1);
			}
		}
		
		return action;
	}

	/**
	 * @return the exp
	 */
	public int getExp() {
		return exp;
	}

	/**
	 * @param exp
	 *            the exp to set
	 */
	public void setExp(int exp) {
		this.exp = exp;
	}
}
