
public class FireTruck extends FireVehicle{

	//constructors
	public FireTruck(int ID, int waterAmount, double eventCost, double yearsExperience, int numFighters) {
		super(ID,waterAmount,eventCost,yearsExperience);
		this.numFighters=numFighters;
	}

	public String toString() {
	return "Truck No. " + ID;
	}




}
