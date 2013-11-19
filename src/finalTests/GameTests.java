package finalTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import finalGame.Ball;
import finalGame.Board;
import finalGame.Game;

public class GameTests {
	private static Game game;
	private static Board board;
	private static Ball ball;
	
	@BeforeClass
	public static void setUp() {
		board = new Board("boardConfig.csv");
		board.loadBoardConfig();
		
		ball = new Ball();
		game = new Game(board, ball, "testFormation1.csv", "plays.txt");
		game.loadConfigFiles();
	}
	@Test
	public void test() {
		fail("Not yet implemented");
	}
}
