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
 * Critter2's main differences from Critter and the default Critter class is that it only fights with Algae,
 * and after it eats an Algae, it rests for three time steps, during which it cannot move or reproduce.
 */
public class Critter2 extends Critter {
	
	@Override
	public String toString() { return "L"; }
	
	private int dir;
	private static int sleepcount;
	private int sleepytime;
	
	public Critter2() {
		dir = 0;
	}
	
	/**
	 * Checks to see if the Critter2 is asleep, if not, it checks to see if it is about sharing a space with an Algae.
	 * If it is, it fights, if it isn't, it doesn't.
	 * @param not_used is a string containing the type of Critter that Critter2 needs to decide whether to fight
	 */
	public boolean fight(String not_used) { 
		if(not_used.equals("A") && sleepytime == 0){
			sleepytime = 3;
			sleepcount += 3;
			return true; 
		}
		return false;
	}

	@Override
	public void doTimeStep() {
		/* take one step forward if critter is not asleep, reproduce if there is enough energy*/
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
	
	/**
	 * Prints out the current number of lazies, and the total amount of time that all lazies (dead and alive),
	 * have slept during this instance of the world. 
	 * @param lazies is a list of all lazies currently alive in the world
	 */
	public static void runStats(java.util.List<Critter> lazies) {
		System.out.println("There are " + lazies.size() + "Critter2s that have slept for a total of " + sleepcount + "time steps.");
	}
}