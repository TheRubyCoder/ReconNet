package transformation;

import java.util.HashMap;
import java.util.Map;

import petrinetze.IArc;
import petrinetze.IPetrinet;
import petrinetze.IPlace;
import petrinetze.ITransition;

class Morphism implements IMorphism {

	private final IPetrinet from;
	private final IPetrinet to;
	
	
	private final Map<IPlace, IPlace> places;
	private final Map<ITransition, ITransition> transitions;
	private final Map<IArc, IArc> edges;
	
	/**
	 * Creates a new Morphism with the given parameters
	 * @param from the petrinet from which this morphism starts.
	 * @param to the petrinet into which this morphism maps to.
	 * @param places mapping of places
	 * @param transitions mapping of transitions
	 * @param edges mapping of edges
	 */
	public Morphism(IPetrinet from, IPetrinet to, 
			Map<IPlace, IPlace> places,
			Map<ITransition, ITransition> transitions, 
			Map<IArc, IArc> edges) {
		this.from = from;
		this.to = to;
		this.places = new HashMap<IPlace, IPlace>(places);
		this.transitions = new HashMap<ITransition, ITransition>(transitions);
		this.edges = new HashMap<IArc, IArc>(edges);
	}


	/**
	 * @see transformation.IMorphism#getTransitionsMorphism()
	 */
	@Override
	public Map<ITransition, ITransition> getTransitionsMorphism() {
		return transitions;
	}
	
	/**
	 * @see transformation.IMorphism#getPlacesMorphism()
	 */
	@Override
	public Map<IPlace, IPlace> getPlacesMorphism() {
		return places;
	}

	/**
	 * @see transformation.IMorphism#getEdgesMorphism()
	 */
	@Override
	public Map<IArc, IArc> getEdgesMorphism() {
		return edges;
	}

	/**
	 * @see transformation.IMorphism#getTransitionMorphism()
	 */
	@Override
	public ITransition getTransitionMorphism(ITransition transition) {
		return transitions.get(transition);
	}

	/**
	 * @see transformation.IMorphism#getPlaceMorphism()
	 */
	@Override
	public IPlace getPlaceMorphism(IPlace place) {
		return places.get(place);
	}

	/**
	 * @see transformation.IMorphism#getArcMorphism()
	 */
	@Override
	public IArc getArcMorphism(IArc arc) {
		return edges.get(arc);
	}


	/**
	 * @see transformation.IMorphism#getFrom()
	 */
	@Override
	public IPetrinet getFrom() {
		return from;
	}

	/**
	 * @see transformation.IMorphism#getTo()
	 */
	@Override
	public IPetrinet getTo() {
		return to;
	}
	
	
}
