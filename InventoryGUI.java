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
	private JPanel itemSelected; // which item is selected
	private JPanel buttonGroup; //group use and equip buttons
	private JPanel rightCenterPanel; //panel to group paper doll and item display	
	private JPanel statsPanel; //panel for character stats, goes in character selector
	private JPanel itemDisplay; //which item is selectied
	private JPanel itemStatsPanel; // display item stats
	private JPanel infoPanel; // item info panel 
	private JPanel characterInfoPanel; // panel for character info
	private JPanel characterStatsPanel; // panel to hold the stats of the character
	private JPanel equippedSlotPanel; //panel for equipped items
	
	//SplitPane of: items in your backpack, and equipped items of selected character
	private JScrollPane itemScrollPane;
	
	
	//toolbar for selecting character, also displays hp, magic, exp...
	private JToolBar toolBar;
	 
	//toolbar components
	private JButton nextCharacterButton; //next character selection 
	private JButton previousCharacterButton; //previous character selection 
	private JButton equipButton; // button to equipt selected item  
	private JButton unequipButton; // button to unequip item
	private JButton useButton; //button for consumables
    private JLabel characterPicture; //character picture and name  
    private JLabel equippedImage; //image of equipped item    
    private JLabel equippedLabel; // name of equipped item
    private JLabel equippedDescription; // description of equipped item  
    private JLabel itemPicture; //item picture and name
    private JLabel itemTitle; //title of item stats area
    private JLabel itemAttack ;
    private JLabel itemDefense;
    private JLabel itemCurrentHP;
    private JLabel itemMaxHP ;	
    private JLabel itemCurrentMana;
    private JLabel itemMaxMana;
    private JLabel itemSpeed;    
    private JLabel characterTitle; // title of character stats area
    private JLabel characterAttack ;
    private JLabel characterDefense;
    private JLabel characterCurrentHP;
    private JLabel characterMaxHP ;	
    private JLabel characterCurrentMana;
    private JLabel characterMaxMana;
    private JLabel characterSpeed;
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
        
        //item equip
        equipButton = new JButton("EQUIP");
        unequipButton = new JButton("UNEQUIP");
        useButton = new JButton("USE");
        equipButton.addActionListener(this);
        unequipButton.addActionListener(this);
        useButton.addActionListener(this);
        
        JLabel itemEquipTitle = new JLabel("SELECTED:", JLabel.CENTER);
        itemSelected = new JPanel();
        itemSelected.setLayout(new BorderLayout());
        itemDisplay = new JPanel();    
        itemDisplay.setLayout(new BorderLayout());        
        itemSelected.add(itemEquipTitle, BorderLayout.NORTH); 
        itemSelected.add(itemDisplay, BorderLayout.CENTER);
        itemPicture = new JLabel();
        itemDisplay.add(itemPicture, BorderLayout.CENTER);
        buttonGroup = new JPanel(); 
        buttonGroup.setLayout(new GridLayout());
        buttonGroup.add(equipButton);
        buttonGroup.add(useButton);        
        itemDisplay.add(buttonGroup,BorderLayout.EAST);
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
        
        infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        characterInfoPanel = new JPanel();
        characterInfoPanel.setLayout(new BorderLayout());
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
        top.add(unequipButton,BorderLayout.EAST);
       	left.add(equippedLabel);
        left.add(equippedImage);        
        right.add(equippedDescription);
               
        equippedSlotPanel.add(left, BorderLayout.WEST);
        equippedSlotPanel.add(right, BorderLayout.CENTER);
        
        //==================
        //item stats panel
        //==================
        itemStatsPanel = new JPanel();
        itemStatsPanel.setLayout(new GridLayout(8,1));
        
        itemTitle = new JLabel("-ITEM STATS-");
        itemAttack = new JLabel();
        itemDefense = new JLabel();
        itemCurrentHP = new JLabel();
        itemMaxHP = new JLabel();	
        itemCurrentMana= new JLabel();
        itemMaxMana = new JLabel();
        itemSpeed = new JLabel();
        itemStatsPanel.add(itemTitle);
        itemStatsPanel.add(itemAttack);
        itemStatsPanel.add(itemDefense);
        itemStatsPanel.add(itemCurrentHP);
        itemStatsPanel.add(itemCurrentMana);
        itemStatsPanel.add(itemSpeed);
        itemStatsPanel.add(itemMaxHP);       
        itemStatsPanel.add(itemMaxMana);
                
        infoPanel.add(top,BorderLayout.NORTH);
        infoPanel.add(centerPanel, BorderLayout.SOUTH);
        infoPanel.add(itemStatsPanel, BorderLayout.WEST);
        infoPanel.add(characterInfoPanel, BorderLayout.EAST);
        
      //==================
        //character stats panel
        //==================
        
        characterStatsPanel = new JPanel();
        characterStatsPanel.setLayout(new GridLayout(8,1));
        
        characterTitle = new JLabel("-CHARACTER STATS-");
        characterAttack = new JLabel();
        characterDefense = new JLabel();
        characterCurrentHP = new JLabel();
        characterMaxHP = new JLabel();	
        characterCurrentMana= new JLabel();
        characterMaxMana = new JLabel();
        characterSpeed = new JLabel();
        characterStatsPanel.add(characterTitle);
        characterStatsPanel.add(characterAttack);
        characterStatsPanel.add(characterDefense);
        characterStatsPanel.add(characterCurrentHP);
        characterStatsPanel.add(characterCurrentMana);
        characterStatsPanel.add(characterSpeed);
        characterStatsPanel.add(characterMaxHP);       
        characterStatsPanel.add(characterMaxMana);
        
        characterInfoPanel.add(characterStatsPanel, BorderLayout.CENTER);
      //==================
        //item inventory list
        //==================
        
        itemScrollPane = new JScrollPane(GE.getItemList()); // add the JList that displays items         
        itemScrollPane.requestFocus(); // enable arrow keys to navigate items     
        update();

                        
        //group together        
        centerPanel.add(rightCenterPanel, BorderLayout.CENTER);        
        rightCenterPanel.add(itemSelected, BorderLayout.CENTER);
                       
        // apply sizing to components
	    toolBar.setPreferredSize(new Dimension(600,50));
        infoPanel.setPreferredSize(new Dimension(300,300));
        itemDisplay.setPreferredSize(new Dimension(600,50));
        itemScrollPane.setPreferredSize(new Dimension(200,200));   
        
        //Add components to the inventory panel    
                
        this.add(toolBar, BorderLayout.NORTH);         
        this.add(itemScrollPane, BorderLayout.WEST);
      	this.add(infoPanel, BorderLayout.CENTER);
               	  
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
	
	//update everything
	protected void update()
	{
				
		updateCharacterLabel(GE.getSelectedCharacter());
		updateCharacterStatsBars(GE.getSelectedCharacter());
		updateCharacterEquipped(GE.getSelectedCharacter());
		
		//update item stats
		itemAttack.setText("ATTACK:    " + Integer.toString(GE.getSelectedItem().getAttack()));
        itemDefense.setText("DEFENSE:    " + Integer.toString(GE.getSelectedItem().getDefense()));
        itemCurrentHP.setText("HEALING:    " + Integer.toString(GE.getSelectedItem().getCurrentHealth()));
        itemCurrentMana.setText("MANA:    " + Integer.toString(GE.getSelectedItem().getCurrentMana()));
        itemSpeed.setText("SPEED:    "+ Integer.toString(GE.getSelectedItem().getSpeed()));
        itemMaxHP.setText("MAX HEALTH:    " + Integer.toString(GE.getSelectedItem().getMaxHealth()));        
        itemMaxMana.setText("MAX MANA:    " +Integer.toString(GE.getSelectedItem().getMaxMana()));
       
        //update character stats
        characterAttack.setText("ATTACK:    " + Integer.toString(GE.getSelectedCharacter().getAttack()));
        characterDefense.setText("DEFENSE:    " + Integer.toString(GE.getSelectedCharacter().getDefense()));
        characterCurrentHP.setText("HEALING:    " + Integer.toString(GE.getSelectedCharacter().getCurrentHealth()));
        characterCurrentMana.setText("MANA:    " + Integer.toString(GE.getSelectedCharacter().getCurrentMana()));
        characterSpeed.setText("SPEED:    "+ Integer.toString(GE.getSelectedCharacter().getSpeed()));
        characterMaxHP.setText("MAX HEALTH:    " + Integer.toString(GE.getSelectedCharacter().getMaxHealth()));        
        characterMaxMana.setText("MAX MANA:    " +Integer.toString(GE.getSelectedCharacter().getMaxMana()));
		
		if(GE.getSelectedCharacter().getEquipped() == null){
			unequipButton.setEnabled(false);
		}
		else{
			unequipButton.setEnabled(true);
		}
		if(GE.itemListModel.size() != 0){
			updateBackpackLabel(GE.getSelectedItem());
				
			if(GE.getSelectedItem().isConsumable())
			{				
				equipButton.setEnabled(false);
				useButton.setEnabled(true);
			}
			else{
				if(GE.getSelectedCharacter().getEquipped() == null){
					equipButton.setEnabled(true);					
				}
				else{ equipButton.setEnabled(false);}
				useButton.setEnabled(false);
			}
		}
		else{
			itemPicture.setIcon(null);
			itemPicture.setText(null);
			equipButton.setEnabled(false);
			useButton.setEnabled(false);			
		}
		
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
    	expBarModel.setMaximum((int)GE.getExpNeeded(entity));
    	magicBarModel = magicBar.getModel();
    	magicBarModel.setMaximum(entity.getMaxMana());
	 	healthBarModel.setValue(GE.getSelectedCharacter().getCurrentHealth());
    	expBar.setValue(GE.getSelectedCharacter().getExp()); 
	 	magicBar.setValue(GE.getSelectedCharacter().getCurrentMana()); 

    }
   
    
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		//if previous character
		if(arg0.getSource() == previousCharacterButton)
		{
			GE.navigateCharacter("previous");
			update();
		}
		
		//if next character
		if(arg0.getSource() == nextCharacterButton)
		{
			GE.navigateCharacter("next");
			update();
		}
		
		//equip button is pressed
		if(arg0.getSource() == equipButton)
		{
			if(GE.getSelectedCharacter().getEquipped() == null){
				GE.getSelectedCharacter().setEquippedItem(GE.getSelectedItem());
				GE.removeItem(GE.itemList.getSelectedIndex());		
				update();
			}
			else{
			}
			
		}
		
		//unequip button is pressed
		if(arg0.getSource() == unequipButton)
		{
			GE.addItem(GE.getSelectedCharacter().getEquipped());
			GE.getSelectedCharacter().setEquippedItem(null);
			update();
		}
		
		//use button is preseed
		if(arg0.getSource() == useButton)
		{
			GE.getSelectedCharacter().useItem(GE.getSelectedItem());
			GE.removeItem(GE.itemList.getSelectedIndex());
			update();
		}
	}
}