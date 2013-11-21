package finalTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import finalGame.Board;

public class BoardTests {
	private static Board board;
	private static int ROWS = 22;
	private static int COLUMNS = 28;
	
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
		assertEquals(0, board.calcIndex(0, 0));
		assertEquals(27, board.calcIndex(0, COLUMNS-1));
		assertEquals(588, board.calcIndex(ROWS-1, 0));
		assertEquals(615, board.calcIndex(ROWS-1, COLUMNS-1));
		
		assertEquals(59, board.calcIndex(2, 3));
		assertEquals(116, board.calcIndex(4, 4));
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
		assertTrue(board.getCellAt(board.calcIndex(1, 26)).isCorner());
		assertTrue(board.getCellAt(board.calcIndex(20, 1)).isCorner());
		assertTrue(board.getCellAt(board.calcIndex(20, 26)).isCorner());
	}
	
	//Tests to make sure the goals are labeled as such
	@Test
	public void testGoal() {
		assertTrue(board.getCellAt(board.calcIndex(8, 0)).isGoal());
		assertTrue(board.getCellAt(board.calcIndex(9, 0)).isGoal());
		assertTrue(board.getCellAt(board.calcIndex(10, 0)).isGoal());
		assertTrue(board.getCellAt(board.calcIndex(11, 0)).isGoal());
		assertTrue(board.getCellAt(board.calcIndex(12, 0)).isGoal());
		assertTrue(board.getCellAt(board.calcIndex(13, 0)).isGoal());
		assertTrue(board.getCellAt(board.calcIndex(8, 27)).isGoal());
		assertTrue(board.getCellAt(board.calcIndex(9, 27)).isGoal());
		assertTrue(board.getCellAt(board.calcIndex(10, 27)).isGoal());
		assertTrue(board.getCellAt(board.calcIndex(11, 27)).isGoal());
		assertTrue(board.getCellAt(board.calcIndex(12, 27)).isGoal());
		assertTrue(board.getCellAt(board.calcIndex(13, 27)).isGoal());
	}
	
	//Tests to make sure out of bounds cells (labeled X) are labeled as such
	@Test
	public void testOutOfBounds() {
		assertFalse(board.getCellAt(board.calcIndex(0, 0)).isField());
		assertFalse(board.getCellAt(board.calcIndex(6, 27)).isField());
	}
}
