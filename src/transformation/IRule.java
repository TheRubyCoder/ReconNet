package transformation;

import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPetrinet;
/**
 * An Interface for Rules<br\>
 * Rules define how a petrinet can be reconfigured<br\>
 * Where L must be found in the petrinet, K is the context of all involved nodes
 * and R is the resulting part-graph
 * @author Philipp Kuehn
 * @author Marvin Ede
 * @author Oliver Willhoeft
 *
 */
public interface IRule 
{
	/**
	 * Returns the left IPetrinet of this rule.
	 * @return the left IPetrinet of this rule.
	 */
	public IPetrinet getL();
	
	/**
	 * Returns the gluing IPetrinet of this rule.
	 * @return the gluing IPetrinet of this rule.
	 */
	public IPetrinet getK();

	/**
	 * Returns the right IPetrinet of this rule.
	 * @return the right IPetrinet of this rule.
	 */
	public IPetrinet getR();
	
	/**
	 * Returns the corresponding node in K.
	 * @param node a node in L.
	 * @return the corresponding node in K.
	 */
	public INode fromLtoK(INode node);
	
	/**
	 * Returns the corresponding edge in K.
	 * @param edge a edge in L.
	 * @return the corresponding edge in K.
	 */
	public IArc fromLtoK(IArc edge);
	
	/**
	 * Returns the corresponding node in K.
	 * @param node a node in R.
	 * @return the corresponding node in K.
	 */
	public INode fromRtoK(INode node);
	
	/**
	 * Returns the corresponding edge in K.
	 * @param edge a edge in R.
	 * @return the corresponding edge in K.
	 */
	public IArc fromRtoK(IArc edge);
	
	/**
	 * Returns the corresponding node in L.
	 * @param node a node in K.
	 * @return the corresponding node in L.
	 */
	public INode fromKtoL(INode node);
	
	/**
	 * Returns the corresponding edge in L.
	 * @param edge a edge in K.
	 * @return the corresponding edge in L.
	 */
	public IArc fromKtoL(IArc edge);
	
	/**
	 * Returns the corresponding node in R.
	 * @param node a node in K.
	 * @return the corresponding node in R.
	 */
	public INode fromKtoR(INode node);
	
	/**
	 * Returns the corresponding edge in R.
	 * @param edge a edge in K.
	 * @return the corresponding edge in R.
	 */
	public IArc fromKtoR(IArc edge);
}
