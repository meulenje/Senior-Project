package rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

/**
 * MessageGUI class
 * 
 * Displays information to the user. Eg. Quest Info, Plot Development
 * @author Austin
 * @version 10/8/2012
 *
 */
@SuppressWarnings("serial")
public class MessageGUI extends JPanel implements KeyListener {

	private GameEngine GE; // link back to engine
	
	private int numberOfQuests = 0;
	
	// gui parts
	private JPanel list;
	private JScrollPane scrollPanel;
	
	/**
	 * Build the Message Panel
	 * @param tempEngine
	 */
	public MessageGUI(GameEngine tempEngine)
	{
		GE = tempEngine; // link back to engine
		
		// create list of quests
		GE.quests = new ArrayList<Quest>();
		
		this.setPreferredSize(new Dimension(GE.X_DIM, GE.Y_DIM + 30));
        this.addKeyListener(this);  // This class has its own key listeners.
        this.setFocusable(true);    // Allow panel to get focus
        
        list = new JPanel(); // create place holders for quest messages
        list.setLayout(new GridLayout(10,0));
        list.setBackground(Color.GRAY);
        
		scrollPanel = new JScrollPane();
		scrollPanel.setViewportView(list);
		scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.setPreferredSize(new Dimension( GE.X_DIM, GE.Y_DIM + 30));
		
		// pack together
		this.add(scrollPanel, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	public void addQuest(Quest q)
	{
		// create a new panel with the contents of Quest
		
		JPanel temp = new JPanel();
		temp.setLayout(new BorderLayout());
		temp.setPreferredSize(new Dimension(GE.X_DIM - 60, 100));
		//TitledBorder border = new TitledBorder("Quest #"+(++numberOfQuests));
		//border.setTitleColor(Color.LIGHT_GRAY);
		//border.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		//temp.setBorder(border);
		temp.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		temp.setBackground(Color.WHITE);
		
		JPanel imagPanel = new JPanel();
		imagPanel.setLayout(new BorderLayout());
		imagPanel.setBackground(Color.LIGHT_GRAY);
		JLabel image = new JLabel("#"+(++numberOfQuests)+" - "+q.title);
		image.setIcon(q.image);
		image.setSize(GE.C_WIDTH, GE.C_HEIGHT);
		image.setLocation(0,0);
		imagPanel.add(image, BorderLayout.NORTH);
		temp.add(imagPanel, BorderLayout.NORTH);
		
		JTextArea text = new JTextArea(q.message);
		text.setEditable(false);
		text.setWrapStyleWord(true);
		text.setLineWrap(true);
		text.setFont(new Font("sansserif",Font.ITALIC,12));
		temp.add(text, BorderLayout.CENTER);
		
		list.add(temp); // update display of quests
		this.validate();
		this.repaint();
	}
	
	public void removeQuest()
	{
		// TODO	
	}

	@Override
	public void keyPressed(KeyEvent k)
	{
		int key = k.getKeyCode();

        if (key == 73 ) // 'i'
        {
        	// shortcut to "Inventory Tab"
        	GE.tabs.setSelectedIndex(1);
        }
        else if (key == 67 ) // 'c'
        {
        	// shortcut to "Combat Tab"
        	GE.tabs.setSelectedIndex(2);
        }
        else if (key == 77) // 'm'
        {
        	// shortcut to "Map Tab"
        	GE.tabs.setSelectedIndex(0);
        }
        else if (key == 40 || key == 83) // arrow down or 's'
		{
			
		}
		else if (key == 38 || key == 87) // arrow up or 'w'
		{
			
		}
		else if (key == 37 || key == 65) // arrow left or 'a'
		{
			
		}
		else if (key == 39 || key == 68) // arrow right or 'd'
		{
			
		}
	}

	@Override
	public void keyReleased(KeyEvent k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent k) {
		// TODO Auto-generated method stub
		
	}
} // end of MessageGUI
