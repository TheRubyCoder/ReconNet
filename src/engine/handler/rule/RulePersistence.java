package engine.handler.rule;

import java.awt.Color;
import java.awt.geom.Point2D;

import petrinet.Arc;
import petrinet.INode;
import petrinet.IRenew;
import petrinet.Renews;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.RuleAttribute;
import engine.attribute.TransitionAttribute;
import engine.handler.NodeTypeEnum;
import engine.handler.RuleNet;
import engine.ihandler.IRulePersistence;
import exceptions.EngineException;

public class RulePersistence implements IRulePersistence {
	
	private static RulePersistence rulePersistence;
	private RuleHandler ruleManipulationBackend;
	
	private RulePersistence(){
		this.ruleManipulationBackend = RuleHandler.getInstance();
	}
	
	public static RulePersistence getInstance(){
		if(rulePersistence == null){
			rulePersistence = new RulePersistence();
		}
		
		return rulePersistence;
	}
	
	
	@Override
	public Arc createArc(int id,RuleNet net, INode from, INode to) throws EngineException {
		
		Arc arc = ruleManipulationBackend.createArc(id, net, from, to);
		
		return arc;
		
	}

	@Override
	public INode createPlace(int id, RuleNet net, Point2D coordinate) throws EngineException {
		
		INode place = ruleManipulationBackend.createPlace(id, net, coordinate);
		
		return place;
		
	}

	@Override
	public int createRule() {
		
		int id = ruleManipulationBackend.createRule();
		
		return id;
		
	}

	@Override
	public INode createTransition(int id, RuleNet net, Point2D coordinate)
			throws EngineException {
		
		INode transition = ruleManipulationBackend.createTransition(id, net, coordinate);
		
		return transition;
		
	}

	@Override
	public ArcAttribute getArcAttribute(int id, Arc arc) {
	
		ArcAttribute attr = ruleManipulationBackend.getArcAttribute(id, arc);
		
		return attr;
		
	}

	@Override
	public AbstractLayout<INode, Arc> getJungLayout(int id, RuleNet net) throws EngineException {
		
		AbstractLayout<INode, Arc> layout = ruleManipulationBackend.getJungLayout(id, net);
		
		return layout;
		
	}

	@Override
	public PlaceAttribute getPlaceAttribute(int id, INode place)
			throws EngineException {
		
		PlaceAttribute attr = ruleManipulationBackend.getPlaceAttribute(id, place);
		
		return attr;
		
	}

	@Override
	public TransitionAttribute getTransitionAttribute(int id, INode transition)
			throws EngineException {
		
		TransitionAttribute attr = ruleManipulationBackend.getTransitionAttribute(id, transition);
		
		return attr;
		
	}

	@Override
	public RuleAttribute getRuleAttribute(int id) throws EngineException {
		
		RuleAttribute attr = ruleManipulationBackend.getRuleAttribute(id);
		
		return attr;
		
	}

	@Override
	public void setMarking(int id, INode place, int marking)
			throws EngineException {
		
		ruleManipulationBackend.setMarking(id, place, marking);
		
	}

	@Override
	public void setPname(int id, INode place, String pname)
			throws EngineException {
		
		ruleManipulationBackend.setPname(id, place, pname);
		
	}

	@Override
	public void setTlb(int id, INode transition, String tlb)
			throws EngineException {
		
		ruleManipulationBackend.setTlb(id, transition, tlb);
		
	}

	@Override
	public void setTname(int id, INode transition, String tname)
			throws EngineException {
		
		ruleManipulationBackend.setTname(id, transition, tname);
		
	}

	@Override
	public void setWeight(int id, Arc arc, int weight) throws EngineException {
		
		ruleManipulationBackend.setWeight(id, arc, weight);
		
	}

	@Override
	public NodeTypeEnum getNodeType(INode node) {
		
		NodeTypeEnum type = ruleManipulationBackend.getNodeType(node);
		
		return type;
		
	}

	@Override
	public void setRnw(int id, INode transition, IRenew renews) throws EngineException {

		ruleManipulationBackend.setRnw(id, transition, renews);
	
	}

	@Override
	public void setPlaceColor(int id, INode place, Color color) throws EngineException {

		ruleManipulationBackend.setPlaceColor(id, place, color);
		
	}
	
}
