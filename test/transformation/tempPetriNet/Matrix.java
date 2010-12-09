package transformation.tempPetriNet;


public interface Matrix {


	public int getNumRows();
	
	
	public int getNumCols();
	
	
	public short get(int row, int col);


	public void set(int row, int col, short value);
	


}
