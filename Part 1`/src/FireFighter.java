
public class FireFighter implements Comparable<FireFighter> ,Experiencable,Costable{
	String name;
	protected int ID, age, salary, subscriptionGrant, eventGrant;
	protected double yearsExperience;

	//constructor:
	
	public FireFighter(String name, int ID, int age, double yearsExperience) {
		this.subscriptionGrant=1000;
		this.name=name;
		this.age=age;
		this.yearsExperience=yearsExperience;
		this.ID=ID;
		eventGrant=200;
		salary=subscriptionGrant+eventGrant;
	}
	//constructor for volunteer
	public FireFighter(String name, int ID, int age) {
		this.subscriptionGrant=0;
		this.yearsExperience=0;
	}

	//getters:

	public double getAge() {
		return age;
	}
	
	public int getID() {
		return ID;
	}

	public double getYearsExperience() {
		return yearsExperience;
	}

	public double getExpenses() {
		return salary;
	}

	//general functions:

	public String toString() {
		return name;
	}

	//compare one fighters to other according to years experience	
	public int compareTo(FireFighter other) {
		if(this.yearsExperience==other.yearsExperience)
			return 0;
		else if(this.yearsExperience>other.yearsExperience)
			return 1;
		return -1;
	}

}
