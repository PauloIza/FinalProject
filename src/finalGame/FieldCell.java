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
		
		int currentCellIndex = b.calcIndex(getRow(), getCol());
		for (Cell c : b.getCells()) {
			if (c.isField()) {
				if (b.getCellAt(currentCellIndex-b.getNumColumns()).isOutOfBounds()) {
					g.setColor(Color.WHITE);
					g.drawLine(x, y, x + getSize(), y);
				}
				if (b.getCellAt(currentCellIndex + 1).isOutOfBounds()) {
					g.setColor(Color.WHITE);
					g.drawLine(x + getSize() - 1, y, x + getSize() - 1, y + getSize());
				}
				if (b.getCellAt(currentCellIndex + b.getNumColumns()).isOutOfBounds()) {
					g.setColor(Color.WHITE);
					g.drawLine(x, y + getSize() - 1, x + getSize(), y + getSize() - 1);
				}
				
				if (b.getCellAt(currentCellIndex - 1).isOutOfBounds()) {
					g.setColor(Color.WHITE);
					g.drawLine(x, y, x, y + getSize());
				}
				if (getCol() == 14) {
					g.setColor(Color.WHITE);
					g.drawLine(x, y, x, y + getSize());
				}
				if (getCol() == 6) {
					if (getRow() > 5 && getRow() < 16) {
						g.setColor(Color.WHITE);
						g.drawLine(x+10, y, x+10, y + getSize());
					}
				}
				if (getCol() == 21) {
					if (getRow() > 5 && getRow() < 16) {
						g.setColor(Color.WHITE);
						g.drawLine(x+10, y, x+10, y + getSize());
					}
				}
				if (getRow() == 6) {
					if (getCol() > 0 && getCol() < 6) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + getSize(), y);
					}
					if (getCol() > 21 && getCol() < 27) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + getSize(), y);
					}
					if (getCol() == 6) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + getSize()-10, y);
					}
					if (getCol() == 21){
						g.setColor(Color.WHITE);
						g.drawLine(x+10, y, x + getSize(), y);
					}
				}
				if (getRow() == 16) {
					if (getCol() > 0 && getCol() < 6) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + getSize(), y);
					}
					if (getCol() > 21 && getCol() < 27) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + getSize(), y);
					}
					if (getCol() == 6) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + getSize()-10, y);
					}
					if (getCol() == 21){
						g.setColor(Color.WHITE);
						g.drawLine(x+10, y, x + getSize(), y);
					}
				}
				if (getRow() == 8) {
					if (getCol() > 0 && getCol() < 3) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + getSize(), y);
					}
					if (getCol() > 24 && getCol() < 27) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + getSize(), y);
					}
					if (getCol() == 3) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + getSize()-10, y);
					}
					if (getCol() == 24){
						g.setColor(Color.WHITE);
						g.drawLine(x+10, y, x + getSize(), y);
					}
				}
				
				if (getRow() == 14) {
					if (getCol() > 0 && getCol() < 3) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + getSize(), y);
					}
					if (getCol() > 24 && getCol() < 27) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + getSize(), y);
					}
					if (getCol() == 3) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + getSize()-10, y);
					}
					if (getCol() == 24){
						g.setColor(Color.WHITE);
						g.drawLine(x+10, y, x + getSize(), y);
					}
				}

				if (getCol() == 3) {
					if (getRow() > 7 && getRow() < 14) {
						g.setColor(Color.WHITE);
						g.drawLine(x+10, y, x+10, y + getSize());		
					}
				}
				
				if (getCol() == 24) {
					if (getRow() > 7 && getRow() < 14) {
						g.setColor(Color.WHITE);
						g.drawLine(x+10, y, x+10, y + getSize());		
					}
				}
				
				g.drawOval(14*getSize()-2, 11*getSize() - 2, 4, 4);
				g.fillOval(14*getSize()-2, 11*getSize() - 2, 4, 4);
				g.drawOval(5*getSize()-2, 11*getSize() - 2, 4, 4);
				g.fillOval(5*getSize()-2, 11*getSize() - 2, 4, 4);
				g.drawOval(23*getSize()-2, 11*getSize() - 2, 4, 4);
				g.fillOval(23*getSize()-2, 11*getSize() - 2, 4, 4);
				g.drawOval(14*getSize()-50, 11*getSize()-50, 100, 100);
			}
		}
	}
}
