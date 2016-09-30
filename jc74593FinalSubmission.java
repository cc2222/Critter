/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Javier Cortes
 * jc74593
 * 16445
 * Casey Cotter
 * cbc2298
 * 16445
 * Slip days used: <0>
 * Git URL: https://github.com/cc2222/Project3.git
 * Fall 2016
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	static int rungs;
	static Set<String> dict;
	static ArrayList<String> userInput;
	static ArrayList<String> ladder;
	static ArrayList<String> guesses;
	static Set<String> dictionary;
	static boolean checkBackwards = false;
	
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
		userInput = parse(kb);
		while(!userInput.isEmpty()){
			ladder = getWordLadderDFS(userInput.get(0), userInput.get(1));
			printLadder(ladder);
			ladder = getWordLadderBFS(userInput.get(0), userInput.get(1));
			printLadder(ladder);
			userInput = parse(kb);
			ladder.clear();
			guesses.clear();
			checkBackwards = false;
		}
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
		dict = makeDictionary();
		guesses = new ArrayList<String>();
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		ArrayList<String> parseInput = new ArrayList<String>();
		String input = keyboard.nextLine();
		
		if(input.equals("/quit")){
			return parseInput;
		}
		
		int index1 = 0;
		for(int a = 0; a<input.length(); a++){
			if(input.charAt(a) == ' '){
				if(index1 == 0){
					index1 = a;
					a = input.length();
				}
			}else if(input.charAt(a) == '	'){
				if(index1 == 0){
					index1 = a;
					a = input.length();
				}
			}
		}
		input = input.replaceAll("\\s+", "");
		
		if(input.equals("")){
			return parseInput;
		}
		
		String[] inArray = new String[2];
		inArray[0] = input.substring(0, index1);
		inArray[1] = input.substring(index1);
		parseInput.add(inArray[0].toUpperCase());
		parseInput.add(inArray[1].toUpperCase());
		
		return parseInput;
	}
	
	/**
	 * Does a depth first search to find an N-rung word ladder between start and end
	 * @param start: the start of the search
	 * @param end: the conclusion of the search
	 * @return the word ladder in order of the path taken
	 */
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		ArrayList<String> ladder = new ArrayList<String>(); 	
		ArrayList<String> newGuesses = new ArrayList<String>(); //Localized guesses for each recursive start
		String newStart = start;
		char [] startChar = start.toCharArray();
		char [] endChar = end.toCharArray();
		char tempChar;
		char [] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		
		int mostCommon = 0;
		int commonIndex = -1;
		
		//add all guesses made, keeps track of all visited words
		if(!guesses.contains(start)){
			guesses.add(start);
		}
		
		//base case: if the end word is reached return a non empty ladder containing the end word
		if(start.equals(end)){
			ladder.add(end);
			return ladder;
		}
		
		//Check all possible paths associated with a word, need to be in dict and needs to be a new guess
		for(int b = 0; b < start.length(); b++){
			for(int c = 0; c < alphabet.length; c++){
				if(startChar[b] != alphabet[c]){
					tempChar = startChar[b];
					startChar[b] = alphabet[c];
					newStart = String.valueOf(startChar);
					if(dict.contains(newStart) && !newGuesses.contains(newStart) && !guesses.contains(newStart)){
						newGuesses.add(newStart);
						guesses.add(newStart);
					}
					startChar[b] = tempChar;
				}
			}
		}
		
		//Look for words in current list of paths that are close to the end
		for(int i = 0; i<newGuesses.size(); i++){
			int commonLetters = 0;
			char[] currentGuess = newGuesses.get(i).toCharArray();
			for(int j = 0; j<newGuesses.get(i).length(); j++){
				if(currentGuess[j] == endChar[j]){
					commonLetters++;
				}
			}
			if(commonLetters > mostCommon){
				mostCommon = commonLetters;
				commonIndex = i;
			}
		}
		
		//If no new guesses exist for this path, return an empty ladder
		if(newGuesses.isEmpty()){
			ArrayList<String> tempLadder = new ArrayList<String>();
			return tempLadder;
		}
		
		//If no word is found that is close to the end word start trying all paths associated
		if(commonIndex == -1){
			for(int i = 0; i<newGuesses.size(); i++){
				ArrayList<String> branchLadder = getWordLadderDFS(newGuesses.get(i), end);
				
				//If ladder found should contain end and will allow merge of correct paths (value of start)
				if(branchLadder.contains(end)){
					if(!start.equals(end) && !branchLadder.contains(start)){
						ladder.add(start);
					}
					ladder.addAll(branchLadder);
				}
			}
		}
		//If word close to end is found go down that path and try to find correct path
		else{
			ArrayList<String> tempLadder = getWordLadderDFS(newGuesses.get(commonIndex), end);
			
			//If end found merge correct path values into ladder
			if(tempLadder.contains(end)){
				if(!start.equals(end) && !tempLadder.contains(start)){
					ladder.add(start);
				}
				ladder.addAll(tempLadder);
			}
			//If end is not found try other paths on same level as past try
			else{
				for(int i = 0; i<newGuesses.size(); i++){
					if(i != commonIndex){
						ArrayList<String> branchLadder = getWordLadderDFS(newGuesses.get(i), end);
						if(branchLadder.contains(end)){
							if(!start.equals(end) && !branchLadder.contains(start)){
								ladder.add(start);
							}
						}
						ladder.addAll(branchLadder);
					}
				}
			}
		}
		
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
		
		if(!checkBackwards){
			checkBackwards = true;
			ArrayList<String> backwards = getWordLadderBFS(userInput.get(1), userInput.get(0));
			if(!backwards.isEmpty()){
				ArrayList<String> reverse = new ArrayList<String>();
				for(int a = backwards.size()-1; a >= 0; a--){
					reverse.add(backwards.get(a));
				}
				return reverse;
			}
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
			System.out.println("no word ladder can be found between " + userInput.get(0).toLowerCase() + " " + userInput.get(1).toLowerCase());
		}
		else{
			rungs = ladder.size() - 2;
			System.out.println("a " + rungs + " rung word ladder exists between " + userInput.get(0).toLowerCase() + " and " + userInput.get(1).toLowerCase());
			for(int i = 0; i < ladder.size(); i++){
				System.out.println(ladder.get(i).toLowerCase());
			}
		}
	}
	// TODO
	// Other private static methods here
}
