package transformation.tempPetriNet;

import java.util.Arrays;

public class ResizableMatrix implements Matrix {

	private static final int INITIAL_CAP_ROWS = 3;
	private static final int INITIAL_CAP_COLS = 3;
	
	private static final double RESIZE_FACTOR = 1.25; 
	

	private int rows;
	private int cols;
	
	
	private short[][] m;
	
	
	
	public ResizableMatrix(int rows, int cols) {
		if (rows < 0 || cols < 0) {
			throw new IllegalArgumentException();
		}
		this.rows = rows;
		this.cols = cols;
		
		int capRows = (rows < INITIAL_CAP_ROWS ? INITIAL_CAP_ROWS : (int) (RESIZE_FACTOR * rows));
		int capCols = (cols < INITIAL_CAP_COLS ? INITIAL_CAP_COLS : (int) (RESIZE_FACTOR * cols));
		
		m = new short[capRows][capCols];
	}
	
	
	@Override
	public int getNumRows() {
		return rows;
	}
	
	
	@Override
	public int getNumCols() {
		return cols;
	}
	
	
	@Override
	public short get(int row, int col) {
		checkRowRange(row);
		checkColRange(col);
		return m[row][col];
	}
	
	
	@Override
	public void set(int row, int col, short value) {
		checkRowRange(row);
		checkColRange(col);
		m[row][col] = value;
	}
	
	
//	public void setRowValue(int row, short value) {
////		checkRowRange(row);
//		for (int c = 0; c < cols; c++) {
//			m[row][c] = value;
//		}
//	}
	
	
//	public void setColValue(int col, short value) {
////		checkColRange(col);
//		for (int r = 0; r < rows; r++) {
//			m[r][col] = value;
//		}
//	}
	
	
	
	public void addRows(int num) {
		if (num < 0) {
			throw new IllegalArgumentException();
		}
		rows += num;
		if (rows > m.length) {
			int oldLength = m.length;
			m = Arrays.copyOf(m, (int) (RESIZE_FACTOR * rows));
			for (int r = oldLength ; r < m.length; r++) {
				m[r] = new short[m[0].length];
			}
		}
	}
	
	
	public void addCols(int num) {
		if (num < 0) {
			throw new IllegalArgumentException();
		}
		
		cols += num;
		if (cols > m[0].length) {
			for (int row = 0; row < m.length; row++) {
				m[row] = Arrays.copyOf(m[row], (int) (RESIZE_FACTOR * cols));
			}
		}
	}
	
	
//	public void deleteRows(int num) {
//		if (num < 0 || num > rows) {
//			throw new IllegalArgumentException();
//		}
//		int temp = rows - num;
//		for (int r = temp ; r < rows; r++) {
//			for (int c = 0; c < cols; c++) {
//				m[r][c] = null;
//			}
//		}
//		rows = temp;
//	}
	
	
//	public void deleteCols(int num) {
//		if (num < 0 || num > cols) {
//			throw new IllegalArgumentException();
//		}
//		int temp = cols - num;
//		for (int c = temp; c < cols; c++) {
//			for (int r = 0; r < rows; r++) {
//				m[r][c] = null;
//			}
//		}
//		cols = temp;
//	}
	
	
//	public void copyRow(int src, int dest) {
////		checkRowRange(src);
////		checkRowRange(dest);
//		for (int c = 0; c < cols; c++) {
//			m[dest][c] = m [src][c];
//		}
//	}
	
	
//	public void copyCol(int src, int dest) {
////		checkColRange(src);
////		checkColRange(dest);
//		for (int r = 0; r < rows; r++) {
//			m[r][dest] = m[r][src];
//		}
//	}
	
	
	
	@Override
	public String toString() {
		String result = "";
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				result = String.format("%s %3d", result, m[r][c]);
			}
			result = String.format("%s%n", result);
		}
		return result;
	}
	
	
	private void checkRowRange(int row) {
		if (row >= rows) {
			throw new IllegalArgumentException();
		}
	}
	
	
	private void checkColRange(int col) {
		if (col >= cols) {
			throw new IllegalArgumentException();
		}
	}
	
	
	




	
}
