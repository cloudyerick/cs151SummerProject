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
	private int presentDate;
	
	public Model() {
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

	public GregorianCalendar getCalendar() {
		return this.cal;
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

	public void newCurrentDate(int year, int month, int day)
	{
		cal.set(year, month, day);
		presentDate = day;
	}

	public void setDay()
	{
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
	}

	//WHETHER OR NOT THE DATE HAS ONE OR MORE EVENT 
	public boolean hasEvents(String date) {
		return events.containsKey(date);
	}

	public boolean eventHasConfliction(Event e) {
		String stringDate = String.valueOf(getMonth()) + "/" + String.valueOf(getDay()) + "/" 
				+ String.valueOf(getYear());
		//Iterate through all events on creation date. 
		try {
			for (Event event: events.get(stringDate)) {  
				// Checks to see if e's start time of end time intersects with any of the store event's times. 
				int hoursInEvents = event.getEndTime() - event.getStartTime();
				for (int i = 0; i <= hoursInEvents; i++) {
					if (e.getStartTime() == event.getStartTime() + i || e.getEndTime() == event.getStartTime() + i) {
						return true;
					}
				}
			}
		}
		catch (NullPointerException n) {
			System.out.println("No events on date.");
		}
		return false;
	}

	//RETURNS WHETHER OR NOT EVENT WAS CREATED
	public boolean createEvent(String name, String date, int startTime, int endTime) {
		
		Event eventBeingCreated = new Event(name, date, startTime, endTime); //Creates the event 
		System.out.println("Confliction: " +eventHasConfliction(eventBeingCreated));
		HashSet<Event> eventDupeTest = new HashSet<Event>();   
		if (hasEvents(date)) {
			eventDupeTest = this.getEvents(date);
		}
		boolean added = eventDupeTest.add(eventBeingCreated);
		//eventList.add(eventToCreate);
		if (!eventHasConfliction(eventBeingCreated)) {
			events.put(date, eventDupeTest);
		}
		//events.put(date, eventDupeTest);
		return added;
	}
	
	public HashSet<Event> getEvents(String date) {
		return events.get(date);
	}

	/**
	 * Enumeration defining the views of the calendar/event list. 
	 * @author Jonathan 
	 */


}
