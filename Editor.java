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
import java.io.File;
import java.io.IOException;

import javax.swing.*;


public class Editor implements ActionListener, MouseListener, MouseMotionListener{


	File f;

	/* Frame */
	JFrame frame;

	/* Menu */
	JMenuBar menubar;
	JMenu file;
	JMenuItem newMap;
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
	protected ImageIcon blank = new ImageIcon("Blank.png");

	/* Edit Images */
	protected ImageIcon editEraser = new ImageIcon("editEraser.png");
	protected ImageIcon editSelect = new ImageIcon("editSelect.png");

	/* Background Images */
	protected ImageIcon dirt = new ImageIcon("Dirt.png");
	protected ImageIcon floorTile = new ImageIcon("FloorTile.png");
	protected ImageIcon floorTile2 = new ImageIcon("FloorTile2.png");
	protected ImageIcon floorTile3 = new ImageIcon("FloorTile3.png");
	protected ImageIcon floorTile4 = new ImageIcon("FloorTile4.png");
	protected ImageIcon grass = new ImageIcon("Grass.png");
	protected ImageIcon water = new ImageIcon("Water.png");
	protected ImageIcon xSpace = new ImageIcon("XSpace.png");

	/*Foreground Images */
	protected ImageIcon gem = new ImageIcon("Gem.gif");
	protected ImageIcon hole = new ImageIcon("Hole.png");
	protected ImageIcon creature = new ImageIcon("Creature.png");
	protected ImageIcon horizontalBottomWall = new ImageIcon("HorizontalBottomWall.png");
	protected ImageIcon horizontalTopWall = new ImageIcon("HorizontalTopWall.png");
	protected ImageIcon horizontalWall = new ImageIcon("HorizontalWall.png");
	protected ImageIcon verticalLeftWall = new ImageIcon("VerticalLeftWall.png");
	protected ImageIcon verticalRightWall = new ImageIcon("VerticalRightWall.png");
	protected ImageIcon verticalWall = new ImageIcon("VerticalWall.png");
	protected ImageIcon LLBuildingWall = new ImageIcon("LLBuildingWall.png");
	protected ImageIcon LLWall = new ImageIcon("LLWall.png");
	protected ImageIcon LRBuildingWall = new ImageIcon("LRBuildingWall.png");
	protected ImageIcon LRWall = new ImageIcon("LRWall.png");
	protected ImageIcon ULBuildingWall = new ImageIcon("ULBuildingWall.png");
	protected ImageIcon ULWall = new ImageIcon("ULWall.png");
	protected ImageIcon URBuildingWall = new ImageIcon("URBuildingWall.png");
	protected ImageIcon URWall = new ImageIcon("URWall.png");
	protected ImageIcon lavaMonster = new ImageIcon("LavaMonster.gif");
	protected ImageIcon mushroom = new ImageIcon("Mushroom.png");
	protected ImageIcon pirate = new ImageIcon("Pirate.gif");
	protected ImageIcon playerFront = new ImageIcon("PlayerFront.gif");
	protected ImageIcon playerLeft = new ImageIcon("PlayerLeft.gif");
	protected ImageIcon playerRight = new ImageIcon("PlayerRight.gif");
	protected ImageIcon rock = new ImageIcon("Rock.png");
	protected ImageIcon spike = new ImageIcon("Spike.png");
	protected ImageIcon spiral = new ImageIcon("Spiral.gif");

	protected ImageIcon tallGrass = new ImageIcon("TallGrass.png");
	protected ImageIcon hideout = new ImageIcon("Hideout.png");





	public Editor() {

		frame = new JFrame("MAP EDITOR");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,600);
		menubar = new JMenuBar();
		file = new JMenu("File");
		newMap = new JMenuItem("New Map");
		save = new JMenuItem("Save");
		quit = new JMenuItem("Quit");
		newMap.addActionListener(this);
		save.addActionListener(this);
		quit.addActionListener(this);

		file.add(newMap);
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
				map[i][j] = new JLabel(blank);
				map2[i][j] = new JLabel(blank);
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

		editButtons = new JButton[2];
		editButtons[0] = new JButton(editEraser);
		editButtons[1] = new JButton(editSelect);

		for (int i = 0; i < editButtons.length; i++){
			editButtons[i].addActionListener(this);
			editPanel.add(editButtons[i]);
		}

		otherPanel = new JPanel();
		otherPanel.setLayout(new GridLayout(1, 20));

		otherButtons = new JButton[7];
		otherButtons[0] = new JButton(creature);
		otherButtons[1] = new JButton(lavaMonster);
		otherButtons[2] = new JButton(spike);
		otherButtons[3] = new JButton(spiral);
		otherButtons[4] = new JButton(rock);
		otherButtons[5] = new JButton(mushroom);
		otherButtons[6] = new JButton(pirate);


		otherScrollPane = new JScrollPane(otherPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		topButtons = new JButton[2];
		topButtons[0] = new JButton(tallGrass);
		topButtons[1] = new JButton(hideout);

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
		groundButtons = new JButton[8];
		groundButtons[0] = new JButton(dirt);
		groundButtons[1] = new JButton(grass);
		groundButtons[2] = new JButton(water);
		groundButtons[3] = new JButton(floorTile);
		groundButtons[4] = new JButton(floorTile2);
		groundButtons[5] = new JButton(floorTile3);
		groundButtons[6] = new JButton(floorTile4);
		groundButtons[7] = new JButton(xSpace);

		for (int i = 0; i < groundButtons.length; i++){
			groundButtons[i].addActionListener(this);
			groundPanel.add(groundButtons[i]);
		}
		mapScrollPane = new JScrollPane(mapPanel);

		buildingPanel = new JPanel();
		buildingButtons = new JButton[14];
		groundPanel.setLayout(new GridLayout(1, 20));

		buildingButtons[0] = new JButton(LLBuildingWall);
		buildingButtons[1] = new JButton(LRBuildingWall);
		buildingButtons[2] = new JButton(ULBuildingWall);
		buildingButtons[3] = new JButton(URBuildingWall);

		buildingButtons[4] = new JButton(horizontalBottomWall);
		buildingButtons[5] = new JButton(horizontalTopWall);
		buildingButtons[6] = new JButton(verticalLeftWall);
		buildingButtons[7] = new JButton(verticalRightWall);

		buildingButtons[8] = new JButton(horizontalWall);
		buildingButtons[9] = new JButton(verticalWall);
		buildingButtons[10] = new JButton(LLWall);
		buildingButtons[11] = new JButton(LRWall);
		buildingButtons[12] = new JButton(ULWall);
		buildingButtons[13] = new JButton(URWall);


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
							map[i][j].setIcon(selected);
							map2[i][j].setIcon(selected);
							map3[i][j].setIcon(selected);
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
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePressed = true;
		for (int i = 0; i < map.length; i++){
			for (int j = 0; j < map[0].length; j++){
				if (e.getSource() == map[i][j]){
					if (selected != null){
						if (layerNum == 10){
							map[i][j].setIcon(selected);
							map2[i][j].setIcon(selected);
							map3[i][j].setIcon(selected);
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
							map[i][j].setIcon(selected);
							map2[i][j].setIcon(selected);
							map3[i][j].setIcon(selected);
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

		}else if (e.getSource() == save){
			f = new File("save.txt");
			if (!f.exists()){
				try {
					f.createNewFile();

					/* TODO 
					 * Buffered Writer, write map to file
					 * */
				} catch (IOException e1) {
				}
			}
		}else if (e.getSource() == editButtons[0]){
			selected = dirt;
			layerNum = 10;
		}else if (e.getSource() == editButtons[1]){
			selected = null;
		}else{


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
