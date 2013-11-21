package finalGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	private String formationFile, playerFile, playFile;
	private Board board;
	private Ball ball;
	private ArrayList<Player> players;
	
	public Game (Board board, Ball ball, String formation, String playerFile) {
		this.board = board;
		this.ball = ball;
		this.formationFile = formation;
		this.playerFile = playerFile;
		players = new ArrayList<Player>();
	}
	
	public void loadConfigFiles() {
		loadPlayerFiles();
		loadFormationFiles();
//		loadPlayFiles();
	}
	
	public void loadPlayerFiles() {
		try {
			Scanner scn = new Scanner(new FileReader(playerFile));
			
			while(scn.hasNextLine()) {
				String line[] = scn.nextLine().split(",");
				System.out.println(line[3]);
				int handleStat = Integer.parseInt(line[1]);
				int strengthStat = Integer.parseInt(line[2]);
				int jerseyNum = Integer.parseInt(line[4]);
				Player person = new Player(line[0], handleStat, strengthStat, line[3], jerseyNum);
				players.add(person);
			} 
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void loadFormationFiles() {
		
	}
	
//	public void loadPlayFiles() {
//		
//	}
	
	public void runGamePlay() {
		
	}
	
	public void saveGameStats() {
		
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
}
