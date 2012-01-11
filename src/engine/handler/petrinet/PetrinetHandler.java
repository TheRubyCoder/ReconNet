package engine.handler.petrinet;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import persistence.Persistence;
import petrinet.Arc;
import petrinet.ElementType;
import petrinet.INode;
import petrinet.IRenew;
import petrinet.Petrinet;
import petrinet.PetrinetComponent;
import petrinet.Place;
import petrinet.RenewCount;
import petrinet.RenewId;
import petrinet.Renews;
import petrinet.Transition;

import com.sun.istack.NotNull;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.NodeLayoutAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.TransitionAttribute;
import engine.data.JungData;
import engine.data.PetrinetData;
import engine.handler.NodeTypeEnum;
import engine.session.SessionManager;
import exceptions.EngineException;

/**
 * 
 * This is the implementation of all methods for the Petrinet by engine.
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

	public Arc createArc(@NotNull int id, @NotNull INode from, @NotNull INode to)
			throws EngineException {

		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("createArc - id of the Petrinet is wrong");

			return null;
		} else {

			Petrinet petrinet = petrinetData.getPetrinet();
			JungData jungData = petrinetData.getJungData();

			if (this.getNodeType(from).equals(NodeTypeEnum.Place)
					&& this.getNodeType(to).equals(NodeTypeEnum.Transition)) {
				// place => transition

				// cast objects
				Place fromPlace = (Place) from;
				Transition toTransition = (Transition) to;

				// create new Arc
				Arc arc = petrinet.createArc("undefined", fromPlace,
						toTransition);

				// ***************************
				// TODO: ChangedPetrinetResult
				// discuss: what to do
				// ***************************

				// call Jung
				try {
					jungData.createArc(arc, fromPlace, toTransition);
				} catch (IllegalArgumentException e) {
					exception("createArc - can not create Arc");
				}

				return arc;
			} else if (this.getNodeType(from).equals(NodeTypeEnum.Transition)
					&& this.getNodeType(to).equals(NodeTypeEnum.Place)) {
				// transition => place

				// cast objects
				Transition fromTransition = (Transition) from;
				Place toPlace = (Place) to;

				// create new Arc
				Arc arc = petrinet.createArc("undefined", fromTransition,
						toPlace);

				// ***************************
				// TODO: ChangedPetrinetResult
				// discuss: what to do
				// ***************************

				// call Jung
				try {
					jungData.createArc(arc, fromTransition, toPlace);
				} catch (IllegalArgumentException e) {
					exception("createArc - can not create Arc");
				}

				return arc;
			} else {
				exception("createArc - wrong combi");

				return null;
			}
		}

	}

	public INode createPlace(@NotNull int id, @NotNull Point2D coordinate)
			throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("createPlace - id of the Petrinet is wrong");

			return null;
		} else {
			Petrinet petrinet = petrinetData.getPetrinet();
			JungData jungData = petrinetData.getJungData();

			if (jungData.isCreatePossibleAt(coordinate)) {

				// create a new Place
				Place newPlace = petrinet.createPlace("undefined");

				// ***************************
				// TODO: ChangedPetrinetResult
				// discuss: what to do
				// ***************************

				// call JungModificator
				try {
					jungData.createPlace(newPlace, coordinate);
				} catch (IllegalArgumentException e) {
					exception("createPlace - can not create Place");
				}

				System.out.println(petrinetData.getPetrinet()
						.getAllGraphElement().getAllNodes());

				return newPlace;

			} else {

				exception("createPlace - can not create Place");
				return null;

			}
		}

	}

	public int createPetrinet() {

		// new Petrinet
		Petrinet petrinet = PetrinetComponent.getPetrinet().createPetrinet();

		// SessionManger => create a new PetrinetData
		PetrinetData petrinetData = sessionManager.createPetrinetData(petrinet);

		// get the Id
		int idPetrinet = petrinetData.getId();

		return idPetrinet;

	}

	public INode createTransition(@NotNull int id, @NotNull Point2D coordinate)
			throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("createTransition - id of the Petrinet is wrong");

			return null;
		} else {

			Petrinet petrinet = petrinetData.getPetrinet();
			JungData jungData = petrinetData.getJungData();

			if (jungData.isCreatePossibleAt(coordinate)) {

				// create a new Place
				Transition newTransition = petrinet
						.createTransition("undefined");

				// ***************************
				// TODO: ChangedPetrinetResult
				// discuss: what to do
				// ***************************

				// call JungModificator
				try {
					jungData.createTransition(newTransition, coordinate);
				} catch (IllegalArgumentException e) {
					exception("createTransition - can not create Transition");
				}

				return newTransition;

			} else {
				
				exception("createTransition - can not create Transition");
				return null;

			}
			
		}

	}

	public void deleteArc(@NotNull int id, @NotNull Arc arc)
			throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("deleteArc - id of the Petrinet is wrong");
		} else {

			Petrinet petrinet = petrinetData.getPetrinet();
			JungData jungData = petrinetData.getJungData();

			petrinet.deleteElementById(arc.getId());

			// call JundModification and create Collection
			Collection<Arc> collArc = new HashSet<Arc>();
			collArc.add(arc);

			Collection<INode> collINodes = new HashSet<INode>();

			try {
				jungData.delete(collArc, collINodes);
			} catch (IllegalArgumentException e) {
				exception("deleteArc - can not delete Arc");
			}

		}

	}

	public void deletePlace(@NotNull int id, @NotNull INode place)
			throws EngineException {
		this.deleteInternal(id, place);
	}

	public void deleteTransition(@NotNull int id, @NotNull INode transition)
			throws EngineException {
		this.deleteInternal(id, transition);
	}

	private void deleteInternal(@NotNull int id, @NotNull INode node)
			throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("deleteInternal - id of the Petrinet is wrong");
		} else {

			Petrinet petrinet = petrinetData.getPetrinet();
			JungData jungData = petrinetData.getJungData();

			// all deleted elements, returned by Petrinet
			Collection<Integer> allDelElemFromPetrinet = petrinet
					.giveAllDeleteElem(node.getId());

			if (allDelElemFromPetrinet.size() == 0)
				exception("deleteInternal - no items in delColl?!");

			Iterator<Integer> iter = allDelElemFromPetrinet.iterator();
			Collection<Arc> collArc = new HashSet<Arc>();
			Collection<INode> collINodes = new HashSet<INode>();

			while (iter.hasNext()) {

				int idOfElem = iter.next();
				ElementType type = petrinet.getNodeType(idOfElem);

				// test type of Element
				if (type.equals(ElementType.ARC)) {
					Arc a = petrinet.getArcById(idOfElem);
					if (a == null)
						exception("deleteInternal - arc is null");
					collArc.add(a);
				} else if (type.equals(ElementType.PLACE)) {
					Place p = petrinet.getPlaceById(idOfElem);
					if (p == null)
						exception("deleteInternal - place is null");
					collINodes.add(p);
				} else if (type.equals(ElementType.TRANSITION)) {
					Transition t = petrinet.getTransitionById(idOfElem);
					if (t == null)
						exception("deleteInternal - transition is null");
					collINodes.add(t);
				} else {
					exception("deleteInternal - invalid type from id: "
							+ idOfElem + ", type: " + type.toString());
				}

			}

			try {
				jungData.delete(collArc, collINodes);
			} catch (IllegalArgumentException e) {
				exception("deleteInternal - can not delete Place/Transition");
			}

			// know kill all
			petrinet.deleteElementById(node.getId());

		}

	}

	public ArcAttribute getArcAttribute(@NotNull int id, @NotNull Arc arc) {

		int weight = arc.getMark();

		ArcAttribute arcAttribute = new ArcAttribute(weight);

		return arcAttribute;

	}

	public AbstractLayout<INode, Arc> getJungLayout(@NotNull int id)
			throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("PetrinetHandler - id of the Petrinet is wrong");
			return null;
		} else {

			JungData jungData = petrinetData.getJungData();

			AbstractLayout<INode, Arc> layout = jungData.getJungLayout();

			System.out.println("layout: " + layout.getGraph());

			return layout;

		}

	}

	public PlaceAttribute getPlaceAttribute(@NotNull int id,
			@NotNull INode place) throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("PetrinetHandler - id of the Petrinet is wrong");
			return null;
		} else {

			JungData jungData = petrinetData.getJungData();

			if (this.getNodeType(place).equals(NodeTypeEnum.Place)) {
				Place p = (Place) place;

				int marking = p.getMark();
				String pname = p.getName();
				Color color = jungData.getPlaceColor(p);

				PlaceAttribute placeAttribute = new PlaceAttribute(marking,
						pname, color);

				return placeAttribute;
			}

		}

		return null;
	}

	public TransitionAttribute getTransitionAttribute(@NotNull int id,
			@NotNull INode transition) throws EngineException {

		if (this.getNodeType(transition).equals(NodeTypeEnum.Transition)) {
			Transition t = (Transition) transition;

			String tlb = t.getTlb();
			String tname = t.getName();
			IRenew rnw = t.getRnw();
			boolean isActivated = t.isActivated();

			TransitionAttribute transitionAttribute = new TransitionAttribute(
					tlb, tname, rnw, isActivated);

			return transitionAttribute;
		}

		return null;
	}

	public void moveGraph(@NotNull int id, @NotNull Point2D relativePosition)
			throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("moveGraph - id of the Petrinet is wrong");
		} else {

			Petrinet petrinet = petrinetData.getPetrinet();
			JungData jungData = petrinetData.getJungData();

			// step 1 alles durchgehen kleinstes x,y ; groeßstes x,y
			Map<INode, NodeLayoutAttribute> layoutMap = jungData
					.getNodeLayoutAttributes();

			double smallestX = Double.MAX_VALUE;
			double smallestY = Double.MAX_VALUE;
			double biggestX = 0;
			double biggestY = 0;

			Collection<NodeLayoutAttribute> entrySet = layoutMap.values();

			for (NodeLayoutAttribute n : entrySet) {
				double x = n.getCoordinate().getX();
				double y = n.getCoordinate().getY();

				if (x < smallestX)
					smallestX = x;
				if (x > biggestX)
					biggestX = x;

				if (y < smallestY)
					smallestY = y;
				if (y > biggestY)
					biggestY = y;
			}

			double smallestXMove = smallestX + relativePosition.getX();
			double smallestYMove = smallestY + relativePosition.getY();

			double addX = relativePosition.getX();
			double addY = relativePosition.getY();

			// Move Graph
			if (smallestXMove < 0 || smallestYMove < 0) {
				addX = relativePosition.getX() - smallestXMove;
				addY = relativePosition.getY() - smallestYMove;
			}

			// For all Places
			Set<Place> places = petrinet.getAllPlaces();
			for (Place p : places) {
				NodeLayoutAttribute nodeLayoutAttribute = layoutMap.get(p);

				Point2D point = new Point2D.Double(addX
						+ nodeLayoutAttribute.getCoordinate().getX(), addY
						+ nodeLayoutAttribute.getCoordinate().getY());

				try {
					jungData.moveNode(p, point);
				} catch (IllegalArgumentException e) {
					exception("moveGraph - can not move Node");
				}
			}

			// For all Transitions
			Set<Transition> transitions = petrinet.getAllTransitions();
			for (Transition t : transitions) {
				NodeLayoutAttribute nodeLayoutAttribute = layoutMap.get(t);

				Point2D point = new Point2D.Double(addX
						+ nodeLayoutAttribute.getCoordinate().getX(), addY
						+ nodeLayoutAttribute.getCoordinate().getY());

				try {
					jungData.moveNode(t, point);
				} catch (IllegalArgumentException e) {
					exception("moveGraph - can not move Node");
				}
			}

		}

	}

	public void moveNode(@NotNull int id, @NotNull INode node,
			@NotNull Point2D relativePosition) throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("moveNode - id of the Petrinet is wrong");
		} else {

			JungData jungData = petrinetData.getJungData();

			// step 1 alles durchgehen kleinstes x,y ; groeßstes x,y
			Map<INode, NodeLayoutAttribute> layoutMap = jungData
					.getNodeLayoutAttributes();

			NodeLayoutAttribute nla = layoutMap.get(node);

			double newPointX = nla.getCoordinate().getX()
					+ relativePosition.getX();
			double newPointY = nla.getCoordinate().getY()
					+ relativePosition.getY();

			if (newPointX < 0 || newPointY < 0) {

				double x = relativePosition.getX() - newPointX;
				double y = relativePosition.getY() - newPointY;

				Point2D point = new Point2D.Double(x, y);

				try {
					jungData.moveNode(node, point);
				} catch (IllegalArgumentException e) {
					exception("moveNode - can not move Node");
				}

			} else {

				Point2D point = new Point2D.Double(newPointX, newPointY);

				try {
					jungData.moveNode(node, point);
				} catch (IllegalArgumentException e) {
					exception("moveNode - can not move Node");
				}

			}

		}

	}

	public void save(@NotNull int id, @NotNull String path,
			@NotNull String filename, @NotNull String format)
			throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("save - id of the Petrinet is wrong");
		} else {

			Petrinet petrinet = petrinetData.getPetrinet();
			JungData jungData = petrinetData.getJungData();

			Map<INode, NodeLayoutAttribute> nodeMap = jungData
					.getNodeLayoutAttributes();

			checkNodeLayoutAttribute(nodeMap == null, "save - nodeMap == null");

			Persistence.savePetrinet(path + "/" + filename + "." + format,
					petrinet, nodeMap);

		}

	}

	public int load(@NotNull String path, @NotNull String filename) {

		int id = Persistence.loadPetrinet(path + "/" + filename,
				PetrinetPersistence.getInstance());

		return id;

	}

	public void setMarking(@NotNull int id, @NotNull INode place,
			@NotNull int marking) throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("setMarking - id of the Petrinet is wrong");
		} else {

			if (this.getNodeType(place).equals(NodeTypeEnum.Place)) {
				// cast object
				Place p = (Place) place;

				// set new marking
				p.setMark(marking);

			}

		}

	}

	public void setPname(@NotNull int id, @NotNull INode place,
			@NotNull String pname) throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("setPname - id of the Petrinet is wrong");
		} else {

			if (this.getNodeType(place).equals(NodeTypeEnum.Place)) {
				// cast object
				Place p = (Place) place;

				// set new Pname
				p.setName(pname);

			}

		}

	}

	public void setTlb(@NotNull int id, @NotNull INode transition,
			@NotNull String tlb) throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("setTlb - id of the Petrinet is wrong");
		} else {

			if (this.getNodeType(transition).equals(NodeTypeEnum.Transition)) {
				// cast object
				Transition t = (Transition) transition;

				// set new Tlb
				t.setTlb(tlb);

			}

		}

	}

	public void setTname(@NotNull int id, @NotNull INode transition,
			@NotNull String tname) throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("setTname - id of the Petrinet is wrong");
		} else {

			if (this.getNodeType(transition).equals(NodeTypeEnum.Transition)) {
				// cast object
				Transition t = (Transition) transition;

				// set new Tname
				t.setName(tname);

			}

		}

	}

	public void setWeight(@NotNull int id, @NotNull Arc arc, @NotNull int weight)
			throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("setWeight - id of the Petrinet is wrong");
		} else {

			// set new weight
			arc.setMark(weight);

		}

	}

	public void setRnw(int id, INode transition, Renews renews)
			throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("setRnw - id of the Petrinet is wrong");
		} else {

			Transition t = (Transition) transition;

			IRenew rnw;

			if (renews.equals(Renews.COUNT)) {

				rnw = new RenewCount();
				t.setRnw(rnw);

			} else if (renews.equals(Renews.IDENTITY)) {

				rnw = new RenewId();
				t.setRnw(rnw);

			} else if (false) {

				// Todo: map

			} else {
				exception("setRnw - renews is not correct");
			}

		}

	}

	public void setPlaceColor(int id, INode place, Color color)
			throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("setPlaceColor - id of the Petrinet is wrong");
		} else {

			JungData jungData = petrinetData.getJungData();

			jungData.setPlaceColor((Place) place, color);

			// PetrinetData petrinetDataNew =
			// sessionManager.getPetrinetData(id);
			// JungData jungDataNew = petrinetDataNew.getJungData();
			//
			// System.out.println(jungDataNew.getJungGraph());
			//
		}

	}

	public void closePetrinet(int id) throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrinetData(id);

		// Test: is id valid
		if (petrinetData == null) {
			exception("closePetrinet - id of the Petrinet is wrong");
		} else {

			if (!sessionManager.closeSessionData(id)) {
				exception("closePetrinet - can not remove PetrinetData");
			}

		}

	}

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

	private void exception(@NotNull String value) throws EngineException {
		throw new EngineException("PetrinetHandler: " + value);
	}

	private void checkNodeLayoutAttribute(boolean value, String errorMessage)
			throws EngineException {
		if (value) {
			exception(errorMessage);
		}
	}

}
