package rpg;

import java.awt.BorderLayout; 
import java.awt.Dimension; 
import java.awt.GridLayout; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.MouseEvent; 
import java.awt.event.MouseListener; 
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;  
import java.io.File;  
import java.io.FileWriter; 
import java.io.IOException; 
import java.util.ArrayList; 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


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

//	ArrayList<Integer> terrainList = new ArrayList<Integer>(); 
//	ArrayList<Integer> entityList = new ArrayList<Integer>(); 
//	ArrayList<Integer> itemList = new ArrayList<Integer>(); 
//
	ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>(); 


	ImageIcon selectedIcon; 

	int numPictures = 0; 
	String selectedID = "";
	
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

		fc = new JFileChooser(); 

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
			entityPanel.add(entityButtons.get(i)); 
		} 
		for (int i = 0; i < itemButtons.size(); i++){ 
			itemButtons.get(i).addActionListener(this); 
			itemPanel.add(itemButtons.get(i)); 
		} 

		for (int i = 0; i < terrainButtons.size(); i++){ 
			terrainButtons.get(i).addActionListener(this); 
			terrainPanel.add(terrainButtons.get(i)); 
		} 

		for (int i = 0; i < objectButtons.size(); i++){ 
			objectButtons.get(i).addActionListener(this); 
			objectPanel.add(objectButtons.get(i)); 
		} 



		tabPane = new JTabbedPane(); 
		tabPane.addTab("Terrain", terrainScrollPane); 	// Terrain
		tabPane.addTab("Objects", objectScrollPane); // Object
		tabPane.addTab("Items", itemScrollPane); 		// Item
		tabPane.addTab("Entities", entityScrollPane);	// Monster

		JPanel gluebox = new JPanel();
		gluebox.setLayout(new BorderLayout());
		gluebox.add(Box.createGlue(), BorderLayout.NORTH);
		gluebox.add(mapPanel, BorderLayout.CENTER);
		gluebox.add(Box.createGlue(), BorderLayout.WEST);
		
		mapScrollPane = new JScrollPane(); 
		mapScrollPane.setViewportView(gluebox);

		frame.add(tabPane, BorderLayout.SOUTH); 
		frame.add(mapScrollPane, BorderLayout.CENTER); 
		frame.add(editPanel, BorderLayout.WEST); 
		frame.setVisible(true); 

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
				System.out.println("new picture is" + newFilePic.getName()); 
			} 
		} 
		else{ 


			for (int i = 0; i < terrainButtons.size(); i++){ 
				if (e.getSource() == terrainButtons.get(i)){ 
					selectedIcon = (ImageIcon) terrainButtons.get(i).getIcon(); 
					selectedID = terrainList.get(i).get(0);
					layerNum = Integer.parseInt(terrainList.get(i).get(5));
				} 

			} 

			for (int i = 0; i < entityButtons.size(); i++){ 
				if (e.getSource() == entityButtons.get(i)){ 
					selectedIcon = (ImageIcon) entityButtons.get(i).getIcon(); 
					selectedID = entityList.get(i).get(0);
					layerNum = 1;
				} 
			} 

			for (int i = 0; i < objectButtons.size(); i++){ 
				if (e.getSource() == objectButtons.get(i)){ 
					selectedIcon = (ImageIcon) objectButtons.get(i).getIcon(); 
					selectedID = objectList.get(i).get(0);
					layerNum = Integer.parseInt(objectList.get(i).get(5));
				} 
			} 

			for (int i = 0; i < itemButtons.size(); i++){ 
				if (e.getSource() == itemButtons.get(i)){ 
					selectedIcon = (ImageIcon) itemButtons.get(i).getIcon(); 
					selectedID = itemList.get(i).get(0);
					layerNum = 1;
				} 
			} 

		} 
	} 


}