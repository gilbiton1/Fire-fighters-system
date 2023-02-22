import java.util.ArrayList;
public class FatmaUnit {
	//help variables-lists of available fighters/vehicles
	private ArrayList <FireFighter> commanderList;
	private ArrayList <FireFighter> fighterList;
	private ArrayList <FireVehicle> planeList;
	private ArrayList <FireVehicle> truckList;
	private ArrayList <FireEvent> eventList;

	//constructor
	public FatmaUnit() {
		commanderList= new ArrayList <FireFighter>();
		fighterList= new ArrayList <FireFighter>();
		planeList= new ArrayList <FireVehicle>();
		truckList= new ArrayList <FireVehicle>();
		eventList= new ArrayList <FireEvent>();
	}
	//add fighter to available fighters list
	public boolean addFighter(FireFighter f) {
		if(fighterList.contains(f))
			return false;

		return fighterList.add(f);
	}
	//add commander to available commander list
	public boolean addCommander(FireCommander c) {
		if(commanderList.contains(c))
			return false;
		return commanderList.add(c);
	}
	//add truck to available truck list
	public boolean addTruck(FireTruck t) {
		if(truckList.contains(t))
			return false;
		return truckList.add(t);
	}
	//add plane to available plane list
	public boolean addPlane (FirePlane p) {
		if(planeList.contains(p))
			return false;
		return planeList.add(p);
	}

	//getters:

	public ArrayList <FireFighter> getCommanderList(){
		return commanderList;
	}

	public ArrayList <FireFighter> getFighterList(){
		return commanderList;
	}

	public ArrayList <FireVehicle> getPlaneList(){
		return planeList;
	}

	public ArrayList <FireVehicle> getTruckList(){
		return truckList;
	}

	public ArrayList <FireEvent> getEventList(){
		return eventList;
	}

	//general functions:

	//handling a new demands fire event- order vehicles and fire fighters,returns if it possible to be handled
	public boolean eventHandling(int level,String location) {

		//help variables
		ArrayList <FireVehicle> vehiclesTeam=new ArrayList <FireVehicle>();
		int minWater=level*10000;
		double counter=0;
		truckList=sort(truckList);//**
		planeList=sort(planeList);//**
		fighterList=sort(fighterList);//**
		commanderList=sort(commanderList);//**

		System.out.println("fighterList " + fighterList);
		System.out.println("commanderList " + commanderList);
		System.out.println("planeList " + planeList);
		System.out.println("truckList " + truckList);

		if(level<=5) {
			for (int i=0;i<truckList.size()&&counter<minWater;i++) {
				counter=counter+truckList.get(i).getWaterAmount();
				vehiclesTeam.add(truckList.get(i));
			}
			if (counter<minWater) {
				System.out.println("not enough water amount");
				return false;
			}

		} else {
			if(planeList.size()<2){//there is no enough planes for handling this event
				System.out.println("not enough planes");
				return false;
			}
			//sum the available truck/plane list waterAmount
			for (int i=0;i<2;i++) {
				counter=counter+planeList.get(i).getWaterAmount();
				vehiclesTeam.add(planeList.get(i));
			}	
			for (int i=0;i<truckList.size()&&counter<minWater;i++) {
				counter=counter+truckList.get(i).getWaterAmount();
				vehiclesTeam.add(truckList.get(i));
			}
			if (counter<minWater){//there is no enough water for handling this event
				System.out.println("not enough water amount");
				return false;
			}
		}

		boolean isPosible=EnoughFighters(vehiclesTeam);
		if(isPosible)
			makeEvent(vehiclesTeam, level, location);
		else
			System.out.println("not enough fighters");

		return isPosible;
	}

	//checks if there is enough available fighters&commanders for vehicles demand
	public boolean EnoughFighters(ArrayList <FireVehicle> vehiclesTeam) {
		int numOfCommanders=0;
		int numOfFighters=0;
		for (int i=0;i<vehiclesTeam.size();i++) {
			numOfCommanders++;
			if(vehiclesTeam.get(i) instanceof FirePlane) {
				numOfFighters++;
			}
			else 
				numOfFighters=numOfFighters+((FireTruck)vehiclesTeam.get(i)).getNumFighters();
		}
		System.out.println("numOfFighters: " + numOfFighters);
		System.out.println("numOfCommanders: " + numOfCommanders);
		System.out.println("fighterList.size(): " + fighterList.size());
		System.out.println("commanderList.size(): " + commanderList.size());
		System.out.println(commanderList);

		if(commanderList.size()<numOfCommanders||fighterList.size()<numOfFighters)
			return false;

		return true;
	}
	//create new event after handle check
	private void makeEvent(ArrayList<FireVehicle> vehiclesTeam, int level,String location) {

		FireEvent newEvent= new FireEvent(level,location);
		newEvent.addVehiclesTeam(vehiclesTeam);
		occupied(vehiclesTeam);

		newEvent.addCommandersTeam(commandersTeam(newEvent.getNumVehicles()));
		newEvent.addFightersTeam(fightersTeam(newEvent.getNumOfFighters()));
		newEvent.setFightersOnVehicle();

		eventList.add(newEvent);//add the new event to this unit event list

	}
	//take commanders team from available list and insert to temp list 
	private ArrayList<FireCommander> commandersTeam(int numOfVehicles) {
		ArrayList<FireCommander> temp=new ArrayList<FireCommander>();
		//		System.out.println("num commanders" + numOfVehicles);
		for(int i=0;i<numOfVehicles;i++)
			temp.add((FireCommander) commanderList.remove(0));

		System.out.println("commandersTeam " + temp);

		return temp;
	}
	//take fighters team from available list and insert to temp list
	private ArrayList<FireFighter> fightersTeam(int numOfFighters) {
		ArrayList<FireFighter> temp=new ArrayList<>();
		//		System.out.println("numOfFighters "+ numOfFighters);
		for(int i=0;i<numOfFighters;i++)
			temp.add(fighterList.remove(0));

		System.out.println("fightersTeam " + temp);
		return temp;
	}

	//remove the required vehicles from available list
	private void occupied(ArrayList<FireVehicle> vehiclesTeam) {
		for(int i=0;i<vehiclesTeam.size();i++) {
			if(vehiclesTeam.get(i) instanceof FirePlane)
				planeList.remove(vehiclesTeam.get(i));
			if(vehiclesTeam.get(i) instanceof FireTruck)
				truckList.remove(vehiclesTeam.get(i));		
		}
	}
	//casing function that checks if the current event exist at the eventList
	public boolean closeEvent(String location) {
		for(int i=0;i<eventList.size();i++) {
			if(location.equals(eventList.get(i).getLocation())) {
				if(eventList.get(i).isOpen()){
					closeEvent(eventList.get(i));
					return true;
				}
			}
		}
		return false;
	}
	//after event -return the event fighters&vehicles to available Fire lists
	public void closeEvent(FireEvent eventToClose) {

		returnFighters(eventToClose);
		returnVehicles(eventToClose.getVehiclesTeam());
		eventToClose.endEvent();
		printEndEvent(eventToClose);

	}
	//return the eventFighters to available fighters list
	private void returnFighters(FireEvent eventToClose) {

		commanderList.addAll(eventToClose.getCommandersTeam());
		fighterList.addAll(eventToClose.getFightersTeam());

		for(int i=0;i<eventToClose.getVehiclesTeam().size();i++) {
			eventToClose.getVehiclesTeam().get(i).clear();
		}
	}

	//put the vehicles back in the available lists.
	private void returnVehicles(ArrayList<FireVehicle> vehiclesTeam) {

		for(int i=0;i<vehiclesTeam.size();i++) {
			if(vehiclesTeam.get(i) instanceof FirePlane) 
				planeList.add(vehiclesTeam.get(i));

			if(vehiclesTeam.get(i) instanceof FireTruck) 
				truckList.add(vehiclesTeam.get(i));
		}
	}

	private void printEndEvent(FireEvent eventToClose) {

		System.out.println("Fire event ended!\r\n"
				+ "Location:" + eventToClose.getLocation() +"\r\n"
				+ "Level:" + eventToClose.getLevel() + "\r\n"
				+ "Number of fighters in the event:" +eventToClose. getNumOfFighters()+ "\r\n"
				+ "Fire fighters average years of experience:" + fighterExperience(eventToClose)  + "\r\n"
				+ "Fire vehicles average years of experience:" + vehicleExperience(eventToClose) + "\r\n"
				+ "Event cost:"+eventToClose.getTotalexpense());
	}

	public static double vehicleExperience(FireEvent eventToClose) {
		double expirienseAve=0;
		expirienseAve=expirienseAve+ averageExperience(eventToClose.getVehiclesTeam());
		return expirienseAve;
	}

	public static double fighterExperience(FireEvent eventToClose) {
		double expirienseAve=0;
		expirienseAve=expirienseAve+ averageExperience(eventToClose.getFightersTeam());
		return expirienseAve;
	}
	//function that sorts the list according to list Compare type
	public static <T extends Comparable<T>> ArrayList<T> sort(ArrayList<T>  compare) {
		ArrayList <T> temp= new ArrayList <T>();
		int num= compare.size();
		for (int i=0;i<num;i++) {
			temp.add(getMax(compare));
			compare.remove(getMax(compare));
		}
		return temp;

	}
	//function that return the max value from Comparable list
	public static <T extends Comparable<T>> T getMax(ArrayList<T> typeList) {
		if(typeList.size()==0)
			return null;
		T max=typeList.get(0);
		for(int i =0;i<typeList.size();i++) {
			if(typeList.get(i).compareTo(max)>0)
				max=typeList.get(i);
			else if (typeList.get(i).compareTo(max)==0) {
				if(typeList.get(i) instanceof FireVehicle) {//**
					if(((FireVehicle)typeList.get(i)).getNumFighters()<((FireVehicle)max).getNumFighters())
						max=typeList.get(i);
				}
			}
		}
		return max;
	}

	//calculate the average of this unit fighters ages
	public double averageAge () {
		double sum=0, ave=0, counter=0;

		for(int i=0;i<commanderList.size();i++) 
			sum= sum + commanderList.get(i).getAge();//sums commanders age

		for(int i=0;i<fighterList.size();i++) //sums fighters age
			sum= sum + fighterList.get(i).getAge();

		for(int i=0;i<eventList.size();i++) {
			if (eventList.get(i).isOpen()) {

				for(int j=0; j<eventList.get(i).getFightersTeam().size();j++) {
					sum= sum + eventList.get(i).getFightersTeam().get(j).getAge();
					counter++;
				}

				for(int j=0; j<eventList.get(i).getCommandersTeam().size();j++) {
					sum= sum + eventList.get(i).getCommandersTeam().get(j).getAge();
					counter++;
				}
			}
		}
		ave=sum/(commanderList.size()+fighterList.size()+counter);
		return ave;

	}
	//return sum of ages


	//returns total sum of costable expenses
	public static <T extends Costable> double totalExpenses(ArrayList<T> costable) {
		double sum = 0;
		for(int i=0;i<costable.size();i++) {
			sum = sum + costable.get(i).getExpenses();
		}
		return sum;
	}

	//return the averge Experience of experiencable list
	public static <T extends Experiencable> double averageExperience(ArrayList<T> experiencable) {
		double sum =0;
		for(int i=0; i<experiencable.size();i++) {
			sum+=experiencable.get(i).getYearsExperience();
		}
		return sum/experiencable.size();//calculate the average

	}
}
