package finalTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import finalGame.Board;

public class BoardTests {
	private static Board board;
	private static int ROWS = 8;
	private static int COLUMNS = 12;
	
	@BeforeClass
	public static void setUp() {
		board = new Board("boardConfig.txt");
		board.loadBoardConfig();
	}

	@Test
	public void testDimensions() {
		assertEquals(ROWS, board.getNumRows());
		assertEquals(COLUMNS, board.getNumColumns());
	}

	@Test
	public void testCalcIndex() {
		assertEquals(0, board.calcIndex(0,0));
		assertEquals(11, board.calcIndex(0, COLUMNS-1));
		assertEquals(84, board.calcIndex(ROWS-1, 0));
		assertEquals(95, board.calcIndex(ROWS-1, COLUMNS-1));
		
		assertEquals(28, board.calcIndex(2, 4));
		assertEquals(58, board.calcIndex(10, 4));
	}
}
