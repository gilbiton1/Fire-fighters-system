import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileReading {
	private Queue <Call> callQueue;
	private ArrayList <Call> tempCalls;
	private File file;
	private int count=0; //?

	public FileReading(Queue<Call> callQueue, File file ) {
		this.callQueue = callQueue;
		tempCalls = new ArrayList<Call>();
		this.file=file;
	}


	public ArrayList<Call> read() {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String st= br.readLine(); 
			st= br.readLine(); 		//skip first line

			while((st != null)) {		
				AddCallToList(st);
				System.out.println(st);
				st= br.readLine();
			}

		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return tempCalls;
	}

	private void AddCallToList(String st) {
		String [] temp = st.split("\t");
		int State= Integer.parseInt(temp[0]);
		int Area= Integer.parseInt(temp[1]);
		double Time = Double.parseDouble(temp[2]);
		int Arrival =  Integer.parseInt(temp[3]);
		String Address=new String();
		Address+=temp[4];
		Call newCall = new Call(State, Area, Time, Arrival, Address, callQueue);
		tempCalls.add(newCall);

	}



}


