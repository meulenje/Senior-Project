package rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

/**
 * Quest class
 * 
 * A simple object to hold quest like information together
 * 
 * @author Austin
 * @version 10/8/2012
 */
@SuppressWarnings("serial")
public class QuestGUI extends JPanel{

	private GameEngine GE;
	
	private JLabel imageLabel;
	private JLabel statusLabel;
	private JTextArea text;
	
	protected ImageIcon image;
	protected String title;
	protected String message;
	protected String status;
	
	public QuestGUI(GameEngine g, ImageIcon i, String t, String m)
	{
		// receive parameters
		GE = g;
		image = i;
		title = t;
		message = m;
		status = "Started";
		
		// build GUI Panel
		
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(GE.X_DIM - 60, 100));
		//TitledBorder border = new TitledBorder("Quest #"+(++numberOfQuests));
		//border.setTitleColor(Color.LIGHT_GRAY);
		//border.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		//this.setBorder(border);
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		this.setBackground(Color.WHITE);
		
		JPanel imagePanel = new JPanel();
		imagePanel.setLayout(new BorderLayout());
		imagePanel.setBackground(Color.LIGHT_GRAY);
		
		imageLabel = new JLabel(title, JLabel.LEFT);
		imageLabel.setIcon(image);
		imageLabel.setSize(GE.C_WIDTH, GE.C_HEIGHT);
		imageLabel.setLocation(0,0);
		
		statusLabel = new JLabel(status, JLabel.RIGHT);
		statusLabel.setForeground(Color.RED);
		
		imagePanel.add(imageLabel, BorderLayout.WEST);
		imagePanel.add(statusLabel, BorderLayout.EAST);
		this.add(imagePanel, BorderLayout.NORTH);
		
		text = new JTextArea(message);
		text.setEditable(false);
		text.setWrapStyleWord(true);
		text.setLineWrap(true);
		text.setFont(new Font("sansserif",Font.ITALIC,12));
		this.add(text, BorderLayout.CENTER);
	}
	
	public void setImageIcon(ImageIcon i)
	{
		image = i;
		imageLabel.setIcon(image);
	}
	
	public void setTitle(String t)
	{
		title = t;
		imageLabel.setText(title);
	}
	
	public void setMessage(String m)
	{
		message = m;
		text.setText(message);
	}
	
	public void setStatus(String s, Color c)
	{
		status = s;
		statusLabel.setText(status);
		statusLabel.setForeground(c);
	}
} // end of QuestGUI
