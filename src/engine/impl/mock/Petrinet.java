package engine.impl.mock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import petrinetze.IArc;
import petrinetze.IGraphElement;
import petrinetze.INode;
import petrinetze.IPetrinet;
import petrinetze.IPetrinetListener;
import petrinetze.IPlace;
import petrinetze.IPost;
import petrinetze.IPre;
import petrinetze.IRenew;
import petrinetze.ITransition;

public class Petrinet implements IPetrinet {



	Set<IArc> arcs = new HashSet<IArc>();
	Set<ITransition> transitions = new HashSet<ITransition>();
	Set<IPlace> places = new HashSet<IPlace>();

	public Petrinet() {
		List<INode> nodes = new ArrayList<INode>();

		for(int i=0 ; i<3 ; i++) {
			ITransition t = new Transition();
			nodes.add(t);
			transitions.add(t);
		}
		for(int i=0 ; i<3 ; i++) {
			IPlace p = new Place();
			nodes.add(p);
			places.add(p);
		}

		arcs.add(new Arc(nodes.get(3), nodes.get(0),1));
		arcs.add(new Arc(nodes.get(0), nodes.get(4),1));
		arcs.add(new Arc(nodes.get(4), nodes.get(1),1));
		arcs.add(new Arc(nodes.get(4), nodes.get(2),1));
		arcs.add(new Arc(nodes.get(2), nodes.get(5),1));
		arcs.add(new Arc(nodes.get(1), nodes.get(3),1));

	}

	@Override
	public IPlace createPlace(String name) {

		return new Place();
	}

	@Override
	public void deletePlaceById(int id) {
		// TODO Auto-generated method stub

	}



	@Override
	public void deleteTransitionByID(int id) {
		// TODO Auto-generated method stub

	}



	@Override
	public void deleteArcByID(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<ITransition> getActivatedTransitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<INode> fire(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<INode> fire() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPre getPre() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPost getPost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<IPlace> getAllPlaces() {
		return places;
	}

	@Override
	public Set<ITransition> getAllTransitions() {
		return transitions;
	}

	@Override
	public Set<IArc> getAllArcs() {
		return arcs;
	}



	@Override
	public void addPetrinetListener(IPetrinetListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removePetrinetListener(IPetrinetListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public ITransition createTransition(String name, IRenew rnw) {

		return new Transition();
	}

    @Override
    public ITransition createTransition(String name) {
        return null;
    }

    @Override
    public IArc createArc(String name, INode start, INode end) {
        return new Arc(start, end, 1);
    }

    @Override
	public IGraphElement getAllGraphElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPlace getPlaceById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITransition getTransitionById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
