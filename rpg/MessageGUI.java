package rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
/**
 * MessageGUI class
 * 
 * Displays information to the user. Eg. Quest Info, Plot Development
 * @author Austin
 * @version 10/11/2012
 *
 */
@SuppressWarnings("serial")
public class MessageGUI extends JPanel implements KeyListener {

	private GameEngine GE; // link back to engine
	
	// gui parts
	private JPanel listPanel;
	private JScrollPane scrollPanel;
	
	/**
	 * Build the Message Panel
	 * @param tempEngine
	 */
	public MessageGUI(GameEngine tempEngine)
	{
		GE = tempEngine; // link back to engine
		
		this.setPreferredSize(new Dimension(GE.X_DIM, GE.Y_DIM + 30));
        this.addKeyListener(this);  // This class has its own key listeners.
        this.setFocusable(true);    // Allow panel to get focus
        
        listPanel = new JPanel(); // create place holders for quest messages
        listPanel.setLayout(new GridLayout(10,0));
        listPanel.setBackground(Color.GRAY);
        
		scrollPanel = new JScrollPane();
		scrollPanel.setViewportView(listPanel);
		scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.setPreferredSize(new Dimension( GE.X_DIM, GE.Y_DIM + 30));
		
		// pack together
		this.add(scrollPanel, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	public void addQuest(QuestGUI q)
	{
		// Take the Quest and add it to the GUI to display its information
		listPanel.add(((JPanel) q));
	}
	
	public void removeQuest(int id)
	{
		// remove quest from list panel
		listPanel.remove(id);
	}
	
	public void removeAllQuests()
	{
		listPanel.removeAll();
	}

	@Override
	public void keyPressed(KeyEvent k)
	{
		int key = k.getKeyCode();

        if (key == 73 ) // 'i'
        {
        	// shortcut to "Inventory Tab"
        	GE.viewInventoryPanel();
        }
        else if (key == 67 ) // 'c'
        {
        	// shortcut to "Combat Tab"
        	GE.viewCombatPanel();
        }
        else if (key == 77) // 'm'
        {
        	// shortcut to "Map Tab"
        	GE.viewMapPanel();
        }
	}

	@Override
	public void keyReleased(KeyEvent k) {
		// Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent k) {
		// Auto-generated method stub
		
	}
} // end of MessageGUI
