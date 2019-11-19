package GameComponents;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import Squares.DoubleLetterSquare;
import Squares.DoubleWordSquare;
import Squares.Square;
import Squares.TripleLetterSquare;
import Squares.TripleWordSquare;

public class Board {
	
	public static final int ROWS = 15;
	public static final int COLS = 15;
	Square[][] squares;
	
	/*
	// instantiates a 15x15 board, with each square being "_"
	public Board() {
		squares = new Square[ROWS][COLS];
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				
				squares[row][col] = new Square();
				squares[row][col].paint();
			}
			System.out.println();
		}
	}
	*/
	
	// this sets up the special tiles on the board at specific locations
	public Board () throws IOException {
		  
		  File file1 = new File("SpecialSquaresBoard.txt"); 
		  
		  BufferedReader br = new BufferedReader(new FileReader(file1)); 
		  squares = new Square[ROWS][COLS];
		  
		try {
				 File file = new File("SpecialSquaresBoard.txt"); 
				 
				 Scanner data = new Scanner(file);
				
				 int row = 0;
				 int col = 0;
				  
				 while (data.hasNextLine()) {
					String line = data.nextLine(); 
					 
					String[] values = line.split(" ");
					
					if (values.length != 15) {
						String err = "parse failed";
						data.close();
						throw new IllegalStateException(err);
					}
					
					for (String str : values) {
						switch(str) {
						case "TW":
							squares[row][col] = new TripleWordSquare();
							squares[row][col].paint();
							break;
						
						case "DW": 
							squares[row][col] = new DoubleWordSquare();
							squares[row][col].paint();
							break;
							
						case "TL":
							squares[row][col] = new TripleLetterSquare();
							squares[row][col].paint();
							break;
							
						case "DL":
							squares[row][col] = new DoubleLetterSquare();
							squares[row][col].paint();
							break;
							
						case "--":
							squares[row][col] = new Square();
							squares[row][col].paint();
						}
						col+=1;
					}
					System.out.println();
					
					row += 1;
					col = 0;
				}
				 
				data.close();
				
		}  catch(Exception e) {
			e.printStackTrace();
			System.exit(2);
		}
	}
	
	// paints ONE SQUARE of the board with a tile. 
	public void paintBoard (Tile tile, int r, int c) {
	
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				
				squares[r][c].setAssignedTile(tile);
				squares[row][col].paint();
			}
			
			System.out.println();
		}
	}
	
	// checks whether word entered is aligned on the board by coordinate entered on board
	/*
	 * can have something like
	 *      _ _ _ _
	 *      _ _ _ _
	 *      _ * * *
	 *      _ _ _ _
	 * 
	 * cannot have something like
	 * 
	 * 
	 *      _ _ _ _
	 *      _ _ _ _
	 *      _ * * _
	 *      _ _ _ *
	 * 
	 */
	
	// checks whether all of the spaces that are occupied are aligned
	// this method is the first check for checking whether word entered on board is allowed
	public boolean isAligned() {
		
		boolean map[][] = new boolean[squares.length][squares.length];
		
		for (int row = 0; row < squares.length; row++) {
			for (int col = 0; col < squares[row].length; col++) {
				if (squares[row][col].isOccupied())
					map[row][col] = true;
				else
					map[row][col] = false;
			}
		}
		
		int count = 0;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j]) {
					isAlignedHelper(i, j, map);
					count++;
				}
			}
		}
		
		if (count > 1)
			return false;
		return true;
	}
	
	public static void isAlignedHelper(int i, int j, boolean [][] map) {
		
		//nothing happens
		int[][] move = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
		
		map[i][j] = false;
		for(int k=0;k<4;k++){
			int i2 = i+move[k][0];
			int j2 = j+move[k][1];
			
			if(0<=i2 && i2<map.length && 0<=j2 && j2<map.length && map [i2][j2])
				
				isAlignedHelper(i2,j2, map);
		}
	}
	
	// input: squares that user entered
	// returns list of words which tiles entered on the board formed words with
	// this is to check whether the letters entered are in the dictionary
	
	// returns list of squares for vertical word PER SQUARE
	public ArrayList<Square> makeWordVertical(int x_initial, int y_initial) {
		ArrayList<Square> vertSquareList = new ArrayList<>();
		
		int row = x_initial;
		
		// increment row until we've reached unoccupied square
		while (row - 1 >= 0) {
			if (squares[row][y_initial].isOccupied()) {
				row--;
			}
			else {
				continue;
			}
		}
		
		while (row < squares.length && squares[row][y_initial].isOccupied()) {
			vertSquareList.add(squares[row][y_initial]);
			row++;
		}
		
		return vertSquareList;
		
	}
	
	// returns list of squares for horizontal word PER SQUARE
	public ArrayList<Square> makeWordHorizontal(int x_initial, int y_initial) {
		
		ArrayList<Square> horizSquareList = new ArrayList<>();
		
		int col = y_initial;
        
		// decrement column until we've reached unoccupied square
		while (col - 1 >= 0) {
			if (squares[x_initial][col].isOccupied()) {
				col--;
			}
			
			else {
				continue;
			}
		}
		
		// increment column to form word (add tiles to ArrayList)
		while (col - 1 < squares.length && squares[x_initial][col+1].isOccupied()) {
			horizSquareList.add(squares[x_initial][col]);
			++col;
		}
		
		return horizSquareList;
	}
	
	public ArrayList<String> getWord(Set<ArrayList<Square>> horizontalOrVerticalList) {
		// for (List<Square> : buc)
		
		return null;
	}
	
	
	// returns squares which the legitimate words formed are on. 
	// supposed to follow same logic as makeWords
	public Set<Square> getWords (ArrayList <Square> vertOrHorizWordList) {
		
		return null;
		
	}

	// this is a function that rips off the tiles on the board if the move is illegitimate 
	// i.e. : if the board is not aligned, if the word(s) formed are not in the dictionary, etc
	public void undoBoard(ArrayList<Square> enteredSquares) {
		
		for (int i = 0; i < enteredSquares.size(); i++) {
			
			Square s = enteredSquares.get(i);
			int row = s.getCoords().getRow();
			int col = s.getCoords().getCol();
			
			squares[row][col].isOccupied();
		}
	}
	
	// this is to make sure that the first player populates middle square (7, 7)
	// if isEmptySquares(), and it's the first
	public boolean isEmptySquares() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (squares[row][col].isOccupied())
					return false;
			}
		}
		return true;
	}
}

