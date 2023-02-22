
public class ReadyEvent {

	private String originAdress;
	private double distance;
	private int State;
	private int numTrucks;
	private int numPlanes;
	private BoundedQueue <ReadyEvent> ReadyEvents;

	//constructor
	public ReadyEvent(FireEvent ready,BoundedQueue <ReadyEvent> ReadyEvents, int numTrucks, int numPlanes) {
		originAdress = ready.getAddress();
		distance = ready.getDistance();
		State = ready.getState();
		this.ReadyEvents = ReadyEvents;
		this.numTrucks = numTrucks;
		this.numPlanes = numPlanes;
	}

	public String getOriginAdress() {
		return originAdress;
	}

	public double getDistance() {
		return distance;
	}

	public int getState() {
		return State;
	}

	public int getNumTrucks() {
		return numTrucks;
	}

	public int getNumPlanes() {
		return numPlanes;
	}	

	public BoundedQueue<ReadyEvent> getReadyEvents() {
		return ReadyEvents;
	}

}
