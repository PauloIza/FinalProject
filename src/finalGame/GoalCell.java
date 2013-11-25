package finalGame;

import java.awt.Color;
import java.awt.Graphics;

public class GoalCell extends Cell {
	public GoalCell() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GoalCell(int row, int col) {
		super(row, col);
		// TODO Auto-generated constructor stub
	}

	public boolean isGoal() {
		return true;
	}
	
	@Override
	void draw(Graphics g, Board b) {
		int x = getCol()*getSize();
		int y = getRow()*getSize();
		
		g.setColor(Color.GREEN);
		g.fillRect(x, y, getSize(), getSize());
		
		for (Cell c : b.getCells()) {
			if (c.isGoal()) {
				for (int i = 0; i <= 20; i+=5) {
					g.setColor(Color.white);
					g.drawLine(x, y+i-1, x+getSize()-1, y+i-1);
				}
				for (int i = 0; i <= 20; i+=5) {
					g.setColor(Color.white);
					g.drawLine(x+i-1, y, x+i-1, y+getSize());
				}
			}
		}
	}
}
