package engine.handler.petrinet;

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
import engine.attribute.TransitionAttribute;
import engine.dependency.PetrinetAdapter;
import engine.handler.NodeTypeEnum;
import engine.ihandler.IPetrinetManipulation;
import exceptions.EngineException;

/**
 * <p>
 * This Class implements {@link IPetrinetManipulation}, and it is the interface
 * for the GUI-Component.
 * </p>
 * <p>
 * We wrap the implementation in: PetriManipulationBackend
 * </p>
 * <p>
 * It is a Singleton. (PetrinetManipulation.getInstance)
 * </p>
 * <p>
 * It can be use for all manipulations for a Petrninet.
 * <ul>
 * <li>create[Petrinet|Arc|Place|Transition](..)</li>
 * <li>delete[Arc|Place|Transition](..)</li>
 * <li>get[Arc|Place|Transition]Attribute(..)</li>
 * <li>getJungLayout(..)</li>
 * <li>move[Graph|Node](..)</li>
 * <li>save(..)</li>
 * <li>set[Marking|Pname|Tlb|Tname|Weight|NodeType](..)</li>
 * </ul>
 * </p>
 * 
 * @author alex (aas772)
 * 
 */

public class PetrinetManipulation implements IPetrinetManipulation {

	/** Singleton instance */
	private static PetrinetManipulation petrinetManipulation;
	/** Object with actual logic to delegate to */
	private PetrinetHandler petrinetManipulationBackend;

	private PetrinetManipulation() {
		this.petrinetManipulationBackend = PetrinetHandler.getInstance();
	}

	public static PetrinetManipulation getInstance() {
		if (petrinetManipulation == null) {
			petrinetManipulation = new PetrinetManipulation();
		}

		return petrinetManipulation;
	}

	@Override
	public void createArc(int id, INode from, INode to) throws EngineException {			
		if (from instanceof Place && to instanceof Transition) {
			petrinetManipulationBackend.createPreArc(id, (Place) from, (Transition) to);			
		} else if (from instanceof Transition && to instanceof Place) {
			petrinetManipulationBackend.createPostArc(id, (Transition) from, (Place) to);
		} else {
			warning("Pfeile dürfen nicht zwischen Stelle und Stelle bzw. zwischen Transition und Transition bestehen.");			
		}
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
	public void deleteArc(int id, IArc arc) throws EngineException {
		petrinetManipulationBackend.deleteArc(id, arc);
	}

	@Override
	public void deletePlace(int id, INode place) throws EngineException {
		if (!(place instanceof Place)) {
			warning("node isn't a place");
			return;
		} 
		
		petrinetManipulationBackend.deletePlace(id, (Place) place);
	}

	@Override
	public void deleteTransition(int id, INode transition)
			throws EngineException {
		
		if (!(transition instanceof Transition)) {
			warning("node isn't a transition");
			return;
		} 
		
		petrinetManipulationBackend.deleteTransition(id, (Transition) transition);
	}

	@Override
	public ArcAttribute getArcAttribute(int id, IArc arc) throws EngineException {

		ArcAttribute attr = petrinetManipulationBackend
				.getArcAttribute(id, arc);

		return attr;

	}

	@Override
	public AbstractLayout<INode, IArc> getJungLayout(int id)
			throws EngineException {

		AbstractLayout<INode, IArc> attr = petrinetManipulationBackend
				.getJungLayout(id);

		return attr;

	}

	@Override
	public PlaceAttribute getPlaceAttribute(int id, INode place)
			throws EngineException {

		if (!(place instanceof Place)) {
			warning("node isn't a place");
			return null;
		} 
		
		PlaceAttribute attr = petrinetManipulationBackend.getPlaceAttribute(id,
				(Place) place);

		return attr;

	}

	@Override
	public TransitionAttribute getTransitionAttribute(int id, INode transition)
			throws EngineException {

		if (!(transition instanceof Transition)) {
			warning("node isn't a transition");
			return null;
		} 

		TransitionAttribute attr = petrinetManipulationBackend
				.getTransitionAttribute(id, (Transition) transition);

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
	public void save(int id, String path, String filename, String format,
			double nodeSize) throws EngineException {
		petrinetManipulationBackend.save(id, path, filename, format, nodeSize);
	}

	@Override
	public int load(String path, String filename) {
		return petrinetManipulationBackend.load(path, filename);
	}

	@Override
	public void setMarking(int id, INode place, int marking)
			throws EngineException {
		
		if (!(place instanceof Place)) {
			warning("node isn't a place");
			return;
		} 
		
		petrinetManipulationBackend.setMarking(id, (Place) place, marking);
	}

	@Override
	public void setPname(int id, INode place, String pname)
			throws EngineException {

		if (!(place instanceof Place)) {
			warning("node isn't a place");
			return;
		} 
		
		petrinetManipulationBackend.setPname(id, (Place) place, pname);
	}

	@Override
	public void setTlb(int id, INode transition, String tlb)
			throws EngineException {

		if (!(transition instanceof Transition)) {
			warning("node isn't a transition");
			return;
		} 
		
		petrinetManipulationBackend.setTlb(id, (Transition) transition, tlb);
	}

	@Override
	public void setTname(int id, INode transition, String tname)
			throws EngineException {

		if (!(transition instanceof Transition)) {
			warning("node isn't a transition");
			return;
		} 
		
		petrinetManipulationBackend.setTname(id, (Transition) transition, tname);
	}

	@Override
	public void setWeight(int id, IArc arc, int weight) throws EngineException {
		if (arc instanceof PreArc) {
			petrinetManipulationBackend.setWeight(id, (PreArc) arc, weight);
		} else if (arc instanceof PostArc) {
			petrinetManipulationBackend.setWeight(id, (PostArc) arc, weight);			
		} else {
			warning("this isn't an arc");			
		}
	}

	@Override
	public void setRnw(int id, INode transition, IRenew renews)
			throws EngineException {

		if (!(transition instanceof Transition)) {
			warning("node isn't a transition");
			return;
		} 
		
		petrinetManipulationBackend.setRnw(id, (Transition) transition, renews);
	}

	@Override
	public NodeTypeEnum getNodeType(INode node) throws EngineException {

		NodeTypeEnum nodeType = petrinetManipulationBackend.getNodeType(node);

		return nodeType;

	}

	@Override
	public void setPlaceColor(int id, INode place, Color color)
			throws EngineException {

		if (!(place instanceof Place)) {
			warning("node isn't a place");
			return;
		} 
		
		petrinetManipulationBackend.setPlaceColor(id, (Place) place, color);

	}

	public void closePetrinet(int id) throws EngineException {

		petrinetManipulationBackend.closePetrinet(id);

	}

	@Override
	public void printPetrinet(int id) {
		System.out.println(PetrinetAdapter.getPetrinetById(id));
	}

	@Override
	public void moveGraphIntoVision(int id) throws EngineException {
		petrinetManipulationBackend.moveGraphIntoVision(id);
	}

	@Override
	public void moveAllNodesTo(int id, float factor, Point point) {
		petrinetManipulationBackend.moveAllNodesTo(id, factor, point);
	}

	@Override
	public void setNodeSize(int id, double nodeDistance) {
		petrinetManipulationBackend.setNodeSize(id, nodeDistance);

	}

	@Override
	public double getNodeSize(int id) throws NullPointerException {
		return petrinetManipulationBackend.getNodeSize(id);
	}

}
