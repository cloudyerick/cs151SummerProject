import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;
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

public class View  implements ChangeListener, Runnable {
	
	private enum SelectedView {
		DAY, WEEK, MONTH, AGENDA;
	}

	private SelectedView currentView;
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
		currentView = SelectedView.DAY; //View defaults to day view. 
		myFrame = new JFrame("Calendar");
		monthPanel = new MonthPanel(cal.get(cal.MONTH), cal.get(cal.YEAR), cal.get(cal.DAY_OF_MONTH), model);
		myFrame.setLocation(20, 20);
		
		//ALL BUTTON FUNCTIONALITY GOES HERE:
		//ALL BUTTON FUNCTIONALITY GOES HERE:
		//ALL BUTTON FUNCTIONALITY GOES HERE:

		eventList.setEditable(false);
		eventList.setRows(24);
		
		//TODAY BUTTON
		JButton today = new JButton("Today");  //TODAY BUTTON SETS MODEL AND VIEW TO CURRENT TIME - JONATHAN 
		today.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.goToToday();
				dateLabel.setText(monthNames[model.getMonth()] + " " + cal.get(cal.DAY_OF_MONTH) + " "+ model.getYear());
				eventList.setText(" " + model.getMonth() + "/" + model.getDay() + "/" + model.getYear() + ":");
				updateView();
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
				updateView();
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
				updateView();
				run();
			}
		});
		
		//preferred size does not run on windows
		//leftDay.setPreferredSize(new Dimension(30, 30));
		//rightDay.setPreferredSize(new Dimension(30, 30));
		
		leftDay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.previousDay();
				monthPanel.prevDay();
				dateLabel.setText(monthNames[model.getMonth()] + " " + model.getDay() + " "+ model.getYear());
				eventList.setText(" " + model.getMonth() + "/" + model.getDay() + "/" + model.getYear() + ":");
				updateView();
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
				updateView();
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
		
        //FROM FILE BUTTON
		fromFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				model.loadEventsFromeFile();
				/*
				setDayView();
				updateView();
				*/
			}
		});
		
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
						if (!eventCreated) {
							JDialog conflictionDialog = new JDialog();
							JPanel conflictionPanel = new JPanel(); 
							conflictionDialog.setTitle("Time Confliction Error");
							conflictionDialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
							conflictionPanel.add(new JLabel("Time confliction occurred. Try Again."));
							JButton okButton2 = new JButton("Ok");
							conflictionPanel.add(okButton2);
							okButton2.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									conflictionDialog.dispose();
								}	
							});
							conflictionDialog.add(conflictionPanel);
							conflictionDialog.pack();
							conflictionDialog.setVisible(true);
							
						}
						updateView();
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
		
		dayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setDayView();
				updateView();
			}
		});
		
		weekButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setWeekView();
				updateView();
			}
		});
		
		monthButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setMonthView();
				updateView();
			}
		});
		
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

	public void updateView() {
		String date = model.getMonth() + "/" + model.getDay() + "/" + model.getYear();
		String eventsDisplayText = "";
		try {
			if (this.currentView == SelectedView.DAY) {
				eventsDisplayText = date + ":" + "\n";
				if (model.hasEvents(date)) {
					try {
						for (Event e: model.getEvents(date)) {
							eventsDisplayText += e.getName() + " - " + "From hour " +  
									e.getStartTime() + " to hour " + e.getEndTime() + "\n";
						} 
					}
					catch (NullPointerException n) {
						return;
					}
				}
				else {
					eventsDisplayText = date + ":";
				}
				eventList.setText(eventsDisplayText);
			}
			
			/**
			 * WORKING ON THIS CURRENTLY - JONATHAN 
			 */
			if (this.currentView == SelectedView.WEEK) {
				

				GregorianCalendar c = new GregorianCalendar();
				Date date1 = new Date(model.getYear(), model.getMonth(), model.getDay());
				
				
				
				c.setTime(date1);
				c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				System.out.println("MONDAY: " + c.getTime());
				
				
				
				c.setTime(date1);
				c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
				System.out.println("TUESDAY: " + c.getTime());
				
				
				c.setTime(date1);
				c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
				System.out.println("WEDNESDAY: " + c.getTime());
				
				
				c.setTime(date1);
				c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
				System.out.println("THURSDAY: " + c.getTime());
				
				c.setTime(date1);
				c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
				System.out.println("FRIDAY: " + c.getTime());
				
				
				c.setTime(date1);
				c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
				System.out.println("SATURDAY: " + c.getTime());
				
				c.setTime(date1);
				c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				System.out.println("SUNDAY: " + c.getTime());

	
			}
			
			//MONTH VIEW 
			if (this.currentView == SelectedView.MONTH) {
				GregorianCalendar placeholderCal = new GregorianCalendar();
				placeholderCal.set(model.getYear(), model.getMonth(), 1);
				for (int i = 1; i <= model.getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
					System.out.println("actual max: " + model.getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH));
					date = model.getMonth() + "/" + i + "/" + model.getYear();
					if (model.hasEvents(date)) {
						eventsDisplayText += date + ":" + "\n";
					}
					try {
						for (Event e: model.getEvents(date)) {
							eventsDisplayText += e.getName() + " - " + "From hour " +  
									e.getStartTime() + " to hour " + e.getEndTime() + "\n";
						}
					}
					catch (NullPointerException n) {
						continue;
					}
				}
				eventList.setText(eventsDisplayText);
			}
			
			if (this.currentView == SelectedView.AGENDA) {
				
			}
			
		}
		catch (NullPointerException n) {
		}
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
		updateView();
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
					System.out.println("Month Array:" + monthPanel.dayBtns.get(b).month);
					System.out.println("Days Array:" + monthPanel.dayBtns.get(b).day);
					System.out.println("Model Year:" + model.getYear());
					System.out.println("Model Month:" + model.getMonth());
					System.out.println("Model Day:" + model.getDay());
					System.out.println("------------------------------");
					dateLabel.setText(monthNames[model.getMonth()] + " " + model.getDay() + " "+ model.getYear());
					eventList.setText(" " + model.getMonth() + "/" + model.getDay() + "/" + model.getYear() + ":");
					setDayView();
					updateView();
					run();
				}
        		
        	});
        }
	}
	
	public void setWeekView() {
		currentView = SelectedView.WEEK;
	}

	public void setMonthView() {
		currentView = SelectedView.MONTH;
	}

	public void setAgendaView() {
		currentView = SelectedView.AGENDA;
	}
	
	//Might not need 
	public SelectedView getView() {
		return currentView;
	}

	public void setDayView() {
		currentView = SelectedView.DAY;
	}

}











