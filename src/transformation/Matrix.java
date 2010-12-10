package transformation;


public interface Matrix {


	public int getNumRows();
	
	
	public int getNumCols();
	
	
	public int get(int row, int col);


	public void set(int row, int col, int value);
	


}
