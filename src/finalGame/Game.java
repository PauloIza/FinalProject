package finalGame;

import java.util.ArrayList;

public class Game {
	private String formation;
	private String plays;
	private Board board;
	private Ball ball;
	private ArrayList<Player> players;
	
	public Game (Board board, Ball ball, String formation, String plays) {
		this.board = board;
		this.ball = ball;
		this.formation = formation;
		this.plays = plays;
		players = new ArrayList<Player>();
	}
	
	public void loadConfigFiles() {
		loadFormationFiles();
		loadPlayFiles();
	}
	
	public void loadFormationFiles() {
		
	}
	
	public void loadPlayFiles() {
		
	}
	
	public void runGamePlay() {
		
	}
	
	public void saveGameStats() {
		
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
}
