package finalTests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.BeforeClass;
import org.junit.Test;

import finalGame.Ball;
import finalGame.Board;
import finalGame.Game;
import finalGame.Player;

public class GameTests {
	private static Game game;
	private static Board board;
	private static Ball ball;
	private final static String TEAM1NAME = "Power Rangers";
	private final static String TEAM2NAME = "Blue Lagoons";
	
	@BeforeClass
	public static void setUp() {
		board = new Board("board.csv");
		board.loadBoardConfig();
		ball = new Ball();
		
		game = new Game(board, ball, "formation.csv", "players.txt", TEAM1NAME, TEAM2NAME);
		game.loadConfigFiles();
	}
	
	//Tests that players are properly loaded
	@Test
	public void testPlayers() {
		assertTrue(game.getPlayers().get(0).getName().equals("John"));
		System.out.println(game.getPlayers().get(1).getTeam());
		assertTrue(game.getPlayers().get(1).getTeam().equals(Color.RED));
		assertTrue(game.getPlayers().get(2).getStats()[0] == 95);
		assertTrue(game.getPlayers().get(3).getJerseyNumber() == 9);
	}
	
	//Tests that teams are properly set up
	@Test
	public void testTeams() {
		assertTrue(game.getTeam1().getTeamName().equals(TEAM1NAME));
		assertTrue(game.getTeam2().getTeam().get(0).getName().equals("Jacob"));
	}
	
	//Tests that the players on/in the teams are starting in the correct place (formation test)
	@Test
	public void testFormationStart() {
		assertEquals(game.getTeam1().getTeam().get(0).getLocation(), board.calcIndex(10,1));
		assertEquals(game.getTeam2().getTeam().get(1).getLocation(), board.calcIndex(11,26));
		
	}
	
	//Tests that players end at the right point
	@Test
	public void testMove() {
		game.runGamePlay();
		System.out.println(game.getPlayers().get(0).getLocation());
		assertEquals(game.getPlayers().get(0).getLocation(), board.calcIndex(10, 1));
		assertEquals(game.getPlayers().get(1).getLocation(), board.calcIndex(11, 1));
//		assertEquals(game.getPlayers().get(2).getLocation(), board.calcIndex(10, 26));
	}
	
	//Tests player passing ball to teammate
	@Test
	public void testPassBall() {
		game.getPlayers().get(0).hasBall = true;
		game.getPlayers().get(0).passBall(game.getPlayers().get(1));
		assertFalse(game.getPlayers().get(0).hasBall);
		assertTrue(game.getPlayers().get(1).hasBall);
	}
	
	//Tests player attempting to steal ball
	@Test
	public void testStealBall() {
		int stealSuccess = 0;
		game.getPlayers().get(1).hasBall = true;
		for (int i = 0; i < 100; i++) {
			game.getPlayers().get(2).stealBall();
			if (game.getPlayers().get(2).hasBall)
				stealSuccess++;
		}
		
		assertTrue(stealSuccess > 90 && stealSuccess < 100);
	}
}
