package petrinet.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import petrinet.model.rnw.Identity;

/**
 * The petrinet compose {@link Place places}, {@link Transition transitions} and
 * {@link IArc arcs} to bundle most logical functionalities of a petrinet.
 */
final public class Petrinet {
	/**
	 * Unique id
	 */
	private final int id;

	/**
	 * Places of this petrinet
	 */
	private Map<Integer, Place> places;

	/**
	 * Transitions of this petrinet
	 */
	private Map<Integer, Transition> transitions;

	/**
	 * (pre) Arcs between and {@link Petrinet#places places} and
	 * {@link Petrinet#transitions transitions} of this petrinet
	 */
	private Map<Integer, PreArc> preArcs;

	/**
	 * (post) Arcs between and {@link Petrinet#transitions transitions} and
	 * {@link Petrinet#places places} of this petrinet
	 */
	private Map<Integer, PostArc> postArcs;

	/**
	 * Random number generator used to fire a random transition
	 */
	private Random random = new Random();
	
	/**
	 * Creates an empty petrinet
	 */
	public Petrinet() {
		id 			= UUID.getnID();
		places 		= new HashMap<Integer, Place>();
		transitions = new HashMap<Integer, Transition>();
		preArcs     = new HashMap<Integer, PreArc>();
		postArcs    = new HashMap<Integer, PostArc>();
	}	
	

	/**
	 * Adds a new {@link PostArc} to the petrinet
	 * 
	 * @param  name    	   Name of the {@link PostArc}
	 * @param  transition  start  {@link Transition transition}  of the arc
	 * @param  place       target {@link Place place} of the arc
	 * @return The new {@link PostArc}
	 * @throws IllegalArgumentException
	 */
	public PostArc addPostArc(String name, Transition transition, Place place) {
		if (name == null || place == null || transition == null) {
			throw new IllegalArgumentException();
		}
		
		if (transition.getOutgoingPlaces().contains(place)
		 || place.getIncomingTransitions().contains(transition)) {
			throw new IllegalArgumentException("arc already exists");			
		}

		int           id  = UUID.getaID();		
		final PostArc arc = new PostArc(id, transition, place, name, 1);

		transition.addOutgoingArc(arc);
		place.addIncomingArc(arc);

		postArcs.put(id, arc);

		return arc;
	}
	
	/**
	 * Adds a new {@link PreArc} to the petrinet
	 * 
	 * @param  name    	   Name of the {@link PreArc}
	 * @param  place       start {@link Place place}  of the arc
	 * @param  transition  target {@link Transition transition} of the arc
	 * @return The new {@link PreArc}
	 * @throws IllegalArgumentException
	 */
	public PreArc addPreArc(String name, Place place, Transition transition) {
		if (name == null || place == null || transition == null) {
			throw new IllegalArgumentException();
		}
		
		if (transition.getIncomingPlaces().contains(place)
		 || place.getOutgoingTransitions().contains(transition)) {
			throw new IllegalArgumentException("arc already exists");			
		}

		int          id  = UUID.getaID();		
		final PreArc arc = new PreArc(id, place, transition, name, 1);

		place.addOutgoingArc(arc);
		transition.addIncomingArc(arc);

		preArcs.put(id, arc);

		return arc;
	}
	
	public boolean containsArc(int id) {
		return containsPreArc(id) || containsPostArc(id);
	}
	
	public boolean containsPreArc(int id) {
		return preArcs.containsKey(id);
	}
	
	public boolean containsPreArc(PreArc preArc) {
		return preArcs.containsKey(preArc.getId());
	}
	
	public boolean containsPostArc(int id) {
		return postArcs.containsKey(id);
	}
	
	public boolean containsPostArc(PostArc postArc) {
		return postArcs.containsKey(postArc.getId());
	}
	
	/**
	 * Returns an {@link IArc} referenced by id
	 * 
	 * @param id  Id of the {@link IArc}
	 * @return <code>null</code> if {@link IArc} is not in petrinet
	 */
	public IArc getArc(int id) {		
		IArc arc = preArcs.get(id);
		
		if (arc == null) {
			arc = postArcs.get(id);
		}
		
		return arc;
	}

	public Set<IArc> getArcs() {
		Set<IArc> arcs = new HashSet<IArc>(preArcs.values()); 
		arcs.addAll(postArcs.values());
		
		return arcs;
	}

	public Set<PreArc> getPreArcs() {
		return new HashSet<PreArc>(preArcs.values());
	}

	public Set<PostArc> getPostArcs() {
		return new HashSet<PostArc>(postArcs.values()); 
	}
	
	/**
	 * removes an arc from the petrinet
	 * 
	 * @param arc  {@link PostArc} to be removed
	 */
	public void removeArc(PostArc arc) {
		if (arc == null) {
			throw new IllegalArgumentException();
		}
		
		arc.getTransition().removeOutgoingArc(arc);
		arc.getPlace().removeIncomingArc(arc);

		postArcs.remove(arc.getId());
	}
	
	/**
	 * removes an arc from the petrinet
	 * 
	 * @param arc  {@link PreArc} to be removed
	 */
	public void removeArc(PreArc arc) {
		if (arc == null) {
			throw new IllegalArgumentException();
		}

		arc.getPlace().removeOutgoingArc(arc);
		arc.getTransition().removeIncomingArc(arc);

		preArcs.remove(arc.getId());
	}

	public void removeArc(int id) {
		PreArc  preArc  = preArcs.get(id);
		
		if (preArc != null) {
			removeArc(preArc);
			return;
		}

		PostArc  postArc  = postArcs.get(id);
		
		if (postArc != null) {
			removeArc(postArc);
			return;
		}

		throw new IllegalArgumentException();
	}
	
	
	
	
	
	
	/**
	 * Adds a new {@link Place} to the petrinet
	 * 
	 * @param  name  Name of the place
	 * @return The new {@link Place}
	 */
	public Place addPlace(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
		
		int   id 		  = UUID.getpID();
		final Place place = new Place(id);
		
		place.setName(name);
		places.put(id, place);
		
		return place;
	}
	
	public boolean containsPlace(Place place) {
		return places.containsKey(place.getId());
	}
	
	public boolean containsPlace(int id) {
		return places.containsKey(id);
	}

	/**
	 * Returns the {@link Place} with <code>id</code>
	 * 
	 * @param id  Id of the {@link Place}
	 * @return <code>null</code> if {@link Place} is not in this petrinet
	 */
	public Place getPlace(int id) {
		return places.get(id);
	}

	public Set<Place> getPlaces() {
		return new HashSet<Place>(places.values());
	}
	
	/**
	 * removes an place 
	 * 
	 * @param place the place that will be removed
	 */
	public void removePlace(Place place) {
		removePlace(place.getId());
	}
	
	/**
	 * removes an place referenced by id
	 * 
	 * @param id  id of the place that will be removed
	 */
	public void removePlace(int id) {
		Place place = places.get(id);

		if (place == null) {
			throw new IllegalArgumentException();
		}

		final List<PostArc> inArcs  = new LinkedList<PostArc>(place.getIncomingArcs());
		final List<PreArc>  outArcs = new LinkedList<PreArc>(place.getOutgoingArcs());

		// remove incoming arcs
		for (PostArc arc : inArcs) {
			removeArc(arc);
		}

		// remove outgoing arcs
		for (PreArc arc : outArcs) {
			removeArc(arc);
		}
		
		places.remove(id);
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
	public Transition addTransition(String name, IRenew rnw) {
		if (name == null || rnw == null) {
			throw new IllegalArgumentException();
		}		
		
		int        id         = UUID.gettID();
		Transition transition = new Transition(id, rnw);
		
		transition.setName(name);
		transitions.put(id, transition);
		
		return transition;
	}

	/**
	 * adds a Transition with {@link Identity id} as a renew
	 * 
	 * @param name
	 *            Name of the {@link Transition}
	 * @return The new {@link Transition}
	 */
	public Transition addTransition(String name) {
		return addTransition(name, Renews.IDENTITY);
	}
	
	public boolean containsTransition(Transition transition) {
		return transitions.containsKey(transition.getId());
	}
	
	public boolean containsTransition(int id) {
		return transitions.containsKey(id);
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
		
		for (Transition t : transitions.values()) {
			if (t.isActivated()) {
				activitedTransitions.add(t);
			}
		}
		
		return activitedTransitions;
	}
	

	/**
	 * Returns the {@link Transition} with <code>id</code>
	 * 
	 * @param id
	 *            Id of the {@link Transition}
	 * @return <code>null</code> if {@link Transition} is not in this petrinet
	 */
	public Transition getTransition(int id) {
		return transitions.get(id);
	}

	public Set<Transition> getTransitions() {
		return new HashSet<Transition>(transitions.values());
	}
	
	/**
	 * removes a {@link Transition} 
	 * 
	 * @param transition the transition that will be removed
	 */
	public void removeTransition(Transition transition) {
		removeTransition(transition.getId());
	}

	
	/**
	 * removes a {@link Transition} referenced by id
	 * 
	 * @param id
	 *            The id of the {@link Transition} that will be removed
	 */
	public void removeTransition(int id) {
		Transition transition = transitions.get(id);
		
		if (transition == null) {
			throw new IllegalArgumentException();
		}

		final List<PreArc>  inArcs  = new LinkedList<PreArc>(transition.getIncomingArcs());
		final List<PostArc> outArcs = new LinkedList<PostArc>(transition.getOutgoingArcs());
		
		for (PreArc arc : inArcs) {
			removeArc(arc);
		}

		for (PostArc arc : outArcs) {
			removeArc(arc);
		}

		transitions.remove(id);
	}

	
	
	/**
	 * Fires a random active {@link Transition}
	 * 
	 * @return Changed nodes
	 * @throws IllegalStateException
	 *             if no {@link Transition} is active
	 */
	public Set<Place> fire() {
		List<Transition> active = new ArrayList<Transition>(
			getActivatedTransitions()
		);

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
	 * Fires a {@link Transition} with the <code>id</code>
	 * 
	 * @param id
	 *            Id of the {@link Transition} that will be fired
	 * @return Changed nodes
	 */
	public Set<Place> fire(int id) {
		Transition transition = transitions.get(id);

		return transition.fire();
	}

	/**
	 * Returns <tt> true </tt> if there are no Transitions or Places in the
	 * petrinet
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return this.getPlaces().isEmpty()
				&& this.getTransitions().isEmpty();
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
		for (Place place : places.values()) {
			pId[i] = place.getId();
			i++;
		}
		// Initialisierung
		int[] tId = new int[transitions.size()];

		int z = 0;
		// Fuellen mit den entsprechenden Ids
		for (Transition transition : transitions.values()) {
			tId[z] = transition.getId();
			z++;
		}

		// Initialisierung mit 0
		int[][] preValues = init(places.size(), transitions.size());
		// Zu jeder Transition holen wir die Stellen und
		// merken uns das in der perValueMatrix (sagt Florian)
		for (Transition t : transitions.values()) {
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
		for (Place place : places.values()) {
			pId[i] = place.getId();
			i++;
		}
		// Initialisierung
		int[] tId = new int[transitions.size()];

		int z = 0;
		// Fuellen mit den entsprechenden Ids
		for (Transition transition : transitions.values()) {
			tId[z] = transition.getId();
			z++;
		}

		// Initialisierung mit 0
		int[][] postValues = init(places.size(), transitions.size());
		// Zu jeder Transition holen wir die Stellen und
		// merken uns das in der perValueMatrix (sagt Florian)
		for (Transition t : transitions.values()) {
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

	public boolean equalsPetrinet(Petrinet petrinet) {
		if (this == petrinet) {
			return true;
		}
		
		if (petrinet == null) {
			return false;
		}
		
		return getPre().matrixStringOnly().equals(petrinet.getPre().matrixStringOnly())
			&& getPost().matrixStringOnly().equals(petrinet.getPost().matrixStringOnly());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result = "Petrinet [id=" + id + "\n\t places= [";
		for (Place place : places.values()) {
			result += "\n\t\t" + place;
		}
		
		result += "\n\t]\n\t transitions=[";
		for (Transition transition : transitions.values()) {
			result += "\n\t\t" + transition;
		}
		
		result += "\n\t]\n\t arcs=[";
		for (IArc arc : getArcs()) {
			result += "\n\t\t" + arc;
		}
		
		return result + "\n\t]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		
		if (!(object instanceof Petrinet)) {
			return false;
		}
		
		Petrinet petrinet = (Petrinet) object;
		
		return id == petrinet.getId();
	}
}
