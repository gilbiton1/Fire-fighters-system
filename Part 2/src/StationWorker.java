import java.util.ArrayList;

public class StationWorker extends Thread implements Costable{
	private double salary;
	private long workTimeValue;
	private String name;
	private static boolean workIsDone=false;
	private IFsystem IF;
	private ArrayList<FireVehicle> trucks;
	private BoundedQueue<ReadyEvent> ReadyEvents;

	public StationWorker(String name, IFsystem IF,ArrayList<FireVehicle> trucks, BoundedQueue<ReadyEvent> ReadyEvents,double workTimeValue) {
		this.name=name;
		this.IF= IF;
		this.trucks=trucks;
		this.ReadyEvents=ReadyEvents;
		this.workTimeValue=(long) workTimeValue;
	}

	public double getSalary() {
		return salary;
	}

	public void run() {
		while(true) {
			FireEvent newEvent = takeEventFromIF();
			if(newEvent==null) {	//indicates that there is no more events
				if(workIsDone)
					break;
				synchronized(this) {
					try {
						this.wait();		//wait till day is over
					} catch (InterruptedException e) {
						e.printStackTrace();
					}	
				}
			}
			else {
				ReadyEvent ready = processEventData(newEvent);
				holdWorkTime();
				enterToReadyEvents(ready);
				addToSalary();
			}
		}
		salary+=100;		//get payed for work day
	}


	private FireEvent takeEventFromIF() {
		return IF.takeEvent();
	}

	//Process the event data and return new event with vehicles number
	private synchronized ReadyEvent processEventData(FireEvent newEvent) {
		int available=getNumOfAvailableTrucks();
		int numTrucks=Math.max(2,available);
		int numPlanes=0;
		switch(newEvent.getState()) {
		case 1:
			if(numTrucks>4)
				numTrucks=4;
			break;
		case 2:
			numTrucks+=1;
			if(numTrucks>5)
				numTrucks=5;
			break;
		case 3:
			numPlanes=1;
			numTrucks+=3;
			if(numTrucks>7)
				numTrucks=7;
			break;
		case 4:
			numPlanes=2;
			numTrucks+=5;
			if(numTrucks>9)
				numTrucks=9;
			break;
		case 5:
			numPlanes=3;
			numTrucks+=8;
			if(numTrucks>12)
				numTrucks=12;
			break;
		}
		return new ReadyEvent(newEvent,ReadyEvents,numTrucks,numPlanes); 
	}

	private void holdWorkTime() {
		try {
			sleep(workTimeValue*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void enterToReadyEvents(ReadyEvent ready) {
		ReadyEvents.insert(ready);
	}

	private void addToSalary() {
		salary+=3;
	}

	private synchronized int getNumOfAvailableTrucks() {
		int available=0;
		for (int i=0;i<trucks.size();i++) 
			available++;
		return available;
	}

	//day is over
	public synchronized void setWorkIsDone() {
		workIsDone=true;
		IF.stopWait();
		ReadyEvents.stopWait();
		this.notifyAll();
	}

	public void initiateStatics() {
		workIsDone=false;
	}
}
