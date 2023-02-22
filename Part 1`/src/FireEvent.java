import java.util.ArrayList;

public class FireEvent implements Comparable<FireEvent>{
	//help variables
	private int level,numOfFighters,numOfCommanders ;
	private String location;
	private ArrayList <FireVehicle> vehiclesTeam;	
	private ArrayList <FireFighter> fightersTeam;
	private ArrayList <FireCommander> commandersTeam;
	private double totalExpense;
	private boolean isOpen;

	//constructor
	public FireEvent(int level, String location) {
			this.level=level;
			this.location=location;
			totalExpense=0;
			vehiclesTeam= new ArrayList<FireVehicle>();
			fightersTeam= new ArrayList<FireFighter>();
			commandersTeam= new ArrayList<FireCommander>();
			isOpen=true;
		
	}
	//update the event vehicleList
	public void addVehiclesTeam(ArrayList <FireVehicle> vehiclesTeam) {
		this.vehiclesTeam.addAll(vehiclesTeam);
		//update the num of fighters and commanders demands for handling the ivent
		for(int i=0;i<vehiclesTeam.size();i++) {
			numOfFighters = numOfFighters + vehiclesTeam.get(i).getNumFighters();
			numOfCommanders +=1; //one commander for each vehicle
		}
		System.out.println(vehiclesTeam);
	}
//update Fighters/Commanders/vehicles list
	public void addFightersTeam(ArrayList <FireFighter> fightersTeam) {
		this.fightersTeam.addAll(fightersTeam);
	}

	public void addCommandersTeam(ArrayList<FireCommander> commandersTeam) {
		this.commandersTeam.addAll(commandersTeam);
	}

	public void addVehicle(FireVehicle v) {
		this.vehiclesTeam.add(v);
		numOfFighters = numOfFighters + v.getNumFighters();
		numOfCommanders +=1;
	}

	public void addCommander(FireCommander C) {
		this.commandersTeam.add(C);
	}

	public void addFighter(FireFighter C) {
		this.fightersTeam.add(C);
	}
	//order fighters and commanders in the vehicles
	public void setFightersOnVehicle() {
		for(int i=0;i<vehiclesTeam.size();i++) {
			int num=vehiclesTeam.get(i).getNumFighters();
			vehiclesTeam.get(i).addCommander(commandersTeam.get(i));
			for(int j=0;j<num;j++) {
				vehiclesTeam.get(i).addFighter(fightersTeam.get(j));
			}
		}
	}

	//getters:

	public String getLocation(){
		return location;
	}

	public int getLevel(){
		return level;
	}

	public int getNumVehicles() {
		return vehiclesTeam.size();
	}

	public int getNumOfFighters() {
		return numOfFighters;
	}

	public ArrayList<FireVehicle> getVehiclesTeam() {
		return vehiclesTeam;		
	}

	public ArrayList<FireCommander> getCommandersTeam() {
		return this.commandersTeam;
	}

	public ArrayList<FireFighter> getFightersTeam() {
		return this.fightersTeam;
	}

	//calculate and return total expense of an event
	public double getTotalexpense() {
		totalExpense= numOfFighters*200+ (numOfCommanders*300);

		for(int i=0;i<vehiclesTeam.size();i++) 
			totalExpense=totalExpense+vehiclesTeam.get(i).getExpenses();
		return totalExpense;		
	}

	//general functions:

	//compare one event to other up to level
	public int compareTo(FireEvent other) {
		if(this.level==other.level)
			return 0;
		else if(this.level>other.level)
			return 1;
		return -1;
	}
	//update the event status
	public void endEvent(){
		isOpen=false;
	}
	// update the event status
	public boolean isOpen() {
		return this.isOpen;
	}
}
