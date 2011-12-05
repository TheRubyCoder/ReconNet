package engine.data;

public class SessionData {

	private int id;
	private boolean isSimulation;
	private int parentId;
	
	/**
	 * 
	 * @param id
	 * @param isSimulation
	 * @param parentId
	 */
	public SessionData(int id, boolean isSimulation, int parentId){
		this.id = id;
		this.isSimulation = isSimulation;
		this.parentId = parentId;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSimulation(){
		return isSimulation;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getParentId(){
		return parentId;
	}
	
}
