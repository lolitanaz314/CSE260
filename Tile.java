package GameComponents;
import java.util.*;
import java.util.Iterator;

import Squares.Square;

public class Tile {
	
	/*
	public static void main(String [] args) {
		
		
		ArrayList<Tile> list = new ArrayList<>();
		
		list.add(new Tile('a', 2));
		list.add(new Tile('b', 3));
		list.add(new Tile('d', 4));
	
		Tile t = new Tile('b', 3);
		
		System.out.println(list.size());
		for (int i = 0; i <= list.size() + 1; i++) {
			
			for (Tile x : list)
				System.out.print(x + " ");
			
			System.out.println();
			
			list.remove(list.get(0));
		}
		
		System.out.println(list.size());
	}
	*/
	
	private char letter;
	private int letterPoints;
	
	public Tile() {
		letter = '_';
		letterPoints = 0;
	}
	
	public Tile(char letter, int letterPoints) {
		this.letter = letter;
		this.letterPoints = letterPoints;
	}
	
	public char getLetter() {
		return letter;
	}
	
	public void setLetter(char ch) {
		this.letter = ch;
	}
	
	public int getLetterPoints() {
		return letterPoints;
	}
	
	public String toString() {
		return "" + letter;
	}
	
	public boolean equals(Tile t) {
		return (this.getLetter() == t.getLetter());
	}
}
