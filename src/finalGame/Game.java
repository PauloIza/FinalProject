package finalGame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Game extends JFrame {
	private String formationFile, playerFile, playFile;
	private static Board board;
	private static Ball ball;
	private ArrayList<Player> players;
	private Team team1, team2;
	private boolean playGame;
	private int currentPlayer;
	private JPanel team1Panel, team2Panel;
	private JButton runPlay;
	private JPanel teamFormation;
	
	public Game (String boardFileName, Ball ball, String formation, String playerFile, String team1, String team2) {
		
		setSize(1135,750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		this.board = new Board(boardFileName);
		board.loadBoardConfig();
		board.calcAdjacencies();
		this.formationFile = formation;
		this.playerFile = playerFile;
		this.team1 = new Team(team1);
		this.team2 = new Team(team2);
		players = new ArrayList<Player>();
		playGame = false;
		currentPlayer = 0;
		
		loadConfigFiles();

		this.ball = ball;
		board.setPlayerList(players);
		board.setBall(ball);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		menuBar.add(createFormationMenu());
		menuBar.add(createStatsMenu());
		
		team1Panel = team1StatsDisplayPanel();
		team2Panel = team2StatsDisplayPanel();
		
		runPlay = new JButton("GO!");
		runPlay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				playGame = true;
				runGamePlay();				
			}
		});
		
		teamFormation = new JPanel();
		JPanel topPanel = new JPanel();
		JPanel botPanel = new JPanel();
		
		topPanel.setLayout(new GridLayout(1,2));
		topPanel.setMaximumSize(new Dimension(1135, 1000));
	    topPanel.setMinimumSize(new Dimension(1135, 450));
		topPanel.setPreferredSize(new Dimension(1135,500));
		botPanel.setLayout(new GridLayout(1,1));
		botPanel.setMaximumSize(new Dimension(1135, 100));
	    botPanel.setMinimumSize(new Dimension(1135, 50));
		botPanel.setPreferredSize(new Dimension(1135, 60));
		setLayout(new GridLayout(2,0));
		
		topPanel.add(board);
		topPanel.add(team1Panel);
		botPanel.add(team2Panel);
		botPanel.add(runPlay);
		add(topPanel, BorderLayout.NORTH);
		add(botPanel, BorderLayout.SOUTH);
		
		board.repaint();
	}
	
	public static Board getBoard() {
		return board;
	}

	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createFileExit());
		return menu;
	}
	
	private JMenuItem createFormationItem() {
		JMenuItem item = new JMenuItem("Choose Formation");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				formationFileChooser();
			}
		}	
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	private void formationFileChooser() {
		JFileChooser chooser = new JFileChooser("C:\\Users\\Leah\\Documents\\F13\\CSCI306\\Workspace\\FinalProject");
		chooser.setCurrentDirectory(chooser.getCurrentDirectory());
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			formationFile = selectedFile.getAbsolutePath();
			loadFormationFiles();
			board.setPlayerList(players);
			board.repaint();
		}
	}
	
	private JMenu createFormationMenu() {
		JMenu menu = new JMenu("Formations");
		menu.add(createFormationItem());
		return menu;
	}
	
	private JMenu createStatsMenu() {
		JMenu menu = new JMenu("Game Stats");
		return menu;
	}
	
	private JPanel team1StatsDisplayPanel() {
		JPanel statsDisplayPanel = new JPanel();
		
		statsDisplayPanel.setLayout(new GridLayout(4,1));
		
		JLabel stats = new JLabel(team1.getTeamName() + " (Blue Team)");
		statsDisplayPanel.add(stats);
		
		JPanel player1Panel = new JPanel();
		player1Panel.setLayout(new GridLayout(1,2));
		
		for (Player p : players) {
			System.out.println(p.getTeam());
			if (p.getColorName().equalsIgnoreCase("blue")) {
				JPanel playerPanel = new JPanel();
				playerPanel.setBorder(new TitledBorder (new EtchedBorder(), p.getName()));
				int tempStats[] = p.getStats();
				String bh = Integer.toString(tempStats[0]);
				String str = Integer.toString(tempStats[1]);
				JTextField people = new JTextField("BH: " + bh + " " + "S: " + str);
				people.setEditable(false);
				playerPanel.add(people);
				statsDisplayPanel.add(playerPanel);
			}
		}
		
		return statsDisplayPanel;
	}
	
	private JPanel team2StatsDisplayPanel() {
		JPanel statsDisplayPanel = new JPanel();
		
		statsDisplayPanel.setLayout(new GridLayout(4,1));
		
		JLabel stats = new JLabel(team2.getTeamName() + " (Red Team)");
		statsDisplayPanel.add(stats);
		
		JPanel player1Panel = new JPanel();
		player1Panel.setLayout(new GridLayout(1,2));
		
		for (Player p : players) {
			System.out.println(p.getTeam());
			if (p.getColorName().equalsIgnoreCase("red")) {
				JPanel playerPanel = new JPanel();
				playerPanel.setBorder(new TitledBorder (new EtchedBorder(), p.getName()));
				int tempStats[] = p.getStats();
				String bh = Integer.toString(tempStats[0]);
				String str = Integer.toString(tempStats[1]);
				JTextField people = new JTextField("BH: " + bh + " " + "S: " + str);
				people.setEditable(false);
				playerPanel.add(people);
				statsDisplayPanel.add(playerPanel);
			}
		}
		
		return statsDisplayPanel;
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
				

				if(line[3].equalsIgnoreCase("red")) {
					Player person = new Player(line[0], handleStat, strengthStat, line[3], jerseyNum, 0);
					team1.addPlayer(person);
					players.add(person);
				} else if(line[3].equalsIgnoreCase("blue")) {
					Player person = new Player(line[0], handleStat, strengthStat, line[3], jerseyNum, 27);
					team2.addPlayer(person);
					players.add(person);
					
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
			move();
		}
	}
	
	public void move() {
		if(!board.getCellAt(ball.getLocation()).isGoal()) {
			Player tempPlayer = team1.getTeam().get(currentPlayer);
			int playerLocation = tempPlayer.getLocation();
			LinkedList<Integer> playerAdjacencies = board.getAdjacencyList(playerLocation);
			ArrayList<Cell> playerAdjacentCells = new ArrayList<Cell>();
			
			for(int i : playerAdjacencies) {
				playerAdjacentCells.add(board.getCellAt(i));
			}		
			
			Player tempPlayer2 = team2.getTeam().get(currentPlayer);
			int playerLocation2 = tempPlayer2.getLocation();
			LinkedList<Integer> playerAdjacencies2 = board.getAdjacencyList(playerLocation);
			ArrayList<Cell> playerAdjacentCells2 = new ArrayList<Cell>();
			
			for(int i : playerAdjacencies2) {
				playerAdjacentCells2.add(board.getCellAt(i));
			}		
			
			tempPlayer.move(playerAdjacentCells, board, ball);
			tempPlayer2.move(playerAdjacentCells2, board, ball);
			
			currentPlayer++;
			if (currentPlayer == 10){
				currentPlayer = 0;
			}
		}
		else
			playGame = false;
	}
	
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
		ball = new Ball();
		Game game = new Game("board.csv", ball, "formation.csv", "players.txt", "Chelsea", "Barcelona");
	}
}