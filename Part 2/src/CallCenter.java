import java.io.File;
import java.util.ArrayList;

public class CallCenter {
	private Queue<Call> callQueue;
	private Queue <FireEvent> eventQueue;
	private BoundedQueue<ReadyEvent> ReadyEvents;
	private ArrayList <Dispatcher> Dispatchers;
	private ArrayList <EventHandler> EventHandlers;
	private	ArrayList <StationWorker> StationWorkers;
	private ArrayList <EventCommander> EventCommanders;
	private ArrayList<FireVehicle> trucks;
	private ArrayList<FireVehicle> planes;
	private ArrayList<Call> tempCalls;
	private FireStationManager chief;
	private int numOfExpectedCalls;
	private IFsystem IF;



	public CallCenter(File file,int commandNum,double workTimeValue,int trucksValue,int planesValue) {
		EventCommanders = new ArrayList <EventCommander>();
		StationWorkers=new ArrayList <StationWorker> ();
		Dispatchers=new ArrayList <Dispatcher>();
		EventHandlers=new ArrayList <EventHandler>();
		callQueue = new Queue<Call>();
		FileReading a= new FileReading(callQueue, file);
		tempCalls = a.read();
		numOfExpectedCalls =tempCalls.size();
		chief =  new FireStationManager(numOfExpectedCalls, Dispatchers,EventHandlers,StationWorkers, EventCommanders);
		eventQueue = new Queue <FireEvent>();
		ReadyEvents = new BoundedQueue<ReadyEvent>();
		planes=new ArrayList<FireVehicle>();
		trucks=new ArrayList<FireVehicle>();
		IF=new IFsystem();
		createVehicles(trucksValue,planesValue);
		createEventHandlers();
		createDispatchers();		
		createStationWorkers(workTimeValue);
		createCommanders(commandNum);
		initiateStatics(); 
		start();

	}

	private void initiateStatics() {
		Dispatchers.get(0).initiateStatics();
		EventCommanders.get(0).initiateStatics();
		StationWorkers.get(0).initiateStatics();
		EventHandlers.get(0).initiateStatics();
		chief.initiateStatics();

	}

	private void createCommanders(int commandNum) {
		for(int i=0; i<(commandNum+5);i++) 
			EventCommanders.add(new EventCommander(ReadyEvents,EventCommanders,chief,trucks,planes));
	}

	private void createStationWorkers(double workTimeValue) {
		for(int i=0; i<3;i++) 
			StationWorkers.add(new StationWorker("worki",IF ,trucks,ReadyEvents,workTimeValue));
	}

	private void createDispatchers() {
		for(int i=0; i<5;i++) 
			Dispatchers.add(new Dispatcher("disp",numOfExpectedCalls,callQueue,eventQueue));
	}

	private void createEventHandlers() {
		for(int i=0; i<3; i++) 
			EventHandlers.add(new EventHandler ("handi",eventQueue,IF));
	}

	private void createVehicles(int trucksValue,int planesValue) { 
		for(int i=0; i<(trucksValue+50); i++)
			trucks.add(new FireVehicle());
		for(int i=0; i<(planesValue+10); i++)
			planes.add(new FireVehicle());		
	}

	public void start() {
		chief.start();
		for(int i=0; i<tempCalls.size(); i++) {
			tempCalls.get(i).start();
		}
		for(int i=0; i<EventCommanders.size(); i++) {
			EventCommanders.get(i).start();
		}
		for(int i=0; i<StationWorkers.size(); i++) {
			StationWorkers.get(i).start();
		}	
		for(int i=0; i<Dispatchers.size(); i++) {
			Dispatchers.get(i).start();
		}
		for(int i=0; i<EventHandlers.size(); i++) {
			EventHandlers.get(i).start();
		}
	}



}
