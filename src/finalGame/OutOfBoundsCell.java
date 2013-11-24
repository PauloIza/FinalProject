package finalGame;

public class OutOfBoundsCell extends Cell {

	public OutOfBoundsCell() {
	}

	public OutOfBoundsCell(int row, int col) {
		super(row, col);
	}

	@Override
	public boolean isGoal() {
		
		return super.isGoal();
	}

	@Override
	public boolean isCorner() {
		
		return super.isCorner();
	}

	@Override
	public boolean isField() {
		
		return super.isField();
	}

	@Override
	public boolean isOutOfBounds() {
		return true;
	}

}
