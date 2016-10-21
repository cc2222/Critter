/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
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

//import necessary files
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */
import java.util.*;
import java.lang.*;
import java.lang.reflect.Constructor;

public abstract class Critter {
	private static String myPackage;
	private int dir;
	
	//the two fields used to hold the population of critters and their babies
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	//walks one space in the given direction
	protected final void walk(int direction) {
		
		switch(direction){
			case 0: this.x_coord++;
					break;
			case 1: this.x_coord++;
					this.y_coord--;
					break;
			case 2: this.y_coord--;
					break;
			case 3: this.x_coord--;
					this.y_coord--;
					break;
			case 4: this.x_coord--;
					break;
			case 5: this.x_coord--;
					this.y_coord++;
					break;
			case 6: this.y_coord++;
					break;
			case 7: this.x_coord++;
					this.y_coord++;
					break;
		}
		if(this.x_coord > Params.world_width-1){
			this.x_coord = 0;
		}
		if(this.y_coord > Params.world_height-1){
			this.y_coord = 0;
		}
		if(this.x_coord < 0){
			this.x_coord = Params.world_width-1;
		}
		if(this.y_coord < 0){
			this.y_coord = Params.world_height-1;
		}
		
		this.energy -= Params.walk_energy_cost;
	}
	
	//walks two spaces in the given direction
	protected final void run(int direction) {
		
		switch(direction){
			case 0: this.x_coord += 2;
					break;
			case 1: this.x_coord += 2;
					this.y_coord -= 2;
					break;
			case 2: this.y_coord -= 2;
					break;
			case 3: this.x_coord -= 2;
					this.y_coord -= 2;
					break;
			case 4: this.x_coord -= 2;
					break;
			case 5: this.x_coord -= 2;
					this.y_coord += 2;
					break;
			case 6: this.y_coord += 2;
					break;
			case 7: this.x_coord += 2;
					this.y_coord += 2;
					break;
		}
		if(this.x_coord > Params.world_width-1){
			this.x_coord -= Params.world_width;
		}
		if(this.y_coord > Params.world_height-1){
			this.y_coord -= Params.world_height;
		}
		if(this.x_coord < 0){
			this.x_coord += Params.world_width;
		}
		if(this.y_coord < 0){
			this.y_coord += Params.world_height;
		}
		
		this.energy -= Params.run_energy_cost;
	}
	
	//checks to see if the critter has enough energy to reproduce, if so, it does
	protected final void reproduce(Critter offspring, int direction) {
		if(this.getEnergy() > Params.min_reproduce_energy){
			dir = direction;
			offspring.energy = this.getEnergy()/2;
			this.energy -= offspring.getEnergy();
			offspring.x_coord = this.x_coord;
			offspring.y_coord = this.y_coord;
			offspring.walk(dir);
			offspring.energy += Params.walk_energy_cost;
			babies.add(offspring);
		}
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	//clears all dead critters from the population
	private static void clearDead(){
		for(int a = 0; a < population.size(); a++){
			if(population.get(a).getEnergy() <= 0){
				Critter temp = population.get(a);
				population.remove(temp);
				temp = null;
			}
		}
	}
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try{
			//gets the Class of the desired critter types, then gets its constructor and creates a new instance of it
			Class<?> cls = Class.forName("assignment4." + critter_class_name);
			Constructor<?> newConstructor = cls.getConstructor();
			Object obj = newConstructor.newInstance();
			Critter newCritter = (Critter)obj;
			
			//sets the energy to start energy, gives it a random location, and adds it to the population
			newCritter.energy = Params.start_energy;
			newCritter.x_coord = Critter.getRandomInt(Params.world_width);
			newCritter.y_coord = Critter.getRandomInt(Params.world_height);
			population.add(newCritter);
		}
		catch(ClassNotFoundException e){
			//if the given class type doesn't exist, throw an InvalidCritterException
			throw new InvalidCritterException(critter_class_name);
		}
		catch(Exception e){
			
		}
		
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		try{
			//gets the Class of the desired critter type, then gets its constructor and creates a new instance of it
			Class<?> cls = Class.forName("assignment4."+critter_class_name);
			Constructor<?> newConstructor = cls.getConstructor();
			Object obj = newConstructor.newInstance();
			Critter newCritter = (Critter)obj;
			
			//gets the toString of the desired critter type, and uses it to find all instances of that critter type in population
			String crittertype = newCritter.toString();
			for(int a = 0; a < population.size(); a++){
				if(population.get(a).toString().equals(crittertype)){
					result.add(population.get(a));
				}
			}
		}	
		catch(ClassNotFoundException e){
			//if the given class type doesn't exist, throw an InvalidCritterException
			throw new InvalidCritterException(critter_class_name);
		}
		catch(Exception e){
			
		}
		
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		for(int a = 0; a < population.size(); a++){
			Critter temp = population.get(a);
			population.remove(temp);
			temp = null;
		}
		for(int a = 0; a < babies.size(); a++){
			Critter temp = babies.get(a);
			babies.remove(temp);
			temp = null;
		}
	}
	
	//calls every critter's doTimeStep if they are alive, then deals with the encounters between critters
	public static void worldTimeStep() {
		//add all of the new critters to population
		population.addAll(babies);
		babies.clear();
		
		//call each critter's doTimeStep
		for(int a = 0; a < population.size(); a++){
			population.get(a).doTimeStep();
		}
		
		//deal with all encounters caused by occupying the same space
		for(int i = 0; i < population.size(); i++){
			for(int j = 0; j < population.size(); j++){
				//checks to see if two critters occupy the same space, if so, determines if they want to fight, and computes the result
				if((population.get(i).x_coord == population.get(j).x_coord) && (population.get(i).y_coord == population.get(j).y_coord) && (i != j) && (population.get(i).energy > 0) && (population.get(j).energy > 0)){
					boolean fightA = population.get(i).fight(population.get(j).toString());
					boolean fightB = population.get(j).fight(population.get(i).toString());
					int attackA = 0;
					int attackB = 0;
					if(fightA){	
						attackA = Critter.getRandomInt(population.get(i).getEnergy());
					}
					if(fightB){
						attackB = Critter.getRandomInt(population.get(j).getEnergy());
					}
					if((attackA > attackB) || (attackA == attackB)){
						population.get(i).energy += (population.get(j).getEnergy()/2);
						population.get(j).energy = 0;
					}
					else if(attackB > attackA){
						population.get(j).energy += (population.get(i).getEnergy()/2);
						population.get(i).energy = 0;
					}
				}
			}
		}
		
		//clear all of the dead critters after the encounters happen
		clearDead();
		
		//add the new Algae to the population
		for(int b = 0; b < Params.refresh_algae_count; b++){
			try{
				makeCritter("Algae");
			}
			catch(Exception e){
				
			}
		}	
	}
	
	//prints out the world's grid and all of the critters contained within it
	public static void displayWorld() {
		int numCols = 2+Params.world_width;
		int numRows = 2+Params.world_height;
		
		String[][] world = new String[numRows][numCols];
		
		//white spaces
		for(int x = 0; x < numRows; x++)
		{
			for(int y = 0; y < numCols; y++)
			{
				world[x][y] = " ";
			}
		}
		//top and bottom rows
		for(int a = 0; a < numCols; a++)
		{
			world[0][a] = "-";
			world[numRows-1][a] = "-";
		}
		//left and right sides
		for(int b = 0; b < numRows; b++)
		{
			world[b][0] = "|";
			world[b][numCols-1] = "|";
		}
		//corners
		world[0][0] = "+";
		world[0][numCols-1] = "+";
		world[numRows-1][0] = "+";
		world[numRows-1][numCols-1] = "+";
		//critter positions
		for(Critter temp : population)
		{
			world[(temp.y_coord)+1][(temp.x_coord)+1] = temp.toString();
		}
		
		for(int k = 0; k < numRows; k++)
		{
			for(int m = 0; m < numCols; m++)
			{
				System.out.print(world[k][m]);
			}
			System.out.println();
		}
	}
}