// MINISCRABBLE

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
	private static ArrayList<ArrayList<Tile>> racks = new ArrayList<>();
	private static Board board;
	private static Bag thisBag;
	private static Dictionary dictionary;
	private static int playerTurn = 0;
	private static int moveCount = 0;

	private static Scanner input = new Scanner(System.in);
	
	public static void main(String [] args) throws IOException {
		
		setupGame();
		
		do {
			Player p = players.get(playerTurn);
			onePlayerTurn(p);
			nextTurn();
			increaseMoveCount();
			
		} while (!isGameOver());
		
		playerWonMessage();
	}
	
	
	// one player turn
	
	
	public static void onePlayerTurn(Player p) {
		
		outerloop:
		while (true) {
			
			// prompt for letters until player says "yes"
			ArrayList<Square> squareList = promptPlayerForSquareList(p);
			
			/*
			System.out.println("After prompting user for squares: ");
			for (Square squa : squareList)
				squa.paint();
			*/
			
			if (board.isAligned()) {
				HashSet<ArrayList<Square>> bucket = makeSetOutOfWordLists(squareList);
				
				
				ArrayList<String> listOfWords = board.getWords(bucket);
				
				System.out.println("The list of words are: ");
				for (String string : listOfWords)
					System.out.println(string + " ");
				
				if (listOfWords.isEmpty()) {
					System.out.println();
					System.out.println("Word too small! Try again");
					undoMove(squareList, p);
					
					System.out.println("move undid");
					
					// redo while loop 
				}
				
				// if list of words is not empty
				else {
					for (String s : listOfWords) {
						String lowerCase = s.toLowerCase();
						
						int scoresOfLetters;
						
						if (dictionary.isValidWord(lowerCase)) {
							
							scoresOfLetters = scoreSquares(bucket);
							p.changeScore(scoresOfLetters);
							displayRacksAndScores();
							refillRack(p);
							
							break outerloop;
						}
						
						else {
							System.out.println();
							System.out.println("Not valid words! Try again");
							undoMove(squareList, p);
						}
					}
				}
			}
			
			else {
				System.out.println("Board is not aligned! Try again");
				undoMove(squareList, p);
			}
		} 
	}
	
	public static void displayRacksAndScores () {
		for (Player player : players) {
			System.out.println(player.getName() + "'s rack:          " + player.getName() + "'s score: " + player.getScore());
			player.paintRack();
			System.out.println("\n\n");
		}
	}
	
	// prompt user to enter tiles until they say "yes"
	// places tiles on board (changes state of the board)
	// returns an ArrayList<Square>
	
	public static ArrayList<Square> promptPlayerForSquareList(Player p) {
		
		char done = 'D';
		char undo = 'U';
		boolean terminatingInput = false;
		char answer = '\u0000';		
		
		ArrayList<Square> squareList = new ArrayList<>();
		
		outerloop:
		do {
			// one tile placed on the board - that means one square is occupied
			Square s = playerMove(p);
	
			if (s == null)
				continue;
			squareList.add(s);
			
			System.out.println("\nDone with move? Press (D)");
			System.out.println("\nContinue the move? Press (C)");
			System.out.println("\nUndo move? Press (U)");
			
			/*
			System.out.println("Square List:");
			for (Square squa : squareList)
				squa.paint();
			*/
			
			answer = input.next().toUpperCase().charAt(0);
			
			if (answer == undo) {
				undoSquare(p, s, squareList);
			}
			
			// if player is done entering squares
			if (answer == done) {
				
				// if it's the first move, we want the player to place one of squares on (7, 7)
				// Set squares to null. If it's an empty board, player should have placed one of squares on (7, 7)
				
				if (moveCount == 0) {
					
						boolean validater = false;
						
						ArrayList <Coord> coordinateList = new ArrayList<>();
						
						for (Square sq : squareList) {
							Coord squareCoords = sq.getCoords();
							
							int row = squareCoords.getRow();
							int col = squareCoords.getCol();
							
							coordinateList.add(squareCoords);
						}
						
						for (Coord c : coordinateList) {
							int row = c.getRow();
							int col = c.getCol();
							
							/*
							System.out.println("row for 77: "  + row);
							System.out.println("col for 77: " + col);
							*/
						
							if (row == 7 && col == 7) { // rip off squares and board is empty)
								validater = true;
							}
							// System.out.println("validater value: " + validater);
						}
						
						if (validater == true) {
							/*
							System.out.println("Validation is true, and squares are: ");
							for (Square sq : squareList) {
								sq.paint();
							}
							*/
							
							terminatingInput = true;
							break outerloop;
						}
						
						else {
							System.out.println("You have to place one of the first tiles on (7, 7)! Try again");
							moveCount = 0;
							undoMove(squareList, p);
							squareList = new ArrayList<Square>();
						}
				}
				
				else {
					terminatingInput = true;
					break outerloop;
				}
				
			}
			
			if (p.rack.size() == 0)
				break;
			
		} while (!terminatingInput);
		
		return squareList;
		
	}
	
	public static void increaseMoveCount() {
		moveCount++;
	}
	
	public static void undoSquare(Player p, Square s, ArrayList<Square> list) {
		
		// remove from squareList
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(s))
				list.remove(list.get(i));
		}
		
		// refill rack again
		p.rack.add(s.getAssignedTile());
		
		// clear Square on the board
		int row = s.getCoords().getRow();
		int col = s.getCoords().getCol();
		board.squares[row][col].setToNull();
		
	}
	
	
	// prompt user to enter number of players
	// setup board, racks & bag 
	public static void setupGame() throws IOException {
		
		boolean validInput = false;
		int numPlayers;
		
		System.out.println("How many players? (Enter 2 - 4)");
		
		do {
			numPlayers = input.nextInt();
			
			if (numPlayers >= 2 && numPlayers <= 4) 
				validInput = true;
			
			else 
				System.out.println("Not valid input. Try again");
				
		} while (!validInput);
		
		ArrayList<String> playerNames = new ArrayList<>();
		
		System.out.println("Enter the names of the players: ");
		int i = 1;
		
		while (i <= numPlayers) {
			String name = input.next();
	        playerNames.add(name);
			i++;
		}
		
		board = new Board();
		thisBag = new Bag(); // starting with 100 tiles
		dictionary = new Dictionary();
		
		for (String name : playerNames)
			players.add(new Player(name));
		
		// initialize racks for all players
		for (Player player : players) {
			System.out.println(player.getName() + "'s rack:          " + player.getName() + "'s score: " + player.getScore());
			initializeRack(player);
			System.out.println("\n\n");
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
		
		// just to debug
		/*
		for (ArrayList<Square> hOrVSquareList : bucket) {
			
			for (Square s : hOrVSquareList)  {
				s.paint();
			}
			System.out.println();
		}
		*/
		
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
		
		p.removeTile(t);
		
		Coord playerCoord = new Coord(row, col);
		Square filledSquare = new Square(t, playerCoord);
		return filledSquare;
	}
	
	// refills rack with squares
	// undo the board
	public static void undoMove(ArrayList<Square> enteredSquares, Player p) {
		
		board.undoBoard(enteredSquares);
		
		for (int i = 0; i < enteredSquares.size(); i++) {
			p.rack.add(enteredSquares.get(i).getAssignedTile());
		}
	} 
	
	// EDIT to remove from the bag or player rack!
	public static void setBlankTile (Tile t, Player p) {
		System.out.println("Which character do you want as the blank?");
		char letter2 = Character.toUpperCase((input.next()).charAt(0));
		t.setLetter(letter2);
		
		for (int i = 0; i < thisBag.getBagList().size(); i++) {
			if (thisBag.getBagList().get(i) == t)
				thisBag.pullTile(t);
		}
	}
	
	public static Square placeSquare (Player p) {
		return null;
	}
	
	// ONE MOVE
	// either return square, pass turn or exchange rack
	public static Square playerMove (Player player) {
		boolean validInput = false;
		
		do {
			try {
				System.out.println("-------------------------------");
				System.out.println(player.getName() + "'s turn");
				//System.out.println("3 options (Choose 1, 2 or 3) : ");
				System.out.println();
				System.out.println(" (1) Enter letter from the rack, row and col [0-14] , all separated by spaces: ");
				//System.out.println(" (2) Pass turn");
				//System.out.println(" (3) Exchange rack");
				
				System.out.println();
				System.out.println(player.getName() + "'s rack: ");
				ArrayList<Tile> playerRack = player.getRack();
				System.out.println();
				
				board.paintBoard();
				System.out.println();
				
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
			} 
			
			catch (Exception e) {
				System.out.println("Wrong input type! Try again");
			}
			
		} while (!validInput);
		
		return null;
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
		
		p.paintRack();
		
		return p.rack;
	}
	
	// this is called after words on board have been verified
	public static void refillRack(Player p) {
		
		while (p.rack.size() < 7)
			p.rack.add(thisBag.pullRandomTile());
	}
	/*
	public static void main (String[] args) {
		
		thisBag = new Bag();
		
		System.out.println(thisBag.size());
		Player p = new Player("nerses");
		initializeRack(p);
		System.out.println();
		
		exchangeTilesInRack(p);
		
		System.out.println("new rack");
		
		p.paintRack();
		
		System.out.print(thisBag.size());
	}
	*/
	
	// if player doesn't like their tiles in the rack
	public static void exchangeTilesInRack(Player p) {
		
		int playerRackSize = p.rack.size();
		
		// moves tiles from player rack to the bag
		for (int i = 0; i < playerRackSize; i++) {
			
			thisBag.getBagList().add(p.removeAndReturnTile(p.rack.get(0)));
			
		}
		// moves tiles from bag to the rack
		for (int i = 0; i < playerRackSize; i++) {
			p.rack.add(thisBag.pullRandomTile());	
		}
	}
	
	private static void playerWonMessage () {
		
		int maxScore = players.get(0).getScore();
		Player playerWithMaxScore = players.get(0);
		
		for (int i = 1; i < players.size(); i++) {
			Player p = players.get(i);
			
			if (p.getScore() > maxScore) {
				maxScore = p.getScore();
				playerWithMaxScore = p;
			}
		}
		
		System.out.println("Player " + playerWithMaxScore.getName() + " won with a score of " + maxScore);
	}
	
	private static boolean isGameOver() {
	
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



