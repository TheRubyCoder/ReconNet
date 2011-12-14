package persistence;

import java.util.Map;

import petrinet.INode;
import engine.attribute.NodeLayoutAttribute;


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
	 * @param nodeMap consists of INode and NodeLayoutAttribute. NodeLayoutAttribute contains a Point2D, Color.
	 * @return false if something went wrong, else 
	 */
	public boolean save(String pathAndFilename, petrinet.Petrinet petrinet, Map<INode, NodeLayoutAttribute> nodeMap);
	
	/**
	 * Load an Petrinet.
	 * @param pathAndFilename The File to load. Consists of path and filename.
	 * @param handler A instance of the IPetrinetHandler
	 * @return Id from Petrinet
	 */
	public int load(String pathAndFilename, engine.ihandler.IPetrinetPersistence handler);
	
}
