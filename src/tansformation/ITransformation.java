package tansformation;

import petrinetze.IPetrinet;
/**
 * An Interface for transformations on IPetrinet.
 * @author Philipp Kühn
 *
 */
public interface ITransformation 
{
	/**
	 * Returns the IRule of this transformation.
	 * @return the IRule of this transformation.
	 */
	IRule rule();

	/**
	 * Returns the IMorphism of this transformation.
	 * @return the IMorphism of this transformation.
	 */
	IMorphism morphism();
	
	/**
	 * Returns the IPetrinet of this transformation.
	 * This net will be changed when transform() is called.
	 * @return the IRule of this transformation.
	 */
	IPetrinet G();
	
	/**
	 * This will transform the IPetrinet returned by D()
	 * using the IRule returned by Rule() and
	 * the IMorphism returned by morphism().
	 */
	void transform();
}
