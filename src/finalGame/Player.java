package finalGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Player {
	public boolean hasBall = false;
	private String name, team;
	private int[] stats;
	private Color teamColor;
	private int jerseyNumber, currentLocation;
	private Cell ballCell;
	
	public Player(String name, int ballHandling, int strength, String color, int jerseyNumber) {
		stats = new int[2];
		currentLocation = 0;
		this.name = name;
		stats[0] = ballHandling;
		stats[1] = strength;
		this.teamColor = convertColor(color);
		this.jerseyNumber = jerseyNumber;
	}
	
	// Be sure to trim the color, we don't want spaces around the name
	public Color convertColor(String strColor) {
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.toUpperCase().trim());     
			color = (Color)field.get(null); 
		} catch (Exception e) {  
			color = null; // Not defined  
		}
		return color;
	}
	
	public void setLocation(int index) {
		currentLocation = index;
	}
	
	public int getLocation() {
		return currentLocation;
	}
	
	public String getName() {
		return name;
	}

	public int[] getStats() {
		return stats;
	}

	public Color getTeam() {
		return teamColor;
	}

	public int getJerseyNumber() {
		return jerseyNumber;
	}

	public void move(ArrayList<Cell> moveableCells, Board board, Ball ball) {
//		this.ballCell = ballCell;
		ArrayList<Cell> targetList = new ArrayList<Cell>(moveableCells);
		
		// this must be modified so that if the player can move onto the ball and steal it,
		// they do. otherwise, the move method checks to see where the ball is and moves toward it
		// whether that is along a row, column, or even diagonal (they can move on diagonals the way it is currently set up)
		
		//So here's what I did. eDistanceBetween returns the straight line distance between 2 points. If the player is next to the ball, he attempts to steal
		//if he is not next to the ball, he checks to see if the target cell is closer to the ball and moves to it if he is, otherwise, he looks to the next target
		for (Cell target : moveableCells) {
			int targetIndex = board.calcIndex(target.getRow(), target.getCol());
			if(ball.isNear(currentLocation)) {
				stealBall(ball);
			} else {
				if (board.eDistanceBetween(targetIndex, ball.getLocation()) < board.eDistanceBetween(currentLocation, ball.getLocation())) {
					setLocation(targetIndex);
				}
			}
		}
	}
	
	public void passBall(Player passTo) {
		passTo.hasBall = true;
		hasBall = false;
	}
	
	//steal success is based off ballHandlingof the player attempting to steal and distance from ball
	//currently, the player has a .5*ballHandling % chance of stealing
	public void stealBall(Ball ball) {
		if (ball.isNear(currentLocation)) {
			//randomly generates a number 0-100, if that number is less than .5*ballHandling of the player, they successfully stole the ball
			Random rand = new Random();
			int stealChance = rand.nextInt(100);
			if ((stealChance < (stats[0]/2)) && (stealChance > 0)) {
				hasBall = true;
			} else {
				hasBall = false;
			}
			
		} else {
			hasBall = false;
		}
	}
}
