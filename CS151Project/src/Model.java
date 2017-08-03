import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.event.ChangeListener;

import java.util.ArrayList;

public class Model {

	private GregorianCalendar cal = new GregorianCalendar();
	private ArrayList<ChangeListener> listeners = new ArrayList<>();
	
	public Model() {
		
	}
	
	//GOES TO NEXT DAY - JONATHAN
	public void nextDay() {
		cal.add(Calendar.DAY_OF_MONTH, 1);
	}
	
	//GOES TO PREVIOUS DAY - JONATHAN 
	public void previousDay() {
		cal.add(Calendar.DAY_OF_MONTH, -1);
	}
	
	//GOES TO NEXT DAY - JONATHAN
	public void nextMonth() {
		cal.add(Calendar.MONTH, 1);
	}
	
	//GOES TO PREVIOUS DAY - JONATHAN 
	public void previousMonth() {
		cal.add(Calendar.MONTH, -1);
	}
}
