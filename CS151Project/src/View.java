import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class View extends JFrame
{
	public View(Model model)
	{
		JFrame myFrame = new JFrame("Calendar");
		GregorianCalendar cal = new GregorianCalendar();
		MonthPanel monthPanel = new MonthPanel(cal.get(cal.MONTH), cal.get(cal.YEAR));
		
		myFrame.setLocation(20, 20);
		
		//BUTTONS
		
		//TODAY BUTTON SETS MODEL AND VIEW TO CURRENT TIME - JONATHAN 
		JButton today = new JButton("Today");
		today.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.goToToday();
			}
		});
		
		JButton left = new JButton("<");
		JButton right = new JButton(">");
		JButton createButton = new JButton("Create");
		
		JButton dayButton = new JButton("Day");
		JButton weekButton = new JButton("Week");
		JButton monthButton = new JButton("Month");
		JButton agendaButton = new JButton("Agenda");
		JButton fromFileButton = new JButton("From File");
		
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		
		JPanel leftButtonPanel = new JPanel();  //Top panel for (LEFT panel).
		leftButtonPanel.add(today);
		leftButtonPanel.add(left);
		leftButtonPanel.add(right);
		
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(leftButtonPanel, BorderLayout.NORTH);
		leftPanel.add(createButton, BorderLayout.CENTER);
		leftPanel.add(monthPanel, BorderLayout.SOUTH);
		
		JPanel rightButtonPanel = new JPanel(); //Top panel for (RIGHT panel).
		rightButtonPanel.add(dayButton);
		rightButtonPanel.add(weekButton);
		rightButtonPanel.add(monthButton);
		rightButtonPanel.add(agendaButton);
		
		JTextArea eventList = new JTextArea();
		eventList.setEditable(false);
		eventList.setRows(24);
		
		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(rightButtonPanel, BorderLayout.NORTH);
		rightPanel.add(eventList, BorderLayout.CENTER);
		
		myFrame.add(leftPanel);
		myFrame.add(rightPanel);
		myFrame.add(fromFileButton);
		myFrame.setLayout(new FlowLayout());  //Experimental Layout (for now)
		myFrame.pack();
		myFrame.setVisible(true);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

}
