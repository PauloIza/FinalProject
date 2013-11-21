package finalGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Board {
	private int numRows;
	private int numCols;
	private String layout;
	private ArrayList<Cell> cells;
	private Map<Character, String> fieldCells;
	
	public Board(String layout) {
		this.layout = layout;
		numRows = 0;
		numCols = 0;
		cells = new ArrayList<Cell>();
	}
	
	public void loadBoardConfig(){
		try{
			FileReader reader = new FileReader(layout);
			Scanner scn = new Scanner(reader);
			
			while(scn.hasNextLine()) {
				String line[] = scn.nextLine().split(",");
				int columnCount = 0;
				
				for(String cell : line) {
					if(cell.equalsIgnoreCase("f")) {
						cells.add(new FieldCell(numRows, columnCount++));
					} else if(cell.equalsIgnoreCase("g")) {
						cells.add(new GoalCell(numRows, columnCount++));
					} else if(cell.equalsIgnoreCase("o")) {
						cells.add(new OutOfBoundsCell(numRows, columnCount++));
					}
					
				}
				
				if(numRows == 0) {
					numCols = columnCount;
				} else {
					if(columnCount != numCols) {
						System.out.println("Incorrectly formatted file! " + numRows + ", " + numCols + ", " + columnCount);
					}
				}
				
				numRows++;
			}
			
		} catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		};
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
		return (numCols*row + col);
	}

	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numCols;
	}
}
