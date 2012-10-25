package rpg;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class InventoryGUI extends JPanel implements ActionListener, ListSelectionListener{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GameEngine GE; // link back to Engine
	
	// gui part
	private JPanel backpackPanel;
	private JPanel statsPanel;
	private JProgressBar healthBar;
    private JProgressBar magicBar;
    private JProgressBar expBar;  
    private JTextField nameField;
    private JLabel itemPicture;
    private JLabel inventoryPicture;
    //private ImageIcon inventoryMenu = new ImageIcon("inventory menu.png");
    private JList<Object> items;
    private JSplitPane inventoryPane;
    private String[] itemNames = { "Add", "Dirt", "Grasshole", "GrassRock", "Player","PlayerBack","Minus","Gem2", "NewPointArrow"};
   
   
    
    //stats bar values
    protected int health;
    protected int magic;
    protected int experience;
        
    public InventoryGUI(GameEngine tempEngine)
    {
    	// link back to JFramel
    	GE = tempEngine;
    	this.setLayout(new BorderLayout());
    	
        //temporarily setting health, magic and experience:
        health = 100;
        magic = 20;
        experience = 0;
        
        //JTextFields and JLabels
        nameField = new JTextField("Louie");
        nameField.setSize(20, 10);
        JLabel healthLabel = new JLabel("HP:");
        healthLabel.setForeground(Color.RED);
        JLabel expLabel = new JLabel("EXP:");
        expLabel.setForeground(Color.GREEN);
        JLabel magicLabel = new JLabel("Magic:");
        magicLabel.setForeground(Color.BLUE);
        
               
        // health bar for the player
        healthBar = new JProgressBar(0);
        healthBar.setForeground(Color.RED);
        healthBar.setBorderPainted(true);       
        healthBar.setValue(health);
        
        // magic bar for the player
        magicBar = new JProgressBar(0);
        magicBar.setForeground(Color.BLUE);
        magicBar.setBorderPainted(true);
        magicBar.setValue(magic);
        
        // experience bar for the player
        expBar = new JProgressBar(0);
        expBar.setForeground(Color.GREEN);
        expBar.setBorderPainted(true);
        expBar.setValue(experience);
        
        // Build Health Bar Area
        statsPanel = new JPanel();
        statsPanel.add(healthLabel);
        statsPanel.add(healthBar);
        statsPanel.add(new JLabel(""));
        statsPanel.add(magicLabel);
        statsPanel.add(magicBar);
        statsPanel.add(new JLabel(""));
        statsPanel.add(expLabel);
        statsPanel.add(expBar);  
        inventoryPicture = new JLabel();
        inventoryPicture.setVisible(true);
        //inventoryPicture.setIcon(inventoryMenu);
        statsPanel.add(inventoryPicture); 
       
        statsPanel.setBackground(Color.BLACK);
        
        JLabel backpackTitle = new JLabel("BackPack");
        backpackTitle.setHorizontalAlignment(JLabel.CENTER);           
         
        //Create the list of images and put it in a scroll pane.        
    	items = new JList<Object>(itemNames);
        items.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        items.setSelectedIndex(0);
        items.addListSelectionListener((ListSelectionListener) this);        
       
        JScrollPane listScrollPane = new JScrollPane(items);
        itemPicture = new JLabel();
        itemPicture.setFont(itemPicture.getFont().deriveFont(Font.BOLD));
        itemPicture.setHorizontalAlignment(JLabel.CENTER);
        itemPicture.setText("Description");
        itemPicture.setVerticalTextPosition(JLabel.BOTTOM);
        itemPicture.setHorizontalTextPosition(JLabel.CENTER);
        itemPicture.setIconTextGap(35);
                
        JScrollPane pictureScrollPane = new JScrollPane(itemPicture);

        //Create a split pane with the two scroll panes in it.
        inventoryPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                   listScrollPane, pictureScrollPane);
        inventoryPane.setOneTouchExpandable(true);
        inventoryPane.setDividerLocation(150);
               

        //Provide minimum sizes for the two components in the split pane.
        Dimension minimumSize = new Dimension(1000, 1000);
        listScrollPane.setMinimumSize(minimumSize);
        pictureScrollPane.setMinimumSize(minimumSize);
        inventoryPane.setResizeWeight(1.0);

        //Provide a preferred size for the split pane.
        inventoryPane.setPreferredSize(new Dimension(400, 200));
        updateLabel(itemNames[items.getSelectedIndex()]);
        
            
        // Build the backpack list
        backpackPanel = new JPanel();  
        backpackPanel.setLayout(new BorderLayout());   
        backpackPanel.add(backpackTitle, BorderLayout.PAGE_START);
        backpackPanel.add(inventoryPane, BorderLayout.CENTER);
        backpackPanel.setBackground(Color.GRAY);
                  
     // group the map pieces together
        this.add(statsPanel, BorderLayout.WEST);
        this.add(backpackPanel, BorderLayout.CENTER);
        backpackPanel.setFocusable(true);   
        
    }
    
    //Listens to the list
    public void valueChanged(ListSelectionEvent e) 
    {
        JList list = (JList)e.getSource();
        updateLabel(itemNames[list.getSelectedIndex()]);
    }
    
    //Brings up the selected image
    protected void updateLabel (String name) 
    {
        ImageIcon icon = createImageIcon( name + ".png");
        itemPicture.setIcon(icon);
        if  (icon != null) {
                       
        } else {
            itemPicture.setText("Image not found");
        }
    }

    //Used by SplitPaneDemo2
    public JList getImageList() 
    {
        return items;
    }

    public JSplitPane getSplitPane()
    {
        return inventoryPane;
    }

   
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) 
    {
       java.net.URL imgURL = InventoryGUI.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource() != null)
		{
			System.out.println("up");
		}
		// TODO Auto-generated method stub
		
	}

	

}
