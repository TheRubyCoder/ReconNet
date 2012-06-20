package petrinet;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * This class represents a Transition within a {@link Petrinet}, holding
 * information about name, renew and label
 * 
 */
public class Transition implements INode {

	/**
	 * Id of this {@link Transition}
	 */
	private int id;

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
	private List<Arc> startArcs;

	/**
	 * list of incoming arcs
	 */
	private List<Arc> endArcs;

	/**
	 * Petrinet of the transition
	 */
	private final Petrinet petrinet;

	public void setStartArcs(Arc arc) {
		this.startArcs.add(arc);
		petrinet.onNodeChanged(this, ActionType.changed);
	}

	public void setEndArcs(Arc arc) {
		this.endArcs.add(arc);
		petrinet.onNodeChanged(this, ActionType.changed);
	}

	/**
	 * Creates a new {@link Transition} with no name and no arcs
	 * 
	 * @param id
	 *            Id of the {@link Transition}
	 * @param rnw
	 *            Renew of the {@link Transition}
	 * @param petrinet
	 *            {@link Petrinet} of the {@link Transition}
	 */
	Transition(int id, IRenew rnw, Petrinet petrinet) {
		this.id = id;
		this.rnw = rnw;
		this.endArcs = new ArrayList<Arc>();
		this.startArcs = new ArrayList<Arc>();
		this.petrinet = petrinet;
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
		petrinet.onNodeChanged(this, ActionType.changed);
	}

	public IRenew getRnw() {
		return this.rnw;
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transition other = (Transition) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Transition [Label=" + tlb + ", id=" + id + ", name=" + name
				+ "]";
	}

	/**
	 * Returns the {@link Place places} that are at the other end of outgoing
	 * {@link Arc arcs}
	 * 
	 * @return Empty list if there are no outgoing arcs
	 */
	public List<Place> getOutgoingPlaces() {
		List<Place> out = new ArrayList<Place>(startArcs.size());
		for (Arc arc : startArcs) {
			Place p = (Place) arc.getEnd();
			out.add(p);
		}
		return out;
	}

	/**
	 * Returns the {@link Place places} that are at the other end of incoming
	 * {@link Arc arcs}
	 * 
	 * @return Empty list if there are no incoming arcs
	 */
	public List<Place> getIncomingPlaces() {
		List<Place> in = new ArrayList<Place>(endArcs.size());

		for (Arc arc : endArcs) {
			in.add((Place) arc.getStart());
		}

		return in;
	}

	/**
	 * @precondition getOutgoingPlaces()
	 */
	public Hashtable<Integer, Integer> getPre() {
		final Hashtable<Integer, Integer> pre = new Hashtable<Integer, Integer>();

		for (Arc arc : endArcs) {
			Place p = (Place) arc.getStart();
			pre.put(Integer.valueOf(p.getId()), Integer.valueOf(arc.getMark()));
		}

		return pre;
	}

	/**
	 * @precondition getIncomingPlaces()
	 */
	public Hashtable<Integer, Integer> getPost() {
		final Hashtable<Integer, Integer> post = new Hashtable<Integer, Integer>();

		for (Arc arc : startArcs) {
			Place p = (Place) arc.getEnd();
			post.put(Integer.valueOf(p.getId()), Integer.valueOf(arc.getMark()));
		}

		return post;
	}

	public List<Arc> getStartArcs() {
		return startArcs;
	}

	public List<Arc> getEndArcs() {
		return endArcs;
	}

	boolean removeStartArc(Arc arc) {
		return startArcs.remove(arc);
	}

	boolean removeEndArc(Arc arc) {
		return endArcs.remove(arc);
	}

	/**
	 * Checks whether this {@link Transition} is active. Only incoming arcs are
	 * checked
	 * 
	 * @return
	 */
	public boolean isActivated() {
		for (Arc a : getEndArcs()) {
			Place p = (Place) a.getStart();

			if (p.getMark() < a.getMark()) {
				return false;
			}
		}
		return true;
	}
}
