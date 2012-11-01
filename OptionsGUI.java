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

@SuppressWarnings("serial")
public class OptionsGUI extends JPanel implements ActionListener, KeyListener{

	private GameEngine GE; // link back to engine
	
	private JButton saveButton;
	private JButton exitButton;
	
	public OptionsGUI(GameEngine tempEngine)
	{
		GE = tempEngine; // link back to engine
		
		// create main menu gui
		this.setPreferredSize(new Dimension(GE.X_DIM, GE.Y_DIM + 60));
		this.setBackground(GE.backgroundColor);
		this.setLayout(new FlowLayout());
        this.addKeyListener(this);  // This class has its own key listeners.
        this.setFocusable(true);    // Allow panel to get focus
		
        // list of options to change
        JPanel optionsPanel = new JPanel(new BorderLayout());
        optionsPanel.setPreferredSize(new Dimension(GE.Y_DIM - 10,400));
        optionsPanel.setBackground(GE.foregroundColor);
		JLabel titleText = new JLabel("under construction!", JLabel.CENTER);
		titleText.setIcon(GE.Book);
		optionsPanel.add(titleText, BorderLayout.CENTER);
		this.add(optionsPanel, BorderLayout.NORTH);
        
        
		// show a list of buttons
		JPanel buttonPanel = new JPanel(new GridLayout(6,0,5,5));
		buttonPanel.setPreferredSize(new Dimension(200, 220));
		buttonPanel.setOpaque(false);
		
		saveButton = new JButton("Save Changes");
		saveButton.addActionListener(this);
		buttonPanel.add(saveButton);
		
		exitButton = new JButton("Cancel");
		exitButton.addActionListener(this);
		buttonPanel.add(exitButton);
		
		JPanel box = new JPanel();
		box.setPreferredSize(new Dimension(GE.Y_DIM -10, 240));
		box.setOpaque(false);
		box.add(buttonPanel, BorderLayout.CENTER);
		TitledBorder tb = new TitledBorder("Options");
		tb.setTitleColor(GE.foregroundColor);
		tb.setBorder(BorderFactory.createLineBorder(GE.foregroundColor));
		tb.setTitlePosition(2);
		tb.setTitleFont(new Font("sansserif",Font.BOLD,16));
		box.setBorder(tb);
		this.add(box, BorderLayout.CENTER);
		
	} // end of constructor 
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		
		if(source == saveButton)
		{
			// save changes to options
			// TODO
			GE.printError("TODO");
		}
		else if(source == exitButton)
		{
			// go back to main menu
			GE.viewMainMenu();
		}
	}

	@Override
	public void keyPressed(KeyEvent k)
	{
		int keyCode = k.getKeyCode();
		
		// shortcut keys to certain actions
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
