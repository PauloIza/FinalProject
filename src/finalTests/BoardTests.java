package finalTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import finalGame.Board;

public class BoardTests {
	private static Board board;
	private static int ROWS = 11;
	private static int COLUMNS = 11;
	
	@BeforeClass
	public static void setUp() {
		board = new Board("boardConfig.csv");
		board.loadBoardConfig();
	}

	//Test that the rows and columns of the board are equal to the know row and column values
	@Test
	public void testDimensions() {
		assertEquals(ROWS, board.getNumRows());
		assertEquals(COLUMNS, board.getNumColumns());
	}

	//Tests to make sure that the calcIndex function returns the correct index
	@Test
	public void testCalcIndex() {
		assertEquals(0, board.calcIndex(0,0));
		assertEquals(10, board.calcIndex(0, COLUMNS-1));
		assertEquals(110, board.calcIndex(ROWS-1, 0));
		assertEquals(120, board.calcIndex(ROWS-1, COLUMNS-1));
		
		assertEquals(26, board.calcIndex(2, 4));
		assertEquals(54, board.calcIndex(4, 10));
	}
	
	//Tests to make sure there are Row*Column cells on the board
	@Test
	public void testCellCount() {
		assertEquals(ROWS*COLUMNS, board.getCells().size());
	}
	
	//Tests to make sure the corners of the field are labeled as such
	@Test
	public void testCorners() {
		assertTrue(board.getCellAt(board.calcIndex(1, 1)).isCorner());
		assertTrue(board.getCellAt(board.calcIndex(1, 9)).isCorner());
		assertTrue(board.getCellAt(board.calcIndex(9, 1)).isCorner());
		assertTrue(board.getCellAt(board.calcIndex(9, 9)).isCorner());
	}
	
	//Tests to make sure the goals are labeled as such
	@Test
	public void testGoal() {
		assertTrue(board.getCellAt(board.calcIndex(4, 0)).isGoal());
		assertTrue(board.getCellAt(board.calcIndex(5, 0)).isGoal());
		assertTrue(board.getCellAt(board.calcIndex(6, 0)).isGoal());
		assertTrue(board.getCellAt(board.calcIndex(4, 10)).isGoal());
		assertTrue(board.getCellAt(board.calcIndex(5, 10)).isGoal());
		assertTrue(board.getCellAt(board.calcIndex(6, 10)).isGoal());
	}
	
	//Tests to make sure out of bounds cells (labeled X) are labeled as such
	@Test
	public void testOutOfBounds() {
		assertFalse(board.getCellAt(board.calcIndex(0, 2)).isField());
		assertFalse(board.getCellAt(board.calcIndex(9, 10)).isField());
	}
}
