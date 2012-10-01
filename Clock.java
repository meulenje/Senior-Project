package rpg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 * @author Roger Ferguson, Hans Dulimarta, Matt Gerber, Austin Delamar
 * 
 *         Revision History
 *         Winter 2011
 *         Add a second ArrayList to avoid "ConcurrentUpdate" exception
 * 
 *         2008-Jan-25
 *         Change array to ArrayList
 *         The clock tick is advanced on a timer signal
 *         Allow clock ticking frequency to be adjusted
 *         
 *         2012-Sept-29
 *         Change currentTime to protected, instead of private.
 */
public class Clock implements ActionListener
{
    public final static int FOREVER = -1;
    private ArrayList<ClockListener> currListeners;
    private ArrayList<ClockListener> newListeners;
    protected int currentTime;
    private int endingTime;
    private int timerInterval;
    private Timer timer;

    /**
     * Constructor
     */
    public Clock()
    {
        currListeners = new ArrayList<ClockListener>();
        newListeners = new ArrayList<ClockListener>();
        currentTime = 0;
        timerInterval = 1000; /* default to one second */
        timer = new Timer(timerInterval, this);
    }

    /**
     * Register a clock listener object to this clock. To avoid
     * "ConcurrentUpdateException" register() does not directly add
     * "obj" to the current list of listeners.
     * 
     * @param obj
     *        a reference to an object whose event() invocation is
     *        to be triggered by this clock
     */
    public void register(ClockListener obj)
    {
        newListeners.add(obj);
    }

    /**
     * Start the clock and run it for a given period of time. To run this
     * clock forever pass the constant Clock.FOREVER as the actual
     * parameter
     * 
     * @param period
     *        the duration of run
     */
    public void run(int duration)
    {
        endingTime = duration;

        timer.start();
    }

    /**
     * Pauses the clock
     */
    public void pause()
    {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        if (currentTime == endingTime)
        {
            timer.stop();
            timer = null;

            // the following commented line will stop the application
            // not just the clock thread....
            //Runtime.getRuntime().exit(0);

            //System.err.println("Clock has stopped");
            return;
        }

        step();
    }

    /**
     * Sets the clock rate
     * 
     * @param val
     */
    public void setRate(int val)
    {
        if (val <= 0)
            val = 1000;

        timerInterval = val;

        timer.setDelay(timerInterval);
    }

    /**
     * Advances the clock time by one unit and notifies all listeners that
     * the clock has ticked.
     * 
     * @return True if any of the listeners performed a meaningful event on the tick
     */
    public boolean step()
    {
        boolean action = false;
        /*
         * To allow adding new ClockListener objects when the clock is
         * "ticking" (i.e. when step() has not yet returned)
         * we have have to use keep two lists (new and current).
         */
        currListeners.addAll(newListeners);
        newListeners.clear();

        for (ClockListener p : currListeners)
            if (p.event(currentTime))
                action = true;

        currentTime++;

        return action;
    }
}
