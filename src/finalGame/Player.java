package finalGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.Random;

public class Player {
	public boolean hasBall = false;
	private String name;
	private int[] stats = new int[2];
	private String team;
	private Color teamColor;
	private int jerseyNumber;
	private int currentLocation;
	
	public Player(String name, int ballHandling, int strength, String color, int jerseyNumber) {
		currentLocation = 0;
		this.name = name;
		stats[0] = ballHandling;
		stats[1] = strength;
		System.out.println(color);
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

	public void move() {
		
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
