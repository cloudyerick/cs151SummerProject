import java.io.Serializable;

public class Event implements Serializable{


		private String name;
		private String date;
		private int startTime;
		private int endTime;

		public Event(String name, String date, int startTime, int endTime) {
			this.name = name;
			this.date = date;
			this.startTime = startTime;
			this.endTime = endTime; 
		}

		public int getStartTime() {
			return startTime;
		}
		
		public int getEndTime() {
			return endTime;
		}
		
		public String getName() {
			return name;
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
