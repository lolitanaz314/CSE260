package GameComponents;
import java.util.ArrayList;

public class Player {
	
	Tile [] rack;
	private int score;
	private String name;
	private Bag bag = new Bag();
	
	public Player(String newName) {
		
		name = newName;
		
		rack = new Tile[7];
		
		// player gets 7 tiles from the bag
		
		for (int i = 0; i < 7; i++)
			rack[i] = bag.pullTile();
	}
	
	public Tile[] getRack () {
		
		for (int i = 0; i < rack.length; i++)
			System.out.print(rack[i] + " ");
		
		return rack;
	}
	/*
    // if player doesn't like their tiles in the rack
	public Tile [] exchangeTilesInRack() {
		
	}
	*/
	
	// checks whether user letter input can be put on the board
	public boolean letterInRack(char letter) {
		
		for (int i = 0; i < rack.length; i++)
			if (letter == rack[i].getLetter())
				return true;
		
		return false;
	}
	
	public int getScore() {
		return score;
	}
	
	public String getName() {
		return name;
	}
	
	
}
