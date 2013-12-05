package finalGame;

import java.awt.Color;
import java.awt.Graphics;

import finalGame.Board;

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
	
	@Override
	void draw(Graphics g, Board b) {
		int x = getCol()*getSize();
		int y = getRow()*getSize();
		
		g.setColor(Color.GREEN);
		g.fillRect(x, y, getSize(), getSize());
	}
}
