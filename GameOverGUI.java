package rpg;

import java.awt.BorderLayout;
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


public class GameOverGUI extends JPanel implements ActionListener, KeyListener{

	private static final long serialVersionUID = 1L;
	private GameEngine GE; // link back to engine
	
	private JButton continueButton;
	private JButton quitButton;
	
	public GameOverGUI(GameEngine tempEngine)
	{
		GE = tempEngine; // link back to engine
		
		// create main menu gui
		this.setPreferredSize(new Dimension(GE.Window_Width, GE.Window_Height));
		this.setBackground(GE.backgroundColor);
		this.setLayout(new FlowLayout());
        this.addKeyListener(this);  // This class has its own key listeners.
        this.setFocusable(true);    // Allow panel to get focus
		
		// show a logo image with text
		JPanel imagePanel = new JPanel(new BorderLayout());
		imagePanel.setPreferredSize(new Dimension(GE.Window_Width - 10,400));
		imagePanel.setBackground(GE.foregroundColor);
		JLabel titleText = new JLabel("", JLabel.CENTER);
		titleText.setIcon(GE.GameOverImage);
		imagePanel.add(titleText, BorderLayout.CENTER);
		this.add(imagePanel, BorderLayout.NORTH);
		
		// show a list of buttons
		JPanel buttonPanel = new JPanel(new GridLayout(0,2,5,5));
		buttonPanel.setPreferredSize(new Dimension(200, 60));
		buttonPanel.setOpaque(false);
		continueButton = new JButton("Yes");
		continueButton.addActionListener(this);
		buttonPanel.add(continueButton);
		
		quitButton = new JButton("No");
		quitButton.addActionListener(this);
		buttonPanel.add(quitButton);
		
		JPanel box = new JPanel();
		box.setPreferredSize(new Dimension(GE.Window_Width -10, 240));
		box.setOpaque(false);
		box.add(buttonPanel, BorderLayout.CENTER);
		TitledBorder tb = new TitledBorder("Continue?");
		tb.setTitleColor(GE.foregroundColor);
		tb.setBorder(BorderFactory.createLineBorder(GE.foregroundColor));
		tb.setTitlePosition(3);
		tb.setTitleJustification(2);
		tb.setTitleFont(new Font("sansserif",Font.BOLD,16));
		box.setBorder(tb);
		this.add(box, BorderLayout.SOUTH);
		
	} // end of constructor 
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		
		if(source == continueButton)
		{
			// try again!
			GE.newGame();
		}
		else if(source == quitButton)
		{
			// quit
			GE.endGame();
		}
	}

	@Override
	public void keyPressed(KeyEvent k)
	{
		int keyCode = k.getKeyCode();
		
		// shortcut keys to certain actions
		if(keyCode == 49 || keyCode == 89 || keyCode == 10) // 1,y,ENTER = continueButton
		{
			GE.newGame();
		}
		else if(keyCode == 50 || keyCode == 81 || keyCode == 78 || keyCode == 27) // 2,q,n,ESC = Quit
		{
			GE.endGame();
		}
	}

	@Override
	public void keyReleased(KeyEvent k) {
		// Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent k) {
		// Auto-generated method stub
	}

}
