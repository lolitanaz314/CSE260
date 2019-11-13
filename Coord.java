package GameComponents;

public class Coord {
	private int row;
	private int col;
	
	public Coord (int r, int c) {
		row = r;
		col = c;
	}
	
	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public String toString() {
		if (row < 0 || col < 0)
			return "(-,-)";
		return "(" + row + "," + col + ")";
	}

}
