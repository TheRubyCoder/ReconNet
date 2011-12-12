package engine.handler;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import petrinet.Arc;
import petrinet.INode;
import petrinet.IRenew;
import petrinet.Petrinet;
import petrinet.PetrinetComponent;
import petrinet.Place;
import petrinet.Transition;

import com.sun.istack.NotNull;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.NodeLayoutAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.TransitionAttribute;
import engine.data.JungData;
import engine.data.PetrinetData;
import engine.ihandler.IPetrinetManipulation;
import engine.jungmodification.JungModification;
import engine.session.SessionManager;
import engine.util.Utility;
import exceptions.EngineException;

public class PetrinetHandler implements IPetrinetManipulation {

	private final SessionManager sessionManager;
	private final JungModification jungModification;
	private final Utility utility;

	public PetrinetHandler() {
		sessionManager = SessionManager.getInstance();
		jungModification = JungModification.getInstance();
		utility = Utility.getInstance();
	}

	@Override
	public boolean createArc(@NotNull int id,@NotNull INode from,@NotNull INode to) throws EngineException {

		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		
		// Test: is id valid
		if(petrinetData == null){
			exception("PetrinetHandler[46] - id of the Petrinet is wrong");
		} else {
		
			Petrinet petrinet = petrinetData.getPetrinet();
			JungData jungData = petrinetData.getJungData();
	
			if (this.getNodeType(from).equals(NodeType.Place)
					&& this.getNodeType(to).equals(NodeType.Transition)) {
				// place => transition
	
				// cast objects
				Place fromPlace = (Place) from;
				Transition toTransition = (Transition) to;
	
				// create new Arc
				Arc arc = petrinet.createArc("undefined", fromPlace, toTransition);
	
				// ***************************
				// TODO: ChangedPetrinetResult
				// ***************************
	
				// call JungModification
				try {
					jungModification.createArc(jungData, arc, fromPlace, toTransition);
				} catch (IllegalArgumentException e) {
					// TODO : write text
					exception("");
				}
			} else if (this.getNodeType(from).equals(NodeType.Transition)
					&& this.getNodeType(to).equals(NodeType.Place)) {
				// transition => place
	
				// cast objects
				Transition fromTransition = (Transition) from;
				Place toPlace = (Place) to;
	
				// create new Arc
				Arc arc = petrinet.createArc("undefined", fromTransition, toPlace);
	
				// ***************************
				// TODO: ChangedPetrinetResult
				// ***************************
	
				// call JungModification
				try {
					jungModification.createArc(jungData, arc, fromTransition, toPlace);
				} catch (IllegalArgumentException e) {
					// TODO : write text
					exception("");
				}
			} else {
				// else => error!
	
				// TODO: EngineException
			}
		}
		// TODO: with ChangedPetrinetResult true/false
		return true;
	}

	@Override
	public boolean createPlace(@NotNull int id,@NotNull Point2D coordinate) throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrninetData(id);

		// Test: is id valid
		if(petrinetData == null){
			exception("PetrinetHandler[111] - id of the Petrinet is wrong");
		} else {
			Petrinet petrinet = petrinetData.getPetrinet();
			JungData jungData = petrinetData.getJungData();
			
			// create a new Place
			Place newPlace = petrinet.createPlace("undefined");
	
			// ***************************
			// TODO: ChangedPetrinetResult
			// ***************************
	
			// call JungModificator
			try {
				jungModification.createPlace(jungData, newPlace, coordinate);
			} catch (IllegalArgumentException e) {
				// TODO : write text
				exception("");
			}
		}
		
		// TODO: with ChangedPetrinetResult true/false
		return true;
	}

	@Override
	public int createPetrinet() {
		// new Petrinet
		Petrinet petrinet = PetrinetComponent.getPetrinet().createPetrinet();

		// SessionManger => create a new PetrinetData
		PetrinetData petrinetData = sessionManager.createPetrinetData(petrinet);

		// get the Id
		int idPetrinet = petrinetData.getId();

		return idPetrinet;
	}

	@Override
	public boolean createTransition(@NotNull int id,@NotNull Point2D coordinate) throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		
		// Test: is id valid
		if(petrinetData == null){
			exception("PetrinetHandler[154] - id of the Petrinet is wrong");
		} else {
			
			Petrinet petrinet = petrinetData.getPetrinet();
			JungData jungData = petrinetData.getJungData();
	
			// create a new Place
			Transition newTransition = petrinet.createTransition("undefined");
	
			// ***************************
			// TODO: ChangedPetrinetResult
			// ***************************
	
			// call JungModificator
			try {
				jungModification.createTransition(jungData, newTransition, coordinate);
			} catch (IllegalArgumentException e) {
				// TODO : write text
				exception("");
			}
		}
		
		// TODO: with ChangedPetrinetResult true/false
		return true;
	}

	@Override
	public boolean deleteArc(@NotNull int id,@NotNull Arc arc) throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		
		// Test: is id valid
		if(petrinetData == null){
			exception("PetrinetHandler[185] - id of the Petrinet is wrong");
		} else {
			
			Petrinet petrinet = petrinetData.getPetrinet();
			JungData jungData = petrinetData.getJungData();
			
			// TODO: ChangePetrinetResult
			petrinet.deleteArcByID(arc.getId());
			
			Collection<Arc> collArc = new HashSet<Arc>();
			collArc.add(arc);
			
			Collection<INode> collINodes = new HashSet<INode>();
			
			try {
				jungModification.delete(jungData, collArc, collINodes);
			} catch (IllegalArgumentException e) {
				// TODO : write text
				exception("");
			}

		}
		
		return true;
	}

	@Override
	public boolean deletePlace(@NotNull int id,@NotNull INode place) {
		// TODO : write with ChangePetrinetResult
		return true;
	}

	@Override
	public boolean deleteTransition(@NotNull int id,@NotNull INode transition) {
		// TODO : write with ChangePetrinetResult
		return true;
	}

	@Override
	public ArcAttribute getArcAttribute(@NotNull int id,@NotNull Arc arc) {

		// get the Petrinet from the id and SessionManager
		// PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		// Petrinet petrinet = petrinetData.getPetrinet();
		// JungData jungData = petrinetData.getJungData();

		int weight = arc.getMark();

		ArcAttribute arcAttribute = new ArcAttribute(weight);

		return arcAttribute;

	}

	@Override
	public AbstractLayout<INode, Arc> getJungLayout(@NotNull int id) throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrninetData(id);

		// Test: is id valid
		if(petrinetData == null){
			exception("PetrinetHandler[243] - id of the Petrinet is wrong");
		} else {
		
			Petrinet petrinet = petrinetData.getPetrinet();
			JungData jungData = petrinetData.getJungData();

		}
			
		// TODO: Mathias =)

		return null;
	}

	@Override
	public PlaceAttribute getPlaceAttribute(@NotNull int id,@NotNull INode place) {

		// get the Petrinet from the id and SessionManager
		// PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		// Petrinet petrinet = petrinetData.getPetrinet();
		// JungData jungData = petrinetData.getJungData();

		if (this.getNodeType(place).equals(NodeType.Place)) {
			Place p = (Place) place;

			int marking = p.getMark();
			String pname = p.getName();

			PlaceAttribute placeAttribute = new PlaceAttribute(marking, pname);

			return placeAttribute;
		}

		return null;
	}

	@Override
	public TransitionAttribute getTransitionAttribute(@NotNull int id,@NotNull INode transition) {

		// get the Petrinet from the id and SessionManager
		// PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		// Petrinet petrinet = petrinetData.getPetrinet();
		// JungData jungData = petrinetData.getJungData();

		if (this.getNodeType(transition).equals(NodeType.Transition)) {
			Transition t = (Transition) transition;

			String tlb = t.getTlb();
			String tname = t.getName();
			IRenew rnw = t.getRnw();

			TransitionAttribute transitionAttribute = new TransitionAttribute(
					tlb, tname, rnw);

			return transitionAttribute;
		}

		return null;
	}

	@Override
	public boolean moveGraph(@NotNull int id,@NotNull Point2D relativePosition) throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		
		// Test: is id valid
		if(petrinetData == null){
			exception("PetrinetHandler[310] - id of the Petrinet is wrong");
		} else {
		
			Petrinet petrinet = petrinetData.getPetrinet();
			JungData jungData = petrinetData.getJungData();
	
			// step 1 alles durchgehen kleinstes x,y ; groeßstes x,y
			Map<INode, NodeLayoutAttribute> layoutMap = utility
					.getNodeLayoutAttributes(jungData);
	
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
	
				Point2D point = new Point2D.Double(
						addX + nodeLayoutAttribute.getCoordinate().getX(), 
						addY + nodeLayoutAttribute.getCoordinate().getY());
	
				try {
					jungModification.moveNode(jungData, p, point);
				} catch (IllegalArgumentException e) {
					// TODO : write text
					exception("");
				}
			}
	
			// For all Transitions
			Set<Transition> transitions = petrinet.getAllTransitions();
			for (Transition t : transitions) {
				NodeLayoutAttribute nodeLayoutAttribute = layoutMap.get(t);
	
				Point2D point = new Point2D.Double(
						addX + nodeLayoutAttribute.getCoordinate().getX(), 
						addY + nodeLayoutAttribute.getCoordinate().getY());
	
				try {
					jungModification.moveNode(jungData, t, point);
				} catch (IllegalArgumentException e) {
					// TODO : write text
					exception("");
				}
			}

		}
			
		return true;
	}

	@Override
	public boolean moveNode(@NotNull int id,@NotNull INode node,@NotNull Point2D relativePosition) throws EngineException {
		
		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		
		// Test: is id valid
		if(petrinetData == null){
			exception("PetrinetHandler[393] - id of the Petrinet is wrong");
		} else {
		
			JungData jungData = petrinetData.getJungData();
	
			// step 1 alles durchgehen kleinstes x,y ; groeßstes x,y
			Map<INode, NodeLayoutAttribute> layoutMap = utility
					.getNodeLayoutAttributes(jungData);
			
			NodeLayoutAttribute nla = layoutMap.get(node);
			
			double newPointX = nla.getCoordinate().getX() + relativePosition.getX();
			double newPointY = nla.getCoordinate().getY() + relativePosition.getY();
			
			if(newPointX < 0 || newPointY < 0){
				
				double x = relativePosition.getX() - newPointX;
				double y = relativePosition.getY() - newPointY;
	
				Point2D point = new Point2D.Double(x, y);
				
				try {
					jungModification.moveNode(jungData, node, point);
				} catch (IllegalArgumentException e) {
					// TODO : write text
					exception("");
				}
				
			} else {
				
				Point2D point = new Point2D.Double(newPointX, newPointY);
				
				try {
					jungModification.moveNode(jungData, node, point);
				} catch (IllegalArgumentException e) {
					// TODO : write text
					exception("");
				}
				
			}

		}	
		
		return true;
	}

	@Override
	public boolean save(@NotNull int id,@NotNull String path,@NotNull String filename,@NotNull String format) {

		// TODO: Alex... warte auf das Persistence-Team

		return false;
	}

	@Override
	public boolean setMarking(@NotNull int id,@NotNull INode place,@NotNull int marking) throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		
		// Test: is id valid
		if(petrinetData == null){
			exception("PetrinetHandler[448] - id of the Petrinet is wrong");
		} else {
		
			JungData jungData = petrinetData.getJungData();
	
			if (this.getNodeType(place).equals(NodeType.Place)) {
				// cast object
				Place p = (Place) place;
	
				// set new marking
				p.setMark(marking);
	
				// call JungModification
				try {
					jungModification.updatePlace(jungData, p);
				} catch (IllegalArgumentException e) {
					// TODO : write text
					exception("");
				}
	
				return true;
			} else {
				return false;
			}

		}
		
		return true;
		
	}

	@Override
	public boolean setPname(@NotNull int id,@NotNull INode place,@NotNull String pname) throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		
		// Test: is id valid
		if(petrinetData == null){
			exception("PetrinetHandler[484] - id of the Petrinet is wrong");
		} else {
		
			JungData jungData = petrinetData.getJungData();
	
			if (this.getNodeType(place).equals(NodeType.Place)) {
				// cast object
				Place p = (Place) place;
	
				// set new Pname
				p.setName(pname);
	
				// call JungModification
				try {
					jungModification.updatePlace(jungData, p);
				} catch (IllegalArgumentException e) {
					// TODO : write text
					exception("");
				}
	
				return true;
			} else {
				return false;
			}

		}
		
		return true;
			
	}

	@Override
	public boolean setTlb(@NotNull int id,@NotNull INode transition,@NotNull String tlb) throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		
		// Test: is id valid
		if(petrinetData == null){
			exception("PetrinetHandler[520] - id of the Petrinet is wrong");
		} else {
		
			JungData jungData = petrinetData.getJungData();
	
			if (this.getNodeType(transition).equals(NodeType.Transition)) {
				// cast object
				Transition t = (Transition) transition;
	
				// set new Tlb
				t.setTlb(tlb);
	
				// call JungModification
				try {
					jungModification.updateTransition(jungData, t);
				} catch (IllegalArgumentException e) {
					// TODO : write text
					exception("");
				}
	
				return true;
			} else {
				return false;
			}

		}
		
		return true;
			
	}

	@Override
	public boolean setTname(@NotNull int id,@NotNull INode transition,@NotNull String tname) throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		
		// Test: is id valid
		if(petrinetData == null){
			exception("PetrinetHandler[556] - id of the Petrinet is wrong");
		} else {
		
			JungData jungData = petrinetData.getJungData();
	
			if (this.getNodeType(transition).equals(NodeType.Transition)) {
				// cast object
				Transition t = (Transition) transition;
	
				// set new Tname
				t.setName(tname);
	
				// call JungModification
				try {
					jungModification.updateTransition(jungData, t);
				} catch (IllegalArgumentException e) {
					// TODO : write text
					exception("");
				}
				
				return true;
			} else {
				return false;
			}

		}
		
		return true;
			
	}

	@Override
	public boolean setWeight(@NotNull int id,@NotNull Arc arc,@NotNull int weight) throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		
		// Test: is id valid
		if(petrinetData == null){
			exception("PetrinetHandler[592] - id of the Petrinet is wrong");
		} else {
		
			JungData jungData = petrinetData.getJungData();
	
			// set new weight
			arc.setMark(weight);
	
			// call JungModification
			try {
				jungModification.updateArc(jungData, arc);
			} catch (IllegalArgumentException e) {
				// TODO : write text
				exception("");
			}

		}
			
		return true;

	}

	@Override
	public Enum<?> getNodeType(@NotNull INode node) {

		if (node instanceof Place) {
			return NodeType.Place;
		} else if (node instanceof Transition) {
			return NodeType.Transition;
		} else {
			return null;
		}

	}
	
	private void exception(@NotNull String value) throws EngineException {
		throw new EngineException(value);
	}
	
}
