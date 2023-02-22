
public class EventHandler extends Thread implements Costable{
	private String name;
	private double salary;
	private Queue <FireEvent> eventQueue;
	private static boolean workIsDone;
	private static int IFindex;
	private IFsystem IF;

	//constructor
	public EventHandler (String name,Queue <FireEvent> eventQueue, IFsystem IF) {
		this.name=name;
		this.eventQueue=eventQueue;
		this.IF = IF;
		salary=0;
	}

	public void setWorkIsDone() {
		workIsDone=true;
		eventQueue.stopWait();
		IF.stopWait();
	}

	public double getSalary() {
		return salary;
	}

	public void run() {
		while(!workIsDone) {
			FireEvent newEvent = takeEventFromQueue();
			if(newEvent==null)			//indicates that the events over
				break;
			calculateDistance(newEvent);
			addToSalary();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			insertEventToIF(newEvent);
			printEmergency(newEvent);
		}
		IF.addEvent(null);				//indicate that work is done
	}

	//try to extract event
	private FireEvent takeEventFromQueue() {
		FireEvent newEvent = eventQueue.extract();	
		return newEvent;
	}

	//calculate the distance by first char and word number
	private void calculateDistance(FireEvent newEvent) {
		if(newEvent==null)
			return;
		String address= new String(newEvent.getAddress());
		char temp1=address.charAt(0);
		char temp2=address.charAt(1);
		int num1= Character.getNumericValue(temp1);
		int num2=Character.getNumericValue(temp2);
		address= addDistanceByFirstChar(address,newEvent,num1,num2);
		addDistanceByWordNum(address,newEvent);
	}

	//update the distance by first char
	private String addDistanceByFirstChar(String address, FireEvent newEvent, int num1, int num2) {
		int rnd;
		if (num1>=0 && num1<=9) {
			if(num2>=0 &&num2<=9) {
				newEvent.setDistance(Integer.parseInt(address.substring(0,2)));
				address= address.substring(3);	//without space
			}
			else {
				newEvent.setDistance(num1);
				address= address.substring(2);
			}
		}
		else {
			rnd= (int)(Math.random()*12) + 8;
			newEvent.setDistance(rnd);
		}	
		return address;
	}

	//update the distance by word number and add random number
	private void addDistanceByWordNum(String address,FireEvent newEvent) {
		int rnd;
		String [] arr = address.split(" "); 	//split the string
		int size = arr.length;
		if(size==1)
			rnd=(int)(Math.random()*4) + 1;
		else if(size==2)
			rnd=(int)(Math.random()*4) + 3;
		else
			rnd=(int)(Math.random()*5) + 5;
		newEvent.setDistance(rnd);
	}

	//add to salary 1 for each second of the call
	private void addToSalary() {
		salary+=3;		
	}

	//add the event to the system with index that shows the time entered the system
	private synchronized void insertEventToIF(FireEvent newEvent) {
		IF.addEvent(newEvent);
		if(newEvent!=null) {
			newEvent.setIFindex(IFindex);
			IFindex++;
		}
	}

	private synchronized void printEmergency(FireEvent newEvent) {
		System.out.println("Notice - New Emergency " + newEvent.getEventID());
	}

	public void initiateStatics() {
		workIsDone=false;
	}
}
