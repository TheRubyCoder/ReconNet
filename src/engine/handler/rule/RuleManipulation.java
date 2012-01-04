package engine.handler.rule;

import java.awt.geom.Point2D;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Renews;
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
 * 
 * This Class implements engine.ihandler.IRuleManipulation.
 * 
 * It is a Singleton.
 * 
 * It can be use for all manipulations for a Rule. 
 * - create[Rule|Arc|Place|Transition](..) 
 * - delete[Arc|Place|Transition](..) 
 * - get[Rule|Arc|Place|Transition]Attribute(..) 
 * - getJungLayout(..) 
 * - moveNode(..) 
 * - save(..) 
 * - set[Marking|Pname|Tlb|Tname|Weight|NodeType](..)
 * 
 * @author alex (aas772)
 * 
 */

public class RuleManipulation implements IRuleManipulation {

	private static RuleManipulation ruleManipulation;
	private RuleHandler ruleManipulationBackend;
	
	private RuleManipulation(){
		this.ruleManipulationBackend = RuleHandler.getInstance();
	}
	
	public static RuleManipulation getInstance(){
		if(ruleManipulation == null){
			ruleManipulation = new RuleManipulation();
		}
		
		return ruleManipulation;
	}
	
	@Override
	public void createArc(int id,RuleNet net, INode from, INode to) throws EngineException {
		
		ruleManipulationBackend.createArc(id, net, from, to);

	}

	@Override
	public void createPlace(int id, RuleNet net, Point2D coordinate) throws EngineException {
		
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
	public void deleteArc(int id, RuleNet net, Arc arc) throws EngineException {
		
		ruleManipulationBackend.deleteArc(id, net, arc);

	}

	@Override
	public void deletePlace(int id, RuleNet net, INode place) throws EngineException {
		
		ruleManipulationBackend.deletePlace(id, net, place);

	}

	@Override
	public void deleteTransition(int id, RuleNet net, INode transition) throws EngineException {
		
		ruleManipulationBackend.deleteTransition(id, net, transition);

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
	public void moveNode(int id, INode node, Point2D relativePosition)
			throws EngineException {
		
		ruleManipulationBackend.moveNode(id, node, relativePosition);
		
	}

	@Override
	public void save(int id, String path, String filename, String format)  throws EngineException {
		
		ruleManipulationBackend.save(id, path, filename, format);
		
	}
	
	@Override
	public void setRnw(int id, INode transition, Renews renews) {
		
		ruleManipulationBackend.setRnw(id, transition, renews);
		
	}
	
	@Override
	public int load(String path, String filename) {

		return ruleManipulationBackend.load(path, filename);
		
	}

}
