/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jeff
 */
public class Main {
    static Character c;
    static Monster m;
    static CombatEngine engine;

    public static void main(String args[]) {
        engine = new CombatEngine();
        populate();
        c = engine.combat(c, m);
    }

    public static void populate() {
        c = new Character("Samus", 40, 40, 5, 5, 4);
        m = new Monster("Goblin", 10, 10, 5, 5, 4, 3);
    }
}
