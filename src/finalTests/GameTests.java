package finalTests;

import static org.junit.Assert.*;

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
	private static Player player1, player2, player3;
	
	@BeforeClass
	public static void setUp() {
		board = new Board("boardConfig.csv");
		board.loadBoardConfig();
		
		ball = new Ball();
		
		player1 = new Player("John", 85, 90, "Barcelona", 10);
		player2 = new Player("James", 75, 95, "Barcelona", 14);
		player3 = new Player("Jacob", 95, 75, "Chelsea", 23);
		game = new Game(board, ball, "testFormation1.csv", "testPlay1.csv");
		game.loadConfigFiles();
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
