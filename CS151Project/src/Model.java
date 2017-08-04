import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.event.ChangeListener;

import java.util.ArrayList;

public class Model {

	private GregorianCalendar cal = new GregorianCalendar();
	private ArrayList<ChangeListener> listeners = new ArrayList<>();
	private SelectedView currentView;
	
	public Model() {
		currentView = SelectedView.DAY;
	}
	
	//SETS CALENDAR TO TODAY'S DATE - JONATHAN 
	public void goToToday() {
		cal = new GregorianCalendar();
	}
	
	//GOES TO NEXT DAY - JONATHAN
	public void nextDay() {
		cal.add(Calendar.DAY_OF_MONTH, 1);
	}
	
	public void nextWeek() {
		cal.add(Calendar.WEEK_OF_YEAR, 1);
	}
	//GOES TO NEXT DAY - JONATHAN
	public void nextMonth() {
		cal.add(Calendar.MONTH, 1);
	}
	
	//GOES TO PREVIOUS DAY - JONATHAN 
	public void previousDay() {
		cal.add(Calendar.DAY_OF_MONTH, -1);
	}
	
	public void previousWeek() {
		cal.add(Calendar.WEEK_OF_YEAR, -1);
	}
	
	//GOES TO PREVIOUS DAY - JONATHAN 
	public void previousMonth() {
		cal.add(Calendar.MONTH, -1);
	}
	
	public SelectedView getView() {
		return currentView;
	}
	
	public void setDayView() {
		currentView = SelectedView.DAY;
	}
	
	public void setWeekView() {
		currentView = SelectedView.WEEK;
	}
	
	public void setMonthView() {
		currentView = SelectedView.MONTH;
	}
	
	public void setAgendaVie() {
		currentView = SelectedView.AGENDA;
	}
	
	/**
	 * Enumeration defining the views of the calendar/event list. 
	 * @author Jonathan 
	 */
	private enum SelectedView {
		DAY, WEEK, MONTH, AGENDA;
	}
}
