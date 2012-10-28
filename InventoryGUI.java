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
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

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

public class InventoryGUI extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private InventoryEngine IE;// link to inventory engine
	private GameEngine GE; // link back to game engine
	
	//gui components:
	
	private JPanel centerPanel; //panel to group everything but character selector
	private JPanel rightCenterPanel; //panel to group paper doll and item display	
	private JPanel statsPanel; //panel for character stats, goes in character selector
	private JPanel itemDisplay; //panel for displaying item
	private JPanel paperDollPanel; //panel for characters equipped items 
	
	//SplitPane of: items in your backpack, and equipped items of selected character
	private JScrollPane itemScrollPane;
	
	
	//toolbar for selecting character, also displays hp, magic, exp...
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
    	this.setLayout(new BorderLayout());  
    	this.setPreferredSize(new Dimension( GE.G_X_DIM , GE.G_Y_DIM )); //set to default grid engine panel size
    	this.setBackground(Color.GRAY);
    	
    	//==========================
    	//character selection 
    	//==========================
    	
    	//make the toolbar
        toolBar = new JToolBar();
        // Todo: set toolbar not draggable
        makeToolbar(toolBar);       
        
      //==================
        //item description 
        //==================
        itemDisplay = new JPanel();        
        itemPicture = new JLabel(); 
        itemDisplay.add(itemPicture);
               
        //==================
        //item inventory list
        //==================
        
        itemScrollPane = new JScrollPane(IE.getItemList()); // add the items to the JScrollPane           
        updateBackpackLabel(IE.getSelectedItem(0));// this just selects the first item from the list

        //==================
        //
        //==================
        
        paperDollPanel = new JPanel();      
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());        
        rightCenterPanel = new JPanel();
        rightCenterPanel.setLayout(new BorderLayout());
        
        //group together
        centerPanel.add(itemScrollPane, BorderLayout.WEST); 
        centerPanel.add(rightCenterPanel, BorderLayout.CENTER);
        rightCenterPanel.add(paperDollPanel,BorderLayout.CENTER);
        rightCenterPanel.add(itemDisplay, BorderLayout.NORTH);
        
               
        // apply sizing to components
	    toolBar.setPreferredSize(new Dimension(600,50));
        paperDollPanel.setPreferredSize(new Dimension(300,300));
        itemDisplay.setPreferredSize(new Dimension(600,50));
        itemScrollPane.setPreferredSize(new Dimension(200,200));   
        
        //Add components to the inventory panel    
                
        this.add(toolBar, BorderLayout.NORTH);         
        
      	this.add(centerPanel, BorderLayout.CENTER);
           
        
        // TODO add the stats bar and abilities of character using the stats panel and character panel        
            	
    }
	
	//make the navigation toolbar
	protected void makeToolbar(JToolBar toolBar) 
	{         	      
		//toolbar properties
	    toolBar.setLayout(new GridLayout(0,4));
	   	
        //make the button to select previous character.
        previousCharacterButton = makeButton("Previous",PREVIOUS, "Previous Character", "Previous");       
        
        //Make a label to hold the picture of the character
        characterPicture = new JLabel(null,null, JLabel.CENTER);
        characterPicture.setPreferredSize(new Dimension(50,50));        
        updateCharacterLabel(GE.getSelectedCharacter());
  
        //make button to select next character
        nextCharacterButton = makeButton("Next", NEXT, "Next Character", "Next");
        
        //make the stats area
        JLabel healthLabel = new JLabel("HP:"); // health
        healthLabel.setForeground(Color.RED);
        JLabel expLabel = new JLabel("EXP:"); // experience
        expLabel.setForeground(Color.GREEN);
        JLabel magicLabel = new JLabel("Magic:"); //magic
        magicLabel.setForeground(Color.BLUE);
                      
        // health bar for the player
        healthBar = new JProgressBar(0);
        healthBar.setForeground(Color.RED);
        healthBar.setSize(new Dimension(15,25));
        healthBar.setBorderPainted(true);       
        healthBar.setValue(100); // just making it an arbitrary value
       
        // magic bar for the player
        magicBar = new JProgressBar(0);
        magicBar.setForeground(Color.BLUE);
        magicBar.setPreferredSize(new Dimension(15,25));
        magicBar.setBorderPainted(true);
        magicBar.setValue(65);
       
        // experience bar for the player
        expBar = new JProgressBar(0);
        expBar.setForeground(Color.GREEN);
        magicBar.setPreferredSize(new Dimension(15,25));
        expBar.setBorderPainted(true);
        expBar.setValue(95);

      
        //add components to stats panel
        statsPanel = new JPanel(new GridLayout(3,2));
        statsPanel.setPreferredSize(new Dimension(45,75));
        statsPanel.add(healthLabel);
        statsPanel.add(healthBar);
        statsPanel.add(magicLabel);
        statsPanel.add(magicBar); 
        statsPanel.add(expLabel);
        statsPanel.add(expBar);
        
        //add components to toolbar
        toolBar.add(previousCharacterButton);        
        toolBar.add(characterPicture);
        toolBar.add(statsPanel);
        toolBar.add(nextCharacterButton);        
        
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

     
    //Brings up the selected image
    protected void updateBackpackLabel (item item) 
    {
        ImageIcon icon = item.getItemIcon();
        itemPicture.setIcon(icon);
        itemPicture.setText(item.getItemDescription());
        if  (icon != null) {
                       
        } else {
            itemPicture.setText("Image not found");
        }
    }
	// Label that shows the item name
    protected void updateCharacterLabel (Entity entity)
    {
    	characterName.setEditable(false);
    	characterName.setHorizontalAlignment(JTextField.CENTER);      	
    	characterName.setText(entity.getName());
    	ImageIcon icon = entity.getImage();
    	characterPicture.setIcon(icon);
    	//characterPicture.setText(name);
    	if  (icon != null) {
            
        } else {
            characterPicture.setText("Image not found");
        }
    }

      
    /** Returns an ImageIcon, or null if the path was invalid. */
    

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		String cmd = arg0.getActionCommand();
		
		if(PREVIOUS.equals(cmd))
		{
			GE.navigateCharacter("previous");
			updateCharacterLabel(GE.getSelectedCharacter());
		}
		
		if(NEXT.equals(cmd))
		{
			GE.navigateCharacter("next");
			updateCharacterLabel(GE.getSelectedCharacter());
		}
			
		
	}	

}
