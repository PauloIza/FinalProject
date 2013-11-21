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
	
	@BeforeClass
	public static void setUp() {
		board = new Board("boardConfig.csv");
		board.loadBoardConfig();
		ball = new Ball();
		
		game = new Game(board, ball, "testFormation1.csv", "players.txt");
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
	
	//Tests that players end at the right point
	@Test
	public void testMove() {
		game.runGamePlay();
		assertEquals(game.getPlayers().get(0).getLocation(), board.calcIndex(2, 9));
		assertEquals(game.getPlayers().get(1).getLocation(), board.calcIndex(5, 9));
		assertEquals(game.getPlayers().get(2).getLocation(), board.calcIndex(8, 9));
	}
	
	//Tests player passing ball to teammate
	@Test
	public void testPassBall() {
		player1.hasBall = true;
		player1.passBall(player2);
		assertFalse(player1.hasBall);
		assertTrue(player2.hasBall);
	}
	
	//Tests player attempting to steal ball
	@Test
	public void testStealBall() {
		int stealSuccess = 0;
		player1.hasBall = true;
		for (int i = 0; i < 100; i++) {
			player3.stealBall();
			if (player3.hasBall)
				stealSuccess++;
		}
		
		assertTrue(stealSuccess > 90 && stealSuccess < 100);
	}
}
