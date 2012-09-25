import java.util.*;

/**
 *
 * @author Jeff
 */
public class Main {
    static Character c;
    static Monster m, n, o;
    static CombatEngine engine;

    public static void main(String args[]) throws InterruptedException{
        engine = new CombatEngine();
        populate();
        ArrayList<Monster> array = engine.initializeEnemies(0);
        c = engine.combat(c, array);
    }

    public static void populate() {
        c = new Character("Samus", 40, 40, 10, 5, 5);
    }
}
