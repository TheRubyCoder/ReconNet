package petrinet;

import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

//import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

import engine.handler.NodeTypeEnum;

/**
 * @mainpage Hier entsteht die Mainpage. <li>
 * 
 *           \li {@link MockObjects Mocki}
 * 
 *           </li>
 * 
 */
public class Petrinet {

	private final Logger logger = Logger.getLogger(Petrinet.class
			.getCanonicalName());

	private int id;
	private final Set<IPetrinetListener> listeners = new HashSet<IPetrinetListener>();
	private Set<Place> places;
	private Set<Transition> transitions;
	private Set<Arc> arcs;
	private IGraphElement graphElements;

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

	Petrinet() {
		id = UUID.getnID();
		places = new HashSet<Place>();
		transitions = new HashSet<Transition>();
		arcs = new HashSet<Arc>();
		graphElements = new GraphElement();
	}

	public Place createPlace(String name) {
		final Place p = new Place(UUID.getpID());
		p.setName(name);
		places.add(p);
		onNodeChanged(p, ActionType.added);
		return p;
	}

	public void deletePlaceById(int id) {
		Place toBeDelete = null;
		// TODO: Es sollen auch die ankommenden und ausgehenden Kanten
		// mitgeloescht werden.
		// und jedes Mal ein Event abfeuern.
		for (Place p : places) {
			if (p.getId() == id) {
				toBeDelete = p;
				break;
			}
		}
		if (toBeDelete != null) {
			List<Arc> start = toBeDelete.getStartArcs();
			List<Arc> end = toBeDelete.getEndArcs();
			// Alle ausgehenden Kanten loeschen und Event abfeuern.
			for (Arc arc : start) {
				deleteArc(arc);
			}
			// Alle eingehenden Kanten loeschen und Event abfeuern.
			for (Arc arc : end) {
				deleteArc(arc);
			}

			places.remove(toBeDelete);
			onNodeChanged(toBeDelete, ActionType.deleted);
		}
	}

	private void deleteArc(Arc arc) {
		// ein-/ausgehende Kanten der Transitionen loeschen
		if (arc.getStart() != null && arc.getStart() instanceof Transition) {
			((Transition) arc.getStart()).removeStartArc(arc);
		} else if (arc.getEnd() != null && arc.getEnd() instanceof Transition) {
			((Transition) arc.getEnd()).removeEndArc(arc);
		}

		arcs.remove(arc);
		onEdgeChanged(arc, ActionType.deleted);
	}

	private Arc getArcById(int id) {
		for (Arc arc : getAllArcs()) {
			if (arc.getId() == id) {
				return arc;
			}
		}
		return null;
	}

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
			
			Collection<Arc> copyOfStartArcs = new ArrayList<Arc>(place.getStartArcs());
			Collection<Arc> copyOfEndArcs = new ArrayList<Arc>(place.getEndArcs());
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
			Collection<Arc> copyOfStartArcs = new ArrayList<Arc>(transition.getStartArcs());
			Collection<Arc> copyOfEndArcs = new ArrayList<Arc>(transition.getEndArcs());
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

	public Transition createTransition(String name, IRenew rnw) {
		Transition t = new Transition(UUID.gettID(), rnw, this);
		t.setName(name);
		transitions.add(t);
		onNodeChanged(t, ActionType.added);
		return t;
	}

	public Transition createTransition(String name) {
		return createTransition(name, Renews.IDENTITY);
	}

	public void deleteTransitionByID(int id) {
		Transition toBeDelete = null;
		// TODO: Es sollen auch die ankommenden und ausgehenden Kanten
		// mitgeloescht werden.
		// und jedes Mal ein Event abfeuern.
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

			// Alle ausgehenden Kanten loeschen und Event abfeuern.
			for (Arc arc : arcs) {
				deleteArc(arc);
			}

			transitions.remove(toBeDelete);
			onNodeChanged(toBeDelete, ActionType.deleted);
		}
	}

	public Arc createArc(String name, INode start, INode end) {
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

	public Set<Transition> getActivatedTransitions() {

		// Eine Transition ist aktiviert bzw. schaltbereit, falls sich
		// in allen Eingangsstellen mindestens so viele Marken befinden,
		// wie die Transition Kosten verursacht und alle Ausgangsstellen
		// noch genug Kapazitaet haben, um die neuen Marken aufnehmen zu k?nnen.
		// TODO
		Set<Transition> activitedTransitions = new HashSet<Transition>();
		for (Transition t : transitions) {
			if (isActivited(t)) {
				activitedTransitions.add(t);
			}
		}
		return activitedTransitions;
	}

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

	private void fireChanged(Iterable<INode> nodes, ActionType action) {
		for (INode node : nodes) {
			try {
				onNodeChanged(node, action);
			} catch (Exception ex) {
				logger.log(Level.SEVERE, "Error on change notification", ex);
			}
		}
	}

	private Random random = new Random();

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
		((GraphElement) graphElements).setNodes(nodes);
		((GraphElement) graphElements).setArcs(tarcs);

		return graphElements;
	}

	public void addPetrinetListener(IPetrinetListener l) {
		listeners.add(l);

	}

	public void removePetrinetListener(IPetrinetListener l) {
		if (listeners.contains(l))
			listeners.remove(l);

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

	public Place getPlaceById(int id) {
		for (Place p : places) {
			if (p.getId() == id) {
				return p;
			}
		}
		return null;
	}

	public Transition getTransitionById(int id) {
		for (Transition t : transitions) {
			if (t.getId() == id) {
				return t;
			}
		}
		return null;
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
			// Vervollstaendige die Liste der graphischen Elemente
			getAllGraphElement();
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
