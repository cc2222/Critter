package assignment4;

/*
 * Example critter
 */
public class Splitter extends Critter {
	
	@Override
	public String toString() { return "S"; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	public static int numSplits = 0;
	
	public Splitter() {
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
	}
	
	public boolean fight(String not_used) { return true; }

	@Override
	public void doTimeStep() {
		/* take one step forward */
		run(dir);
		Splitter x = new Splitter();
		dup(x, Critter.getRandomInt(8));
		numSplits++;
		
		/* pick a new direction based on our genes */
		
	}

	public static void runStats(java.util.List<Critter> craigs) {
		System.out.print("Splitter has split into " + numSplits + " additional Splitters");
		System.out.println();
	}
}
