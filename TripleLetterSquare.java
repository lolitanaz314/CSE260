package Squares;

import GameComponents.Tile;
import GameComponents.Coord;

public class TripleLetterSquare extends Square {
	
	// private Coord coordinates;
	// private Tile assignedTile;
	private int multiplier = 3;
	
	public TripleLetterSquare() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TripleLetterSquare(Tile tile, Coord coordinates) {
		super(tile, coordinates);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getScore () {
		return multiplier * getAssignedTile().getLetterPoints();
	}

}
