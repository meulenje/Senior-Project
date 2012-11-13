package rpg;

import java.awt.*;

import javax.swing.JPanel;

/**
 * AnimationGUI class
 * 
 * Displays visual animations
 * @author Austin
 *
 */
@SuppressWarnings("serial")
public class AnimationGUI extends JPanel
{
	private GameEngine GE;
	
    private int size = 25;// initial size of the box
    private boolean run;

    public AnimationGUI(GameEngine t)
    {
    	GE = t; // link back to game engine
    	run = false;
    	
    	this.setPreferredSize(new Dimension(GE.X_DIM, GE.Y_DIM + 60));
		this.setLayout(new FlowLayout());
        this.setFocusable(false);    // do not Allow panel to get focus
    	this.setOpaque(false);
    }
    
    public void paint(Graphics g)
    {
    	super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect((GE.X_DIM-size)/2, (GE.Y_DIM-size)/2, size, size);
        
        if(run)
        {
        	if(size < GE.X_DIM-1 && size < GE.Y_DIM-1)
        		size++;
        	else
        		run=false;
        	
        	this.repaint();
        	
        	try{Thread.sleep(3);} catch(Exception e){ ; }
        	
        	//Toolkit.getDefaultToolkit().sync();
        }
    }

    public boolean startAnimation()
    {       
        run = true;
        size = 0;
        
        repaint();
        
        return run;
    }
    
    public boolean isRunning()
    {
    	return run;
    }
}