package engine.ihandler;

import java.util.Collection;

import exceptions.EngineException;
import exceptions.ShowAsWarningException;

/**
 * 
 * This is a Interface for the GUI-Component for all Simulations.
 * 
 * @author alex (aas772)
 *
 */

public interface ISimulation {

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
	 */
	public void fire(int id, int n) throws EngineException;
	
	/**
	 * Saves a Petrinet.
	 * 
	 * @param id of the petrinet
	 * @param path where to save the petrinet
	 * @param filename name for the petrinet
	 * @param format which the net should be saved. (PNML the only option till now)
	 */
	public void save(int id, String path, String filename, String format) throws EngineException; // TODO: String format zu => Format format
	
	/**
	 * Applies Rules to a Petrinet
	 * 
	 * @param id of the petrinet
	 * @param ruleIDs IDs of the rules that will be used
	 * @param n how many steps should be done
	 */
	public void transform(int id,Collection<Integer> ruleIDs, int n) throws EngineException;
	
	/**
	 * Fires or Applies Rules to a Petrinet. Non Deterministic choice which is done.
	 * 
	 * @param id of the petrinet
	 * @param ruleIDs IDs of the rules that will be used
	 * @param n how many steps should be done
	 */
	public void fireOrTransform(int id, Collection<Integer> ruleIDs, int n) throws EngineException;
	// TODO: fire & transform & weitere
	
}
