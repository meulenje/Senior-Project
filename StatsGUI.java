package rpg;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class StatsGUI extends JPanel implements ActionListener{
	
	private GameEngine GE;
	
	protected JPanel ScrollBar;
	protected JButton previousCharacterButton;
	protected JButton nextCharacterButton;
	
	protected JPanel mainFrame;
	protected JTextArea charName;
	
	protected JPanel HealthPanel;
	protected JTextArea HealthLabel;
	protected JTextArea HealthAmount;
	protected JTextArea HealthCurrent;
	protected JProgressBar HealthBar;
	protected JButton HealthUp;
	protected JButton HealthDown;
	
	protected JPanel ManaPanel;
	protected JTextArea ManaLabel;
	protected JTextArea ManaAmount;
	protected JTextArea ManaCurrent;
	protected JProgressBar ManaBar;
	protected JButton ManaUp;
	protected JButton ManaDown;
	
	protected JTextArea AttackLabel;
	protected JTextArea AttackAmount;
	protected JButton AttackUp;
	protected JButton AttackDown;
	
	protected JTextArea DefenseLabel;
	protected JTextArea DefenseAmount;
	protected JButton DefenseUp;
	protected JButton DefenseDown;
	
	protected JTextArea SpeedLabel;
	protected JTextArea SpeedAmount;
	protected JButton SpeedUp;
	protected JButton SpeedDown;
	
	protected JTextArea PointsLeft;
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
	
	public StatsGUI (GameEngine theEngine){
		
		String info1 = "";
		String info2 = "";
		
		GE = theEngine;
		position = 0;
		TheCharacter = GE.characters.get(position);
		for (int i = 0; i < GE.characters.size(); i++){
			GE.characters.get(i).setLevelUpPoints(5);
		}
		modifiedHealth = TheCharacter.getMaxHealth();
		modifiedAttack = TheCharacter.getAttack();
		modifiedMana = TheCharacter.getMaxMana();
		modifiedDefense = TheCharacter.getAttack();
		modifiedSpeed = TheCharacter.getSpeed();
		pointsLeft = TheCharacter.getLevelUpPoints();
		
		this.setLayout(new BorderLayout());
		
		ScrollBar = new JPanel();
		
	    ScrollBar.setLayout(new BorderLayout());
		charName = new JTextArea(TheCharacter.getName());
		charName.setEditable(false);
		ScrollBar.add(charName, BorderLayout.CENTER);
        previousCharacterButton = new JButton("<==");   
        previousCharacterButton.addActionListener(this);
        ScrollBar.add(previousCharacterButton, BorderLayout.WEST);
        nextCharacterButton = new JButton("==>");
        nextCharacterButton.addActionListener(this);
        ScrollBar.add(nextCharacterButton, BorderLayout.EAST);
        
        this.add(ScrollBar, BorderLayout.NORTH);
        
		mainFrame = new JPanel (new GridLayout(6,4));
		
		HealthPanel = new JPanel(new GridLayout(3,1));
		HealthLabel = new JTextArea("Health: ");
		HealthLabel.setEditable(false);
		info1 = "" + TheCharacter.getCurrentHealth();
		info2 = "" + TheCharacter.getMaxHealth();
		HealthCurrent = new JTextArea(info1 + "/" + info2);
		HealthCurrent.setEditable(false);
		HealthBar = new JProgressBar(0);
		HealthBar.setForeground(Color.red);
		HealthBar.setBackground(Color.white);
		HealthAmount = new JTextArea("" + GE.characters.get(position).getMaxHealth());
		HealthAmount.setEditable(false);
		HealthUp = new JButton("+");
		HealthUp.addActionListener(this);
		HealthUp.setEnabled(false);
		HealthDown = new JButton("-");
		HealthDown.addActionListener(this);
		HealthDown.setEnabled(false);
		
		HealthPanel.add(HealthLabel);
		HealthPanel.add(HealthCurrent);
		HealthPanel.add(HealthBar);
		mainFrame.add(HealthPanel);
		mainFrame.add(HealthAmount);
		mainFrame.add(HealthUp);
		mainFrame.add(HealthDown);
		
		ManaPanel = new JPanel(new GridLayout(3,1));
		ManaLabel = new JTextArea("Mana: ");
		info1 = "" + TheCharacter.getCurrentMana();
		info2 = "" + TheCharacter.getMaxMana();
		ManaCurrent = new JTextArea(info1 + "/" + info2);
		ManaCurrent.setEditable(false);
		ManaBar = new JProgressBar(0);
		ManaBar.setForeground(Color.blue);
		ManaBar.setBackground(Color.white);
		ManaLabel.setEditable(false);
		ManaAmount = new JTextArea("" + GE.characters.get(position).getMaxMana());
		ManaAmount.setEditable(false);
		ManaUp = new JButton("+");
		ManaUp.addActionListener(this);
		ManaUp.setEnabled(false);
		ManaDown = new JButton("-");
		ManaDown.addActionListener(this);
		ManaDown.setEnabled(false);
		
		ManaPanel.add(ManaLabel);
		ManaPanel.add(ManaCurrent);
		ManaPanel.add(ManaBar);
		mainFrame.add(ManaPanel);
		mainFrame.add(ManaAmount);
		mainFrame.add(ManaUp);
		mainFrame.add(ManaDown);
		
		AttackLabel = new JTextArea("Attack: ");
		AttackLabel.setEditable(false);
		AttackAmount = new JTextArea("" + GE.characters.get(position).getAttack());
		AttackAmount.setEditable(false);
		AttackUp = new JButton("+");
		AttackUp.addActionListener(this);
		AttackUp.setEnabled(false);
		AttackDown = new JButton("-");
		AttackDown.addActionListener(this);
		AttackDown.setEnabled(false);
		
		mainFrame.add(AttackLabel);
		mainFrame.add(AttackAmount);
		mainFrame.add(AttackUp);
		mainFrame.add(AttackDown);
		
		DefenseLabel = new JTextArea("Defense: ");
		DefenseLabel.setEditable(false);
		DefenseAmount = new JTextArea("" + GE.characters.get(position).getDefense());
		DefenseAmount.setEditable(false);
		DefenseUp = new JButton("+");
		DefenseUp.addActionListener(this);
		DefenseUp.setEnabled(false);
		DefenseDown = new JButton("-");
		DefenseDown.addActionListener(this);
		DefenseDown.setEnabled(false);
		
		mainFrame.add(DefenseLabel);
		mainFrame.add(DefenseAmount);
		mainFrame.add(DefenseUp);
		mainFrame.add(DefenseDown);
		
		SpeedLabel = new JTextArea("Speed: ");
		SpeedLabel.setEditable(false);
		SpeedAmount = new JTextArea("" + GE.characters.get(position).getDefense());
		SpeedAmount.setEditable(false);
		SpeedUp = new JButton("+");
		SpeedUp.addActionListener(this);
		SpeedUp.setEnabled(false);
		SpeedDown = new JButton("-");
		SpeedDown.addActionListener(this);
		SpeedDown.setEnabled(false);
		
		mainFrame.add(SpeedLabel);
		mainFrame.add(SpeedAmount);
		mainFrame.add(SpeedUp);
		mainFrame.add(SpeedDown);
		
		PointsLeft = new JTextArea("" + TheCharacter.getLevelUpPoints());
		PointsLeft.setEditable(false);
		Confirm = new JButton("Confirm");
		Confirm.addActionListener(this);
		Confirm.setEnabled(false);
		Reset = new JButton("Reset");
		Reset.addActionListener(this);
		Reset.setEnabled(true);
		
		mainFrame.add(PointsLeft);
		mainFrame.add(Confirm);
		mainFrame.add(Reset);
		
		this.add(mainFrame, BorderLayout.CENTER);
		this.setVisible(true);
		
		update();
		
	}
	
	private void update(){
		String info1 = "";
		String info2 = "";
		if (pointsLeft == 0){
			HealthUp.setEnabled(false);
			ManaUp.setEnabled(false);
			AttackUp.setEnabled(false);
			DefenseUp.setEnabled(false);
			SpeedUp.setEnabled(false);
		}
		else {
			HealthUp.setEnabled(true);
			ManaUp.setEnabled(true);
			AttackUp.setEnabled(true);
			DefenseUp.setEnabled(true);
			SpeedUp.setEnabled(true);
		}
		if (modifiedHealth == TheCharacter.getMaxHealth()){
			HealthDown.setEnabled(false);
		}
		else {
			HealthDown.setEnabled(true);
		}
		if (modifiedMana == TheCharacter.getMaxMana()){
			ManaDown.setEnabled(false);
		}
		else {
			ManaDown.setEnabled(true);
		}
		if (modifiedAttack == TheCharacter.getAttack()){
			AttackDown.setEnabled(false);
		}
		else {
			AttackDown.setEnabled(true);
		}
		if (modifiedDefense == TheCharacter.getDefense()){
			DefenseDown.setEnabled(false);
		}
		else {
			DefenseDown.setEnabled(true);
		}
		if (modifiedSpeed == TheCharacter.getSpeed()){
			SpeedDown.setEnabled(false);
		}
		else {
			SpeedDown.setEnabled(true);
		}
		if (TheCharacter.getMaxHealth() == modifiedHealth && TheCharacter.getMaxMana() == modifiedMana && 
				TheCharacter.getAttack() == modifiedAttack && TheCharacter.getDefense() == modifiedDefense 
				&& TheCharacter.getSpeed() == modifiedSpeed	&& pointsLeft == 0){
				Confirm.setEnabled(false);
				Reset.setEnabled(false);
		}
		else {
			Confirm.setEnabled(true);
			Reset.setEnabled(true);
		}
		info1 = "" + TheCharacter.getCurrentHealth();
		info2 = "" + TheCharacter.getMaxHealth();
		HealthCurrent.setText(info1 + "/" + info2);
		info1 = "" + TheCharacter.getCurrentMana();
		info2 = "" + TheCharacter.getMaxMana();
		ManaCurrent.setText(info1 + "/" + info2);
		charName.setText(TheCharacter.getName());
		HealthAmount.setText("" + modifiedHealth);
		ManaAmount.setText("" + modifiedMana);
		AttackAmount.setText("" + modifiedAttack);
		DefenseAmount.setText("" + modifiedDefense);
		SpeedAmount.setText("" + modifiedSpeed);
		PointsLeft.setText("" + pointsLeft);
		int healthPercent = (int) (((double)TheCharacter.getCurrentHealth() / (double)TheCharacter.getMaxHealth()) * 100);
		HealthBar.setValue(healthPercent);
		int manaPercent = (int) (((double)TheCharacter.getCurrentMana() / (double)TheCharacter.getMaxMana()) * 100);
		ManaBar.setValue(manaPercent);
	}
	
	private void modification(String stat, int plusminus){
		if (stat.equals("health")){
			if (plusminus < 0){
				modifiedHealth = modifiedHealth - 5;
				pointsLeft++;
			}
			else {
				modifiedHealth = modifiedHealth + 5;
				pointsLeft--;
			}
		}
		else if(stat.equals("mana")){
			if (plusminus < 0){
				modifiedMana = modifiedMana - 5;
				pointsLeft++;
			}
			else {
				modifiedMana = modifiedMana + 5;
				pointsLeft--;
			}
		}
		else if(stat.equals("attack")){
			if (plusminus < 0){
				modifiedAttack--;
				pointsLeft++;
			}
			else {
				modifiedAttack++;
				pointsLeft--;
			}
		}
		else if(stat.equals("defense")){
			if (plusminus < 0){
				modifiedDefense--;
				pointsLeft++;
			}
			else {
				modifiedDefense++;
				pointsLeft--;
			}
		}
		else if(stat.equals("speed")){
			if (plusminus < 0){
				modifiedSpeed--;
				pointsLeft++;
			}
			else {
				modifiedSpeed++;
				pointsLeft--;
			}
		}
		update();
	}
	
	private void reset(){
		modifiedHealth = TheCharacter.getMaxHealth();
		modifiedMana = TheCharacter.getMaxMana();
		modifiedAttack = TheCharacter.getAttack();
		modifiedDefense = TheCharacter.getDefense();
		modifiedSpeed = TheCharacter.getSpeed();
		pointsLeft = TheCharacter.getLevelUpPoints();
		update();
	}
	
	public int confirm(){
		int result = GE.printYesNoQuestion("Confirm stat allocation?");
		if (result == 0){
			TheCharacter.setCurrentHealth(modifiedHealth - TheCharacter.getMaxHealth()
					+ TheCharacter.getCurrentHealth());
			TheCharacter.setMaxHealth(modifiedHealth);
			TheCharacter.setCurrentMana(modifiedMana - TheCharacter.getMaxMana()
					+ TheCharacter.getCurrentMana());
			TheCharacter.setMaxMana(modifiedMana);
			TheCharacter.setAttack(modifiedAttack);
			TheCharacter.setDefense(modifiedDefense);
			TheCharacter.setSpeed(modifiedSpeed);
			TheCharacter.setLevelUpPoints(pointsLeft);
			update();
			return result;
		}
		else {
			return result;
		}
	}
	
	private void cycle(int inc){
		if (pointsLeft == TheCharacter.getLevelUpPoints() || confirm() ==  0){
			if (inc > 0){
				position++;
			}
			else {
				position --;
			}
			if (position > (GE.characters.size()-1)){
				position = 0;
			}
			else if (position < 0){
				position = (GE.characters.size()-1);
			}
		TheCharacter = GE.characters.get(position);
		reset();
		update();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == HealthUp){
			modification("health", 1);
		}
		else if (e.getSource() == HealthDown){
			modification("health", -1);
		}
		else if (e.getSource() == ManaUp){
			modification("mana", 1);
		}
		else if (e.getSource() == ManaDown){
			modification("mana", -1);
		}
		else if (e.getSource() == AttackUp){
			modification("attack", 1);
		}
		else if (e.getSource() == AttackDown){
			modification("attack", -1);
		}
		else if (e.getSource() == DefenseUp){
			modification("defense", 1);
		}
		else if (e.getSource() == DefenseDown){
			modification("defense", -1);
		}
		else if (e.getSource() == SpeedUp){
			modification("speed", 1);
		}
		else if (e.getSource() == SpeedDown){
			modification("speed", -1);
		}
		else if (e.getSource() == Reset){
			reset();
		}
		else if (e.getSource() == Confirm){
			confirm();
		}
		else if (e.getSource() == nextCharacterButton){
			cycle(1);
		}
		else if (e.getSource() == previousCharacterButton){
			cycle(-1);
		}
	}
	
	/*public static void main(String[] args){
		Entity testChar = new Character("Tiff", 140, 140, 10, 15, 20);
		System.out.println(testChar.getMaxHealth());
		LevelUp testLevelup = new LevelUp(testChar);
		while (testLevelup.isRunning == true){
			continue;
		}
		if (testLevelup.isRunning == false){
			testChar = testLevelup.confirm();
		}
		System.out.println(testChar.getMaxHealth());
	}*/
}
