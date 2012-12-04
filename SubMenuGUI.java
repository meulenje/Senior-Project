package rpg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * SubMenuGUI class
 * 
 * A simple menu for the in-game user.
 * @author Austin
 *
 */
public class SubMenuGUI extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private GameEngine GE;
	
	// gui parts
	private JButton close;
	private JButton menuItems;
	private JButton menuStats;
	private JButton menuQuests;
	private JButton menuSave;
	private JButton menuQuit;
	
	public SubMenuGUI(GameEngine tempEngine)
	{
		GE = tempEngine;
		
		// make the in-game menu screen
		this.setLayout(new BorderLayout());
		this.setBackground(GE.foregroundColor);
		this.setPreferredSize(new Dimension(200, 400));
		
		// buttons for the menu
		menuItems = new JButton("Items",GE.InventoryIcon);
		menuStats = new JButton("Stats",GE.ListIcon);
		menuQuests = new JButton("Quests",GE.MailIcon);
		menuSave = new JButton("Save Game",GE.DrawIcon);
		menuQuit = new JButton("Quit Game",GE.XSpace);
		
		menuItems.addActionListener(this);
		menuStats.addActionListener(this);
		menuQuests.addActionListener(this);
		menuSave.addActionListener(this);
		menuQuit.addActionListener(this);
		
		JPanel grid = new JPanel(new GridLayout(6,0,0,10));
		grid.setOpaque(false);
		JLabel menu = new JLabel("Menu", JLabel.CENTER);
		menu.setFont(new Font("sansserif",Font.BOLD,16));
		menu.setForeground(GE.highlightColor);
		grid.add(menu);
		grid.add(menuItems);
		grid.add(menuStats);
		grid.add(menuQuests);
		grid.add(menuSave);
		grid.add(menuQuit);
		
		this.add(Box.createHorizontalStrut(15), BorderLayout.EAST);
		this.add(Box.createHorizontalStrut(15), BorderLayout.WEST);
		this.add(grid, BorderLayout.CENTER);
		this.add(Box.createVerticalStrut(15), BorderLayout.NORTH);
		this.add(Box.createVerticalStrut(15), BorderLayout.SOUTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent a) {
		Object src = a.getSource();
		
		if(src == menuItems)
		{
			GE.viewInventoryPanel();
		}
		else if(src == menuStats)
		{
			GE.viewStatsPanel();
		}
		else if(src == menuQuests)
		{
			GE.viewQuestPanel();
		}
		else if(src == menuSave)
		{
			GE.saveGame();
		}
		else if(src == menuQuit)
		{
			GE.endGame();
		}
		else if(src == close)
		{
			GE.viewMapPanel();
		}
	}

}
