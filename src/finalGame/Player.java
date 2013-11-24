package finalGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;

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

	public void move(ArrayList<Cell> moveableCells, Cell ballCell) {
		this.ballCell = ballCell;
		ArrayList<Cell> targetList = new ArrayList<Cell>(moveableCells);
		
		//
		for (Cell target : moveableCells) {
			if()
		}
	}
	
	public void passBall(Player passTo) {
		passTo.hasBall = true;
		hasBall = false;
	}
	
	//steal success is based off ballHandling stat of player who has the ball, and the player attempting to steal
	//currently (in tests) is steal chance = stealing player ballHandling % (eg 95 ballHandling = 95% chance of steal)
	public void stealBall() {
		if (hasBall == true) {
			
		}
	}
}
