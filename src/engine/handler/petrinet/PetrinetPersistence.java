package engine.handler.petrinet;

import java.awt.Color;
import java.awt.geom.Point2D;

import petrinet.model.IRenew;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;
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
 * It can be use for all manipulations for a Petrinet.
 * <ul>
 * <li>create[Petrinet|PreArc|PostArc|Place|Transition](..)</li>
 * <li>set[Marking|Pname|Tlb|Tname|Weight](..)</li>
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
	public PreArc createPreArc(int id, Place place, Transition transition) throws EngineException {
		return petrinetManipulationBackend.createPreArc(id, place, transition);
	}

	@Override
	public PostArc createPostArc(int id, Transition transition, Place place) throws EngineException {
		return petrinetManipulationBackend.createPostArc(id, transition, place);
	}

	@Override
	public Place createPlace(int id, Point2D coordinate) throws EngineException {
		return petrinetManipulationBackend.createPlace(id, coordinate);
	}

	@Override
	public int createPetrinet() {
		int petrinetId = petrinetManipulationBackend.createPetrinet();

		return petrinetId;
	}

	@Override
	public Transition createTransition(int id, Point2D coordinate)
			throws EngineException {

		return petrinetManipulationBackend.createTransition(id,
				coordinate);
	}


	@Override
	public void setMarking(int id, Place place, int marking)
			throws EngineException {

		petrinetManipulationBackend.setMarking(id, place, marking);
	}

	@Override
	public void setPname(int id, Place place, String pname)
			throws EngineException {

		petrinetManipulationBackend.setPname(id, place, pname);
	}

	@Override
	public void setTlb(int id, Transition transition, String tlb)
			throws EngineException {

		petrinetManipulationBackend.setTlb(id, transition, tlb);
	}

	@Override
	public void setTname(int id, Transition transition, String tname)
			throws EngineException {

		petrinetManipulationBackend.setTname(id, transition, tname);
	}

	@Override
	public void setWeight(int id, PreArc preArc, int weight) throws EngineException {
		petrinetManipulationBackend.setWeight(id, preArc, weight);
	}

	@Override
	public void setWeight(int id, PostArc postArc, int weight) throws EngineException {
		petrinetManipulationBackend.setWeight(id, postArc, weight);
	}

	@Override
	public void setRnw(int id, Transition transition, IRenew renews)
			throws EngineException {

		petrinetManipulationBackend.setRnw(id, transition, renews);
	}

	@Override
	public void setPlaceColor(int id, Place place, Color color)
			throws EngineException {

		petrinetManipulationBackend.setPlaceColor(id, place, color);
	}

	@Override
	public void setNodeSize(int id, double nodeSize) {
		petrinetManipulationBackend.setNodeSize(id, nodeSize);
	}
}
