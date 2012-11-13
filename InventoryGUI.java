package rpg;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
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
	
	private GameEngine GE; // link back to game engine
	
	//gui components:
	
	private JPanel centerPanel; //panel to group everything but character selector
	private JPanel rightCenterPanel; //panel to group paper doll and item display	
	private JPanel statsPanel; //panel for character stats, goes in character selector
	private JPanel itemDisplay; //panel for displaying item
	private JPanel paperDollPanel; //panel for characters equipped items 
	private JPanel equippedSlotPanel; //panel for equipped items
	
	//SplitPane of: items in your backpack, and equipped items of selected character
	private JScrollPane itemScrollPane;
	
	
	//toolbar for selecting character, also displays hp, magic, exp...
	private JToolBar toolBar;
	 
	//toolbar components
	private JButton nextCharacterButton; //next character selection 
	private JButton previousCharacterButton; //previous character selection 
	private JButton equiptButton; // button to equipt selected item    
    private JLabel characterPicture; //character picture and name  
    private JLabel equippedImage; //image of equipped item    
    private JLabel equippedLabel; // name of equipped item
    private JLabel equippedDescription; // description of equipped item  
    private JLabel itemPicture; //item picture and name
    private JProgressBar healthBar; 
    private JProgressBar magicBar;
    private JProgressBar expBar;
    private BoundedRangeModel healthBarModel;  //enables flexibility of health progress bar
    private BoundedRangeModel magicBarModel; 
    private BoundedRangeModel expBarModel;    
    
    //The IventoryGUI panel
	public InventoryGUI(GameEngine tempEngine)
    {
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
        //Selected Item display
        //==================
        
        //equipt button
        equiptButton = new JButton("EQUIPT");
        equiptButton.addActionListener(this);
        
        JPanel title = new JPanel();
        itemDisplay = new JPanel();    
        itemDisplay.setLayout(new BorderLayout());
        title.setLayout(new BorderLayout());
        title.add(new JLabel("SELECTED ITEM: ", JLabel.CENTER),BorderLayout.NORTH); 
        title.add(itemDisplay, BorderLayout.CENTER);
        itemPicture = new JLabel();
        itemDisplay.add(itemPicture, BorderLayout.WEST);
        itemDisplay.add(equiptButton, BorderLayout.EAST);
        itemDisplay.setBorder(BorderFactory.createLineBorder(Color.black));       
             
        
        //==================
        //add extra panels to align
        //==================
  
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());        
        rightCenterPanel = new JPanel();
        rightCenterPanel.setLayout(new BorderLayout());
        
        //==================
        //paperDollPanel
        //==================
        
        paperDollPanel = new JPanel();
        paperDollPanel.setLayout(new BorderLayout());
        equippedSlotPanel = new JPanel();
        equippedSlotPanel.setLayout(new BorderLayout());        
        equippedSlotPanel.setBorder(BorderFactory.createLineBorder(Color.black));        
        equippedLabel = new JLabel(); 
        equippedDescription = new JLabel();
        equippedImage = new JLabel();
        
        //temporary panels for alignment        
        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());
        JPanel left = new JPanel();
        left.setLayout(new GridLayout(2,1));
        JPanel right = new JPanel();
                        
        updateCharacterEquipped(GE.characters.get(0)); //update to default (first) character in party
                        
        //equipped area
        JLabel equipTitle = new JLabel("EQUIPPED:", JLabel.CENTER);        
        top.add(equipTitle, BorderLayout.NORTH);
        top.add(equippedSlotPanel, BorderLayout.CENTER);      
       	left.add(equippedLabel);
        left.add(equippedImage);        
        right.add(equippedDescription);
               
        equippedSlotPanel.add(left, BorderLayout.WEST);
        equippedSlotPanel.add(right, BorderLayout.CENTER);
        
        paperDollPanel.add(top, BorderLayout.SOUTH);
        
      //==================
        //item inventory list
        //==================
        
        itemScrollPane = new JScrollPane(GE.getItemList()); // add the items to the JScrollPane           
        updateBackpackLabel(GE.getItem(0));// this just selects the first item from the list
        updateCharacterStatsBars(GE.getSelectedCharacter());

                        
        //group together
        centerPanel.add(itemScrollPane, BorderLayout.WEST); 
        centerPanel.add(rightCenterPanel, BorderLayout.NORTH);
        rightCenterPanel.add(paperDollPanel,BorderLayout.NORTH);
        rightCenterPanel.add(title, BorderLayout.CENTER);
                       
        // apply sizing to components
	    toolBar.setPreferredSize(new Dimension(600,50));
        paperDollPanel.setPreferredSize(new Dimension(300,300));
        itemDisplay.setPreferredSize(new Dimension(600,50));
        itemScrollPane.setPreferredSize(new Dimension(200,200));   
        
        //Add components to the inventory panel    
                
        this.add(toolBar, BorderLayout.NORTH);         
        
      	this.add(centerPanel);
               	  
    }
	
	//make the navigation toolbar
	protected void makeToolbar(JToolBar toolBar) 
	{         	      
		//toolbar properties
	    toolBar.setLayout(new GridLayout(0,4));
	    toolBar.setFloatable(false);
	   	
        //make the button to select previous character.
        previousCharacterButton = new JButton("Previous"); 
        previousCharacterButton.addActionListener(this);
        
        //Make a label to hold the picture of the character
        characterPicture = new JLabel(null,null, JLabel.CENTER);
        characterPicture.setPreferredSize(new Dimension(50,50));        
        updateCharacterLabel(GE.getSelectedCharacter());
  
        //make button to select next character
        nextCharacterButton = new JButton("Next");
        nextCharacterButton.addActionListener(this);
        
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
               
        // magic bar for the player
        magicBar = new JProgressBar(0);   
        magicBar.setForeground(Color.BLUE);
        magicBar.setPreferredSize(new Dimension(15,25));
        magicBar.setBorderPainted(true);
              
        // experience bar for the player
        expBar = new JProgressBar(0);    
        expBar.setForeground(Color.GREEN);
        magicBar.setPreferredSize(new Dimension(15,25));
        expBar.setBorderPainted(true);
      
      
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
        
    //Brings up the selected image
    protected void updateBackpackLabel (Item item) 
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
    	ImageIcon icon = entity.getImage();
    	characterPicture.setIcon(icon);
    	characterPicture.setText(entity.getName());
    	if  (icon != null) {
            
        } else {
            characterPicture.setText("Image not found");
        }
    }
    
    //update the equipped slot
    protected void updateCharacterEquipped(Entity entity)
    {
    	if(entity.getEquipped() != null)
    	{
    		equippedLabel.setText(entity.getEquipped().getItemName());
        	equippedDescription.setText(entity.getEquipped().getItemDescription());
        	equippedImage.setIcon(entity.getEquipped().image);
    	}
    	else
    	{
    		equippedLabel.setText("");
        	equippedDescription.setText("");
        	equippedImage.setIcon(GE.Empty);
    	}    	
    }
    

   //updates the stats panel
    protected void updateCharacterStatsBars(Entity entity)
    {
    	healthBarModel = healthBar.getModel();
    	healthBarModel.setMaximum(entity.getMaxHealth());
    	expBarModel = expBar.getModel();
    	expBarModel.setMaximum(entity.getMaxHealth());
    	magicBarModel = magicBar.getModel();
    	magicBarModel.setMaximum(entity.getMaxMana());
	 	healthBarModel.setValue(GE.getSelectedCharacter().getCurrentHealth());
    	expBar.setValue(GE.getSelectedCharacter().getCurrentHealth()); //TODO: magic and expierence progress bars only use health
	 	magicBar.setValue(GE.getSelectedCharacter().getCurrentMana()); 

    }
   
    
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		//if previous character
		if(arg0.getSource() == previousCharacterButton)
		{
			GE.navigateCharacter("previous");
			updateCharacterLabel(GE.getSelectedCharacter());
			updateCharacterStatsBars(GE.getSelectedCharacter());
			updateCharacterEquipped(GE.getSelectedCharacter());
		}
		
		//if next character
		if(arg0.getSource() == nextCharacterButton)
		{
			GE.navigateCharacter("next");
			updateCharacterLabel(GE.getSelectedCharacter());
			updateCharacterStatsBars(GE.getSelectedCharacter());
			updateCharacterEquipped(GE.getSelectedCharacter());
		}
		
		//equipt button is pressed
		if(arg0.getSource() == equiptButton)
		{
			GE.getSelectedCharacter().setEquippedItem(GE.getSelectedItem());
			updateCharacterEquipped(GE.getSelectedCharacter());
		}
	}
}
