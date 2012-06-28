package petrinet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.transaction.TransactionRequiredException;

import exceptions.ShowAsWarningException;

/**
 * The petrinet compose {@link Place places}, {@link Transition transitions} and
 * {@link Arc arcs} to bundle most logical functionalities of a petrinet.
 */
public class Petrinet {

	/**
	 * Unique id
	 */
	private int id;

	/**
	 * Listeners attached to this petrinet
	 */
	private final Set<IPetrinetListener> listeners = new HashSet<IPetrinetListener>();

	/**
	 * Places of this petrinet
	 */
	private Set<Place> places;

	/**
	 * Transitions of this petrinet
	 */
	private Set<Transition> transitions;

	/**
	 * Arcs between and {@link Petrinet#places places} and
	 * {@link Petrinet#transitions transitions} of this petrinet
	 */
	private Set<Arc> arcs;

	/**
	 * Random number generator used to fire a random transition
	 */
	private Random random = new Random();

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Petrinet))
			return false;
		Petrinet other = (Petrinet) obj;
		if (!getPre().matrixStringOnly().equals(
				other.getPre().matrixStringOnly()))
			return false;
		if (!getPost().matrixStringOnly().equals(
				other.getPost().matrixStringOnly()))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	/**
	 * Creates an empty petrinet
	 */
	Petrinet() {
		id = UUID.getnID();
		places = new HashSet<Place>();
		transitions = new HashSet<Transition>();
		arcs = new HashSet<Arc>();
	}

	/**
	 * Adds a new {@link Place} to the petrinet
	 * 
	 * @param name
	 *            Name of the place
	 * @return The new {@link Place}
	 */
	public Place createPlace(String name) {
		final Place place = new Place(UUID.getpID());
		place.setName(name);
		places.add(place);
		onNodeChanged(place, ActionType.added);
		return place;
	}

	/**
	 * Deletes an place referenced by id
	 * 
	 * @param id
	 *            id of the place that will be deleted
	 */
	public void deletePlaceById(int id) {
		Place toBeDelete = null;
		for (Place p : places) {
			if (p.getId() == id) {
				toBeDelete = p;
				break;
			}
		}
		if (toBeDelete != null) {
			List<Arc> start = toBeDelete.getStartArcs();
			List<Arc> end = toBeDelete.getEndArcs();
			// delete outgoing arcs
			for (Arc arc : start) {
				deleteArc(arc);
			}
			// delete incoming arcs
			for (Arc arc : end) {
				deleteArc(arc);
			}

			places.remove(toBeDelete);
			onNodeChanged(toBeDelete, ActionType.deleted);
		}
	}

	/**
	 * Deletes an arc from the petrinet
	 * 
	 * @param arc
	 *            {@link Arc} to be removed
	 */
	private void deleteArc(Arc arc) {
		if (arc.getStart() != null && arc.getStart() instanceof Transition) {
			((Transition) arc.getStart()).removeStartArc(arc);
		} else if (arc.getEnd() != null && arc.getEnd() instanceof Transition) {
			((Transition) arc.getEnd()).removeEndArc(arc);
		}

		arcs.remove(arc);
		onEdgeChanged(arc, ActionType.deleted);
	}

	/**
	 * Returns an {@link Arc} referenced by id
	 * 
	 * @param id
	 *            Id of the {@link Arc}
	 * @return <code>null</code> if {@link Arc} is not in petrinet
	 */
	public Arc getArcById(int id) {
		for (Arc arc : getAllArcs()) {
			if (arc.getId() == id) {
				return arc;
			}
		}
		return null;
	}

	/**
	 * Deletes any element of this petrinet that has the <code>id</code>
	 * 
	 * @param id
	 *            Id of the element that will be deleted
	 * @return Collection<Integer> of integers with ids of elements that has
	 *         been deleted
	 * @example A place with id 1 has two arcs with id 2 and 3 attached. When
	 *          the place is deleted the return value will be [1,2,3]
	 */
	public Collection<Integer> deleteElementById(int id) {
		List<Integer> result = new ArrayList<Integer>();
		if (getNodeType(id) == ElementType.ARC) {
			result.add(id);
			Arc arc = getArcById(id);
			// ein-/ausgehende Kanten der Transitionen loeschen
			if (arc.getStart() != null) {
				if (arc.getStart() instanceof Transition) {
					((Transition) arc.getStart()).removeStartArc(arc);
				} else if (arc.getStart() instanceof Place) {
					((Place) arc.getStart()).removeStartArc(arc);
				}
			}
			if (arc.getEnd() != null) {
				if (arc.getEnd() instanceof Transition) {
					((Transition) arc.getEnd()).removeEndArc(arc);
				} else if (arc.getEnd() instanceof Place) {
					((Place) arc.getEnd()).removeEndArc(arc);
				}
			}

			getAllArcs().remove(arc);
			onEdgeChanged(arc, ActionType.deleted);
		} else if (getNodeType(id) == ElementType.PLACE) {
			Place place = getPlaceById(id);

			Collection<Arc> copyOfStartArcs = new ArrayList<Arc>(
					place.getStartArcs());
			Collection<Arc> copyOfEndArcs = new ArrayList<Arc>(
					place.getEndArcs());
			for (Arc arc : copyOfStartArcs) {
				result.addAll(deleteElementById(arc.getId()));
			}
			for (Arc arc : copyOfEndArcs) {
				result.addAll(deleteElementById(arc.getId()));
			}

			result.add(place.getId());
			getAllPlaces().remove(place);
			onNodeChanged(place, ActionType.deleted);
		} else if (getNodeType(id) == ElementType.TRANSITION) {
			Transition transition = getTransitionById(id);
			result.add(transition.getId());
			Collection<Arc> copyOfStartArcs = new ArrayList<Arc>(
					transition.getStartArcs());
			Collection<Arc> copyOfEndArcs = new ArrayList<Arc>(
					transition.getEndArcs());
			for (Arc arc : copyOfStartArcs) {
				result.addAll(deleteElementById(arc.getId()));
			}
			for (Arc arc : copyOfEndArcs) {
				result.addAll(deleteElementById(arc.getId()));
			}

			getAllTransitions().remove(transition);
			onNodeChanged(transition, ActionType.deleted);
		}

		return result;
	}

	/**
	 * Checks whether a node or arc is part of this petrinet
	 */
	public boolean contains(INode nodeOrArc) {
		return getAllArcs().contains(nodeOrArc)
				|| getAllPlaces().contains(nodeOrArc)
				|| getAllTransitions().contains(nodeOrArc);
	}

	/**
	 * Just like {@link Petrinet#deleteElementById(int)} but does <b>not</b>
	 * delete any element. It just returns the approriate Collection<Integer>
	 * without altering the petrinet
	 */
	public Collection<Integer> peekDeleteElementById(int id) {
		List<Integer> result = new ArrayList<Integer>();
		result.add(id);
		if (getNodeType(id) == ElementType.ARC) {
			result.add(id);

		} else if (getNodeType(id) == ElementType.PLACE) {
			Place place = getPlaceById(id);

			Collection<Arc> copyOfStartArcs = new ArrayList<Arc>(
					place.getStartArcs());
			Collection<Arc> copyOfEndArcs = new ArrayList<Arc>(
					place.getEndArcs());
			for (Arc arc : copyOfStartArcs) {
				result.addAll(peekDeleteElementById(arc.getId()));
			}
			for (Arc arc : copyOfEndArcs) {
				result.addAll(peekDeleteElementById(arc.getId()));
			}

			result.add(place.getId());
		} else if (getNodeType(id) == ElementType.TRANSITION) {
			Transition transition = getTransitionById(id);
			result.add(transition.getId());
			Collection<Arc> copyOfStartArcs = new ArrayList<Arc>(
					transition.getStartArcs());
			Collection<Arc> copyOfEndArcs = new ArrayList<Arc>(
					transition.getEndArcs());
			for (Arc arc : copyOfStartArcs) {
				result.addAll(peekDeleteElementById(arc.getId()));
			}
			for (Arc arc : copyOfEndArcs) {
				result.addAll(peekDeleteElementById(arc.getId()));
			}

		}

		return result;
	}

	/**
	 * Adds a new {@link Transition} to this petrinet
	 * 
	 * @param name
	 *            Name of the {@link Transition}
	 * @param rnw
	 *            {@link IRenew Renew} of the {@link Transition}
	 * @return The new {@link Transition}
	 */
	public Transition createTransition(String name, IRenew rnw) {
		Transition transition = new Transition(UUID.gettID(), rnw, this);
		transition.setName(name);
		transitions.add(transition);
		onNodeChanged(transition, ActionType.added);
		return transition;
	}

	/**
	 * Creates a Transition with {@link RenewId id} as a renew
	 * 
	 * @param name
	 *            Name of the {@link Transition}
	 * @return The new {@link Transition}
	 */
	public Transition createTransition(String name) {
		return createTransition(name, Renews.IDENTITY);
	}

	/**
	 * Deletes a {@link Transition} referenced by id
	 * 
	 * @param id
	 *            The id of the {@link Transition} that will be deleted
	 */
	public void deleteTransitionByID(int id) {
		Transition toBeDelete = null;
		for (Transition t : transitions) {
			if (t.getId() == id) {
				toBeDelete = t;
				break;
			}
		}
		if (toBeDelete != null) {
			final List<Arc> arcs = new LinkedList<Arc>();
			arcs.addAll(toBeDelete.getStartArcs());
			arcs.addAll(toBeDelete.getEndArcs());

			for (Arc arc : arcs) {
				deleteArc(arc);
			}

			transitions.remove(toBeDelete);
			onNodeChanged(toBeDelete, ActionType.deleted);
		}
	}

	/**
	 * Adds a new {@link Arc} to the petrinet
	 * 
	 * @param name
	 *            Name of the {@link Arc}
	 * @param start
	 *            start {@link INode node} of the arc
	 * @param end
	 *            target {@link INode node} of the arc
	 * @return The new {@link Arc}
	 * @throws ShowAsWarningException with user friendly message if start and end are of same type
	 */
	public Arc createArc(String name, INode start, INode end) {

		if (start.getClass().equals(end.getClass())) {
			throw new ShowAsWarningException(
					"Kanten nur zwischen Stellen und Transitionen, nicht zwischen Knoten des gleichen Typs.");
		}

		final Arc arc = new Arc(UUID.getaID(), this, start, end);
		// Fuege Arc in die Startliste von Transition hinzu
		if (start instanceof Transition) {
			((Transition) start).setStartArcs(arc);
		}
		// Fuege Arc in die Endliste von Transition hinzu
		if (end instanceof Transition) {
			((Transition) end).setEndArcs(arc);
		}

		// Fuege Arc in die Startliste von Place hinzu
		if (start instanceof Place) {
			((Place) start).setStartArcs(arc);
		}
		// Fuege Arc in die Endliste von Place hinzu
		if (end instanceof Place) {
			((Place) end).setEndArcs(arc);
		}

		arc.setName(name);
		arcs.add(arc);
		// fireChanged(arc);
		onEdgeChanged(arc, ActionType.added);
		return arc;
	}

	public void deleteArcByID(int id) {
		Arc toBeDelete = null;
		for (Arc a : arcs) {
			if (a.getId() == id) {
				toBeDelete = a;
				break;
			}
		}
		if (toBeDelete != null) {
			deleteArc(toBeDelete);
		}
	}

	/**
	 * Returns all active {@link Transition transitions} of this petrinet
	 * 
	 * @return Empty Set<Transition> if no transitions are active
	 */
	public Set<Transition> getActivatedTransitions() {
		// Eine Transition ist aktiviert bzw. schaltbereit, falls sich
		// in allen Eingangsstellen mindestens so viele Marken befinden,
		// wie die Transition Kosten verursacht und alle Ausgangsstellen
		// noch genug Kapazitaet haben, um die neuen Marken aufnehmen zu
		// koennen.
		Set<Transition> activitedTransitions = new HashSet<Transition>();
		for (Transition t : transitions) {
			if (isActivited(t)) {
				activitedTransitions.add(t);
			}
		}
		return activitedTransitions;
	}

	/**
	 * Checks whether a transition is active or not. Notice: There are no
	 * capacities for nodes so the post nodes are always ok
	 * 
	 * @return
	 */
	private boolean isActivited(Transition t) {
		List<Arc> incoming = t.getEndArcs();
		for (Arc a : incoming) {
			Place p = (Place) a.getStart();

			if (p.getMark() < a.getMark()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Fires a {@link Transition} with the <code>id</code>
	 * 
	 * @param id
	 *            Id of the {@link Transition} that will be fired
	 * @return Changed nodes
	 */
	public Set<INode> fire(int id) {
		// Get the transition
		Transition transition = null;
		for (Transition t : transitions) {
			if (t.getId() == id) {
				transition = t;
				break;
			}
		}

		// Get all pre and post places and check if they have enough tokens
		Map<Place, Integer> inc = new HashMap<Place, Integer>();
		Map<Place, Integer> out = new HashMap<Place, Integer>();
		for (Arc arc : arcs) {
			if (arc.getEnd().equals(transition)) {
				if (arc.getMark() > ((Place) arc.getStart()).getMark()) {
					throw new IllegalArgumentException("The place "
							+ arc.getStart() + " does not have enough tokens");
				}
				inc.put((Place) arc.getStart(), arc.getMark());
			} else if (arc.getStart().equals(transition)) {
				out.put((Place) arc.getEnd(), arc.getMark());
			}
		}

		// set tokens
		for (Entry<Place, Integer> e : inc.entrySet())
			e.getKey().setMark(e.getKey().getMark() - e.getValue());
		for (Entry<Place, Integer> e : out.entrySet())
			e.getKey().setMark(e.getKey().getMark() + e.getValue());

		// all changed nodes
		Set<INode> changedNodes = new HashSet<INode>(inc.keySet());
		changedNodes.addAll(out.keySet());

		// Renew
		transition.rnw();

		// fire event
		fireChanged(changedNodes, ActionType.changed);

		return changedNodes;
	}

	/**
	 * Fires the {@link Petrinet#onNodeChanged(INode, ActionType)} event for all
	 * <code>nodes</code>
	 * 
	 * @param nodes
	 *            Nodes that have been changed
	 * @param action
	 *            changed, deleted or added?
	 */
	private void fireChanged(Iterable<INode> nodes, ActionType action) {
		for (INode node : nodes) {
			try {
				onNodeChanged(node, action);
			} catch (Exception ex) {
				throw new ShowAsWarningException(ex);
			}
		}
	}

	/**
	 * Fires a random active {@link Transition}
	 * 
	 * @return Changed nodes
	 * @throws IllegalStateException
	 *             if no {@link Transition} is active
	 */
	public Set<INode> fire() {
		List<Transition> active = new ArrayList<Transition>(
				getActivatedTransitions());

		if (active.isEmpty()) {
			throw new IllegalStateException("No activated transitions");
		}

		// We use an iterator here because we do not know, wich indexes are in
		// the set
		// and this way we will get a random one without knowing its id.
		int id = random.nextInt(active.size());

		return fire(active.get(id).getId());
	}

	/**
	 * Returns <tt> true </tt> if there are no Transitions or Places in the
	 * petrinet
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return this.getAllPlaces().isEmpty()
				&& this.getAllTransitions().isEmpty();
	}

	/**
	 * Liefert das Pre-Objekt zu dem Netz zurueck PRE in der t-ten Spalte gibt
	 * an, wieviele Token die Transition t von p wegnimmt
	 * 
	 * @return {@link Pre}
	 */
	public Pre getPre() {
		// Initialisierung
		int[] pId = new int[places.size()];

		int i = 0;
		// Fuellen mit den entsprechenden Ids
		for (Place place : places) {
			pId[i] = place.getId();
			i++;
		}
		// Initialisierung
		int[] tId = new int[transitions.size()];

		int z = 0;
		// Fuellen mit den entsprechenden Ids
		for (Transition transition : transitions) {
			tId[z] = transition.getId();
			z++;
		}

		// Initialisierung mit 0
		int[][] preValues = init(places.size(), transitions.size());
		// Zu jeder Transition holen wir die Stellen und
		// merken uns das in der perValueMatrix (sagt Florian)
		for (Transition t : transitions) {
			// Das Pre von der entsprechenden Transition
			// D.h., alle Stellen aus dem Vorbereich
			// Das Ergebnis wird in Form eines Hashtables geliefert
			// und zwar <Id, Mark> der Stelle
			final Hashtable<Integer, Integer> prePlaces = t.getPre();
			// Findet den Index der Transition in Pre-Matrix (Array)
			int tmpTransitionId = getIdFromArray(t.getId(), tId);
			//
			for (Entry<Integer, Integer> e : prePlaces.entrySet()) {
				int tmpPlaceId = getIdFromArray(e.getKey().intValue(), pId);

				if (tmpPlaceId >= 0 && tmpTransitionId >= 0) {
					preValues[tmpPlaceId][tmpTransitionId] = e.getValue()
							.intValue();
				}
			}
		}
		return new Pre(preValues, pId, tId);
	}

	/**
	 * Findet die Position der Id in der Id-Liste
	 * 
	 * @param intValue
	 *            = Id der Transition/Stelle
	 * @param ids
	 *            = Liste aller Ids Transition/Stelle
	 * @return position der Transition/Stelle
	 */
	private int getIdFromArray(int intValue, int[] ids) {
		for (int i = 0; i <= ids.length; i++) {
			if (ids[i] == intValue) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Initialisiert ein zweidimensionales Array mit Nullen
	 * 
	 * @param m
	 *            Groesse der 1. Dimension
	 * @param n
	 *            Groesse der 2. Dimension
	 * @return initialisiertes Array
	 */
	private int[][] init(int m, int n) {
		int tmp[][] = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int z = 0; z < n; z++) {
				tmp[i][z] = 0;
			}
		}
		return tmp;
	}

	/**
	 * Liefert das Post-Objekt zu dem Netz zurueck
	 * 
	 * @return {@link IPost}
	 */
	public Post getPost() {
		// Initialisierung
		int[] pId = new int[places.size()];

		int i = 0;
		// Fuellen mit den entsprechenden Ids
		for (Place place : places) {
			pId[i] = place.getId();
			i++;
		}
		// Initialisierung
		int[] tId = new int[transitions.size()];

		int z = 0;
		// Fuellen mit den entsprechenden Ids
		for (Transition transition : transitions) {
			tId[z] = transition.getId();
			z++;
		}

		// Initialisierung mit 0
		int[][] postValues = init(places.size(), transitions.size());
		// Zu jeder Transition holen wir die Stellen und
		// merken uns das in der perValueMatrix (sagt Florian)
		for (Transition t : transitions) {
			// Das Post von der entsprechenden Transition
			// D.h., alle Stellen aus dem Nachbereich
			// Das Ergebnis wird in Form eines Hashtables geliefert
			// und zwar <Id, Mark> der Stelle
			final Hashtable<Integer, Integer> postPlaces = t.getPost();
			// Findet den Index der Transition in Post-Matrix (Array)
			int tmpTransitionId = getIdFromArray(t.getId(), tId);
			for (Entry<Integer, Integer> e : postPlaces.entrySet()) {
				int tmpPlaceId = getIdFromArray(e.getKey().intValue(), pId);

				if (tmpPlaceId >= 0 && tmpTransitionId >= 0) {
					postValues[tmpPlaceId][tmpTransitionId] = e.getValue()
							.intValue();
				}
			}
		}
		return new Post(postValues, pId, tId);
	}

	public int getId() {
		return this.id;
	}

	public Set<Place> getAllPlaces() {
		return places;
	}

	public Set<Transition> getAllTransitions() {
		return transitions;
	}

	public Set<Arc> getAllArcs() {
		return arcs;
	}

	public IGraphElement getAllGraphElement() {
		final Set<INode> nodes = new HashSet<INode>();
		final Set<Arc> tarcs = new HashSet<Arc>();
		for (INode node : places) {
			nodes.add(node);
		}
		for (INode node : transitions) {
			nodes.add(node);
		}
		for (Arc arc : arcs) {
			tarcs.add(arc);
		}

		GraphElement graphElements = new GraphElement();
		((GraphElement) graphElements).setNodes(nodes);
		((GraphElement) graphElements).setArcs(tarcs);

		return graphElements;
	}

	/**
	 * Attaches a {@link IPetrinetListener} to this petrinet
	 * 
	 * @param listener
	 */
	public void addPetrinetListener(IPetrinetListener listener) {
		listeners.add(listener);

	}

	public void removePetrinetListener(IPetrinetListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Notify all listeners, that a node has changed. This will also be called
	 * by all Nodes in this petrinet.
	 * 
	 * @param element
	 *            the element which has changed
	 * @param actionType
	 *            the ActionType
	 */
	void onNodeChanged(INode element, ActionType actionType) {
		Set<IPetrinetListener> tempListeners = new HashSet<IPetrinetListener>(
				listeners);
		for (IPetrinetListener listener : tempListeners) {
			listener.changed(this, element, actionType);
		}
	}

	/**
	 * Notify all listeners, that a node has changed. This will also be called
	 * by Edges in this petrinet.
	 * 
	 * @param element
	 *            the element which has changed
	 * @param actionType
	 *            the ActionType
	 */
	void onEdgeChanged(Arc element, ActionType actionType) {
		Set<IPetrinetListener> tempListeners = new HashSet<IPetrinetListener>(
				listeners);
		for (IPetrinetListener listener : tempListeners) {
			listener.changed(this, element, actionType);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result = "Petrinet [id=" + id + "\n\t places= [";
		for (Place place : places) {
			result += "\n\t\t" + place;
		}
		result += "\n\t]\n\t transitions=[";
		for (Transition transition : transitions) {
			result += "\n\t\t" + transition;
		}
		result += "\n\t]\n\t arcs=[";
		for (Arc arc : arcs) {
			result += "\n\t\t" + arc;

		}
		return result + "\n\t]";
	}

	/**
	 * Returns the {@link Place} with <code>id</code>
	 * 
	 * @param id
	 *            Id of the {@link Place}
	 * @return <code>null</code> if {@link Place} is not in this petrinet
	 */
	public Place getPlaceById(int id) {
		for (Place p : places) {
			if (p.getId() == id) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Returns the {@link Transition} with <code>id</code>
	 * 
	 * @param id
	 *            Id of the {@link Transition}
	 * @return <code>null</code> if {@link Transition} is not in this petrinet
	 */
	public Transition getTransitionById(int id) {
		for (Transition t : transitions) {
			if (t.getId() == id) {
				return t;
			}
		}
		return null;
	}

	/**
	 * Returns the {@link INode} with <code>id</code>
	 * 
	 * @param id
	 *            Id of the {@link INode}
	 * @return <code>null</code> if {@link INode} is not in this petrinet
	 */
	public INode getNodeById(int id){
		Transition transition = getTransitionById(id);
		return transition != null ? transition : getPlaceById(id);
	}

	/**
	 * Returns all incident arcs to a node referenced by its <code>id</code>
	 * 
	 * @param id
	 *            Id of the references node
	 * @return Empty List of there is no node with that <code>id</code>
	 */
	public List<Arc> getIncidetenArcsByNodeId(int id) {
		LinkedList<Arc> arcs = new LinkedList<Arc>();
		ElementType nodeType = getNodeType(id);
		if (nodeType == ElementType.PLACE) {
			Place place = getPlaceById(id);
			arcs.addAll(place.getStartArcs());
			arcs.addAll(place.getEndArcs());
		} else if (nodeType == ElementType.TRANSITION) {
			Transition transition = getTransitionById(id);
			arcs.addAll(transition.getStartArcs());
			arcs.addAll(transition.getEndArcs());
		} else {
			// ARC or INVALID -> skip to return empty arc list
		}
		return arcs;
	}

	/**
	 * Fuegt einem bestehenden Petrinetz ein weiters hinzu
	 * 
	 * @param net
	 */
	public void addNet(Petrinet net) {
		if (null != net) {
			final Set<Place> places = net.getAllPlaces();
			final Set<Transition> transitions = net.getAllTransitions();
			final Set<Arc> arcs = net.getAllArcs();

			addPlaces(places);
			addTransitions(transitions);
			addArcs(arcs);
		}
	}

	/**
	 * Die uebergebene Liste wird der besthenden hinzu gefuegt.
	 * 
	 * @param arcs2
	 */
	private void addArcs(Set<Arc> arcs2) {
		for (Arc arc : arcs2) {
			this.arcs.add(arc);
		}
	}

	/**
	 * Die uebergebene Liste wird der besthenden hinzu gefuegt.
	 * 
	 * @param transitions2
	 */
	private void addTransitions(Set<Transition> transitions2) {
		for (Transition transition : transitions2) {
			this.transitions.add(transition);
		}

	}

	/**
	 * Die uebergebene Liste wird der besthenden hinzu gefuegt.
	 * 
	 * @param places2
	 */
	private void addPlaces(Set<Place> places2) {
		for (Place place : places2) {
			this.places.add(place);
		}
	}

	/**
	 * Returns the {@link ElementType type} of the node referenced by
	 * <code>nodeId</code>
	 * 
	 * @param nodeId
	 *            Id of the node
	 * @return {@link ElementType#INVALID} if node is not in this petrinet
	 */
	public ElementType getNodeType(int nodeId) {
		for (Place place : getAllPlaces()) {
			if (place.getId() == nodeId) {
				return ElementType.PLACE;
			}
		}
		for (Transition transition : getAllTransitions()) {
			if (transition.getId() == nodeId) {
				return ElementType.TRANSITION;
			}
		}
		for (Arc arc : getAllArcs()) {
			if (arc.getId() == nodeId) {
				return ElementType.ARC;
			}
		}
		return ElementType.INVALID;
	}

}
