package assignment4;

/*
 * CopyCat Critter does not move or fight. Any time another Critter lands on his position, 
 * he creates a new instance of the Critter type CopyCat is encountering.
 * The number of Critters made from CopyCats are shown in runStats.
 */
public class Critter3 extends Critter {
	
	public static int doubled = 0;
	
	public String toString() { return "3"; }
	
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

	public static void runStats(java.util.List<Critter> cat) {

		System.out.print("" + doubled + " total Critters have spawned from " + cat.size() + " CopyCat");
		System.out.println();
	}
}
