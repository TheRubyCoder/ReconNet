package engine.handler;

import java.awt.geom.Point2D;

import petrinet.Arc;
import petrinet.INode;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.TransitionAttribute;
import engine.ihandler.IPetrinetPersistence;
import exceptions.EngineException;

public class PetrinetPersistence implements IPetrinetPersistence {

	@Override
	public Arc createArc(int id, INode from, INode to) throws EngineException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public INode createPlace(int id, Point2D coordinate) throws EngineException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createPetrinet() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public INode createTransition(int id, Point2D coordinate)
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
	public AbstractLayout<INode, Arc> getJungLayout(int id)
			throws EngineException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlaceAttribute getPlaceAttribute(int id, INode place) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransitionAttribute getTransitionAttribute(int id, INode transition) {
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
	public Enum getNodeType(INode node) throws EngineException {
		// TODO Auto-generated method stub
		return null;
	}

}

