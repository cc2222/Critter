/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Casey Cotter
 * cbc2298
 * 16445
 * Javier Cortes
 * jc74593
 * 16445
 * Slip days used: <0>
 * Git URL:
 * Fall 2016
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	static int rungs;
	static ArrayList<String> userinput;
	static ArrayList<String> ladder;
	static ArrayList<String> guesses;
	static Set<String> dict;
	static Set<String> dictionary;
	static String[] inputArray;
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin
			ps = System.out;			// default to Stdout
		}
		initialize();
		// TODO methods to read in words, output ladder
		userinput = parse(kb);
		while(!userinput.isEmpty()){
			//TODO wordLadders
			//ladder = getWordLadderDFS(userinput.get(0), userinput.get(1));
			ladder = getWordLadderBFS(userinput.get(0), userinput.get(1));
			printLadder(ladder);
			userinput = parse(kb);
			ladder.clear();
		}
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
		guesses = new ArrayList<String>();
		dict = makeDictionary();
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		// TO DO
		int index1 = 0;
		int index2 = 0;
		ArrayList<String> parseInput = new ArrayList<String>();
		String input = keyboard.nextLine();
		if(input.equals("/quit")){
			return parseInput;
		}
		else{
			for(int a = 0; a < input.length(); a++){
				if(input.charAt(a) == ' '){
						if(index1 == 0){
							index1 = a;
						}
				}
				if(index1 != 0 && input.charAt(a) != ' '){
						index2 = a;
						a = input.length();
				}
			}
			input.
			input = input.substring(0,index1+1) + input.substring(index2);
			inputArray = input.split(" ");
			parseInput.add(inputArray[0].toUpperCase());
			parseInput.add(inputArray[1].toUpperCase());
		}
		return parseInput;
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		ArrayList<String> ladder = new ArrayList<String>();
		char[] startchars = start.toCharArray();
		String newStart = String.valueOf(startchars);
		char[] endchars = end.toCharArray();
		char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};  
		char tempchar;
		ladder.add(start);
		if(start.equals(end)){
			return ladder;
		}
		for(int a = 0; a < start.length(); a++){
			if(startchars[a] != endchars[a]){
				tempchar = startchars[a];
				startchars[a] = endchars[a];
				newStart = String.valueOf(startchars);
				if(dict.contains(newStart) && !guesses.contains(newStart)){
					a = start.length();
				}
				else{
					startchars[a] = tempchar;
					newStart = String.valueOf(startchars);
				}
			}	
		}
		if(newStart.equals(start)){
			for(int b = 0; b < start.length(); b++){
				for(int c = 0; c < alphabet.length; c++){
					if(startchars[b] != alphabet[c]){
						tempchar = startchars[b];
						startchars[b] = alphabet[c];
						newStart = String.valueOf(startchars);
						if(dict.contains(newStart) && !guesses.contains(newStart)){
							c = alphabet.length;
							b = start.length();
						}
						else{
							startchars[b] = tempchar;
							newStart = String.valueOf(startchars);
						}
					}
				}
			}
		}
		if(guesses.contains(newStart)){
			ArrayList<String> tempLadder = new ArrayList<String>();
			return tempLadder;
		}
		guesses.add(newStart);
		ArrayList<String> tempLadder = getWordLadderDFS(newStart,end);
		if(tempLadder.isEmpty()){
			tempLadder = getWordLadderDFS(guesses.get(guesses.indexOf(start)), end);
		}
		ladder.addAll(tempLadder);
		return ladder;
	}
	
	 public static ArrayList<String> getWordLadderBFS(String start, String end) {
		//Create new dictionary for each BFS instance
		 
	 	dictionary = makeDictionary();
	 	
    	//Create a beginning ArrayList, add the start word
	 
    	ArrayList<String> begin = new ArrayList<String>();
    	begin.add(start);
    	
    	//Create LinkedList for word ladder and add the first ArrayList
    	
		LinkedList<ArrayList<String>> queue = new LinkedList<ArrayList<String>>( );
		queue.add(begin);
		
		//While queue contains at least one ArrayList
		
		while(!queue.isEmpty()){
			
			//Remove the first ArrayList from the queue and pull out the last word
			
			ArrayList<String> ladder2 = queue.remove();
			String word = ladder2.get(ladder2.size() - 1);
			
			//If word is the end word, you're done, return the ArrayList
			
			if(word.equals(end)){
				return ladder2;
			}
			
			//Convert the word to an array of characters
			
			char[] chars = word.toCharArray();
			
			//Look at all the possible 1 letter permutations of the word
			
			for(int i = 0; i < chars.length; i++){
				
				//Check every character of the alphabet at every position in the word to check for new valid words
				
				for(char alpha = 'A'; alpha < 'Z'; alpha++){
					char temp = chars[i];
					if(chars[i] != alpha){
						chars[i] = alpha;
					}
					
					//Check to see if the new word created is in the dictionary and not already in the ladder
					
					String newStart = new String(chars);
					
					if(dictionary.contains(newStart) && !ladder2.contains(newStart)){
						
						//If the word is valid and unused, create a copy of the current ladder and add the new word to it
						
						ArrayList<String> laddercopy = new ArrayList<String>();
						laddercopy.addAll(ladder2);
						laddercopy.add(newStart);
						
						//Remove the word from the dictionary so it wont be used again
						
						dictionary.remove(newStart);
						
						//Add the new ladder to the queue
						
						queue.add(laddercopy);
					}
					
					//Put the temp char back into the original word
					
					chars[i] = temp;
				}
			}
		}
		
		//If no path is found, check to see if it finds one in reverse, 
		//if so, reverse that backwards ArrayList and return it
		//otherwise, return an empty ArrayList
		
		ArrayList<String> backwards = getWordLadderBFS(userinput.get(1), userinput.get(0));
		if(!backwards.isEmpty()){
			ArrayList<String> reverse = new ArrayList<String>();
			for(int a = backwards.size()-1; a >= 0; a--){
				reverse.add(backwards.get(a));
			}
			return reverse;
		}
		ArrayList<String> empty = new ArrayList<String>();
		return empty;
	}
    
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
	
	public static void printLadder(ArrayList<String> ladder) {
		if(ladder.isEmpty()){
			System.out.println("no word ladder can be found between " + userinput.get(0).toLowerCase() + " and " + userinput.get(1).toLowerCase());
		}
		else{
			rungs = ladder.size() - 2;
			System.out.println("a " + rungs + " rung ladder exists between " + userinput.get(0).toLowerCase() + " and " + userinput.get(1).toLowerCase());
			for(int a = 0; a < ladder.size(); a++){
				System.out.println(ladder.get(a).toLowerCase());
			}
			
		}
	}
	// TODO
	// Other private static methods here
}
