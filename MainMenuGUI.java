package rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class MainMenuGUI extends JPanel implements ActionListener, KeyListener{

	private GameEngine GE; // link back to engine
	
	private JButton newButton;
	private JButton loadButton;
	private JButton editorButton;
	private JButton quitButton;
	private Color backgroundColor = Color.black;
	private Color foregroundColor = Color.white;
	
	public MainMenuGUI(GameEngine tempEngine)
	{
		GE = tempEngine; // link back to engine
		
		// create main menu gui
		this.setPreferredSize(new Dimension(GE.X_DIM, GE.Y_DIM + 60));
		this.setBackground(backgroundColor);
		this.setLayout(new FlowLayout());
        this.addKeyListener(this);  // This class has its own key listeners.
        this.setFocusable(true);    // Allow panel to get focus
		
		// show a logo image with text
		JPanel imagePanel = new JPanel(new BorderLayout());
		imagePanel.setPreferredSize(new Dimension(GE.Y_DIM - 10,400));
		imagePanel.setBackground(foregroundColor);
		JLabel titleText = new JLabel("Game Logo Here", JLabel.CENTER);
		titleText.setIcon(GE.PlayerOutline);
		imagePanel.add(titleText, BorderLayout.CENTER);
		this.add(imagePanel, BorderLayout.NORTH);
		
		// show a list of buttons
		JPanel buttonPanel = new JPanel(new GridLayout(4,0,5,5));
		buttonPanel.setPreferredSize(new Dimension(200, 150));
		buttonPanel.setOpaque(false);
		newButton = new JButton("New Game");
		newButton.addActionListener(this);
		buttonPanel.add(newButton);
		
		loadButton = new JButton("Load Game");
		loadButton.addActionListener(this);
		buttonPanel.add(loadButton);
		
		editorButton = new JButton("Map Editor");
		editorButton.addActionListener(this);
		buttonPanel.add(editorButton);
		
		quitButton = new JButton("Quit");
		quitButton.addActionListener(this);
		buttonPanel.add(quitButton);
		
		JPanel box = new JPanel();
		box.setPreferredSize(new Dimension(GE.Y_DIM -10, 240));
		box.setOpaque(false);
		box.add(buttonPanel, BorderLayout.CENTER);
		TitledBorder tb = new TitledBorder("Main Menu");
		tb.setTitleColor(foregroundColor);
		tb.setTitlePosition(2);
		tb.setTitleFont(new Font("sansserif",Font.BOLD,16));
		box.setBorder(tb);
		this.add(box, BorderLayout.SOUTH);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		
		if(source == newButton)
		{
			// make a new game!
			GE.newGame();
			this.setVisible(false);
		}
		else if(source == loadButton)
		{
			// load a game
			GE.loadGame();
			//this.setVisible(false);
			//GE.tabs.setVisible(true);
		}
		else if(source == editorButton)
		{
			// start the map editor
			Editor e = new Editor();
		}
		else if(source == quitButton)
		{
			// quit
			GE.quitProgram();
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
