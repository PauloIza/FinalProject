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
	private JPanel statsPanel;
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
		
		statsPanel = statsDisplayPanel();
		runPlay = new JButton("GO!");
		
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
		topPanel.add(statsPanel);
		botPanel.add(teamFormation);
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
		JFileChooser chooser = new JFileChooser();
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
	
	private JPanel statsDisplayPanel() {
		JPanel statsDisplayPanel = new JPanel();
		
		statsDisplayPanel.setLayout(new GridLayout(4,1));
		
		JLabel stats = new JLabel("Team Stats");
		statsDisplayPanel.add(stats);
		
		JPanel player1Panel = new JPanel();
		player1Panel.setLayout(new GridLayout(1,2));
		
		player1Panel.setBorder(new TitledBorder (new EtchedBorder(), "Player 1"));
		statsDisplayPanel.add(player1Panel);
		
		JPanel player2Panel = new JPanel();
		player2Panel.setLayout(new GridLayout(1,2));
		
		player2Panel.setBorder(new TitledBorder (new EtchedBorder(), "Player 2"));
		statsDisplayPanel.add(player2Panel);
		
		JPanel player3Panel = new JPanel();
		player3Panel.setLayout(new GridLayout(1,2));
		
		player3Panel.setBorder(new TitledBorder (new EtchedBorder(), "Player 3"));
		statsDisplayPanel.add(player3Panel);
		
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
		ball = new Ball();
		Game game = new Game("board.csv", ball, "formation.csv", "players.txt", "Chelsea", "Barcelona");
	}
}