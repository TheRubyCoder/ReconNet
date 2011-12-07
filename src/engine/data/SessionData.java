package engine.data;

public class SessionData {

	private int id;
	private boolean isSimulation;
	private int parentId;
	
	/**
	 * SessionManager manage this ID.
	 * @param id
	 */
	public SessionData(int id){
		this.id = id;
		
		// TODO: parentId, isSimulation
	}
	
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
	 * @return the id of the Session
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * 
	 * @return if true it is a simulation, else not
	 */
	public boolean isSimulation(){
		return isSimulation;
	}
	
	/**
	 * 
	 * @return the ID of the Parent
	 */
	public int getParentId(){
		return parentId;
	}
	
}
