package engine.handler;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import petrinet.Arc;
import petrinet.INode;
import petrinet.IRenew;
import petrinet.Petrinet;
import petrinet.PetrinetComponent;
import petrinet.Place;
import petrinet.Transition;
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
	public boolean createArc(int id, INode from, INode to) {

		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
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
			jungModification.createArc(jungData, arc, fromPlace, toTransition);
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
			jungModification.createArc(jungData, arc, fromTransition, toPlace);
		} else {
			// else => error!

			// TODO: EngineException
		}

		// TODO: with ChangedPetrinetResult true/false
		return true;
	}

	@Override
	public boolean createPlace(int id, Point2D coordinate) {

		// get the Petrinet from the ip and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		Petrinet petrinet = petrinetData.getPetrinet();
		JungData jungData = petrinetData.getJungData();

		// create a new Place
		Place newPlace = petrinet.createPlace("undefined");

		// ***************************
		// TODO: ChangedPetrinetResult
		// ***************************

		// call JungModificator
		jungModification.createPlace(jungData, newPlace, coordinate);

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
	public boolean createTransition(int id, Point2D coordinate) {

		// get the Petrinet from the ip and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		Petrinet petrinet = petrinetData.getPetrinet();
		JungData jungData = petrinetData.getJungData();

		// create a new Place
		Transition newTransition = petrinet.createTransition("undefined");

		// ***************************
		// TODO: ChangedPetrinetResult
		// ***************************

		// call JungModificator
		jungModification.createTransition(jungData, newTransition, coordinate);

		// TODO: with ChangedPetrinetResult true/false
		return true;
	}

	@Override
	public boolean deleteArc(int id, Arc arc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletePlace(int id, INode place) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteTransition(int id, INode transition) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArcAttribute getArcAttribute(int id, Arc arc) {
		
		// get the Petrinet from the ip and SessionManager
//		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
//		Petrinet petrinet = petrinetData.getPetrinet();
//		JungData jungData = petrinetData.getJungData();
		
		int weight = arc.getMark();
		
		ArcAttribute arcAttribute = new ArcAttribute(weight);
		
		return arcAttribute;
		
	}

	@Override
	public AbstractLayout<INode, Arc> getJungLayout(int id) {

		// get the Petrinet from the ip and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		Petrinet petrinet = petrinetData.getPetrinet();
		JungData jungData = petrinetData.getJungData();
		
		// TODO: Mathias =)
		
		return null;
	}

	@Override
	public PlaceAttribute getPlaceAttribute(int id, INode place) {

		// get the Petrinet from the ip and SessionManager
//		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
//		Petrinet petrinet = petrinetData.getPetrinet();
//		JungData jungData = petrinetData.getJungData();
		
		if(this.getNodeType(place).equals(NodeType.Place)){
			Place p = (Place) place;
			
			int marking = p.getMark();
			String pname = p.getName();
			
			PlaceAttribute placeAttribute = new PlaceAttribute(marking, pname);
			
			return placeAttribute;
		}
		
		return null;
	}

	@Override
	public TransitionAttribute getTransitionAttribute(int id, INode transition) {

		// get the Petrinet from the ip and SessionManager
//		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
//		Petrinet petrinet = petrinetData.getPetrinet();
//		JungData jungData = petrinetData.getJungData();
		
		if(this.getNodeType(transition).equals(NodeType.Transition)){
			Transition t = (Transition) transition;
			
			String tlb = t.getTlb();
			String tname = t.getName();
			IRenew rnw = t.getRnw();
			
			TransitionAttribute transitionAttribute = new TransitionAttribute(tlb, tname, rnw);
			
			return transitionAttribute;
		}
		
		return null;
	}

	@Override
	public boolean moveGraph(int id, Point2D relativePosition) {
		
		// get the Petrinet from the ip and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		Petrinet petrinet = petrinetData.getPetrinet();
		JungData jungData = petrinetData.getJungData();
		
		// step 1 alles durchgehen kleinstes x,y ; groeßstes x,y
		Map<INode, NodeLayoutAttribute> layoutMap = utility.getNodeLayoutAttributes(jungData);
		
		double smallestX = Double.MAX_VALUE;
		double smallestY = Double.MAX_VALUE;
		double biggestX = 0;
		double biggestY = 0;
		
		Collection<NodeLayoutAttribute> entrySet = layoutMap.values();
		
		for(NodeLayoutAttribute n : entrySet){
			double x = n.getCoordinate().getX();
			double y = n.getCoordinate().getY();
			
			if(x < smallestX) smallestX = x;
			if(x > biggestX) biggestX = x;
			
			if(y < smallestY) smallestY = y;
			if(y > biggestY) biggestY = y;
		}
		
		// wenn die box aus dem breich lauft wird sie an 0/0 angedockt
		
		// TODO: weiter machen!!!
		
		// step 2 alles durchgehen jungModifcation.moveNode(...)
		
		return false;
	}

	@Override
	public boolean moveNode(int id, INode node, Point2D relativePosition) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean save(int id, String path, String filename, String format) {
		
		// TODO: Alex
		
		return false;
	}

	@Override
	public boolean setMarking(int id, INode place, int marking) {

		// get the Petrinet from the ip and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		Petrinet petrinet = petrinetData.getPetrinet();
		JungData jungData = petrinetData.getJungData();

		if (this.getNodeType(place).equals(NodeType.Place)) {
			// cast object
			Place p = (Place) place;

			// set new marking
			p.setMark(marking);

			// call JungModification
			jungModification.updatePlace(jungData, p);

			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean setPname(int id, INode place, String pname) {

		// get the Petrinet from the ip and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		Petrinet petrinet = petrinetData.getPetrinet();
		JungData jungData = petrinetData.getJungData();

		if (this.getNodeType(place).equals(NodeType.Place)) {
			// cast object
			Place p = (Place) place;

			// set new Pname
			p.setName(pname);

			// call JungModification
			jungModification.updatePlace(jungData, p);

			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean setTlb(int id, INode transition, String tlb) {

		// get the Petrinet from the ip and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		Petrinet petrinet = petrinetData.getPetrinet();
		JungData jungData = petrinetData.getJungData();

		if (this.getNodeType(transition).equals(NodeType.Transition)) {
			// cast object
			Transition t = (Transition) transition;

			// set new Tlb
			t.setTlb(tlb);

			// call JungModification
			jungModification.updateTransition(jungData, t);

			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean setTname(int id, INode transition, String tname) {

		// get the Petrinet from the ip and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		Petrinet petrinet = petrinetData.getPetrinet();
		JungData jungData = petrinetData.getJungData();

		if (this.getNodeType(transition).equals(NodeType.Transition)) {
			// cast object
			Transition t = (Transition) transition;

			// set new Tname
			t.setName(tname);

			// call JungModification
			jungModification.updateTransition(jungData, t);

			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean setWeight(int id, Arc arc, int weight) {

		// get the Petrinet from the ip and SessionManager
		PetrinetData petrinetData = sessionManager.getPetrninetData(id);
		Petrinet petrinet = petrinetData.getPetrinet();
		JungData jungData = petrinetData.getJungData();

		// set new weight
		arc.setMark(weight);

		// call JungModification
		jungModification.updateArc(jungData, arc);

		return true;

	}

	@Override
	public Enum getNodeType(INode node) {

		if (node instanceof Place) {
			return NodeType.Place;
		} else if (node instanceof Transition) {
			return NodeType.Transition;
		} else {
			return null;
		}

	}

}
