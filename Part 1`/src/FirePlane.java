
public class FirePlane extends FireVehicle{

	private double minHeight;
	//constructors
	public FirePlane(int ID, int waterAmount, double eventCost, double yearsExperience, double minHeight) {
		super(ID,waterAmount,eventCost,yearsExperience);
		if(minHeight>50)
			throw new IllegalPlaneMinHeightException("invalid min Height for plane! minHeight must be under 50");

		this.minHeight=minHeight;
		numFighters=1;
	}
	//getter
	public double getMinHeight() {
		return minHeight;
	}
	
	public String toString() {
		return "Plane No. " + ID;
	}
	
	

}
