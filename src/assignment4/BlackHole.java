package assignment4;

/*
 * Example critter
 */
public class BlackHole extends Critter {
	
	public static int energy = 1000;
	public static int killCount = 0;
	public static int dead = 0;
	
	public String toString() { return "B"; }
	
	public boolean fight(String not_used) { 
		
		if(not_used.equals("B"))
		{
			dead++;
		}
		else
		{
			killCount++;
		}
		
		return true;
	}
	
	public void doTimeStep() {
		energy = 1000;
	}

	public static void runStats(java.util.List<Critter> black) {

		System.out.print("" + black.size() + " total BlackHoles    ");
		System.out.print("" + killCount + " total Critters consumed    ");
		System.out.print("" + dead/2 + " total BlackHoles collapsed");
		System.out.println();
	}
}
