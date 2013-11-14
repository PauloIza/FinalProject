package finalGame;

public class FieldCell extends Cell {
	public boolean isField() {
		return true;
	}
	
	public boolean isCorner() {
		return false;
	}
}
