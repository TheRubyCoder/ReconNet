package transformation;

import java.util.List;

import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.Petrinet;

/**
 * Interface for accessing transformation component so other components do not
 * need to directly access classes within the component
 */
public interface ITransformation {

	/**
	 * Creates a new empty rule
	 * 
	 * @return
	 */
	public Rule createRule();

	/**
	 * Sets the mark of a node in a rule and modifies other parts of rule
	 * accordingly
	 * 
	 * @param rule
	 *            Rule in wich the node is included
	 * @param placeId
	 *            id of place
	 * @param mark
	 *            new value for mark
	 * @return 
	 */
	public void setMark(Rule rule, int placeId, int mark);
	
	/**
	 * Sets the name of a node in a rule and modifies other parts of the rule accordingly
	 * @param rule
	 * @param nodeId
	 * @param name
	 */
	public void setName(Rule rule, int nodeId, String name);

	/**
	 * Transformations the petrinet like defined in rule with random morphism
	 * 
	 * @param petrinet
	 *            Petrinet to transform
	 * @param rule
	 *            Rule to apply to petrinet
	 * @return the transformation that was used for transforming (containing
	 *         rule, nNet and morphism)
	 */
	Transformation transform(Petrinet net, Rule rule);

	/**
	 * Returns the respective representations of a node
	 * 
	 * @param rule
	 *            The rule in which the node lies
	 * @param node
	 *            The node that has representations
	 * @return A List with always 3 Elements:
	 *         <ul>
	 *         <li>Index 0: The Element of L-Net</li>
	 *         <li>Index 1: The Element of K-Net</li>
	 *         <li>Index 2: The Element if R-Net</li>
	 *         </ul>
	 *         Where each Element is <tt>null</tt> if there was no
	 *         representation. <br>
	 *         Returns <tt>null</tt> if the <tt>node</tt> is not in
	 *         <tt>rule</tt> <h4>
	 *         example 1</h4> <tt>rule</tt> L, K, and R has each only one place
	 *         and the parameter <tt>node</tt> refers to the node in L. The
	 *         return would be List(node of L, node of K, node of R) <h4>example
	 *         2</h4> <tt>rule</tt> has one node in each L and K, but R is empty
	 *         (deletes one node)<br>
	 *         <tt>node</tt> refers to the node in K<br>
	 *         the return would be List(node of L, node of K, <tt>null</tt>)
	 */
	List<INode> getMappings(Rule rule, INode node);

	/**
	 * Very similar to {@link ITransformation#getMappings(Rule, INode)} but
	 * with the <code>ruleId</code> instead of <tt>rule</tt>.
	 * 
	 * @see ITransformation#storeSessionId(int, Rule)
	 * @param ruleId
	 * @param node
	 * @return
	 */
	List<INode> getMappings(int ruleId, INode node);

	/**
	 * Very similar to {@link ITransformation#getMappings(Rule, INode)} but with
	 * Arc instead of INode
	 */
	List<IArc> getMappings(Rule rule, IArc arc);

	/**
	 * Stores the session id of a rule so it can be used in
	 * {@link ITransformation#getMappings(int, INode)}
	 * 
	 * @param id
	 * @param rule
	 */
	void storeSessionId(int id, Rule rule);

}
