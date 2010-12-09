package transformation.tempPetriNet;


public class MatrixImpl implements Matrix {

	private final short[][] m;
	
	private final int cols;
	
	
	
	public MatrixImpl(int rows, int cols) {
		m = new short[rows][cols];
		this.cols =cols;
	}
	
	
	public MatrixImpl(short[]... rows) {
		m = new short[rows.length][];
		this.cols = rows[0].length;
		for (int i = 0; i < m.length; i++) {
			if (rows[i].length != cols) {
				throw new IllegalArgumentException();
			}
			m[i] = rows[i];
		}
	}
	
	
	
	
	@Override
	public int getNumRows() {
		return m.length;
	}

	@Override
	public int getNumCols() {
		return cols;
	}

	@Override
	public short get(int row, int col) {
		return m[row][col];
	}

	@Override
	public void set(int row, int col, short value) {
		m[row][col] = value;
	}

	
	@Override
	public String toString() {
		String result = "";
		for (int r = 0; r < m.length; r++) {
			for (int c = 0; c < cols; c++) {
				result = String.format("%s %3d", result, m[r][c]);
			}
			result = String.format("%s%n", result);
		}
		return result;
	}
}
