package assignment4;

/*
 * LazyCritter's main differences from Craig and the default Critter class is that it only fights with Algae,
 * and after it eats an Algae, it rests for three time steps, during which it cannot move or reproduce.
 */
public class LazyCritter extends Critter {
	
	@Override
	public String toString() { return "L"; }
	
	private int dir;
	private static int sleepcount;
	private int sleepytime;
	
	public LazyCritter() {
		dir = 0;
	}
	
	/**
	 * Checks to see if the LazyCritter is asleep, if not, it checks to see if it is about sharing a space with an Algae.
	 * If it is, it fights, if it isn't, it doesn't.
	 * @param not_used is a string containing the type of Critter that LazyCritter needs to decide whether to fight
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
				LazyCritter child = new LazyCritter();
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
		System.out.println("There are " + lazies.size() + "LazyCritters that have slept for a total of " + sleepcount + "time steps.");
	}
}
