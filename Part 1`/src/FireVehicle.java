import java.util.ArrayList;

public abstract class FireVehicle implements Comparable<FireVehicle>, Experiencable,Costable{
//help variables
	protected ArrayList <FireCommander> commanderList;
	protected ArrayList <FireFighter> fighterList;
	protected int ID,waterAmount,numFighters;
	protected double eventCost,yearsExperience;

	//constructors
	public FireVehicle(int ID, int waterAmount, double eventCost, double yearsExperience) {
		this.ID=ID;
		this.waterAmount=waterAmount;
		this.eventCost=eventCost;
		this.yearsExperience=yearsExperience;
		commanderList= new ArrayList <FireCommander>();
		fighterList= new ArrayList <FireFighter>();
	}

	//update Fighters/Commanders to this vehicle fighters
	public void addFighter(FireFighter f) {
		this.fighterList.add(f);
	}
	
	public void addCommander(FireCommander f) {
		this.commanderList.add(f);
	}
	
	public void addAllFighter(ArrayList<FireFighter> fighterList) {
		this.fighterList.addAll(fighterList);
	}

	public void setFighters(ArrayList <FireFighter> fighterList) {
		for(int i=0;i<fighterList.size();i++) {
			this.fighterList.add(fighterList.get(i));
		}
	}

	//getters:
	
	public ArrayList<FireFighter> getFightersTeam(){
		return fighterList;
	}

	public double getExpenses() {
		return eventCost;
	}
	
	public int getID() {
		return ID;
	}

	public double getWaterAmount() {
		return this.waterAmount;
	}

	public int getNumFighters() {
		return this.numFighters;
	}

	public double getYearsExperience() {
		return yearsExperience;
	}

	public FireCommander getCommander() {
		return commanderList.get(0);	
	}

	public ArrayList<FireFighter> getAllFighters() {
		ArrayList<FireFighter> temp = new ArrayList<>();//??
		temp.addAll(fighterList);
		return temp;
	} 

	//general functions:

	public void clear() {
		fighterList.clear();
		commanderList.clear();
	}
	
	//compare one vehicle to other according to water amount
	public int compareTo(FireVehicle other) {
		if(this.waterAmount==other.waterAmount)
			return 0;
		else if(this.waterAmount>other.waterAmount)
			return 1;
		return -1;
	}
	
	public abstract String toString();
}
