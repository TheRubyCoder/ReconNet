package transformation;

import java.util.HashSet;
import java.util.Set;

import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;
import exceptions.EngineException;

/**
 * An Transformation on a Petrinet<br/>
 * The Transformation applies a rule on an petrinet under a certain morphism
 */
public class Transformation {

	private final Petrinet petrinet;
	private final Morphism morphism;
	private final Rule     rule;
	
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
	 * @param morph  the morphism to use
	 * @param rule   the rule that should apply
	 */
	private Transformation(Petrinet petrinet, Morphism morphism, Rule rule) {
		this.petrinet = petrinet;
		this.morphism = morphism;
		this.rule     = rule;
	}

	/**
	 * Creates a new Transformation with given parameters
	 * 
	 * @param petrinet   Petrinet to transform
	 * @param morphism   Morphism to use the rule under
	 * @param rule       Rule to apply to petrinet
	 * @return the transformation
	 */
	static Transformation createTransformation(Petrinet petrinet,
			Morphism morphism, Rule rule) {
		
		return new Transformation(petrinet, morphism, rule);
	}

	/**
	 * Creates a new Transformation with given parameters
	 * 
	 * @param petrinet   Petrinet to transform
	 * @param morphism   Morphism to use the rule under
	 * @param rule       Rule to apply to petrinet
	 * 
	 * @return the transformation<br/>
	 *         <tt>null</tt>if no Morphism found
	 */
	static Transformation createTransformationWithAnyMorphism(
			Petrinet petrinet, Rule rule) {

		Morphism tempMorphism = MorphismFactory.createMorphism(rule.getL(),
				petrinet);
		
		// no Morphism found?
		if (tempMorphism == null) {
			return null;			
		}
		
		return new Transformation(petrinet, tempMorphism, rule);
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
	 * Returns the Morphism of this transformation.
	 * 
	 * @return the Morphism of this transformation.
	 */
	public Morphism getMorphism() {
		return morphism;
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
	 * the Morphism returned by getMorphism().
	 * 
	 * @return the Transformation that was used (<tt>this</tt>)
	 * @throws EngineException
	 *             When contact condition is not fulfilled
	 */
	Transformation transform() throws EngineException {
		if (!contactConditionFulfilled(getPetrinet(), getRule(), getMorphism())) {
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
			morphism.getPlacesMorphism().put(rule.fromRtoK(placeToAdd), newPlace);
		}

		// Add new transitions, map k to these new Places
		for (Transition transitionToAdd : rule.getTransitionsToAdd()) {			
			Transition newTransition = petrinet.addTransition(transitionToAdd.getName(), transitionToAdd.getRnw());			
			addedTransitions.add(newTransition);
			morphism.getTransitionsMorphism().put(rule.fromRtoK(transitionToAdd), newTransition);
		}

		// map remaining old K places to the match of L
		for (Place place : kNet.getPlaces()) {
			if (morphism.getPlacesMorphism().get(place) == null) {
				morphism.getPlacesMorphism().put(
					place,
					morphism.getPlaceMorphism(rule.fromKtoL(place))
				);
			}
		}

		// map remaining old K transitions to the match of L
		for (Transition transition : kNet.getTransitions()) {
			if (morphism.getTransitionsMorphism().get(transition) == null) {
				morphism.getTransitionsMorphism().put(
					transition,
					morphism.getTransitionMorphism(rule.fromKtoL(transition))
				);
			}
		}
		
		// Add new preArcs, map k preArcs to these 
		for (PreArc preArcToAdd : rule.getPreArcsToAdd()) {	
			PreArc newPreArc = petrinet.addPreArc(
				preArcToAdd.getName(),
				morphism.getPlaceMorphism(rule.fromRtoK(preArcToAdd.getPlace())),
				morphism.getTransitionMorphism(rule.fromRtoK(preArcToAdd.getTransition()))
			);
			addedPreArcs.add(newPreArc);
			morphism.getArcsMorphism().put(preArcToAdd, newPreArc);
		}

		// Add new postArcs, map k preArcs to these
		for (PostArc postArcToAdd : rule.getPostArcsToAdd()) {	
			PostArc newPostArc = petrinet.addPostArc(
				postArcToAdd.getName(),
				morphism.getTransitionMorphism(rule.fromRtoK(postArcToAdd.getTransition())),
				morphism.getPlaceMorphism(rule.fromRtoK(postArcToAdd.getPlace()))
			);
			addedPostArcs.add(newPostArc);
			morphism.getArcsMorphism().put(postArcToAdd, newPostArc);
		}

		// map remaining old K preArcs to the match of L
		for (PreArc preArc : kNet.getPreArcs()) {
			if (morphism.getArcsMorphism().get(preArc) == null) {
				morphism.getArcsMorphism().put(
					preArc,
					morphism.getArcMorphism(rule.fromKtoL(preArc))
				);
			}
		}

		// map remaining old K postArcs to the match of L
		for (PostArc postArc : kNet.getPostArcs()) {
			if (morphism.getArcsMorphism().get(postArc) == null) {
				morphism.getArcsMorphism().put(
					postArc,
					morphism.getArcMorphism(rule.fromKtoL(postArc))
				);
			}
		}


		// Delete K - R Places
		for (Place placeToDelete : rule.getPlacesToDelete()) {
			Place deletedPlace = morphism.getPlaceMorphism(rule.fromLtoK(placeToDelete));
			deletedPlaces.add(deletedPlace);				
			petrinet.removePlace(deletedPlace);				
		}

		// Delete K - R Transitions
		for (Transition transitionToDelete : rule.getTransitionsToDelete()) {
			Transition deletedTransition = morphism.getTransitionMorphism(rule.fromLtoK(transitionToDelete));
			deletedTransitions.add(deletedTransition);				
			petrinet.removeTransition(deletedTransition);				
		}
		
		// Deleted K - R PreArcs
		for (PreArc preArcToDelete : rule.getPreArcsToDelete()) {
			PreArc deletedArc = (PreArc) morphism.getArcMorphism(rule.fromLtoK(preArcToDelete));

			deletedPreArcs.add(deletedArc);	
			
			if (petrinet.containsPreArc(deletedArc)) {			
				petrinet.removeArc(deletedArc);
			}				
		}
		
		// Deleted K - R PostArcs
		for (PostArc postArcToDelete : rule.getPostArcsToDelete()) {	
			PostArc deletedArc = (PostArc) morphism.getArcMorphism(rule.fromLtoK(postArcToDelete));	

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
	 * mapped in the morphism?
	 */
	private boolean contactConditionFulfilled(Place place, Morphism morphism,
			Petrinet toNet, Petrinet fromNet) {
		
		Place      mappedPlace   = morphism.getPlaceMorphism(place);
		
		for (IArc arc : mappedPlace.getIncomingArcs()) {
			if (!morphism.getArcsMorphism().containsValue(arc)) {
				return false;
			}
		}

		for (IArc arc : mappedPlace.getOutgoingArcs()) {
			if (!morphism.getArcsMorphism().containsValue(arc)) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Returns <tt>true</tt> if the contact condition for <tt>node</tt> is
	 * fulfilled. Which means: Are all incident Arcs of <tt>node</tt> also
	 * mapped in the morphism?
	 */
	private boolean contactConditionFulfilled(Transition transition, Morphism morphism,
			Petrinet toNet, Petrinet fromNet) {
		
		Transition mappedTransition = morphism.getTransitionMorphism(transition);

		for (IArc arc : mappedTransition.getIncomingArcs()) {
			if (!morphism.getArcsMorphism().containsValue(arc)) {
				return false;
			}
		}

		for (IArc arc : mappedTransition.getOutgoingArcs()) {
			if (!morphism.getArcsMorphism().containsValue(arc)) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Returns <tt>true</tt> if the contact condition for all Nodes in K-R is
	 * fulfilled.
	 * 
	 * @see   {link {@link Transformation#contactConditionFulfilled(INode, Morphism, Petrinet)}
	 * @param petrinet
	 * @param rule
	 * @param morphism
	 * @return
	 */
	private boolean contactConditionFulfilled(Petrinet petrinet, Rule rule,
			Morphism morphism) {

		for (Place place : rule.getPlacesToDelete()) {
			if (!contactConditionFulfilled(place, getMorphism(), getPetrinet(), getRule().getL())) {
				return false;
			}
		}

		for (Transition transition : rule.getTransitionsToDelete()) {
			if (!contactConditionFulfilled(transition, getMorphism(), getPetrinet(), getRule().getL())) {
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