import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MonthPanel extends JPanel {

    private static final long serialVersionUID    = -6030371583841330976L;

    protected int month;
    protected int year;
    protected int day;
    public int tMonth;
    public int tDay;
    public int tYear;
    public Calendar today;
    public int lDay;
    private Model model;
    public ArrayList<buttonList > dayBtns = new ArrayList<buttonList >();
    public int lYear = 0;
    public int lMonth = 0;
    
    public String[] monthNames = 
    	{ "January", "February",
            "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"       
        };

    public String[] dayNames = 
    	{ "S", "M", "T", "W",
            "T", "F", "S"};

    public MonthPanel(int month, int year, int day, Model model) {
        this.month = month;
        this.year = year;
        this.day = day;
        this.model = model;
        this.add(createGUI());
        
    }

    public void nextDay()
    {
    	today.add(Calendar.DAY_OF_MONTH, 1);
    }
    public void prevDay()
    {
    	today.add(Calendar.DAY_OF_MONTH, -1);

    }
    
    protected JPanel createGUI() {
        JPanel monthPanel = new JPanel(true);
        monthPanel.setBorder(BorderFactory
                .createLineBorder(SystemColor.activeCaption));
        monthPanel.setLayout(new BorderLayout());
        monthPanel.setBackground(Color.WHITE);
        monthPanel.setForeground(Color.BLACK);
        monthPanel.add(createDaysGUI(), BorderLayout.SOUTH);

        return monthPanel;
    }


    protected JPanel createDaysGUI() {
        JPanel dayPanel = new JPanel(true);
        dayPanel.setLayout(new GridLayout(0, dayNames.length));
        
        today = Calendar.getInstance();
        tMonth = today.get(Calendar.MONTH);
        tYear = today.get(Calendar.YEAR);
        tDay = today.get(Calendar.DAY_OF_MONTH);
        
        
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        Calendar iterator = (Calendar) calendar.clone();
        iterator.add(Calendar.DAY_OF_MONTH,
                -(iterator.get(Calendar.DAY_OF_WEEK) - 1));

        Calendar maximum = (Calendar) calendar.clone();
        maximum.add(Calendar.MONTH, +1);

        for (int i = 0; i < dayNames.length; i++) {
            JPanel dPanel = new JPanel(true);
            dPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            JLabel dLabel = new JLabel(dayNames[i]);
            dPanel.add(dLabel);
            dayPanel.add(dPanel);
        }

        int count = 0;
        int limit = dayNames.length * 6;
        
        while (iterator.getTimeInMillis() < maximum.getTimeInMillis()) {
            lMonth = iterator.get(Calendar.MONTH);
            lYear = iterator.get(Calendar.YEAR);

            JPanel dPanel = new JPanel(true);
            dPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            JLabel dayLabel = new JLabel();
            JButton dayButton = new JButton();
            //dayButton.setPreferredSize(new Dimension(40, 40));

            
            
            
            
            
            
            if ((lMonth == month) && (lYear == year)) {
                lDay = iterator.get(Calendar.DAY_OF_MONTH);
                
                dayButton.setText(Integer.toString(lDay));
                dayLabel.setText(Integer.toString(lDay));
                
                if ((lMonth == month) && (lYear == year) && (day == lDay)) {
                    dPanel.setBackground(Color.ORANGE);
                    
                } else {
                    dPanel.setBackground(Color.WHITE);
                }
            } else {
                dayButton.setText("n");
                dayLabel.setText(" ");
                dPanel.setBackground(Color.WHITE);
            }
            
            if(dayButton.getText() == "n")
            {
            		dPanel.add(dayLabel);
            }
            else
            {
            		dPanel.add(dayButton);
            }
            
            buttonList test = new buttonList(dayButton, lYear, lMonth, lDay);
            dayBtns.add(test);
            
            
            //dPanel.add(dayButton);
            dayPanel.add(dPanel);
            iterator.add(Calendar.DAY_OF_YEAR, +1);
            count++;

        }

        for (int i = count; i < limit; i++) {
            JPanel dPanel = new JPanel(true);
            dPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            JLabel dayLabel = new JLabel();
            dayLabel.setText(" ");
            dPanel.setBackground(Color.WHITE);
            dPanel.add(dayLabel);
            dayPanel.add(dPanel);
        }
        

        
        return dayPanel;
    }
    
	public void setter(int month, int year)
	{
		this.month = month;
		this.year = year;
	}
	
	
	public class buttonList 
	{
		public JButton button;
		public int year;
		public int month;
		public int day;
		
		public buttonList(JButton button, int iYear, int iMonth, int iDay) 
		{
			this.button = button;
			this.year = iYear;
			this.month = iMonth;
			this.day = iDay;
		}
		
		
		
	}





}