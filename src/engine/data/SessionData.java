package engine.data;

public class SessionData {

	private int id;
	private boolean isSimulation;
	private int parentId;
	
	public SessionData(int id, boolean isSimulation, int parentId){
		this.id = id;
		this.isSimulation = isSimulation;
		this.parentId = parentId;
	}
	
	public int getId(){
		return id;
	}
	
	public boolean isSimulation(){
		return isSimulation;
	}
	
	public int getParentId(){
		return parentId;
	}
	
}
