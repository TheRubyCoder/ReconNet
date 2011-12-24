package engine.handler;

import java.awt.geom.Point2D;

import petrinet.Arc;
import petrinet.INode;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.RuleAttribute;
import engine.attribute.TransitionAttribute;
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
	
	private RuleManipulation(){}
	
	public static RuleManipulation getInstance(){
		if(ruleManipulation == null){
			ruleManipulation = new RuleManipulation();
		}
		
		return ruleManipulation;
	}
	
	@Override
	public void createArc(int id, INode from, INode to) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createPlace(int id, RuleNet net, Point2D coordinate) {
		// TODO Auto-generated method stub

	}

	@Override
	public int createRule() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void createTransition(int id, RuleNet net, Point2D coordinate)
			throws EngineException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteArc(int id, RuleNet net, Arc arc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePlace(int id, RuleNet net, INode place) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteTransition(int id, RuleNet net, INode transition) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArcAttribute getArcAttribute(int id, Arc arc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractLayout<INode, Arc> getJungLayout(int id, RuleNet net) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlaceAttribute getPlaceAttribute(int id, INode place)
			throws EngineException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransitionAttribute getTransitionAttribute(int id, INode transition)
			throws EngineException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RuleAttribute getRuleAttribute(int id) throws EngineException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void moveNode(int id, INode node, Point2D relativePosition)
			throws EngineException {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(int id, String path, String filename, String format) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMarking(int id, INode place, int marking)
			throws EngineException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPname(int id, INode place, String pname)
			throws EngineException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTlb(int id, INode transition, String tlb)
			throws EngineException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTname(int id, INode transition, String tname)
			throws EngineException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWeight(int id, Arc arc, int weight) throws EngineException {
		// TODO Auto-generated method stub

	}

	@Override
	public Enum<?> getNodeType(INode node) {
		// TODO Auto-generated method stub
		return null;
	}

}
