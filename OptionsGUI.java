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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class OptionsGUI extends JPanel implements ActionListener, KeyListener, ChangeListener{

	private static final long serialVersionUID = 1L;
	private GameEngine GE; // link back to engine
	private boolean unsavedChange = false;
	
	// gui parts
	private JButton saveButton;
	private JButton exitButton;
	private JButton restoreButton;
	
	// gui options
	private JCheckBox musicEnabled;
    private JCheckBox soundEnabled;
    private JCheckBox fullScreenMode;
	private JCheckBox showHintsEnabled;
	
    private JButton backgroundColor;
	private JButton foregroundColor;
	private JButton highlightColor;
	
    private JSlider playerVisionRange; //3
    private JSlider monsterGridSpeed; //2
    private JSlider percentChanceOfEncounter; //0.05
    private JSlider clockSpeed;
    private JSlider opacityLevel;
	
	public OptionsGUI(GameEngine tempEngine)
	{
		GE = tempEngine; // link back to engine
		
		// create main menu gui
		this.setPreferredSize(new Dimension(GE.X_DIM, GE.Y_DIM + 60));
		this.setBackground(GE.backgroundColor);
		this.setLayout(new FlowLayout());
        this.addKeyListener(this);  // This class has its own key listeners.
        this.setFocusable(true);    // Allow panel to get focus
		
        // Build options panel
        JPanel op = new JPanel(new BorderLayout());
        op.setPreferredSize(new Dimension(GE.X_DIM - 10,GE.Y_DIM));
        op.setBackground(GE.foregroundColor);
        TitledBorder tb = new TitledBorder("Options");
        tb.setTitleJustification(2);
		tb.setTitleColor(GE.backgroundColor);
		tb.setBorder(BorderFactory.createLineBorder(GE.backgroundColor));
		tb.setTitlePosition(2);
		tb.setTitleFont(new Font("sansserif",Font.BOLD,16));
		op.setBorder(tb);
		// -----
		
		// List of CheckBoxes
		JPanel boxes = new JPanel(new GridLayout(4,0,5,15));
		boxes.setOpaque(false);
		boxes.setBorder(new TitledBorder(BorderFactory.createLineBorder(GE.highlightColor),
				"Gameplay", 0, 0, new Font("Verdana", Font.PLAIN, 16), GE.highlightColor));
		boxes.setOpaque(false);
		musicEnabled = new JCheckBox("Music");
		musicEnabled.addChangeListener(this);
		musicEnabled.setOpaque(false);
		boxes.add(musicEnabled);
		
	    soundEnabled = new JCheckBox("SFX");
	    soundEnabled.addChangeListener(this);
	    soundEnabled.setOpaque(false);
	    boxes.add(soundEnabled);
	    
	    fullScreenMode = new JCheckBox("Full Screen");
	    fullScreenMode.addChangeListener(this);
	    fullScreenMode.setOpaque(false);
	    boxes.add(fullScreenMode);
	    
		showHintsEnabled = new JCheckBox("Show Hints");
		showHintsEnabled.addChangeListener(this);
		showHintsEnabled.setOpaque(false);
		boxes.add(showHintsEnabled);
		// ------
		
		// GUI color options
		JPanel colors = new JPanel(new GridLayout(3,2,5,15));
		colors.setBorder(new TitledBorder(BorderFactory.createLineBorder(GE.highlightColor),
				"Colors", 0, 0, new Font("Verdana", Font.PLAIN, 16), GE.highlightColor));
		colors.setOpaque(false);
		backgroundColor = new JButton("");
		backgroundColor.addActionListener(this);
		colors.add(new JLabel("Background Color:"));
		colors.add(backgroundColor);
		
		foregroundColor = new JButton("");
		foregroundColor.addActionListener(this);
		colors.add(new JLabel("Foreground Color:"));
		colors.add(foregroundColor);
		
		highlightColor = new JButton("");
		highlightColor.addActionListener(this);
		colors.add(new JLabel("Highlighting Color:"));
		colors.add(highlightColor);
		// ------
		
		// Difficulty options
		JPanel difficult = new JPanel(new GridLayout(5,2,5,15));
		difficult.setBorder(new TitledBorder(BorderFactory.createLineBorder(GE.highlightColor),
				"Game Settings", 0, 0, new Font("Verdana", Font.PLAIN, 16), GE.highlightColor));
		difficult.setOpaque(false);
		clockSpeed = new JSlider(100,1000);
		clockSpeed.addChangeListener(this);
	    clockSpeed.setSnapToTicks(true);
	    clockSpeed.setMinorTickSpacing(50);
	    clockSpeed.setMajorTickSpacing(100);
	    clockSpeed.setPaintTicks(true);
	    clockSpeed.setPaintLabels(true);
	    clockSpeed.setOpaque(false);
	    difficult.add(new JLabel("Clock Speed (milliseconds)", JLabel.RIGHT));
	    difficult.add(clockSpeed);
		
		playerVisionRange = new JSlider(1,10);
		playerVisionRange.addChangeListener(this);
		playerVisionRange.setSnapToTicks(true);
		playerVisionRange.setMinorTickSpacing(1);
		playerVisionRange.setMajorTickSpacing(1);
		playerVisionRange.setPaintTicks(true);
		playerVisionRange.setPaintLabels(true);
		playerVisionRange.setOpaque(false);
		difficult.add(new JLabel("Player Vision Range", JLabel.RIGHT));
		difficult.add(playerVisionRange);
		
	    monsterGridSpeed = new JSlider(1,10);
	    monsterGridSpeed.addChangeListener(this);
	    monsterGridSpeed.setSnapToTicks(true);
	    monsterGridSpeed.setMinorTickSpacing(1);
	    monsterGridSpeed.setMajorTickSpacing(1);
	    monsterGridSpeed.setPaintTicks(true);
	    monsterGridSpeed.setPaintLabels(true);
	    monsterGridSpeed.setOpaque(false);
	    difficult.add(new JLabel("Monster Movement Speed", JLabel.RIGHT));
	    difficult.add(monsterGridSpeed);
	    
	    percentChanceOfEncounter = new JSlider(0,100);
	    percentChanceOfEncounter.addChangeListener(this);
	    percentChanceOfEncounter.setSnapToTicks(true);
	    percentChanceOfEncounter.setMinorTickSpacing(5);
	    percentChanceOfEncounter.setMajorTickSpacing(25);
	    percentChanceOfEncounter.setPaintTicks(true);
	    percentChanceOfEncounter.setPaintLabels(true);
	    percentChanceOfEncounter.setOpaque(false);
	    difficult.add(new JLabel("% Chance of Random Battles", JLabel.RIGHT));
	    difficult.add(percentChanceOfEncounter);
		
	    opacityLevel = new JSlider(0,100);
	    opacityLevel.addChangeListener(this);
	    opacityLevel.setSnapToTicks(true);
	    opacityLevel.setMinorTickSpacing(5);
	    opacityLevel.setMajorTickSpacing(25);
	    opacityLevel.setPaintTicks(true);
	    opacityLevel.setPaintLabels(true);
	    opacityLevel.setOpaque(false);
	    difficult.add(new JLabel("Brightness", JLabel.RIGHT));
	    difficult.add(opacityLevel);
	    
	    op.add(boxes, BorderLayout.CENTER);
	    op.add(colors, BorderLayout.EAST);
	    op.add(difficult, BorderLayout.NORTH);
		this.add(op, BorderLayout.CENTER);
        // -----
		
		// show a list of buttons at the bottom
		JPanel buttonPanel = new JPanel(new GridLayout(0,3,60,5));
		buttonPanel.setPreferredSize(new Dimension(GE.X_DIM - 20, 35));
		buttonPanel.setOpaque(false);
		
		saveButton = new JButton("Apply Changes");
		saveButton.addActionListener(this);
		saveButton.setEnabled(false);
		buttonPanel.add(saveButton);
		
		exitButton = new JButton("Close");
		exitButton.addActionListener(this);
		buttonPanel.add(exitButton);
		
		restoreButton = new JButton("Restore Default");
		restoreButton.addActionListener(this);
		buttonPanel.add(restoreButton);		
		
		JPanel buttons = new JPanel();
		buttons.setPreferredSize(new Dimension(GE.Window_Width -10, 50));
		buttons.setOpaque(false);
		buttons.add(buttonPanel, BorderLayout.CENTER);
		this.add(buttons, BorderLayout.SOUTH);
		// -----
		update();
	} // end of constructor
	
	/**
	 * Updates the Options GUI with new values.
	 */
	public void update()
	{
		musicEnabled.setSelected(GE.musicEnabled);
	    soundEnabled.setSelected(GE.soundEnabled);
	    fullScreenMode.setSelected(GE.fullScreenMode);
		showHintsEnabled.setSelected(GE.showHintsEnabled);
		
		backgroundColor.setBackground(GE.backgroundColor);
		foregroundColor.setBackground(GE.foregroundColor);
		highlightColor.setBackground(GE.highlightColor);
		
		clockSpeed.setValue(GE.clockSpeed);
		playerVisionRange.setValue(GE.playerVisionRange);
	    monsterGridSpeed.setValue(GE.monsterGridSpeed);
	    percentChanceOfEncounter.setValue((int)(GE.percentChanceOfEncounter*100));
	    opacityLevel.setValue(100 - GE.opacityLevel);
	    
	    unsavedChange = false;
	    saveButton.setEnabled(false);
	}
	
	/**
	 * Saves the changes to the Game Properties.
	 * Other Panels should repaint() in order to
	 * show new colors, and or, sizes.
	 */
	public void saveChanges()
	{
		GE.musicEnabled = musicEnabled.isSelected();
	    GE.soundEnabled = soundEnabled.isSelected();
	    GE.fullScreenMode = fullScreenMode.isSelected();
		GE.showHintsEnabled = showHintsEnabled.isSelected();
		
		GE.backgroundColor = backgroundColor.getBackground();
		GE.foregroundColor = foregroundColor.getBackground();
		GE.highlightColor = highlightColor.getBackground();
		
		GE.clockSpeed = clockSpeed.getValue();
		GE.klok.setRate(GE.clockSpeed);
		GE.playerVisionRange = playerVisionRange.getValue();
	    GE.monsterGridSpeed = monsterGridSpeed.getValue();
	    GE.percentChanceOfEncounter = ((double) percentChanceOfEncounter.getValue())/100.0;
	    GE.opacityLevel = 100 - opacityLevel.getValue();
	    GE.opacityScreen.setBackground(new Color(0,0,0,GE.opacityLevel));
	    
	    update();
	    GE.printInfo("Save successful!");   
	}
	
	/**
	 * Resets the values for all options back
	 * to a reliable, and usable setting.
	 */
	public void restoreDefaults()
	{
		GE.musicEnabled = true;
	    GE.soundEnabled = true;
	    GE.fullScreenMode = true;
	    GE.backgroundColor = Color.black;
		GE.foregroundColor = Color.white;
		GE.highlightColor = Color.blue;
	    GE.showHintsEnabled = false;
	    GE.playerVisionRange = 3;
	    GE.clockSpeed = 1000; // 1 second
	    GE.klok.setRate(GE.clockSpeed);
	    GE.monsterGridSpeed = 2; // monster moves after X seconds
	    GE.percentChanceOfEncounter = 0.05; // % chance of battle 
	    
	    update();
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		
		if(source == saveButton)
		{
			// save changes to options
			saveChanges();
		}
		else if(source == restoreButton)
		{
			// restore default settings
			restoreDefaults();
		}
		else if(source == exitButton)
		{
			// go back to main menu
			if(unsavedChange)
			{
				// are you sure?
				Object[] choice = {"Save","Discard"};
				int r = GE.printCustomQuestion("There are unsaved changes.\n\nWhat do you want to\ndo with these changes?", choice);
				if(r == 0)
				{
					saveChanges();
				}
				GE.viewMainMenu();
			}
			else
				GE.viewMainMenu();
		}
		else if(source == backgroundColor)
		{
			Color temp = JColorChooser.showDialog(null, "Choose a Background Color", GE.backgroundColor);
			
			if(temp != null)
			{
				backgroundColor.setBackground(temp);
				unsavedChange = true;
			}
		}
		else if(source == foregroundColor)
		{
			Color temp = JColorChooser.showDialog(null, "Choose a Foreground Color", GE.foregroundColor);
			
			if(temp != null)
			{
				foregroundColor.setBackground(temp);
				unsavedChange = true;
			}
		}
		else if(source == highlightColor)
		{
			Color temp = JColorChooser.showDialog(null, "Choose a Highlighting Color", GE.highlightColor);
			
			if(temp != null)
			{
				highlightColor.setBackground(temp);
				unsavedChange = true;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent k)
	{
		int keyCode = k.getKeyCode();
		
		// shortcut keys to certain actions
		if(keyCode == 65) // a
		{
			saveButton.doClick();
		}
		else if(keyCode == 67) // c
		{
			exitButton.doClick();
		}
		else if(keyCode == 82) // r
		{
			restoreButton.doClick();
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

	@Override
	public void stateChanged(ChangeEvent c) {
		if((c.getSource() == musicEnabled)
		|| (c.getSource() == soundEnabled)
		|| (c.getSource() == fullScreenMode)
		|| (c.getSource() == showHintsEnabled)
		|| (c.getSource() == clockSpeed)
		|| (c.getSource() == playerVisionRange)
		|| (c.getSource() == monsterGridSpeed)
		|| (c.getSource() == percentChanceOfEncounter))
		{
			if(GE.musicEnabled != musicEnabled.isSelected() ||
					GE.soundEnabled != soundEnabled.isSelected() ||
					GE.fullScreenMode != fullScreenMode.isSelected() ||
					GE.backgroundColor != backgroundColor.getBackground() ||
					GE.foregroundColor != foregroundColor.getBackground() ||
					GE.highlightColor != highlightColor.getBackground() ||
					GE.showHintsEnabled != showHintsEnabled.isSelected() ||
					GE.playerVisionRange != playerVisionRange.getValue() ||
					GE.clockSpeed != clockSpeed.getValue() ||
					GE.monsterGridSpeed != monsterGridSpeed.getValue() ||
					GE.percentChanceOfEncounter != percentChanceOfEncounter.getValue())
			{
				unsavedChange = true;
				saveButton.setEnabled(true);
			}
		}
	}
}
