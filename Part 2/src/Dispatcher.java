public class Dispatcher extends Thread implements Costable{

	private String name;
	private double salary;
	private Queue<Call> callQueue;
	private Queue <FireEvent> eventQueue;
	private int numOfExpectedCalls;
	private static int numOfCurCalls;
	private static boolean workIsDone;

	//constructor
	public Dispatcher(String name, int numOfCalls,Queue<Call> callQueue, Queue <FireEvent> eventQueue) {
		this.callQueue= callQueue;
		this.eventQueue = eventQueue;
		this.name=name;
		numOfExpectedCalls=numOfCalls;
		salary=0;
	}

	public double getSalary() {
		return salary;
	}

	public void run() {
		while(!workIsDone) {	//there is more calls
			if(numOfCurCalls==numOfExpectedCalls) 
				callQueue.insert(null);			//indicate that the expected calls treated
			Call newCall = callQueue.extract();					//try to extract call
			if(newCall==null)
				break;							//indicate expected calls finished
			long handleTime = holdTillCallOver(newCall);
			addToSalary(handleTime);
			FireEvent event = createFireEvent(newCall); 
			eventQueue.insert(event);
			informCall(newCall);
		}
		informDispatchers(); //day is over
	}

	//returns new FireEvent
	private synchronized FireEvent createFireEvent(Call newCall) {
		numOfCurCalls++;								
		FireEvent event = new FireEvent (numOfCurCalls,newCall.getCallState(),newCall.getArea(),newCall.getArrival(),newCall.getAddress(),eventQueue);
		return event;

	}

	//wait call time and returns it
	private long holdTillCallOver(Call newCall) {
		double rnd = Math.random()*1001 + 1000;
		long handleTime =((long)(rnd + newCall.getTime()));
		try {
			Thread.sleep(handleTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return handleTime;
	}


	private void addToSalary(long handleTime) {
		salary = salary + (handleTime*3/1000) + 0.5;
	}

	//inform call that the treatment over
	private void informCall( Call newCall) {
		newCall.isFinished();	
	}

	//inform the other dispatchers that the work is done
	private void informDispatchers() {
		workIsDone= true;
		callQueue.stopWait();
	}

	public void initiateStatics() {
		numOfCurCalls=0;
		workIsDone=false;
	}
}
