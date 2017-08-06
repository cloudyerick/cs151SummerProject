import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.io.Serializable;
import java.util.ArrayList;

public class Model {

	private GregorianCalendar cal = new GregorianCalendar();
	private ArrayList<ChangeListener> listeners = new ArrayList<>();
	private TreeMap<String, HashSet<Event>> events = new TreeMap<>();
	private SelectedView currentView;
	private int presentDate;
	
	public Model() {
		currentView = SelectedView.DAY;
		presentDate = cal.get(Calendar.DAY_OF_MONTH);
	}


	public void attach(ChangeListener l) {
		listeners.add(l);
	}

	public void update() {
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}

	//SETS CALENDAR TO TODAY'S DATE - JONATHAN 
	public void goToToday() {
		cal = new GregorianCalendar();
	}

	public int getDay() {
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public int getMonth() {
		return cal.get(Calendar.MONTH);
		
	}

	public int getYear() {
		return cal.get(Calendar.YEAR);
	}

	
	
	
	
	//GOES TO NEXT DAY - JONATHAN
	public void nextDay() 
	{
		presentDate++;
		cal.add(Calendar.DAY_OF_MONTH, 1);


	}
	//GOES TO PREVIOUS DAY - JONATHAN 
	public void previousDay() {
		presentDate--;
		cal.add(Calendar.DAY_OF_MONTH, -1);
		
	}

	
	//GOES TO NEXT DAY - JONATHAN
	public void nextMonth() {
		cal.add(Calendar.MONTH, 1);
		update();
	
	}
	
	public void nextWeek() {
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		update();
		
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
	
	
	
	
	
	public void newCurrentDate(int year, int month, int day)
	{
		cal.set(year, month, day);
		presentDate = day;
	}
	
	
	
	
	
	
	public void setDay()
	{
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
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
	
	//WHETHER OR NOT THE DATE HAS ONE OR MORE EVENT 
	public boolean hasEvents(String date) {
		return events.containsKey(date);
	}
	
	//RETURNS WHETHER OR NOT EVENT WAS CREATED
	public boolean createEvent(String name, String date, int startTime, int endTime) {
		
		Event eventBeingCreated = new Event(name, date, startTime, endTime); //Creates the event 
		HashSet<Event> eventDupeTest = new HashSet<Event>();   
		if (hasEvents(date)) {
			eventDupeTest = this.getEvents(date);
		}
		boolean added = eventDupeTest.add(eventBeingCreated);
		//eventList.add(eventToCreate);
		events.put(date, eventDupeTest);
		return added;
	}
	
	public HashSet<Event> getEvents(String date) {
		return events.get(date);
	}

	/**
	 * Enumeration defining the views of the calendar/event list. 
	 * @author Jonathan 
	 */
	private enum SelectedView {
		DAY, WEEK, MONTH, AGENDA;
	}


	private static class Event implements Serializable {

		private static final long serialVersionUID    = -6030371583841330976L;
		private String name;
		private String date;
		private int startTime;
		private int endTime;

		private Event(String name, String date, int startTime, int endTime) {
			this.name = name;
			this.date = date;
			this.startTime = startTime;
			this.endTime = endTime; 
		}

		public String toString() {
			return startTime + "-" + endTime + ":" + name;
		} 
		
		//Need equals() method for treeset/treemap
		public boolean equals(Object other) {
			Event otherEvent = (Event) other;
			if (!this.name.equals(otherEvent.name)){
				return false;
			}
			else if (!this.date.equals(otherEvent.date)){
				return false;
			}
			else if (this.startTime != otherEvent.startTime) {
				return false;
			}
			else if (this.endTime != otherEvent.endTime) {
				return false;
			}
			return true;
		}
		
		public int hashCode() {
			return name.length() + date.length() + startTime + endTime;
		}
	}
}
