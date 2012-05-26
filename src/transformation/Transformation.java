package transformation;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import petrinet.Arc;
import petrinet.ElementType;
import petrinet.INode;
import petrinet.IRenew;
import petrinet.Petrinet;
import petrinet.Place;
import petrinet.Transition;
import exceptions.EngineException;

/**
 * An Transformation on a Petrinet<br/>
 * The Transformation applies a rule on an petrinet under a certain morphism
 */
public class Transformation {

	private final Petrinet petrinet;
	private final Morphism morphism;
	private final Rule rule;
	// New for Engine
	private Set<INode> addedNodes = null;
	private Set<INode> deletedNodes = null;
	private Set<Arc> addedArcs = null;
	private Set<Arc> deletedArcs = null;

	/**
	 * Constructor for the class Transformation
	 * 
	 * @param net
	 *            ,the petrinet to transform
	 * @param morph
	 *            , the morphism to use
	 * @param r
	 *            , the rule that should apply
	 */
	private Transformation(Petrinet petrinet, Morphism morphism, Rule rule) {
		this.petrinet = petrinet;
		this.morphism = morphism;
		this.rule = rule;

	}

	/**
	 * Creates a new Transformation with given parameters
	 * 
	 * @param petrinet
	 *            Petrinet to transform
	 * @param morphism
	 *            Morphism to use the rule under
	 * @param rule
	 *            Rule to apply to petrinet
	 * @return the transformation
	 */
	static Transformation createTransformation(Petrinet petrinet,
			Morphism morphism, Rule rule) {
		return new Transformation(petrinet, morphism, rule);
	}

	/**
	 * Creates a new Transformation with given parameters
	 * 
	 * @param petrinet
	 *            Petrinet to transform
	 * @param morphism
	 *            Morphism to use the rule under
	 * @param rule
	 *            Rule to apply to petrinet
	 * @return the transformation<br/>
	 *         <tt>null</tt>if no Morphism found
	 */
	static Transformation createTransformationWithAnyMorphism(
			Petrinet petrinet, Rule rule) {

		Morphism tempMorphism = MorphismFactory.createMorphism(rule.getL(),
				petrinet);
		// Morphism found?
		if (tempMorphism != null) {
			return new Transformation(petrinet, tempMorphism, rule);
		} else {
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
		if (contactConditionFulfilled(getPetrinet(), getRule(), getMorphism())) {
			addedNodes = new HashSet<INode>();
			deletedNodes = new HashSet<INode>();
			addedArcs = new HashSet<Arc>();
			deletedArcs = new HashSet<Arc>();
			Petrinet kNet = rule.getK();
			Set<INode> kNodes = kNet.getAllGraphElement().getAllNodes();
			Set<Arc> kArcs = kNet.getAllArcs();
			for (INode kNode : kNodes) // Add K - L Places
			{
				if (rule.fromKtoL(kNode) == null) { // If K not in L do,.....
					if (kNode instanceof Place) {
						Place n = petrinet.createPlace(kNode.getName());
						addedNodes.add(n);
						morphism.getPlacesMorphism().put((Place) kNode, n);
					} else {
						IRenew rnw = ((Transition) kNode).getRnw();
						Transition n = petrinet.createTransition(
								kNode.getName(), rnw);
						addedNodes.add(n);
						morphism.getTransitionsMorphism().put(
								(Transition) kNode, n);
					}
				} else {
					// just add the nodes to create d
					if (kNode instanceof Place) {
						morphism.getPlacesMorphism().put(
								(Place) kNode,
								morphism.getPlaceMorphism((Place) rule
										.fromKtoL(kNode)));
					} else {
						morphism.getTransitionsMorphism()
								.put((Transition) kNode,
										morphism.getTransitionMorphism((Transition) rule
												.fromKtoL(kNode)));
					}
				}
			}
			for (Arc a : kArcs) { // Add K - L Arcs
				if (rule.fromKtoL(a) == null) {
					if (a.getStart() instanceof Place) {
						Arc n = petrinet
								.createArc(
										a.getName(),
										morphism.getPlaceMorphism((Place) a
												.getStart()),
										morphism.getTransitionMorphism((Transition) a
												.getEnd()));
						addedArcs.add(n);
						morphism.getEdgesMorphism().put(a, n);
					} else {
						Arc n = petrinet.createArc(a.getName(), morphism
								.getTransitionMorphism((Transition) a
										.getStart()), morphism
								.getPlaceMorphism((Place) a.getEnd()));
						addedArcs.add(n);
						morphism.getEdgesMorphism().put(a, n);
					}
				} else {
					// just add the edges to create d
					morphism.getEdgesMorphism().put(a,
							morphism.getArcMorphism(rule.fromKtoL(a)));
				}
			}
			for (INode i : kNodes) // Delete K - R Places and check for contact
									// condition
			{
				if (rule.fromKtoR(i) == null) {
					if (i instanceof Place) {
						deletedNodes.add(morphism.getPlaceMorphism((Place) i));
						petrinet.deletePlaceById(morphism.getPlaceMorphism(
								(Place) i).getId());
					} else {
						deletedNodes.add(morphism
								.getTransitionMorphism((Transition) i));
						petrinet.deleteTransitionByID(morphism
								.getTransitionMorphism((Transition) i).getId());
					}
				}
			}
			for (Arc i : kArcs) { // Delete K - R Arcs
				if (rule.fromKtoR(i) == null) {
					deletedArcs.add(i);
					petrinet.deleteArcByID(morphism.getArcMorphism(i).getId());
				}
			}
			return this;
		} else {
			throw new EngineException("Kontaktbedingung verletzt");
		}
	}

	/**
	 * Returns <tt>true</tt> if the contact condition for <tt>node</tt> is
	 * fulfilled. Which means: Are all incident Arcs of <tt>node</tt> also
	 * mapped in the morphism?
	 */
	private boolean contactConditionFulfilled(INode node, Morphism morphism,
			Petrinet toNet, Petrinet fromNet) {
		ElementType nodeType = fromNet.getNodeType(node.getId());
		INode mappedNode = null;
		if (nodeType == ElementType.PLACE) {
			mappedNode = morphism.getPlaceMorphism((Place) node);
		} else {
			mappedNode = morphism.getTransitionMorphism((Transition) node);
		}
		List<Arc> incidentArcs = toNet.getIncidetenArcsByNodeId(mappedNode
				.getId());
		for (Arc arc : incidentArcs) {
			if (!morphism.getEdgesMorphism().containsValue(arc)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns <tt>true</tt> if the contact condition for all Nodes in K-R is
	 * fulfilled.
	 * 
	 * @see {link
	 *      {@link Transformation#contactConditionFulfilled(INode, Morphism, Petrinet)}
	 * @param petrinet
	 * @param rule
	 * @param morphism
	 * @return
	 */
	private boolean contactConditionFulfilled(Petrinet petrinet, Rule rule,
			Morphism morphism) {
		List<INode> lWithoutR = new LinkedList<INode>();
		lWithoutR.addAll(rule.getL().getAllPlaces());
		lWithoutR.addAll(rule.getL().getAllTransitions());
		lWithoutR.removeAll(rule.getR().getAllPlaces());
		lWithoutR.removeAll(rule.getR().getAllTransitions());
		for (INode node : lWithoutR) {
			if (!contactConditionFulfilled(node, getMorphism(), getPetrinet(),
					getRule().getL())) {
				return false;
			}
		}
		return true;

	}

	public Set<INode> getAddedNodes() {
		if (addedNodes == null) {
			return new HashSet<INode>();
		} else {
			return addedNodes;
		}
	}

	public Set<INode> getDeletedNodes() {
		if (deletedNodes == null) {
			return new HashSet<INode>();
		} else {
			return deletedNodes;
		}
	}

	public Set<Arc> getAddedArcs() {
		if (addedArcs == null) {
			return new HashSet<Arc>();
		} else {
			return addedArcs;
		}
	}

	public Set<Arc> getDeletedArcs() {
		if (deletedArcs == null) {
			return new HashSet<Arc>();
		} else {
			return deletedArcs;
		}
	}

}