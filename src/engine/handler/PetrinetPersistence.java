package engine.handler;

import java.awt.geom.Point2D;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Place;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.TransitionAttribute;
import engine.ihandler.IPetrinetPersistence;
import exceptions.EngineException;

/**
 * 
 * This Class implements engine.ihandler.IPetrinetPersistence,
 * and it is the interface for the Persistence-Component.
 * 
 * We wrap the implementation in: PetriManipulationBackend
 * 
 * It is a Singleton. (PetrinetPersistence.getInstance)
 * 
 * It can be use for all manipulations for a Petrninet.
 * 	- create[Petrinet|Arc|Place|Transition](..)
 * 	- delete[Arc|Place|Transition](..)
 *  - get[Arc|Place|Transition]Attribute(..)
 *  - getJungLayout(..)
 *  - move[Graph|Node](..)
 *  - save(..)
 *  - set[Marking|Pname|Tlb|Tname|Weight|NodeType](..)
 * 
 * @author alex (aas772)
 *
 */

public class PetrinetPersistence implements IPetrinetPersistence {

	private static PetrinetPersistence petrinetPersistence;
	private PetrinetHandler petrinetManipulationBackend;
	
	private PetrinetPersistence(){
		this.petrinetManipulationBackend = PetrinetHandler.getInstance();
	}
	
	public static PetrinetPersistence getInstance(){
		if(petrinetPersistence == null){
			petrinetPersistence = new PetrinetPersistence();
		}
		
		return petrinetPersistence;
	}
	
	@Override
	public Arc createArc(int id, INode from, INode to) throws EngineException {

		Arc arc = petrinetManipulationBackend.createArc(id, from, to);
		
		return arc;
		
	}

	@Override
	public INode createPlace(int id, Point2D coordinate) throws EngineException {

		INode place = petrinetManipulationBackend.createPlace(id, coordinate);
		
		return place;
		
	}

	@Override
	public int createPetrinet() {

		int petrinetId = petrinetManipulationBackend.createPetrinet();
		
		return petrinetId;
		
	}

	@Override
	public INode createTransition(int id, Point2D coordinate)
			throws EngineException {

		INode transition = petrinetManipulationBackend.createTransition(id, coordinate);
		
		return transition;
		
	}

	@Override
	public ArcAttribute getArcAttribute(int id, Arc arc) {

		ArcAttribute attr = petrinetManipulationBackend.getArcAttribute(id, arc);
		
		return attr;
		
	}

	@Override
	public AbstractLayout<INode, Arc> getJungLayout(int id)
			throws EngineException {

		AbstractLayout<INode, Arc> attr = petrinetManipulationBackend.getJungLayout(id);
		
		return attr;
		
	}

	@Override
	public PlaceAttribute getPlaceAttribute(int id, INode place) throws EngineException {

		PlaceAttribute attr = petrinetManipulationBackend.getPlaceAttribute(id, place);
		
		return attr;
		
	}

	@Override
	public TransitionAttribute getTransitionAttribute(int id, INode transition) throws EngineException {

		TransitionAttribute attr = petrinetManipulationBackend.getTransitionAttribute(id, transition);
		
		return attr;
		
	}

	@Override
	public void setMarking(int id, INode place, int marking)
			throws EngineException {

		petrinetManipulationBackend.setMarking(id, place, marking);
		
	}

	@Override
	public void setPname(int id, INode place, String pname)
			throws EngineException {

		petrinetManipulationBackend.setPname(id, place, pname);
		
	}

	@Override
	public void setTlb(int id, INode transition, String tlb)
			throws EngineException {
		
		petrinetManipulationBackend.setTlb(id, transition, tlb);
		
	}

	@Override
	public void setTname(int id, INode transition, String tname)
			throws EngineException {

		petrinetManipulationBackend.setTname(id, transition, tname);
		
	}

	@Override
	public void setWeight(int id, Arc arc, int weight) throws EngineException {

		petrinetManipulationBackend.setWeight(id, arc, weight);
		
	}

	@Override
	public Enum<?> getNodeType(INode node) throws EngineException {

		Enum<?> nodeType = petrinetManipulationBackend.getNodeType(node);
		
		return nodeType;
	}

}

