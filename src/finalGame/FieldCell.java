package finalGame;

public class FieldCell extends Cell {
	
	
	public FieldCell() {
		super();

	}

	public FieldCell(int row, int col) {
		super(row, col);

	}

	public boolean isField() {
		return true;
	}
	
	public boolean isCorner() {
		return false;
	}
}
