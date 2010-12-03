package petrinetze.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
import petrinetze.IRenew;
import petrinetze.ITransition;

public class Petrinet implements IPetrinet {
	
	private int id;
	private final Set<IPetrinetListener> listeners = new HashSet<IPetrinetListener>();
	private Set<IPlace> places;
	private Set<ITransition> transitions;
	private Set<IArc> arcs;
	private IGraphElement graphElements;
	
	
//	public Petrinet(int id) {
//		this.id = id;
//		places = new HashSet<IPlace>();
//		transitions = new HashSet<ITransition>();
//		arcs = new HashSet<IArc>();
//		graphElements = new GraphElement();
//	}

	
	
	public Petrinet() {
		id = UUID.getnID();
		places = new HashSet<IPlace>();
		transitions = new HashSet<ITransition>();
		arcs = new HashSet<IArc>();
		graphElements = new GraphElement();
	}



	@Override
	public IPlace createPlace(String name) {
		final Place p = new Place(UUID.getpID());
		p.setName(name);
		places.add(p);
		onNodeChanged(p, ActionType.added);
		return p;
	}

	@Override
	public void deletePlaceById(int id) {
		IPlace toBeDelete = null;
		for (IPlace p : places) {
			if (p.getId() == id) {
				toBeDelete = p;
				break;
			}
		}
		if (toBeDelete != null) {
			places.remove(toBeDelete);
			onNodeChanged(toBeDelete, ActionType.deleted);
		}
	}

	@Override
	public ITransition createTransition(String name, IRenew rnw) {
		Transition t = new Transition(UUID.getpID(), rnw, this);
		t.setName(name);
		transitions.add(t);
		onNodeChanged(t, ActionType.added);
		return t;

	}

	@Override
	public void deleteTransitionByID(int id) {
		ITransition toBeDelete = null;
		for (ITransition t : transitions) {
			if (t.getId() == id) {
				toBeDelete = t;
				break;
			}
		}
		if (toBeDelete != null) {
			transitions.remove(toBeDelete);
			onNodeChanged(toBeDelete, ActionType.deleted);
		}
		
	}

	@Override
	public IArc createArc(String name) {
		final IArc arc = new Arc(UUID.getaID(), this);
		arc.setName(name);
		arcs.add(arc);
		//fireChanged(arc);
		onEdgeChanged(arc, ActionType.added);
		return arc;
	}

	@Override
	public void deleteArcByID(int id) {
		IArc toBeDelete = null;
		for (IArc a : arcs) {
			if (a.getId() == id) {
				toBeDelete = a;
				break;
			}
		}
		if (toBeDelete != null) {
			arcs.remove(toBeDelete);
			onEdgeChanged(toBeDelete, ActionType.deleted);
		}
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
	
	//PRE in der t-ten Spalte gibt an,
	//wieviele Token die Transition t von p wegnimmt
	@Override
	public IPre getPre() {
		
		//IPre pre = new Pre();
		//TODO: abaendern von Aufruf
		IPre pre = new Pre(0,0);
		
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

	/*private void fireChanged(INode element) {
		final List<IPetrinetListener> listeners;
		
		synchronized (this.listeners){
			listeners = new ArrayList<IPetrinetListener>(this.listeners);
		}
		
		for (IPetrinetListener l : listeners)  {
			l.changed(this, element, ActionType.changed);
		}
	}*/

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
	public IGraphElement getAllGraphElement() {
		final Set<INode> nodes = new HashSet<INode>();
		final Set<IArc> tarcs = new HashSet<IArc>();
		for (INode node : places) {
			nodes.add(node);
		}
		for (INode node : transitions) {
			nodes.add(node);
		}
		for (IArc arc : arcs) {
			tarcs.add(arc);
		}
		((GraphElement) graphElements).setNodes(nodes);
		((GraphElement) graphElements).setArcs(tarcs);
		
		return graphElements;
	}

	public void addPetrinetListener(IPetrinetListener l) {
		listeners.add(l);
		
	}

	public void removePetrinetListener(IPetrinetListener l) {
		if(listeners.contains(l))
			listeners.remove(l);
		
	}
	
	/**
	 * Notify all listeners, that a node has changed.
	 * This will also be called by all Nodes in this petrinet.
	 * @param element the element which has changed
	 * @param actionType the ActionType
	 */
	void onNodeChanged(INode element, ActionType actionType)
	{
		Set<IPetrinetListener> tempListeners = new HashSet<IPetrinetListener>(listeners);
		for(IPetrinetListener listener : tempListeners)
			listener.changed(this, element, actionType);
	}

	/**
	 * Notify all listeners, that a node has changed.
	 * This will also be called by Edges in this petrinet.
	 * @param element the element which has changed
	 * @param actionType the ActionType
	 */
	void onEdgeChanged(IArc element, ActionType actionType)
	{
		Set<IPetrinetListener> tempListeners = new HashSet<IPetrinetListener>(listeners);
		for(IPetrinetListener listener : tempListeners)
			listener.changed(this, element, actionType);
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Petrinet [id=" + id + "\n\t places=" + places + "\n\t transitions="
				+ transitions + "\n\t arcs=" + arcs + "]";
	}


	
}
