package Squares;
import GameComponents.Coord;
import GameComponents.Tile;

public class Square {
	
	private Coord coordinates;
	private char letter;
	private boolean occupied;
	private Tile assignedTile;
	
	// empty square
	public Square() {
		this.letter = '_';
		this.assignedTile = null;
		this.occupied = false;
	}
	
	// square with tile on it
	public Square(Tile tile, Coord coordinates) {
		this.coordinates = coordinates;
		this.letter = tile.getLetter();
		this.assignedTile = tile;
		this.occupied = true;
	}
	
	// to undo the board if player has incorrect tile placement
	public void setToNull() {
		this.letter = '_';
		this.assignedTile = null;
		this.occupied = false;
	}
	
	public boolean isOccupied() {
		return occupied;
	}
	
	public int getScore () {
		return assignedTile.getLetterPoints();
	}
	
	public Coord getCoords() {
		return coordinates;
	}
	
	public char getLetter() {
		return letter;
	}

	public void paint() {
		if (isOccupied())
			System.out.print(getLetter() + " ");
		else
			System.out.print("_ ");
	}
	
	public Tile getAssignedTile() {
		return assignedTile;
	}
	
	public void setAssignedTile(Tile tile) {
		assignedTile = tile;
		this.occupied = true;
		this.letter = tile.getLetter();
	}
	
	public boolean equals (Square s) {
		return ((this.assignedTile).equals(s.assignedTile));
	}
	
}
