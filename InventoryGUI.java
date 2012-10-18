package rpg;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Inventory GUI class
 * 
 * An extension of JPanel used by GameEngine to display the 
 * characters inventory in your party
 * 
 * @author Corey Jones
 * @version 10/00/2012
 *
 */

public class InventoryGUI extends JPanel implements ActionListener , ListSelectionListener{
	
	private static final long serialVersionUID = 1L;
	
	private InventoryEngine IE;// link to inventory engine
	private GameEngine GE; // link back to game engine
	
	//gui components:
	
	private JPanel characterPanel; //panel for character selection
	private JPanel statsPanel; //panel for character stats
	
	//SplitPane of: items in your backpack, and the items' description
	private JSplitPane inventorySplitPane;
	private JScrollPane itemScrollPane;
	private JScrollPane descriptionScrollPane;
	
	//toolbar for selecting character
	private JToolBar toolBar;
	 
	//toolbar components
	private JButton nextCharacterButton; //next character selection 
	private JButton previousCharacterButton; //previous character selection 
    static final private String PREVIOUS = "previous";	//button action names
    static final private String NEXT = "next";    
    private JLabel characterPicture; //character picture and name  
    private JTextField characterName = new JTextField();    
    private JLabel itemPicture; //item picture and name
    private JProgressBar healthBar; 
    private JProgressBar magicBar;
    private JProgressBar expBar;
    
    //The IventoryGUI panel
	public InventoryGUI(GameEngine tempEngine)
    {
		// link to inventory engine
		IE = new InventoryEngine(this);
		
    	// link back to JFrame
    	GE = tempEngine;
    	
    	// The inventory panel
    	this.setLayout(new GridLayout(4,0));  
    	this.setPreferredSize(new Dimension( GE.G_X_DIM , GE.G_Y_DIM )); //set to default grid engine panel size
    	this.setBackground(Color.GRAY);
    	
    	//==========================
    	//character selection part
    	//==========================
    	
    	//make the toolbar
        toolBar = new JToolBar();
        // Todo: set toolbar not draggable
        makeToolbar(toolBar);                      
               
        //==================
        //inventory part
        //==================
        
        itemScrollPane = new JScrollPane(IE.getItemList()); // add the items to the JScrollPane
        
        itemPicture = new JLabel(); //picture of the item selected
        itemPicture.setFont(itemPicture.getFont().deriveFont(Font.BOLD));
        itemPicture.setHorizontalAlignment(JLabel.CENTER);
        itemPicture.setText(IE.getItemDescription());
        itemPicture.setVerticalTextPosition(JLabel.BOTTOM);
        itemPicture.setHorizontalTextPosition(JLabel.CENTER);
        itemPicture.setIconTextGap(35);
                
        descriptionScrollPane = new JScrollPane(itemPicture); //Description of item area

        //Create a split pane with the two scroll panes in it.
        inventorySplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                   itemScrollPane, descriptionScrollPane);
        inventorySplitPane.setOneTouchExpandable(true);
        inventorySplitPane.setDividerLocation(150);
               

        //Provide minimum sizes for the two components in the split pane.
        Dimension minimumSize = new Dimension(200, 300);
        descriptionScrollPane.setMinimumSize(minimumSize);
        descriptionScrollPane.setMinimumSize(minimumSize);
        inventorySplitPane.setResizeWeight(1.0);

        //Provide a preferred size for the split pane.
        inventorySplitPane.setPreferredSize(new Dimension(200, 200));
        //updateBackpackLabel(IE.);

        
        //Add components to the inventory panel
        this.add(toolBar);
        this.add(inventorySplitPane);
        
        // TODO add the stats bar and abilities of character using the stats panel and character panel        
        
    	
    }
	//make the navigation toolbar
	protected void makeToolbar(JToolBar toolBar) 
	{         	      
		//toolbar properties
	    toolBar.setLayout(new BorderLayout());
	    toolBar.setPreferredSize(new Dimension(100,100));
	   	   	    
        //make the button to select previous character.
        previousCharacterButton = makeButton("Previous",PREVIOUS, "Previous Character", "Previous");       
        
        //Make a label to hold the picture of the character
        characterPicture = new JLabel(null,null, JLabel.CENTER);
        characterPicture.setPreferredSize(new Dimension(50,50));        
        updateCharacterLabel(IE.getSelectedCharacter());
  
        //make button to select next character
        nextCharacterButton = makeButton("Next", NEXT, "Next Character", "Next");
        
        //make the stats area
        
        //JLabels for stats
        JLabel healthLabel = new JLabel("HP:"); // health
        healthLabel.setForeground(Color.RED);
        JLabel expLabel = new JLabel("EXP:"); // experience
        expLabel.setForeground(Color.GREEN);
        JLabel magicLabel = new JLabel("Magic:"); //magic
        magicLabel.setForeground(Color.BLUE);
                      
        // health bar for the player
        healthBar = new JProgressBar(0);
        healthBar.setForeground(Color.RED);
        healthBar.setBorderPainted(true);       
        healthBar.setValue(100); // just making it an arbitrary value
       
        // magic bar for the player
        magicBar = new JProgressBar(0);
        magicBar.setForeground(Color.BLUE);
        magicBar.setBorderPainted(true);
        magicBar.setValue(65);
       
        // experience bar for the player
        expBar = new JProgressBar(0);
        expBar.setForeground(Color.GREEN);
        expBar.setBorderPainted(true);
        expBar.setValue(15);

      
        // Build Health Bar Area
        characterPanel = new JPanel(new GridLayout(0,5));
        statsPanel= new JPanel(new BorderLayout());
        statsPanel.add(healthLabel, BorderLayout.CENTER);
        statsPanel.add(healthBar, BorderLayout.CENTER);
        statsPanel.add(new JLabel(""));
        statsPanel.add(magicLabel);
        statsPanel.add(magicBar);
        statsPanel.add(new JLabel(""));
        statsPanel.add(expLabel, BorderLayout.CENTER);
        statsPanel.add(expBar, BorderLayout.CENTER);                 
        statsPanel.setPreferredSize(new Dimension(35,35));
        statsPanel.setBackground(Color.BLACK);

        
        //add components to toolbar
        toolBar.add(characterName, BorderLayout.NORTH);
        toolBar.add(previousCharacterButton, BorderLayout.WEST);
        toolBar.add(nextCharacterButton, BorderLayout.EAST);
        toolBar.add(characterPicture, BorderLayout.SOUTH);
//        toolBar.add(characterPanel, BorderLayout.SOUTH);
//        characterPanel.add(previousCharacterButton);  
//        characterPanel.add(statsPanel);
//        characterPanel.add(characterPicture);
//        characterPanel.add(nextCharacterButton);
        
    }
    // method to make buttons
    protected JButton makeButton(String imageName,
								            String actionCommand,
								            String toolTipText,
								            String altText) 
    {
		//Look for the location of the image.
		String imgLocation = ""
		+ imageName
		+ ".jpg";
		URL imageURL = InventoryGUI.class.getResource(imgLocation);
		
		//Create and initialize the button.
		JButton button = new JButton();
		button.setActionCommand(actionCommand);
		button.setToolTipText(toolTipText);
		button.addActionListener(this);
		System.out.println(imageURL);
		
		//if image found
		if (imageURL != null)
		{    
			button.setIcon(new ImageIcon(imageURL, altText));
		}
		//else no image found
		else
		{                                     
			button.setText(altText);
			System.err.println("Resource not found: "
					+ imgLocation);
		}
		
		return button;
	}

 
    //Listens to the list whether selected item is changed
    public void valueChanged(ListSelectionEvent arg0) 
    {
        JList list = (JList)arg0.getSource();
        updateBackpackLabel(IE.getSelectedItem(list.getSelectedIndex()));
    }
    
    //Brings up the selected image
    protected void updateBackpackLabel (String name) 
    {
        ImageIcon icon = createImageIcon( name + ".png");
        itemPicture.setIcon(icon);
        if  (icon != null) {
                       
        } else {
            itemPicture.setText("Image not found");
        }
    }
	// Label that shows the item name
    protected void updateCharacterLabel (String name)
    {
    	characterName.setEditable(false);
    	characterName.setHorizontalAlignment(JTextField.CENTER);      	
    	characterName.setText(name);    
    	//characterName.setFont(itemPicture.getFont().deriveFont(Font.BOLD));
    	ImageIcon icon = createImageIcon (name + ".png");
    	characterPicture.setIcon(icon);
    	if  (icon != null) {
            
        } else {
            characterPicture.setText("Image not found");
        }
    }

      
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) 
    {
       URL imgURL = InventoryGUI.class.getResource(path);
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
		String cmd = arg0.getActionCommand();
		
		if(PREVIOUS.equals(cmd))
		{
			IE.navigateCharacter("previous");
			updateCharacterLabel(IE.getSelectedCharacter());
		}
		
		if(NEXT.equals(cmd))
		{
			IE.navigateCharacter("next");
			updateCharacterLabel(IE.getSelectedCharacter());
		}
			
		
	}	

}
