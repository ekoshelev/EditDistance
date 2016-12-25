// Elizabeth Koshelev
// COSI 12B, Spring 2015 
// Programming Assignment #6, 4/5/16
// This program finds and prints the edit distance between two words.

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EditDistance {
	//The main class takes information from the client and runs the code with it, or returns an error message.
	public static void main(String[] args) throws FileNotFoundException{
		System.out.println("Enter name of dictionary file:");
		Scanner console = new Scanner(System.in);
		Scanner input = new Scanner(new File(console.next()));
		ArrayList<String> checkWords = new ArrayList<String>();
		checkWords = allWords(input);
		boolean run =true;
		while (run=true){
		System.out.println("Enter two words separated by a space:");
		String word1 = console.next();
		String word2 = console.next();
		ArrayList<String> temp = new ArrayList<String>();
		temp=shortWords(checkWords, word1);
			if (word1.length() != word2.length()){
					System.out.println("No solution");
					run=false;
				} else {
				if (!checkWords.contains(word1) || !checkWords.contains(word2) ) {
				System.out.println("Word does not exist!");
				run=false;
			}  else {
					findPath(mapWords(temp), word1, word2, temp);
				}
			}
		}
	} 
	//The short words method returns a list of words of the same length as the one the user is trying to find a path for.
	public static ArrayList<String> shortWords(ArrayList<String> allWords, String word1){
		 ArrayList<String> shortWords = new ArrayList<String>(); 
		for (int i=0; i<allWords.size(); i++){
			if (allWords.get(i).length()==word1.length()){
			shortWords.add(allWords.get(i));
			}
		}         
		return shortWords;
	}
	public static ArrayList<String> allWords(Scanner in){
		 ArrayList<String> allWords = new ArrayList<String>(); 
		 while (in.hasNext()) { //puts all words in an arraylist
			String word = in.next();
			allWords.add(word); 
		 }
		return allWords;
	}
	//The map method creates a map of every word to an arraylist of all the words that are one letter different, an arraylist titled 'neighborwords'.
	public static Map<String, ArrayList<String>> mapWords(ArrayList<String> shortWords) {
		Map<String, ArrayList<String>> lengthMap = new HashMap<String, ArrayList<String>>();
		int tempsize= shortWords.size();
		for (int i=0; i<tempsize; i++){
				String temp1= shortWords.get(i);
				ArrayList<String> neighborwords = new ArrayList<String>();
			for (int z=0; z<tempsize; z++){
				if (checksimilarCharacters(temp1,shortWords.get(z))==1){//For every word one letter different, put that word into an array called neighborvalues and map the original word to it.					
					neighborwords.add(shortWords.get(z));
				}
				lengthMap.put(temp1, neighborwords);
			}
		}
		return lengthMap;
	}
	//This method finds and prints the path.
	public static void findPath(Map<String, ArrayList<String>> lengthMap, String word1,String word2, ArrayList<String> shortList){
		int difference = checksimilarCharacters(word1, word2);
		Map<String, ArrayList<String>> tempMap = new HashMap<String, ArrayList<String>>();
		tempMap=lengthMap;
		String startword=word1;
		int counter=0;
		int extra=0;
		String temp = "";
		System.out.print("Path = " + word1 + ", ");
		while (difference!=0){
			for (int z=0; z<lengthMap.get(startword).size(); z++){
				temp = lengthMap.get(startword).get(z);	//Go through the list,and if there exists a path that makes the difference between the first word and final one less, change the new word to the first word.
				if (checksimilarCharacters(temp, word2)==difference-1){ 
					counter++;
					difference=difference-1;
					z=lengthMap.get(startword).size();
				} else if (z==lengthMap.get(startword).size()){ //If no words reduce the difference between the two, select the first neighborword to be the new word and delete the old word from the new word's list.
					counter++;
					extra=1;
					String temp1 =lengthMap.get(startword).get(0); //Take the first word in the neighborlist
					int index = lengthMap.get(temp1).indexOf(startword); //Find the index of the first word in the path
					lengthMap.get(temp1).remove(index); //Remove it from the list
					temp=lengthMap.get(startword).get(0);
				}
			}
			if (difference!=0){
				startword=temp;
				System.out.print(startword + ", ");
			} else {
				System.out.println(word2);
			}
		} 
		if (extra!=0){
			counter = counter+1;
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
