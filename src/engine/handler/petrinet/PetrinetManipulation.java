package engine.handler.petrinet;

import java.awt.Color;
import java.awt.geom.Point2D;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Renews;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.TransitionAttribute;
import engine.handler.NodeTypeEnum;
import engine.ihandler.IPetrinetManipulation;
import exceptions.EngineException;

/**
 * 
 * This Class implements engine.ihandler.IPetrinetManipulation,
 * and it is the interface for the GUI-Component.
 * 
 * We wrap the implementation in: PetriManipulationBackend
 * 
 * It is a Singleton. (PetrinetManipulation.getInstance)
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

public class PetrinetManipulation implements IPetrinetManipulation {

	private static PetrinetManipulation petrinetManipulation;
	private PetrinetHandler petrinetManipulationBackend;
	
	private PetrinetManipulation(){
		this.petrinetManipulationBackend = PetrinetHandler.getInstance();
	}
	
	public static PetrinetManipulation getInstance(){
		if(petrinetManipulation == null){
			petrinetManipulation = new PetrinetManipulation();
		}
		
		return petrinetManipulation;
	}
	
	@Override
	public void createArc(int id, INode from, INode to) throws EngineException {
		petrinetManipulationBackend.createArc(id, from, to);
	}

	@Override
	public void createPlace(int id, Point2D coordinate) throws EngineException {
		petrinetManipulationBackend.createPlace(id, coordinate);
	}

	@Override
	public int createPetrinet() {

		int petrinetid = petrinetManipulationBackend.createPetrinet();
		
		return petrinetid;
		
	}

	@Override
	public void createTransition(int id, Point2D coordinate)
			throws EngineException {
		petrinetManipulationBackend.createTransition(id, coordinate);
	}

	@Override
	public void deleteArc(int id, Arc arc) throws EngineException {
		petrinetManipulationBackend.deleteArc(id, arc);
	}

	@Override
	public void deletePlace(int id, INode place) throws EngineException {
		petrinetManipulationBackend.deletePlace(id, place);
	}

	@Override
	public void deleteTransition(int id, INode transition)
			throws EngineException {
		petrinetManipulationBackend.deleteTransition(id, transition);
	}

	@Override
	public ArcAttribute getArcAttribute(int id, Arc arc) throws EngineException {
		
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
	public PlaceAttribute getPlaceAttribute(int id, INode place)
			throws EngineException {

		PlaceAttribute attr = petrinetManipulationBackend.getPlaceAttribute(id, place);
		
		return attr;
		
	}

	@Override
	public TransitionAttribute getTransitionAttribute(int id, INode transition)
			throws EngineException {

		TransitionAttribute attr = petrinetManipulationBackend.getTransitionAttribute(id, transition);
		
		return attr;
		
	}

	@Override
	public void moveGraph(int id, Point2D relativePosition)
			throws EngineException {
		petrinetManipulationBackend.moveGraph(id, relativePosition);
	}

	@Override
	public void moveNode(int id, INode node, Point2D relativePosition)
			throws EngineException {
		petrinetManipulationBackend.moveNode(id, node, relativePosition);
	}

	@Override
	public void save(int id, String path, String filename, String format)
			throws EngineException {
		petrinetManipulationBackend.save(id, path, filename, format);
	}
	
	@Override
	public int load(String path, String filename) {
		return petrinetManipulationBackend.load(path, filename);
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
	public void setRnw(int id, INode transition, Renews renews) throws EngineException {
		petrinetManipulationBackend.setRnw(id, transition, renews);
	}
	
	@Override
	public NodeTypeEnum getNodeType(INode node) throws EngineException {

		NodeTypeEnum nodeType = petrinetManipulationBackend.getNodeType(node);
		
		return nodeType;
		
	}

	@Override
	public void setPlaceColor(int id, INode place, Color color) throws EngineException {

		petrinetManipulationBackend.setPlaceColor(id, place, color);
		
	}
	
	public void closePetrinet(int id) throws EngineException{
		
		petrinetManipulationBackend.closePetrinet(id);
		
	}

}
