package engine.handler;

import java.awt.geom.Point2D;

import petrinet.Arc;
import petrinet.INode;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.TransitionAttribute;
import engine.ihandler.IPetrinetManipulation;

public class PetrinetHandler implements IPetrinetManipulation {

	@Override
	public boolean createArc(int id, INode from, INode to) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createPlace(int id, Point2D coordinate) {
		/*
		 * 	GUI ruft PetrinetHandler.create[Place|Transition](...) auf
			PetrinetHandler holt PetrinetData vom SessionManager
			Handler ruft auf dem Petrinet create[Place|Transition] auf
			das Petrinetz liefert ein ChangedPetrinetResult, wobei: (das Element der jeweiligen Collection entspricht dem hinzugefügten Knoten)
				Collection<Arc>.size() == 0
				Collection<Place>.size() == 1 bei createPlace sonst 0
				Collection<Transition>.size() == 1 bei createTransition sonst 0
			wenn createPlace, dann JungModification.createPlace(...)
			wenn createTransition, dann JungModification.createTransition(...)
			Rückgabewert (sagt der GUI, dass etwas geändert wurde) :
				true: im Erfolgsfall
				false: bei Fehler 
		 */
		
		
		
		
		return false;
	}

	@Override
	public int createPetrinet() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean createTransition(int id, Point2D coordinate) {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractLayout<INode, Arc> getJungLayout(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlaceAttribute getPlaceAttribute(int id, INode place) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransitionAttribute getTransitionAttribute(int id, INode transition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean moveGraph(int id, Point2D relativePosition) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveNode(int id, INode node, Point2D relativePosition) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean save(int id, String path, String filename, String format) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setMarking(int id, INode place, int marking) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setPname(int id, INode place, String pname) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setTlb(int id, INode transition, String tlb) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setTname(int id, INode transition, String tname) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setWeight(int id, Arc arc, int weight) {
		// TODO Auto-generated method stub
		return false;
	}

}
