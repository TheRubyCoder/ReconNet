package petrinetze.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import petrinetze.ActionType;
import petrinetze.IArc;
import petrinetze.IGraphElement;
import petrinetze.INode;
import petrinetze.IPetrinet;
import petrinetze.IPetrinetListener;
import petrinetze.IPlace;
import petrinetze.IPost;
import petrinetze.IPre;
import petrinetze.ITransition;

public class Petrinet implements IPetrinet {
	
	private final Set<IPetrinetListener> listeners = new HashSet<IPetrinetListener>();
	
	
	@Override
	public IPlace createPlace(String name) {
		return new Place();
	}

	@Override
	public void deletePlaceById(int id) {

	}

	@Override
	public ITransition createTransition(String name) {
		return new Transition();

	}

	@Override
	public void deleteTransitionByID(int id) {
		
	}

	@Override
	public IArc createArc() {
		final IArc arc = null;
		fireChanged(arc);
		return arc;
	}

	@Override
	public void deleteArcByID(int id) {

	}

	@Override
	public Set<ITransition> getActivatedTransitions() {
		return null;
	}

	@Override
	public Set<INode> fire(int id) {
		return null;
	}

	@Override
	public Set<INode> fire() {
		return null;
	}

	@Override
	public IPre getPre() {
		return null;
	}

	@Override
	public IPost getPost() {
		return null;
	}

	@Override
	public int getId() {
		return 0;
	}

	private void fireChanged(INode element) {
		final List<IPetrinetListener> listeners;
		
		synchronized (this.listeners){
			listeners = new ArrayList<IPetrinetListener>(this.listeners);
		}
		
		for (IPetrinetListener l : listeners)  {
			l.changed(this, element, ActionType.changed);
		}
	}

	@Override
	public Set<IPlace> getAllPlaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ITransition> getAllTransitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IArc> getAllArcs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IGraphElement> getAllGraphicElement() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addPetrinetListener(IPetrinetListener l) {
		// TODO Auto-generated method stub
		
	}

	public void removePetrinetListener(IPetrinetListener l) {
		// TODO Auto-generated method stub
		
	}


}
