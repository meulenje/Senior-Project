import java.awt.BorderLayout; 
import java.awt.Color;
import java.awt.Dimension; 
import java.awt.Graphics;
import java.awt.GridLayout; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.MouseEvent; 
import java.awt.event.MouseListener; 
import java.awt.event.MouseMotionListener; 
import java.awt.image.BufferedImage;
import java.io.BufferedReader; 
import java.io.BufferedWriter; 
import java.io.DataInputStream; 
import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import java.io.FileWriter; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.net.URL;
import java.util.ArrayList; 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


import javax.imageio.ImageIO;
import javax.swing.*; 


public class Editor implements ActionListener, MouseListener, MouseMotionListener{ 


	File f; 
	File newFilePic; 

	JFileChooser fc; 

	/* Frame */ 
	JFrame frame; 

	/* Menu */ 
	JMenuBar menubar; 
	JMenu file; 
	JMenuItem newMap; 
	JMenuItem load; 
	JMenuItem save; 
	JMenuItem quit; 


	/* Panels */ 
	JPanel itemPanel; 
	JPanel terrainPanel; 
	JPanel mapPanel; 
	JPanel newPanel;
	JPanel editPanel; 
	JPanel objectPanel; 
	JPanel entityPanel;

	JTabbedPane tabPane; 

	/* Scroll Panes */ 
	JScrollPane itemScrollPane; 
	JScrollPane terrainScrollPane; 
	JScrollPane mapScrollPane; 
	JScrollPane objectScrollPane; 
	JScrollPane entityScrollPane;


	/* Map Grid */ 
	JLabel[][] map; 
	JLabel[][] map2; 
	JLabel[][] map3; 
	JLayeredPane[][] mapLayer; 

	
	int[][] idMap;
	int[][] idMap2;
	int[][] idMap3;
	
	ArrayList<ArrayList<String>> objectList = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> entityList = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> itemList = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> terrainList = new ArrayList<ArrayList<String>>();
	
	/* Map Options */ 
	ArrayList<JButton> terrainButtons = new ArrayList<JButton>(); 
	ArrayList<JButton> entityButtons = new ArrayList<JButton>();
	ArrayList<JButton> itemButtons = new ArrayList<JButton>();
	ArrayList<JButton> objectButtons = new ArrayList<JButton>();
	ArrayList<JButton> editButtons = new ArrayList<JButton>();

	ArrayList<JPanel> newPanelJPanels = new ArrayList<JPanel>();
	
	String[] trueFalseOption = { "true", "false"};
	String[] layerOption = {"1", "2", "3"};
	String[] imageOption = {"png", "jpg", "gif"};
	
	ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>(); 


	ImageIcon selectedIcon; 

	int numPictures = 0; 
	String selectedID = "";
	String name; 
	
	boolean mousePressed = false; 


	int layerNum = 2; 

	protected ImageIcon temp = new ImageIcon(); 
	protected ImageIcon temp2 = new ImageIcon(); 
	protected ImageIcon blank = new ImageIcon("images/Blank.png"); 
	protected ImageIcon dirt = new ImageIcon("images/Dirt.png"); 
	protected ImageIcon defaultTerrain = dirt;




	protected ImageIcon editEraser = new ImageIcon("images/editEraser.png"); 
	protected ImageIcon editSelect = new ImageIcon("images/editSelect.png"); 
	protected ImageIcon editNewIcon = new ImageIcon("images/editNewIcon.png"); 

	JPanel blankPanel = new JPanel();
	
	JLabel newPanelImageLabel;
	JButton newPanelImageButton;
	JLabel newPanelImageLabel1;
	JButton newPanelImageButton1;
	JLabel newPanelImageLabel2;
	JButton newPanelImageButton2;
	JLabel newPanelImageLabel3;
	JButton newPanelImageButton3;
	JLabel newPanelImageLabel4;
	JButton newPanelImageButton4;
	
	JLabel newPanelTypeSelectLabel;
	JComboBox newPanelTypeSelectOption;
	String[] typeSelectOptions = {"terrain", "object", "entity", "item"};
	
	JLabel newPanelImageTypeLabel;
	JComboBox newPanelImageTypeOption;
	JButton newPanelContinue;
	
	JButton newPanelSave1;
	JButton newPanelSave2;
	JButton newPanelSave3;
	JButton newPanelSave4;
	
	JButton newPanelCancel;
	JButton newPanelCancel1;
	JButton newPanelCancel2;
	JButton newPanelCancel3;
	JButton newPanelCancel4;
	
	
	JPanel newTerrainPanel;
	JPanel newObjectPanel;
	JPanel newItemPanel;
	JPanel newEntityPanel;
	
	// Terrain Options
	JLabel newPanelTerrainNameLabel;
	JTextField newPanelTerrainNameOption;
	JLabel newPanelTerrainIsWalkableLabel;
	JComboBox newPanelTerrainIsWalkableOption;
	JLabel newPanelTerrainLayerLabel;
	JComboBox newPanelTerrainLayerOption;
	
	// Object Options
	JLabel newPanelObjectNameLabel;
	JTextField newPanelObjectNameOption;
	JLabel newPanelObjectDescriptionLabel;
	JTextField newPanelObjectDescriptionOption;
	JLabel newPanelObjectLayerLabel;
	JComboBox newPanelObjectLayerOption;
	JLabel newPanelObjectIsPushableLabel;
	JComboBox newPanelObjectIsPushableOption;
	JLabel newPanelObjectIsTrapLabel;
	JComboBox newPanelObjectIsTrapOption;
	JLabel newPanelObjectIsSignPostLabel;
	JTextField newPanelObjectIsSignPostOption;
	JLabel newPanelObjectIsJumpableLabel;
	JComboBox newPanelObjectIsJumpableOption;
	JLabel newPanelObjectIsHoleLabel;
	JComboBox newPanelObjectIsHoleOption;
	
	// Item Options
	JLabel newPanelItemNameLabel;
	JTextField newPanelItemNameOption;
	JLabel newPanelItemDescriptionLabel;
	JTextField newPanelItemDescriptionOption;
	JLabel newPanelItemLevelRequiredLabel;
	JTextField newPanelItemLevelRequiredOptionOption;
	JLabel newPanelItemAttackLabel;
	JTextField newPanelItemAttackOption;
	JLabel newPanelItemDefenseLabel;
	JTextField newPanelItemDefenseOption;
	JLabel newPanelItemSpeedLabel;
	JTextField newPanelItemSpeedOption;
	JLabel newPanelItemHealthLabel;
	JTextField newPanelItemHealthOption;
	JLabel newPanelItemMagicLabel;
	JTextField newPanelItemMagicOption;
	JLabel newPanelItemIsEquipableLabel;
	JComboBox newPanelItemIsEquipableOption;
	
	// Entity Options 
	JLabel newPanelEntityNameLabel;
	JTextField newPanelEntityNameOption;
	JLabel newPanelEntityLevelLabel;
	JTextField newPanelEntityLevelOption;
	JLabel newPanelEntityIsPlayerLabel;
	JComboBox newPanelEntityIsPlayerOption;
	JLabel newPanelEntityTotalHealthLabel;
	JTextField newPanelEntityTotalHealthOption;
	JLabel newPanelEntityCurrentHealthLabel;
	JTextField newPanelEntityCurrentHealthOption;
	JLabel newPanelEntityTotalMagicLabel;
	JTextField newPanelEntityTotalMagicOption;
	JLabel newPanelEntityCurrentMagicLabel;
	JTextField newPanelEntityCurrentMagicOption;
	JLabel newPanelEntityTotalExperienceLabel;
	JTextField newPanelEntityTotalExperienceOption;
	JLabel newPanelEntityCurrentLevelUpPointsLabel;
	JTextField newPanelEntityCurrentLevelUpPointsOption;
	JLabel newPanelEntityTotalAbilitesLabel;
	JTextField newPanelEntityTotalAbilitiesOption;
	JLabel newPanelEntityCurrentEquippedItemLabel;
	JTextField newPanelEntityCurrentEquippedItemOption;
	JLabel newPanelEntityAttackLabel;
	JTextField newPanelEntityAttackOption;
	JLabel newPanelEntityDefenseLabel;
	JTextField newPanelEntityDefenseOption;
	JLabel newPanelEntitySpeedLabel;
	JTextField newPanelEntitySpeedOption;

	
	
	
	

	public Editor() { 
		frame = new JFrame("MAP EDITOR"); 
		frame.setLayout(new BorderLayout()); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setSize(600,600); 
		
		menubar = new JMenuBar(); 
		file = new JMenu("File"); 
		newMap = new JMenuItem("New Map"); 
		load = new JMenuItem("Load"); 
		save = new JMenuItem("Save"); 
		quit = new JMenuItem("Quit"); 
		load.addActionListener(this); 
		newMap.addActionListener(this); 
		save.addActionListener(this); 
		quit.addActionListener(this); 

		fc = new JFileChooser("images/"); 

		file.add(newMap); 
		file.add(load); 
		file.add(save); 
		file.add(quit); 

		menubar.add(file); 

		frame.setJMenuBar(menubar); 
		mapPanel = new JPanel(); 

		mapPanel.setLayout(new GridLayout(20,20)); 
		mapLayer = new JLayeredPane[20][20]; 
		map = new JLabel[20][20]; 
		map2 = new JLabel[20][20]; 
		map3 = new JLabel[20][20]; 

		idMap = new int[20][20]; 
		idMap2 = new int[20][20]; 
		idMap3 = new int[20][20]; 
		
		
		newPanelImageLabel = new JLabel("Image Preview");
		newPanelImageButton = new JButton();
		newPanelImageLabel1 = new JLabel("Image Preview");
		newPanelImageButton1 = new JButton();
		newPanelImageLabel2 = new JLabel("Image Preview");
		newPanelImageButton2 = new JButton();
		newPanelImageLabel3 = new JLabel("Image Preview");
		newPanelImageButton3 = new JButton();
		newPanelImageLabel4 = new JLabel("Image Preview");
		newPanelImageButton4 = new JButton();
		
		newPanelTypeSelectLabel = new JLabel("Select the type.");
		newPanelTypeSelectOption = new JComboBox(typeSelectOptions);
		newPanelContinue = new JButton("Continue");
		newPanelSave1 = new JButton("Save");
		newPanelSave2 = new JButton("Save");
		newPanelSave3 = new JButton("Save");
		newPanelSave4 = new JButton("Save");
		
		newPanelCancel = new JButton("Cancel");
		newPanelCancel1 = new JButton("Cancel");
		newPanelCancel2 = new JButton("Cancel");
		newPanelCancel3 = new JButton("Cancel");
		newPanelCancel4 = new JButton("Cancel");
		
		newPanelImageTypeLabel = new JLabel("Image Type");
		newPanelImageTypeOption = new JComboBox(imageOption);
		
		newPanel = new JPanel();
		newPanel.setLayout(new GridLayout(4,2));
		newPanel.add(newPanelImageLabel);
		newPanel.add(newPanelImageButton);
		newPanel.add(newPanelTypeSelectLabel);
		newPanel.add(newPanelTypeSelectOption);
		newPanel.add(newPanelContinue);
		newPanel.add(newPanelCancel);
		newPanel.add(blankPanel);
		//newPanel.add(blankPanel);
		
		newPanelImageLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelImageLabel1.setHorizontalAlignment(JLabel.CENTER);
		newPanelImageLabel2.setHorizontalAlignment(JLabel.CENTER);
		newPanelImageLabel3.setHorizontalAlignment(JLabel.CENTER);
		newPanelImageLabel4.setHorizontalAlignment(JLabel.CENTER);
		
		newPanelTypeSelectLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelImageTypeLabel.setHorizontalAlignment(JLabel.CENTER);
		
		
		newPanelSave1.addActionListener(this);
		newPanelSave2.addActionListener(this);
		newPanelSave3.addActionListener(this);
		newPanelSave4.addActionListener(this);
		
		newPanelCancel.addActionListener(this);
		newPanelCancel1.addActionListener(this);
		newPanelCancel2.addActionListener(this);
		newPanelCancel3.addActionListener(this);
		newPanelCancel4.addActionListener(this);
		
		newPanelContinue.addActionListener(this);
		
		// Terrain Options
		
		
		newPanelTerrainNameLabel = new JLabel("Name: ");
		newPanelTerrainNameOption = new JTextField();
		newPanelTerrainIsWalkableLabel = new JLabel("IsWalkable: ");
		newPanelTerrainIsWalkableOption = new JComboBox(trueFalseOption);
		newPanelTerrainLayerLabel = new JLabel("Layer: ");
		newPanelTerrainLayerOption = new JComboBox(layerOption);
		
		newTerrainPanel = new JPanel();
		newTerrainPanel.setLayout(new GridLayout(6, 2));
		
		newTerrainPanel.add(newPanelImageLabel1);
		newTerrainPanel.add(newPanelImageButton1);
		newTerrainPanel.add(newPanelImageTypeLabel);  
		newTerrainPanel.add(newPanelImageTypeOption);
		newTerrainPanel.add(newPanelTerrainNameLabel);
		newTerrainPanel.add(newPanelTerrainNameOption);
		newTerrainPanel.add(newPanelTerrainIsWalkableLabel);
		newTerrainPanel.add(newPanelTerrainIsWalkableOption);
		newTerrainPanel.add(newPanelTerrainLayerLabel);
		newTerrainPanel.add(newPanelTerrainLayerOption);
		newTerrainPanel.add(newPanelSave1);
		newTerrainPanel.add(newPanelCancel1);
		
		newPanelTerrainNameLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelTerrainIsWalkableLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelTerrainLayerLabel.setHorizontalAlignment(JLabel.CENTER);
		
		// Object Options
		newObjectPanel = new JPanel();
		
		newPanelObjectNameLabel = new JLabel("Name: ");
		newPanelObjectNameOption = new JTextField();
		newPanelObjectDescriptionLabel = new JLabel("Desription: ");
		newPanelObjectDescriptionOption = new JTextField();
		newPanelObjectLayerLabel = new JLabel("Layer: ");
		newPanelObjectLayerOption = new JComboBox(layerOption);
		newPanelObjectIsPushableLabel = new JLabel("IsPushable: ");
		newPanelObjectIsPushableOption = new JComboBox(trueFalseOption);
		newPanelObjectIsTrapLabel = new JLabel("IsTrap: ");
		newPanelObjectIsTrapOption = new JComboBox(trueFalseOption);
		newPanelObjectIsSignPostLabel = new JLabel("IsSignPost");
		newPanelObjectIsSignPostOption = new JTextField("false, null");
		newPanelObjectIsJumpableLabel = new JLabel("IsJumpable: ");
		newPanelObjectIsJumpableOption = new JComboBox(trueFalseOption);
		newPanelObjectIsHoleLabel = new JLabel("IsHole: ");
		newPanelObjectIsHoleOption = new JComboBox(trueFalseOption);
		
		newObjectPanel.setLayout(new GridLayout(11, 2));
		newObjectPanel.add(newPanelImageLabel2);
		newObjectPanel.add(newPanelImageButton2);
		newObjectPanel.add(newPanelImageTypeLabel);
		newObjectPanel.add(newPanelImageTypeOption);
		newObjectPanel.add(newPanelObjectNameLabel);
		newObjectPanel.add(newPanelObjectNameOption);
		newObjectPanel.add(newPanelObjectDescriptionLabel);
		newObjectPanel.add(newPanelObjectDescriptionOption);
		newObjectPanel.add(newPanelObjectLayerLabel);
		newObjectPanel.add(newPanelObjectLayerOption);
		newObjectPanel.add(newPanelObjectIsPushableLabel);
		newObjectPanel.add(newPanelObjectIsPushableOption);
		newObjectPanel.add(newPanelObjectIsTrapLabel);
		newObjectPanel.add(newPanelObjectIsTrapOption);
		newObjectPanel.add(newPanelObjectIsSignPostLabel);
		newObjectPanel.add(newPanelObjectIsSignPostOption);
		newObjectPanel.add(newPanelObjectIsJumpableLabel);
		newObjectPanel.add(newPanelObjectIsJumpableOption);
		newObjectPanel.add(newPanelObjectIsHoleLabel);
		newObjectPanel.add(newPanelObjectIsHoleOption);
		newObjectPanel.add(newPanelSave2);
		newObjectPanel.add(newPanelCancel2);
		
		newPanelObjectNameLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelObjectDescriptionLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelObjectLayerLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelObjectIsPushableLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelObjectIsTrapLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelObjectIsSignPostLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelObjectIsJumpableLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelObjectIsHoleLabel.setHorizontalAlignment(JLabel.CENTER);
		
		// Item Options
		newPanelItemNameLabel = new JLabel("Name: ");
		newPanelItemNameOption = new JTextField();
		newPanelItemDescriptionLabel = new JLabel("Description: ");
		newPanelItemDescriptionOption = new JTextField();
		newPanelItemLevelRequiredLabel = new JLabel("Level Required: ");
		newPanelItemLevelRequiredOptionOption = new JTextField();
		newPanelItemAttackLabel = new JLabel("Attack: ");
		newPanelItemAttackOption = new JTextField();
		newPanelItemDefenseLabel = new JLabel("Defense: ");
		newPanelItemDefenseOption = new JTextField();
		newPanelItemSpeedLabel = new JLabel("Speed: ");
		newPanelItemSpeedOption = new JTextField();
		newPanelItemHealthLabel = new JLabel("Health: ");
		newPanelItemHealthOption = new JTextField();
		newPanelItemMagicLabel = new JLabel("Magic: ");
		newPanelItemMagicOption = new JTextField();
		newPanelItemIsEquipableLabel = new JLabel("IsEquipable: ");
		newPanelItemIsEquipableOption = new JComboBox(trueFalseOption);
		
		newItemPanel = new JPanel();
		newItemPanel.setLayout(new GridLayout(11, 2));
		newItemPanel.add(newPanelImageLabel3);
		newItemPanel.add(newPanelImageButton3);
		newItemPanel.add(newPanelItemNameLabel);
		newItemPanel.add(newPanelItemNameOption);
		newItemPanel.add(newPanelItemDescriptionLabel);
		newItemPanel.add(newPanelItemDescriptionOption);
		newItemPanel.add(newPanelItemLevelRequiredLabel);
		newItemPanel.add(newPanelItemLevelRequiredOptionOption);
		newItemPanel.add(newPanelItemAttackLabel);
		newItemPanel.add(newPanelItemAttackOption);
		newItemPanel.add(newPanelItemDefenseLabel);
		newItemPanel.add(newPanelItemDefenseOption);
		newItemPanel.add(newPanelItemSpeedLabel);
		newItemPanel.add(newPanelItemSpeedOption);
		newItemPanel.add(newPanelItemHealthLabel);
		newItemPanel.add(newPanelItemHealthOption);
		newItemPanel.add(newPanelItemMagicLabel);
		newItemPanel.add(newPanelItemMagicOption);
		newItemPanel.add(newPanelItemIsEquipableLabel);
		newItemPanel.add(newPanelItemIsEquipableOption);
		newItemPanel.add(newPanelSave3);
		newItemPanel.add(newPanelCancel3);
		
		newPanelItemNameLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelItemDescriptionLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelItemLevelRequiredLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelItemAttackLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelItemDefenseLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelItemSpeedLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelItemHealthLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelItemMagicLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelItemIsEquipableLabel.setHorizontalAlignment(JLabel.CENTER);
		
		// Entity Options 
		newEntityPanel = new JPanel();
		
		newPanelEntityNameLabel = new JLabel("Name: ");
		newPanelEntityNameOption = new JTextField();
		newPanelEntityLevelLabel = new JLabel("Level: ");
		newPanelEntityLevelOption = new JTextField();
		newPanelEntityIsPlayerLabel = new JLabel("IsPlayer: ");
		newPanelEntityIsPlayerOption = new JComboBox(trueFalseOption);
		newPanelEntityTotalHealthLabel = new JLabel("Total Health: ");
		newPanelEntityTotalHealthOption = new JTextField();
		newPanelEntityCurrentHealthLabel = new JLabel("Current Health: ");
		newPanelEntityCurrentHealthOption = new JTextField();
		newPanelEntityTotalMagicLabel = new JLabel("Total Magic: ");
		newPanelEntityTotalMagicOption = new JTextField();
		newPanelEntityCurrentMagicLabel = new JLabel("Current Magic: ");
		newPanelEntityCurrentMagicOption = new JTextField();
		newPanelEntityTotalExperienceLabel = new JLabel("Experience: ");
		newPanelEntityTotalExperienceOption = new JTextField();
		newPanelEntityCurrentLevelUpPointsLabel = new JLabel("Level Up Points: ");
		newPanelEntityCurrentLevelUpPointsOption = new JTextField();
		newPanelEntityTotalAbilitesLabel = new JLabel("Abilities: ");
		newPanelEntityTotalAbilitiesOption = new JTextField();
		newPanelEntityCurrentEquippedItemLabel = new JLabel("Equipped Item");
		newPanelEntityCurrentEquippedItemOption = new JTextField();
		newPanelEntityAttackLabel = new JLabel("Attack: ");
		newPanelEntityAttackOption = new JTextField();
		newPanelEntityDefenseLabel = new JLabel("Defense: ");
		newPanelEntityDefenseOption = new JTextField();
		newPanelEntitySpeedLabel = new JLabel("Speed: ");
		newPanelEntitySpeedOption = new JTextField();
		
		newEntityPanel.setLayout(new GridLayout(8,4));
		newEntityPanel.add(newPanelImageLabel4);
		newEntityPanel.add(newPanelImageButton4);
		newEntityPanel.add(newPanelEntityNameLabel);
		newEntityPanel.add(newPanelEntityNameOption);
		newEntityPanel.add(newPanelEntityLevelLabel);
		newEntityPanel.add(newPanelEntityLevelOption);
		newEntityPanel.add(newPanelEntityIsPlayerLabel);
		newEntityPanel.add(newPanelEntityIsPlayerOption);
		newEntityPanel.add(newPanelEntityTotalHealthLabel);
		newEntityPanel.add(newPanelEntityTotalHealthOption);
		newEntityPanel.add(newPanelEntityCurrentHealthLabel);
		newEntityPanel.add(newPanelEntityCurrentHealthOption);
		newEntityPanel.add(newPanelEntityTotalMagicLabel);
		newEntityPanel.add(newPanelEntityTotalMagicOption);
		newEntityPanel.add(newPanelEntityCurrentMagicLabel);
		newEntityPanel.add(newPanelEntityCurrentMagicOption);
		newEntityPanel.add(newPanelEntityTotalExperienceLabel);
		newEntityPanel.add(newPanelEntityTotalExperienceOption);
		newEntityPanel.add(newPanelEntityCurrentLevelUpPointsLabel);
		newEntityPanel.add(newPanelEntityCurrentLevelUpPointsOption);
		newEntityPanel.add(newPanelEntityTotalAbilitesLabel);
		newEntityPanel.add(newPanelEntityTotalAbilitiesOption);
		newEntityPanel.add(newPanelEntityCurrentEquippedItemLabel);
		newEntityPanel.add(newPanelEntityCurrentEquippedItemOption);
		newEntityPanel.add(newPanelEntityAttackLabel);
		newEntityPanel.add(newPanelEntityAttackOption);
		newEntityPanel.add(newPanelEntityDefenseLabel);
		newEntityPanel.add(newPanelEntityDefenseOption);
		newEntityPanel.add(newPanelEntitySpeedLabel);
		newEntityPanel.add(newPanelEntitySpeedOption);
		newEntityPanel.add(newPanelSave4);
		newEntityPanel.add(newPanelCancel4);
		
		
		newPanelEntityNameLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelEntityLevelLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelEntityIsPlayerLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelEntityTotalHealthLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelEntityCurrentHealthLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelEntityTotalMagicLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelEntityCurrentMagicLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelEntityTotalExperienceLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelEntityCurrentLevelUpPointsLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelEntityTotalAbilitesLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelEntityCurrentEquippedItemLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelEntityAttackLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelEntityDefenseLabel.setHorizontalAlignment(JLabel.CENTER);
		newPanelEntitySpeedLabel.setHorizontalAlignment(JLabel.CENTER);
		/* TODO */
		
		
		for (int i = 0; i < mapLayer.length; i++){ 
			for (int j = 0; j < mapLayer[0].length; j++){ 

				// Top level 
				map[i][j] = new JLabel(blank); 
				idMap[i][j] = 0;
				
				// Main level 
				map2[i][j] = new JLabel(blank); 
				idMap2[i][j] = 0;
				
				// Ground Level 
				map3[i][j] = new JLabel(defaultTerrain); 
				idMap3[i][j] = 0;
				
				map[i][j].addMouseListener(this); 
				map[i][j].addMouseMotionListener(this); 
				map[i][j].setBounds(0,0,dirt.getIconWidth(),dirt.getIconHeight()); 
				map2[i][j].addMouseListener(this); 
				map2[i][j].addMouseMotionListener(this); 
				map2[i][j].setBounds(0,0,dirt.getIconWidth(),dirt.getIconHeight()); 
				map3[i][j].addMouseListener(this); 
				map3[i][j].addMouseMotionListener(this); 
				map3[i][j].setBounds(0,0,dirt.getIconWidth(),dirt.getIconHeight()); 

				mapLayer[i][j] = new JLayeredPane(); 
				mapLayer[i][j].setPreferredSize(new Dimension(25,25)); 
				mapLayer[i][j].addMouseListener(this); 
				mapLayer[i][j].addMouseMotionListener(this); 
				mapLayer[i][j].add(map[i][j],JLayeredPane.DEFAULT_LAYER, 0); 
				mapLayer[i][j].add(map2[i][j],JLayeredPane.DEFAULT_LAYER, 1); 
				mapLayer[i][j].add(map3[i][j],JLayeredPane.DEFAULT_LAYER, 2); 
				mapLayer[i][j].setBounds(0, 0, 25, 25); 

				mapPanel.add(mapLayer[i][j]); 

			} 
		} 

		editPanel = new JPanel(); 
		editPanel.setLayout(new GridLayout(5, 1)); 

		editButtons.add(new JButton(editEraser)); 
		editButtons.add(new JButton(editSelect)); 
		editButtons.add(new JButton(editNewIcon)); 


		entityPanel  = new JPanel();
		entityPanel.setLayout(new GridLayout(1, 20));
		entityScrollPane =  new JScrollPane(entityPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 

		itemPanel = new JPanel(); 
		itemPanel.setLayout(new GridLayout(1, 20)); 
		itemScrollPane = new JScrollPane(itemPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 

		terrainPanel = new JPanel(); 
		terrainPanel.setLayout(new GridLayout(1, 20)); 
		terrainScrollPane = new JScrollPane(terrainPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 

		objectPanel = new JPanel();  
		objectPanel.setLayout(new GridLayout(1, 20)); 
		objectScrollPane = new JScrollPane(objectPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 

		readAllPictures(); 
		for (int i = 0; i < list.size(); i++){

			if (list.get(i).get(1).equals("terrain")){
				terrainButtons.add(new JButton(new ImageIcon("images/" + list.get(i).get(3))));
				terrainList.add(list.get(i));
			}else if (list.get(i).get(1).equals("object")){
				objectButtons.add(new JButton(new ImageIcon("images/" + list.get(i).get(3))));
				objectList.add(list.get(i));
			}else if (list.get(i).get(1).equals("item")){
				itemButtons.add(new JButton(new ImageIcon("images/" + list.get(i).get(3))));
				itemList.add(list.get(i));
			}else if (list.get(i).get(1).equals("entity")){
				entityButtons.add(new JButton(new ImageIcon("images/" + list.get(i).get(3))));
				entityList.add(list.get(i));
			}
		}


		for (int i = 0; i < editButtons.size(); i++){ 
			editButtons.get(i).addActionListener(this); 
			
			editPanel.add(editButtons.get(i)); 
		} 

		for (int i = 0; i < entityButtons.size(); i++){ 
			entityButtons.get(i).addActionListener(this); 
			entityButtons.get(i).setToolTipText("<html>Name: " + entityList.get(i).get(2) + "<br>" +
					"Level: " + entityList.get(i).get(4) + "<br>" +
					"IsPlayer: " + entityList.get(i).get(5) + "<br>" +
					"TotalHealth: " + entityList.get(i).get(6) + "<br>" +
					"CurrentHealth: " + entityList.get(i).get(7) + "<br>" +
					"TotalMagic: " + entityList.get(i).get(8) + "<br>" +
					"CurrentMagic: " + entityList.get(i).get(9) + "<br>" +
					"Attack: " + entityList.get(i).get(10) + "<br>" +
					"Defense: " + entityList.get(i).get(11) + "<br>" +
					"Speed: " + entityList.get(i).get(12) + "<br>" +
					"Experience: " + entityList.get(i).get(13) + "<br>" +
					"LevelUpPoints: " + entityList.get(i).get(14) + "<br>" +
					"Abilities: " + entityList.get(i).get(15) + "<br>" +
					"EquippedItem: " + entityList.get(i).get(16) + "<br>" +
					"TriggerType: " + entityList.get(i).get(17) + "<br>" +
					"TriggerState: " + entityList.get(i).get(18) + "</html>");
			entityPanel.add(entityButtons.get(i)); 
		} 
		for (int i = 0; i < itemButtons.size(); i++){ 
			itemButtons.get(i).addActionListener(this); 
			itemButtons.get(i).setToolTipText("<html>Name: " + itemList.get(i).get(2) + "<br>" +
					"Description: " + itemList.get(i).get(4) + "<br>" +
					"LevelRequired: " + itemList.get(i).get(5) + "<br>" +
					"Attack Bonus: " + itemList.get(i).get(6) + "<br>" +
					"Defense Bonus: " + itemList.get(i).get(7) + "<br>" +
					"Speed Bonus" + itemList.get(i).get(8) + "<br>" +
					"Health Bonus: " + itemList.get(i).get(9) + "<br>" +
					"Magic Bonus: " + itemList.get(i).get(10) + "<br>" +
					"IsEquipable: " + itemList.get(i).get(11) + "<br>" +
					"TriggerType: " + itemList.get(i).get(12) + "<br>" +
					"TriggerState" + itemList.get(i).get(13) + "</html>");
			itemPanel.add(itemButtons.get(i)); 
		} 

		
		
		for (int i = 0; i < terrainButtons.size(); i++){ 
			terrainButtons.get(i).addActionListener(this); 
			terrainButtons.get(i).setToolTipText("<html>Name: " + terrainList.get(i).get(2) + "<br>" +
			"IsWalkable: " + terrainList.get(i).get(4) + "<br>" +
			"Layer: " + terrainList.get(i).get(5) + "<br>" + 
			"Encounter Rate: " + terrainList.get(i).get(6) + "<br>" + 
			"TriggerType: " + terrainList.get(i).get(7) + "<br>" +
			"TriggerState: " + terrainList.get(i).get(8) + "</html>");
			terrainPanel.add(terrainButtons.get(i)); 
		} 

		
		
		for (int i = 0; i < objectButtons.size(); i++){ 
			objectButtons.get(i).addActionListener(this); 
			objectButtons.get(i).setToolTipText("<html>Name: " + objectList.get(i).get(2) + "<br>" +
					"Description: " + objectList.get(i).get(4) + "<br>" +
					"Layer: " + objectList.get(i).get(5) + "<br>" + 
					"IsPushable: " + objectList.get(i).get(6) + "<br>" + 
					"IsTrap: " + objectList.get(i).get(7) + "<br>" + 
					"IsSignPost: " + objectList.get(i).get(8) + "<br>" + 
					"IsJumpable: " + objectList.get(i).get(9) + "<br>" + 
					"IsHole: " + objectList.get(i).get(10)  + "<br>" + 
					"TriggerType: " + objectList.get(i).get(11) + "<br>" +
					"TriggerState: " + objectList.get(i).get(12) +  "</html>");
			objectPanel.add(objectButtons.get(i)); 
		} 


		tabPane = new JTabbedPane(); 
		tabPane.addTab("Terrain", terrainScrollPane); 	// Terrain
		tabPane.addTab("Objects", objectScrollPane); // Object
		tabPane.addTab("Items", itemScrollPane); 		// Item
		tabPane.addTab("Entities", entityScrollPane);	// Monster

		mapScrollPane = new JScrollPane(mapPanel); 

		frame.add(tabPane, BorderLayout.SOUTH); 
		frame.add(mapScrollPane, BorderLayout.CENTER); 
		frame.add(editPanel, BorderLayout.WEST); 
		frame.setVisible(true); 

	} 

	public int findNewIdNumber(){
		int num = 1;
		boolean found = false;
		boolean foundInList = false;
		while (!found){
			for (int i = 0; i < list.size(); i++){
				if (num == Integer.parseInt((list.get(i).get(0)))){
					foundInList = true;
				}
			}
			if (foundInList){
				foundInList = false;
				num++;
			}else{
				found = true;
			}
		}
		return num;
	}
	private void readAllPictures(){ 
		ArrayList<String> info = new ArrayList<String>(); 

		try {

			File fXmlFile = new File("temp.txt");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			System.out.println("Game Name: " + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("terrain");
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					info = new ArrayList<String>();
					Element eElement = (Element) nNode;

					info.add(getTagValue("id", eElement));
					info.add("terrain");
					info.add(getTagValue("name", eElement));
					info.add(getTagValue("imageFileName", eElement));
					info.add(getTagValue("isWalkable", eElement));
					info.add(getTagValue("layer", eElement));
					info.add(getTagValue("encounterRate", eElement));
					info.add(getTagValue("triggerType", eElement));
					info.add(getTagValue("triggerState", eElement));
					list.add(info);
				}
			}

			nList = doc.getElementsByTagName("object");
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					info = new ArrayList<String>();
					Element eElement = (Element) nNode;

					info.add(getTagValue("id", eElement));
					info.add("object");
					info.add(getTagValue("name", eElement));
					info.add(getTagValue("imageFileName", eElement));
					info.add(getTagValue("description", eElement));
					info.add(getTagValue("layer", eElement));
					info.add(getTagValue("isPushable", eElement));
					info.add(getTagValue("isTrap", eElement));
					info.add(getTagValue("isSignPost", eElement));
					info.add(getTagValue("isJumpable", eElement));
					info.add(getTagValue("isHole", eElement));
					info.add(getTagValue("triggerType", eElement));
					info.add(getTagValue("triggerState", eElement));
					list.add(info);
				}
			}

			nList = doc.getElementsByTagName("item");
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					info = new ArrayList<String>();
					Element eElement = (Element) nNode;

					info.add(getTagValue("id", eElement));
					info.add("item");
					info.add(getTagValue("name", eElement));
					info.add(getTagValue("imageFileName", eElement));
					info.add(getTagValue("description", eElement));
					info.add(getTagValue("levelRequired", eElement));
					info.add(getTagValue("attack", eElement));
					info.add(getTagValue("defense", eElement));
					info.add(getTagValue("speed", eElement));
					info.add(getTagValue("health", eElement));
					info.add(getTagValue("magic", eElement));
					info.add(getTagValue("isEquipable", eElement)); 
					info.add(getTagValue("triggerType", eElement));
					info.add(getTagValue("triggerState", eElement));
					list.add(info);
				}
			}

			nList = doc.getElementsByTagName("entity");
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					info = new ArrayList<String>();
					Element eElement = (Element) nNode;

					info.add(getTagValue("id", eElement));
					info.add("entity");
					info.add(getTagValue("name", eElement));
					info.add(getTagValue("imageFileName", eElement));
					info.add(getTagValue("level", eElement));
					info.add(getTagValue("isPlayer", eElement));
					info.add(getTagValue("totalHealth", eElement));
					info.add(getTagValue("currentHealth", eElement));
					info.add(getTagValue("totalMagic", eElement));
					info.add(getTagValue("currentMagic", eElement));
					info.add(getTagValue("attack", eElement));
					info.add(getTagValue("defense", eElement)); 
					info.add(getTagValue("speed", eElement)); 
					info.add(getTagValue("experience", eElement)); 
					info.add(getTagValue("levelUpPoints", eElement)); 
					info.add(getTagValue("abilities", eElement)); 
					info.add(getTagValue("equippedItem", eElement));
					info.add(getTagValue("triggerType", eElement));
					info.add(getTagValue("triggerState", eElement));
					
					/* If multiple abilities use following.
					 * temp = getTagValue("abilities", eElement));
					 * ArrayList<String> tempAbilities = new ArrayList<String>();
					 * tempAbilities = temp.split(",").toArrayList();
					 * for (int i = 0; i < tempAbilities.size; i++){
					 * 		info.add(tempAbilities.get(i);
					 *  }
					 */
					list.add(info);
				}
			}

/* Prints out all objects, entities, items, and terrain attributes
			for (int i = 0; i < list.size(); i++){
				for (int j = 0; j < list.get(i).size(); j++){
					System.out.print(list.get(i).get(j) + " ");
				}
				System.out.println();
			}
*/
		} catch (Exception e) {
			e.printStackTrace();
		}






	} 

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}
	@Override 
	public void mouseClicked(MouseEvent e) { 
		if (e.getClickCount() == 2){ 
			for (int i = 0; i < map.length; i++){ 
				for (int j = 0; j < map[0].length; j++){ 
					if ((e.getSource() == map[i][j]) && (!map2[i][j].getIcon().equals(blank))){ 
						System.out.println("Properties Screen"); 
					} 
				} 
			} 

		} 

	} 

	@Override 
	public void mouseEntered(MouseEvent e) { 
		for (int i = 0; i < map.length; i++){ 
			for (int j = 0; j < map[0].length; j++){ 
				if ((e.getSource() == map[i][j]) && mousePressed){ 
					if (selectedIcon != null){ 
						if (layerNum == 10){ 
							map[i][j].setIcon(blank); 
							map2[i][j].setIcon(blank); 
							map3[i][j].setIcon(defaultTerrain); 
							mapLayer[i][j].add(map[i][j], JLayeredPane.DEFAULT_LAYER, 0); 
							mapLayer[i][j].add(map2[i][j], JLayeredPane.DEFAULT_LAYER, 1); 
							mapLayer[i][j].add(map3[i][j], JLayeredPane.DEFAULT_LAYER, 2); 
						}else if (layerNum == 0){ 
							map[i][j].setIcon(selectedIcon); 
							mapLayer[i][j].add(map[i][j], JLayeredPane.DEFAULT_LAYER, layerNum); 
						}else if (layerNum == 1){ 
							map2[i][j].setIcon(selectedIcon); 
							mapLayer[i][j].add(map2[i][j], JLayeredPane.DEFAULT_LAYER, layerNum); 
						}else if (layerNum == 2){ 
							map3[i][j].setIcon(selectedIcon); 
							mapLayer[i][j].add(map3[i][j], JLayeredPane.DEFAULT_LAYER, layerNum); //, new Integer(layerNum), 0); 
						} 
					} 
				} 
				else if ((e.getSource() == map[i][j]) && (!mousePressed) && (selectedIcon != null)){ 


					if (layerNum == 0){ 
						temp = (ImageIcon) map[i][j].getIcon(); 
						map[i][j].setIcon(selectedIcon); 
					}else if (layerNum == 1){ 
						temp = (ImageIcon) map2[i][j].getIcon(); 
						map2[i][j].setIcon(selectedIcon); 
					}else if (layerNum == 2){ 
						temp = (ImageIcon) map3[i][j].getIcon(); 
						map3[i][j].setIcon(selectedIcon); 
					} 

				} 

			} 
		} 
	} 

	@Override 
	public void mouseExited(MouseEvent e) { 
		for (int i = 0; i < map.length; i++){ 
			for (int j = 0; j < map[0].length; j++){ 
				if ((e.getSource() == map[i][j]) && (mousePressed == false) && (selectedIcon != null)){ 

					if (layerNum == 0){ 
						map[i][j].setIcon(temp); 
					}else if (layerNum == 1){ 
						map2[i][j].setIcon(temp); 
					}else if (layerNum == 2){ 
						map3[i][j].setIcon(temp); 
					} 

				} 
			} 
		} 
	} 

	@Override 
	public void mousePressed(MouseEvent e) { 
		mousePressed = true; 
		for (int i = 0; i < map.length; i++){ 
			for (int j = 0; j < map[0].length; j++){ 
				if (e.getSource() == map[i][j]){ 
					if (selectedIcon != null){ 
						if (layerNum == 10){ 
							map[i][j].setIcon(blank); 
							map2[i][j].setIcon(blank); 
							map3[i][j].setIcon(defaultTerrain); 
							mapLayer[i][j].add(map[i][j], JLayeredPane.DEFAULT_LAYER, 0); 
							mapLayer[i][j].add(map2[i][j], JLayeredPane.DEFAULT_LAYER, 1); 
							mapLayer[i][j].add(map3[i][j], JLayeredPane.DEFAULT_LAYER, 2); 
						}else if (layerNum == 0){ 
							map[i][j].setIcon(selectedIcon); 
							mapLayer[i][j].add(map[i][j], JLayeredPane.DEFAULT_LAYER, layerNum); 
						}else if (layerNum == 1){ 
							map2[i][j].setIcon(selectedIcon); 
							mapLayer[i][j].add(map2[i][j], JLayeredPane.DEFAULT_LAYER, layerNum); 
						}else if (layerNum == 2){ 
							map3[i][j].setIcon(selectedIcon); 
							mapLayer[i][j].add(map3[i][j], JLayeredPane.DEFAULT_LAYER, layerNum); //, new Integer(layerNum), 0); 
						} 
					} 
				} 
			} 
		} 
	} 

	@Override 
	public void mouseDragged(MouseEvent e) { 
		for (int i = 0; i < map.length; i++){ 
			for (int j = 0; j < map[0].length; j++){ 
				if ((e.getSource() == map[i][j]) && mousePressed){ 
					if (selectedIcon != null){ 
						if (layerNum == 10){ 
							map[i][j].setIcon(blank); 
							map2[i][j].setIcon(blank); 
							map3[i][j].setIcon(dirt); 
							mapLayer[i][j].add(map[i][j], JLayeredPane.DEFAULT_LAYER, 0); 
							mapLayer[i][j].add(map2[i][j], JLayeredPane.DEFAULT_LAYER, 1); 
							mapLayer[i][j].add(map3[i][j], JLayeredPane.DEFAULT_LAYER, 2); 
						}else if (layerNum == 0){ 
							map[i][j].setIcon(selectedIcon); 
							mapLayer[i][j].add(map[i][j], JLayeredPane.DEFAULT_LAYER, layerNum); 
						}else if (layerNum == 1){ 
							map2[i][j].setIcon(selectedIcon); 
							mapLayer[i][j].add(map2[i][j], JLayeredPane.DEFAULT_LAYER, layerNum); 
						}else if (layerNum == 2){ 
							map3[i][j].setIcon(selectedIcon); 
							mapLayer[i][j].add(map3[i][j], JLayeredPane.DEFAULT_LAYER, layerNum); //, new Integer(layerNum), 0); 
						} 
					} 
				}else if ((e.getSource() == map[i][j]) && (!mousePressed) && (selectedIcon != null)){ 


					if (layerNum == 0){ 
						temp = (ImageIcon) map[i][j].getIcon(); 
						map[i][j].setIcon(selectedIcon); 
					}else if (layerNum == 1){ 
						temp = (ImageIcon) map2[i][j].getIcon(); 
						map2[i][j].setIcon(selectedIcon); 
					}else if (layerNum == 2){ 
						temp = (ImageIcon) map3[i][j].getIcon(); 
						map3[i][j].setIcon(selectedIcon); 
					} 

				} 

			} 
		} 
	} 


	@Override 
	public void mouseMoved(MouseEvent e) { 

	} 

	@Override 
	public void mouseReleased(MouseEvent e) { 
		mousePressed = false; 
		temp = selectedIcon; 
	} 

	@Override 
	public void actionPerformed(ActionEvent e) { 

		if (e.getSource() == quit){ 
			System.exit(0); 
		}else if (e.getSource() == newMap){ 

		}else if (e.getSource() == load){  /* TODO File select */ 
			try { 

				FileInputStream fstream = new FileInputStream("save.txt"); 
				DataInputStream in = new DataInputStream(fstream); 
				BufferedReader br = new BufferedReader(new InputStreamReader(in)); 
				String strLine = br.readLine(); 
				String[] mapSize = strLine.split(" ");                            // Acquires number of rows and columns 



			} catch (FileNotFoundException e1) { 
				e1.printStackTrace(); 
			} catch (IOException e1) { 
				e1.printStackTrace(); 
			} 

		}else if (e.getSource() == save){ 


			/* SAVE FORMAT 
			 * Rows Columns                  Grid Size 
			 * pic pic pic                    Top Level Grid 
			 * pic pic pic 
			 * pic pic pic 
			 *  
			 * pic pic pic                    Main Level Grid 
			 * pic pic pic 
			 * pic pic pci 
			 *  
			 * pic pic pic                    Ground Level Grid 
			 * pic pic pic 
			 * pic pic pic 
			 *  
			 *  
			 */ 


			f = new File("save.txt");    /* TODO Ask user file name */ 

			if (!f.exists()){ 
				try { 
					f.createNewFile(); 
				} catch (IOException e1) {} 
			} 

			try { 
				FileWriter fstream = new FileWriter(f.getName()); 
				BufferedWriter out = new BufferedWriter(fstream); 
				String temp = map.length + " " + map[0].length; 
				out.write(temp + "\n"); 
				temp = ""; 

				for (int i = 0; i < map.length; i++){ 
					for (int j = 0; j < map[i].length; j++){ 
						temp += map[i][j].getIcon() + " "; 
					} 
					temp += "\n"; 
					out.write(temp); 
					temp = ""; 
				} 
				out.write("\n"); 

				for (int i = 0; i < map2.length; i++){ 
					for (int j = 0; j < map2[i].length; j++){ 
						temp += map2[i][j].getIcon() + " "; 
					} 
					temp += "\n"; 
					out.write(temp); 
					temp = ""; 
				} 
				out.write("\n"); 

				for (int i = 0; i < map3.length; i++){ 
					for (int j = 0; j < map3[i].length; j++){ 
						temp += map3[i][j].getIcon() + " "; 
					} 
					temp += "\n"; 
					out.write(temp); 
					temp = ""; 
				} 
				out.write("\n"); 


				out.close(); 

			} catch (IOException e1) {} 



		}else if (e.getSource() == editButtons.get(0)){    // Eraser 
			selectedIcon = dirt; 
			layerNum = 10; 
		}else if (e.getSource() == editButtons.get(1)){    // Select Tool 
			selectedIcon = null; 

		}else if (e.getSource() == editButtons.get(2)){ 	// New Icon 
			selectedIcon = null; 
			int returnVal = fc.showOpenDialog(null); 
			if (returnVal == JFileChooser.APPROVE_OPTION){ 
				newFilePic = fc.getSelectedFile(); 
				System.out.println("new picture is " + newFilePic.getName()); 
				System.out.println("new picture location is " + fc.getCurrentDirectory()); 
				
			
			//	frame.remove(mapScrollPane);
			//	frame.add(newPanel, BorderLayout.CENTER);
				
				
				
				//String[] temp = newFilePic.getAbsolutePath().split("\");"
			//	name = fc.getCurrentDirectory() + "/" + newFilePic.getName();
					name = "images/" + newFilePic.getName();
				newPanelImageButton.setIcon(new ImageIcon(name));
				/* TODO in the continue button set the 1,2,3,4 to icon */
				
				
				frame.remove(mapScrollPane);
				frame.remove(editPanel);
				frame.remove(tabPane);
				frame.add(newEntityPanel, BorderLayout.CENTER);
				frame.pack();
				frame.setSize(600, 600);
				
                
			} 
		}else if ((e.getSource() == newPanelSave1) || (e.getSource() == newPanelSave2)
			|| (e.getSource() == newPanelSave3) || (e.getSource() == newPanelSave4)){
	
			/*
			BufferedImage image = null;
	        try {
	 
	            
	            image = ImageIO.read(new File(name));
	 
	            if (newPanelImageTypeOption.getSelectedIndex() == 0){
	            	ImageIO.write(image, "jpg",new File("images/out.png"));
	            } else if (newPanelImageTypeOption.getSelectedIndex() == 1){
	            	ImageIO.write(image, "gif",new File("images/out.jpg"));
	            }else //(newPanelImageTypeOption.getSelectedIndex() == 2){
	            	ImageIO.write(image, "png",new File("images/out.gif"));
	           // }
	            
	        } catch (IOException e2) {
	        	e2.printStackTrace();
	        }*/
			
			// Write to Manifest file the new saved object
			f = new File("temp2.txt");    /* TODO Ask user file name */ 
		
			
			if (!f.exists()){ 
				try { 
					f.createNewFile(); 
				} catch (IOException e1) {} 
			} 

			try { 
				/*TODO*/
				FileWriter fstream = new FileWriter(f.getName()); 
				BufferedWriter out = new BufferedWriter(fstream); 
				
				
				out.write("<RPG_GAME>");
				
			//	for (i
				out.write("\n"); 

				out.write("</RPG_GAME>");
				out.close(); 

			} catch (IOException e1) {} 

		}else if (e.getSource() == newPanelCancel){
			frame.remove(newPanel);
			
			frame.add(tabPane, BorderLayout.SOUTH); 
			frame.add(mapScrollPane, BorderLayout.CENTER); 
			frame.add(editPanel, BorderLayout.WEST); 
			frame.pack();
			frame.setSize(600,600);
		}else if (e.getSource() == newPanelCancel1){
			frame.remove(newTerrainPanel);
			
			frame.add(tabPane, BorderLayout.SOUTH); 
			frame.add(mapScrollPane, BorderLayout.CENTER); 
			frame.add(editPanel, BorderLayout.WEST); 
			frame.pack();
			frame.setSize(600,600);
		}
		else if (e.getSource() == newPanelCancel2){
			frame.remove(newObjectPanel);
			
			frame.add(tabPane, BorderLayout.SOUTH); 
			frame.add(mapScrollPane, BorderLayout.CENTER); 
			frame.add(editPanel, BorderLayout.WEST); 
			frame.pack();
			frame.setSize(600,600);
		}
		else if (e.getSource() == newPanelCancel3){
			frame.remove(newItemPanel);
			
			frame.add(tabPane, BorderLayout.SOUTH); 
			frame.add(mapScrollPane, BorderLayout.CENTER); 
			frame.add(editPanel, BorderLayout.WEST); 
			frame.pack();
			frame.setSize(600,600);
		}else if (e.getSource() == newPanelCancel4){
			frame.remove(newEntityPanel);
			
			frame.add(tabPane, BorderLayout.SOUTH); 
			frame.add(mapScrollPane, BorderLayout.CENTER); 
			frame.add(editPanel, BorderLayout.WEST); 
			frame.pack();
			frame.setSize(600,600);
		}
		else{ 

			
			for (int i = 0; i < terrainButtons.size(); i++){ 
				terrainButtons.get(i).setEnabled(true);
				if (e.getSource() == terrainButtons.get(i)){ 
					terrainButtons.get(i).setEnabled(false);
					selectedIcon = (ImageIcon) terrainButtons.get(i).getIcon(); 
					selectedID = terrainList.get(i).get(0);
					layerNum = Integer.parseInt(terrainList.get(i).get(5));
				} 

			} 

			for (int i = 0; i < entityButtons.size(); i++){ 
				entityButtons.get(i).setEnabled(true);
				if (e.getSource() == entityButtons.get(i)){ 
					entityButtons.get(i).setEnabled(false);
					selectedIcon = (ImageIcon) entityButtons.get(i).getIcon(); 
					selectedID = entityList.get(i).get(0);
					layerNum = 1;
				} 
			} 

			for (int i = 0; i < objectButtons.size(); i++){ 
				objectButtons.get(i).setEnabled(true);
				if (e.getSource() == objectButtons.get(i)){ 
					objectButtons.get(i).setEnabled(false);
					selectedIcon = (ImageIcon) objectButtons.get(i).getIcon(); 
					selectedID = objectList.get(i).get(0);
					layerNum = Integer.parseInt(objectList.get(i).get(5));
				} 
			} 

			for (int i = 0; i < itemButtons.size(); i++){ 
				itemButtons.get(i).setEnabled(true);
				if (e.getSource() == itemButtons.get(i)){ 
					itemButtons.get(i).setEnabled(false);
					selectedIcon = (ImageIcon) itemButtons.get(i).getIcon(); 
					selectedID = itemList.get(i).get(0);
					layerNum = 1;
				} 
			} 

		} 
	} 
}