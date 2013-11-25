package finalGame;

public class Ball {
	private int currentLocation;
	
	public Ball() {
		currentLocation = 0;
	}
	
	public void drawBall() {
		
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

	public void updateLocation() {
		
	}
	
	public int getLocation() {
		return currentLocation;
	}
}
