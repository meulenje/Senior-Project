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

public class MainMenuGUI extends JPanel implements ActionListener, KeyListener{

	private static final long serialVersionUID = 1L;
	private GameEngine GE; // link back to engine
	
	private JButton newButton;
	private JButton loadButton;
	private JButton optionsButton;
	private JButton editorButton;
	private JButton quitButton;
	private JLabel key;
	
	public MainMenuGUI(GameEngine tempEngine)
	{
		GE = tempEngine; // link back to engine
		
		// create main menu gui
		this.setPreferredSize(new Dimension(GE.X_DIM, GE.Y_DIM + 60));
		this.setBackground(GE.backgroundColor);
		this.setLayout(new FlowLayout());
        this.addKeyListener(this);  // This class has its own key listeners.
        this.setFocusable(true);    // Allow panel to get focus
		
		// show a logo image with text
		JPanel imagePanel = new JPanel(new BorderLayout());
		imagePanel.setPreferredSize(new Dimension(GE.Y_DIM - 10,400));
		imagePanel.setBackground(GE.foregroundColor);
		JLabel titleText = new JLabel("", JLabel.CENTER);
		titleText.setIcon(GE.RPGLogo);
		imagePanel.add(titleText, BorderLayout.CENTER);
		this.add(imagePanel, BorderLayout.NORTH);
		
		// show a list of buttons
		JPanel buttonPanel = new JPanel(new GridLayout(6,0,5,5));
		buttonPanel.setPreferredSize(new Dimension(200, 220));
		buttonPanel.setOpaque(false);
		newButton = new JButton("New Game");
		newButton.addActionListener(this);
		buttonPanel.add(newButton);
		
		loadButton = new JButton("Load Game");
		loadButton.addActionListener(this);
		buttonPanel.add(loadButton);
		
		optionsButton = new JButton("Options");
		optionsButton.addActionListener(this);
		buttonPanel.add(optionsButton);
		
		editorButton = new JButton("Map Editor");
		editorButton.addActionListener(this);
		buttonPanel.add(editorButton);
		
		quitButton = new JButton("Quit");
		quitButton.addActionListener(this);
		buttonPanel.add(quitButton);
		
		key = new JLabel("", JLabel.RIGHT);
		key.setForeground(Color.GRAY);
		buttonPanel.add(key);
		
		JPanel box = new JPanel();
		box.setPreferredSize(new Dimension(GE.Y_DIM -10, 240));
		box.setOpaque(false);
		box.add(buttonPanel, BorderLayout.CENTER);
		TitledBorder tb = new TitledBorder("Main Menu");
		tb.setTitleColor(GE.foregroundColor);
		tb.setBorder(BorderFactory.createLineBorder(GE.foregroundColor));
		tb.setTitlePosition(2);
		tb.setTitleFont(new Font("sansserif",Font.BOLD,16));
		box.setBorder(tb);
		this.add(box, BorderLayout.SOUTH);
		
	} // end of constructor 
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		
		if(source == newButton)
		{
			// make a new game!
			GE.newGame();
		}
		else if(source == loadButton)
		{
			// load a game
			GE.loadGame();
		}
		else if(source == optionsButton)
		{
			// view options panel
			GE.viewOptions();
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
	public void keyPressed(KeyEvent k)
	{
		int keyCode = k.getKeyCode();
		
		// debugging purposes (displays key input number
		key.setText(""+keyCode);
		
		// shortcut keys to certain actions
		if(keyCode == 49 || keyCode == 78 || keyCode == 10) // 1,n,ENTER = newGame
		{
			GE.newGame();
		}
		else if(keyCode == 50 || keyCode == 76) // 2 or l = loadGame
		{
			GE.loadGame();
		}
		else if(keyCode == 51 || keyCode == 79) // 3 or o = options
		{
			// TODO
		}
		else if(keyCode == 52 || keyCode == 77 || keyCode == 69) // 4,m,e = Map Editor
		{
			Editor e = new Editor();
		}
		else if(keyCode == 53 || keyCode == 81 || keyCode == 27) // 5,q,ESC = Quit
		{
			GE.quitProgram();
		}
	}

	@Override
	public void keyReleased(KeyEvent k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent k) {
		// TODO Auto-generated method stub
		
	}

}
