package rpg;

import java.awt.BorderLayout;
import java.util.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * CombatGUI class
 * 
 * Displays the combat mode for the game engine
 * 
 * @author Austin
 * @version 40/18/2012
 */

public class CombatGUI extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private GameEngine GE; // link back to engine

	private final int actionHeight = 50; // lower panel height
	private final int actionWidth = 570; // lower panel width

	private int action; // remember their action chosen
	// 0 = null
	// 1 = attack
	// 2 = flee
	// 3 = ability
	// 4 = item

	// gui parts
	private JPanel playerSide;
	private JPanel middleArea;
	private JPanel enemySide;
	private JLayeredPane actionPanel; // lower panel
	private JPanel actionMenu; // highest layer
	private JPanel actionAttack; // second layer
	private JPanel actionAbility; // third layer
	private JPanel actionItem; // fourth layer
	private JPanel actionText; // lowest layer
	private JScrollPane actionTextScrollBar;

	// specific text labels, images and DropDown Menus
	protected JTextArea status;
	protected JLabel imageBackground;
	protected JComboBox<String> attackTargets;
	protected JComboBox<String> abilityTargets;
	protected JComboBox<String> itemTargets;
	protected JComboBox<String> abilities;
	protected JComboBox<String> items;

	// container for CombatObjects
	ArrayList<CombatObject> enemies;
	ArrayList<CombatObject> characters;

	// flag that combat is over
	boolean combatOver;
	boolean fled = false;

	// specific buttons for actions
	private JButton attackButton;
	private JButton fleeButton;
	private JButton abilityButton;
	public JButton itemButton;
	private JButton submitAttackButton;
	private JButton cancelAttackButton;
	private JButton submitAbilityButton;
	private JButton cancelAbilityButton;
	private JButton submitItemButton;
	private JButton cancelItemButton;
	private JButton okButton;

	/**
	 * Constructor for CombatGUI
	 * 
	 * @param tempEngine
	 */
	public CombatGUI(GameEngine tempEngine) {
		GE = tempEngine; // link back to engine

		this.setPreferredSize(new Dimension(GE.X_DIM, GE.Y_DIM));
		this.setLayout(new BorderLayout());
		this.setBackground(Color.BLACK);
		this.addKeyListener(this); // This class has its own key listeners.
		this.setFocusable(true); // Allow panel to get focus

		// use a panel to hold the top three panels, player, middle, and enemy
		// sides
		JPanel northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(GE.X_DIM - 10, GE.Y_DIM
				- actionHeight - 200));
		// northPanel.setOpaque(false); // transparent background
		northPanel.setBackground(Color.WHITE);
		northPanel.setLayout(new GridLayout(0, 3)); // rows, cols

		// build the player's side roster
		playerSide = new JPanel();
		TitledBorder ptb = new TitledBorder("Player Team");
		playerSide.setBorder(ptb);
		ptb.setTitleColor(Color.BLUE);
		ptb.setBorder(new LineBorder(Color.BLUE));
		playerSide.setLayout(new GridLayout(4, 2, 0, 0)); // rows, cols, hspace, vspace
		playerSide.setOpaque(false); // transparent background

		// build a blank middle panel
		middleArea = new JPanel();
		middleArea.setOpaque(false); // transparent background

		// build the enemy's side roster
		enemySide = new JPanel();
		TitledBorder etb = new TitledBorder("Enemy Team");
		etb.setTitleColor(Color.RED);
		etb.setBorder(new LineBorder(Color.RED));
		enemySide.setBorder(etb);
		enemySide.setLayout(new GridLayout(4, 2, 0, 0)); // rows, cols, hspace, vspace
		enemySide.setOpaque(false); // transparent background

		// place each component into the northPanel
		northPanel.add(playerSide);
		northPanel.add(middleArea);
		northPanel.add(enemySide);

		// build and place each layer to the action panel
		// build the main action menu for the highest layer
		actionMenu = new JPanel();
		actionMenu.setLayout(new GridLayout(0, 4, 20, 40)); // rows, cols, hspace, vspace
		actionMenu.setSize(actionWidth, actionHeight);
		actionMenu.setLocation(10, 10);

		attackButton = new JButton("Attack");
		attackButton.addActionListener(this);
		actionMenu.add(attackButton);

		abilityButton = new JButton("Ability");
		abilityButton.addActionListener(this);
		actionMenu.add(abilityButton);

		itemButton = new JButton("Item");
		itemButton.addActionListener(this);
		actionMenu.add(itemButton);

		fleeButton = new JButton("Flee");
		fleeButton.addActionListener(this);
		actionMenu.add(fleeButton);
		// finished making main action menu

		// build the target panel
		actionAttack = new JPanel();
		actionAttack.setLayout(new GridLayout(1, 3, 20, 40));
		attackTargets = new JComboBox<String>(); // pass String[] of list to this object
		attackTargets.addActionListener(this);
		actionAttack.setSize(actionWidth, actionHeight);
		actionAttack.setLocation(10, 10);
		cancelAttackButton = new JButton("Back"); // back button
		cancelAttackButton.addActionListener(this);
		submitAttackButton = new JButton("Attack!"); // submit button
		submitAttackButton.addActionListener(this);
		actionAttack.add(attackTargets);
		actionAttack.add(submitAttackButton);
		actionAttack.add(cancelAttackButton);
		// finished making ability panel

		// build the ability panel
		actionAbility = new JPanel();
		actionAbility.setLayout(new GridLayout(1, 4, 20, 40));
		actionAbility.setSize(actionWidth, actionHeight);
		actionAbility.setLocation(10, 10);
		abilities = new JComboBox<String>(); // pass String[] of list to this object
		abilityTargets = new JComboBox<String>();
		abilityTargets.addActionListener(this);
		cancelAbilityButton = new JButton("Back"); // back button
		cancelAbilityButton.addActionListener(this);
		submitAbilityButton = new JButton("Cast!"); // submit button
		submitAbilityButton.addActionListener(this);
		actionAbility.add(abilities);
		actionAbility.add(abilityTargets);
		actionAbility.add(submitAbilityButton);
		actionAbility.add(cancelAbilityButton); // re-add the same back button
		// finished making ability panel

		// build the item panel
		actionItem = new JPanel();
		actionItem.setLayout(new GridLayout(1, 4, 20, 40));
		actionItem.setSize(actionWidth, actionHeight);
		actionItem.setLocation(10, 10);
		items = new JComboBox<String>(); // pass String[] of list to this object
		itemTargets = new JComboBox<String>();
		itemTargets.addActionListener(this);
		cancelItemButton = new JButton("Back"); // back button
		cancelItemButton.addActionListener(this);
		submitItemButton = new JButton("Use!"); // submit button
		submitItemButton.addActionListener(this);
		actionItem.add(items);
		actionItem.add(itemTargets);
		actionItem.add(submitItemButton);
		actionItem.add(cancelItemButton); // re-add the same back button
		// finished making item panel

		// build the status/text/updater panel
		actionText = new JPanel();
		actionText.setLayout(new BorderLayout());
		actionText.setSize(actionWidth, actionHeight);
		actionText.setLocation(10, 10);
		status = new JTextArea("");
		status.setEditable(false);
		actionTextScrollBar = new JScrollPane(status);
		actionTextScrollBar
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		actionText.add(actionTextScrollBar, BorderLayout.CENTER);
		actionText.add(okButton, BorderLayout.EAST);
		// finished making status panel

		// place all the panels on the layered pane
		// build the lower actions panel
		actionPanel = new JLayeredPane();
		actionPanel.add(actionMenu); // top layer
		actionPanel.add(actionAttack);
		actionPanel.add(actionAbility);
		actionPanel.add(actionItem);
		actionPanel.add(actionText); // bottom layer

		actionPanel.setLayer(actionMenu, JLayeredPane.DRAG_LAYER, 0);
		actionPanel.setLayer(actionAttack, JLayeredPane.POPUP_LAYER, 0);
		actionPanel.setLayer(actionAbility, JLayeredPane.MODAL_LAYER, 0);
		actionPanel.setLayer(actionItem, JLayeredPane.PALETTE_LAYER, 0);
		actionPanel.setLayer(actionText, JLayeredPane.DEFAULT_LAYER, 0);
		actionPanel.setLayout(new BorderLayout());
		actionPanel.setSize(actionWidth + 20, actionHeight + 20);
		actionPanel.setVisible(true);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.setBorder(new LineBorder(Color.BLACK));
		southPanel.add(Box.createHorizontalGlue());
		southPanel.add(Box.createHorizontalGlue(), BorderLayout.WEST);
		southPanel.add(actionPanel, BorderLayout.CENTER);
		southPanel.add(Box.createHorizontalGlue(), BorderLayout.EAST);

		// container for CombatObjects
		characters = new ArrayList<CombatObject>();
		enemies = new ArrayList<CombatObject>();

		// Finalize the GUI and place each component
		this.add(northPanel, BorderLayout.NORTH);
		this.add(southPanel); // doesn't need layout
		this.setBorder(BorderFactory.createEmptyBorder(100, 0, 100, 0));

		// view the aciton menu first
		viewActionMenu();

		appendStatus("Scroll Down to see current status. I'm working on this bug.");
	} // end of constructor

	/**
	 * Cleans the GUI board for the next round of combat
	 * 
	 * This funciton should be called each time combat is started
	 */
	public void resetCombatGUI() 
	{
		// should clear the player and enemy sides
		playerSide.removeAll();
		enemySide.removeAll();
		characters.removeAll(characters);
		enemies.removeAll(enemies);
		
		combatOver = false;
		fled = false;

		// function to populate player's side
		populatePlayerTeam();

		// function to populate enemy's side
		populateEnemyTeam();

		// reset the view to normal for player's turn
		switchToPlayerTurn();
		
		//loadItems
		loadItems();

		// erase the status box
		status.setText("");

		// clear prev actions
		action = 0;

	}

	/**
	 * Populates the player's team on the CombatGUI
	 */
	public void populatePlayerTeam() {
		for (Entity c : GE.characters) {
			CombatObject temp = new CombatObject(GE, c, GE.Grass);
			playerSide.add(temp);
			characters.add(temp);
		}
	}

	/**
	 * Populates the enemy's team on the CombatGUI
	 */
	public void populateEnemyTeam() {		
		for (Entity c : GE.enemies) {
			CombatObject temp = new CombatObject(GE, c, GE.Grass);
			temp.manaBar.setVisible(false);
			enemySide.add(temp);
			enemies.add(temp);
		}
	}

	public void update() {
		
		for (CombatObject obj : enemies){
			obj.setHealthBar();
			obj.setManaBar();
		}
		
		for (CombatObject obj : characters){
			obj.setHealthBar();
			obj.setManaBar();
		}
		
	}
	
	public void loadItems(){
		Item[] array = new Item[GE.itemListModel.size()];
		GE.itemListModel.copyInto(array);
		for(Item i : array){
			if(i.isConsumable()){
				this.items.addItem(i.getItemName());
			}
		}
	}

	public void cleanEnemies() {
		ArrayList<CombatObject> temp = new ArrayList<CombatObject>();
		
		for(CombatObject obj : enemies){
			if(obj.entity.getCurrentHealth() <= 0){
				temp.add(obj);
			}
		}
		
		for(CombatObject obj : temp){
			enemySide.remove(obj);
			enemies.remove(obj);
		}
		
		temp.removeAll(temp);
	}

	public void endCombat(int result, int experience) {
		if (result == 0) {
			// code to handle running away (nothing happens)
			appendStatus("You ran away successfully!");
		} else {
			if (result == 1) {
				enemySide.removeAll();
				appendStatus("Victory!");
				for (Entity character : GE.characters) {
					appendStatus(character.getName() + " has earned "
							+ experience + " experience!");
				}
			} else {
				appendStatus("Defeat!");
			}
		}
	}

	/**
	 * Updates the text of the status label layer. Note: this function only
	 * updates the text, if you want to view the text, you need to call
	 * viewStatusPanel()
	 * 
	 * @param newStatus
	 */
	public void appendStatus(String newStatus) {
		// udpate the text label
		status.append("\n" + newStatus);

		// force scroll down to bottom
		actionTextScrollBar.getVerticalScrollBar().setValue(status.getRows() * 10);
	}

	public void viewActionMenu() {
		actionText.setVisible(false);
		actionItem.setVisible(false);
		actionAbility.setVisible(false);
		actionAttack.setVisible(false);
		actionMenu.setVisible(true);
	}

	public void viewAttackMenu() {
		actionText.setVisible(false);
		actionItem.setVisible(false);
		actionAbility.setVisible(false);
		actionAttack.setVisible(true);
		actionMenu.setVisible(false);
	}

	public void viewAbilityMenu() {
		actionText.setVisible(false);
		actionItem.setVisible(false);
		actionAbility.setVisible(true);
		actionAttack.setVisible(false);
		actionMenu.setVisible(false);
	}

	public void viewItemMenu() {
		actionText.setVisible(false);
		actionItem.setVisible(true);
		actionAbility.setVisible(false);
		actionAttack.setVisible(false);
		actionMenu.setVisible(false);
	}

	public void viewStatusPanel() {
		actionText.setVisible(true);
		actionItem.setVisible(false);
		actionAbility.setVisible(false);
		actionAttack.setVisible(false);
		actionMenu.setVisible(false);
	}

	/**
	 * Switches the Combat mode to player's turn
	 */
	public void switchToPlayerTurn() {
		okButton.setEnabled(true);
		viewActionMenu();
	}

	/**
	 * Switches the Combat mode to enemy's turn
	 */
	public void switchToEnemyTurn() {
		okButton.setEnabled(false);
		viewStatusPanel();
	}
	
	public void paintCurrentTargetBorder(CombatObject target){
		for(CombatObject obj : enemies){
			if(obj.equals(target) && !obj.currentTurn){
				obj.setCurrentTarget(true);
			}else if(!obj.currentTurn){
				obj.setCurrentTarget(false);
			}
		}
		
		for(CombatObject obj : characters){
			if(obj.equals(target) && !obj.currentTurn){
				obj.setCurrentTarget(true);
			}else if (!obj.currentTurn){
				obj.setCurrentTarget(false);
			}
		}
	}
	
	public CombatObject getCombatObjectByName(String name){
		CombatObject returnVal = null;
		for(CombatObject obj : characters){
			if(obj.entity.getName().equals(name)){
				returnVal = obj;
			}
		}
		
		for(CombatObject obj : enemies){
			if(obj.entity.getName().equals(name)){
				returnVal = obj;
			}
		}
		
		return returnVal;
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		// if the user clicks on a button, perform the function, then
		// show and hide the necessary panels

		if (a.getSource() == attackButton) {
			action = 1; // remember what they chose
			viewAttackMenu();
		} else if (a.getSource() == fleeButton) {
			action = 2; // remember what they chose
			GE.playerTurn("Flee", 0, null);
			viewStatusPanel();
		} else if (a.getSource() == abilityButton) {
			action = 3; // remember what they chose
			if (abilities.getItemCount() > 0) {
				viewAbilityMenu();
			}
		} else if (a.getSource() == itemButton) {
			action = 4; // remember what they chose
			viewItemMenu();
		} else if (a.getSource() == submitAttackButton
				|| a.getSource() == submitAbilityButton
				|| a.getSource() == submitItemButton) {
			// execute the specified action
			if (action == 1) // attack
				GE.playerTurn("Attack", attackTargets.getSelectedIndex(), null);
			else if (action == 3){
				
				Ability ability = GE.turnStack.peek().getAbilityByName(abilities.getSelectedItem().toString());
				if(GE.turnStack.peek().getCurrentMana() < ability.getCost()){
					appendStatus("You do not have enough mana for this ability!");
				}else{
					GE.playerTurn(abilities.getSelectedItem().toString(),
						abilityTargets.getSelectedIndex(), null);
				}
			}
			else if (action == 4){
				Item item = GE.getItemByName(items.getSelectedItem().toString());
				GE.playerTurn("Item",
					itemTargets.getSelectedIndex(), item);
			}
			// look at status panel
			viewStatusPanel();
		} else if (a.getSource() == cancelAttackButton
				|| a.getSource() == cancelAbilityButton
				|| a.getSource() == cancelItemButton) {
			// back out, and show the main action menu
			viewActionMenu();
			action = 0;
			
		//highlight attack target
		} else if (a.getSource() == attackTargets){
			//paintCurrentTargetBorder(getCombatObjectByName(attackTargets.getSelectedItem().toString()));
		
		//highlight ability target
		} else if (a.getSource() == abilityTargets){
			//paintCurrentTargetBorder(getCombatObjectByName(abilityTargets.getSelectedItem().toString()));
		
		//highlight item target
		} else if (a.getSource() == itemTargets){
			//paintCurrentTargetBorder(getCombatObjectByName(itemTargets.getSelectedItem().toString()));
		
		} else if (a.getSource() == okButton) {
			
			//combat will close
			if (combatOver || fled) {
				cleanEnemies();
				
				GE.endCombat(GE.characters, 0, GE.combatResult);
				GE.viewMapPanel();
				
				// apply points now?
				if(GE.hasLeveledUp)
				{
					String temp = "One of your characters has leveled up!\n\nWould you like to spend your points now?";
					int result2 = GE.printYesNoQuestion(temp);
					if (result2 == 0)
						GE.viewStatsPanel();
					
					GE.hasLeveledUp = false;
				}
				
				combatOver = true;
			}
			
			//combat will display one more status update
			else if (GE.combatOver) {
				enemySide.removeAll();
				GE.endCombat(GE.characters, GE.accumulatedExp, GE.combatResult);
				combatOver = true;
				
			} else {
				viewActionMenu();
				Entity next = GE.turnStack.peek();
				//populateEnemyTeam();
				cleanEnemies();
				GE.setupTurn(next, GE.combatants);
			}

		}
	}

	@Override
	public void keyPressed(KeyEvent k) {
		int key = k.getKeyCode();
		
		if(key == 65) // a
		{
			// enter attack panel
			if(actionMenu.isVisible())
			{
				attackButton.doClick();
			}
		}
		else if (key == 83) // s
		{
			// enter abilities panel
			if(actionMenu.isVisible())
			{
				abilityButton.doClick();
			}
		}
		else if(key == 68) // d
		{
			// enter items panel
			if(actionMenu.isVisible())
			{
				itemButton.doClick();
			}
		}
		else if(key == 70) // f
		{
			// enter flee attempt
			if(actionMenu.isVisible())
			{
				fleeButton.doClick();
			}
		}				
		else if (key == 40) // arrow down
		{
			if(actionAttack.isVisible())
			{
				if(attackTargets.getSelectedIndex() != attackTargets.getItemCount()-1)
					attackTargets.setSelectedIndex(attackTargets.getSelectedIndex()+1);
				else
					attackTargets.setSelectedIndex(0);
			}
			else if(actionAbility.isVisible())
			{
				if(abilityTargets.getSelectedIndex() != abilityTargets.getItemCount()-1)
					abilityTargets.setSelectedIndex(abilityTargets.getSelectedIndex()+1);
				else
					abilityTargets.setSelectedIndex(0);
			}
			else if(actionItem.isVisible())
			{
				if(itemTargets.getSelectedIndex() != itemTargets.getItemCount()-1)
					itemTargets.setSelectedIndex(itemTargets.getSelectedIndex()+1);
				else
					itemTargets.setSelectedIndex(0);
			}
		} 
		else if (key == 38) // arrow up
		{
			if(actionAttack.isVisible())
			{
				if(attackTargets.getSelectedIndex() != 0)
					attackTargets.setSelectedIndex(attackTargets.getSelectedIndex()-1);
				else
					attackTargets.setSelectedIndex(attackTargets.getItemCount()-1);
			}
			else if(actionAbility.isVisible())
			{
				if(abilityTargets.getSelectedIndex() != 0)
					abilityTargets.setSelectedIndex(abilityTargets.getSelectedIndex()-1);
				else
					abilityTargets.setSelectedIndex(abilityTargets.getItemCount()-1);
			}
			else if(actionItem.isVisible())
			{
				if(itemTargets.getSelectedIndex() != 0)
					itemTargets.setSelectedIndex(itemTargets.getSelectedIndex()-1);
				else
					itemTargets.setSelectedIndex(itemTargets.getItemCount()-1);
			}
		} 
		else if (key == 37) // arrow left
		{
			if(actionAbility.isVisible())
			{
				if(abilities.getSelectedIndex() != abilities.getItemCount()-1)
					abilities.setSelectedIndex(abilities.getSelectedIndex()+1);
				else
					abilities.setSelectedIndex(0);
			}
			else if(actionItem.isVisible())
			{
				if(items.getSelectedIndex() != items.getItemCount()-1)
					items.setSelectedIndex(items.getSelectedIndex()+1);
				else
					items.setSelectedIndex(0);
			}
		}
		else if (key == 39) // arrow right
		{
			if(actionAbility.isVisible())
			{
				if(abilities.getSelectedIndex() != 0)
					abilities.setSelectedIndex(abilities.getSelectedIndex()-1);
				else
					abilities.setSelectedIndex(abilities.getItemCount()-1);
			}
			else if(actionItem.isVisible())
			{
				if(items.getSelectedIndex() != 0)
					items.setSelectedIndex(items.getSelectedIndex()-1);
				else
					items.setSelectedIndex(items.getItemCount()-1);
			}
		}
		else if(key == 8) // backspace
		{
			if(!actionMenu.isVisible() && !actionText.isVisible())
			{
				viewActionMenu();
			}
		}
		else if(key == 10) // enter
		{
			if(actionAttack.isVisible())
			{
				submitAttackButton.doClick();
			}
			else if(actionAbility.isVisible())
			{
				submitAbilityButton.doClick();
			}
			else if(actionItem.isVisible())
			{
				submitItemButton.doClick();
			}
			else if(actionText.isVisible())
			{
				okButton.doClick();
			}
		}
		else if(key == 27) // ESC to pause game
			GE.pauseGame();
		
		GE.keysPushed++;
	}

	@Override
	public void keyReleased(KeyEvent k) {
		int key = k.getKeyCode();

		if (key == 16) // left shift
			GE.leftShift = false;

		if (key == 32) // space bar
			GE.spaceBar = false;

		if (key == 40 || key == 83) // arrow down or 's'
		{
			GE.down = false;
		} else if (key == 38 || key == 87) // arrow up or 'w'
		{
			GE.up = false;
		} else if (key == 37 || key == 65) // arrow left or 'a'
		{
			GE.left = false;
		} else if (key == 39 || key == 68) // arrow right or 'd'
		{
			GE.right = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent k) {
		// Auto-generated method stub
	}
} // end of CombatGUI
