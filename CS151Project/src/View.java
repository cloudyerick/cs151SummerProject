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
	
	private JButton leftDay = new JButton("<");
	private JButton rightDay = new JButton(">");
	

	
	
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
		monthPanel = new MonthPanel(cal.get(cal.MONTH), cal.get(cal.YEAR), cal.get(cal.DAY_OF_MONTH));
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
				dateLabel.setText(monthNames[model.getMonth()] + " " + cal.get(cal.DAY_OF_MONTH) + " "+ model.getYear());
				run();
			}
		});
		
		left.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				model.previousMonth();
				model.setDay();
				dateLabel.setText(monthNames[model.getMonth()] + " " + model.getDay() + " " + model.getYear());
				run();
			}
		});
		
		right.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.nextMonth();
				model.setDay();
				dateLabel.setText(monthNames[model.getMonth()] + " " + model.getDay() + " "+ model.getYear());
				run();
			}
		});
		
		leftDay.setPreferredSize(new Dimension(30, 30));
		rightDay.setPreferredSize(new Dimension(30, 30));
		
		leftDay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.previousDay();
				monthPanel.prevDay();
				dateLabel.setText(monthNames[model.getMonth()] + " " + model.getDay() + " "+ model.getYear());
				run();
			}
		});
		
		rightDay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.nextDay();
				monthPanel.nextDay();
				dateLabel.setText(monthNames[model.getMonth()] + " " + model.getDay() + " "+ model.getYear());
				run();
			}
		});
		
		
		//Date Label
		dateLabel.setOpaque(true);
		dateLabel.setBackground(Color.WHITE);
		
		JPanel leftButtonPanel = new JPanel();  //Top panel for (LEFT panel).
		leftButtonPanel.add(createButton);
		leftButtonPanel.add(today);
		leftButtonPanel.add(left);
		leftButtonPanel.add(right);
		
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(leftButtonPanel, BorderLayout.NORTH);
		leftPanel.add(dateLabel);
		leftPanel.add(monthPanel, BorderLayout.SOUTH);
		JPanel rightButtonPanel = new JPanel(); //Top panel for (RIGHT panel).
		rightButtonPanel.add(leftDay);
		rightButtonPanel.add(dayButton);
		rightButtonPanel.add(rightDay);
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
	
<<<<<<< HEAD
	//create event method
	public void createEvent(){
		

=======
	public void addTodayActionListener(ActionListener l){
		today.addActionListener(l);
>>>>>>> ee83f7888a5866a78ec925f4c89c99789e1f506f
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
	}

	@Override
	public void run() {
		leftPanel.remove(monthPanel);
		monthPanel = null;
		monthPanel = new MonthPanel(model.getMonth(), model.getYear(), model.getDay());
		leftPanel.add(monthPanel, BorderLayout.SOUTH);
		leftPanel.repaint();
		myFrame.pack();
		
	}
}

