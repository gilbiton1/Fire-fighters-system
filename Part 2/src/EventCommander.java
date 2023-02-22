import java.util.ArrayList;

public class EventCommander extends Thread {
	private BoundedQueue <ReadyEvent> ReadyEvents;
	private boolean isSevere, occupied;
	private ArrayList<FireVehicle> trucks;
	private ArrayList<FireVehicle> planes;
	private ArrayList<FireVehicle> eventVehicles;
	private ArrayList<EventCommander> commanders;
	private EventCommander another;
	private FireStationManager chief;
	private static boolean workIsDone;


	public EventCommander(BoundedQueue <ReadyEvent> ReadyEvents,ArrayList<EventCommander> commanders, FireStationManager chief
			, ArrayList<FireVehicle> trucks,ArrayList<FireVehicle> planes) {
		this.ReadyEvents=ReadyEvents;
		this.chief=chief;
		this.trucks = trucks;
		this.planes=planes;
		this.commanders=commanders;
		eventVehicles = new ArrayList<>();
	}

	public void run() {
		while(true) {
			if(!this.occupied) {	 //the commander available		
				ReadyEvent newEvent = takeEventFromQueue();
				if(newEvent==null) 				//no more events
					break;
				outToMission(newEvent);
				waitHandleTime(newEvent);
				releaseVehicles(newEvent);
				reportToManager(newEvent);	
			} else {
				synchronized(this){
					try {
						this.wait();		//occupied
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			if(workIsDone)	//day is over
				break;
		}
	}

	//extract event from eventQueue
	private ReadyEvent takeEventFromQueue() {
		ReadyEvent newEvent= ReadyEvents.extract();
		if(newEvent!=null)
			occupied=true;
		return newEvent;
	}

	//go out for mission and returns if happened
	private synchronized void outToMission(ReadyEvent newEvent) {		
		int numTrucks = newEvent.getNumTrucks();
		int numPlanes = newEvent.getNumPlanes();
		while(!workIsDone) {
			if(notEnoughAvailables(numTrucks,numPlanes)) {	//waits until there is enough vehicles
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else {
				takeVehicles(numTrucks,numPlanes); 
				if(newEvent.getState()==5)					//for severe events get another commander
					another = getAnotherCommander();
				break;
			}
		}
	}

	//checks if there are enough vehicles for the mission
	private boolean notEnoughAvailables(int numTrucks,int numPlanes) {
		if (numTrucks > trucks.size() || numPlanes > planes.size())
			return true;
		return false;
	}

	//remove vehicles from general list and add to event list
	private void takeVehicles(int numTrucks, int numPlanes) {
		for(int i=0; i<numTrucks; i++) {
			eventVehicles.add(trucks.remove(0)); 
		}
		for(int i=0; i<numPlanes; i++) {
			eventVehicles.add(planes.remove(0)); 
		}		
	}

	//try to get another commander if the event is severe
	private synchronized EventCommander getAnotherCommander() {
		for(int i=0;i<commanders.size();i++) {
			if(!commanders.get(i).occupied) {
				commanders.get(i).occupied=true;
				return commanders.get(i);
			}
		}
		return null;
	}

	//sleep handle time 
	private void waitHandleTime(ReadyEvent newEvent) {
		double temp=(newEvent.getState()*2)+newEvent.getDistance();
		double temp2=newEvent.getNumPlanes()+newEvent.getNumTrucks();
		long time=(long)(temp/temp2);
		try {
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//release the vehicles when the commander comes back from mission
	private synchronized void releaseVehicles(ReadyEvent newEvent) {
		for (int i=0;i<newEvent.getNumTrucks();i++) 
			trucks.add(eventVehicles.remove(0));		//remove from event list and returns to general list
		for (int i=0;i<newEvent.getNumPlanes();i++) 
			planes.add(eventVehicles.remove(0));
		occupied=false;
		if(another!=null)		//if the event was severe release the second commander
			another.occupied=false;
		this.notifyAll();		//wake up other commanders that needs vehicles
	}

	//reports the manager event details
	private void reportToManager(ReadyEvent newEvent) {
		chief.updateManager(newEvent);
	}

	//manager informed that the day is over
	public synchronized void setWorkIsDone() {
		workIsDone=true;
		ReadyEvents.stopWait();
		this.notifyAll();
	}

	public void initiateStatics() {
		workIsDone=false;
	}
}
