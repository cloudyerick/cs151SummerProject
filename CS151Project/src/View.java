import java.awt.BorderLayout;
import java.awt.Dimension;

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
		
		JPanel topLeft = new JPanel();
		
		topLeft.add(today);
		topLeft.add(left);
		topLeft.add(right);
		
		myFrame.add(topLeft);
		
		myFrame.setVisible(true);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
