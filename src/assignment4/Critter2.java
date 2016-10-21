package assignment4;


public class Critter2 extends Critter {
	
	@Override
	public String toString() { return "2"; }
	
	private int dir;
	private static int sleepcount;
	private static int sleepytime;
	
	public Critter2() {
		dir = 0;
	}
	
	public boolean fight(String not_used) { 
		if(not_used.equals("A")){
			sleepytime = 3;
			sleepcount += 3;
			return true; 
		}
		return false;
	}

	@Override
	public void doTimeStep() {
		/* take one step forward */
		if(sleepytime == 0){
			walk(Critter.getRandomInt(8));
			if(this.getEnergy() > Params.min_reproduce_energy*2){
				Critter2 child = new Critter2();
				reproduce(child, Critter.getRandomInt(8));
			}
		}
		else{
			sleepytime--;
		}
	}
	
	public static void runStats(java.util.List<Critter> lazies) {
		System.out.println("" + lazies.size() + " Lazys have slept for a total of " + sleepcount + " time steps.");
	}
}