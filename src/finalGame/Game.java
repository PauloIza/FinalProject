package finalGame;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game extends JFrame {
	private String formationFile, playerFile, playFile;
	private static Board board;
	private static Ball ball;
	private ArrayList<Player> players;
	private Team team1, team2;
	private boolean playGame;
	private int currentPlayer;
	
	public Game (Board board, Ball ball, String formation, String playerFile, String team1, String team2) {
		this.board = board;
		this.ball = ball;
		this.formationFile = formation;
		this.playerFile = playerFile;
		this.team1 = new Team(team1);
		this.team2 = new Team(team2);
		players = new ArrayList<Player>();
		playGame = false;
		currentPlayer = 0;
		
		JPanel topPanel = new JPanel();
		JPanel botPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,2));
		topPanel.setSize(600,600);
		//botPanel.setLayout(new GridLayout(1,1));
		//botPanel.setSize(100,500);
		setLayout(new GridLayout(2,0));
		
		board.loadBoardConfig();
		board.calcAdjacencies();
		
		topPanel.add(board);
		
		add(topPanel);
		//add(botPanel);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		menuBar.add(createFormationMenu());
		menuBar.add(createStatsMenu());
		
		setSize(800,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		JMenu menu2 = new JMenu("Formations");
		JMenu menu3 = new JMenu("Game Stats");
		menu.add(createFileExit());
		return menu;
	}
	
	private JMenu createFormationMenu() {
		JMenu menu = new JMenu("Formations");
		return menu;
	}
	
	private JMenu createStatsMenu() {
		JMenu menu = new JMenu("Game Stats");
		return menu;
	}
	
	private JMenuItem createFileExit() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}	
		item.addActionListener(new MenuItemListener());
		return item;
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
				int handleStat = Integer.parseInt(line[1]);
				int strengthStat = Integer.parseInt(line[2]);
				int jerseyNum = Integer.parseInt(line[4]);
				Player person = new Player(line[0], handleStat, strengthStat, line[3], jerseyNum);
				players.add(person);
				if(line[3].equalsIgnoreCase("red")) {
					team1.addPlayer(person);
				} else if(line[3].equalsIgnoreCase("blue")) {
					team2.addPlayer(person);
				}
			} 
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void loadFormationFiles() {
		try {
			Scanner scn = new Scanner(new FileReader(formationFile));
			int i = 0;
			int j = 0;
			int columnCount = 0;
			int rowCount = 0;
			int tempLoc;
			
			while(scn.hasNextLine()) {
				String line[] = scn.nextLine().split(",");
				columnCount = 0;
				tempLoc = 0;
				for(String cell : line) {
					tempLoc = board.calcIndex(rowCount, columnCount);
					if(cell.equalsIgnoreCase("a")) {
						team1.getTeam().get(i).setLocation(tempLoc);
						i++;
					} else if(cell.equalsIgnoreCase("b")) {
						team2.getTeam().get(j).setLocation(tempLoc);
						j++;
					} else if(cell.equalsIgnoreCase("s")) {
						ball.setLocation(tempLoc);
					}
					columnCount++;
				}
				
				rowCount++;
				
			} 
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
//	public void loadPlayFiles() {
//		
//	}
	
	public void runGamePlay() {
		while(playGame) {
			
		}
	}
	
	public void move() {
		Player tempPlayer = team1.getTeam().get(currentPlayer);
		int playerLocation = tempPlayer.getLocation();
		LinkedList<Integer> playerAdjacencies = board.getAdjacencyList(playerLocation);
		ArrayList<Cell> playerAdjacentCells = new ArrayList<Cell>();
		
		for(int i : playerAdjacencies) {
			playerAdjacentCells.add(board.getCellAt(i));
		}

		
		tempPlayer.move(playerAdjacentCells, board, ball);	}
	
	public void saveGameStats() {
		
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Team getTeam1() {
		return team1;
	}

	public Team getTeam2() {
		return team2;
	}
	
	public Ball getBall() {
		return ball;
	}
	
	public static void main(String[] args) {
		board = new Board("board.csv");
		ball = new Ball();
		Game game = new Game(board, ball, "formation.csv", "players.txt", "Chelsea", "Barcelona");
	}
}