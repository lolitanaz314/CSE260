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

	private static Scanner input = new Scanner(System.in);
	
	public static void main(String [] args) throws IOException {
		
		setupGame();
		
		do {
		
			Player p = players.get(playerTurn);
			onePlayerTurn(p);
			nextTurn();
			
		} while (!isGameOver());
		
		playerWonMessage();
	}
	
	// one player turn
	public static void onePlayerTurn(Player p) {
		
		outerloop:
		while (true) {
			//empty SquareList
			ArrayList<Square> squareList = new ArrayList<>();
			
			// prompt for letters until player says "yes"
			promptPlayerForSquares(p, squareList);
			
			if (board.isAligned()) {
				HashSet<ArrayList<Square>> bucket = makeSetOutOfWordLists(squareList);
				
				ArrayList<String> listOfWords = board.getWords(bucket);
				
				if (listOfWords.isEmpty()) {
					System.out.println("Word too small! Try again");
					undoMove(squareList, p);
					
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
	public static void promptPlayerForSquares(Player p, ArrayList<Square> squareList) {
		
		String keyWord = "yes";
		boolean terminatingInput = false;
		String answer = null;		
		
		do {
			// one tile placed on the board - that means one square is occupied
			Square s = playerMove(p);
	
			if (s == null)
				continue;
			squareList.add(s);
			
			System.out.println("Done with move? Enter yes or no");
			answer = input.next().toLowerCase();
			
			if (answer.equals(keyWord)) {
				terminatingInput = true;
				break;
			}
			
			if (p.rack.size() == 0)
				break;
			
		} while (!terminatingInput);
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
		
		// removes tile from rack
		// DOESN'T WORK - the rack needs to shrink, but it doesn't
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
		
		// if the bag doesn't contain the letter entered, 
		// CHANGE TO DO WHILE LOOP
		
		for (int i = 0; i < thisBag.getBagList().size(); i++) {
			if (thisBag.getBagList().get(i) == t)
				thisBag.pullTile(t);
		}
	}
	
	// paints the board
	// returns null if move is invalid
	// otherwise, returns square
	public static Square playerMove (Player player) {
		boolean validInput = false;
		
		do {
			
			try {
				System.out.println("Enter letter from the rack, row and col [0-14]: , all separated by spaces: ");
				
				System.out.println(player.getName() + "'s rack: ");
				ArrayList<Tile> playerRack = player.getRack();
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


