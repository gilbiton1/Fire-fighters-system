

public class FireCommander extends FireFighter {

	private int commandLevel;

	//constructors
	public FireCommander(String name, int ID, int age, double yearsExperience, int commandLevel) {
		super(name,ID,age,yearsExperience);
		if(yearsExperience<3)
			throw new IllegalCommanderYearsExperienceException("invalid Experience for commander !");

		this.commandLevel=commandLevel;
		this.subscriptionGrant=2000;
	}

	//compare one commander to other acording to the command level
	public int compareTo(FireFighter other) {
		if(other instanceof FireCommander) {
			if(this.commandLevel==((FireCommander)other).commandLevel)
				return 0;
			else if(this.commandLevel>((FireCommander)other).commandLevel)
				return 1;
			return -1;
		}
		return 1;//if the user trying compare between FireCommander and FireFighter>>FireCommander is bigger
	}
}
