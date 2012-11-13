package rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

/**
 * QuestObject class
 * 
 * A simple object to hold quest like information together
 * 
 * @author Austin
 * @version 10/31/2012
 */
@SuppressWarnings("serial")
public class QuestObject extends JPanel implements ActionListener{

	private GameEngine GE;
	
	private JLabel imageLabel;
	private JLabel statusLabel;
	private JTextArea text;
	private JButton viewStats;
	
	protected int id;
	protected ImageIcon image;
	protected String title;
	protected String message;
	protected String status;
	protected String statistics;
	
	public QuestObject(GameEngine g, int id, ImageIcon i, String t, String m, String s)
	{
		// receive parameters
		GE = g;
		this.id = id;
		image = i;
		title = t;
		message = m;
		status = s;
		statistics = "";
		
		// build GUI Panel
		
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(GE.X_DIM - 60, GE.Y_DIM / 6));
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		this.setBackground(Color.WHITE);
		
		JPanel imagePanel = new JPanel();
		imagePanel.setLayout(new BorderLayout());
		imagePanel.setBackground(Color.LIGHT_GRAY);
		
		imageLabel = new JLabel("Quest #"+(this.id+1)+" - "+title, JLabel.LEFT);
		imageLabel.setIcon(image);
		imageLabel.setSize(GE.C_WIDTH, GE.C_HEIGHT);
		imageLabel.setLocation(0,0);
		
		statusLabel = new JLabel(status, JLabel.RIGHT);
		statusLabel.setForeground(Color.RED);
		
		viewStats = new JButton("See Details");
		viewStats.addActionListener(this);
		viewStats.setFont(new Font("sansserif",Font.BOLD,10));
		viewStats.setEnabled(false);
		
		imagePanel.add(imageLabel, BorderLayout.WEST);
		imagePanel.add(Box.createGlue(), BorderLayout.CENTER);
		imagePanel.add(statusLabel);
		imagePanel.add(viewStats, BorderLayout.EAST);
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
	}// TODO Auto-generated method stub
	
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
	
	public void setStatistics(String s)
	{
		statistics = s;
		viewStats.setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		// TODO Auto-generated method stub
		if(source == viewStats)
		{
			// show the stats in a popup menu
			GE.printInfo(statistics);
		}
		
	}
} // end of QuestGUI
