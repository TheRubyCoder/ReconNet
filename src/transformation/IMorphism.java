package transformation;

import java.util.Map;

import petrinetze.IArc;
import petrinetze.IPetrinet;
import petrinetze.IPlace;
import petrinetze.ITransition;
/**
 * An interface for morphisms on IPetrinet.
 * @author Philipp Kühn
 *
 */
public interface IMorphism 
{
	/**
	 * Returns the morphisms of all transition.
	 * @return the morphisms of all transition.
	 */
	Map<ITransition, ITransition> transitions();
	
	/**
	 * Returns the morphisms of all places.
	 * @return the morphisms of all places.
	 */
	Map<IPlace, IPlace> places();
	
	/**
	 * Returns the morphism of all edges.
	 * @return the morphism of all edges.
	 */
	Map<IArc, IArc> edges();
	
	/**
	 * Applies this morphism to a transition.
	 * @param transition the transition to apply this morphism to.
	 * @return the transition after applying this morphism.
	 */
	ITransition morph(ITransition transition);

	/**
	 * Applies this morphism to a place.
	 * @param place the transition to apply this morphism to.
	 * @return the place after applying this morphism .
	 */
	IPlace morph(IPlace place);

	/**
	 * Applies this morphism to a place.
	 * @param place the transition to apply this morphism to.
	 * @return the arc after applying this morphism.
	 */
	IArc morph(IArc arc);
	

	/**
	 * Returns true if this morphism is valid.
	 * @return true if this morphism is valid.
	 */
	boolean IsValid();
	
	/**
	 * Returns the Petrinet from which this morphism starts.
	 * @return the Petrinet from which this morphism starts.
	 */
	IPetrinet From();
	
	/**
	 * Returns the Petrinet into which this morphism maps to.
	 * @return the Petrinet into which this morphism maps to.
	 */
	IPetrinet To();
}
