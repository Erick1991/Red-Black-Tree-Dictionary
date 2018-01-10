package sjsu.Rodriguez.cs146.project3;

import java.io.File;
import java.util.Scanner;

public class MyTester {
	
	public static void main(String[] args) throws Exception { 
		/**
		 * this scanner creates the dictionary
		 */
		RedBlackTree dictionary = new RedBlackTree();
		File file = new File("wordlist.txt");
		Scanner sc = new Scanner(file);
		//start time to creating dictionary
		long timeBefore = System.currentTimeMillis(); 
		while (sc.hasNextLine()) {
			String word = sc.nextLine();
			dictionary.insert(word);
		}
		//end time to creating dictionary
		long timeAfter = System.currentTimeMillis();
		long total = timeAfter - timeBefore;

//-------------------------------------------------------------------------------------------------------------------------------------------------------------
		/**
		 * looks up the words of the text file in my dictionary 
		 */
		File f = new File("TheRoadNotTaken.txt");
		Scanner s = new Scanner(f);
		//start time to looking up the words to the text file in the dictionary
		long timeBefore2 = System.currentTimeMillis();
		while (s.hasNext()) {
			String word = s.next().toLowerCase();
			//to able to find strings attached to period, commas, semicolons
			if (word.substring(word.length()-1).equals(",") || word.substring(word.length()-1).equals(".") || word.substring(word.length()-1).equals(";")) {
				word = word.substring(0, word.length()-1); 
			}
			RedBlackTree.Node theWord = dictionary.lookup(word);
			System.out.println(theWord.key);

		}
		//end time to looking up the words in the dictionary
		long timeAfter2 = System.currentTimeMillis();
		long total2 = timeAfter2 - timeBefore2;
		
		//print out the times at the end 
		System.out.println("Time to create dictionary: "+ total/1000.0+" seconds");
		System.out.println("Time to lookup words in dictionary: "+ total2/1000.0+" seconds");		
	}
}
