import java.util.ArrayList;

public class FireStationManager extends Thread{
	private ArrayList <Dispatcher> Dispatchers;
	private ArrayList <EventHandler> EventHandlers;
	private	ArrayList <StationWorker> StationWorkers;
	private ArrayList <EventCommander> EventCommanders;
	private int numOfExpectedCalls,curEvents,numOfSevere;
	private boolean dayIsOver;
	private double salaries;

	//constructor
	public FireStationManager(int numOfExpectedCalls,ArrayList <Dispatcher> Dispatchers,ArrayList <EventHandler> EventHandlers,
			ArrayList <StationWorker> StationWorkers,ArrayList <EventCommander> EventCommanders){
		this.numOfExpectedCalls=numOfExpectedCalls;
		this.Dispatchers=Dispatchers;
		this.EventHandlers=EventHandlers;
		this.StationWorkers=StationWorkers;
		this.EventCommanders=EventCommanders;
	}

	public void run() {
		while(!dayIsOver) {
			synchronized(this){
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		informEveryOne();
		sumAllSalaries();
		printDaySummary();
	}

	private void sumAllSalaries() {
		sumAllSalaries(Dispatchers);
		sumAllSalaries(EventHandlers);
		sumAllSalaries(StationWorkers);
	}

	private <T extends Costable> void sumAllSalaries(ArrayList<T> a) {
		for(int i=0;i<a.size();i++)
			salaries+=a.get(i).getSalary();
	}

	//updates the manager about an event that over
	public synchronized void updateManager(ReadyEvent newEvent) {
		if(newEvent.getState()==5)
			numOfSevere++;		
		curEvents++;
		if(curEvents==numOfExpectedCalls) {
			dayIsOver=true;
		}
		this.notify();		//wake up the manager to check if the day is over
	}

	//inform everyone that the day is over
	private void informEveryOne() {
		EventHandlers.get(0).setWorkIsDone();
		StationWorkers.get(0).setWorkIsDone();
		EventCommanders.get(0).setWorkIsDone();
	}

	//print all the data about the work day 
	private void printDaySummary() {
		System.out.println("total Salaries: " + salaries);
		System.out.println("number of handled events : " + curEvents);
		System.out.println("number of severe events : " + numOfSevere);
	}

	public void initiateStatics() {
		dayIsOver=false;
	}
}
