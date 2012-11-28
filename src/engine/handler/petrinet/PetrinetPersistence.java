package engine.handler.petrinet;

import java.awt.Color;
import java.awt.geom.Point2D;

import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.IRenew;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.TransitionAttribute;
import engine.ihandler.IPetrinetPersistence;
import exceptions.EngineException;

/**
 * <p>
 * This Class implements engine.ihandler.IPetrinetPersistence, and it is the
 * interface for the Persistence-Component.
 * </p>
 * <p>
 * We wrap the implementation in: PetriManipulationBackend
 * </p>
 * <p>
 * It is a Singleton. (PetrinetPersistence.getInstance)
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
public class PetrinetPersistence implements IPetrinetPersistence {

	private static PetrinetPersistence petrinetPersistence;
	private PetrinetHandler petrinetManipulationBackend;

	private PetrinetPersistence() {
		this.petrinetManipulationBackend = PetrinetHandler.getInstance();
	}

	public static PetrinetPersistence getInstance() {
		if (petrinetPersistence == null) {
			petrinetPersistence = new PetrinetPersistence();
		}

		return petrinetPersistence;
	}

	@Override
	public IArc createArc(int id, INode from, INode to) throws EngineException {

		IArc arc = petrinetManipulationBackend.createArc(id, from, to);

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

		INode transition = petrinetManipulationBackend.createTransition(id,
				coordinate);

		return transition;

	}

	@Override
	public ArcAttribute getArcAttribute(int id, IArc arc) {

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

		PlaceAttribute attr = petrinetManipulationBackend.getPlaceAttribute(id,
				place);

		return attr;

	}

	@Override
	public TransitionAttribute getTransitionAttribute(int id, INode transition)
			throws EngineException {

		TransitionAttribute attr = petrinetManipulationBackend
				.getTransitionAttribute(id, transition);

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
	public void setWeight(int id, IArc arc, int weight) throws EngineException {

		petrinetManipulationBackend.setWeight(id, arc, weight);

	}

	@Override
	public Enum<?> getNodeType(INode node) throws EngineException {

		Enum<?> nodeType = petrinetManipulationBackend.getNodeType(node);

		return nodeType;
	}

	@Override
	public void setRnw(int id, INode transition, IRenew renews)
			throws EngineException {

		petrinetManipulationBackend.setRnw(id, transition, renews);

	}

	@Override
	public void setPlaceColor(int id, INode place, Color color)
			throws EngineException {

		petrinetManipulationBackend.setPlaceColor(id, place, color);

	}

	@Override
	public void setNodeSize(int id, double nodeSize) {
		petrinetManipulationBackend.setNodeSize(id, nodeSize);
	}

}
