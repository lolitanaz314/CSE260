package Squares;

import GameComponents.Tile;
import GameComponents.Coord;

public class DoubleWordSquare extends Square {
	
	// private Tile tile;
	// private Coord coordinates;
	private int multiplier = 2;
	
	
	public DoubleWordSquare() {
		super();
	}
	
	public DoubleWordSquare(Tile tile, Coord coordinates) {
		super(tile, coordinates);
		// TODO Auto-generated constructor stub
	}
	
	public int multiply (int score) {
		return multiplier * score;
	}
	
}
