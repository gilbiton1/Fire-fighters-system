import java.util.ArrayList;

public class BoundedQueue<T> extends Queue<T> {
	private int maxSize;

	public BoundedQueue() {
		super();
		maxSize=15;
	}

	//insert item to queue
	public synchronized void insert (T t) {
		while(queue.size()>= maxSize)
			try {
				this.wait();	//wait if queue is full
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		queue.add(t);
		this.notifyAll();

	}

	//return item from queue
	public synchronized T extract() {
		T t= super.extract();
		this.notifyAll();
		return t;	
	}
}
