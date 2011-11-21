package transformation;

import java.util.HashMap;
import java.util.Map;

import petrinetze.Arc;
import petrinetze.Petrinet;
import petrinetze.Place;
import petrinetze.Transition;

class Morphism implements IMorphism {

	private final Petrinet from;
	private final Petrinet to;
	
	
	private final Map<Place, Place> places;
	private final Map<Transition, Transition> transitions;
	private final Map<Arc, Arc> edges;
	
	/**
	 * Creates a new Morphism with the given parameters
	 * @param from the petrinet from which this morphism starts.
	 * @param to the petrinet into which this morphism maps to.
	 * @param places mapping of places
	 * @param transitions mapping of transitions
	 * @param edges mapping of edges
	 */
	public Morphism(Petrinet from, Petrinet to, 
			Map<Place, Place> places,
			Map<Transition, Transition> transitions, 
			Map<Arc, Arc> edges) {
		this.from = from;
		this.to = to;
		this.places = new HashMap<Place, Place>(places);
		this.transitions = new HashMap<Transition, Transition>(transitions);
		this.edges = new HashMap<Arc, Arc>(edges);
	}


	/**
	 * @see transformation.IMorphism#getTransitionsMorphism()
	 */
	@Override
	public Map<Transition, Transition> getTransitionsMorphism() {
		return transitions;
	}
	
	/**
	 * @see transformation.IMorphism#getPlacesMorphism()
	 */
	@Override
	public Map<Place, Place> getPlacesMorphism() {
		return places;
	}

	/**
	 * @see transformation.IMorphism#getEdgesMorphism()
	 */
	@Override
	public Map<Arc, Arc> getEdgesMorphism() {
		return edges;
	}

	/**
	 * @see transformation.IMorphism#getTransitionMorphism()
	 */
	@Override
	public Transition getTransitionMorphism(Transition transition) {
		return transitions.get(transition);
	}

	/**
	 * @see transformation.IMorphism#getPlaceMorphism()
	 */
	@Override
	public Place getPlaceMorphism(Place place) {
		return places.get(place);
	}

	/**
	 * @see transformation.IMorphism#getArcMorphism()
	 */
	@Override
	public Arc getArcMorphism(Arc arc) {
		return edges.get(arc);
	}


	/**
	 * @see transformation.IMorphism#getFrom()
	 */
	@Override
	public Petrinet getFrom() {
		return from;
	}

	/**
	 * @see transformation.IMorphism#getTo()
	 */
	@Override
	public Petrinet getTo() {
		return to;
	}
	
	
}
