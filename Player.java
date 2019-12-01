// MINISCRABBLE

package GameComponents;
import java.util.ArrayList;

public class Player {
	
	ArrayList <Tile> rack;
	private int score;
	private String name;
	private boolean turn;
	
	public Player(String newName) {
		
		this.name = newName;
		this.turn = false;
		this.rack = new ArrayList<Tile>();
	}
	
	public boolean getTurn() {
		return turn;
	}
	
	public void changeTurn() {
		if (turn)
			turn = false;
		else
			turn = true;
	}
	
	public ArrayList<Tile> getRack () {
		
		for (int i = 0; i < rack.size(); i++)
			System.out.print(rack.get(i) + " ");
		
		return rack;
	}
	
	public void paintRack() {
		for (int i = 0; i < rack.size(); i++)
			System.out.print(rack.get(i) + " ");
	}
	
	// checks whether user letter input can be put on the board
	public boolean letterInRack(char letter) {
		
		for (int i = 0; i < rack.size(); i++)
			if (letter == rack.get(i).getLetter())
				return true;
		
		return false;
	}
	
	public void removeTile(Tile tile) {
		
		for (int i = 0; i < rack.size(); i++) {
			if (rack.get(i).equals(tile)) {
				rack.remove(rack.get(i));
				break;
			}
		}
	}
	
	
	// for exchanging tiles in the rack
	public Tile removeAndReturnTile(Tile tile) {
		
		Tile t = new Tile();
		for (int i = 0; i < rack.size(); i++) {
			if (rack.get(i).equals(tile)) {
				t = rack.get(i);
				rack.remove(rack.get(i));
				break;
			}
		}
		
		return t;
	}
	
	public int getScore() {
		return score;
	}
	
	public void changeScore(int value) {
		score += value;
	}
	
	public String getName() {
		return name;
	}
}
