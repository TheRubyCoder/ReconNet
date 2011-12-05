package engine.ihandler;

public interface ISimulationHandler {

	/**
	 * 
	 * @param id
	 * @return
	 */
	public int createSimulationSession(int id);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean fire(int id);
	
	/**
	 * 
	 * @param id
	 * @param path
	 * @param filename
	 * @param format
	 */
	public void save(int id, String path, String filename, String format); // TODO: String format zu => Format format
	
	// TODO: fire & transform & weitere
	
}
