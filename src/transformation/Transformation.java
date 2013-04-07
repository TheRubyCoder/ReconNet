package transformation;

import java.util.HashSet;
import java.util.Set;

import petrinet.model.INode;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;
import transformation.matcher.*;
import transformation.matcher.PNVF2.MatchException;
import exceptions.EngineException;

/**
 * An Transformation on a Petrinet<br/>
 * The Transformation applies a rule on an petrinet under a certain match
 */
public class Transformation {
	protected final static class CheckContactConditionFulfilledMatchVisitor implements PNVF2.MatchVisitor {		
		private Petrinet petrinet;
		private Rule     rule;
				
		public CheckContactConditionFulfilledMatchVisitor(Petrinet petrinet, Rule rule) {
			this.petrinet = petrinet;
			this.rule     = rule;			
		}

		@Override
		public boolean visit(Match match) {
			return true;
		//	return contactConditionFulfilled(this.petrinet, this.rule, match);
		}		
	}

	private final Petrinet petrinet;
	private final Rule     rule;
	private final Match    match;
	
	// New for Engine
	private Set<Place> addedPlaces   = null;
	private Set<Place> deletedPlaces = null;
	
	private Set<Transition> addedTransitions   = null;
	private Set<Transition> deletedTransitions = null;

	private Set<PreArc>   addedPreArcs     = null;
	private Set<PreArc>   deletedPreArcs   = null;

	private Set<PostArc>  addedPostArcs    = null;
	private Set<PostArc>  deletedPostArcs  = null;

	/**
	 * Constructor for the class Transformation
	 * 
	 * @param net    the petrinet to transform
	 * @param morph  the match to use
	 * @param rule   the rule that should apply
	 */
	private Transformation(Petrinet petrinet, Match match, Rule rule) {
		this.petrinet = petrinet;
		this.match    = match;
		this.rule     = rule;
	}

	/**
	 * Creates a new Transformation with given parameters
	 * 
	 * @param  petrinet   Petrinet to transform
	 * @param  match      Match to use the rule under
	 * @param  rule       Rule to apply to petrinet
	 * @return the transformation
	 */
	static Transformation createTransformation(Petrinet petrinet,
			Match match, Rule rule) {
		
		return new Transformation(petrinet, match, rule);
	}

	/**
	 * Creates a new Transformation with given parameters
	 * 
	 * @param petrinet   Petrinet to transform
	 * @param match      Match to use the rule under
	 * @param rule       Rule to apply to petrinet
	 * 
	 * @return the transformation<br/>
	 *         <tt>null</tt>if no Match found
	 */
	static Transformation createTransformationWithAnyMatch(
			Petrinet petrinet, Rule rule) {
		
		//VF2.MatchVisitor visitor = new CheckContactConditionFulfilledMatchVisitor(petrinet, rule);
		
		//Match match = Ullmann.createMatch(rule.getL(), petrinet);
		
		try {
			Match match = PNVF2.getInstance(rule.getL(), petrinet).getMatch(false, rule.getPlacesToDelete());
			return new Transformation(petrinet, match, rule);			
		} catch (MatchException e) {
			return null;
		}		
	}

	/**
	 * Returns the Petrinet of this transformation. This net will be changed
	 * when transform() is called.
	 * 
	 * @return the Rule of this transformation.
	 */
	public Petrinet getPetrinet() {
		return petrinet;
	}

	/**
	 * Returns the Match of this transformation.
	 * 
	 * @return the Match of this transformation.
	 */
	public Match getMatch() {
		return match;
	}

	/**
	 * Returns the Rule of this transformation.
	 * 
	 * @return the Rule of this transformation.
	 */
	public Rule getRule() {
		return rule;
	}

	/**
	 * This will transform the petrinet using the Rule returned by getRule() and
	 * the Match returned by getMatch().
	 * 
	 * @return the Transformation that was used (<tt>this</tt>)
	 * @throws EngineException
	 *             When contact condition is not fulfilled
	 */
	Transformation transform() throws EngineException {
		if (!contactConditionFulfilled(getPetrinet(), getRule(), getMatch())) {
			throw new EngineException("Kontaktbedingung verletzt");
		}

		addedPlaces        = new HashSet<Place>();
		deletedPlaces      = new HashSet<Place>();		
		
		addedTransitions   = new HashSet<Transition>();
		deletedTransitions = new HashSet<Transition>();
		
		addedPreArcs       = new HashSet<PreArc>();
		deletedPreArcs     = new HashSet<PreArc>();
		
		addedPostArcs      = new HashSet<PostArc>();
		deletedPostArcs    = new HashSet<PostArc>();		
		
		Petrinet      kNet = rule.getK();
		
		// Add new places, map k to these new Places
		for (Place placeToAdd : rule.getPlacesToAdd()) {			
			Place newPlace = petrinet.addPlace(placeToAdd.getName());
			addedPlaces.add(newPlace);
			match.getPlaces().put(rule.fromRtoK(placeToAdd), newPlace);
		}

		// Add new transitions, map k to these new Places
		for (Transition transitionToAdd : rule.getTransitionsToAdd()) {			
			Transition newTransition = petrinet.addTransition(transitionToAdd.getName(), transitionToAdd.getRnw());			
			addedTransitions.add(newTransition);
			match.getTransitions().put(rule.fromRtoK(transitionToAdd), newTransition);
		}

		// map remaining old K places to the match of L
		for (Place place : kNet.getPlaces()) {
			if (match.getPlaces().get(place) == null) {
				match.getPlaces().put(
					place,
					match.getPlace(rule.fromKtoL(place))
				);
			}
		}

		// map remaining old K transitions to the match of L
		for (Transition transition : kNet.getTransitions()) {
			if (match.getTransitions().get(transition) == null) {
				match.getTransitions().put(
					transition,
					match.getTransition(rule.fromKtoL(transition))
				);
			}
		}
		
		// Add new preArcs, map k preArcs to these 
		for (PreArc preArcToAdd : rule.getPreArcsToAdd()) {	
			PreArc newPreArc = petrinet.addPreArc(
				preArcToAdd.getName(),
				match.getPlace(rule.fromRtoK(preArcToAdd.getPlace())),
				match.getTransition(rule.fromRtoK(preArcToAdd.getTransition()))
			);
			addedPreArcs.add(newPreArc);
			match.getPreArcs().put(preArcToAdd, newPreArc);
		}

		// Add new postArcs, map k preArcs to these
		for (PostArc postArcToAdd : rule.getPostArcsToAdd()) {	
			PostArc newPostArc = petrinet.addPostArc(
				postArcToAdd.getName(),
				match.getTransition(rule.fromRtoK(postArcToAdd.getTransition())),
				match.getPlace(rule.fromRtoK(postArcToAdd.getPlace()))
			);
			addedPostArcs.add(newPostArc);
			match.getPostArcs().put(postArcToAdd, newPostArc);
		}

		// map remaining old K preArcs to the match of L
		for (PreArc preArc : kNet.getPreArcs()) {
			if (match.getPreArcs().get(preArc) == null) {
				match.getPreArcs().put(
					preArc,
					match.getPreArc(rule.fromKtoL(preArc))
				);
			}
		}

		// map remaining old K postArcs to the match of L
		for (PostArc postArc : kNet.getPostArcs()) {
			if (match.getPostArcs().get(postArc) == null) {
				match.getPostArcs().put(
					postArc,
					match.getPostArc(rule.fromKtoL(postArc))
				);
			}
		}


		// Delete K - R Places
		for (Place placeToDelete : rule.getPlacesToDelete()) {
			Place deletedPlace = match.getPlace(rule.fromLtoK(placeToDelete));
			deletedPlaces.add(deletedPlace);				
			petrinet.removePlace(deletedPlace);				
		}

		// Delete K - R Transitions
		for (Transition transitionToDelete : rule.getTransitionsToDelete()) {
			Transition deletedTransition = match.getTransition(rule.fromLtoK(transitionToDelete));
			deletedTransitions.add(deletedTransition);				
			petrinet.removeTransition(deletedTransition);				
		}
		
		// Deleted K - R PreArcs
		for (PreArc preArcToDelete : rule.getPreArcsToDelete()) {
			PreArc deletedArc = match.getPreArc(rule.fromLtoK(preArcToDelete));

			deletedPreArcs.add(deletedArc);	
			
			if (petrinet.containsPreArc(deletedArc)) {			
				petrinet.removeArc(deletedArc);
			}				
		}
		
		// Deleted K - R PostArcs
		for (PostArc postArcToDelete : rule.getPostArcsToDelete()) {	
			PostArc deletedArc = match.getPostArc(rule.fromLtoK(postArcToDelete));	

			deletedPostArcs.add(deletedArc);	
			
			if (petrinet.containsPostArc(deletedArc)) {			
				petrinet.removeArc(deletedArc);
			}				
		}

		return this;
	}

	/**
	 * Returns <tt>true</tt> if the contact condition for <tt>node</tt> is
	 * fulfilled. Which means: Are all incident Arcs of <tt>node</tt> also
	 * mapped in the match?
	 */
	private static boolean contactConditionFulfilled(Place place, Match match,
			Petrinet toNet, Petrinet fromNet) {
		
		Place mappedPlace = match.getPlace(place);
		
		for (PostArc arc : mappedPlace.getIncomingArcs()) {
			if (!match.getPostArcs().containsValue(arc)) {
				return false;
			}
		}

		for (PreArc arc : mappedPlace.getOutgoingArcs()) {
			if (!match.getPreArcs().containsValue(arc)) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Returns <tt>true</tt> if the contact condition for <tt>node</tt> is
	 * fulfilled. Which means: Are all incident Arcs of <tt>node</tt> also
	 * mapped in the match?
	 */
	private static boolean contactConditionFulfilled(Transition transition, Match match,
			Petrinet toNet, Petrinet fromNet) {
		
		Transition mappedTransition = match.getTransition(transition);

		for (PreArc arc : mappedTransition.getIncomingArcs()) {
			if (!match.getPreArcs().containsValue(arc)) {
				return false;
			}
		}

		for (PostArc arc : mappedTransition.getOutgoingArcs()) {
			if (!match.getPostArcs().containsValue(arc)) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Returns <tt>true</tt> if the contact condition for all Nodes in K-R is
	 * fulfilled.
	 * 
	 * @see   {link {@link Transformation#contactConditionFulfilled(INode, Match, Petrinet)}
	 * @param petrinet
	 * @param rule
	 * @param match
	 * @return
	 */
	private static boolean contactConditionFulfilled(Petrinet petrinet, Rule rule,
			Match match) {

		for (Place place : rule.getPlacesToDelete()) {
			if (!contactConditionFulfilled(place, match, petrinet, rule.getL())) {
				return false;
			}
		}

		for (Transition transition : rule.getTransitionsToDelete()) {
			if (!contactConditionFulfilled(transition, match, petrinet, rule.getL())) {
				return false;
			}
		}
		
		return true;

	}

	public Set<Place> getAddedPlaces() {
		if (addedPlaces == null) {
			return new HashSet<Place>();
		} 
			
		return addedPlaces;
	}

	public Set<Place> getDeletedPlaces() {
		if (deletedPlaces == null) {
			return new HashSet<Place>();
		} 
			
		return deletedPlaces;
	}

	public Set<Transition> getAddedTransitions() {
		if (addedTransitions == null) {
			return new HashSet<Transition>();
		} 
			
		return addedTransitions;
	}

	public Set<Transition> getDeletedTransitions() {
		if (deletedTransitions == null) {
			return new HashSet<Transition>();
		} 
			
		return deletedTransitions;
	}

	public Set<PreArc> getAddedPreArcs() {
		if (addedPreArcs == null) {
			return new HashSet<PreArc>();
		}

		return addedPreArcs;
	}

	public Set<PreArc> getDeletedPreArcs() {
		if (deletedPreArcs == null) {
			return new HashSet<PreArc>();
		}

		return deletedPreArcs;
	}

	public Set<PostArc> getAddedPostArcs() {
		if (addedPostArcs == null) {
			return new HashSet<PostArc>();
		}

		return addedPostArcs;
	}

	public Set<PostArc> getDeletedPostArcs() {
		if (deletedPostArcs == null) {
			return new HashSet<PostArc>();
		}

		return deletedPostArcs;
	}
}