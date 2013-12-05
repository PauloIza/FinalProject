/* 
 * Final Project
 * Team: Eleven Wise Monkeys
 * Team Members: Leah Moldauer, Paulo Iza, Danny Victor
 */
package finalGame;

import java.awt.Graphics;

public abstract class Cell {
	private int row;
	private int col;
	protected final static int SIZE = 20;
	
	public Cell() {
		super();
	}
	
	public Cell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public boolean isGoal() {
		return false;
	}
	
	public boolean isCorner() {
		return false;
	}
	
	public static int getSize() {
		return SIZE;
	}
	
	public boolean isField() {
		return false;
	}
	
	public boolean isOutOfBounds() {
		return false;
	}
	
	abstract void draw(Graphics g, Board b);
}
