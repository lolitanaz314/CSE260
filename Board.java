package GameComponents;

import java.util.*;

import Squares.Square;

public class Board {
	
	public static final int ROWS = 15;
	public static final int COLS = 15;
	Square[][] squares;
	
	
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
	
	
	// paints ONE SQUARE of the board with a tile. 
	public void paintboard (Tile tile, int r, int c) {
	
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
	
	// forms words with surrounding tiles
	public ArrayList<String> makeWords (Coord coordinates) {
		return null;
		
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
