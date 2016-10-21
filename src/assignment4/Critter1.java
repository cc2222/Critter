/* CRITTERS Critter.java
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
 * Jumpers main difference from Critter is that it teleports in a random direction for a random number of steps (up to ten).
 * It also doesn't reproduce.
 */

public class Critter1 extends Critter {
	
	@Override
	public String toString() { return "J"; }
	
	private int dir;
	private static int teleportcount;
	
	public Critter1() {
		dir = 0;
	}
	
	//Jumpers always fight
	public boolean fight(String not_used) { return true; }

	@Override
	public void doTimeStep() {
		/* teleport */
		teleport();
	}

	//returns the number of teleports for all Jumpers(dead and alive)
	public int getTeleports(){
		return teleportcount;
	}
	
	/*
	 * Since we weren't allowed to create public setX and setY methods to truly teleport, I just picked a random direction,
	 * and moved a random number of steps (up to 10) in that direction. 
	 * The amount of energy increases as the number of spaces teleported increases, so these Critters will die frequently.
	 */
	public void teleport(){
		dir = Critter.getRandomInt(8);
		int numstep = Critter.getRandomInt(10);
		for(int a = 0; a < numstep; a++){
			walk(dir);
		}
		teleportcount++;
	}
	
	/**
	 * This prints out the number of jumpers currently alive 
	 * and the total number of teleports by all the jumpers(dead and alive) for this instance of the world
	 * @param jumpers is a list of all the jumpers that are alive in the current world
	 */
	public static void runStats(java.util.List<Critter> jumpers) {
		System.out.println("There are " + jumpers.size() + "Jumpers that have teleported a total of " + teleportcount + "times.");
	}
}