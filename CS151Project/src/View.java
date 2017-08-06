import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
	
	
	
	private JTextArea eventList = new JTextArea(" " + cal.get(cal.MONTH) + "/" + cal.get(cal.DAY_OF_MONTH) + "/" + cal.get(cal.YEAR) + ":");
	
	
	
	
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
		monthPanel = new MonthPanel(cal.get(cal.MONTH), cal.get(cal.YEAR), cal.get(cal.DAY_OF_MONTH), model);
		myFrame.setLocation(20, 20);
		
		//ALL BUTTON FUNCTIONALITY GOES HERE:
		//ALL BUTTON FUNCTIONALITY GOES HERE:
		//ALL BUTTON FUNCTIONALITY GOES HERE:
		
		
		//JTextArea eventList = new JTextArea();
		eventList.setEditable(false);
		eventList.setRows(24);
		//eventList.setText(" " + cal.get(cal.MONTH) + "/" + cal.get(cal.DAY_OF_MONTH) + "/" + cal.get(cal.YEAR) + ":");
		
		
		
		//TODAY BUTTON
		JButton today = new JButton("Today");  //TODAY BUTTON SETS MODEL AND VIEW TO CURRENT TIME - JONATHAN 
		today.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.goToToday();
				dateLabel.setText(monthNames[model.getMonth()] + " " + cal.get(cal.DAY_OF_MONTH) + " "+ model.getYear());
				eventList.setText(" " + model.getMonth() + "/" + model.getDay() + "/" + model.getYear() + ":");
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
				eventList.setText(" " + model.getMonth() + "/" + model.getDay() + "/" + model.getYear() + ":");
				run();
			}
		});
		
		right.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.nextMonth();
				model.setDay();
				dateLabel.setText(monthNames[model.getMonth()] + " " + model.getDay() + " "+ model.getYear());
				eventList.setText(" " + model.getMonth() + "/" + model.getDay() + "/" + model.getYear() + ":");
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
				eventList.setText(" " + model.getMonth() + "/" + model.getDay() + "/" + model.getYear() + ":");
				run();
			}
		});
		
		rightDay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.nextDay();
				monthPanel.nextDay();
				dateLabel.setText(monthNames[model.getMonth()] + " " + model.getDay() + " "+ model.getYear());
				eventList.setText(" " + model.getMonth() + "/" + model.getDay() + "/" + model.getYear() + ":");
				run();
			}
		});
		
        int a;
        for(a = 0; a < monthPanel.dayBtns.size(); a++)
        {
        	int b = a;
        	monthPanel.dayBtns.get(a).button.addActionListener (new ActionListener () {
				@Override
				public void actionPerformed(ActionEvent e) {
					model.newCurrentDate(monthPanel.dayBtns.get(b).year, monthPanel.dayBtns.get(b).month, monthPanel.dayBtns.get(b).day);
			        monthPanel.month = monthPanel.dayBtns.get(b).month;
			        monthPanel.year = monthPanel.dayBtns.get(b).year;
			        monthPanel.day = monthPanel.dayBtns.get(b).day;
					System.out.println("Year Array:" + monthPanel.dayBtns.get(b).year);
					System.out.println("Month Array:" +monthPanel.dayBtns.get(b).month);
					System.out.println("Days Array:" +monthPanel.dayBtns.get(b).day);
					System.out.println("Model Year:" + model.getYear());
					System.out.println("Model Month:" + model.getMonth());
					System.out.println("Model Day:" + model.getDay());
					System.out.println("------------------------------");
					dateLabel.setText(monthNames[model.getMonth()] + " " + model.getDay() + " "+ model.getYear());
					eventList.setText(" " + model.getMonth() + "/" + model.getDay() + "/" + model.getYear() + ":");
					run();
				}
        		
        	});
        }
		
		
		
		//CREATE BUTTON
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Dialog frame
				JDialog createDialog = new JDialog();
				createDialog.setTitle("Create An Event");
				createDialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
				
				//Text Components 
				JTextField eventName = new JTextField("Untitled Event", 30);
				JTextField startTime = new JTextField("Start Time");
				JLabel toLabel = new JLabel("to");
				JTextField endTime = new JTextField("End Time");
				JLabel instruction = new JLabel("Enter time in hour format (0-23): ");
				//JCheckBox repeatBox = new JCheckBox("Repeat", false); 
				JButton saveButton = new JButton("Save");
				saveButton.addActionListener(new ActionListener() {
					@Override

					public void actionPerformed(ActionEvent e) {
						try {
							if (Integer.valueOf(startTime.getText()) <= 23 && Integer.valueOf(startTime.getText()) >= 0 && 
									Integer.valueOf(endTime.getText()) <= 23 && Integer.valueOf(endTime.getText()) >= 0 && eventName.getText().length() > 0) {
								//SAVE FUNCTIONALITY HERE
								createDialog.dispose();
							}
							else {
								//ERROR DIALOG FROM PRE-CONDITION 
								JDialog errorDialog = new JDialog();
								errorDialog.setTitle("Error");
								errorDialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
								JPanel errorPanel = new JPanel();
								JLabel errorMessage = new JLabel("Invalid event name or time. Try again.");
								JButton okButton = new JButton("Ok");
								okButton.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										errorDialog.dispose();
									}
								});
								errorPanel.setLayout(new BorderLayout());
								errorPanel.add(errorMessage, BorderLayout.NORTH);
								errorPanel.add(okButton, BorderLayout.CENTER);
								errorDialog.add(errorPanel);
								errorDialog.setLocationRelativeTo(createDialog);
								errorDialog.pack();
								errorDialog.setVisible(true);
							}
						}
						catch (NumberFormatException n) {
							//ERROR DIALOG FROM EXCEPTION
							JDialog errorDialog = new JDialog();
							errorDialog.setTitle("Error");
							errorDialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
							JPanel errorPanel = new JPanel();
							JLabel errorMessage = new JLabel("Invalid event name or time. Try again.");
							JButton okButton = new JButton("Ok");
							okButton.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									errorDialog.dispose();
								}
							});
							errorPanel.setLayout(new BorderLayout());
							errorPanel.add(errorMessage, BorderLayout.NORTH);
							errorPanel.add(okButton, BorderLayout.CENTER);
							errorDialog.add(errorPanel);
							errorDialog.setLocationRelativeTo(createDialog);
							errorDialog.pack();
							errorDialog.setVisible(true);
						}
						String stringDate = String.valueOf(model.getMonth()) + "/" + String.valueOf(model.getDay()) + "/" 
						+ String.valueOf(model.getYear());
						boolean eventCreated = model.createEvent(eventName.getText(), stringDate, Integer.valueOf(startTime.getText()), Integer.valueOf(endTime.getText()));
						System.out.println(eventCreated);
					}
					
				});
				
				//DONT NEED A REPEAT FUNCTIONALITY, KEEP AS COMMENT FOR NOW
				/*
				repeatBox.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (repeatBox.isSelected()) {
						JDialog repeatDialog = new JDialog();
						repeatDialog.setTitle("Repeat");
						repeatDialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
						JPanel repeatPanel = new JPanel();
						
						JPanel boxStrip = new JPanel();
						JPanel monthStrip = new JPanel();
						
						JLabel topLabel = new JLabel("Repeat every ");
						JLabel fromLabel = new JLabel("from");
						JLabel toLabel = new JLabel("to");
						
						String[] months = {"January", "Februrary", "March", "April", "May", "June", "July", "August", "September",
								"October", "November", "December"
						};
						
						JComboBox startMonth = new JComboBox(months);	
						JComboBox endMonth = new JComboBox(months);
						
						JCheckBox sunday = new JCheckBox("S", false);
						JCheckBox monday = new JCheckBox("M", false);
						JCheckBox tuesday = new JCheckBox("T", false);
						JCheckBox wednesday = new JCheckBox("W", false);
						JCheckBox thursday = new JCheckBox("T", false);
						JCheckBox friday = new JCheckBox("F", false);
						JCheckBox saturday = new JCheckBox("S", false);
						
						JButton okButton = new JButton("Ok");
					
						monthStrip.add(fromLabel);
						monthStrip.add(startMonth);
						monthStrip.add(toLabel);
						monthStrip.add(endMonth);
						
						boxStrip.add(sunday);
						boxStrip.add(monday);
						boxStrip.add(tuesday);
						boxStrip.add(wednesday);
						boxStrip.add(thursday);
						boxStrip.add(friday);
						boxStrip.add(saturday);
						
						BoxLayout layout = new BoxLayout(repeatPanel, BoxLayout.Y_AXIS);
						repeatPanel.setLayout(layout);
						repeatPanel.add(topLabel);
						repeatPanel.add(boxStrip);
						repeatPanel.add(monthStrip);
						repeatPanel.add(okButton);
						
						repeatDialog.add(repeatPanel);
						repeatDialog.pack();
						repeatDialog.setVisible(true);
						}
					}
				});
				*/
				
				saveButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//INSERT SAVE FUNCTIONALITY
						createDialog.dispose();
					}
				});
				
				createDialog.setLayout(new BorderLayout());
				createDialog.add(eventName, BorderLayout.NORTH);
				createDialog.add(instruction, BorderLayout.CENTER);
				JPanel timeStrip = new JPanel();
				timeStrip.add(startTime);
				timeStrip.add(toLabel);
				timeStrip.add(endTime);
				//timeStrip.add(repeatBox);
				timeStrip.add(saveButton);
				
				createDialog.setLocationRelativeTo(myFrame);
				createDialog.add(timeStrip, BorderLayout.SOUTH);
				createDialog.pack();
				createDialog.setVisible(true);
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
	
	//create event method
	public void createEvent(){
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
		monthPanel = new MonthPanel(model.getMonth(), model.getYear(), model.getDay(), model);
		leftPanel.add(monthPanel, BorderLayout.SOUTH);
		leftPanel.repaint();
		myFrame.pack();
		
        int a;
        for(a = 0; a < monthPanel.dayBtns.size(); a++)
        {
        	int b = a;
        	monthPanel.dayBtns.get(a).button.addActionListener (new ActionListener () {
				@Override
				public void actionPerformed(ActionEvent e) {
					model.newCurrentDate(monthPanel.dayBtns.get(b).year, monthPanel.dayBtns.get(b).month, monthPanel.dayBtns.get(b).day);
			        monthPanel.month = monthPanel.dayBtns.get(b).month;
			        monthPanel.year = monthPanel.dayBtns.get(b).year;
			        monthPanel.day = monthPanel.dayBtns.get(b).day;
					System.out.println("Year Array:" + monthPanel.dayBtns.get(b).year);
					System.out.println("Month Array:" +monthPanel.dayBtns.get(b).month);
					System.out.println("Days Array:" +monthPanel.dayBtns.get(b).day);
					System.out.println("Model Year:" + model.getYear());
					System.out.println("Model Month:" + model.getMonth());
					System.out.println("Model Day:" + model.getDay());
					System.out.println("------------------------------");
					dateLabel.setText(monthNames[model.getMonth()] + " " + model.getDay() + " "+ model.getYear());
					eventList.setText(" " + model.getMonth() + "/" + model.getDay() + "/" + model.getYear() + ":");
					run();
				}
        		
        	});
        }
		
	}

}











