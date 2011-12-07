package persistence;

import petrinet.INode;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;

/**
 * 
 * Interface between Persistance and Engine.
 * It includes two methods. A method to save and one to load. 
 * 
 * @author alex
 *
 */
public interface IPersistance {
	
	/**
	 * Save saves the Petrinet to a File.
	 * @param pathAndFilename Path and Filename as String
	 * @param petrinet save this object
	 * @param layout of this Petrinet
	 */
	public void save(String pathAndFilename, petrinet.Petrinet petrinet, AbstractLayout<INode, Arc> layout);
	
	/**
	 * Load an Petrinet.
	 * @param pathAndFilename The File to load. Consists of path and filename.
	 * @param handler A instance of the IPetrinetHandler
	 */
	public void load(String pathAndFilename, engine.ihandler.IPetrinetHandler handler);
	
}
