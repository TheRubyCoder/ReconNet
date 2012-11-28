package petrinet.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * This class represents a Transition within a {@link Petrinet}, holding
 * information about name, renew and label
 * 
 */
public class Transition implements INode {
	/**
	 * Id of this {@link Transition}
	 */
	private final int id;

	/**
	 * Name of this {@link Transition}
	 */
	private String name;

	/**
	 * {@link IRenew Renew} of this {@link Transition}
	 */
	private IRenew rnw;

	/**
	 * Label of this {@link Transition}
	 */
	private String tlb = "";

	/**
	 * list of outgoing arcs
	 */
	private List<PostArc> outgoingArcs;

	/**
	 * list of incoming arcs
	 */
	private List<PreArc> incomingArcs;

	/**
	 * Petrinet of the transition
	 */
	private final Petrinet petrinet;

	/**
	 * Creates a new {@link Transition} with no name and no arcs
	 * 
	 * @param id        Id of the {@link Transition}
	 * @param rnw       Renew of the {@link Transition}
	 * @param petrinet  {@link Petrinet} of the {@link Transition}
	 */
	public Transition(int id, IRenew rnw, Petrinet petrinet) {
		this.id           = id;
		this.rnw          = rnw;
		this.incomingArcs = new ArrayList<PreArc>();
		this.outgoingArcs = new ArrayList<PostArc>();
		this.petrinet     = petrinet;
	}

	public void addIncomingArc(PreArc arc) {
		this.incomingArcs.add(arc);
		petrinet.onNodeChanged(this, ActionType.CHANGED);
	}

	public void addOutgoingArc(PostArc arc) {
		this.outgoingArcs.add(arc);
		petrinet.onNodeChanged(this, ActionType.CHANGED);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see haw.wp.rcpn.Transition#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see haw.wp.rcpn.Transition#getId()
	 */
	@Override
	public int getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see haw.wp.rcpn.Transition#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	public String getTlb() {
		return tlb;
	}

	/**
	 * Sets the label of this {@link Transition}
	 * 
	 * @param tlb
	 *            New Label
	 * @throws IllegalArgumentException
	 *             if Label is not valid for current {@link IRenew renew}
	 * @see Transition#setRnw(IRenew)
	 */
	public void setTlb(String tlb) {
		if (!rnw.isTlbValid(tlb)) {
			throw new IllegalArgumentException("Invalid tlb: " + tlb
					+ " for rnw " + rnw);
		}
		
		this.tlb = tlb;
	}

	public String rnw() {
		this.tlb = rnw.renew(this.tlb);
		return tlb;
	}

	/**
	 * Sets the new renew without checking of the new renew fits to the label
	 * 
	 * @param renew
	 *            The new Renew
	 * @see Transition#setTlb(String)
	 */
	public void setRnw(IRenew renew) {
		this.rnw = renew;
		petrinet.onNodeChanged(this, ActionType.CHANGED);
	}

	public IRenew getRnw() {
		return this.rnw;
	}

	/**
	 * Returns the {@link Place places} that are at the other end of outgoing
	 * {@link IArc arcs}
	 * 
	 * @return Empty list if there are no outgoing arcs
	 */
	public List<Place> getOutgoingPlaces() {
		List<Place> out = new ArrayList<Place>(outgoingArcs.size());
		
		for (PostArc arc : outgoingArcs) {
			out.add(arc.getTarget());
		}
		
		return out;
	}

	/**
	 * Returns the {@link Place places} that are at the other end of incoming
	 * {@link IArc arcs}
	 * 
	 * @return Empty list if there are no incoming arcs
	 */
	public List<Place> getIncomingPlaces() {
		List<Place> in = new ArrayList<Place>(incomingArcs.size());

		for (PreArc arc : incomingArcs) {
			in.add(arc.getSource());
		}

		return in;
	}

	/**
	 * @precondition getOutgoingPlaces()
	 */
	public Hashtable<Integer, Integer> getPre() {
		final Hashtable<Integer, Integer> pre = new Hashtable<Integer, Integer>();

		for (PreArc arc : incomingArcs) {
			Place place = arc.getSource();			
			pre.put(Integer.valueOf(place.getId()), arc.getMark());
		}

		return pre;
	}

	/**
	 * @precondition getIncomingPlaces()
	 */
	public Hashtable<Integer, Integer> getPost() {
		final Hashtable<Integer, Integer> post = new Hashtable<Integer, Integer>();

		for (PostArc arc : outgoingArcs) {
			Place place = arc.getTarget();
			post.put(Integer.valueOf(place.getId()), arc.getMark());
		}

		return post;
	}

	public List<PostArc> getOutgoingArcs() {
		return outgoingArcs;
	}

	public List<PreArc> getIncomingArcs() {
		return incomingArcs;
	}

	boolean removeOutgoingArc(PostArc arc) {
		return outgoingArcs.remove(arc);
	}

	boolean removeIncomingArc(PreArc arc) {
		return incomingArcs.remove(arc);
	}

	/**
	 * Checks whether this {@link Transition} is active. Only incoming arcs are
	 * checked
	 * 
	 * @return
	 */
	public boolean isActivated() {
		for (PreArc arc : getIncomingArcs()) {
			Place place = arc.getSource();

			if (place.getMark() < arc.getMark()) {
				return false;
			}
		}
		
		return true;
	}
	

	
	/**
	 * Fires a {@link Transition} with the <code>id</code>
	 * 
	 * @return Changed nodes
	 */
	public Set<INode> fire() {
		if (!this.isActivated()) {
			throw new IllegalArgumentException();
		}

		// all changed nodes
		Set<INode> changedNodes = new HashSet<INode>();
		
		// set tokens
		for (PreArc arc : incomingArcs) {
			arc.getPlace().setMark(arc.getPlace().getMark() - arc.getMark());
			changedNodes.add(arc.getPlace());
		}

		for (PostArc arc : outgoingArcs) {
			arc.getPlace().setMark(arc.getPlace().getMark() + arc.getMark());
			changedNodes.add(arc.getPlace());
		}

		// Renew
		this.rnw();

		// fire event
		petrinet.fireChanged(changedNodes, ActionType.CHANGED);

		return changedNodes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Transition [Label=" + tlb + ", id=" + id + ", name=" + name + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		
		if (!(object instanceof Transition)) {
			return false;
		}
		
		Transition transition = (Transition) object;
		
		return id == transition.getId();
	}
}
