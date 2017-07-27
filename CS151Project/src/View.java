import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class View extends JFrame
{
	public View()
	{
		JFrame myFrame = new JFrame();
		myFrame.setSize(800, 650);
		
		myFrame.setLocation(60, 60);
		
		JButton today = new JButton("Today");
		JButton left = new JButton("<");
		JButton right = new JButton(">");
		
		JButton dayButton = new JButton("Day");
		JButton weekButton = new JButton("Week");
		JButton monthButton = new JButton("Month");
		JButton agendaButton = new JButton("Agenda");
		
		JPanel topLeft = new JPanel();
		JPanel topRight = new JPanel();
		
		topLeft.add(today);
		topLeft.add(left);
		topLeft.add(right);
		
		topRight.add(dayButton);
		topRight.add(weekButton);
		topRight.add(monthButton);
		topRight.add(agendaButton);
		
		myFrame.add(topLeft);
		myFrame.add(topRight);
		
		myFrame.setLayout(new FlowLayout());  //Experimental Layout (for now)
		myFrame.setVisible(true);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
