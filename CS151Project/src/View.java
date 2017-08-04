import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
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

public class View  implements ChangeListener, Runnable
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
	private JFrame myFrame;
	
	/******************************
	 * PANEL INSTANCE VARIABLES ***
	 *****************************/
	private MonthPanel monthPanel;
	private JPanel leftPanel = new JPanel();
	private JPanel rightPanel = new JPanel();
	
	/******
	 * OTHER
	 */
	private Model model;
	
	/***************************
	 * LABEL INSTANCE VARIABLES
	 **************************/
	private JLabel dateLabel = new JLabel(monthNames[cal.get(cal.MONTH)] + " " + cal.get(cal.DAY_OF_MONTH) + " " + cal.get(cal.YEAR), SwingConstants.CENTER); 
	
	
	public View(Model model)
	{
		this.model = model;
		myFrame = new JFrame("Calendar");
		monthPanel = new MonthPanel(cal.get(cal.MONTH), cal.get(cal.YEAR));
		myFrame.setLocation(20, 20);
		
		//ALL BUTTON FUNCTIONALITY GOES HERE:
		//ALL BUTTON FUNCTIONALITY GOES HERE:
		//ALL BUTTON FUNCTIONALITY GOES HERE:
		
		//TODAY BUTTON
		JButton today = new JButton("Today");  //TODAY BUTTON SETS MODEL AND VIEW TO CURRENT TIME - JONATHAN 
		today.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.goToToday();
<<<<<<< HEAD
=======
				dateLabel.setText(monthNames[model.getMonth()] + " " + cal.get(cal.DAY_OF_MONTH) + " "+ model.getYear());
				run();
>>>>>>> 968706d903088cf389612bcfeb5fd7df9eaac31b
			}
		});
		
		left.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				model.previousMonth();
				dateLabel.setText(monthNames[model.getMonth()] + " " + model.getYear());
				run();
			}
		});
		
		right.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.nextMonth();
				dateLabel.setText(monthNames[model.getMonth()] + " " + model.getYear());
				run();
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
		
	}

	@Override
	public void run() {
		leftPanel.remove(monthPanel);
		monthPanel = null;
		monthPanel = new MonthPanel(model.getMonth(), model.getYear());
		leftPanel.add(monthPanel, BorderLayout.SOUTH);
		leftPanel.repaint();
		myFrame.pack();
	}
}

