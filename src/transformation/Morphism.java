package transformation;

import java.util.HashMap;
import java.util.Map;

import petrinet.Arc;
import petrinet.Petrinet;
import petrinet.Place;
import petrinet.Transition;

/**
 * A morphism maps places, transitions and arcs in a way that pre and post have
 * the same "structure" in 'from' and 'to'. For more details look at documnets
 * of the petrinet course.
 */
public class Morphism {

	/**
	 * The source petrinet (left part of morphism)
	 */
	private final Petrinet from;
	/**
	 * The target petrinet (right part of morphism)
	 */
	private final Petrinet to;

	/**
	 * Morphisms between places
	 */
	private final Map<Place, Place> places;
	
	/**
	 * Morphisms between transitions
	 */
	private final Map<Transition, Transition> transitions;
	
	/**
	 * Morphisms between arcs
	 */
	private final Map<Arc, Arc> arcs;

	/**
	 * Creates a new Morphism with the given parameters
	 * 
	 * @param from
	 *            the petrinet from which this morphism starts.
	 * @param to
	 *            the petrinet into which this morphism maps to.
	 * @param places
	 *            mapping of places
	 * @param transitions
	 *            mapping of transitions
	 * @param arcs
	 *            mapping of arcs
	 */
	Morphism(Petrinet from, Petrinet to, Map<Place, Place> places,
			Map<Transition, Transition> transitions, Map<Arc, Arc> edges) {
		this.from = from;
		this.to = to;
		this.places = new HashMap<Place, Place>(places);
		this.transitions = new HashMap<Transition, Transition>(transitions);
		this.arcs = new HashMap<Arc, Arc>(edges);
	}

	/**
	 * Returns the morphisms of all transition.
	 * 
	 * @return the morphisms of all transition.
	 */
	public Map<Transition, Transition> getTransitionsMorphism() {
		return transitions;
	}

	/**
	 * Returns the morphisms of all places.
	 * 
	 * @return the morphisms of all places.
	 */
	public Map<Place, Place> getPlacesMorphism() {
		return places;
	}

	/**
	 * Returns the morphism of all arcs.
	 * 
	 * @return the morphism of all arcs.
	 */
	public Map<Arc, Arc> getArcsMorphism() {
		return arcs;
	}

	/**
	 * Returns the morphism to a single transition.
	 * 
	 * @param transition
	 *            transition in the "from" net
	 * @return the respective transition in the "to" net
	 */
	public Transition getTransitionMorphism(Transition transition) {
		return transitions.get(transition);
	}

	/**
	 * Returns the morphism to a single place.
	 * 
	 * @param place
	 *            place in the "from" net
	 * @return the respective place in the "to" net
	 */
	public Place getPlaceMorphism(Place place) {
		return places.get(place);
	}

	/**
	 * Returns the morphism to a single arc.
	 * 
	 * @param arc
	 *            arc in the "from" net
	 * @return the respective arc in the "to" net
	 */
	public Arc getArcMorphism(Arc arc) {
		return arcs.get(arc);
	}

	/**
	 * Returns the Petrinet from which this morphism starts.
	 * 
	 * @return the Petrinet from which this morphism starts.
	 */
	public Petrinet getFrom() {
		return from;
	}

	/**
	 * Returns the Petrinet into which this morphism maps to.
	 * 
	 * @return the Petrinet into which this morphism maps to.
	 */
	public Petrinet getTo() {
		return to;
	}

}
