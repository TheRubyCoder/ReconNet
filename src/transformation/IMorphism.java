package transformation;

import java.util.Map;

import petrinetze.Arc;
import petrinetze.Petrinet;
import petrinetze.Place;
import petrinetze.Transition;
/**
 * An interface for morphisms on Petrinet.<br\>
 * A morphism maps places, transitions and edges in a way that pre and post
 * have the same "structure" in 'from' and 'to'. For more details look at documnets of the petrinet course. 
 * <h4>changelog</h4>
 * method isValid() deleted at 9-11-2011 because it was not implemented
 * @author Philipp Kuehn
 * @author Marvin Ede
 * @author Oliver Willhoeft
 *
 */
public interface IMorphism 
{
	/**
	 * Returns the morphisms of all transition.
	 * @return the morphisms of all transition.
	 */
	Map<Transition, Transition> getTransitionsMorphism();
	
	/**
	 * Returns the morphisms of all places.
	 * @return the morphisms of all places.
	 */
	Map<Place, Place> getPlacesMorphism();
	
	/**
	 * Returns the morphism of all edges.
	 * @return the morphism of all edges.
	 */
	Map<Arc, Arc> getEdgesMorphism();
	
	/**
	 * Returns the morphism to a single transition.
	 * @param transition transition in the "from" net
	 * @return the respective transition in the "to" net
	 */
	Transition getTransitionMorphism(Transition transition);

	/**
	 * Returns the morphism to a single place.
	 * @param place place in the "from" net
	 * @return the respective place in the "to" net
	 */
	Place getPlaceMorphism(Place place);

	/**
	 * Returns the morphism to a single arc.
	 * @param arc arc in the "from" net
	 * @return the respective arc in the "to" net
	 */
	Arc getArcMorphism(Arc arc);
	
	
	/**
	 * Returns the Petrinet from which this morphism starts.
	 * @return the Petrinet from which this morphism starts.
	 */
	Petrinet getFrom();
	
	/**
	 * Returns the Petrinet into which this morphism maps to.
	 * @return the Petrinet into which this morphism maps to.
	 */
	Petrinet getTo();
}
