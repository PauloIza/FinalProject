/* 
 * Final Project
 * Team: Eleven Wise Monkeys
 * Team Members: Leah Moldauer, Paulo Iza, Danny Victor
 */

package finalGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Player {
	public boolean hasBall;
	private String name, team;
	private int[] stats;
	protected Color teamColor;
	private int jerseyNumber, currentLocation;
	private Cell ballCell;
	private int goalColumn, scores;
	private String colorName;
	
	public Player(String name, int ballHandling, int strength, String color, int jerseyNumber, int goalCol) {
		stats = new int[2];
		currentLocation = 0;
		hasBall = false;
		this.name = name;
		this.colorName = color;
		stats[0] = ballHandling;
		stats[1] = strength;
		this.teamColor = convertColor(color);
		this.jerseyNumber = jerseyNumber;
		this.goalColumn = goalCol;
		scores = 0;
	}
	
	public int scored() {
		return scores;
	}
	
	public String getColorName() {
		return colorName;
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
		ArrayList<Cell> targetList = new ArrayList<Cell>(moveableCells);
		
		if(!hasBall) {
			//So here's what I did. eDistanceBetween returns the straight line distance between 2 points. If the player is next to the ball, he attempts to steal
			//if he is not next to the ball, he checks to see if the target cell is closer to the ball and moves to it if he is, otherwise, he looks to the next target
			for (Cell target : moveableCells) {
				int targetIndex = board.calcIndex(target.getRow(), target.getCol());
				if(ball.isNear(currentLocation)) {
					stealBall(ball);
					return;
				} else {
					if (board.eDistanceBetween(targetIndex, ball.getLocation()) < board.eDistanceBetween(currentLocation, ball.getLocation())) {
						setLocation(targetIndex);
						return;
					}
				}
			}
			
		} else if(hasBall) {
			int goalIndex = board.calcIndex(board.getRow(currentLocation), goalColumn);
			
			for (Cell target : moveableCells) {
				int targetIndex = board.calcIndex(target.getRow(), target.getCol());
				if(Math.abs(goalColumn - board.getCol(currentLocation)) <= 2) {
					ball.setLocation(goalIndex);
					scores++;
					return;
				} else {
					if (board.eDistanceBetween(targetIndex, goalIndex) < board.eDistanceBetween(currentLocation, goalIndex)) {
						ball.setLocation(targetIndex);
						setLocation(targetIndex);
						return;
					}
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
