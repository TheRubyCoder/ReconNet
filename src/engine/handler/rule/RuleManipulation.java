package engine.handler.rule;

import static exceptions.Exceptions.warning;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.IRenew;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.RuleAttribute;
import engine.attribute.TransitionAttribute;
import engine.handler.NodeTypeEnum;
import engine.handler.RuleNet;
import engine.ihandler.IRuleManipulation;
import exceptions.EngineException;

/**
 * <p>
 * This Class implements {@link IRuleManipulation}.
 * </p>
 * <p>
 * It is a Singleton.
 * </p>
 * <p>
 * It can be use for all manipulations of a Rule.
 * <ul>
 * <li>create[Rule|Arc|Place|Transition](..)</li>
 * <li>delete[Arc|Place|Transition](..)</li>
 * <li>get[Rule|Arc|Place|Transition]Attribute(..)</li>
 * <li>getJungLayout(..)</li>
 * <li>moveNode(..)</li>
 * <li>save(..)</li>
 * <li>set[Marking|Pname|Tlb|Tname|Weight|NodeType](..)</li>
 * </ul>
 * </p>
 * 
 * @author alex (aas772)
 * 
 */
public class RuleManipulation implements IRuleManipulation {
	/** Singleton instance of this class */
	private static RuleManipulation ruleManipulation;
	/** Object with actual logic to delegate to */
	private RuleHandler ruleManipulationBackend;

	private RuleManipulation() {
		this.ruleManipulationBackend = RuleHandler.getInstance();
	}

	/** Returns the singleton instance of this class */
	public static RuleManipulation getInstance() {
		if (ruleManipulation == null) {
			ruleManipulation = new RuleManipulation();
		}

		return ruleManipulation;
	}

	@Override
	public void createArc(int id, RuleNet net, INode from, INode to)
			throws EngineException {

		if (from instanceof Place && to instanceof Transition) {
			ruleManipulationBackend.createPreArc(id, net, (Place) from, (Transition) to);			
		} else if (from instanceof Transition && to instanceof Place) {
			ruleManipulationBackend.createPostArc(id, net, (Transition) from, (Place) to);
		} else {
			warning("Pfeile dürfen nicht zwischen Stelle und Stelle bzw. zwischen Transition und Transition bestehen.");			
		}
	}

	@Override
	public void createPlace(int id, RuleNet net, Point2D coordinate)
			throws EngineException {

		ruleManipulationBackend.createPlace(id, net, coordinate);
	}

	@Override
	public int createRule() {
		int id = ruleManipulationBackend.createRule();

		return id;
	}

	@Override
	public void createTransition(int id, RuleNet net, Point2D coordinate)
			throws EngineException {

		ruleManipulationBackend.createTransition(id, net, coordinate);
	}

	@Override
	public void deleteArc(int id, RuleNet net, IArc arc) throws EngineException {

		if (!(arc instanceof IArc)) {
			warning("this isn't an arc");
			return;
		} 
		
		ruleManipulationBackend.deleteArc(id, net, arc);
	}

	@Override
	public void deletePlace(int id, RuleNet net, INode place)
			throws EngineException {

		if (!(place instanceof Place)) {
			warning("node isn't a place");
			return;
		} 
		
		ruleManipulationBackend.deletePlace(id, net, (Place) place);
	}

	@Override
	public void deleteTransition(int id, RuleNet net, INode transition)
			throws EngineException {

		if (!(transition instanceof Transition)) {
			warning("transition isn't a Transition");
			return;
		} 
		
		ruleManipulationBackend.deleteTransition(id, net, (Transition) transition);
	}

	@Override
	public ArcAttribute getArcAttribute(int id, IArc arc) throws EngineException {
		return ruleManipulationBackend.getArcAttribute(id, arc);
	}

	@Override
	public AbstractLayout<INode, IArc> getJungLayout(int id, RuleNet net)
			throws EngineException {

		return ruleManipulationBackend.getJungLayout(id, net);
	}

	@Override
	public PlaceAttribute getPlaceAttribute(int id, INode place)
			throws EngineException {

		if (!(place instanceof Place)) {
			warning("node isn't a place");
			return null;
		} 

		return ruleManipulationBackend.getPlaceAttribute(id, (Place) place);
	}

	@Override
	public TransitionAttribute getTransitionAttribute(int id, INode transition)
			throws EngineException {

		if (!(transition instanceof Transition)) {
			warning("transition isn't a Transition");
			return null;
		} 

		return ruleManipulationBackend.getTransitionAttribute(id, (Transition) transition);
	}

	@Override
	public RuleAttribute getRuleAttribute(int id) throws EngineException {
		return ruleManipulationBackend.getRuleAttribute(id);
	}

	@Override
	public void setMarking(int id, INode place, int marking)
			throws EngineException {
		
		if (!(place instanceof Place)) {
			warning("place isn't a Place");
			return;
		} 

		ruleManipulationBackend.setMarking(id, (Place) place, marking);

	}

	@Override
	public void setPname(int id, INode place, String pname)
			throws EngineException {

		if (!(place instanceof Place)) {
			warning("place isn't a Place");
			return;
		} 
		
		ruleManipulationBackend.setPname(id, (Place) place, pname);
	}

	@Override
	public void setTlb(int id, INode transition, String tlb)
			throws EngineException {

		if (!(transition instanceof Transition)) {
			warning("transition isn't a Transition");
			return;
		} 
		
		ruleManipulationBackend.setTlb(id, (Transition) transition, tlb);
	}

	@Override
	public void setTname(int id, INode transition, String tname)
			throws EngineException {

		if (!(transition instanceof Transition)) {
			warning("transition isn't a Transition");
			return;
		} 

		ruleManipulationBackend.setTname(id, (Transition) transition, tname);
	}

	@Override
	public void setWeight(int id, IArc arc, int weight) throws EngineException {
		if (arc instanceof PreArc) {
			ruleManipulationBackend.setWeight(id, (PreArc) arc, weight);
		} else if (arc instanceof PostArc) {
			ruleManipulationBackend.setWeight(id, (PostArc) arc, weight);			
		} else {
			warning("this isn't an arc");			
		}
	}

	@Override
	public NodeTypeEnum getNodeType(INode node) throws EngineException {
		return ruleManipulationBackend.getNodeType(node);
	}

	@Override
	public void moveNode(int id, INode node, Point2D relativePosition)
			throws EngineException {
		
		ruleManipulationBackend.moveNode(id, node, relativePosition);
	}

	@Override
	public void save(int id, String path, String filename, String format)
			throws EngineException {

		ruleManipulationBackend.save(id, path, filename, format);
	}

	@Override
	public void setRnw(int id, INode transition, IRenew renews)
			throws EngineException {

		if (!(transition instanceof Transition)) {
			warning("transition isn't a Transition");
			return;
		} 
		
		ruleManipulationBackend.setRnw(id, (Transition) transition, renews);
	}

	@Override
	public int load(String path, String filename) {

		return ruleManipulationBackend.load(path, filename);
	}

	@Override
	public void setPlaceColor(int id, INode place, Color color)
			throws EngineException {

		if (!(place instanceof Place)) {
			warning("place isn't a Place");
			return;
		} 
		
		ruleManipulationBackend.setPlaceColor(id, (Place) place, color);
	}

	@Override
	public void closeRule(int id) throws EngineException {
		ruleManipulationBackend.closeRule(id);
	}

	@Override
	public void moveGraph(int id, Point2D relativePosition) throws EngineException {
		ruleManipulationBackend.moveGraph(id, relativePosition);
	}

	@Override
	public void moveGraphIntoVision(int id) throws EngineException {
		ruleManipulationBackend.moveGraphIntoVision(id);
	}

	@Override
	public void moveAllNodesTo(int id, float factor, Point point) throws EngineException {
		ruleManipulationBackend.moveAllNodesTo(id, factor, point);
	}

	@Override
	public void setNodeSize(int id, double nodeSize) throws EngineException {
		ruleManipulationBackend.setNodeSize(id, nodeSize);

	}

	@Override
	public double getNodeSize(int id) throws EngineException {
		return ruleManipulationBackend.getNodeSize(id);
	}
}
