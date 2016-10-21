/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Casey Cotter>
 * <cbc2298>
 * <16445>
 * <Max Fennis>
 * <maf3743>
 * <16450>
 * Slip days used: <0>
 * Fall 2016
 */

package assignment4; // cannot be in default package

//import all necessary files
import java.util.*;
import java.util.Scanner;
import java.io.*;
import java.lang.reflect.Method;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
        //while playing...
        boolean play = true;
        while(play){
        	
        	//print the command prompt
        	System.out.print("critter> ");
            
        	//wait for the next line and put it in given
            String given = kb.nextLine();
            
            //split given into separate words in an ArrayList<String>
            List<String> inputs = new ArrayList<String>(Arrays.asList(given.split("\\s+")));
            String input = inputs.get(0);
                        
            //check to see what the command is, print out correct errors if input is command is invalid or throws and exception
            if(input.equals("quit")){
                if (inputs.size() == 1){
                	 if(input.equals("quit")){
                		 play = false; // is this needed 
                		 return;
                	 }
                }
                else{
                	System.out.println("error processing: " + given);
                }
            }
            else if(input.equals("show")){
            	if (inputs.size() == 1){
                	try{
                		Critter.displayWorld();
                	}
                	catch(Exception e){
                		System.out.println("error processing: " + given);
                	}
                }
                else{
                	System.out.println("error processing: " + given);
                }
            }
            else if(input.equals("step")){
            	if (inputs.size() == 1 || inputs.size() == 2){
            		try{
            			if(inputs.size() == 2){
            				int steps = Integer.parseInt(inputs.get(1));
                			for(int cnt = 0; cnt < steps; cnt++){
                				Critter.worldTimeStep();
                			}
            			}
            			else{
            				Critter.worldTimeStep();
            			}
                	}
            		catch(NumberFormatException e){
            			System.out.println("error processing: " + given);
                	}
                	catch(Exception e){
                		e.printStackTrace();
                		System.out.println("error processing: " + given);
                	}
                }
                else{
                	System.out.println("error processing: " + given);
                }
            }
            else if(input.equals("seed")){
            	if (inputs.size() == 2){
            		try{
            			int numSeeds = Integer.parseInt(inputs.get(1));
            			Critter.setSeed((long)numSeeds);
                	}
            		catch(NumberFormatException e){
            			System.out.println("error processing: " + given);
                	}
                	catch(Exception e){
                		System.out.println("error processing: " + given);
                	}
                }
                else{
                	System.out.println("error processing: " + given);
                }
            }
            else if(input.equals("make")){
            	if (inputs.size() == 2 || inputs.size() == 3){
            		try{
                		if(inputs.size() == 3){
                			int times = Integer.parseInt(inputs.get(2));
                			for(int cnt = 0; cnt < times; cnt++){
                				Critter.makeCritter(inputs.get(1));
                			}
                		}
                		else{
                			Critter.makeCritter(inputs.get(1));
                		}
                	}
                	catch(Exception e){
                		System.out.println("error processing: " + given);
                	}
                }
                else{
                	System.out.println("error processing: " + given);
                }
            }
            else if(input.equals("stats")){
            	if (inputs.size() == 2){
            		try{
            			//get all instances of the desired critter type
            			List<Critter> tempStats = Critter.getInstances(inputs.get(1));
                		Class<?> classType = Class.forName("assignment4."+inputs.get(1));
                		
                		//get the correct runstats method for the desired critter type and call it
                		Method method = classType.getMethod("runStats",List.class);
                		method.invoke(classType,tempStats);
                	}
                	catch(Exception e){
                		System.out.println("error processing: " + given);
                	}
                }
                else{
                	System.out.println("error processing: " + given);
                }
            }
            else{
            	System.out.println("invalid command: " + given);
            }
            System.out.println();
        }
        
        /* Write your code above */
        System.out.flush();

    }
}