package tansformation;

import petrinetze.IPetrinet;
/**
 * An Interface for Rules.
 * @author Philipp Kühn
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
}
