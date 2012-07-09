package engine.handler.rule;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.List;
import java.util.Map;

import persistence.Persistence;
import petrinet.Arc;
import petrinet.INode;
import petrinet.IRenew;
import petrinet.Petrinet;
import petrinet.Place;
import petrinet.Transition;
import transformation.Rule;
import transformation.TransformationComponent;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.NodeLayoutAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.RuleAttribute;
import engine.attribute.TransitionAttribute;
import engine.data.JungData;
import engine.data.RuleData;
import engine.handler.NodeTypeEnum;
import engine.handler.RuleNet;
import engine.ihandler.IRuleManipulation;
import engine.ihandler.IRulePersistence;
import engine.session.SessionManager;
import exceptions.EngineException;
import exceptions.ShowAsWarningException;

/**
 * This is the implementation of all methods regarding rules by engine.
 * 
 * @see IRuleManipulation
 * @see IRulePersistence
 */
final public class RuleHandler {

	/** Session manager of engine */
	private final SessionManager sessionManager;
	/** Singleton instance of this class */
	private static RuleHandler ruleManipulation;

	private RuleHandler() {
		sessionManager = SessionManager.getInstance();
	}

	protected static RuleHandler getInstance() {
		if (ruleManipulation == null) {
			ruleManipulation = new RuleHandler();
		}
		return ruleManipulation;
	}

	/**
	 * Creates an Arc in the rule referenced by <code>id</code>
	 * 
	 * @param id
	 *            Id of the rule
	 * @param net
	 *            Part of the rule the arc will be added to
	 * @param from
	 *            Node to start the edge at
	 * @param to
	 *            Target node of edge
	 * @return Created Arc
	 * @throws EngineException
	 *             if id is wrong or illegal node combination
	 */
	public Arc createArc(int id, RuleNet net, INode from, INode to)
			throws EngineException {

		RuleData ruleData = sessionManager.getRuleData(id);

		if (ruleData == null) {
			exception("createArc - id of the Rule is wrong");
			return null;
		} else {
			Rule rule = ruleData.getRule();
			Petrinet petrinet = null;
			JungData lJungData = ruleData.getLJungData();
			JungData kJungData = ruleData.getKJungData();
			JungData rJungData = ruleData.getRJungData();

			ensureLegalNodeCombination(net, from, to, rule);

			if (net.equals(RuleNet.L)) {
				petrinet = rule.getL();
			} else if (net.equals(RuleNet.K)) {
				petrinet = rule.getK();
			} else if (net.equals(RuleNet.R)) {
				petrinet = rule.getR();
			}

			// create Arc in defined map
			Arc createdArc = petrinet.createArc("undefined", from, to);

			// find arcs that were added automatically
			List<Arc> arcMappings = TransformationComponent.getTransformation()
					.getMappings(rule, createdArc);
			// add those arcs into the corresponding jung data
			for (int i = 0; i <= 2; i++) {
				Arc correspondingArc = arcMappings.get(i);
				if (correspondingArc != null) {
					JungData correspondingJungData;
					if (i == 0) {
						correspondingJungData = lJungData;
					} else if (i == 1) {
						correspondingJungData = kJungData;
					} else {
						correspondingJungData = rJungData;
					}
					// find out which if the to JungData.createArc methods is
					// the right one and cast the arguments
					INode start = correspondingArc.getStart();
					INode end = correspondingArc.getEnd();
					if (start instanceof Place) {
						correspondingJungData.createArc(correspondingArc,
								(Place) start, (Transition) end);
					} else {
						correspondingJungData.createArc(correspondingArc,
								(Transition) start, (Place) end);
					}
				}
			}
			return createdArc;

			// The following is the old method for adding arcs. Its still here
			// in case the above version does not work as well as expected
			//
			// if (net.equals(RuleNet.L)) {
			// // Manipulation in L
			// //
			// petrinet = rule.getL();
			//
			// if (this.getNodeType(from).equals(NodeTypeEnum.Place)
			// && this.getNodeType(to).equals(NodeTypeEnum.Transition)) {
			// // place => transition
			//
			// // cast objects
			// Place fromPlace = (Place) from;
			// Transition toTransition = (Transition) to;
			//
			// // create new Arc
			// Arc arc = petrinet.createArc("undefined", fromPlace,
			// toTransition);
			//
			// // call Jung
			// try {
			// lJungData.createArc(arc, fromPlace, toTransition);
			// } catch (IllegalArgumentException e) {
			// exception("createArc - can not create Arc in L");
			// }
			//
			// // get automatically added Corresponding Arc in K
			// Arc newArcInK = rule.fromLtoK(arc);
			//
			// // Add this Arc into the JungData of K
			// if (newArcInK != null) {
			// Place fromInK = (Place) newArcInK.getStart();
			// Transition toInK = (Transition) newArcInK.getEnd();
			// try {
			// kJungData.createArc(newArcInK, fromInK, toInK);
			// } catch (IllegalArgumentException e) {
			// exception("createArc - can not create Arc in K");
			// }
			// }
			//
			// return arc;
			// } else if (this.getNodeType(from).equals(
			// NodeTypeEnum.Transition)
			// && this.getNodeType(to).equals(NodeTypeEnum.Place)) {
			// // transition => place
			//
			// // cast objects
			// Transition fromTransition = (Transition) from;
			// Place toPlace = (Place) to;
			//
			// // create new Arc
			// Arc arc = petrinet.createArc("undefined", fromTransition,
			// toPlace);
			//
			// // call Jung
			// try {
			// lJungData.createArc(arc, fromTransition, toPlace);
			// } catch (IllegalArgumentException e) {
			// exception("createArc - can not create Arc");
			// }
			//
			// // get automatically added Corresponding Arc in K
			// Arc newArcInK = rule.fromLtoK(arc);
			//
			// // Add this Arc into the JungData of K
			// if (newArcInK != null) {
			// Transition fromInK = (Transition) newArcInK.getStart();
			// Place toInK = (Place) newArcInK.getEnd();
			// try {
			// kJungData.createArc(newArcInK, fromInK, toInK);
			// } catch (IllegalArgumentException e) {
			// exception("createArc - can not create Arc");
			// }
			// }
			// return arc;
			// } else {
			// exception("createArc - wrong combi");
			//
			// return null;
			// }
			//
			// } else if (net.equals(RuleNet.K)) {
			// // Manipulation in K
			// // Get Petrinet and corresponding JungData
			// petrinet = rule.getK();
			//
			// if (rule.fromKtoL(from) == null || rule.fromKtoL(to) == null) {
			// exception("Arc not possible in L");
			// }
			// if (rule.fromKtoR(from) == null || rule.fromKtoR(to) == null) {
			// exception("Arc not possible in R");
			// }
			//
			// if (this.getNodeType(from).equals(NodeTypeEnum.Place)
			// && this.getNodeType(to).equals(NodeTypeEnum.Transition)) {
			// // place => transition
			//
			// // cast objects
			// Place fromPlace = (Place) from;
			// Transition toTransition = (Transition) to;
			//
			// // create new Arc
			// Arc arc = petrinet.createArc("undefined", fromPlace,
			// toTransition);
			//
			// // call Jung
			// try {
			// kJungData.createArc(arc, fromPlace, toTransition);
			// } catch (IllegalArgumentException e) {
			// exception("createArc - can not create Arc in K");
			// }
			//
			// // get automatically added Corresponding Arc in L and R
			// Arc newArcInL = rule.fromKtoL(arc);
			// Arc newArcInR = rule.fromKtoR(arc);
			//
			// // Add this Arc into the JungData of L and R
			// if (newArcInL != null && newArcInR != null) {
			// Place fromInL = (Place) newArcInL.getStart();
			// Transition toInL = (Transition) newArcInL.getEnd();
			// Place fromInR = (Place) newArcInR.getStart();
			// Transition toInR = (Transition) newArcInR.getEnd();
			// try {
			// lJungData.createArc(newArcInL, fromInL, toInL);
			// rJungData.createArc(newArcInR, fromInR, toInR);
			// } catch (IllegalArgumentException e) {
			// exception("createArc - can not create Arc in L or R");
			// }
			// }
			//
			// return arc;
			// } else if (this.getNodeType(from).equals(
			// NodeTypeEnum.Transition)
			// && this.getNodeType(to).equals(NodeTypeEnum.Place)) {
			// // transition => place
			//
			// // cast objects
			// Transition fromTransition = (Transition) from;
			// Place toPlace = (Place) to;
			//
			// // create new Arc
			// Arc arc = petrinet.createArc("undefined", fromTransition,
			// toPlace);
			//
			// // call Jung
			// try {
			// kJungData.createArc(arc, fromTransition, toPlace);
			// } catch (IllegalArgumentException e) {
			// exception("createArc - can not create Arc in K");
			// }
			//
			// // get automatically added Corresponding Arc in K
			// Arc newArcInL = rule.fromKtoL(arc);
			// Arc newArcInR = rule.fromKtoR(arc);
			//
			// // Add this Arc into the JungData of K
			// if (newArcInL != null && newArcInR != null) {
			// Transition fromInL = (Transition) newArcInL.getStart();
			// Place toInL = (Place) newArcInL.getEnd();
			// Transition fromInR = (Transition) newArcInR.getStart();
			// Place toInR = (Place) newArcInR.getEnd();
			// try {
			// lJungData.createArc(newArcInL, fromInL, toInL);
			// rJungData.createArc(newArcInR, fromInR, toInR);
			// } catch (IllegalArgumentException e) {
			// e.printStackTrace();
			// exception("createArc - can not create Arc in L or R");
			// }
			// }
			// return arc;
			// } else {
			// exception("createArc - wrong combi");
			//
			// return null;
			// }
			//
			// } else if (net.equals(RuleNet.R)) {
			// // Manipulation in R
			// // Get Petrinet and corresponding JungData
			// petrinet = rule.getR();
			//
			// if (this.getNodeType(from).equals(NodeTypeEnum.Place)
			// && this.getNodeType(to).equals(NodeTypeEnum.Transition)) {
			// // place => transition
			//
			// // cast objects
			// Place fromPlace = (Place) from;
			// Transition toTransition = (Transition) to;
			//
			// // create new Arc
			// Arc arc = petrinet.createArc("undefined", fromPlace,
			// toTransition);
			//
			// // call Jung
			// try {
			// rJungData.createArc(arc, fromPlace, toTransition);
			// } catch (IllegalArgumentException e) {
			// exception("createArc - can not create Arc in R");
			// }
			//
			// // get automatically added Corresponding Arc in K
			// Arc newArcInK = rule.fromRtoK(arc);
			//
			// // Add this Arc into the JungData of K
			// if (newArcInK != null) {
			// Place fromInK = (Place) newArcInK.getStart();
			// Transition toInK = (Transition) newArcInK.getEnd();
			// try {
			// kJungData.createArc(newArcInK, fromInK, toInK);
			// } catch (IllegalArgumentException e) {
			// exception("createArc - can not create Arc in K");
			// }
			// }
			//
			// return arc;
			// } else if (this.getNodeType(from).equals(
			// NodeTypeEnum.Transition)
			// && this.getNodeType(to).equals(NodeTypeEnum.Place)) {
			// // transition => place
			//
			// // cast objects
			// Transition fromTransition = (Transition) from;
			// Place toPlace = (Place) to;
			//
			// // create new Arc
			// Arc arc = petrinet.createArc("undefined", fromTransition,
			// toPlace);
			//
			// // call Jung
			// try {
			// rJungData.createArc(arc, fromTransition, toPlace);
			// } catch (IllegalArgumentException e) {
			// exception("createArc - can not create Arc in R");
			// }
			//
			// // get automatically added Corresponding Arc in K
			// Arc newArcInK = rule.fromRtoK(arc);
			//
			// // Add this Arc into the JungData of K
			// if (newArcInK != null) {
			// Transition fromInK = (Transition) newArcInK.getStart();
			// Place toInK = (Place) newArcInK.getEnd();
			// try {
			// kJungData.createArc(newArcInK, fromInK, toInK);
			// } catch (IllegalArgumentException e) {
			// exception("createArc - can not create Arc in K");
			// }
			// }
			// return arc;
			//
			// } else {
			// exception("createArc - wrong combi");
			//
			// return null;
			// }
			//
			// } else {
			// exception("createArc - Not given if Manipulation is in L,K or R");
			// return null;
			// }

		}

	}

	/**
	 * Checks whether a combination of start and taget nodes is valid for
	 * creating an arc in the rule
	 * 
	 * @param net
	 * @param from
	 * @param to
	 * @param rule
	 * @throws ShowAsWarningException
	 *             with human friendly text message if combination is illegal
	 */
	private void ensureLegalNodeCombination(RuleNet net, INode from, INode to,
			Rule rule) {
		if (net.equals(RuleNet.K)) {
			if (rule.fromKtoL(from) == null) {
				throw new ShowAsWarningException(
						"Startknoten in L nicht verfügbar");
			} else if (rule.fromKtoL(to) == null) {
				throw new ShowAsWarningException(
						"Zielknoten in L nicht verfügbar");
			} else if (rule.fromKtoR(from) == null) {
				throw new ShowAsWarningException(
						"Startknoten in R nicht verfügbar");
			} else if (rule.fromKtoR(to) == null) {
				throw new ShowAsWarningException(
						"Zielknoten in R nicht verfügbar");
			}
		}
	}

	/**
	 * @see IRuleManipulation#createPlace(int, RuleNet, Point2D)
	 */
	public INode createPlace(int id, RuleNet net, Point2D coordinate)
			throws EngineException {

		RuleData ruleData = sessionManager.getRuleData(id);

		if (ruleData == null) {
			exception("createPlace - id of the Rule is wrong");
			return null;
		} else {
			Rule rule = ruleData.getRule();
			Petrinet petrinet = null;
			JungData lJungData = ruleData.getLJungData();
			JungData kJungData = ruleData.getKJungData();
			JungData rJungData = ruleData.getRJungData();
			if (!lJungData.isCreatePossibleAt(coordinate)) {
				exception("Place too close to Node in L");
			}
			if (!kJungData.isCreatePossibleAt(coordinate)) {
				exception("Place too close to Node in K");
			}
			if (!rJungData.isCreatePossibleAt(coordinate)) {
				exception("Place too close to Node in R");
			}

			if (net.equals(RuleNet.L)) {
				// Manipulation in L
				//
				petrinet = rule.getL();

				// create a new Place
				Place newPlace = petrinet.createPlace("undefined");

				// call JungModificator
				try {
					lJungData.createPlace(newPlace, coordinate);
				} catch (IllegalArgumentException e) {
					exception("createPlace - can not create Place in L");
				}

				// get automatically added Corresponding Place in K
				Place newPlaceInK = (Place) rule.fromLtoK(newPlace);
				if (newPlaceInK != null) {
					try {
						kJungData.createPlace(newPlaceInK, coordinate);
					} catch (IllegalArgumentException e) {
						exception("createPlace - can not create Place in K");
					}
				}

				setPlaceColor(id, newPlace, ruleData.getColorGenerator().next());

				return newPlace;

			} else if (net.equals(RuleNet.K)) {
				// Manipulation in K
				//
				petrinet = rule.getK();

				// create a new Place
				Place newPlace = petrinet.createPlace("undefined");

				// call JungModificator
				try {
					kJungData.createPlace(newPlace, coordinate);
				} catch (IllegalArgumentException e) {
					exception("createPlace - can not create Place in K");
				}

				// get automatically added Corresponding Place in L and R
				Place newPlaceInL = (Place) rule.fromKtoL(newPlace);
				Place newPlaceInR = (Place) rule.fromKtoR(newPlace);
				if (newPlaceInL != null && newPlaceInR != null) {
					try {
						lJungData.createPlace(newPlaceInL, coordinate);
						rJungData.createPlace(newPlaceInR, coordinate);
					} catch (IllegalArgumentException e) {
						exception("createPlace - can not create Place in K");
					}
				}

				setPlaceColor(id, newPlace, ruleData.getColorGenerator().next());
				return newPlace;

			} else if (net.equals(RuleNet.R)) {
				// Manipulation in R
				// Get Petrinet and corresponding JungData
				petrinet = rule.getR();

				// create a new Place
				Place newPlace = petrinet.createPlace("undefined");

				// call JungModificator
				try {
					rJungData.createPlace(newPlace, coordinate);
				} catch (IllegalArgumentException e) {
					exception("createPlace - can not create Place in R");
				}

				// get automatically added Corresponding Place in K
				Place newPlaceInK = (Place) rule.fromRtoK(newPlace);
				if (newPlaceInK != null) {
					try {
						kJungData.createPlace(newPlaceInK, coordinate);
					} catch (IllegalArgumentException e) {
						exception("createPlace - can not create Place in K");
					}
				}
				setPlaceColor(id, newPlace, ruleData.getColorGenerator().next());

				return newPlace;

			} else {
				exception("createPlace - Not given if Manipulation is in L,K or R");
				return null;
			}

		}

	}

	/**
	 * @see IRuleManipulation#createRule()
	 */
	public int createRule() {

		Rule rule = TransformationComponent.getTransformation().createRule();

		RuleData ruleData = sessionManager.createRuleData(rule);

		TransformationComponent.getTransformation().storeSessionId(
				ruleData.getId(), rule);

		return ruleData.getId();
	}

	/**
	 * @see IRuleManipulation#createTransition(int, RuleNet, Point2D)
	 */
	public INode createTransition(int id, RuleNet net, Point2D coordinate)
			throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("createTransition - id of the Rule is wrong");
			return null;
		} else {
			Rule rule = ruleData.getRule();
			Petrinet petrinet = null;
			JungData lJungData = ruleData.getLJungData();
			JungData kJungData = ruleData.getKJungData();
			JungData rJungData = ruleData.getRJungData();

			if (!lJungData.isCreatePossibleAt(coordinate)) {
				exception("Transition too close to Node in L");
			}
			if (!kJungData.isCreatePossibleAt(coordinate)) {
				exception("Transition too close to Node in K");
			}
			if (!rJungData.isCreatePossibleAt(coordinate)) {
				exception("Transition too close to Node in R");
			}

			if (net.equals(RuleNet.L)) {
				// Manipulation in L
				//
				petrinet = rule.getL();

				// create a new Place
				Transition newTransition = petrinet
						.createTransition("undefined");

				// call JungModificator
				try {
					lJungData.createTransition(newTransition, coordinate);
				} catch (IllegalArgumentException e) {
					exception("createTransition - can not create Transition in L");
				}

				// get automatically added Corresponding Place in K
				Transition newTransitionInK = (Transition) rule
						.fromLtoK(newTransition);
				if (newTransitionInK != null) {
					try {
						kJungData
								.createTransition(newTransitionInK, coordinate);
					} catch (IllegalArgumentException e) {
						exception("createPlace - can not create Transition in K");
					}
				}

				return newTransition;

			} else if (net.equals(RuleNet.K)) {
				// Manipulation in K
				//
				petrinet = rule.getK();

				// create a new Transition
				Transition newTransition = petrinet
						.createTransition("undefined");

				// call JungModificator
				try {
					kJungData.createTransition(newTransition, coordinate);
				} catch (IllegalArgumentException e) {
					exception("createTransition - can not create Transition in K");
				}

				// get automatically added Corresponding Transition in L and R
				Transition newTransitionInL = (Transition) rule
						.fromKtoL(newTransition);
				Transition newTransitionInR = (Transition) rule
						.fromKtoR(newTransition);
				if (newTransitionInL != null && newTransitionInR != null) {
					try {
						lJungData
								.createTransition(newTransitionInL, coordinate);
						rJungData
								.createTransition(newTransitionInR, coordinate);
					} catch (IllegalArgumentException e) {
						exception("createTransition - can not create Transition in L or R");
					}
				}

				return newTransition;

			} else if (net.equals(RuleNet.R)) {
				// Manipulation in R
				//
				petrinet = rule.getR();

				// create a new Transition
				Transition newTransition = petrinet
						.createTransition("undefined");

				// call JungModificator
				try {
					rJungData.createTransition(newTransition, coordinate);
				} catch (IllegalArgumentException e) {
					exception("createTransition - can not create Transition in R");
				}

				// get automatically added Corresponding Transition in K
				Transition newTransitionInK = (Transition) rule
						.fromRtoK(newTransition);
				if (newTransitionInK != null) {
					try {
						kJungData
								.createTransition(newTransitionInK, coordinate);
					} catch (IllegalArgumentException e) {
						exception("createTransition - can not create Transition in K");
					}
				}

				return newTransition;

			} else {
				exception("createTransition - Not given if Manipulation is in L,K or R");
				return null;
			}

		}

	}

	/**
	 * @see IRuleManipulation#deleteArc(int, RuleNet, Arc)
	 */
	public void deleteArc(int id, RuleNet net, Arc arc) throws EngineException {
		deleteInternal(id, net, arc);
	}

	/**
	 * @see IRuleManipulation#deletePlace(int, RuleNet, INode)
	 */
	public void deletePlace(int id, RuleNet net, INode place)
			throws EngineException {
		deleteInternal(id, net, place);
	}

	/**
	 * @see IRuleManipulation#deleteTransition(int, RuleNet, INode)
	 */
	public void deleteTransition(int id, RuleNet net, INode transition)
			throws EngineException {
		deleteInternal(id, net, transition);
	}

	/**
	 * Deletes the node from the rule with <code>id</code> in part
	 * <code>net</code>. This is a refactored method as for deletion its not
	 * important if a node is a place or a transition
	 * 
	 * @throws EngineException
	 */
	private void deleteInternal(int id, RuleNet net, INode node)
			throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("deleteInternal - id of the Rule is wrong");
		} else {
			Rule rule = ruleData.getRule();

			rule.removeNodeOrArc(node);

			ruleData.deleteDataOfMissingElements(rule);
		}
	}

	/**
	 * @see IRuleManipulation#getArcAttribute(int, Arc)
	 */
	public ArcAttribute getArcAttribute(int id, Arc arc) {

		int weight = arc.getMark();

		ArcAttribute arcAttribute = new ArcAttribute(weight);

		return arcAttribute;

	}

	/**
	 * @see IRuleManipulation#getJungLayout(int, RuleNet)
	 */
	public AbstractLayout<INode, Arc> getJungLayout(int id, RuleNet net)
			throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("getJungLayout - id of the Rule is wrong");
		} else {
			// Rule rule = ruleData.getRule();
			JungData jungData = null;

			if (net.equals(RuleNet.L)) {
				// Manipulation in L
				// Get JungData
				jungData = ruleData.getLJungData();
			} else if (net.equals(RuleNet.K)) {
				// Manipulation in K
				// Get JungData
				jungData = ruleData.getKJungData();
			} else if (net.equals(RuleNet.R)) {
				// Manipulation in R
				// Get JungData
				jungData = ruleData.getRJungData();
			} else {
				exception("getJungLayout - Not given if Manipulation is in L,K or R");
			}
			return jungData.getJungLayout();
		}

		return null;
	}

	/**
	 * @see IRuleManipulation#getPlaceAttribute(int, INode)
	 */
	public PlaceAttribute getPlaceAttribute(int id, INode place)
			throws EngineException {

		if (this.getNodeType(place).equals(NodeTypeEnum.Place)) {
			Place p = (Place) place;

			int marking = p.getMark();
			String pname = p.getName();

			RuleData ruleData = sessionManager.getRuleData(id);
			RuleNet containingNet = getContainingNet(id, place);
			JungData petrinetData = null;
			switch (containingNet) {
			case L:
				petrinetData = ruleData.getLJungData();
				break;
			case K:
				petrinetData = ruleData.getKJungData();
				break;
			case R:
				petrinetData = ruleData.getRJungData();
				break;
			}
			Color color = null;
			try {
				color = petrinetData.getPlaceColor(p);
			} catch (IllegalArgumentException ex) {
				color = Color.gray;
			}

			PlaceAttribute placeAttribute = new PlaceAttribute(marking, pname,
					color);

			return placeAttribute;
		}

		exception("getPlaceAttribute - the INode value is not a Place");
		return null;
	}

	/**
	 * @see IRuleManipulation#getTransitionAttribute(int, INode)
	 */
	public TransitionAttribute getTransitionAttribute(int id, INode transition)
			throws EngineException {

		if (this.getNodeType(transition).equals(NodeTypeEnum.Transition)) {
			Transition t = (Transition) transition;

			String tlb = t.getTlb();
			String tname = t.getName();
			IRenew rnw = t.getRnw();

			boolean isActivated = t.isActivated();

			TransitionAttribute transitionAttribute = new TransitionAttribute(
					tlb, tname, rnw, isActivated);

			return transitionAttribute;
		}

		exception("getPlaceAttribute - the INode value is not a Transition");
		return null;
	}

	/**
	 * @see IRuleManipulation#getRuleAttribute(int)
	 */
	public RuleAttribute getRuleAttribute(int id) throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("getRuleAttribute - id of the rule is wrong");
		} else {
			// get all Id's from L, K, R
			int lId = ruleData.getRule().getL().getId();
			int kId = ruleData.getRule().getK().getId();
			int rId = ruleData.getRule().getR().getId();

			// create a RuleAttribute
			RuleAttribute ruleAttribute = new RuleAttribute(lId, kId, rId);

			return ruleAttribute;
		}

		return null;
	}

	/**
	 * @see IRuleManipulation#moveNode(int, INode, Point2D)
	 */
	public void moveNode(int id, INode node, Point2D relativePosition)
			throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("moveNode - id of the rule is wrong");
		} else {
			// get Position

			ruleData.moveNodeRelative(node, relativePosition);
		}

	}

	/**
	 * @see IRuleManipulation#save(int, String, String, String)
	 */
	public void save(int id, String path, String filename, String format)
			throws EngineException {

		// get the Petrinet from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("save - id of the Petrinet is wrong");
		} else {

			Rule rule = ruleData.getRule();

			// Petrinet petrinet = ruleData.getPetrinet();
			JungData jungDataL = ruleData.getLJungData();
			JungData jungDataK = ruleData.getKJungData();
			JungData jungDataR = ruleData.getRJungData();

			Map<INode, NodeLayoutAttribute> nodeMapL = jungDataL
					.getNodeLayoutAttributes();
			Map<INode, NodeLayoutAttribute> nodeMapK = jungDataK
					.getNodeLayoutAttributes();
			Map<INode, NodeLayoutAttribute> nodeMapR = jungDataR
					.getNodeLayoutAttributes();

			checkNodeLayoutAttribute(nodeMapL == null,
					"save - nodeMapL == null");
			checkNodeLayoutAttribute(nodeMapK == null,
					"save - nodeMapK == null");
			checkNodeLayoutAttribute(nodeMapR == null,
					"save - nodeMapR == null");

			Persistence.saveRule(path + "/" + filename + "." + format, rule,
					nodeMapL, nodeMapK, nodeMapR, ruleData.getKJungData()
							.getNodeSize());

		}

	}

	/**
	 * @see IRuleManipulation#load(String, String)
	 */
	public int load(String path, String filename) {

		return Persistence.loadRule(path + "/" + filename,
				RulePersistence.getInstance());

	}

	/**
	 * @see IRuleManipulation#setMarking(int, INode, int)
	 */
	public void setMarking(int id, INode place, int marking)
			throws EngineException {

		// get the Petrinet from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("setMarking - id of the rule is wrong");
		} else {

			Rule rule = ruleData.getRule();
			if (this.getNodeType(place).equals(NodeTypeEnum.Place)) {
				TransformationComponent.getTransformation().setMark(rule,
						place.getId(), marking);
			}

		}

	}

	/**
	 * @see IRuleManipulation#setPname(int, INode, String)
	 */
	public void setPname(int id, INode place, String pname)
			throws EngineException {
		setNodeName(id, place, pname);
	}

	/**
	 * Sets the name of a node and its counterparts in the other parts of a rule
	 * @throws EngineException if ruleId is wrong
	 */
	private void setNodeName(int ruleId, INode node, String name) throws EngineException {
		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(ruleId);

		// Test: is id valid
		if (ruleData == null) {
			exception("setPname - id of the rule is wrong");
		} else {
			ruleData.getRule().setName(node.getId(), name);
		}

	}


	/**
	 * @see IRuleManipulation#setPlaceColor(int, INode, Color)
	 */
	public void setPlaceColor(int id, INode place, Color color)
			throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("setColor - id of the Rule is wrong");
		} else {
			Rule rule = ruleData.getRule();
			JungData lJungData = ruleData.getLJungData();
			JungData kJungData = ruleData.getKJungData();
			JungData rJungData = ruleData.getRJungData();
			if (this.getNodeType(place).equals(NodeTypeEnum.Place)) {
				List<INode> mappings = TransformationComponent
						.getTransformation().getMappings(rule, place);
				INode nodeInL = mappings.get(0);
				INode nodeInK = mappings.get(1);
				INode nodeInR = mappings.get(2);
				if (nodeInL != null) {
					lJungData.setPlaceColor((Place) nodeInL, color);
				}
				if (nodeInK != null) {
					kJungData.setPlaceColor((Place) nodeInK, color);
				}
				if (nodeInR != null) {
					rJungData.setPlaceColor((Place) nodeInR, color);
				}
			}
		}
	}

	/**
	 * @see IRuleManipulation#setTlb(int, INode, String)
	 */
	public void setTlb(int id, INode transition, String tlb)
			throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("setTlb - id of the rule is wrong");
		} else {

			Rule rule = ruleData.getRule();
			if (this.getNodeType(transition).equals(NodeTypeEnum.Transition)) {
				// cast object
				Transition t = (Transition) transition;

				// set new Tlb
				t.setTlb(tlb);

				// Synchronize Transitions in the other parts of the rules
				RuleNet net = getContainingNet(id, transition);
				if (net.equals(RuleNet.L)) {
					Transition transitionInK = (Transition) rule.fromLtoK(t);
					Transition transitionInR = (Transition) rule
							.fromKtoR(transitionInK);
					transitionInK.setTlb(tlb);
					if (transitionInR != null) {
						transitionInR.setTlb(tlb);
					}
				} else if (net.equals(RuleNet.K)) {
					Transition transitionInL = (Transition) rule.fromKtoL(t);
					Transition transitionInR = (Transition) rule.fromKtoR(t);
					if (transitionInL != null) {
						transitionInL.setTlb(tlb);
					}
					if (transitionInR != null) {
						transitionInR.setTlb(tlb);
					}
				} else if (net.equals(RuleNet.R)) {
					Transition transitionInK = (Transition) rule.fromRtoK(t);
					Transition transitionInL = (Transition) rule
							.fromKtoL(transitionInK);
					transitionInK.setTlb(tlb);
					if (transitionInL != null) {
						transitionInL.setTlb(tlb);
					}
				}

			}

		}

	}

	/**
	 * @see IRuleManipulation#setTname(int, INode, String)
	 */
	public void setTname(int id, INode transition, String tname)
			throws EngineException {
		setNodeName(id, transition, tname);
	}

	/**
	 * @see IRuleManipulation#setRnw(int, INode, IRenew)
	 */
	public void setRnw(int id, INode transition, IRenew renew)
			throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("setWeight - id of the Rule is wrong");
		} else {
			Rule rule = ruleData.getRule();
			if (this.getNodeType(transition).equals(NodeTypeEnum.Transition)) {
				Transition t = (Transition) transition;

				t.setRnw(renew);

				// Synchronize Transitions in the other parts of the rules
				RuleNet net = getContainingNet(id, transition);
				if (net.equals(RuleNet.L)) {
					Transition transitionInK = (Transition) rule.fromLtoK(t);
					Transition transitionInR = (Transition) rule
							.fromKtoR(transitionInK);
					transitionInK.setRnw(renew);
					if (transitionInR != null) {
						transitionInR.setRnw(renew);
					}
				} else if (net.equals(RuleNet.K)) {
					Transition transitionInL = (Transition) rule.fromKtoL(t);
					Transition transitionInR = (Transition) rule.fromKtoR(t);
					if (transitionInL != null) {
						transitionInL.setRnw(renew);
					}
					if (transitionInR != null) {
						transitionInR.setRnw(renew);
					}
				} else if (net.equals(RuleNet.R)) {
					Transition transitionInK = (Transition) rule.fromRtoK(t);
					Transition transitionInL = (Transition) rule
							.fromKtoL(transitionInK);
					transitionInK.setRnw(renew);
					if (transitionInL != null) {
						transitionInL.setRnw(renew);
					}
				}
			}
		}
	}

	/**
	 * @see IRuleManipulation#setWeight(int, Arc, int)
	 */
	public void setWeight(int id, Arc arc, int weight) throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("setWeight - id of the Rule is wrong");
		} else {

			Rule rule = ruleData.getRule();
			// set new weight
			arc.setMark(weight);

			// Synchronize Arcs in the other parts of the rules
			RuleNet net = getContainingNet(id, arc);
			if (net.equals(RuleNet.L)) {
				Arc arcInK = rule.fromLtoK(arc);
				Arc arcInR = rule.fromKtoR(arcInK);
				arcInK.setMark(weight);
				if (arcInR != null) {
					arcInR.setMark(weight);
				}
			} else if (net.equals(RuleNet.K)) {
				Arc arcInL = rule.fromKtoL(arc);
				Arc arcInR = rule.fromKtoR(arc);
				if (arcInL != null) {
					arcInL.setMark(weight);
				}
				if (arcInR != null) {
					arcInR.setMark(weight);
				}
			} else if (net.equals(RuleNet.R)) {
				Arc arcInK = rule.fromRtoK(arc);
				Arc arcInL = rule.fromKtoL(arcInK);
				arcInK.setMark(weight);
				if (arcInL != null) {
					arcInL.setMark(weight);
				}
			}

		}

	}

	/**
	 * @see IRuleManipulation#closeRule(int)
	 */
	public void closeRule(int id) throws EngineException {

		// get the Petrinet from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("closeRule - id of the Rule is wrong");
		} else {

			if (!sessionManager.closeSessionData(id)) {
				exception("closeRule - can not remove RuleData");
			}

		}

	}

	/**
	 * @see IRuleManipulation#getNodeType(INode)
	 */
	public NodeTypeEnum getNodeType(INode node) {

		if (node instanceof Place) {
			return NodeTypeEnum.Place;
		} else if (node instanceof Transition) {
			return NodeTypeEnum.Transition;
		} else {
			return null;
		}

	}

	/**
	 * 
	 * @param value
	 * @throws EngineException
	 */
	private void exception(String value) throws EngineException {
		throw new EngineException("RuleHandler: " + value);
	}

	private void checkNodeLayoutAttribute(boolean value, String errorMessage)
			throws EngineException {
		if (value) {
			exception(errorMessage);
		}
	}

	// Helper Method
	private RuleNet getContainingNet(int id, INode node) {
		RuleData ruleData = sessionManager.getRuleData(id);
		Rule rule = ruleData.getRule();
		if (rule.getL().getAllPlaces().contains(node)
				|| rule.getL().getAllTransitions().contains(node)
				|| rule.getL().getAllArcs().contains(node)) {
			return RuleNet.L;
		}
		if (rule.getK().getAllPlaces().contains(node)
				|| rule.getK().getAllTransitions().contains(node)
				|| rule.getK().getAllArcs().contains(node)) {
			return RuleNet.K;
		}
		if (rule.getR().getAllPlaces().contains(node)
				|| rule.getR().getAllTransitions().contains(node)
				|| rule.getR().getAllArcs().contains(node)) {
			return RuleNet.R;
		}
		return null;
	}

	/**
	 * @see {@link IRuleManipulation#moveGraph(int, Point2D)}
	 * @param id
	 * @param relativePosition
	 */
	public void moveGraph(int id, Point2D relativePosition) {
		RuleData ruleData = sessionManager.getRuleData(id);
		ruleData.getLJungData().moveGraph(relativePosition);
		ruleData.getKJungData().moveGraph(relativePosition);
		ruleData.getRJungData().moveGraph(relativePosition);
	}

	/**
	 * @see {@link IRuleManipulation#moveGraphIntoVision(int)}
	 * @param id
	 */
	public void moveGraphIntoVision(int id) {
		RuleData ruleData = sessionManager.getRuleData(id);
		// how must k be moved?
		Point2D.Double vectorToMoveIntoVision = ruleData.getKJungData().getVectorToMoveIntoVision();
		// move all graphs equally to k so their relative position stay the same
		ruleData.getLJungData().moveGraph(vectorToMoveIntoVision);
		ruleData.getKJungData().moveGraph(vectorToMoveIntoVision);
		ruleData.getRJungData().moveGraph(vectorToMoveIntoVision);
	}

	/**
	 * @see {@link IRuleManipulation#moveAllNodesTo(int, float, Point)}
	 * @param id
	 * @param factor
	 * @param point
	 */
	public void moveAllNodesTo(int id, float factor, Point point) {
		RuleData ruleData = sessionManager.getRuleData(id);
		ruleData.getLJungData().moveAllNodesTo(factor, point);
		ruleData.getKJungData().moveAllNodesTo(factor, point);
		ruleData.getRJungData().moveAllNodesTo(factor, point);
	}

	/**
	 * @see {@link IRuleManipulation#setNodeSize(int, double)}
	 * @param id
	 * @param nodeSize
	 */
	public void setNodeSize(int id, double nodeSize) {
		RuleData ruleData = sessionManager.getRuleData(id);
		ruleData.getLJungData().setNodeSize(nodeSize);
		ruleData.getKJungData().setNodeSize(nodeSize);
		ruleData.getRJungData().setNodeSize(nodeSize);
	}

	/**
	 * @see {@link IRuleManipulation#getNodeSize(int)}
	 * 
	 * @param id
	 * @return
	 */
	public double getNodeSize(int id) {
		RuleData ruleData = sessionManager.getRuleData(id);
		// NodeSize is equal for all parts of the rule
		return ruleData.getLJungData().getNodeSize();
	}

}
