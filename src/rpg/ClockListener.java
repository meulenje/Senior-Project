package rpg;

/**
 * ClockListener keeps track of time and notifies other classes
 * of an increase in the time (tick).
 * @author Austin Delamar
 * @version 9/29/2012
 */
public interface ClockListener {
	/**
	 * This method is called on every "clock tick" to allow the
	 * implementing class to perform actions synchronously with the master
	 * clock.
	 * @param tick	the current simulation time
	 * @return 		the implementing class should return true when 
	 * 				an "important" action has occurred.
	 */
	public boolean event(int tick);
}
