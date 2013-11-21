package finalGame;

import java.awt.Color;

public class Player {
	public boolean hasBall = false;
	private String name;
	private int[] stats = new int[2];
	private String team;
	private Color color;
	private int jerseyNumber;
	private int currentLocation;
	
	public Player(String name, int ballHandling, int strength, String team, int jerseyNumber) {
		currentLocation = 0;
		color = null;
		this.name = name;
		stats[0] = ballHandling;
		stats[1] = strength;
		this.team = team;
		this.jerseyNumber = jerseyNumber;
	}
	
	public void setLocation(int index) {
		
	}
	
	public int getLocation() {
		return currentLocation;
	}
	
	public void move() {
		
	}
	
	public void passBall(Player passTo) {
		
	}
	
	//steal success is based off ballHandling stat of player who has the ball, and the player attempting to steal
	//currently (in tests) is steal chance = stealing player ballHandling % (eg 95 ballHandling = 95% chance of steal)
	public void stealBall() {
		
	}
}
