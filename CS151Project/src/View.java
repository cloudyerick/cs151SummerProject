import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class View  implements ChangeListener
{
	private GregorianCalendar cal = new GregorianCalendar();
    private String[] monthNames = 
    	{ "January", "February",
            "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"       
        };
	
	/*********************************
	 * BUTTON INSTANCE VARIABLES******
	 ********************************/
	private JButton today = new JButton("Today");
	private JButton left = new JButton("<");
	private JButton right = new JButton(">");
	private JButton createButton = new JButton("Create");
	private JButton dayButton = new JButton("Day");
	private JButton weekButton = new JButton("Week");
	private JButton monthButton = new JButton("Month");
	private JButton agendaButton = new JButton("Agenda");
	private JButton fromFileButton = new JButton("From File");
	
	/******************************
	 * PANEL INSTANCE VARIABLES ***
	 *****************************/
	private MonthPanel monthPanel;
	private JPanel leftPanel = new JPanel();
	private JPanel rightPanel = new JPanel();
	
	/***************************
	 * LABEL INSTANCE VARIABLES
	 **************************/
	private JLabel dateLabel = new JLabel(monthNames[cal.get(cal.MONTH)] + " " + cal.get(cal.DAY_OF_MONTH) + " " + cal.get(cal.YEAR), SwingConstants.CENTER); 
	
	
	public View(Model model)
	{
		JFrame myFrame = new JFrame("Calendar");
		monthPanel = new MonthPanel(cal.get(cal.MONTH), cal.get(cal.YEAR));
		myFrame.setLocation(20, 20);
		
		//ALL BUTTON FUNCTIONALITY GOES HERE:
		JButton today = new JButton("Today");  //TODAY BUTTON SETS MODEL AND VIEW TO CURRENT TIME - JONATHAN 
		today.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.goToToday();
				
			}
		});
		
		left.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.previousDay();
			}
		});
		
		right.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.nextDay();
			}
		});
		
		
		
		
		//Date Label
		dateLabel.setOpaque(true);
		dateLabel.setBackground(Color.WHITE);
		
		JPanel leftButtonPanel = new JPanel();  //Top panel for (LEFT panel).
		leftButtonPanel.add(today);
		leftButtonPanel.add(left);
		leftButtonPanel.add(right);
		
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(leftButtonPanel, BorderLayout.NORTH);
		leftPanel.add(createButton, BorderLayout.CENTER);
		leftPanel.add(dateLabel);
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

	//add action listener methods
	public void addEventActionListener(ActionListener l){
		createButton.addActionListener(l);
	}
	
	public void addTodayActionListener(ActionListener l){
		today.addActionListener(l);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		monthPanel.removeAll();
		monthPanel.createGUI();
	}
}

