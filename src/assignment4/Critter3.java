/* CRITTERS Critter3.java
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
 * CopyCat Critter does not move or fight. Any time another Critter lands on his position, 
 * he creates a new instance of the Critter type CopyCat is encountering.
 * The number of Critters made from CopyCats are shown in runStats.
 */
public class Critter3 extends Critter {
	
	public static int doubled = 0;
	public boolean cat = false;
	
	public String toString() { return "3"; }
	
	/**
	 * fight: makes a new instance of the Critter CopyCat is interacting with 
	 * @param otherCrit: string of Critter that CopyCat is "fighting"
	 * return: false
	 */
	public boolean fight(String otherCrit) { 
		
		doubled++;
		
		try{
			Critter.makeCritter(otherCrit);
		}
		catch(Exception e){ }
		
		return false;
	}
	
	public void doTimeStep() {
		
	}
	/**
	 * runStats: Prints total number of Critters that have spawned from Copy Cat critters 
	 * @param cat: List of all CopyCat critters
	 * return: none
	 */
	public static void runStats(java.util.List<Critter> cat) {

		System.out.print("" + doubled + " total Critters have spawned from " + cat.size() + " CopyCat");
		System.out.println();
	}
}
