package persistence;

import java.util.Map;

import petrinet.INode;
import transformation.Rule;
import engine.attribute.NodeLayoutAttribute;


/**
 * 
 * Interface between Persistance and Engine.
 * It includes two methods. A method to save and one to load. 
 * 
 * @author alex
 *
 */
public interface IPersistence {
	
	/**
	 * Saves the Petrinet to a File.
	 * @param pathAndFilename Path and Filename as String
	 * @param petrinet save this object
	 * @param nodeMap consists of INode and NodeLayoutAttribute. NodeLayoutAttribute contains a Point2D and Color.
	 * @return false if something went wrong, else 
	 */
	public boolean savePetrinet(String pathAndFilename, petrinet.Petrinet petrinet, Map<INode, NodeLayoutAttribute> nodeMap);
	
	/**
	 * Load an Petrinet.
	 * @param pathAndFilename The file to load. Consists of path and filename.
	 * @param handler an instance of the IPetrinetPersistence.
	 * @param petrinetID the ID of an existing petrinet to create the net in. 
	 * @return Id from Petrinet
	 */
	public int loadPetrinet(String pathAndFilename, engine.ihandler.IPetrinetPersistence handler, int petrinetID);
	
	/**
	 * Saves the Rule to a File.
	 * @param pathAndFilename Path and Filename as String
	 * @param rule save this object
	 * @param nodeMap consits of INode and NodeLayoutAttribute. NodeLayoutAttribute contains a Point2D and Color.
	 * @return
	 */
	public  boolean saveRule(String pathAndFilename, Rule rule, Map<INode, NodeLayoutAttribute> nodeMap);
	
	/**
	 * Load a Rule.
	 * @param pathAndFilename The file to load. Consists of path and filename.
	 * @param handler an instance of IRulePersistence.
	 * @param ruleID the ID of an existing rule to create the rule data in.
	 * @return Id from Rule
	 */
	public int loadRule(String pathAndFilename, engine.ihandler.IRulePersistence handler, int ruleID);
	
}
