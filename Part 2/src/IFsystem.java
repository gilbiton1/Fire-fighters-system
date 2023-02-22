import java.util.SortedSet;
import java.util.TreeSet;

public  class IFsystem {
	private SortedSet <FireEvent> closeEvents;
	private SortedSet <FireEvent> normalEvents;
	private SortedSet <FireEvent> farEvents;
	public boolean stopWait;


	//constructor
	public IFsystem() {
		closeEvents= new TreeSet<FireEvent>();
		normalEvents= new TreeSet<FireEvent>();
		farEvents= new TreeSet<FireEvent>();
	}

	//add events to the system to the right category
	public synchronized void addEvent (FireEvent newEvent) {
		if(newEvent==null) 	//indicates that the day is over
			stopWait=true;
		else if(newEvent.getDistance()<=10)
			closeEvents.add(newEvent);
		else if(newEvent.getDistance()>10 && newEvent.getDistance()<=20)
			normalEvents.add(newEvent);
		else
			farEvents.add(newEvent);

		this.notifyAll();		//wake up the workers that waits for new events
	}

	//give the workers events
	public synchronized FireEvent takeEvent() {
		while(true) {
			if(stopWait)
				return null;
			if(!closeEvents.isEmpty()) {
				FireEvent event = closeEvents.first(); 
				closeEvents.remove(event);
				return event;
			}
			else if(!normalEvents.isEmpty()) {
				FireEvent event = normalEvents.first();
				normalEvents.remove(event);
				return event;
			}
			else if(!farEvents.isEmpty()) {
				FireEvent event = farEvents.first();
				farEvents.remove(event);
				return event;
			} else {
				try {
					this.wait();		//wait until there will be more events
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//the day is over
	public synchronized void stopWait() {
		stopWait=true;
		this.notifyAll();
	}

}
