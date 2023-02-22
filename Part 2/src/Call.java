
public class Call extends Thread{

	private String Address;
	private int State;
	private int Area;
	private long Arrival; 	// time that the call arrived
	private double Time; 	//time needed to handle the call
	private  Queue <Call> callQueue;
	private boolean finished;

	//constructor
	public Call(int State, int Area , double Time , int Arrival , String Address, Queue <Call> callQueue) {
		this.State = State;
		this.Area = Area;
		this.Time = Time;
		this.Arrival = Arrival;
		this.Address = Address;
		this.callQueue = callQueue;

	}

	public String getAddress() {
		return Address;
	}

	public double getTime() {
		return Time;
	}

	public int getCallState() {
		return State;
	}

	public int getArea() {
		return Area;
	}

	public long getArrival() {
		return Arrival;
	}

	public void run() {
		try {
			Call.sleep(Arrival*1000);		//hold before entering queue
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		callQueue.insert(this);			//enter callQueue
		synchronized(this) {
			while(!finished) {
				try {
					this.wait();				//wait until dispatcher done
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public synchronized void isFinished() {	//dispatcher done 
		finished = true;
		this.notifyAll();
	}

}
