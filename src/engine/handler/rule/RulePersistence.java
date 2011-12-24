package engine.handler.rule;

import java.awt.geom.Point2D;

import petrinet.Arc;
import petrinet.INode;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.RuleAttribute;
import engine.attribute.TransitionAttribute;
import engine.handler.RuleNet;
import engine.ihandler.IRulePersistence;
import exceptions.EngineException;

public class RulePersistence implements IRulePersistence {

	@Override
	public Arc createArc(int id, INode from, INode to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public INode createPlace(int id, RuleNet net, Point2D coordinate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createRule() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public INode createTransition(int id, RuleNet net, Point2D coordinate)
			throws EngineException {
		// TODO Auto-generated method stub
		return null;
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
