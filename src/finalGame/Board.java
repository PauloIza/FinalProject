package finalGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JPanel;

import finalGame.Cell;
import finalGame.Player;

public class Board extends JPanel {
	private int numRows, numCols;
	private String layout;
	private ArrayList<Cell> cells;
	private ArrayList<Player> players;
	private Ball ball;
	private Map<Character, String> fieldCells;
	private Map<Integer, LinkedList<Integer>> adjacentCells;
	
	public Board(String layout) {
		this.layout = layout;
		players = new ArrayList<Player>();
		ball = new Ball();
		numRows = 0;
		numCols = 0;
		cells = new ArrayList<Cell>();
		adjacentCells = new HashMap<Integer, LinkedList<Integer>>();
	}
	
	public void setPlayerList(ArrayList<Player> playerList) {
		this.players = playerList;
	}
	
	public void setBall(Ball tempBall) {
		this.ball = tempBall;
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
						cells.add(new FieldCell(numRows, columnCount++, false));
					} else if(cell.equalsIgnoreCase("c")) {
						cells.add(new FieldCell(numRows, columnCount++, true));
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
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				
				LinkedList<Integer> adjacencies = new LinkedList<Integer>();
				int location = calcIndex(i, j);
				Cell tempCell = cells.get(location);
				
				if(tempCell.isGoal()) {
					adjacentCells.put(location, adjacencies);
					continue;
				}
				
				if(tempCell.isOutOfBounds()) {
					continue;
				}
				
				/*
				 *  If the cell is a field cell, we need to check if the cell above it is an out of bounds cell
				 *  	or a goal cell.
				 */
				
				if(tempCell.isField()) {
					if(i-1 >= 0) {
						Cell northCell = cells.get(calcIndex(i-1, j));
						
						if(!northCell.isOutOfBounds()) {
							adjacencies.add(calcIndex(i-1, j));
						}
					}
					
					
					if(i+1 < numRows) {
						Cell southCell = cells.get(calcIndex(i+1, j));
						
						if(!southCell.isOutOfBounds()) {
							adjacencies.add(calcIndex(i+1, j));
						}
					}
					
					if(j-1 >= 0) {
						Cell eastCell = cells.get(calcIndex(i,j-1));
						
						if(!eastCell.isOutOfBounds()) {
							adjacencies.add(calcIndex(i,j-1));
						}
					}
					
					if(j+1 < numCols) {
						Cell westCell = cells.get(calcIndex(i,j+1));
						
						if(!westCell.isOutOfBounds()) {
							adjacencies.add(calcIndex(i,j+1));
						}
					}
					
					if(i-1 >= 0 && j-1 >= 0) {
						Cell diagonalCell = cells.get(calcIndex(i-1, j-1));
						
						if(!diagonalCell.isOutOfBounds()) {
							adjacencies.add(calcIndex(i-1,j-1));
						}
					}
					
					if(i-1 >= 0 && j+1 < numCols) {
						Cell diagonalCell = cells.get(calcIndex(i-1, j+1));
						
						if(!diagonalCell.isOutOfBounds()) {
							adjacencies.add(calcIndex(i-1,j+1));
						}
					}
					
					if(i+1 < numRows && j-1 >= 0) {
						Cell diagonalCell = cells.get(calcIndex(i+1, j-1));
						
						if(!diagonalCell.isOutOfBounds()) {
							adjacencies.add(calcIndex(i+1,j-1));
						}
					}
					
					if(i+1 < numRows && j+1 < numCols) {
						Cell diagonalCell = cells.get(calcIndex(i+1, j+1));
						
						if(!diagonalCell.isOutOfBounds()) {
							adjacencies.add(calcIndex(i+1,j+1));
						}
					}
				}
				
				adjacentCells.put(location, adjacencies);
			}
		}
	}
	
	public ArrayList<Cell> getCells() {
		return cells;
	}
	
	public Cell getCellAt(int index) {
		return cells.get(index);
	}
	
	public int calcIndex(int row, int col) {
		return (numCols*row + col);
	}
	
	public int getRow(int index) {
		int row = index / numCols;
		return row;
	}
	
	public int getCol(int index) {
		int column = index % numCols;
		return column;
	}

	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numCols;
	}
	
	public LinkedList<Integer> getAdjacencyList(int location) {
		return adjacentCells.get(location);
	}
	
	//Calculates Euclidean(straight line) distance between 2 indices	
	public double eDistanceBetween(int current, int target) {
		int targetRow = getRow(target);
		int targetCol = getCol(target);
		int currentRow = getRow(current);
		int currentCol = getCol(current);

		double a = Math.abs(targetRow - currentRow);
		double b = Math.abs(targetCol - currentCol);

		double c = Math.sqrt((Math.pow(a, 2)) + Math.pow(b, 2));
		
		return c;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, 550, 900);
		
		for (Cell c : cells){
			c.draw(g, this);
			int x = c.getCol()*20;
			int y = c.getRow()*20;
			int currentCellIndex = calcIndex(c.getRow(), c.getCol());
			if (c.isField()) {
				if (getCellAt(currentCellIndex-getNumColumns()).isOutOfBounds()) {
					g.setColor(Color.WHITE);
					g.drawLine(x, y, x + 20, y);
				}
				if (getCellAt(currentCellIndex + 1).isOutOfBounds()) {
					g.setColor(Color.WHITE);
					g.drawLine(x + 20 - 1, y, x + 20 - 1, y + 20);
				}
				if (getCellAt(currentCellIndex + getNumColumns()).isOutOfBounds()) {
					g.setColor(Color.WHITE);
					g.drawLine(x, y + 20 - 1, x + 20, y + 20 - 1);
				}
				
				if (getCellAt(currentCellIndex - 1).isOutOfBounds()) {
					g.setColor(Color.WHITE);
					g.drawLine(x, y, x, y + 20);
				}
				if (c.getCol() == 14) {
					g.setColor(Color.WHITE);
					g.drawLine(x, y, x, y + 20);
				}
				if (c.getCol() == 6) {
					if (c.getRow() > 5 && c.getRow() < 16) {
						g.setColor(Color.WHITE);
						g.drawLine(x+10, y, x+10, y + 20);
					}
				}
				if (c.getCol() == 21) {
					if (c.getRow() > 5 && c.getRow() < 16) {
						g.setColor(Color.WHITE);
						g.drawLine(x+10, y, x+10, y + 20);
					}
				}
				if (c.getRow() == 6) {
					if (c.getCol() > 0 && c.getCol() < 6) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + 20, y);
					}
					if (c.getCol() > 21 && c.getCol() < 27) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + 20, y);
					}
					if (c.getCol() == 6) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + 10, y);
					}
					if (c.getCol() == 21){
						g.setColor(Color.WHITE);
						g.drawLine(x+10, y, x + 20, y);
					}
				}
				if (c.getRow() == 16) {
					if (c.getCol() > 0 && c.getCol() < 6) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + 20, y);
					}
					if (c.getCol() > 21 && c.getCol() < 27) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + 20, y);
					}
					if (c.getCol() == 6) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + 10, y);
					}
					if (c.getCol() == 21){
						g.setColor(Color.WHITE);
						g.drawLine(x+10, y, x + 20, y);
					}
				}
				if (c.getRow() == 8) {
					if (c.getCol() > 0 && c.getCol() < 3) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + 20, y);
					}
					if (c.getCol() > 24 && c.getCol() < 27) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + 20, y);
					}
					if (c.getCol() == 3) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + 10, y);
					}
					if (c.getCol() == 24){
						g.setColor(Color.WHITE);
						g.drawLine(x+10, y, x + 20, y);
					}
				}
				
				if (c.getRow() == 14) {
					if (c.getCol() > 0 && c.getCol() < 3) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + 20, y);
					}
					if (c.getCol() > 24 && c.getCol() < 27) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + 20, y);
					}
					if (c.getCol() == 3) {
						g.setColor(Color.WHITE);
						g.drawLine(x, y, x + 10, y);
					}
					if (c.getCol() == 24){
						g.setColor(Color.WHITE);
						g.drawLine(x+10, y, x + 20, y);
					}
				}

				if (c.getCol() == 3) {
					if (c.getRow() > 7 && c.getRow() < 14) {
						g.setColor(Color.WHITE);
						g.drawLine(x+10, y, x+10, y + 20);		
					}
				}
				
				if (c.getCol() == 24) {
					if (c.getRow() > 7 && c.getRow() < 14) {
						g.setColor(Color.WHITE);
						g.drawLine(x+10, y, x+10, y + 20);		
					}
				}
				
				g.drawOval(14*20-2, 11*20 - 2, 4, 4);
				g.fillOval(14*20-2, 11*20 - 2, 4, 4);
				g.drawOval(5*20-2, 11*20 - 2, 4, 4);
				g.fillOval(5*20-2, 11*20 - 2, 4, 4);
				g.drawOval(23*20-2, 11*20 - 2, 4, 4);
				g.fillOval(23*20-2, 11*20 - 2, 4, 4);
				g.drawOval(14*20-50, 11*20-50, 100, 100);
			}
		}
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 559, 900);
		
		paintPlayers(g);
		paintBall(g);
	}
	
	public void paintPlayers(Graphics g) {
		for(Player tempPlayer : players) {
			int tempRow = getRow(tempPlayer.getLocation());
			int tempCol = getCol(tempPlayer.getLocation());
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(3));
			g2.setColor(Color.BLACK);
			g2.drawOval(tempCol*20, tempRow*20, 15, 15);
			
			g.setColor(tempPlayer.getTeam());
			g.fillOval(tempCol*20, tempRow*20, 15, 15);
		}
	}
	
	public void paintBall(Graphics g) {
		int tempRow = getRow(ball.getLocation());
		int tempCol = getCol(ball.getLocation());
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.BLACK);
		g2.drawOval(tempCol*20, tempRow*20, 9, 9);
		
		g.setColor(Color.WHITE);
		g.fillOval(tempCol*20, tempRow*20, 9, 9);
	}
}
