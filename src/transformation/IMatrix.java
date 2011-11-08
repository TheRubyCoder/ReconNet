package transformation;

/**
 * A Simple Matrix with Integers in it 
 *
 */
public interface IMatrix {

	int getNumRows();
	
	
	int getNumCols();
	
	/**
	 * Returns the value at given position
	 * @param row index of row of position
	 * @param column index of column of position
	 * @return value
	 * @throws ArrayIndexOutOfBoundsException if <tt>row</tt> or <tt>column</tt> bigger than matrix
	 */
	int get(int row, int column);

	/**
	 * Sets the value at given position
	 * @param row index of row of position
	 * @param column index of column of position
	 * @param value value to set
	 * @return value
	 * @throws ArrayIndexOutOfBoundsException if <tt>row</tt> or <tt>column</tt> bigger than matrix
	 * @deprecated not implemented
	 */
	@Deprecated
	void set(int row, int column, int value);

}
