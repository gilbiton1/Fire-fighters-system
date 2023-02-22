import java.util.ArrayList;
import java.util.List;

public class Queue<T> {

	protected List<T> queue;
	public boolean stopWait;

	public Queue() {
		queue= new ArrayList<T>();
	}

	public synchronized void insert ( T t) {
		queue.add(t);
		this.notifyAll();
	}

	public synchronized void stopWait() {
		stopWait = true;
		this.notifyAll();
	}

	public synchronized T extract() {
		while(queue.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(stopWait) {
				this.notifyAll();
				return null;
			}
		}
		T t = queue.remove(0);
		return t;
	}


}

