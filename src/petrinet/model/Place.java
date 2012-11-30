package petrinet.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections15.BidiMap;
import org.apache.commons.collections15.bidimap.DualHashBidiMap;

/**
 * This class represents a place within a petrinet, holding information about
 * name, mark and arcs
 * 
 * @author Reiter, Safai
 * @version 1.0
 */

public class Place implements INode {
	private String name;
	/**
	 * Capacity of the node
	 */
	private int mark;
	/**
	 * Unique id
	 */
	private final int id;
	/**
	 * bijective Map of incoming arcs
	 */
	private BidiMap<Integer, PostArc> incomingArcs;
	/**
	 * bijective Map of outgoing arcs
	 */
	private BidiMap<Integer, PreArc> outgoingArcs;

	/**
	 * Creates a new {@link Place} without name or arcs
	 * @param id
	 */
	public Place(int id) {
		this.id           = id;
		this.incomingArcs = new DualHashBidiMap<Integer, PostArc>();
		this.outgoingArcs = new DualHashBidiMap<Integer, PreArc>();
	}

	public void addIncomingArc(PostArc arc) {
		this.incomingArcs.put(arc.getId(), arc);
	}

	public void addOutgoingArc(PreArc arc) {
		this.outgoingArcs.put(arc.getId(), arc);
	}

	public Set<PostArc> getIncomingArcs() {
		return incomingArcs.values();
	}

	public Set<PreArc> getOutgoingArcs() {
		return outgoingArcs.values();
	}

	/**
	 * Removes a single arc from the outgoing arcs
	 * @param arc
	 * @return <code>false</code> if arc was not in the outgoing arcs
	 */
	public boolean removeOutgoingArc(PreArc arc) {
		return outgoingArcs.remove(arc.getId()) != null;
	}

	/**
	 * Removes a single arc from the incoming arcs
	 * @param arc
	 * @return <code>false</code> if arc was not in the incoming arcs
	 */
	public boolean removeIncomingArc(PostArc arc) {
		return incomingArcs.remove(arc.getId()) != null;
	}

	/**
	 * Returns the {@link Transition transitions} that are at the other end
	 * of incoming arcs
	 * @return
	 */
	public Set<Transition> getIncomingTransitions() {
		Set<Transition> in = new HashSet<Transition>();
		
		for (PostArc arc : incomingArcs.values()) {
			in.add(arc.getTransition());
		}
		
		return in;
	}

	/**
	 * Returns the {@link Transition transitions} that are at the other end
	 * of outgoing arcs
	 * @return
	 */
	public Set<Transition> getOutgoingTransitions() {
		Set<Transition> out = new HashSet<Transition>();
		
		for (PreArc arc : outgoingArcs.values()) {
			out.add(arc.getTransition());
		}
		
		return out;
	}
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see haw.wp.rcpn.Place#getName()
	 */
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see haw.wp.rcpn.Place#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see haw.wp.rcpn.Place#getId()
	 */
	public int getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see haw.wp.rcpn.Place#getMark()
	 */
	public int getMark() {
		return mark;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see haw.wp.rcpn.Place#setMark(int)
	 */
	public void setMark(int mark) {
		if (mark < 0) {
			throw new IllegalArgumentException("mark is negative");
		}
		
		this.mark = mark;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Place [name=" + name + ", mark=" + mark + ", id=" + id + "]";
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
		
		if (!(object instanceof Place)) {
			return false;
		}
		
		Place place = (Place) object;
		
		return id == place.getId();
	}
}
