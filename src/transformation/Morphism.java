package transformation;

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
	
	
	public Morphism(IPetrinet from, IPetrinet to, Map<IPlace, IPlace> places,
			Map<ITransition, ITransition> transitions, Map<IArc, IArc> edges) {
		this.from = from;
		this.to = to;
		this.places = places;
		this.transitions = transitions;
		this.edges = edges;
	}


	@Override
	public Map<ITransition, ITransition> transitions() {
		return transitions;
	}
	

	@Override
	public Map<IPlace, IPlace> places() {
		return places;
	}


	@Override
	public Map<IArc, IArc> edges() {
		return edges;
	}


	@Override
	public ITransition morph(ITransition transition) {
		return transitions.get(transition);
	}


	@Override
	public IPlace morph(IPlace place) {
		return places.get(place);
	}


	@Override
	public IArc morph(IArc arc) {
		return edges.get(arc);
	}


	@Override
	public boolean IsValid() {
		throw new UnsupportedOperationException();
	}


	@Override
	public IPetrinet From() {
		return from;
	}


	@Override
	public IPetrinet To() {
		return to;
	}
	
	
}
