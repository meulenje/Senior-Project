package rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class CombatObject extends JPanel implements ActionListener {

	private GameEngine GE;
	
	protected Entity entity;
	private int objectWidth = 50;
	private int objectHeight = 50;
	
	// gui parts
	private JLabel imageLabel;
	protected JProgressBar healthBar;
	private JLabel name;
	private JPanel imagePanel;
    protected ImageIcon backgroundImage; // grass, dirt, floor, etc...
    protected JLabel background; // container to hold terrainID image
    protected JLabel foreground; // container to hold entityID image
	
	/**
	 * Constructor for CombatObject
	 */
	public CombatObject(GameEngine tempEngine, Entity entity, ImageIcon bg)
	{
		GE = tempEngine;
		this.entity = entity;
		
		// build the GUI
		this.setLayout(new BorderLayout());
		this.setOpaque(false); // transparent background

    	// name tag
    	name = new JLabel(entity.getName(), JLabel.CENTER);

    	// health bar for the player
        healthBar = new JProgressBar(0);
        healthBar.setForeground(Color.RED);
        healthBar.setBackground(Color.WHITE);
        healthBar.setBorderPainted(true);
        healthBar.setMaximumSize(new Dimension(GE.C_WIDTH, 15));
        setHealthBar();
    	
    	// Lay a background, foreground, and highground JLabel
    	background = new JLabel();
    	background.setSize(GE.C_WIDTH, GE.C_HEIGHT);
    	background.setLocation(0,0);
    	foreground = new JLabel();
    	foreground.setSize(GE.C_WIDTH, GE.C_HEIGHT);
    	foreground.setLocation(0,0);
    	
    	// image with layers
    	JLayeredPane layer = new JLayeredPane();
    	layer.add(background, JLayeredPane.DEFAULT_LAYER); // 0
    	layer.add(foreground, JLayeredPane.PALETTE_LAYER); // 100 (above 0)
    	layer.setLayout(new BorderLayout());
    	layer.setOpaque(false); // non-transparent
    	layer.setSize(new Dimension(GE.C_WIDTH, GE.C_HEIGHT));

    	imagePanel = new JPanel();
    	imagePanel.setLayout(new BorderLayout());
    	imagePanel.setOpaque(false); // transparent background
    	imagePanel.setPreferredSize(new Dimension(GE.C_WIDTH, GE.C_HEIGHT + 15));
    	imagePanel.add(layer, BorderLayout.CENTER);
    	imagePanel.add(healthBar, BorderLayout.SOUTH);
    	
    	setForeground();
    	setBackground(bg);    	
        
    	
    	// pack all together into wrapping JPanel
        JPanel wrapper = new JPanel();
        wrapper.setOpaque(false);
        wrapper.setLayout(new BorderLayout());
        wrapper.setPreferredSize(new Dimension(objectWidth,objectHeight));
		wrapper.add(name, BorderLayout.NORTH);
		wrapper.add(Box.createRigidArea(new Dimension(GE.C_WIDTH,20)), BorderLayout.WEST);
		wrapper.add(imagePanel, BorderLayout.CENTER);
		wrapper.add(Box.createRigidArea(new Dimension(GE.C_WIDTH,20)), BorderLayout.EAST);
		wrapper.add(Box.createRigidArea(new Dimension(objectWidth,GE.C_HEIGHT)), BorderLayout.SOUTH);
		
		// set wrapper to main panel
		this.add(wrapper, BorderLayout.CENTER);
		this.add(Box.createGlue(), BorderLayout.SOUTH);
		setCurrentTurn(false);
	}
	
	/**
	 * Highlights the Object with a GREEN box when flag is true.
	 * @param flag
	 */
	public void setCurrentTurn(boolean flag)
	{
		if(flag)
			imagePanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
		else
			imagePanel.setBorder(null);
	}
	
	public void setBackground(ImageIcon bg)
	{
		backgroundImage = bg;
		background.setIcon(backgroundImage);
	}
	
	public void setForeground()
	{
		foreground.setIcon(entity.getImage());
	}
	
	/**
	 * Updates the CombatObject's health bar to the current % health
	 * of the entity it holds.
	 */
	public void setHealthBar()
	{
		int healthPercent = (int) (((double)entity.getCurrentHealth() / (double)entity.getMaxHealth()) * 100);
		healthBar.setValue(healthPercent);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

} // end of CombatObject
