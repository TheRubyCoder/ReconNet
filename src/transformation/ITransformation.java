package transformation;

import petrinetze.IPetrinet;
/**
 * An Interface for transformations on IPetrinet.<br\>
 * The Transformation applies a rule on an petrinet under a certain morphism
 * 
 * @author Philipp Kuehn
 * @author Marvin Ede
 * @author Oliver Willhoeft
 *
 */
public interface ITransformation 
{
	/**
	 * Returns the IRule of this transformation.
	 * @return the IRule of this transformation.
	 */
	IRule getRule();

	/**
	 * Returns the IMorphism of this transformation.
	 * @return the IMorphism of this transformation.
	 */
	IMorphism getMorphism();
	
	/**
	 * Returns the IPetrinet of this transformation.
	 * This net will be changed when transform() is called.
	 * @return the IRule of this transformation.
	 */
	IPetrinet getPetrinet();
	
	/**
	 * This will transform the petrinet
	 * using the IRule returned by getRule() and
	 * the IMorphism returned by getMorphism().
	 * @return the Transformation that was used (this)
	 */
	ITransformation transform();
}
