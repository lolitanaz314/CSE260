package GameComponents;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;

public class Dictionary {
	
	ArrayList<String> listOfWords = new ArrayList<>();
	
	
	// just to test
	/*
	public static void main(String [] args) throws FileNotFoundException {
		
		Dictionary newDictionary = new Dictionary();
		
		System.out.println(newDictionary.isValidWord("pie"));
		
	}
	*/
	

	// adds ALL of the dictionary words in text file to the listOfWords ArrayList
	
	public Dictionary() throws FileNotFoundException
	{
		FileReader reader2 = new FileReader("wordList.txt");
		Scanner in2 = new Scanner(reader2);
		
		for(int count1=0; in2.hasNextLine(); count1++)
		{
		    listOfWords.add(in2.nextLine());
		}
		
		in2.close();
	}
	
	// basically a binary search
	// don't need to sort bc the list is already sorted
	
	public boolean isValidWord (String word) {
		
		int index = Collections.binarySearch(listOfWords, word);
		
		if (index < 0) {
			return false;
		}
		return true;
	}
	   
   // just to test
	
   /*
   public ArrayList<String> getlistOfWords()
   {
	   ArrayList<String> testList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			testList.add(listOfWords.get(i));
		}
		
		return testList;
   }
   */
	   
}
