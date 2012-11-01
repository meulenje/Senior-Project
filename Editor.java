package rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.*;


public class Editor implements ActionListener, MouseListener, MouseMotionListener{


	File f;

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
	JPanel otherPanel;
	JPanel groundPanel;
	JPanel mapPanel;
	JPanel editPanel;
	JPanel buildingPanel;

	JTabbedPane tabPane;

	/* Scroll Panes */
	JScrollPane otherScrollPane;
	JScrollPane groundScrollPane;
	JScrollPane mapScrollPane;
	JScrollPane buildingScrollPane;

	/* Map Grid */
	JLabel[][] map;
	JLabel[][] map2;
	JLabel[][] map3;
	JLayeredPane[][] mapLayer;

	/* Map Options */
	JButton[] groundButtons;
	JButton[] otherButtons;
	JButton[] topButtons;
	JButton[] buildingButtons;
	JButton[] editButtons;

	ImageIcon selected;

	boolean mousePressed = false;


	int layerNum = 2;

	/* Images */
	protected ImageIcon blank = new ImageIcon("images/Blank.png");

	/* Edit Images */
	protected ImageIcon editEraser = new ImageIcon("images/editEraser.png");
	protected ImageIcon editSelect = new ImageIcon("images/editSelect.png");
	protected ImageIcon editNewIcon = new ImageIcon("images/editNewIcon.png");
	
	protected ImageIcon temp = new ImageIcon();
	protected ImageIcon temp2 = new ImageIcon();
	
	/* Background Images */
	protected ImageIcon dirt = new ImageIcon("images/Dirt.png");
	protected ImageIcon grey = new ImageIcon("images/Grey.png");
	protected ImageIcon floorTile = new ImageIcon("images/FloorTile.png");
	protected ImageIcon floorTile2 = new ImageIcon("images/FloorTile2.png");
	protected ImageIcon floorTile3 = new ImageIcon("images/FloorTile3.png");
	protected ImageIcon floorTile4 = new ImageIcon("images/FloorTile4.png");
	protected ImageIcon grass = new ImageIcon("images/Grass.png");
	protected ImageIcon water = new ImageIcon("images/Water.gif");
	protected ImageIcon xSpace = new ImageIcon("images/XSpace.png");
	protected ImageIcon voidSpace = new ImageIcon("images/VoidSpace.png");

	/*Foreground Images */
	protected ImageIcon gem = new ImageIcon("images/Gem.gif");
	protected ImageIcon hole = new ImageIcon("images/Hole.png");
	protected ImageIcon creature = new ImageIcon("images/Creature.png");
	protected ImageIcon horizontalTopWall = new ImageIcon("images/HorizontalTopWall.png");
	protected ImageIcon horizontalBottomWall = new ImageIcon("images/HorizontalBottomWall.png");
	protected ImageIcon verticalLeftWall = new ImageIcon("images/VerticalLeftWall.png");
	protected ImageIcon verticalRightWall = new ImageIcon("images/VerticalRightWall.png");
	protected ImageIcon LLBuildingWall = new ImageIcon("images/LLBuildingWall.png");
	protected ImageIcon LRBuildingWall = new ImageIcon("images/LRBuildingWall.png");
	protected ImageIcon ULBuildingWall = new ImageIcon("images/ULBuildingWall.png");
	protected ImageIcon URBuildingWall = new ImageIcon("images/URBuildingWall.png");
	protected ImageIcon door1 = new ImageIcon("images/Door1.png");
	protected ImageIcon door2 = new ImageIcon("images/Door2.png");
	protected ImageIcon lavaMonster = new ImageIcon("images/LavaMonster.gif");
	protected ImageIcon mushroom = new ImageIcon("images/Mushroom.png");
	protected ImageIcon pirate = new ImageIcon("images/Pirate.gif");
	protected ImageIcon playerFront = new ImageIcon("images/PlayerFront.gif");
	protected ImageIcon playerLeft = new ImageIcon("images/PlayerLeft.gif");
	protected ImageIcon playerRight = new ImageIcon("images/PlayerRight.gif");
	protected ImageIcon rock = new ImageIcon("images/Rock.png");
	protected ImageIcon spike = new ImageIcon("images/Spike.png");
	protected ImageIcon spiral = new ImageIcon("images/Spiral.gif");
	protected ImageIcon tree = new ImageIcon("images/Tree.png");

	/* Top Images */
	protected ImageIcon tallGrass = new ImageIcon("images/TallGrass.png");
	protected ImageIcon hideout = new ImageIcon("images/Hideout.png");
	protected ImageIcon chair = new ImageIcon("images/Chair.png");
	protected ImageIcon table = new ImageIcon("images/Table.png");
	protected ImageIcon bookcase = new ImageIcon("images/BookCase.png");
	protected ImageIcon whiteboard = new ImageIcon("images/WhiteBoard.png");



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

		for (int i = 0; i < mapLayer.length; i++){
			for (int j = 0; j < mapLayer[0].length; j++){

				// Top level
				map[i][j] = new JLabel(blank);

				// Main level
				map2[i][j] = new JLabel(blank);

				// Ground Level
				map3[i][j] = new JLabel(dirt);

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

		editButtons = new JButton[3];
		editButtons[0] = new JButton(editEraser);
		editButtons[1] = new JButton(editSelect);
		editButtons[2] = new JButton(editNewIcon);

		for (int i = 0; i < editButtons.length; i++){
			editButtons[i].addActionListener(this);
			editPanel.add(editButtons[i]);
		}

		otherPanel = new JPanel();
		otherPanel.setLayout(new GridLayout(1, 20));

		otherButtons = new JButton[8];
		otherButtons[0] = new JButton(creature);
		otherButtons[1] = new JButton(lavaMonster);
		otherButtons[2] = new JButton(spike);
		otherButtons[3] = new JButton(spiral);
		otherButtons[4] = new JButton(rock);
		otherButtons[5] = new JButton(mushroom);
		otherButtons[6] = new JButton(pirate);
		otherButtons[7] = new JButton(tree);

		otherScrollPane = new JScrollPane(otherPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		topButtons = new JButton[6];
		topButtons[0] = new JButton(tallGrass);
		topButtons[1] = new JButton(hideout);
		topButtons[2] = new JButton(chair);
		topButtons[3] = new JButton(table);
		topButtons[4] = new JButton(bookcase);
		topButtons[5] = new JButton(whiteboard);

		for (int i = 0; i < otherButtons.length; i++){
			otherButtons[i].addActionListener(this);
			otherPanel.add(otherButtons[i]);
		}
		for (int i = 0; i < topButtons.length; i++){
			topButtons[i].addActionListener(this);
			otherPanel.add(topButtons[i]);
		}

		groundPanel = new JPanel();
		groundScrollPane = new JScrollPane(groundPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		groundPanel.setLayout(new GridLayout(1, 20));
		groundButtons = new JButton[10];
		groundButtons[0] = new JButton(dirt);
		groundButtons[1] = new JButton(grass);
		groundButtons[2] = new JButton(water);
		groundButtons[3] = new JButton(floorTile);
		groundButtons[4] = new JButton(floorTile2);
		groundButtons[5] = new JButton(floorTile3);
		groundButtons[6] = new JButton(floorTile4);
		groundButtons[7] = new JButton(grey);
		groundButtons[8] = new JButton(xSpace);
		groundButtons[9] = new JButton(voidSpace);

		for (int i = 0; i < groundButtons.length; i++){
			groundButtons[i].addActionListener(this);
			groundPanel.add(groundButtons[i]);
		}
		mapScrollPane = new JScrollPane(mapPanel);

		buildingPanel = new JPanel();
		buildingButtons = new JButton[10];
		groundPanel.setLayout(new GridLayout(1, 20));

		buildingButtons[0] = new JButton(LLBuildingWall);
		buildingButtons[1] = new JButton(LRBuildingWall);
		buildingButtons[2] = new JButton(ULBuildingWall);
		buildingButtons[3] = new JButton(URBuildingWall);

		buildingButtons[4] = new JButton(verticalLeftWall);
		buildingButtons[5] = new JButton(verticalRightWall);

		buildingButtons[6] = new JButton(horizontalTopWall);
		buildingButtons[7] = new JButton(horizontalBottomWall);

		buildingButtons[8] = new JButton(door1);
		buildingButtons[9] = new JButton(door2);

		for (int i = 0; i < buildingButtons.length; i++){
			buildingButtons[i].addActionListener(this);
			buildingPanel.add(buildingButtons[i]);
		}
		buildingScrollPane = new JScrollPane(buildingPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		tabPane = new JTabbedPane();
		tabPane.addTab("Ground", groundScrollPane);
		tabPane.addTab("Building", buildingScrollPane);
		tabPane.addTab("Other", otherScrollPane);

		//	frame.add(otherScrollPane, BorderLayout.EAST);
		//	frame.add(groundScrollPane, BorderLayout.SOUTH);

		frame.add(tabPane, BorderLayout.SOUTH);
		frame.add(mapScrollPane, BorderLayout.CENTER);
		frame.add(editPanel, BorderLayout.WEST);
		frame.setVisible(true);

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
					if (selected != null){
						if (layerNum == 10){
							map[i][j].setIcon(blank);
							map2[i][j].setIcon(blank);
							map3[i][j].setIcon(dirt);
							mapLayer[i][j].add(map[i][j], JLayeredPane.DEFAULT_LAYER, 0);
							mapLayer[i][j].add(map2[i][j], JLayeredPane.DEFAULT_LAYER, 1);
							mapLayer[i][j].add(map3[i][j], JLayeredPane.DEFAULT_LAYER, 2);
						}else if (layerNum == 0){
							map[i][j].setIcon(selected);
							mapLayer[i][j].add(map[i][j], JLayeredPane.DEFAULT_LAYER, layerNum);
						}else if (layerNum == 1){
							map2[i][j].setIcon(selected);
							mapLayer[i][j].add(map2[i][j], JLayeredPane.DEFAULT_LAYER, layerNum);
						}else if (layerNum == 2){
							map3[i][j].setIcon(selected);
							mapLayer[i][j].add(map3[i][j], JLayeredPane.DEFAULT_LAYER, layerNum); //, new Integer(layerNum), 0);
						}
					}
				}else if ((e.getSource() == map[i][j]) && (!mousePressed) && (selected != null)){
					

					if (layerNum == 0){
						temp = (ImageIcon) map[i][j].getIcon();
						map[i][j].setIcon(selected);
					}else if (layerNum == 1){
						temp = (ImageIcon) map2[i][j].getIcon();
						map2[i][j].setIcon(selected);
					}else if (layerNum == 2){
						temp = (ImageIcon) map3[i][j].getIcon();
						map3[i][j].setIcon(selected);
					}

				}

			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		for (int i = 0; i < map.length; i++){
			for (int j = 0; j < map[0].length; j++){
				if ((e.getSource() == map[i][j]) && (mousePressed == false) && (selected != null)){
					
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
					if (selected != null){
						if (layerNum == 10){
							map[i][j].setIcon(blank);
							map2[i][j].setIcon(blank);
							map3[i][j].setIcon(dirt);
							mapLayer[i][j].add(map[i][j], JLayeredPane.DEFAULT_LAYER, 0);
							mapLayer[i][j].add(map2[i][j], JLayeredPane.DEFAULT_LAYER, 1);
							mapLayer[i][j].add(map3[i][j], JLayeredPane.DEFAULT_LAYER, 2);
						}else if (layerNum == 0){
							map[i][j].setIcon(selected);
							mapLayer[i][j].add(map[i][j], JLayeredPane.DEFAULT_LAYER, layerNum);
						}else if (layerNum == 1){
							map2[i][j].setIcon(selected);
							mapLayer[i][j].add(map2[i][j], JLayeredPane.DEFAULT_LAYER, layerNum);
						}else if (layerNum == 2){
							map3[i][j].setIcon(selected);
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
					if (selected != null){
						if (layerNum == 10){
							map[i][j].setIcon(blank);
							map2[i][j].setIcon(blank);
							map3[i][j].setIcon(dirt);
							mapLayer[i][j].add(map[i][j], JLayeredPane.DEFAULT_LAYER, 0);
							mapLayer[i][j].add(map2[i][j], JLayeredPane.DEFAULT_LAYER, 1);
							mapLayer[i][j].add(map3[i][j], JLayeredPane.DEFAULT_LAYER, 2);
						}else if (layerNum == 0){
							map[i][j].setIcon(selected);
							mapLayer[i][j].add(map[i][j], JLayeredPane.DEFAULT_LAYER, layerNum);
						}else if (layerNum == 1){
							map2[i][j].setIcon(selected);
							mapLayer[i][j].add(map2[i][j], JLayeredPane.DEFAULT_LAYER, layerNum);
						}else if (layerNum == 2){
							map3[i][j].setIcon(selected);
							mapLayer[i][j].add(map3[i][j], JLayeredPane.DEFAULT_LAYER, layerNum); //, new Integer(layerNum), 0);
						}
					}
				}else if ((e.getSource() == map[i][j]) && (!mousePressed) && (selected != null)){
					

					if (layerNum == 0){
						temp = (ImageIcon) map[i][j].getIcon();
						map[i][j].setIcon(selected);
					}else if (layerNum == 1){
						temp = (ImageIcon) map2[i][j].getIcon();
						map2[i][j].setIcon(selected);
					}else if (layerNum == 2){
						temp = (ImageIcon) map3[i][j].getIcon();
						map3[i][j].setIcon(selected);
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
				String[] mapSize = strLine.split(" ");							// Acquires number of rows and columns



			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}else if (e.getSource() == save){


			/* SAVE FORMAT
			 * Rows Columns  				Grid Size
			 * pic pic pic					Top Level Grid
			 * pic pic pic
			 * pic pic pic
			 * 
			 * pic pic pic					Main Level Grid
			 * pic pic pic
			 * pic pic pci
			 * 
			 * pic pic pic					Ground Level Grid
			 * pic pic pic
			 * pic pic pic
			 * 
			 * 
			 */


			f = new File("save.txt");	/* TODO Ask user file name */

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



		}else if (e.getSource() == editButtons[0]){	// Eraser
			selected = dirt;
			layerNum = 10;
		}else if (e.getSource() == editButtons[1]){	// Select Tool
			selected = null;

		}else if (e.getSource() == editButtons[2]){ // New Icon
			
		}
		else{


			for (int i = 0; i < groundButtons.length; i++){
				if (e.getSource() == groundButtons[i]){
					selected = (ImageIcon) groundButtons[i].getIcon();
					layerNum = 2;
				}

			}

			for (int i = 0; i < otherButtons.length; i++){
				if (e.getSource() == otherButtons[i]){
					selected = (ImageIcon) otherButtons[i].getIcon();
					layerNum = 1;

				}
			}

			for (int i = 0; i < buildingButtons.length; i++){
				if (e.getSource() == buildingButtons[i]){
					selected = (ImageIcon) buildingButtons[i].getIcon();
					layerNum = 1;
				}
			}

			for (int i = 0; i < topButtons.length; i++){
				if (e.getSource() == topButtons[i]){
					selected = (ImageIcon) topButtons[i].getIcon();
					layerNum = 0;
				}
			}
		}
	}


}
