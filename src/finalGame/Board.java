package finalGame;

import java.util.ArrayList;

public class Board {
	private int numRows;
	private int numCols;
	private String layout;
	private ArrayList<Cell> cells;
	
	public Board(String layout) {
		this.layout = layout;
		cells = new ArrayList<Cell>();
	}
	
	public void loadBoardConfig() {
		
	}
	
	public void calcAdjacencies() {
		
	}
	
	public ArrayList<Cell> getCells() {
		return null;
	}
	
	public Cell getCellAt(int index) {
		return null;
	}
	
	public int calcIndex(int row, int col) {
		return 0;
	}

	public int getNumRows() {
		return 0;
	}
	
	public int getNumColumns() {
		return 0;
	}
}
