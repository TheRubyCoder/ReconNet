package transformation;


/**
 * A simple matrix with Integers in it 
 */
class Matrix{

	/** Matrix is internally realized as a nested array */
	private final int[][] matrix;
	
	
	public Matrix(int[][] matrix) {
		this.matrix = matrix;
	}
	
	
	public int getNumRows() {
		return matrix.length;
	}

	public int getNumCols() {
		if(matrix.length == 0)
			return 0;
		return matrix[0].length;
	}

	/**
	 * Returns the value at given position
	 * @param row index of row of position
	 * @param column index of column of position
	 * @return value
	 * @throws ArrayIndexOutOfBoundsException if <tt>row</tt> or <tt>column</tt> bigger than matrix
	 */
	public int get(int row, int column) {
		return matrix[row][column];
	}

}
