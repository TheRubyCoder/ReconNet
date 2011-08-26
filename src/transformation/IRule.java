package transformation;

import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPetrinet;
/**
 * An Interface for Rules.
 * @author Philipp Kuehn
 *
 */
public interface IRule 
{
	/**
	 * Returns the left IPetrinet of this rule.
	 * @return the left IPetrinet of this rule.
	 */
	public IPetrinet L();
	
	/**
	 * Returns the gluing IPetrinet of this rule.
	 * @return the gluing IPetrinet of this rule.
	 */
	public IPetrinet K();

	/**
	 * Returns the right IPetrinet of this rule.
	 * @return the right IPetrinet of this rule.
	 */
	public IPetrinet R();
	
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
