package Squares;

import GameComponents.Coord;
import GameComponents.Tile;

public class DoubleLetterSquare extends Square {
	
	private int multiplier = 2;
	// private Tile tile;
	// private Coord coordinates;
	
	public DoubleLetterSquare() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DoubleLetterSquare(Tile tile, Coord coordinates) {
		super(tile, coordinates);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getScore () {
		return multiplier * getAssignedTile().getLetterPoints();
	}

}
