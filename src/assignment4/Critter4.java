/* CRITTERS Critter4.java
 * EE422C Project 4 submission by
 * Casey Cotter
 * cbc2298
 * 16445
 * Max Fennis
 * maf3743
 * 16450
 * Slip days used: <0>
 * Fall 2016
 */

package assignment4;

/*
 * Trapper Critter does not move and has a chance of killing Critter that lands on it. Trappers  
 * always fight and only can be escaped by Critters if a Critter rolls a higher number. 
 * The number of Trappers alive and total number of Critters that engage a trap are shown in runStats.
 * If another Trapper is placed onto its position, the engage is not incremented.
 */
public class Critter4 extends Critter {
	
	public static int energy = 100;
	public static int engaged = 0;
	
	public String toString() { return "4"; }
	
	/**
	 * fight: increments the count of Critters engaged if the Critter is not a trap 
	 * @param not_used: string of Critter that CopyCat is "fighting"
	 * return: true
	 */
	public boolean fight(String not_used) { 
		
		if(!(not_used.equals("4")))
		{
			engaged++;
		}
		
		return true;
	}
	
	/**
	 * engageTraps: if below 50, increment back to 100 so Trap never dies
	 * @param c: integer, energy value of trap
	 * return: c
	 */
	public int engageTraps(int c)
	{
		if(c <= 50)
		{
			c = c + 50;
		}
		return c;
	}
	
	/**
	 * doTimeStep: sets energy to 100
	 * return: none
	 */
	public void doTimeStep() {
		energy = 100;
	}

	/**
	 * runStats: Prints the total number of Trappers active and the total number
	 * of Critters that have encountered Trappers
	 * @param trap: List of all Trapper critters
	 * return: none
	 */
	public static void runStats(java.util.List<Critter> trap) {
		System.out.print("" + trap.size() + " total Trappers active");
		System.out.print("" + engaged + " total Critters that have encountered Trappers");
		System.out.println();
	}
}