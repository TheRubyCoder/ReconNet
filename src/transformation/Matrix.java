package transformation;


public interface Matrix {

	int getNumRows();
	
	
	int getNumCols();
	
	
	int get(int row, int col);


	void set(int row, int col, int value);

}
