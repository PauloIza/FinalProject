package finalGame;

public abstract class Cell {
	private int row;
	private int col;
	
	public Cell() {
		super();
	}
	
	public Cell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	
	public boolean isGoal() {
		return false;
	}
	
	public boolean isCorner() {
		return false;
	}
	
	public boolean isField() {
		return false;
	}
	
	public boolean isOutOfBounds() {
		return false;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
}
