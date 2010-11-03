package tansformation;

import java.util.Map;

import petrinetze.Arc;
import petrinetze.Place;
import petrinetze.Transition;
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
	Map<Transition, Transition> transitions();
	
	/**
	 * Returns the morphisms of all places.
	 * @return the morphisms of all places.
	 */
	Map<Place, Place> places();
	
	/**
	 * Returns the morphism of all edges.
	 * @return the morphism of all edges.
	 */
	Map<Arc, Arc> edges();
	
	/**
	 * Applies this morphism to a transition.
	 * @param transition the transition to apply this morphism to.
	 * @return the transition after applying this morphism.
	 */
	Transition morph(Transition transition);

	/**
	 * Applies this morphism to a place.
	 * @param place the transition to apply this morphism to.
	 * @return the place after applying this morphism .
	 */
	Place morph(Place place);

	/**
	 * Applies this morphism to a place.
	 * @param place the transition to apply this morphism to.
	 * @return the arc after applying this morphism.
	 */
	Arc morph(Arc arc);
	

	/**
	 * Returns true if this morphism is valid.
	 * @return true if this morphism is valid.
	 */
	boolean IsValid();
}
