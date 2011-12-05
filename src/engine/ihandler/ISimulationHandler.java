package engine.ihandler;

import java.util.Collection;

public interface ISimulationHandler {

	/**
	 * 
	 * @param id of the petrinet
	 * @return 
	 */
	public int createSimulationSession(int id);
	
	/**
	 * Fires a Petrinet with n steps.
	 * 
	 * @param id of the petrinet
	 * @param n how many steps should be done
	 * @return <code> true </code> if simulation was successful, <code> false </code> otherwise 
	 */
	public boolean fire(int id, int n);
	
	/**
	 * Saves a Petrinet.
	 * 
	 * @param id of the petrinet
	 * @param path where to save the petrinet
	 * @param filename name for the petrinet
	 * @param format which the net should be saved. (PNML the only option till now)
	 */
	public void save(int id, String path, String filename, String format); // TODO: String format zu => Format format
	
	/**
	 * Applies Rules to a Petrinet
	 * 
	 * @param id of the petrinet
	 * @param ruleIDs IDs of the rules that will be used
	 * @param n how many steps should be done
	 * @return <code> true </code> if simulation was successful, <code> false </code> otherwise
	 */
	public boolean transform(int id,Collection<Integer> ruleIDs, int n);
	
	/**
	 * Fires or Applies Rules to a Petrinet. Non Deterministic choice which is done.
	 * 
	 * @param id of the petrinet
	 * @param ruleIDs IDs of the rules that will be used
	 * @param n how many steps should be done
	 * @return <code> true </code> if simulation was successful, <code> false </code> otherwise
	 */
	public boolean fireOrTransform(int id, Collection<Integer> ruleIDs, int n);
	// TODO: fire & transform & weitere
	
}
