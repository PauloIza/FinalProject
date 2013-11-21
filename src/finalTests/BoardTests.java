package finalTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import finalGame.Board;

public class BoardTests {
	private static Board board;
	private static int ROWS = 9;
	private static int COLUMNS = 5;
	
	@BeforeClass
	public static void setUp() throws Exception {
		board = new Board("board.csv");
		board.loadBoardConfig();
	}

	//Test that the rows and columns of the board are equal to the known row and column values
	@Test
	public void testDimensions() {
		assertEquals(ROWS, board.getNumRows());
		assertEquals(COLUMNS, board.getNumColumns());
	}

	//Tests to make sure that the calcIndex function returns the correct index
	@Test
	public void testCalcIndex() {
		assertEquals(0, board.calcIndex(0,0));
		assertEquals(4, board.calcIndex(0, COLUMNS-1));
		assertEquals(40, board.calcIndex(ROWS-1, 0));
		assertEquals(44, board.calcIndex(ROWS-1, COLUMNS-1));
		
		assertEquals(13, board.calcIndex(2, 3));
		assertEquals(24, board.calcIndex(4, 4));
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
		assertTrue(board.getCellAt(board.calcIndex(1, 10)).isCorner());
		assertTrue(board.getCellAt(board.calcIndex(10, 1)).isCorner());
		assertTrue(board.getCellAt(board.calcIndex(9, 10)).isCorner());
	}
	
	//Tests to make sure the goals are labeled as such
	@Test
	public void testGoal() {
		assertTrue(board.getCellAt(board.calcIndex(4, 0)).isGoal());
		assertTrue(board.getCellAt(board.calcIndex(5, 0)).isGoal());
		assertTrue(board.getCellAt(board.calcIndex(6, 0)).isGoal());
		assertTrue(board.getCellAt(board.calcIndex(4, 11)).isGoal());
		assertTrue(board.getCellAt(board.calcIndex(5, 11)).isGoal());
		assertTrue(board.getCellAt(board.calcIndex(6, 11)).isGoal());
	}
	
	//Tests to make sure out of bounds cells (labeled X) are labeled as such
	@Test
	public void testOutOfBounds() {
		assertFalse(board.getCellAt(board.calcIndex(0, 2)).isField());
		assertFalse(board.getCellAt(board.calcIndex(9, 11)).isField());
	}
}
