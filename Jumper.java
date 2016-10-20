package assignment4;

public class Jumper extends Critter {
	
	@Override
	public String toString() { return "J"; }
	
	private int dir;
	private static int teleportcount;
	
	public Jumper() {
		dir = 0;
	}
	
	public boolean fight(String not_used) { return true; }

	@Override
	public void doTimeStep() {
		/* teleport */
		teleport();
	}

	public int getTeleports(){
		return teleportcount;
	}
	public void teleport(){
		this.setX(Critter.getRandomInt(Params.world_width));
		this.setY(Critter.getRandomInt(Params.world_height));
		this.setNRG(this.getEnergy()-Params.run_energy_cost);
		teleportcount++;
	}
	
	public static void runStats(java.util.List<Critter> jumpers) {
		System.out.println("There are " + jumpers.size() + "Jumpers that have teleported a total of " + teleportcount + "times.");
	}
}
