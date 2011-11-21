package transformation;

import petrinetze.Arc;
import petrinetze.INode;
import petrinetze.Petrinet;
/**
 * An Interface for Rules<br\>
 * Rules define how a petrinet can be reconfigured<br\>
 * Where L must be found in the petrinet, K is the context of all involved nodes
 * and R is the resulting part-graph
 *
 */
public interface IRule 
{
	/**
	 * Returns the left Petrinet of this rule.
	 * @return the left Petrinet of this rule.
	 */
	public Petrinet getL();
	
	/**
	 * Returns the gluing Petrinet of this rule.
	 * @return the gluing Petrinet of this rule.
	 */
	public Petrinet getK();

	/**
	 * Returns the right Petrinet of this rule.
	 * @return the right Petrinet of this rule.
	 */
	public Petrinet getR();
	
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
	public Arc fromLtoK(Arc edge);
	
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
	public Arc fromRtoK(Arc edge);
	
	/**
	 * Returns the corresponding node in L.
	 * @param node a node in K.
	 * @return the corresponding node in L.
	 */
	public INode fromKtoL(INode node);
	
	/**
	 * Returns the corresponding  edge in L.
	 * @param edge a edge in K.
	 * @return the corresponding  edge in L.
	 */
	public Arc fromKtoL(Arc edge);
	
	/**
	 * Returns the corresponding node in R.
	 * @param node a node in K.
	 * @return the corresponding node in R.
	 */
	public INode fromKtoR(INode node);
	
	/**
	 * Returns the corresponding  edge in R.
	 * @param edge a edge in K.
	 * @return the corresponding  edge in R.
	 */
	public Arc fromKtoR(Arc edge);
}
