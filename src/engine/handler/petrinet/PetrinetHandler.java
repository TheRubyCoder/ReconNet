package engine.handler.petrinet;

import static exceptions.Exceptions.exception;
import static exceptions.Exceptions.exceptionIf;
import static exceptions.Exceptions.warning;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import persistence.Persistence;
import petrinet.PetrinetComponent;
import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.IRenew;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;

import com.sun.istack.NotNull;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.Positioning;
import engine.attribute.ArcAttribute;
import engine.attribute.NodeLayoutAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.TransitionAttribute;
import engine.data.JungData;
import engine.data.PetrinetData;
import engine.handler.NodeTypeEnum;
import engine.ihandler.IPetrinetManipulation;
import engine.ihandler.IPetrinetPersistence;
import engine.session.SessionManager;
import exceptions.EngineException;

/**
 * 
 * This is the implementation of all methods regarding petrinets by engine.
 * @see IPetrinetManipulation
 * @see IPetrinetPersistence
 * 
 * @author alex (aas772)
 * 
 */

final public class PetrinetHandler {

	private final SessionManager sessionManager;
	private static PetrinetHandler petrinetHandler;

	private PetrinetHandler() {
		sessionManager = SessionManager.getInstance();
	}

	protected static PetrinetHandler getInstance() {
		if (petrinetHandler == null) {
			petrinetHandler = new PetrinetHandler();
		}

		return petrinetHandler;
	}

	/**
	 * {@link IPetrinetManipulation#createArc(int, INode, INode)}
	 */
	public PreArc createPreArc(@NotNull int id, @NotNull Place place, @NotNull Transition transition)
			throws EngineException {
		
		checkIsPlace(place);
		checkIsTransition(transition);
		
		PetrinetData petrinetData = getPetrinetData(id);
		Petrinet 	 petrinet 	  = petrinetData.getPetrinet();
		JungData 	 jungData 	  = petrinetData.getJungData();

		// create new Arc
		PreArc arc = petrinet.addPreArc("undefined", place, transition);

		// call Jung
		try {
			jungData.createArc(arc, place, transition);
		} catch (IllegalArgumentException e) {
			exception("createPreArc - can not create Arc");
		}

		return arc;
	}

	/**
	 * {@link IPetrinetManipulation#createArc(int, INode, INode)}
	 */
	public PostArc createPostArc(@NotNull int id, @NotNull Transition transition, @NotNull Place place)
			throws EngineException {
		
		checkIsTransition(transition);
		checkIsPlace(place);
		
		PetrinetData petrinetData = getPetrinetData(id);
		Petrinet 	 petrinet 	  = petrinetData.getPetrinet();
		JungData 	 jungData 	  = petrinetData.getJungData();

		// create new Arc
		PostArc arc = petrinet.addPostArc("undefined", transition, place);

		// call Jung
		try {
			jungData.createArc(arc, transition, place);
		} catch (IllegalArgumentException e) {
			exception("createPostArc - can not create Arc");
		}

		return arc;
	}
	

	/**
	 * {@link IPetrinetManipulation#createPlace(int, Point2D)}
	 */
	public Place createPlace(@NotNull int id, @NotNull Point2D coordinate)
			throws EngineException {

		PetrinetData petrinetData = getPetrinetData(id);
		Petrinet 	 petrinet 	  = petrinetData.getPetrinet();
		JungData 	 jungData 	  = petrinetData.getJungData();

		if (!jungData.isCreatePossibleAt(coordinate)) {
			warning("Dort kann keine Stelle erstellt werden.");
			return null;
		}

		// create a new Place
		Place place = petrinet.addPlace("undefined");

		// call JungModificator
		try {
			jungData.createPlace(place, coordinate);
		} catch (IllegalArgumentException e) {
			exception("createPlace - can not create Place");
		}

		return place;
	}

	/**
	 * {@link IPetrinetManipulation#createPetrinet()}
	 */
	public int createPetrinet() {
		// new Petrinet
		Petrinet petrinet = PetrinetComponent.getPetrinet().createPetrinet();

		// SessionManger => create a new PetrinetData
		PetrinetData petrinetData = sessionManager.createPetrinetData(petrinet);

		// get the Id
		int idPetrinet = petrinetData.getId();

		return idPetrinet;
	}

	/**
	 * {@link IPetrinetManipulation#createTransition(int, Point2D)}
	 */
	public Transition createTransition(@NotNull int id, @NotNull Point2D coordinate)
			throws EngineException {

		PetrinetData petrinetData = getPetrinetData(id);
		Petrinet 	 petrinet 	  = petrinetData.getPetrinet();
		JungData 	 jungData 	  = petrinetData.getJungData();

		if (!jungData.isCreatePossibleAt(coordinate)) {
			warning("Dort kann keine Transition erstellt werden.");
			return null;
		}

		// create a new Transition
		Transition transition = petrinet.addTransition("undefined");

		// call JungModificator
		try {
			jungData.createTransition(transition, coordinate);
		} catch (IllegalArgumentException e) {
			exception("createTransition - can not create Transition");
		}

		return transition;
	}

	/**
	 * {@link IPetrinetManipulation#deleteArc(int, IArc)}
	 */
	public void deleteArc(@NotNull int id, @NotNull IArc arc)
			throws EngineException {

		checkIsIArc(arc);

		PetrinetData petrinetData = getPetrinetData(id);
		Petrinet 	 petrinet 	  = petrinetData.getPetrinet();
		JungData 	 jungData 	  = petrinetData.getJungData();

		try {
			petrinet.removeArc(arc.getId());

			// call JundModification and create Collection
			Collection<IArc> collArc = new HashSet<IArc>();
			collArc.add(arc);
			
			jungData.delete(collArc, new HashSet<INode>());
		} catch (IllegalArgumentException e) {
			exception("deleteArc - can not delete Arc");
		}
	}

	/**
	 * {@link IPetrinetManipulation#deletePlace(int, INode)}
	 */
	public void deletePlace(@NotNull int id, @NotNull Place place)
			throws EngineException {
		
		checkIsPlace(place);

		PetrinetData petrinetData = getPetrinetData(id);
		Petrinet 	 petrinet 	  = petrinetData.getPetrinet();
		JungData 	 jungData 	  = petrinetData.getJungData();

		petrinet.removePlace(place);	
		
		jungData.deleteDataOfMissingElements(petrinet);
	}

	/**
	 * {@link IPetrinetManipulation#deleteTransition(int, INode)}
	 */
	public void deleteTransition(@NotNull int id, @NotNull Transition transition)
			throws EngineException {
		
		checkIsTransition(transition);

		PetrinetData petrinetData = getPetrinetData(id);
		Petrinet 	 petrinet 	  = petrinetData.getPetrinet();
		JungData 	 jungData 	  = petrinetData.getJungData();

		petrinet.removeTransition(transition);
		
		jungData.deleteDataOfMissingElements(petrinet);
	}


	/**
	 * {@link IPetrinetManipulation#getArcAttribute(int, IArc)}
	 */
	public ArcAttribute getArcAttribute(@NotNull int id, @NotNull IArc arc) throws EngineException {
		checkIsIArc(arc);
		
		int weight = arc.getWeight();

		ArcAttribute arcAttribute = new ArcAttribute(weight);

		return arcAttribute;
	}

	/**
	 * {@link IPetrinetManipulation#getJungLayout(int)}
	 */
	public AbstractLayout<INode, IArc> getJungLayout(@NotNull int id)
			throws EngineException {

		PetrinetData petrinetData = getPetrinetData(id);
		JungData 	 jungData 	  = petrinetData.getJungData();

		AbstractLayout<INode, IArc> layout = jungData.getJungLayout();

		return layout;
	}

	/**
	 * {@link IPetrinetManipulation#getPlaceAttribute(int, INode)}
	 */
	public PlaceAttribute getPlaceAttribute(@NotNull int id,
			@NotNull Place place) throws EngineException {
		
		checkIsPlace(place);

		PetrinetData petrinetData = getPetrinetData(id);
		JungData 	 jungData 	  = petrinetData.getJungData();

		return new PlaceAttribute(
			place.getMark(), 
			place.getName(), 
			jungData.getPlaceColor(place)
		);
	}

	/**
	 * {@link IPetrinetManipulation#getTransitionAttribute(int, INode)}
	 */
	public TransitionAttribute getTransitionAttribute(@NotNull int id,
			@NotNull Transition transition) throws EngineException {
		
		checkIsTransition(transition);

		return new TransitionAttribute(
			transition.getTlb(), 
			transition.getName(), 
			transition.getRnw(), 
			transition.isActivated()
		);
	}

	/**
	 * {@link IPetrinetManipulation#moveGraph(int, Point2D)}
	 */
	public void moveGraph(@NotNull int id, @NotNull Point2D relativePosition)
			throws EngineException {

		PetrinetData petrinetData = getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("moveGraph - id of the Petrinet is wrong");
		} else {
			petrinetData.getJungData().moveGraph(relativePosition);
		}
	}

	/**
	 * {@link IPetrinetManipulation#moveNode(int, INode, Point2D)}
	 */
	public void moveNode(@NotNull int id, @NotNull INode node,
			@NotNull Point2D relativePosition) throws EngineException {

		PetrinetData petrinetData = getPetrinetData(id);
		JungData 	 jungData 	  = petrinetData.getJungData();

		try {
			jungData.moveNodeWithPositionCheck(
				node,
				Positioning.addPoints(jungData.getNodeLayoutAttributes()
						.get(node).getCoordinate(), relativePosition));
		} catch (IllegalArgumentException e) {
			throw new EngineException(e.getMessage());
		}
	}

	/**
	 * {@link IPetrinetManipulation#save(int, String, String, String, double)}
	 */
	public void save(@NotNull int id, @NotNull String path,
			@NotNull String filename, @NotNull String format, double nodeSize)
			throws EngineException {

		PetrinetData petrinetData = getPetrinetData(id);
		Petrinet 	 petrinet 	  = petrinetData.getPetrinet();
		JungData 	 jungData 	  = petrinetData.getJungData();

		Map<INode, NodeLayoutAttribute> nodeMap = jungData
				.getNodeLayoutAttributes();

		exceptionIf(nodeMap == null, "save - nodeMap == null");

		Persistence.savePetrinet(path + "/" + filename + "." + format,
				petrinet, nodeMap, nodeSize);
	}

	/**
	 * {@link IPetrinetManipulation#load(String, String)}
	 */
	public int load(@NotNull String path, @NotNull String filename) {

		return Persistence.loadPetrinet(
			path + "/" + filename,
			PetrinetPersistence.getInstance()
		);
	}

	/**
	 * {@link IPetrinetManipulation#setMarking(int, INode, int)}
	 */
	public void setMarking(@NotNull int id, @NotNull Place place,
			@NotNull int marking) throws EngineException {
		
		checkIsPlace(place);
		getPetrinetData(id);

		// set new marking
		place.setMark(marking);
	}

	/**
	 * {@link IPetrinetManipulation#setPname(int, INode, String)}
	 */
	public void setPname(@NotNull int id, @NotNull Place place,
			@NotNull String pname) throws EngineException {
		
		checkIsPlace(place);
		getPetrinetData(id);

		// set new Pname
		place.setName(pname);		
	}

	/**
	 * {@link IPetrinetManipulation#setTlb(int, INode, String)}
	 */
	public void setTlb(@NotNull int id, @NotNull Transition transition,
			@NotNull String tlb) throws EngineException {
		
		checkIsTransition(transition);
		getPetrinetData(id);

		transition.setTlb(tlb);
	}

	/**
	 * {@link IPetrinetManipulation#setTname(int, INode, String)}
	 */
	public void setTname(@NotNull int id, @NotNull Transition transition,
			@NotNull String tname) throws EngineException {
		
		checkIsTransition(transition);
		getPetrinetData(id);

		transition.setName(tname);
	}

	/**
	 * {@link IPetrinetManipulation#setWeight(int, IArc, int)}
	 */
	public void setWeight(@NotNull int id, @NotNull PreArc preArc, @NotNull int weight)
			throws EngineException {

		checkIsPreArc(preArc);
		getPetrinetData(id);

		// set new weight
		preArc.setWeight(weight);
	}

	/**
	 * {@link IPetrinetManipulation#setWeight(int, IArc, int)}
	 */
	public void setWeight(@NotNull int id, @NotNull PostArc postArc, @NotNull int weight)
			throws EngineException {

		checkIsPostArc(postArc);
		getPetrinetData(id);

		// set new weight
		postArc.setWeight(weight);
	}

	/**
	 * {@link IPetrinetManipulation#setRnw(int, INode, IRenew)}
	 */
	public void setRnw(int id, Transition transition, IRenew renews)
			throws EngineException {
		
		checkIsTransition(transition);
		getPetrinetData(id);

		transition.setRnw(renews);
	}

	/**
	 * {@link IPetrinetManipulation#setPlaceColor(int, INode, Color)}
	 */
	public void setPlaceColor(int id, Place place, Color color)
			throws EngineException {
		
		checkIsPlace(place);

		PetrinetData petrinetData = getPetrinetData(id);
		JungData 	 jungData 	  = petrinetData.getJungData();
		
		jungData.setPlaceColor(place, color);
	}

	/**
	 * {@link IPetrinetManipulation#closePetrinet(int)}
	 */
	public void closePetrinet(int id) throws EngineException {

		getPetrinetData(id);

		if (!sessionManager.closeSessionData(id)) {
			exception("closePetrinet - can not remove PetrinetData");
		}
	}

	/**
	 * {@link IPetrinetManipulation#getNodeType(INode)}
	 */
	public NodeTypeEnum getNodeType(@NotNull INode node) throws EngineException {
		if (node instanceof Place) {
			return NodeTypeEnum.Place;
		} else if (node instanceof Transition) {
			return NodeTypeEnum.Transition;
		} else {
			exception("getNodeType - wrong type");
			return null;
		}
	}

	/**
	 * @see {@link IPetrinetManipulation#moveGraphIntoVision(int)}
	 */
	public void moveGraphIntoVision(int id) throws EngineException {
		PetrinetData petrinetData = sessionManager.getPetrinetData(id);
		petrinetData.getJungData().moveGraphIntoVision();
	}

	/**
	 * @see {@link IPetrinetManipulation#moveAllNodesTo(int, float, Point)}
	 * @param id
	 * @param factor
	 * @param point
	 */
	public void moveAllNodesTo(int id, float factor, Point point) {
		PetrinetData petrinetData = sessionManager.getPetrinetData(id);
		petrinetData.getJungData().moveAllNodesTo(factor, point);
	}

	/**
	 * @see {@link IPetrinetManipulation#setNodeSize(int, double)}
	 * @param id
	 * @param nodeSize
	 */
	public void setNodeSize(int id, double nodeSize) {
		PetrinetData petrinetData = sessionManager.getPetrinetData(id);
		petrinetData.getJungData().setNodeSize(nodeSize);
	}

	/**
	 * @see {@link IPetrinetManipulation#getNodeSize(int)}
	 * 
	 * @param id
	 */
	public double getNodeSize(int id) {
		PetrinetData petrinetData = sessionManager.getPetrinetData(id);
		return petrinetData.getJungData().getNodeSize();
	}

	private PetrinetData getPetrinetData(int petrinetDataId) throws EngineException {
		PetrinetData petrinetData = sessionManager.getPetrinetData(petrinetDataId);

		// Test: is id valid
		if (petrinetData == null) {
			exception("id of the Petrinet is wrong");
			return null;
		}

		return petrinetData;
	}
	
	private void checkIsPlace(Place place) throws EngineException {
		exceptionIf(!(place instanceof Place), "this isn't a place");
	}
	
	private void checkIsTransition(Transition transition) throws EngineException {
		exceptionIf(!(transition instanceof Transition), "this isn't a transition");
	}
	
	private void checkIsPreArc(PreArc preArc) throws EngineException {
		exceptionIf(!(preArc instanceof PreArc), "this isn't a preArc");
	}
	
	private void checkIsPostArc(PostArc postArc) throws EngineException {
		exceptionIf(!(postArc instanceof PostArc), "this isn't a postArc");
	}
	
	private void checkIsIArc(IArc arc) throws EngineException {
		exceptionIf(!(arc instanceof IArc), "this isn't an arc");
	}
}
