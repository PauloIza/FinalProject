package finalGame;

public class FieldCell extends Cell {
	private boolean isCorner;
	
	public FieldCell() {
		super();

		isCorner = false;
	}

	public FieldCell(int row, int col, boolean corner) {
		super(row, col);
		
		isCorner = corner;
	}

	@Override
	public boolean isField() {
		return true;
	}
	
	public boolean isCorner() {
		return isCorner;
	}
	
}
