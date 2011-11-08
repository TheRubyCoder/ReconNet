package transformation;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * A simple matrix with Integers in it 
 */
class MatrixImpl implements IMatrix {

	/** Matrix is internally realized as a nested array */
	private final int[][] matrix;
	
	
	public MatrixImpl(int[][] matrix) {
		this.matrix = matrix;
	}
	
	
	@Override
	public int getNumRows() {
		return matrix.length;
	}

	@Override
	public int getNumCols() {
		if(matrix.length == 0)
			return 0;
		return matrix[0].length;
	}

	/**
	 * @see IMatrix#get(int, int)
	 */
	@Override
	public int get(int row, int column) {
		return matrix[row][column];
	}

	/**
	 * @see IMatrix#set(int, int, int)
	 */
	@Override
	public void set(int row, int col, int value) {
		throw new NotImplementedException();
	}

}
