package GameComponents;
import java.util.ArrayList;
import java.util.Scanner;

import Squares.Square;

public class Main {
	
	private ArrayList<Player> players = new ArrayList<Player>();
	private static Board board;
	private static Bag thisBag;
	private static Dictionary dictionary;

	private static Scanner input = new Scanner(System.in);
	
	public static void main(String [] args) {
		
		board = new Board();
		thisBag = new Bag(); // starting with 100 tiles
		
		Player p = new Player("Nerses");
		String keyWord = "yes";
		
		ArrayList<Coord> coordinateList = new ArrayList<>();
		
		do {
			// one tile placed on the board
			Coord xy = playerMove(p);
			coordinateList.add(xy);
			
			// store coordinates in order to call makeWords 
			for (Coord coordinate : coordinateList)
				System.out.print(coordinate + " ");
			
			System.out.println("Done with move? Enter yes or no");
			String answer = input.nextLine();
			
			// after we get coordinates of new letters, store into ArrayList and call board.isAligned() 
		} while (!(input.nextLine() == keyWord));
	}
	
	// puts ONE TILE on the board, repaints entire board with the one new tile
	// returns the coordinates entered
	public static Coord playerMove (Player player) {
		boolean validInput = false;
		
		do {
			System.out.println("Enter letter from the rack, row and col [0-14]: , all separated by spaces: ");
			System.out.println("Player rack: ");
			
			Tile[] playerRack = player.getRack();
			
			char letter = Character.toUpperCase(input.next().charAt(0));
			int row = input.nextInt();
			int col = input.nextInt();
			
			if (player.letterInRack(letter) && row >= 0 && row < Board.ROWS && col >=0 && col < Board.COLS && !board.squares[row][col].isOccupied()) {
				
				int letterPoints = thisBag.getLetterScore(letter);
				Tile t = new Tile (letter, letterPoints);
				
				// paints new board with the letter added onto board at specific coordinates
				board.paintboard(t, row, col);
				validInput = true;
				
				Coord playerCoord = new Coord(row, col);
				return playerCoord;
				
			}
			
			else {
				System.out.println("Move is invalid! Try again");
				return null;
			}
		} while (!validInput);
		
	}
	
	/*
	private void checkGameOver() {
	
	// game over if bag size == 0 and one of player's rack size == 1
	
		if (getTurn().getRack().size() == 0)
			refillPlayerRack(getTurn());
		if (isTileBagEmpty() && getTurn().getRack().size() == 0) {
			for (int i = 0; i < players.size(); i++)
				if (players.get(i) != getTurn()) {
					int total = 0;
					for (int j = 0; j < players.get(i).getRack().size(); j++)
						total += players.get(i).getRack().get(j).getValue();
					players.get(i).setScore(players.get(i).getScore() - total);
					getTurn().setScore(getTurn().getScore() + total);
				}
			gameOver = true;
		}
	}
	
	*/

	/*
	public void nextTurn() {
		turn = (turn + 1) % players.size();
		turnIndex++;
	}
	*/
	
}


