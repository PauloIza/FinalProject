/* 
 * Final Project
 * Team: Eleven Wise Monkeys
 * Team Members: Leah Moldauer, Paulo Iza, Danny Victor
 */

package finalGame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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
	private Timer t;
	private boolean playGame, running, resetBoard;
	private int currentPlayer1, currentPlayer2, firstTeam;
	private JPanel topPanel, botPanel, team1Panel, team2Panel;
	private JButton runPlay, resetPlay;
	private FormationsWindow formationsDialog;
	
	public Game (String boardFileName, Ball ball, String formation, String playerFile, String team1, String team2) {
		this.board = new Board(boardFileName);
		board.loadBoardConfig();
		board.calcAdjacencies();
		this.formationFile = formation;
		this.playerFile = playerFile;
		this.team1 = new Team(team1);
		this.team2 = new Team(team2);
		players = new ArrayList<Player>();
		playGame = false;
		running = true;
		resetBoard = false;
		Random rand = new Random();
		currentPlayer1 = rand.nextInt(10) + 1;
		currentPlayer2 = rand.nextInt(10) + 1;
		firstTeam = rand.nextInt(3);
		this.ball = ball;
		
		loadConfigFiles();
		
		board.setPlayerList(players);
		board.setBall(ball);
		
		setSize(1135,625);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		menuBar.add(createFormationMenu());
		
		team1Panel = team1StatsDisplayPanel();
		team2Panel = team2StatsDisplayPanel();
		
		runPlay = new JButton("GO!");
		resetPlay = new JButton("Reset board");
		
		board.repaint();
		
		topPanel = new JPanel();
		setupTopPanel();
		botPanel = new JPanel();
	    
		botPanel.setLayout(new GridLayout(1,1));
		botPanel.add(team2Panel);
		botPanel.add(team1Panel);
		
		add(topPanel, BorderLayout.CENTER);
		add(botPanel, BorderLayout.SOUTH);
		
		formationsDialog = new FormationsWindow();
		formationsDialog.game = this;
		
		setVisible(true);
		
		t = new Timer(50, new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				playGame = true;

				runPlay.setText("Reset Board");
				runPlay.repaint();

				if(playGame){
					running = true;

					while(running) {
						runGamePlay();

						board.repaint();

					}

				}
				resetBoard = true;
			}
		});
		
		runPlay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(!resetBoard) {
					t.start();					
				} else if (resetBoard) {
					t.stop();
					runPlay.setText("RUN!");
					runPlay.repaint();
					dispose();
					
					Game.main(null);
					repaint();
					
				}
			}
		});
	}
	
	

	public void setupTopPanel() {
		board.repaint();
		topPanel.setLayout(new GridLayout(1,2));
		
		topPanel.add(board, BorderLayout.EAST);
		topPanel.add(runPlay, BorderLayout.WEST);
		topPanel.repaint();
	}
	
	public static Board getBoard() {
		return board;
	}

	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createCreatePlayer());
		menu.add(createFileExit());
		return menu;
	}
	
	private JMenuItem createFormationItem() {
		JMenuItem item = new JMenuItem("Choose Formation");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				formationsDialog.setVisible(true);
			}
		}	
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	private JMenu createFormationMenu() {
		JMenu menu = new JMenu("Formations");
		menu.add(createFormationItem());
		return menu;
	}
	
	private JPanel team1StatsDisplayPanel() {
		JPanel statsDisplayPanel = new JPanel();
		
		statsDisplayPanel.setLayout(new GridLayout(4,1));
		
		statsDisplayPanel.setBorder(new TitledBorder (new EtchedBorder(), team1.getTeamName() + " (Blue Team)"));
		
		JPanel player1Panel = new JPanel();
		player1Panel.setLayout(new GridLayout(1,2));
		
		for (Player p : players) {
			if (p.getColorName().equalsIgnoreCase("blue")) {
				JPanel playerPanel = new JPanel();
				JTextArea pText = new JTextArea(p.getName());
				pText.setEditable(false);
				playerPanel.add(pText);
				statsDisplayPanel.add(playerPanel);
			}
		}
		
		return statsDisplayPanel;
	}
	
	private JPanel team2StatsDisplayPanel() {
		JPanel statsDisplayPanel = new JPanel();
		
		statsDisplayPanel.setLayout(new GridLayout(4,1));
		
		statsDisplayPanel.setBorder(new TitledBorder (new EtchedBorder(), team2.getTeamName() + " (Red Team)"));
		
		JPanel player1Panel = new JPanel();
		player1Panel.setLayout(new GridLayout(1,2));
		
		for (Player p : players) {
			if (p.getColorName().equalsIgnoreCase("red")) {
				JPanel playerPanel = new JPanel();
				JTextArea pText = new JTextArea(p.getName());
				pText.setEditable(false);
				playerPanel.add(pText);
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
	
	private JMenuItem createCreatePlayer() {
		JMenuItem item = new JMenuItem("Create New Player");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				newPlayer();
			}
		}	
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	public void newPlayer() {
		JPanel creation = new JPanel(new GridLayout(0,2));
		JLabel name = new JLabel("Enter Player name: ");
		JTextField nameEnter = new JTextField();
		JLabel ballHandling = new JLabel("Enter Ball Handle stat: ");
		JTextField ballHandlingEnter = new JTextField();
		JLabel strength= new JLabel("Enter Strength stat: ");
		JTextField strengthEnter = new JTextField();
		JLabel jNum = new JLabel("Enter Jersey Number: ");
		JTextField jNumEnter = new JTextField();
		String[] teams = {"RED", "BLUE"};
		JComboBox teamBox = new JComboBox(teams);
		JLabel teamLabel = new JLabel("Choose a team: ");

		Object[] messageOptions = {creation, name, nameEnter, ballHandling, ballHandlingEnter, 
								   strength, strengthEnter, jNum, jNumEnter , teamLabel, teamBox};

		Object[] options = { "OK", "Cancel" };
		Object selection = JOptionPane.showOptionDialog(this, messageOptions, "Create New Player",
		         JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
		String newName = nameEnter.getText();
		int newballHandling = Integer.parseInt(ballHandlingEnter.getText());
		int newStrength = Integer.parseInt(strengthEnter.getText());
		int newjNum = Integer.parseInt(jNumEnter.getText());
		String team = teamBox.getSelectedItem().toString();
		
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(playerFile, true)));
		    out.println(newName + "," + newballHandling + "," + newStrength + "," + team + "," + newjNum);
		    out.close();
		    repaint();
		} catch (IOException e) {
		    //oh noes!
		}
	}
	
	public void loadConfigFiles() {
		loadPlayerFiles();
		loadFormationFiles();
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
					Player person = new Player(line[0], handleStat, strengthStat, line[3], jerseyNum, 27);
					team1.addPlayer(person);
					players.add(person);
				} else if(line[3].equalsIgnoreCase("blue")) {
					Player person = new Player(line[0], handleStat, strengthStat, line[3], jerseyNum, 0);
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
	
	public void runGamePlay() {
		while(playGame && running) {
			step();
		}
	}
	
	
	// step() allows us to choose which team (randomly) goes first, then moves the current player in that team before the next team.
	public void step() {
		if(firstTeam == 0) {
			currentPlayer1 = move(team1, currentPlayer1);
			currentPlayer2 = move(team2, currentPlayer2);
			board.repaint();
		} else if(firstTeam > 0) {	
			currentPlayer2 = move(team2, currentPlayer2);
			currentPlayer1 = move(team1, currentPlayer1);
			board.repaint();
		}
		
		running = false;		
	}
	
	public int move(Team tempTeam, int currentPlayer) {
		
		if(!board.getCellAt(ball.getLocation()).isGoal()) {
			Player tempPlayer = tempTeam.getTeam().get(currentPlayer);
			int playerLocation = tempPlayer.getLocation();
			LinkedList<Integer> playerAdjacencies = board.getAdjacencyList(playerLocation);
			ArrayList<Cell> playerAdjacentCells = new ArrayList<Cell>();
			boolean badLoc = false;
			
			for(int i : playerAdjacencies) {
				for(Player tP : players) {
					if(tP.getLocation() == i) {
						badLoc = true;
					}
				}
				
				if (board.getCellAt(i).isOutOfBounds() || board.getCellAt(i).isGoal()) {
					badLoc = true;

				}
				
				if(!badLoc) {
					playerAdjacentCells.add(board.getCellAt(i));
				}
				
				badLoc = false;
			}

			tempPlayer.move(playerAdjacentCells, board, ball);
			board.repaint();

			currentPlayer++;
			
			if (currentPlayer == 11){
				currentPlayer = 0;
			}

		}

		else
			playGame = false;
		
		return currentPlayer;
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
	
	public void setFormationFile(String formationFile) {
		this.formationFile = formationFile;
		loadFormationFiles();
		board.repaint();
	}
	
	public static void main(String[] args) {
		ball = new Ball();
		Game game = new Game("board.csv", ball, "formation.csv", "players.txt", "Chelsea", "Barcelona");
	}
}