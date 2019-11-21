package GameComponents;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
import java.util.Scanner;
import java.util.Set;

import Squares.Square;
import Squares.DoubleLetterSquare;
import Squares.DoubleWordSquare;
import Squares.TripleLetterSquare;
import Squares.TripleWordSquare;

public class Game {
	
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static Board board;
	private static Bag thisBag;
	private static Dictionary dictionary;
	private static int playerTurn = 0;

	private static Scanner input = new Scanner(System.in);
	
	public static void main(String [] args) throws IOException {
		
		board = new Board();
		thisBag = new Bag(); // starting with 100 tiles
		
		Player p = new Player("Nerses");
		initializeRack(p);
		
		String keyWord = "yes";
		
		ArrayList<Square> squareList = new ArrayList<>();
		String answer;
		
		/*
		do {
			// one tile placed on the board - that means one square is occupied
			Square s = playerMove(p);
			
			// store the squares entered into a list so that we can call 
			// board.makeWords if everything is aligned, or board.undoBoard if everything is not aligned
			squareList.add(s);
			
			System.out.println("Done with move? Enter yes or no");
			answer = input.nextLine().toLowerCase();
			
			
			// if user enters yes --> board.makeWords(ArrayList<Squares>)
			
			
			// else, if words are in dictionary --> 
			// getSquaresPartOfWords(ArrayList<Square>) --> input: squares entered by user   output: squares that are part of words formed
			
			// scoreSquares(ArrayList<Square>) --> input: squares part of words   output: points for all the squares
			
			// after we get coordinates of new letters, store into ArrayList and call board.isAligned() 
		} while (!(input.nextLine() == keyWord));
		*/
		
		for (int i = 1; i <= 6; i++) {
			
			Square s = playerMove(p);
			
			
			// store the squares entered into a list so that we can call 
			// board.makeWords if everything is aligned, or board.undoBoard if everything is not aligned
			
			if (s == null)
				continue;
			squareList.add(s);
		}
		// squareList = {o}, {x}
		
		if (board.isAligned()) {
			
			System.out.println("Square list: ");
			// JUST FOR TESTING
			
			/*
			for (Square sq : squareList)
			{
				sq.paint();
			}
				
			for (Square sq : squareList) {
				System.out.print("Square:  " );
				sq.paint();
				System.out.print(" ||");
			
				int x_initial = sq.getCoords().getRow();
				int y_initial = sq.getCoords().getCol();
				
				ArrayList<Square> verticalSquares = board.makeWordVertical(x_initial, y_initial);
			
				for (Square s : verticalSquares)
					s.paint();
				
				
				System.out.println();
			}
			*/
			
			
			HashSet<ArrayList<Square>> bucket = makeSetOutOfWordLists(squareList);
			
			/*
			for (Square sq : squareList) {
				
				int x_initial = sq.getCoords().getRow();
				int y_initial = sq.getCoords().getCol();
				
				// call makewordVertical + makewordHorizontal on every single square
				ArrayList<Square> vertSquareList = board.makeWordVertical(x_initial, y_initial);
				ArrayList<Square> horizSquareList = board.makeWordHorizontal(x_initial, y_initial);
				
				bucket.add(vertSquareList);
				bucket.add(horizSquareList);
			}
			
			System.out.println("entire word array list: ");
			for (ArrayList<Square> hOrVSquareList : bucket) {
				
				for (Square s : hOrVSquareList)  {
					s.paint();
				}
				System.out.println();
			}
			*/
		}
	}
	
	// for each entered square in squareList, make horizontal and vertical words as LISTS
	// add all wordlists to HashSet
	
	public static HashSet<ArrayList<Square>> makeSetOutOfWordLists (ArrayList<Square> enteredSquares) {
		
		HashSet<ArrayList<Square>> bucket = new HashSet<>();
		
		for (Square sq : enteredSquares) {
			
			int x_initial = sq.getCoords().getRow();
			int y_initial = sq.getCoords().getCol();
			
			// call makewordVertical + makewordHorizontal on every single square
			ArrayList<Square> vertSquareList = board.makeWordVertical(x_initial, y_initial);
			ArrayList<Square> horizSquareList = board.makeWordHorizontal(x_initial, y_initial);
			
			bucket.add(vertSquareList);
			bucket.add(horizSquareList);
		}
		
		for (ArrayList<Square> hOrVSquareList : bucket) {
			
			for (Square s : hOrVSquareList)  {
				s.paint();
			}
			System.out.println();
		}
		
		return bucket;
	}
	
	// puts ONE TILE on the board
	// repaints entire board with the one new tile
	// returns the Square on which the tile was entered
	// removes tile from player rack
	public static Square placeSquareOnBoard(Player p, char letter, int row, int col) {
		
		int letterPoints = thisBag.getLetterScore(letter);
		Tile t = new Tile(letter, letterPoints);
		
		if (letter == '?') {
			setBlankTile(t, p);
		}
		
		// paints new board with the letter added onto board at specific coordinates
		board.paintBoard(t, row, col);
		
		// removes tile from rack
		p.removeTile(t);
		
		Coord playerCoord = new Coord(row, col);
		Square filledSquare = new Square(t, playerCoord);
		
		return filledSquare;
	}
	
	// EDIT to remove from the bag or player rack!
	public static void setBlankTile (Tile t, Player p) {
		System.out.println("Which character do you want as the blank?");
		char letter2 = Character.toUpperCase((input.next()).charAt(0));
		t.setLetter(letter2);
		
		// if the bag doesn't contain the letter entered, 
		// CHANGE TO DO WHILE LOOP
		
		for (int i = 0; i < thisBag.getBagList().size(); i++) {
			if (thisBag.getBagList().get(i) == t)
				thisBag.pullTile(t);
		}
	}
	
	// paints the board, removes tile from bag
	public static Square playerMove (Player player) {
		boolean validInput = false;
		
		do {
			System.out.println("Enter letter from the rack, row and col [0-14]: , all separated by spaces: ");
			System.out.println("Player rack: ");
			
			ArrayList<Tile> playerRack = player.getRack();
			
			char letter = Character.toUpperCase(input.next().charAt(0));
			int row = input.nextInt();
			int col = input.nextInt();
			
			if (player.letterInRack(letter) && row >= 0 && row < Board.ROWS && col >=0 && col < Board.COLS && !board.squares[row][col].isOccupied()) {
				
				Square filledSquare = placeSquareOnBoard(player, letter, row, col);
				validInput = true;
				return filledSquare;
				
			}
			
			else {
				System.out.println("Move is invalid! Try again");
				validInput = false;
				return null;
				
			}
		} while (!validInput);
	}
	
	//input is the squares that are part of legitimate words on board
	// called after all vertical and horizontal words have been made
	public static int scoreSquares (Set<ArrayList<Square>> bucket) {
		
		Set<Square> st = new HashSet<Square>(); // [A,T] --> Type: Square;
		    int total_score = 0;
		     for (List<Square> word: bucket) {
			    // verify if word is valid
			   
			    for(Square square: word) {
				    if(st.contains(square)) {
				        continue;
				    }
				    else { // CAT; A is tws, in the total_score we already have the score for c.
				        total_score += square.getScore();
				        st.add(square);
				    }
			    }
		    }
		return total_score;
	}
	
	//each player gets 7 tiles in their rack
	public static ArrayList<Tile> initializeRack (Player p) {
		
		for (int i = 0; i < 7; i++) {
			p.rack.add(thisBag.pullRandomTile());
		}
		
		return p.rack;
	}
	
	// this is called after words on board have been verified
	public static void refillRack(Player p) {
		
		while (p.rack.size() <= 7)
			p.rack.add(thisBag.pullRandomTile());
	}
	
	// if player doesn't like their tiles in the rack
	public static void exchangeTilesInRack(Player p) {
		
		int rackSize = p.rack.size();
		
		// moves tiles from player rack to the bag
		for (int i = 0; i < rackSize; i++) {
			Tile t = p.rack.remove(i);
			thisBag.getBagList().add(t);
		}
		
		// moves tiles from bag to the rack
		for (int i = 0; i < rackSize; i++) {
			p.rack.add(thisBag.pullRandomTile());	
		}
	}
	
	private static boolean checkGameOver() {
	
	// game over if bag size == 0 and one of player's rack size == 1
	
		if (thisBag.getBagList().size() == 0) {
			for (Player p : players) {
				
				if (p.rack.size() == 0)
					return true;
			}
		}
		return false;
	}
	
	public static void nextTurn() {
		playerTurn = (playerTurn + 1) % players.size();
		
	}
	
	
}


