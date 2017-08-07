import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Model {

	private GregorianCalendar cal = new GregorianCalendar();
	private ArrayList<ChangeListener> listeners = new ArrayList<>();
	private TreeMap<String, HashSet<Event>> events = new TreeMap<>();
	private int presentDate;
	
	public Model() {
		presentDate = cal.get(Calendar.DAY_OF_MONTH);
	}

	public void setDate(int iYear, int iMonth, int iDay)
	{
		cal.set(iYear, iMonth, iDay);
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
				System.out.println(hoursInEvents);
				for (int i = 0; i <= hoursInEvents; i++) {
					if (e.getStartTime() == event.getStartTime() + i || e.getEndTime() == event.getEndTime() + i) {
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
		boolean confliction = eventHasConfliction(eventBeingCreated);
		if (!confliction) {
			events.put(date, eventDupeTest);
		}
		else {
			events.get(date).remove(eventBeingCreated);
		}
		//events.put(date, eventDupeTest);
		return !confliction;
	}
	
	//LOADS EVENTS FROM INPUT.TXT, returns true if successful
	public void loadEventsFromeFile(){
		
		
		//get all data into lines ArayList
		ArrayList<String> lines = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
				/*//test
				System.out.println(line);
				*/
			}
			
		} catch (IOException e){
			e.printStackTrace();
		}
		
		//process all lines
		for(String line: lines){
			
			String[] s = line.split(";");
			
			String name = s[0];
			String year = s[1];
			String startMonth = s[2];
			String endMonth = s[3];
			String days = s[4];
			String startTime = s[5];
			String endTime = s[6];
			
			int yearInt = Integer.parseInt(year);
			int startMonthInt = Integer.parseInt(startMonth);
			int endMonthInt = Integer.parseInt(endMonth);
			int startTimeInt = Integer.parseInt(startTime);
			int endTimeInt = Integer.parseInt(endTime);
			
			//this value will change
			String date;
			
			//new calendar starting on beginning date range
			GregorianCalendar cal1 = new GregorianCalendar(yearInt, startMonthInt, 1);
			GregorianCalendar cal2 = new GregorianCalendar(yearInt, startMonthInt, 1);
			
			//calculate # of days in range
			int daysInRange = 0;
			for(int i = startMonthInt; i <= endMonthInt; i++){
				daysInRange += cal2.getActualMaximum(Calendar.DAY_OF_MONTH);
				cal2.add(Calendar.MONTH, 1);
			}
			
			for(int i = 0; i < daysInRange; i++){
				if(days.contains("S") && (cal1.get(Calendar.DAY_OF_WEEK) == 1)){
					
					date = String.valueOf(cal1.get(Calendar.MONTH)) + "/" + String.valueOf(cal1.get(Calendar.DAY_OF_MONTH)) + "/" 
					 			+ String.valueOf(cal1.get(Calendar.YEAR));
				
					createEvent(name, date, startTimeInt, endTimeInt);
					
					
				}
				
				if(days.contains("M") && (cal1.get(Calendar.DAY_OF_WEEK) == 2)){
					
					date = String.valueOf(cal1.get(Calendar.MONTH)) + "/" + String.valueOf(cal1.get(Calendar.DAY_OF_MONTH)) + "/" 
				 			+ String.valueOf(cal1.get(Calendar.YEAR));
			
					createEvent(name, date, startTimeInt, endTimeInt);
					/*//test
					System.out.println(name + "+" + date + "+" + startTimeInt + "+" + endTimeInt);
					*/
				}
				
				if(days.contains("T") && (cal1.get(Calendar.DAY_OF_WEEK) == 3)){
					
					date = String.valueOf(cal1.get(Calendar.MONTH)) + "/" + String.valueOf(cal1.get(Calendar.DAY_OF_MONTH)) + "/" 
				 			+ String.valueOf(cal1.get(Calendar.YEAR));
			
					createEvent(name, date, startTimeInt, endTimeInt);
				}
				
				if(days.contains("W") && (cal1.get(Calendar.DAY_OF_WEEK) == 4)){
					
					date = String.valueOf(cal1.get(Calendar.MONTH)) + "/" + String.valueOf(cal1.get(Calendar.DAY_OF_MONTH)) + "/" 
				 			+ String.valueOf(cal1.get(Calendar.YEAR));
			
					createEvent(name, date, startTimeInt, endTimeInt);
				}
				
				if(days.contains("H") && (cal1.get(Calendar.DAY_OF_WEEK) == 5)){
					
					date = String.valueOf(cal1.get(Calendar.MONTH)) + "/" + String.valueOf(cal1.get(Calendar.DAY_OF_MONTH)) + "/" 
				 			+ String.valueOf(cal1.get(Calendar.YEAR));
			
					createEvent(name, date, startTimeInt, endTimeInt);
				}
				
				if(days.contains("F") && (cal1.get(Calendar.DAY_OF_WEEK) == 6)){
					
					date = String.valueOf(cal1.get(Calendar.MONTH)) + "/" + String.valueOf(cal1.get(Calendar.DAY_OF_MONTH)) + "/" 
				 			+ String.valueOf(cal1.get(Calendar.YEAR));
			
					createEvent(name, date, startTimeInt, endTimeInt);
				}
				if(days.contains("A") && (cal1.get(Calendar.DAY_OF_WEEK) == 7)){
					
					date = String.valueOf(cal1.get(Calendar.MONTH)) + "/" + String.valueOf(cal1.get(Calendar.DAY_OF_MONTH)) + "/" 
				 			+ String.valueOf(cal1.get(Calendar.YEAR));
			
					createEvent(name, date, startTimeInt, endTimeInt);
				}
				
				//Increment calendar
				cal1.add(Calendar.DAY_OF_MONTH, 1);	
			}	
		}
	}
	
	public HashSet<Event> getEvents(String date) {
		return events.get(date);
	}

	/**
	 * Enumeration defining the views of the calendar/event list. 
	 * @author Jonathan 
	 */


}
