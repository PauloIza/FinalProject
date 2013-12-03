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
		ball = new Ball();
		
		game = new Game("board.csv", ball, "formation.csv", "players.txt", TEAM1NAME, TEAM2NAME);
		game.loadConfigFiles();
		board = game.getBoard();
	}
	
	//Tests that players are properly loaded
	@Test
	public void testPlayers() {
		assertTrue(game.getPlayers().get(0).getName().equals("John"));
		assertTrue(game.getPlayers().get(1).getTeam().equals(Color.RED));
		assertTrue(game.getPlayers().get(2).getStats()[0] == 69);
		assertTrue(game.getPlayers().get(3).getJerseyNumber() == 17);
	}
	
	//Tests that teams are properly set up
	@Test
	public void testTeams() {
		assertTrue(game.getTeam1().getTeamName().equals(TEAM1NAME));
		assertTrue(game.getTeam2().getTeam().get(0).getName().equals("Jeff"));
	}
	
	//Tests that the players on/in the teams are starting in the correct place (formation test)
	@Test
	public void testFormationStart() {
		assertEquals(game.getTeam1().getTeam().get(0).getLocation(), board.calcIndex(6,1));
		assertEquals(game.getTeam2().getTeam().get(1).getLocation(), board.calcIndex(7,26));
		assertEquals(game.getBall().getLocation(), board.calcIndex(10, 11));
		
	}
	
	//Tests player passing ball to teammate
	@Test
	public void testPassBall() {
		game.getPlayers().get(0).hasBall = true;
		game.getPlayers().get(0).passBall(game.getPlayers().get(1));
		assertFalse(game.getPlayers().get(0).hasBall);
		assertTrue(game.getPlayers().get(1).hasBall);
	}
	
	//Tests the ball's isNear function
	@Test
	public void testIsNear() {
		Ball testBall = new Ball();
		testBall.setLocation(board.calcIndex(10,11));
		Player testPlayer = new Player("Tester", 90, 80, "PINK", 1, 0);
		//testPlayer location is currently 0,0;
		assertFalse(testBall.isNear(testPlayer.getLocation()));
		//Move to 10,10
		testPlayer.setLocation(board.calcIndex(10, 10));
		assertTrue(testBall.isNear(testPlayer.getLocation()));
		//Move to 9,10
		testPlayer.setLocation(board.calcIndex(9, 10));
		assertTrue(testBall.isNear(testPlayer.getLocation()));
	}
	
	//Tests player attempting to steal ball
	@Test
	public void testStealBall() {
		int stealSuccess = 0;
		Player testPlayer = new Player("Tester", 90, 80, "PINK", 1, 0);
		Ball testBall = new Ball();
		testBall.setLocation(board.calcIndex(10,11));
		
		//Player is at 0,0; Ball at 10,11
		//Player should not be able to steal the ball
		testPlayer.stealBall(testBall);
		assertFalse(testPlayer.hasBall);
		
		testPlayer.setLocation(board.calcIndex(10, 10));
		for (int i = 0; i < 100; i++) {
			testPlayer.stealBall(testBall);
			if (testPlayer.hasBall)
				stealSuccess++;
		}
		//since testPlayer's ballHandling is 90, they should steal AROUND 45% of the time
		assertTrue(stealSuccess > 35 && stealSuccess < 55);

	}
}
