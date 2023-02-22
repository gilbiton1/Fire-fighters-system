public class FireEvent implements Comparable<FireEvent>{

	private int eventID;
	private String Address;
	private int State;
	private int Area;
	private long Arrival;		//time that the cal arrived
	private Queue <FireEvent> eventQueue;
	private double distance; 
	private int IFindex;

	//constructor
	public FireEvent(int eventID, int State, int Area , long Arrival , String Address , Queue <FireEvent> eventQueue) {
		this.eventID = eventID;
		this.State = State;
		this.Area = Area;
		this.Arrival = Arrival;
		this.Address = Address;
		this.eventQueue = eventQueue;
	}

	public int getEventID() {
		return eventID;
	}

	public int getArea() {
		return Area;
	}

	public long getArrival() {
		return Arrival;
	}

	public String getAddress() {
		return Address;
	}

	public Queue<FireEvent> getEventQueue() {
		return eventQueue;
	}
	public int getIFindex() {
		return IFindex;
	}

	public int getState() {
		return State;
	}

	public double getDistance() {
		return distance;
	}
	public void setIFindex(int IFindex) {
		this.IFindex = IFindex;
	}

	public void setDistance(double distance) {
		this.distance+=distance;
	}
	
	public void setEventID(int eventID) {
		this.eventID=eventID;
	}

	public int compareTo(FireEvent other) {		//when the event entered IF system
		return IFindex- other.getIFindex();	
	}


}