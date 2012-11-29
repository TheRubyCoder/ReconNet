package engine.handler.rule;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.List;
import java.util.Map;

import persistence.Persistence;
import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.IRenew;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;
import transformation.Rule;
import transformation.Rule.Net;
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
	public IArc createArc(int id, RuleNet net, INode from, INode to)
			throws EngineException {

		RuleData ruleData = sessionManager.getRuleData(id);

		if (ruleData == null) {
			exception("createArc - id of the Rule is wrong");
			return null;
		} 
	
		Rule rule = ruleData.getRule();

		JungData lJungData = ruleData.getLJungData();
		JungData kJungData = ruleData.getKJungData();
		JungData rJungData = ruleData.getRJungData();

		ensureLegalNodeCombination(net, from, to, rule);

		// create Arc in defined map
		if (from instanceof Place && to instanceof Transition) {
			PreArc createdArc = null;
			PreArc kArc       = null;
			
			if (net.equals(RuleNet.L)) {
				createdArc = rule.addPreArcToL("undefined", (Place) from, (Transition) to);
				kArc       = rule.fromLtoK((PreArc) createdArc);
			} else if (net.equals(RuleNet.K)) {
				createdArc = rule.addPreArcToK("undefined", (Place) from, (Transition) to);
				kArc       = createdArc;
			} else if (net.equals(RuleNet.R)) {
				createdArc = rule.addPreArcToR("undefined", (Place) from, (Transition) to);		
				kArc       = rule.fromRtoK((PreArc) createdArc);		
			}		

			// add those arcs into the corresponding jung data
			PreArc lArc = rule.fromKtoL(kArc);
			PreArc rArc = rule.fromKtoR(kArc); 
			
			if (lArc != null) {
				lJungData.createArc(lArc, lArc.getSource(), lArc.getTarget());
			}

			kJungData.createArc(kArc, kArc.getSource(), kArc.getTarget());

			if (rArc != null) {
				rJungData.createArc(rArc, rArc.getSource(), rArc.getTarget());
			}
			
			
			return createdArc;
		} else if (from instanceof Transition && to instanceof Place) {
			PostArc createdArc = null;
			PostArc kArc       = null;
			
			if (net.equals(RuleNet.L)) {
				createdArc = rule.addPostArcToL("undefined", (Transition) from, (Place) to);	
				kArc       = rule.fromLtoK((PostArc) createdArc);			
			} else if (net.equals(RuleNet.K)) {
				createdArc = rule.addPostArcToK("undefined", (Transition) from, (Place) to);
				kArc       = createdArc;				
			} else if (net.equals(RuleNet.R)) {
				createdArc = rule.addPostArcToR("undefined", (Transition) from, (Place) to);
				kArc       = rule.fromRtoK((PostArc) createdArc);					
			}

			// add those arcs into the corresponding jung data
			PostArc lArc = rule.fromKtoL(kArc);
			PostArc rArc = rule.fromKtoR(kArc); 
			
			if (lArc != null) {
				lJungData.createArc(lArc, lArc.getSource(), lArc.getTarget());
			}

			kJungData.createArc(kArc, kArc.getSource(), kArc.getTarget());

			if (rArc != null) {
				rJungData.createArc(rArc, rArc.getSource(), rArc.getTarget());
			}

			return createdArc;
		} else {
			exception("createArc - error");				
		}


		return null;
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
	private void ensureLegalNodeCombination(RuleNet net, INode from, INode to, Rule rule) {
		if (!net.equals(RuleNet.K)) {
			return;
		}
		
		if (from instanceof Place) {
			if (rule.fromKtoL((Place) from) == null) {
				throw new ShowAsWarningException("Startknoten in L nicht verfügbar");
			} else if (rule.fromKtoR((Place) from) == null) {
				throw new ShowAsWarningException("Startknoten in R nicht verfügbar");
			}			
		}
		
		if (to instanceof Place) {
			if (rule.fromKtoL((Place) to) == null) {
				throw new ShowAsWarningException("Zielknoten in L nicht verfügbar");
				
			} else if (rule.fromKtoR((Place) to) == null) {
				throw new ShowAsWarningException("Zielknoten in R nicht verfügbar");
			}
		}
		
		if (from instanceof Transition) {
			if (rule.fromKtoL((Transition) from) == null) {
				throw new ShowAsWarningException("Startknoten in L nicht verfügbar");
			} else if (rule.fromKtoR((Transition) from) == null) {
				throw new ShowAsWarningException("Startknoten in R nicht verfügbar");
			}			
		}
		
		if (to instanceof Transition) {
			if (rule.fromKtoL((Transition) to) == null) {
				throw new ShowAsWarningException("Zielknoten in L nicht verfügbar");
				
			} else if (rule.fromKtoR((Transition) to) == null) {
				throw new ShowAsWarningException("Zielknoten in R nicht verfügbar");
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
		} 

		Rule rule          = ruleData.getRule();
		
		JungData lJungData = ruleData.getLJungData();
		JungData kJungData = ruleData.getKJungData();
		JungData rJungData = ruleData.getRJungData();
		
		if (!lJungData.isCreatePossibleAt(coordinate)) {
			exception("Place too close to Node in L");
			return null;
		}
		
		if (!kJungData.isCreatePossibleAt(coordinate)) {
			exception("Place too close to Node in K");
			return null;
		}
		
		if (!rJungData.isCreatePossibleAt(coordinate)) {
			exception("Place too close to Node in R");
			return null;
		}

		if (net.equals(RuleNet.L)) {

			// create a new Place
			Place newPlace = rule.addPlaceToL("undefined");

			// call JungModificator
			try {
				lJungData.createPlace(newPlace, coordinate);
			} catch (IllegalArgumentException e) {
				exception("createPlace - can not create Place in L");
			}

			// get automatically added Corresponding Place in K
			Place newPlaceInK = rule.fromLtoK(newPlace);
			
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
			// create a new Place
			Place newPlace = rule.addPlaceToK("undefined");

			// call JungModificator
			try {
				kJungData.createPlace(newPlace, coordinate);
			} catch (IllegalArgumentException e) {
				exception("createPlace - can not create Place in K");
			}

			// get automatically added Corresponding Place in L and R
			Place newPlaceInL = rule.fromKtoL(newPlace);
			Place newPlaceInR = rule.fromKtoR(newPlace);
			
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
			// create a new Place
			Place newPlace = rule.addPlaceToR("undefined");

			// call JungModificator
			try {
				rJungData.createPlace(newPlace, coordinate);
			} catch (IllegalArgumentException e) {
				exception("createPlace - can not create Place in R");
			}

			// get automatically added Corresponding Place in K
			Place newPlaceInK = rule.fromRtoK(newPlace);
			
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
		} 
			
		Rule     rule      = ruleData.getRule();
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
			// create a new transition
			Transition newTransition = rule.addTransitionToL("undefined");

			// call JungModificator
			try {
				lJungData.createTransition(newTransition, coordinate);
			} catch (IllegalArgumentException e) {
				exception("createTransition - can not create Transition in L");
			}

			// get automatically added Corresponding Place in K
			Transition newTransitionInK = rule.fromLtoK(newTransition);
			
			if (newTransitionInK != null) {
				try {
					kJungData.createTransition(newTransitionInK, coordinate);
				} catch (IllegalArgumentException e) {
					exception("createPlace - can not create Transition in K");
				}
			}

			return newTransition;

		} else if (net.equals(RuleNet.K)) {
			// create a new Transition
			Transition newTransition = rule.addTransitionToK("undefined");

			// call JungModificator
			try {
				kJungData.createTransition(newTransition, coordinate);
			} catch (IllegalArgumentException e) {
				exception("createTransition - can not create Transition in K");
			}

			// get automatically added Corresponding Transition in L and R
			Transition newTransitionInL = rule.fromKtoL(newTransition);
			Transition newTransitionInR = rule.fromKtoR(newTransition);
			
			if (newTransitionInL != null && newTransitionInR != null) {
				try {
					lJungData.createTransition(newTransitionInL, coordinate);
					rJungData.createTransition(newTransitionInR, coordinate);
				} catch (IllegalArgumentException e) {
					exception("createTransition - can not create Transition in L or R");
				}
			}

			return newTransition;

		} else if (net.equals(RuleNet.R)) {
			// create a new Transition
			Transition newTransition = rule.addTransitionToR("undefined");

			// call JungModificator
			try {
				rJungData.createTransition(newTransition, coordinate);
			} catch (IllegalArgumentException e) {
				exception("createTransition - can not create Transition in R");
			}

			// get automatically added Corresponding Transition in K
			Transition newTransitionInK = rule.fromRtoK(newTransition);
			
			if (newTransitionInK != null) {
				try {
					kJungData.createTransition(newTransitionInK, coordinate);
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

	/**
	 * @see IRuleManipulation#deleteArc(int, RuleNet, Arc)
	 */
	public void deleteArc(int id, RuleNet net, IArc arc) throws EngineException {
		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("id of the Rule is wrong");
			return;
		} 
		
		if (net == null || arc == null) {
			exception("Netz nicht erkannt");
			return;			
		}

		Rule rule = ruleData.getRule();
		
		if (arc instanceof PreArc && net == RuleNet.L) {
			rule.removePreArcFromL((PreArc) arc);
			
		} else if (arc instanceof PreArc && net == RuleNet.K) {
			rule.removePreArcFromK((PreArc) arc);
			
		} else if (arc instanceof PreArc && net == RuleNet.R) {
			rule.removePreArcFromR((PreArc) arc);
			
		} else if (arc instanceof PostArc && net == RuleNet.L) {
			rule.removePostArcFromL((PostArc) arc);
			
		} else if (arc instanceof PostArc && net == RuleNet.K) {
			rule.removePostArcFromK((PostArc) arc);
			
		} else if (arc instanceof PostArc && net == RuleNet.R) {
			rule.removePostArcFromR((PostArc) arc);			
		} 

		ruleData.deleteDataOfMissingElements(rule);
	}

	/**
	 * @see IRuleManipulation#deletePlace(int, RuleNet, INode)
	 */
	public void deletePlace(int id, RuleNet net, INode place)
			throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("id of the Rule is wrong");
			return;
		} 
		
		if (net == null || !(place instanceof Place)) {
			exception("Netz nicht erkannt");
			return;			
		}

		Rule rule = ruleData.getRule();
		
		if (net == RuleNet.L) {
			rule.removePlaceFromL((Place) place);
			
		} else if (net == RuleNet.K) {
			rule.removePlaceFromK((Place) place);
			
		} else if (net == RuleNet.R) {
			rule.removePlaceFromR((Place) place);			
		} 

		ruleData.deleteDataOfMissingElements(rule);
	}

	/**
	 * @see IRuleManipulation#deleteTransition(int, RuleNet, INode)
	 */
	public void deleteTransition(int id, RuleNet net, INode transition)
			throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("id of the Rule is wrong");
			return;
		} 
		
		if (net == null || !(transition instanceof Transition)) {
			exception("Netz nicht erkannt");
			return;			
		}

		Rule rule = ruleData.getRule();
		
		if (net == RuleNet.L) {
			rule.removeTransitionFromL((Transition) transition);
			
		} else if (net == RuleNet.K) {
			rule.removeTransitionFromK((Transition) transition);
			
		} else if (net == RuleNet.R) {
			rule.removeTransitionFromR((Transition) transition);			
		} 

		ruleData.deleteDataOfMissingElements(rule);
	}


	/**
	 * @see IRuleManipulation#getArcAttribute(int, Arc)
	 */
	public ArcAttribute getArcAttribute(int id, IArc arc) {

		int weight = arc.getMark();

		ArcAttribute arcAttribute = new ArcAttribute(weight);

		return arcAttribute;

	}

	/**
	 * @see IRuleManipulation#getJungLayout(int, RuleNet)
	 */
	public AbstractLayout<INode, IArc> getJungLayout(int id, RuleNet net)
			throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("getJungLayout - id of the Rule is wrong");
			return null;
		} 

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

	/**
	 * @see IRuleManipulation#getPlaceAttribute(int, INode)
	 */
	public PlaceAttribute getPlaceAttribute(int id, INode place)
			throws EngineException {

		if (!this.getNodeType(place).equals(NodeTypeEnum.Place)) {
			exception("getPlaceAttribute - the INode value is not a Place");
			return null;
		}


		Place p = (Place) place;

		int marking  = p.getMark();
		String pname = p.getName();

		RuleData ruleData     = sessionManager.getRuleData(id);
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

	/**
	 * @see IRuleManipulation#getTransitionAttribute(int, INode)
	 */
	public TransitionAttribute getTransitionAttribute(int id, INode transition)
			throws EngineException {

		if (!this.getNodeType(transition).equals(NodeTypeEnum.Transition)) {
			exception("getPlaceAttribute - the INode value is not a Transition");
			return null;
		}

		Transition t = (Transition) transition;

		String tlb   = t.getTlb();
		String tname = t.getName();
		IRenew rnw   = t.getRnw();

		boolean isActivated = t.isActivated();

		TransitionAttribute transitionAttribute = new TransitionAttribute(
				tlb, tname, rnw, isActivated);

		return transitionAttribute;
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
			return null;
		}

		// get all Id's from L, K, R
		int lId = ruleData.getRule().getL().getId();
		int kId = ruleData.getRule().getK().getId();
		int rId = ruleData.getRule().getR().getId();

		// create a RuleAttribute
		RuleAttribute ruleAttribute = new RuleAttribute(lId, kId, rId);

		return ruleAttribute;
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
			return;
		}
		
		// get Position
		ruleData.moveNodeRelative(node, relativePosition);
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
			return;
		}

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

	/**
	 * @see IRuleManipulation#load(String, String)
	 */
	public int load(String path, String filename) {

		return Persistence.loadRule(
			path + "/" + filename,
			RulePersistence.getInstance()
		);

	}

	/**
	 * @see IRuleManipulation#setMarking(int, INode, int)
	 */
	public void setMarking(int id, INode place, int marking)
			throws EngineException {

		// get the Petrinet from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null || !(place instanceof Place)) {
			exception("setMarking - id of the rule is wrong");
			return;
		}

		Rule rule = ruleData.getRule();
		Net  net  = TransformationComponent.getTransformation().getNet(rule, (Place) place);
		
		if (net == Net.L) {
			rule.setMarkInL((Place) place, marking);
			
		} else if (net == Net.K) {
			rule.setMarkInK((Place) place, marking);
			
		} else if (net == Net.R) {
			rule.setMarkInR((Place) place, marking); 
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
			return;
		} 
		
		if (node instanceof Place) {
			Net  net  = TransformationComponent.getTransformation().getNet(ruleData.getRule(), (Place) node);
			
			if (net == Net.L) {
				ruleData.getRule().setNameInL((Place) node, name);
				
			} else if (net == Net.K) {
				ruleData.getRule().setNameInK((Place) node, name);
				
			} else if (net == Net.R) {
				ruleData.getRule().setNameInR((Place) node, name); 
			} 
		} else if (node instanceof Transition) {
			Net  net  = TransformationComponent.getTransformation().getNet(ruleData.getRule(), (Transition) node);
			
			if (net == Net.L) {
				ruleData.getRule().setNameInL((Transition) node, name);
				
			} else if (net == Net.K) {
				ruleData.getRule().setNameInK((Transition) node, name);
				
			} else if (net == Net.R) {
				ruleData.getRule().setNameInR((Transition) node, name); 
			} 
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
			return;
		} 
		
		Rule rule = ruleData.getRule();
		JungData lJungData = ruleData.getLJungData();
		JungData kJungData = ruleData.getKJungData();
		JungData rJungData = ruleData.getRJungData();
		
		if (!this.getNodeType(place).equals(NodeTypeEnum.Place)) {
			return;
		}
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
			return;
		} 

		Rule rule = ruleData.getRule();
		if (!this.getNodeType(transition).equals(NodeTypeEnum.Transition)) {
			return;
		}

		RuleNet net = getContainingNet(id, transition);
		
		if (net.equals(RuleNet.L)) {
			rule.setTlbInL((Transition) transition, tlb);
		} else if (net.equals(RuleNet.K)) {
			rule.setTlbInK((Transition) transition, tlb);
		} else if (net.equals(RuleNet.R)) {
			rule.setTlbInR((Transition) transition, tlb);
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
			return;
		}
			
		if (!this.getNodeType(transition).equals(NodeTypeEnum.Transition)) {
			return;
		}

		Rule rule    = ruleData.getRule();
		RuleNet net  = getContainingNet(id, transition);
		
		if (net.equals(RuleNet.L)) {
			rule.setRnwInL((Transition) transition, renew);
		} else if (net.equals(RuleNet.K)) {
			rule.setRnwInK((Transition) transition, renew);
		} else if (net.equals(RuleNet.R)) {
			rule.setRnwInR((Transition) transition, renew);
		}
	}

	/**
	 * @see IRuleManipulation#setWeight(int, Arc, int)
	 */
	public void setWeight(int id, IArc arc, int weight) throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("setWeight - id of the Rule is wrong");
			return;
		} 

		Rule rule = ruleData.getRule();
		// set new weight
		arc.setMark(weight);

		// Synchronize Arcs in the other parts of the rules
		RuleNet net = getContainingNet(id, arc);
		
		if (net == RuleNet.L && arc instanceof PreArc) {
			rule.setWeightInL((PreArc) arc, weight);
			
		} else if (net == RuleNet.L && arc instanceof PreArc) {
			rule.setWeightInK((PreArc) arc, weight);
			
		} else if (net == RuleNet.L && arc instanceof PreArc) {
			rule.setWeightInR((PreArc) arc, weight);
			
		} else if (net == RuleNet.L && arc instanceof PostArc) {
			rule.setWeightInL((PostArc) arc, weight);
			
		} else if (net == RuleNet.L && arc instanceof PostArc) {
			rule.setWeightInK((PostArc) arc, weight);
			
		} else if (net == RuleNet.L && arc instanceof PostArc) {
			rule.setWeightInR((PostArc) arc, weight);
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
			return;
		} 

		if (!sessionManager.closeSessionData(id)) {
			exception("closeRule - can not remove RuleData");
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
		Rule     rule     = ruleData.getRule();
		
		if (rule.getL().getPlaces().contains(node)
		 || rule.getL().getTransitions().contains(node)) {
			return RuleNet.L;
		}
		
		if (rule.getK().getPlaces().contains(node)
		 || rule.getK().getTransitions().contains(node)) {
			return RuleNet.K;
		}
		
		if (rule.getR().getPlaces().contains(node)
		 || rule.getR().getTransitions().contains(node)) {
			return RuleNet.R;
		}
		
		return null;
	}

	private RuleNet getContainingNet(int id, IArc arc) {
		RuleData ruleData = sessionManager.getRuleData(id);
		Rule     rule     = ruleData.getRule();
		
		if (rule.getL().getArcs().contains(arc)) {
			return RuleNet.L;
		}
		
		if (rule.getK().getArcs().contains(arc)) {
			return RuleNet.K;
		}
		
		if (rule.getR().getArcs().contains(arc)) {
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
