package GameComponents;
import java.util.Iterator;

import Squares.Square;

public class Tile {
	
	private char letter;
	private int letterPoints;
	private Square assignedSquare;
	
	public Tile() {
		letter = '_';
		letterPoints = 0;
		assignedSquare = null;
	}
	
	public Tile(char letter, int letterPoints) {
		this.letter = letter;
		this.letterPoints = letterPoints;
	}
	
	public char getLetter() {
		return letter;
	}
	
	public int getLetterPoints() {
		return letterPoints;
	}
	
	public Square getAssignedSquare() {
		return assignedSquare;
	}
	
	public String toString() {
		return "" + letter;
	}

}
