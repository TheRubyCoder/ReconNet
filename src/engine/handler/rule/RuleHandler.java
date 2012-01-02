package engine.handler.rule;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import petrinet.Arc;
import petrinet.ElementType;
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
import engine.session.SessionManager;
import exceptions.EngineException;

final public class RuleHandler {

	private final SessionManager sessionManager;
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

	public Arc createArc(int id, RuleNet net, INode from, INode to)
			throws EngineException {

		RuleData ruleData = sessionManager.getRuleData(id);

		if (ruleData == null) {
			exception("RuleHandler - id of the Rule is wrong");
			return null;
		} else {
			Rule rule = ruleData.getRule();
			Petrinet petrinet = null;
			JungData jungData = null;

			if (net.equals(RuleNet.L)) {
				// Manipulation in L
				// Get Petrinet and corresponding JungData
				petrinet = rule.getL();
				jungData = ruleData.getLJungData();
			} else if (net.equals(RuleNet.K)) {
				// Manipulation in K
				// Get Petrinet and corresponding JungData
				petrinet = rule.getK();
				jungData = ruleData.getKJungData();
			} else if (net.equals(RuleNet.R)) {
				// Manipulation in R
				// Get Petrinet and corresponding JungData
				petrinet = rule.getR();
				jungData = ruleData.getRJungData();
			} else {
				exception("Not given if Manipulation is in L,K or R");
			}
			if (this.getNodeType(from).equals(NodeTypeEnum.Place)
					&& this.getNodeType(to).equals(NodeTypeEnum.Transition)) {
				// place => transition

				// cast objects
				Place fromPlace = (Place) from;
				Transition toTransition = (Transition) to;

				// create new Arc
				Arc arc = petrinet.createArc("undefined", fromPlace,
						toTransition);

				// call Jung
				try {
					jungData.createArc(arc, fromPlace, toTransition);
				} catch (IllegalArgumentException e) {
					exception("PetrinetManipulation - can not create Arc");
				}

				return arc;
			} else if (this.getNodeType(from).equals(NodeTypeEnum.Transition)
					&& this.getNodeType(to).equals(NodeTypeEnum.Place)) {
				// transition => place

				// cast objects
				Transition fromTransition = (Transition) from;
				Place toPlace = (Place) to;

				// create new Arc
				Arc arc = petrinet.createArc("undefined", fromTransition,
						toPlace);

				// ***************************
				// TODO: ChangedPetrinetResult
				// discuss: what to do
				// ***************************

				// call Jung
				try {
					jungData.createArc(arc, fromTransition, toPlace);
				} catch (IllegalArgumentException e) {
					exception("PetrinetManipulation - can not create Arc");
				}

				return arc;
			} else {
				exception("RuleManipulation - wrong combi");

				return null;
			}

		}
	}

	public INode createPlace(int id, RuleNet net, Point2D coordinate)
			throws EngineException {

		RuleData ruleData = sessionManager.getRuleData(id);

		if (ruleData == null) {
			exception("RuleHandler - id of the Rule is wrong");
			return null;
		} else {
			Rule rule = ruleData.getRule();
			Petrinet petrinet = null;
			JungData jungData = null;

			if (net.equals(RuleNet.L)) {
				// Manipulation in L
				// Get Petrinet and corresponding JungData
				petrinet = rule.getL();
				jungData = ruleData.getLJungData();
			} else if (net.equals(RuleNet.K)) {
				// Manipulation in K
				// Get Petrinet and corresponding JungData
				petrinet = rule.getK();
				jungData = ruleData.getKJungData();
			} else if (net.equals(RuleNet.R)) {
				// Manipulation in R
				// Get Petrinet and corresponding JungData
				petrinet = rule.getR();
				jungData = ruleData.getRJungData();
			} else {
				exception("Not given if Manipulation is in L,K or R");
			}
			// create a new Place
			Place newPlace = petrinet.createPlace("undefined");

			// call JungModificator
			try {
				jungData.createPlace(newPlace, coordinate);
			} catch (IllegalArgumentException e) {
				exception("RuleManipulation - can not create Place");
			}

			return newPlace;

		}

	}

	public int createRule() {

		Rule rule = TransformationComponent.getTransformation().createRule();

		RuleData ruleData = sessionManager.createRuleData(rule);

		return ruleData.getId();
	}

	public INode createTransition(int id, RuleNet net, Point2D coordinate)
			throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("RuleManipulation - id of the Rule is wrong");
			return null;
		} else {
			Rule rule = ruleData.getRule();
			Petrinet petrinet = null;
			JungData jungData = null;

			if (net.equals(RuleNet.L)) {
				// Manipulation in L
				// Get Petrinet and corresponding JungData
				petrinet = rule.getL();
				jungData = ruleData.getLJungData();
			} else if (net.equals(RuleNet.K)) {
				// Manipulation in K
				// Get Petrinet and corresponding JungData
				petrinet = rule.getK();
				jungData = ruleData.getKJungData();
			} else if (net.equals(RuleNet.R)) {
				// Manipulation in R
				// Get Petrinet and corresponding JungData
				petrinet = rule.getR();
				jungData = ruleData.getRJungData();
			} else {
				exception("Not given if Manipulation is in L,K or R");
			}
			// create a new Place
			Transition newTransition = petrinet.createTransition("undefined");

			// call JungModificator
			try {
				jungData.createTransition(newTransition, coordinate);
			} catch (IllegalArgumentException e) {
				exception("RuleManipulation - can not create Transition");
			}

			return newTransition;
		}

	}

	public void deleteArc(int id, RuleNet net, Arc arc) throws EngineException {
		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("RuleManipulation - id of the Rule is wrong");
		} else {
			Rule rule = ruleData.getRule();
			Petrinet petrinet = null;
			JungData jungData = null;

			if (net.equals(RuleNet.L)) {
				// Manipulation in L
				// Get Petrinet and corresponding JungData
				petrinet = rule.getL();
				jungData = ruleData.getLJungData();
			} else if (net.equals(RuleNet.K)) {
				// Manipulation in K
				// Get Petrinet and corresponding JungData
				petrinet = rule.getK();
				jungData = ruleData.getKJungData();
			} else if (net.equals(RuleNet.R)) {
				// Manipulation in R
				// Get Petrinet and corresponding JungData
				petrinet = rule.getR();
				jungData = ruleData.getRJungData();
			} else {
				exception("Not given if Manipulation is in L,K or R");
			}
			petrinet.deleteElementById(arc.getId());

			// call JundModification and create Collection
			Collection<Arc> collArc = new HashSet<Arc>();
			collArc.add(arc);

			Collection<INode> collINodes = new HashSet<INode>();

			try {
				jungData.delete(collArc, collINodes);
			} catch (IllegalArgumentException e) {
				exception("PetrinetManipulation - can not delete Arc");
			}
		}
	}

	public void deletePlace(int id, RuleNet net, INode place) throws EngineException {
		deleteInternal(id, net, place);
	}

	public void deleteTransition(int id, RuleNet net, INode transition) throws EngineException {
		deleteInternal(id, net, transition);
	}

	private void deleteInternal(int id, RuleNet net, INode node) throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("RuleManipulation - id of the Rule is wrong");
		} else {
			Rule rule = ruleData.getRule();
			Petrinet petrinet = null;
			JungData jungData = null;

			if (net.equals(RuleNet.L)) {
				// Manipulation in L
				// Get Petrinet and corresponding JungData
				petrinet = rule.getL();
				jungData = ruleData.getLJungData();
			} else if (net.equals(RuleNet.K)) {
				// Manipulation in K
				// Get Petrinet and corresponding JungData
				petrinet = rule.getK();
				jungData = ruleData.getKJungData();
			} else if (net.equals(RuleNet.R)) {
				// Manipulation in R
				// Get Petrinet and corresponding JungData
				petrinet = rule.getR();
				jungData = ruleData.getRJungData();
			} else {
				exception("Not given if Manipulation is in L,K or R");
			}

			// all deleted elements, returned by Petrinet
			Collection<Integer> allDelElemFromPetrinet = petrinet
					.deleteElementById(node.getId());

			Iterator<Integer> iter = allDelElemFromPetrinet.iterator();
			Collection<Arc> collArc = new HashSet<Arc>();
			Collection<INode> collINodes = new HashSet<INode>();

			while (iter.hasNext()) {
				int idOfElem = iter.next();
				ElementType type = petrinet.getNodeType(idOfElem);

				// test type of Element
				if (type.name().equals(ElementType.ARC)) {
					collArc.add(petrinet.getArcById(idOfElem));
				} else if (type.name().equals(ElementType.PLACE)) {
					collINodes.add(petrinet.getPlaceById(idOfElem));
				} else if (type.name().equals(ElementType.TRANSITION)) {
					collINodes.add(petrinet.getTransitionById(idOfElem));
				} else {
					exception("RuleHandler - invalid type from id");
				}

			}

			try {
				jungData.delete(collArc, collINodes);
			} catch (IllegalArgumentException e) {
				exception("RuleHandler - can not delete Place/Transition");
			}

		}

	}

	public ArcAttribute getArcAttribute(int id, Arc arc) {

		int weight = arc.getMark();

		ArcAttribute arcAttribute = new ArcAttribute(weight);

		return arcAttribute;

	}

	public AbstractLayout<INode, Arc> getJungLayout(int id, RuleNet net) throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("RuleManipulation - id of the Rule is wrong");
		} else {
			Rule rule = ruleData.getRule();
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
				exception("Not given if Manipulation is in L,K or R");
			}
			return jungData.getJungLayout();
		}

		return null;
	}

	public PlaceAttribute getPlaceAttribute(int id, INode place)
			throws EngineException {

		if (this.getNodeType(place).equals(NodeTypeEnum.Place)) {
			Place p = (Place) place;

			int marking = p.getMark();
			String pname = p.getName();

			// TODO : change default color
			Color color = Color.gray;

			PlaceAttribute placeAttribute = new PlaceAttribute(marking, pname,
					color);

			return placeAttribute;
		}

		exception("RuleManipulation - getPlaceAttribute: the INode value is not a Place");
		return null;
	}

	public TransitionAttribute getTransitionAttribute(int id, INode transition)
			throws EngineException {

		if (this.getNodeType(transition).equals(NodeTypeEnum.Transition)) {
			Transition t = (Transition) transition;

			String tlb = t.getTlb();
			String tname = t.getName();
			IRenew rnw = t.getRnw();

			// TODO : change default boolean
			boolean isActivated = false;

			TransitionAttribute transitionAttribute = new TransitionAttribute(
					tlb, tname, rnw, isActivated);

			return transitionAttribute;
		}

		exception("RuleManipulation - getPlaceAttribute: the INode value is not a Transition");
		return null;
	}

	public RuleAttribute getRuleAttribute(int id) throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("RuleManipulation - id of the rule is wrong");
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

	public void moveNode(int id, INode node, Point2D relativePosition)
			throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("RuleManipulation - id of the rule is wrong");
		} else {

			// get all jungdata => l, k, r
			JungData lJungData = ruleData.getLJungData();
			JungData kJungData = ruleData.getKJungData();
			JungData rJungData = ruleData.getRJungData();

			Map<INode, NodeLayoutAttribute> lLayoutMap = lJungData
					.getNodeLayoutAttributes();
			Map<INode, NodeLayoutAttribute> kLayoutMap = kJungData
					.getNodeLayoutAttributes();
			Map<INode, NodeLayoutAttribute> rLayoutMap = rJungData
					.getNodeLayoutAttributes();

			// move node in l
			moveNodeInternal(lJungData, lLayoutMap, node, relativePosition);

			// move node in k
			moveNodeInternal(kJungData, kLayoutMap, node, relativePosition);

			// move node in r
			moveNodeInternal(rJungData, rLayoutMap, node, relativePosition);

		}

	}

	/**
	 * 
	 * This is a helper method for moveNode. It calculate the new position.
	 * 
	 * @param jungData
	 * @param layoutMap
	 * @param node
	 * @param relativePosition
	 * @throws EngineException
	 * 
	 */
	private void moveNodeInternal(JungData jungData,
			Map<INode, NodeLayoutAttribute> layoutMap, INode node,
			Point2D relativePosition) throws EngineException {

		NodeLayoutAttribute nla = layoutMap.get(node);

		double newPointX = nla.getCoordinate().getX() + relativePosition.getX();
		double newPointY = nla.getCoordinate().getY() + relativePosition.getY();

		if (newPointX < 0 || newPointY < 0) {

			double x = relativePosition.getX() - newPointX;
			double y = relativePosition.getY() - newPointY;

			Point2D point = new Point2D.Double(x, y);

			try {
				jungData.moveNode(node, point);
			} catch (IllegalArgumentException e) {
				exception("RuleManipulation - error: moveNode");
			}

		} else {

			Point2D point = new Point2D.Double(newPointX, newPointY);

			try {
				jungData.moveNode(node, point);
			} catch (IllegalArgumentException e) {
				exception("RuleManipulation - error: moveNode");
			}

		}

	}

	public void save(int id, String path, String filename, String format) {

		// TODO: Alex... warte auf das Persistence-Team

	}

	public void setMarking(int id, INode place, int marking)
			throws EngineException {

		// get the Petrinet from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("RuleManipulation - id of the rule is wrong");
		} else {

			JungData lJungData = ruleData.getLJungData();
			JungData kJungData = ruleData.getKJungData();
			JungData rJungData = ruleData.getRJungData();

			if (this.getNodeType(place).equals(NodeTypeEnum.Place)) {
				// cast object
				Place p = (Place) place;

				// set new marking
				p.setMark(marking);

				// call Jung for l
				try {
					// TODO existiert nimmer
					// lJungData.updatePlace(p);

				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setMarking in l");
				}

				// call Jung for k
				try {

					// TODO existiert nimmer
					// kJungData.updatePlace(p);

				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setMarking in k");
				}

				// call Jung for r
				try {

					// TODO existiert nimmer
					// rJungData.updatePlace(p);

				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setMarking in r");
				}

			}

		}

	}

	public void setPname(int id, INode place, String pname)
			throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("RuleManipulation - id of the rule is wrong");
		} else {

			JungData lJungData = ruleData.getLJungData();
			JungData kJungData = ruleData.getKJungData();
			JungData rJungData = ruleData.getRJungData();

			if (this.getNodeType(place).equals(NodeTypeEnum.Place)) {
				// cast object
				Place p = (Place) place;

				// set new Pname
				p.setName(pname);

				// call Jung for l
				try {
					// TODO existiert nimmer
					// lJungData.updatePlace(p);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setPname in l");
				}

				// call JungModification for k
				try {
					// TODO existiert nimmer
					// kJungData.updatePlace(p);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setPname in k");
				}

				// call JungModification for r
				try {
					// TODO existiert nimmer
					// rJungData.updatePlace(p);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setPname in r");
				}

			}

		}

	}

	public void setTlb(int id, INode transition, String tlb)
			throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("RuleManipulation - id of the rule is wrong");
		} else {

			JungData lJungData = ruleData.getLJungData();
			JungData kJungData = ruleData.getKJungData();
			JungData rJungData = ruleData.getRJungData();

			if (this.getNodeType(transition).equals(NodeTypeEnum.Transition)) {
				// cast object
				Transition t = (Transition) transition;

				// set new Tlb
				t.setTlb(tlb);

				// call Jung for l
				try {
					// TODO existiert nimmer
					// lJungData.updateTransition(t);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setTlb in l");
				}

				// call Jung for k
				try {
					// TODO existiert nimmer
					// kJungData.updateTransition(t);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setTlb in k");
				}

				// call Jung for r
				try {
					// TODO existiert nimmer
					// rJungData.updateTransition(t);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setTlb in r");
				}

			}

		}

	}

	public void setTname(int id, INode transition, String tname)
			throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("RuleManipulation - id of the rule is wrong");
		} else {

			JungData lJungData = ruleData.getLJungData();
			JungData kJungData = ruleData.getKJungData();
			JungData rJungData = ruleData.getRJungData();

			if (this.getNodeType(transition).equals(NodeTypeEnum.Transition)) {
				// cast object
				Transition t = (Transition) transition;

				// set new Tname
				t.setName(tname);

				// call Jung for l
				try {
					// TODO existiert nimmer
					// lJungData.updateTransition(t);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setTname in l");
				}

				// call Jung for k
				try {
					// TODO existiert nimmer
					// kJungData.updateTransition(t);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setTname in k");
				}

				// call Jung for r
				try {
					// TODO existiert nimmer
					// rJungData.updateTransition(t);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setTname in r");
				}

			}

		}

	}

	public void setWeight(int id, Arc arc, int weight) throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("RuleManipulation - id of the Rule is wrong");
		} else {

			JungData lJungData = ruleData.getLJungData();
			JungData kJungData = ruleData.getKJungData();
			JungData rJungData = ruleData.getRJungData();

			// set new weight
			arc.setMark(weight);

			// call JungModification for l
			try {
				// TODO existiert nimmer
				// lJungData.updateArc(arc);
			} catch (IllegalArgumentException e) {
				exception("RuleManipulation - error: setWeight in l");
			}

			// call JungModification for k
			try {
				// TODO existiert nimmer
				// kJungData.updateArc(arc);
			} catch (IllegalArgumentException e) {
				exception("RuleManipulation - error: setWeight in k");
			}

			// call JungModification for r
			try {
				// TODO existiert nimmer
				// rJungData.updateArc(arc);
			} catch (IllegalArgumentException e) {
				exception("RuleManipulation - error: setWeight in r");
			}

		}

	}

	public NodeTypeEnum getNodeType(INode node) {

		if (node instanceof Place) {
			return NodeTypeEnum.Place;
		} else if (node instanceof Transition) {
			return NodeTypeEnum.Transition;
		} else {
			return null;
		}

	}

	private void exception(String value) throws EngineException {
		throw new EngineException(value);
	}

}
