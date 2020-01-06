package Squares;

import GameComponents.Tile;
import GameComponents.Coord;

public class TripleWordSquare extends Square {
	
	// private Coord coordinates;
	// private Tile assignedTile;
	private int multiplier = 3;

	public TripleWordSquare() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public TripleWordSquare(Tile tile, Coord coordinates) {
		super(tile, coordinates);
		// TODO Auto-generated constructor stub
	}
	
	
	public int multiply (int score) {
		return multiplier * score;
	}
}
