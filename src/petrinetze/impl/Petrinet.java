package petrinetze.impl;

import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import petrinetze.Renews;

public class Petrinet implements IPetrinet {

    private final Logger logger = Logger.getLogger(Petrinet.class.getCanonicalName());
	
	private int id;
	private final Set<IPetrinetListener> listeners = new HashSet<IPetrinetListener>();
	private Set<IPlace> places;
	private Set<ITransition> transitions;
	private Set<IArc> arcs;
	private IGraphElement graphElements;

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
		//TODO: Es sollen auch die ankommenden und ausgehenden Kanten mitgelöscht werden.
		//und jedes Mal ein Event abfeuern.
		for (IPlace p : places) {
			if (p.getId() == id) {
				toBeDelete = p;
				break;
			}
		}
		if (toBeDelete != null) {
			List<IArc> start = toBeDelete.getStartArcs();
			List<IArc> end = toBeDelete.getEndArcs();
			//Alle ausgehenden Kanten loeschen und Event abfeuern.
			for(IArc arc : start){
				deleteArc(arc);
			}
			//Alle eingehenden Kanten loeschen und Event abfeuern.
			for(IArc arc : end) {
				deleteArc(arc);
			}
			
			places.remove(toBeDelete);
			onNodeChanged(toBeDelete, ActionType.deleted);
		}
	}

    private void deleteArc(IArc arc) {
        // ein-/ausgehende Kanten der Transitionen löschen
        if (arc.getStart() != null && arc.getStart() instanceof Transition) {
            ((Transition)arc.getStart()).removeStartArc(arc);
        }
        else if (arc.getEnd() != null && arc.getEnd() instanceof Transition) {
            ((Transition)arc.getEnd()).removeEndArc(arc);
        }

        arcs.remove(arc);
        onEdgeChanged(arc, ActionType.deleted);
    }

	@Override
	public ITransition createTransition(String name, IRenew rnw) {
		Transition t = new Transition(UUID.gettID(), rnw, this);
		t.setName(name);
		transitions.add(t);
		onNodeChanged(t, ActionType.added);
		return t;
	}

    @Override
    public ITransition createTransition(String name) {
        return createTransition(name, Renews.IDENTITY);
    }

    @Override
	public void deleteTransitionByID(int id) {
		ITransition toBeDelete = null;
		//TODO: Es sollen auch die ankommenden und ausgehenden Kanten mitgelöscht werden.
		//und jedes Mal ein Event abfeuern.
		for (ITransition t : transitions) {
			if (t.getId() == id) {
				toBeDelete = t;
				break;
			}
		}
		if (toBeDelete != null) {
			final List<IArc> arcs = new LinkedList<IArc>();
			arcs.addAll(toBeDelete.getStartArcs());
			arcs.addAll(toBeDelete.getEndArcs());
			
			//Alle ausgehenden Kanten loeschen und Event abfeuern.
			for(IArc arc : arcs){
				deleteArc(arc);
			}
			
			transitions.remove(toBeDelete);
			onNodeChanged(toBeDelete, ActionType.deleted);
		}
	}

	@Override
	public IArc createArc(String name, INode start, INode end) {
		final IArc arc = new Arc(UUID.getaID(), this, start, end);
		//Fuege Arc in die Startliste von Transition hinzu
		if(start instanceof ITransition) {
			((ITransition) start).setStartArcs(arc);
		}
		//Fuege Arc in die Endliste von Transition hinzu
		if(end instanceof ITransition) {
			((ITransition) end).setEndArcs(arc);
		}
		
		//Fuege Arc in die Startliste von Place hinzu
		if(start instanceof IPlace) {
			((IPlace) start).setStartArcs(arc);
		}
		//Fuege Arc in die Endliste von Place hinzu
		if(end instanceof IPlace) {
			((IPlace) end).setEndArcs(arc);
		}
		
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
			deleteArc(toBeDelete);
		}
	}

	@Override
	public Set<ITransition> getActivatedTransitions() {
		
		//Eine Transition ist aktiviert bzw. schaltbereit, falls sich 
		//in allen Eingangsstellen mindestens so viele Marken befinden, 
		//wie die Transition Kosten verursacht und alle Ausgangsstellen 
		//noch genug Kapazitaet haben, um die neuen Marken aufnehmen zu k?nnen.
		//TODO
		Set<ITransition> activitedTransitions = new HashSet<ITransition>();
		for (ITransition t : transitions) {
			if (isActivited(t)) {
				activitedTransitions.add(t);
			}
		}
		return activitedTransitions;
	}

	private boolean isActivited(ITransition t) {
		List<IArc> incoming = t.getEndArcs();
		for (IArc a : incoming) {
			IPlace p = (IPlace) a.getStart();
			
			if (p.getMark() < a.getMark()) {
				return false;
			} 
		}
		return true;
	}



	@Override
	public Set<INode> fire(int id) 
	{
		//Get the transition
		ITransition transition = null;
		for(ITransition t : transitions)
		{
			if(t.getId() == id)
			{
				transition = t;
				break;
			}
		}
		
		//Get all pre and post places and check if they have enough tokens
		Map<IPlace, Integer> inc = new HashMap<IPlace, Integer>();
		Map<IPlace, Integer> out = new HashMap<IPlace, Integer>();
		for(IArc arc : arcs)
		{
			if(arc.getEnd().equals(transition)) {
				if(arc.getMark() > ((IPlace)arc.getStart()).getMark()) {
					throw new IllegalArgumentException("The place " + arc.getStart() + " does not have enough tokens");
				}
				inc.put((IPlace)arc.getStart(), arc.getMark());
			}
			else if(arc.getStart().equals(transition))
			{
				out.put((IPlace)arc.getEnd(), arc.getMark());
			}
		}
		
		//set tokens
		for(Entry<IPlace, Integer> e : inc.entrySet())
			e.getKey().setMark(e.getKey().getMark() - e.getValue());
		for(Entry<IPlace, Integer> e : out.entrySet())
			e.getKey().setMark(e.getKey().getMark() + e.getValue());
		
		//all changed nodes
		Set<INode> changedNodes = new HashSet<INode>(inc.keySet());
		changedNodes.addAll(out.keySet());
		
		//Renew
		transition.rnw();
		
		//fire event
		fireChanged(changedNodes, ActionType.changed);

		return changedNodes;
	}

    private void fireChanged(Iterable<INode> nodes, ActionType action) {
        for (INode node : nodes) {
            try {
                onNodeChanged(node, action);
            }
            catch (Exception ex) {
                logger.log(Level.SEVERE, "Error on change notification", ex);
            }
        }
    }

	private Random random = new Random();
	@Override
	public Set<INode> fire() 
	{
		//We use an iterator here because we do not know, wich indexes are in the set
		//and this way we will get a random one without knowing its id.
		int id = random.nextInt(transitions.size());
		Iterator<ITransition> iterator = transitions.iterator();
		for(int i = 0; i < id; i++)
			iterator.next();
		
		return fire(iterator.next().getId());
	}
	
	//PRE in der t-ten Spalte gibt an,
	//wieviele Token die Transition t von p wegnimmt
	@Override
	public IPre getPre() {
		//Initialisierung 
		int[] pId = new int[places.size()];
		
		int i=0;
		//Fuellen mit den entsprechenden Ids
		for (IPlace place : places) {
			pId[i]=place.getId();
			i++;
		}
		//Initialisierung
		int[] tId = new int[transitions.size()];
		
		int z=0;
		//Fuellen mit den entsprechenden Ids
		for (ITransition transition : transitions) {
			tId[z]=transition.getId();
			z++;
		}
		
		
		//Initialisierung mit 0
		int[][] preValues = init(places.size(),transitions.size());
		//Zu jeder Transition holen wir die Stellen und
		//merken uns das in der perValueMatrix (sagt Florian)
		for (ITransition t : transitions) {
			final Hashtable<Integer, Integer> ht = t.getPre();
			int tmpTransitionId = getIdFromArray(t.getId(), tId);
			for (Integer p : ht.keySet()) {
				int tmpPlaceId = getIdFromArray(p.intValue(), pId);
				
				if (tmpPlaceId >= 0 && tmpTransitionId >= 0) {
					preValues[tmpPlaceId][tmpTransitionId] = ht.get(p);
				}
			}
		}
		return new Pre(preValues, pId, tId);
	}

	private int getIdFromArray(int intValue, int[] pId) {
		for (int i = 0; i <= pId.length; i++) {
			if (pId[i] == intValue) {
				return i;
			}
		}
		return -1;
	}



	private int[][] init(int m, int n) {
		int tmp [][] = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int z = 0; z < n; z++) {
				tmp[i][z] = 0;
			}
		}
		return tmp;
	}



	@Override
	public IPost getPost() {
		//Initialisierung 
		int[] pId = new int[places.size()];
		
		int i=0;
		//Fuellen mit den entsprechenden Ids
		for (IPlace place : places) {
			pId[i]=place.getId();
			i++;
		}
		//Initialisierung
		int[] tId = new int[transitions.size()];
		
		int z=0;
		//Fuellen mit den entsprechenden Ids
		for (ITransition transition : transitions) {
			tId[z]=transition.getId();
			z++;
		}
		
		
		//Initialisierung mit 0
		int[][] postValues = init(places.size(),transitions.size());
		//Zu jeder Transition holen wir die Stellen und
		//merken uns das in der perValueMatrix (sagt Florian)
		for (ITransition t : transitions) {
			final Hashtable<Integer, Integer> ht = t.getPost();
			int tmpTransitionId = getIdFromArray(t.getId(), tId);
			for (Integer p : ht.keySet()) {
				int tmpPlaceId = getIdFromArray(p.intValue(), pId);
				
				if (tmpPlaceId >= 0 && tmpTransitionId >= 0) {
					postValues[tmpPlaceId][tmpTransitionId] = ht.get(p);
				}
			}
		}
		return new Post(postValues, pId, tId);
	}

	@Override
	public int getId() {
		return this.id;
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
		for(IPetrinetListener listener : tempListeners) {
			listener.changed(this, element, actionType);
		}
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
		for(IPetrinetListener listener : tempListeners) {
			listener.changed(this, element, actionType);
		}
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Petrinet [id=" + id + "\n\t places=" + places + "\n\t transitions="
				+ transitions + "\n\t arcs=" + arcs + "]";
	}



	@Override
	public IPlace getPlaceById(int id) {
		for (IPlace  p : places) {
			if(p.getId() == id) {
				return p;
			}
		}
		return null;
	}



	@Override
	public ITransition getTransitionById(int id) {
		for (ITransition  t : transitions) {
			if(t.getId() == id) {
				return t;
			}
		}
		return null;
	}

}
