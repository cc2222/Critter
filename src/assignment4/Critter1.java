package assignment4;

/*
 * Example critter
 */
public class Critter1 extends Critter {
	
	@Override
	public String toString() { return "1"; }
	
	private int dir;
	private static int teleportcount;
	
	public Critter1() {
		dir = 0;
	}
	
	public boolean fight(String not_used) { return true; }

	@Override
	public void doTimeStep() {
		/* take one step forward */
		teleport();
	}

	public void teleport(){
		dir = Critter.getRandomInt(8);
		int numstep = Critter.getRandomInt(10);
		for(int a = 0; a < numstep; a++){
			walk(dir);
		}
		teleportcount++;
	}
	
	public static void runStats(java.util.List<Critter> jumpers) {
		System.out.println("Jumpers have teleported a total of " + teleportcount + " times and " + jumpers.size() + " Jumpers remain");
	}
}