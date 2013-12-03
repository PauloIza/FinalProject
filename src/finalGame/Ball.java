/* 
 * Final Project
 * Team: Eleven Wise Monkeys
 * Team Members: Leah Moldauer, Paulo Iza, Danny Victor
 */

package finalGame;

public class Ball {
	private int currentLocation;
	
	public Ball() {
		currentLocation = 0;
	}
	
	//returns true if the ball is near the index specified
	public boolean isNear(int index) {
		//directly on top of the ball
		if (currentLocation == index) {
			return true;
		//directly adjacent(left, right, up, down)
		} else if (currentLocation == index + 1 || currentLocation == index - 1 || currentLocation == index - 28 || currentLocation == index + 28) {
			return true;
		}
		//diagonal
		else if (currentLocation == index + 28 + 1 || currentLocation == index + 28 - 1 || currentLocation == index - 28 + 1 || currentLocation == index - 28 - 1) {
			return true;
		}
		
		else
			return false;
	}

	public void setLocation(int currentLocation) {
		this.currentLocation = currentLocation;
	}
	
	public int getLocation() {
		return currentLocation;
	}
}
