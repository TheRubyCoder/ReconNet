package transformation;

import java.util.HashMap;
import java.util.Map;

import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;

/**
 * A match maps places, transitions and arcs in a way that pre and post have
 * the same "structure" in 'from' and 'to'. For more details look at documents
 * of the petrinet course.
 */
public class Match {

	/**
	 * The source petrinet (left part of match)
	 */
	private final Petrinet source;
	/**
	 * The target petrinet (right part of match)
	 */
	private final Petrinet target;

	/**
	 * Matchs between places
	 */
	private final Map<Place, Place> places;
	
	/**
	 * Matchs between transitions
	 */
	private final Map<Transition, Transition> transitions;
	
	/**
	 * Matchs between arcs
	 */
	private final Map<PreArc, PreArc> preArcs;
	
	/**
	 * Matchs between arcs
	 */
	private final Map<PostArc, PostArc> postArcs;

	/**
	 * Creates a new Match with the given parameters
	 * 
	 * @param source
	 *            the petrinet from which this match starts.
	 * @param target
	 *            the petrinet into which this match maps to.
	 * @param places
	 *            mapping of places
	 * @param transitions
	 *            mapping of transitions
	 * @param preArcs
	 *            mapping of preArcs
	 * @param postArcs
	 *            mapping of postArcs
	 */
	public Match(Petrinet source, Petrinet target, Map<Place, Place> places,
			Map<Transition, Transition> transitions, Map<PreArc, PreArc> preArcs, 
			Map<PostArc, PostArc> postArcs) {
		
		this.source 	 = source;
		this.target 	 = target;
		this.places 	 = new HashMap<Place, Place>(places);
		this.transitions = new HashMap<Transition, Transition>(transitions);
		this.preArcs     = new HashMap<PreArc, PreArc>(preArcs);
		this.postArcs    = new HashMap<PostArc, PostArc>(postArcs);
	}

	/**
	 * Returns the matchs of all transition.
	 * 
	 * @return the matchs of all transition.
	 */
	public Map<Transition, Transition> getTransitions() {
		return transitions;
	}

	/**
	 * Returns the matchs of all places.
	 * 
	 * @return the matchs of all places.
	 */
	public Map<Place, Place> getPlaces() {
		return places;
	}

	/**
	 * Returns the match of all pre arcs.
	 * 
	 * @return the match of all pre arcs.
	 */
	public Map<PreArc, PreArc> getPreArcs() {
		return preArcs;
	}

	/**
	 * Returns the match of all post arcs.
	 * 
	 * @return the match of all post arcs.
	 */
	public Map<PostArc, PostArc> getPostArcs() {
		return postArcs;
	}

	/**
	 * Returns the match to a single transition.
	 * 
	 * @param transition
	 *            transition in the "from" net
	 * @return the respective transition in the "to" net
	 */
	public Transition getTransition(Transition transition) {
		return transitions.get(transition);
	}

	/**
	 * Returns the match to a single place.
	 * 
	 * @param place
	 *            place in the "from" net
	 * @return the respective place in the "to" net
	 */
	public Place getPlace(Place place) {
		return places.get(place);
	}

	/**
	 * Returns the match to a single pre arc.
	 * 
	 * @param preArc
	 *            pre arc in the "from" net
	 * @return the respective pre arc in the "to" net
	 */
	public PreArc getPreArc(PreArc preArc) {
		return preArcs.get(preArc);
	}

	/**
	 * Returns the match to a single arc.
	 * 
	 * @param postArc
	 *            post arc in the "from" net
	 * @return the respective post arc in the "to" net
	 */
	public PostArc getPostArc(PostArc postArc) {
		return postArcs.get(postArc);
	}

	/**
	 * Returns the Petrinet from which this match starts.
	 * 
	 * @return the Petrinet from which this match starts.
	 */
	public Petrinet getSource() {
		return source;
	}

	/**
	 * Returns the Petrinet into which this match maps to.
	 * 
	 * @return the Petrinet into which this match maps to.
	 */
	public Petrinet getTarget() {
		return target;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((places == null) ? 0 : places.hashCode());
		result = prime * result
				+ ((postArcs == null) ? 0 : postArcs.hashCode());
		result = prime * result + ((preArcs == null) ? 0 : preArcs.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		result = prime * result
				+ ((transitions == null) ? 0 : transitions.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Match))
			return false;
		Match other = (Match) obj;
		if (places == null) {
			if (other.places != null)
				return false;
		} else if (!places.equals(other.places))
			return false;
		if (postArcs == null) {
			if (other.postArcs != null)
				return false;
		} else if (!postArcs.equals(other.postArcs))
			return false;
		if (preArcs == null) {
			if (other.preArcs != null)
				return false;
		} else if (!preArcs.equals(other.preArcs))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		if (transitions == null) {
			if (other.transitions != null)
				return false;
		} else if (!transitions.equals(other.transitions))
			return false;
		return true;
	}
}
