package rpg;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

@SuppressWarnings("serial")
public class StatsGUI extends JPanel implements ActionListener, KeyListener,
		ListSelectionListener {

	private GameEngine GE;

	protected JPanel TopBar;

	protected JToolBar ScrollBar;
	protected JButton previousCharacterButton;
	protected JButton nextCharacterButton;

	protected JPanel expPanel;
	protected JLabel expLabel;
	protected JProgressBar expBar;

	protected JPanel mainFrame;
	protected JLabel charName;
	protected JLabel picture;

	protected JPanel statsStuff;
	protected JPanel attributeNames;
	protected JPanel attributeNumbers;
	protected JPanel buttons;
	protected JPanel confirmReset;

	protected JLabel HealthLabel;
	protected JLabel HealthAmount;
	protected JProgressBar HealthBar;
	protected JButton HealthUp;
	protected JButton HealthDown;

	protected JLabel ManaLabel;
	protected JLabel ManaAmount;
	protected JProgressBar ManaBar;
	protected JButton ManaUp;
	protected JButton ManaDown;

	protected JLabel AttackLabel;
	protected JLabel AttackAmount;
	protected JButton AttackUp;
	protected JButton AttackDown;

	protected JLabel DefenseLabel;
	protected JLabel DefenseAmount;
	protected JButton DefenseUp;
	protected JButton DefenseDown;

	protected JLabel SpeedLabel;
	protected JLabel SpeedAmount;
	protected JButton SpeedUp;
	protected JButton SpeedDown;

	protected JPanel AbilitiesPanel;
	protected JLabel AbilitiesLabel;
	protected JTextArea AbilitiesDescription;
	protected JList<Ability> AbilitiesBox;
	protected JScrollPane AbilitiesScrollPane;
	protected ArrayList<JLabel> Abilities;

	protected JLabel PointsLeft;
	protected JButton Confirm;
	protected JButton Reset;

	int modifiedHealth;
	int modifiedMana;
	int modifiedAttack;
	int modifiedDefense;
	int modifiedSpeed;
	int pointsLeft;
	int position;

	Entity TheCharacter;

	boolean isRunning = true;

	public StatsGUI(GameEngine theEngine) {

		String info1 = "";
		String info2 = "";

		GE = theEngine;
		position = 0;
		TheCharacter = GE.characters.get(position);
		for (int i = 0; i < GE.characters.size(); i++) {
			GE.characters.get(i).setLevelUpPoints(5);
		}
		modifiedHealth = 0;
		modifiedAttack = 0;
		modifiedMana = 0;
		modifiedDefense = 0;
		modifiedSpeed = 0;
		pointsLeft = TheCharacter.getLevelUpPoints();

		this.setLayout(new BorderLayout());

		ScrollBar = new JToolBar();
		ScrollBar.setFloatable(false);
		TopBar = new JPanel();
		TopBar.setLayout(new BorderLayout());
		TopBar.setPreferredSize(new Dimension(600, 100));

		ScrollBar.setLayout(new GridLayout(0, 4));
		ScrollBar.setPreferredSize(new Dimension(600, 50));
		charName = new JLabel(TheCharacter.getName() + " Level: "
				+ TheCharacter.getLevel());
		picture = new JLabel();
		picture.setIcon(TheCharacter.getImage());
		previousCharacterButton = new JButton("Previous");
		previousCharacterButton.addActionListener(this);
		nextCharacterButton = new JButton("Next");
		nextCharacterButton.addActionListener(this);
		ScrollBar.add(previousCharacterButton);
		ScrollBar.add(picture);
		ScrollBar.add(charName);
		ScrollBar.add(nextCharacterButton);

		expPanel = new JPanel();
		expPanel.setLayout(new GridLayout(0, 4));
		expLabel = new JLabel("Experience:");
		expLabel.setForeground(Color.GREEN);
		expBar = new JProgressBar(0);
		expBar.setForeground(Color.green);
		expBar.setBackground(Color.white);
		int expPercent = (int) (((double) TheCharacter.getExp() / (double) GE
				.getExpNeeded(TheCharacter)) * 100);
		expBar.setValue(expPercent);
		expBar.setStringPainted(true);
		expBar.setString("" + TheCharacter.getExp() + "/"
				+ GE.getExpNeeded(TheCharacter));
		PointsLeft = new JLabel("Points Left: "
				+ TheCharacter.getLevelUpPoints());
		Reset = new JButton("Reset");
		Reset.addActionListener(this);
		Reset.setEnabled(true);
		expPanel.add(expLabel);
		expPanel.add(expBar);
		expPanel.add(PointsLeft);
		expPanel.add(Reset);

		TopBar.add(ScrollBar, BorderLayout.NORTH);
		TopBar.add(expPanel, BorderLayout.SOUTH);

		this.add(TopBar, BorderLayout.NORTH);

		mainFrame = new JPanel(new BorderLayout());
		statsStuff = new JPanel(new BorderLayout());
		attributeNames = new JPanel(new GridLayout(5, 1));
		attributeNumbers = new JPanel(new GridLayout(5, 1, 50, 50));
		buttons = new JPanel(new GridLayout(5, 2, 0, 50));
		confirmReset = new JPanel(new BorderLayout());

		HealthLabel = new JLabel("Health: ");
		HealthLabel.setForeground(Color.RED);
		info1 = "" + TheCharacter.getCurrentHealth();
		info2 = "" + TheCharacter.getMaxHealth();
		HealthBar = new JProgressBar(0);
		HealthBar.setForeground(Color.red);
		HealthBar.setBackground(Color.white);
		HealthBar.setStringPainted(true);
		HealthBar.setString(info1 + "/" + info2);
		HealthAmount = new JLabel(""
				+ GE.characters.get(position).getMaxHealth());
		HealthUp = new JButton("+");
		HealthUp.addActionListener(this);
		HealthUp.setEnabled(false);
		HealthDown = new JButton("-");
		HealthDown.addActionListener(this);
		HealthDown.setEnabled(false);

		ManaLabel = new JLabel("Mana: ");
		ManaLabel.setForeground(Color.BLUE);
		info1 = "" + TheCharacter.getCurrentMana();
		info2 = "" + TheCharacter.getMaxMana();
		ManaBar = new JProgressBar(0);
		ManaBar.setForeground(Color.blue);
		ManaBar.setBackground(Color.white);
		ManaBar.setStringPainted(true);
		ManaBar.setString(info1 + "/" + info2);
		ManaAmount = new JLabel("" + GE.characters.get(position).getMaxMana());
		ManaUp = new JButton("+");
		ManaUp.addActionListener(this);
		ManaUp.setEnabled(false);
		ManaDown = new JButton("-");
		ManaDown.addActionListener(this);
		ManaDown.setEnabled(false);

		AttackLabel = new JLabel("Attack: ");
		AttackAmount = new JLabel("" + GE.characters.get(position).getAttack(),
				JLabel.CENTER);
		AttackUp = new JButton("+");
		AttackUp.addActionListener(this);
		AttackUp.setEnabled(false);
		AttackDown = new JButton("-");
		AttackDown.addActionListener(this);
		AttackDown.setEnabled(false);

		DefenseLabel = new JLabel("Defense: ");
		DefenseAmount = new JLabel(""
				+ GE.characters.get(position).getDefense(), JLabel.CENTER);
		DefenseUp = new JButton("+");
		DefenseUp.addActionListener(this);
		DefenseUp.setEnabled(false);
		DefenseDown = new JButton("-");
		DefenseDown.addActionListener(this);
		DefenseDown.setEnabled(false);

		SpeedLabel = new JLabel("Speed: ");
		SpeedAmount = new JLabel("" + GE.characters.get(position).getSpeed(),
				JLabel.CENTER);
		SpeedUp = new JButton("+");
		SpeedUp.addActionListener(this);
		SpeedUp.setEnabled(false);
		SpeedDown = new JButton("-");
		SpeedDown.addActionListener(this);
		SpeedDown.setEnabled(false);

		Confirm = new JButton("Confirm");
		Confirm.addActionListener(this);
		Confirm.setEnabled(false);
		Confirm.setPreferredSize(new Dimension(100, 25));

		AbilitiesPanel = new JPanel(new BorderLayout());
		AbilitiesLabel = new JLabel("Abilities:");
		Ability[] array = new Ability[0];
		AbilitiesBox = new JList<Ability>(TheCharacter.abilities.toArray(array));
		AbilitiesBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		AbilitiesBox.addListSelectionListener(this);
		AbilitiesScrollPane = new JScrollPane(AbilitiesBox);
		AbilitiesScrollPane.setBorder(BorderFactory
				.createTitledBorder("Abilities:"));
		//AbilitiesScrollPane.setSize(new Dimension (200, 250));

		AbilitiesDescription = new JTextArea(10, 10);
		AbilitiesDescription.setEditable(false);
		AbilitiesDescription.setWrapStyleWord(true);

		AbilitiesPanel.setPreferredSize(new Dimension(200, 500));

		AbilitiesPanel.add(AbilitiesScrollPane, BorderLayout.CENTER);
		AbilitiesPanel.add(AbilitiesDescription, BorderLayout.SOUTH);

		confirmReset.setPreferredSize(new Dimension(400, 100));

		attributeNames.add(HealthLabel);
		attributeNames.add(ManaLabel);
		attributeNames.add(AttackLabel);
		attributeNames.add(DefenseLabel);
		attributeNames.add(SpeedLabel);

		attributeNumbers.add(HealthBar);
		attributeNumbers.add(ManaBar);
		attributeNumbers.add(AttackAmount);
		attributeNumbers.add(DefenseAmount);
		attributeNumbers.add(SpeedAmount);

		buttons.add(HealthUp);
		buttons.add(HealthDown);
		buttons.add(ManaUp);
		buttons.add(ManaDown);
		buttons.add(AttackUp);
		buttons.add(AttackDown);
		buttons.add(DefenseUp);
		buttons.add(DefenseDown);
		buttons.add(SpeedUp);
		buttons.add(SpeedDown);
		statsStuff.add(attributeNames, BorderLayout.WEST);
		statsStuff.add(attributeNumbers, BorderLayout.CENTER);
		statsStuff.add(buttons, BorderLayout.EAST);

		confirmReset.setPreferredSize(new Dimension(400, 50));
		confirmReset.add(Confirm, BorderLayout.EAST);

		mainFrame.add(statsStuff, BorderLayout.CENTER);
		mainFrame.add(confirmReset, BorderLayout.SOUTH);

		this.add(mainFrame, BorderLayout.CENTER);
		this.add(AbilitiesPanel, BorderLayout.EAST);
		this.setVisible(true);
		this.addKeyListener(this); // This class has its own key listeners.
		this.setFocusable(true); // Allow panel to get focus

		update();

	}

	void update() {
		String info1 = "";
		String info2 = "";
		if (pointsLeft == 0) {
			HealthUp.setEnabled(false);
			ManaUp.setEnabled(false);
			AttackUp.setEnabled(false);
			DefenseUp.setEnabled(false);
			SpeedUp.setEnabled(false);
		} else {
			HealthUp.setEnabled(true);
			ManaUp.setEnabled(true);
			AttackUp.setEnabled(true);
			DefenseUp.setEnabled(true);
			SpeedUp.setEnabled(true);
		}
		if (modifiedHealth == 0) {
			HealthDown.setEnabled(false);
		} else {
			HealthDown.setEnabled(true);
		}
		if (modifiedMana == 0) {
			ManaDown.setEnabled(false);
		} else {
			ManaDown.setEnabled(true);
		}
		if (modifiedAttack == 0) {
			AttackDown.setEnabled(false);
		} else {
			AttackDown.setEnabled(true);
		}
		if (modifiedDefense == 0) {
			DefenseDown.setEnabled(false);
		} else {
			DefenseDown.setEnabled(true);
		}
		if (modifiedSpeed == 0) {
			SpeedDown.setEnabled(false);
		} else {
			SpeedDown.setEnabled(true);
		}
		if (modifiedHealth == 0 && modifiedMana == 0 && modifiedAttack == 0
				&& modifiedDefense == 0 && modifiedSpeed == 0
				&& pointsLeft == 0) {
			Confirm.setEnabled(false);
			Reset.setEnabled(false);
		} else {
			Confirm.setEnabled(true);
			Reset.setEnabled(true);
		}
		info1 = "" + TheCharacter.getCurrentHealth();
		int hold = TheCharacter.getMaxHealth() + modifiedHealth;
		info2 = "" + hold;
		HealthBar.setString(info1 + "/" + info2);
		info1 = "" + TheCharacter.getCurrentMana();
		hold = TheCharacter.getMaxMana() + modifiedMana;
		info2 = "" + hold;
		ManaBar.setString(info1 + "/" + info2);
		info1 = "" + TheCharacter.getExp();
		info2 = "" + GE.getExpNeeded(TheCharacter);
		expBar.setString(info1 + "/" + info2);
		charName.setText(TheCharacter.getName() + " Level: "
				+ TheCharacter.getLevel());
		hold = TheCharacter.getAttack() + modifiedAttack;
		AttackAmount.setText("" + hold);
		hold = TheCharacter.getDefense() + modifiedDefense;
		DefenseAmount.setText("" + hold);
		hold = TheCharacter.getSpeed() + modifiedSpeed;
		SpeedAmount.setText("" + hold);
		PointsLeft.setText("Points Left: " + pointsLeft);
		int healthPercent = (int) (((double) TheCharacter.getCurrentHealth() / (double) TheCharacter
				.getMaxHealth()) * 100);
		HealthBar.setValue(healthPercent);
		int manaPercent = (int) (((double) TheCharacter.getCurrentMana() / (double) TheCharacter
				.getMaxMana()) * 100);
		ManaBar.setValue(manaPercent);
		int expPercent = (int) (((double) TheCharacter.getExp() / (double) GE
				.getExpNeeded(TheCharacter)) * 100);
		expBar.setValue(expPercent);
		picture.setIcon(TheCharacter.getImage());

		Ability[] array = new Ability[TheCharacter.fullAbilities.size()];
		AbilitiesBox = new JList<Ability>(TheCharacter.fullAbilities.toArray(array));
		AbilitiesBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		AbilitiesBox.addListSelectionListener(this);
		AbilitiesScrollPane.setViewportView(AbilitiesBox);

		if (TheCharacter.abilities.size() > 0) {
			AbilitiesBox.setSelectedIndex(0);
			AbilitiesDescription.setText(
				"Name: " + AbilitiesBox.getSelectedValue().getName() + '\n' + 
				"Cost: " + AbilitiesBox.getSelectedValue().getCost() + '\n' +
				"Level Required: " + AbilitiesBox.getSelectedValue().getLevel() + '\n' +
				"Description: " + AbilitiesBox.getSelectedValue().getDescription() + '\n' +
				"Unlocked: "+ (!AbilitiesBox.getSelectedValue().isLocked()));
		}
	}

	private void modification(String stat, int plusminus) {
		if (stat.equals("health")) {
			if (plusminus < 0) {
				modifiedHealth = modifiedHealth - 5;
				pointsLeft++;
			} else {
				modifiedHealth = modifiedHealth + 5;
				pointsLeft--;
			}
		} else if (stat.equals("mana")) {
			if (plusminus < 0) {
				modifiedMana = modifiedMana - 5;
				pointsLeft++;
			} else {
				modifiedMana = modifiedMana + 5;
				pointsLeft--;
			}
		} else if (stat.equals("attack")) {
			if (plusminus < 0) {
				modifiedAttack--;
				pointsLeft++;
			} else {
				modifiedAttack++;
				pointsLeft--;
			}
		} else if (stat.equals("defense")) {
			if (plusminus < 0) {
				modifiedDefense--;
				pointsLeft++;
			} else {
				modifiedDefense++;
				pointsLeft--;
			}
		} else if (stat.equals("speed")) {
			if (plusminus < 0) {
				modifiedSpeed--;
				pointsLeft++;
			} else {
				modifiedSpeed++;
				pointsLeft--;
			}
		}
		update();
	}

	public void reset() {
		modifiedHealth = 0;
		modifiedMana = 0;
		modifiedAttack = 0;
		modifiedDefense = 0;
		modifiedSpeed = 0;
		pointsLeft = TheCharacter.getLevelUpPoints();
		update();
	}

	public int confirm() {
		int result = GE.printYesNoQuestion("Confirm stat allocation?");
		if (result == 0) {
			TheCharacter.setMaxHealth(TheCharacter.getBaseHealth()
					+ modifiedHealth);
			TheCharacter.setMaxMana(TheCharacter.getBaseMana() + modifiedMana);
			TheCharacter.setAttack(TheCharacter.getBaseAttack()
					+ modifiedAttack);
			TheCharacter.setDefense(TheCharacter.getBaseDefense()
					+ modifiedDefense);
			TheCharacter.setSpeed(TheCharacter.getBaseSpeed() + modifiedSpeed);
			TheCharacter.setLevelUpPoints(pointsLeft);
			GE.restoreCharacter(TheCharacter.getName());
			reset();
			update();
			return result;
		} else {
			return result;
		}
	}

	public void cycle(int inc) {
		if (pointsLeft == TheCharacter.getLevelUpPoints() || confirm() == 0) {
			if (inc > 0) {
				position++;
			} else {
				position--;
			}
			if (position > (GE.characters.size() - 1)) {
				position = 0;
			} else if (position < 0) {
				position = (GE.characters.size() - 1);
			}
			TheCharacter = GE.characters.get(position);
			reset();
			update();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == HealthUp) {
			modification("health", 1);
		} else if (e.getSource() == HealthDown) {
			modification("health", -1);
		} else if (e.getSource() == ManaUp) {
			modification("mana", 1);
		} else if (e.getSource() == ManaDown) {
			modification("mana", -1);
		} else if (e.getSource() == AttackUp) {
			modification("attack", 1);
		} else if (e.getSource() == AttackDown) {
			modification("attack", -1);
		} else if (e.getSource() == DefenseUp) {
			modification("defense", 1);
		} else if (e.getSource() == DefenseDown) {
			modification("defense", -1);
		} else if (e.getSource() == SpeedUp) {
			modification("speed", 1);
		} else if (e.getSource() == SpeedDown) {
			modification("speed", -1);
		} else if (e.getSource() == Reset) {
			reset();
		} else if (e.getSource() == Confirm) {
			confirm();
		} else if (e.getSource() == nextCharacterButton) {
			cycle(1);
		} else if (e.getSource() == previousCharacterButton) {
			cycle(-1);
		}
	}

	@Override
	public void keyPressed(KeyEvent k) {
		int key = k.getKeyCode();

		if (key == 73) // 'i'
		{
			// shortcut to "Inventory Tab"
			GE.viewInventoryPanel();
		} else if (key == 67) // 'c'
		{
			// shortcut to "Combat Tab"
			GE.viewCombatPanel();
		} else if (key == 77) // 'm'
		{
			// shortcut to "Map Tab"
			GE.viewMapPanel();
		} else if (key == 83) // 's'
		{
			// shortcut to "Stats Tab"
			GE.viewStatsPanel();
		} else if (key == 81) // 'q'
		{
			// shortcut to "Quest Tab"
			GE.viewQuestPanel();
		} else if (key == 27) // ESC to pause game
			GE.pauseGame();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if (AbilitiesBox.getSelectedValue() != null) {
			
			AbilitiesDescription.setText(
					"Name: " + AbilitiesBox.getSelectedValue().getName() + '\n' + 
					"Cost: " + AbilitiesBox.getSelectedValue().getCost() + '\n' +
					"Level Required: " + AbilitiesBox.getSelectedValue().getLevel() + '\n' +
					"Description: " + AbilitiesBox.getSelectedValue().getDescription() + '\n' +
					"Unlocked: "+ (!AbilitiesBox.getSelectedValue().isLocked()));
		}
		else {
			AbilitiesDescription.setText("Click on a Ability!");
		}

	}
}
