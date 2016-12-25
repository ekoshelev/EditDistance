// Elizabeth Koshelev
// COSI 12B, Spring 2015 
// Programming Assignment #5, 3/26/16
// This program runs the stable marriage algorithm on a list of people and preferences.
//I originally turned this set in on time, and then afterwords realized I made a bug while commenting.

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EditDistance {
	//The main class takes information from the client and runs the code with it, or returns an error message.
	public static void main(String[] args) throws FileNotFoundException{
		System.out.println("File?");
		Scanner console = new Scanner(System.in);
		Scanner input = new Scanner(new File(console.next()));
		String word1 = console.next();
		ArrayList<String> temp = new ArrayList<String>();
		temp=shortWords(input, word1);
		if (!temp.contains(word1) ) {
			System.out.print("Word does not exist!");
		} else {
			String word2 = console.next();
			if (!temp.contains(word2) ) {
				System.out.print("Word does not exist!");
			} else {
				if (word1.length() != word2.length()){
					System.out.print("No solution");
				} else {
				findPath(mapWords(temp), word1, word2, temp);
				}
			}
		}
	} 
	//The short words method returns a list of words of the same length as the one the user is trying to find a path for.
	public static ArrayList<String> shortWords(Scanner in, String word1){
		 ArrayList<String> allWords = new ArrayList<String>(); 
		 ArrayList<String> shortWords = new ArrayList<String>(); 
		 while (in.hasNext()) { //puts all words in an arraylist
			String word = in.next();
			allWords.add(word); 
		 }
		for (int i=0; i<allWords.size(); i++){
			if (allWords.get(i).length()==word1.length()){
			shortWords.add(allWords.get(i));
			}
		}         
		return shortWords;
	}
	//The map method creates a map of every word to an arraylist of all the words that are one letter different, an arraylist titled 'neighborwords'.
	public static Map<String, ArrayList<String>> mapWords(ArrayList<String> shortWords) {
		Map<String, ArrayList<String>> lengthMap = new HashMap<String, ArrayList<String>>();
		int tempsize= shortWords.size();
		for (int i=0; i<tempsize; i++){
				String temp1= shortWords.get(i);
				ArrayList<String> neighborwords = new ArrayList<String>();
				for (int z=0; z<tempsize; z++){
				if (checksimilarCharacters(temp1,shortWords.get(z))==1){					
					neighborwords.add(shortWords.get(z));
				}
				lengthMap.put(temp1, neighborwords);
				
				}
		}
		return lengthMap;
	}
	//This method finds and pritns the path.
	public static void findPath(Map<String, ArrayList<String>> lengthMap, String word1,String word2, ArrayList<String> shortList){
		int difference = checksimilarCharacters(word1, word2);
		Map<String, ArrayList<String>> tempMap = new HashMap<String, ArrayList<String>>();
		tempMap=lengthMap;
		String startword=word1;
		int counter=0;
		int steprandom=0;
		String temp = "";
		System.out.println(word1);
		while (difference!=0){
			steprandom = 0;
			if (lengthMap.get(startword).get(0).equals(null)){
				temp = "Error: Path does not exist.";
				difference=0;
			} else {
				for (int z=0; z<lengthMap.get(startword).size(); z++){
					temp = lengthMap.get(startword).get(z);	//Go through the list
					if (checksimilarCharacters(temp, word2)==difference-1){ //If the difference between the words is 1 less, go for that word
						steprandom=1;
						counter++;
						difference=difference-1;
						z=lengthMap.get(startword).size();
					} 
					else if (z==lengthMap.get(startword).size()){ //If no words reduce the difference
					counter ++;
					String temp1 =lengthMap.get(startword).get(0); //Take the first word in the neighborlist
					int index = lengthMap.get(temp1).indexOf(startword); //Find the index of the first word in the path
					lengthMap.get(temp1).remove(index); //Remove it from the list
					temp=lengthMap.get(startword).get(0);
					}
				}

				startword=temp;
				System.out.println(startword);
				//startword=temp;
					}
			} 
		System.out.println("Edit distance = " + counter);
	}
	//This method returns the difference in characters.
	public static int checksimilarCharacters(String word, String nextword){
		int diffchar=0;
		for (int i=0; i<word.length(); i++){
			if (word.charAt(i)!=nextword.charAt(i)){
				diffchar++;
			}
		}
		return diffchar;
	}
}
