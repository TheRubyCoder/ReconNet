package engine.handler;

import java.awt.geom.Point2D;

import petrinet.Arc;
import petrinet.INode;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.TransitionAttribute;
import engine.ihandler.IPetrinetManipulation;
import exceptions.EngineException;

public class PetrinetManipulation implements IPetrinetManipulation {

	private static PetrinetManipulation petrinetManipulation;
	
	private PetrinetManipulation(){}
	
	public static PetrinetManipulation getInstance(){
		if(petrinetManipulation == null){
			petrinetManipulation = new PetrinetManipulation();
		}
		
		return petrinetManipulation;
	}
	
	@Override
	public void createArc(int id, INode from, INode to) throws EngineException {
		// TODO Auto-generated method stub

	}

	@Override
	public void createPlace(int id, Point2D coordinate) throws EngineException {
		// TODO Auto-generated method stub

	}

	@Override
	public int createPetrinet() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void createTransition(int id, Point2D coordinate)
			throws EngineException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteArc(int id, Arc arc) throws EngineException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePlace(int id, INode place) throws EngineException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteTransition(int id, INode transition)
			throws EngineException {
		// TODO Auto-generated method stub

	}

	@Override
	public ArcAttribute getArcAttribute(int id, Arc arc) throws EngineException {
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
	public void moveGraph(int id, Point2D relativePosition)
			throws EngineException {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveNode(int id, INode node, Point2D relativePosition)
			throws EngineException {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(int id, String path, String filename, String format)
			throws EngineException {
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
	public Enum getNodeType(INode node) throws EngineException {
		// TODO Auto-generated method stub
		return null;
	}

}
