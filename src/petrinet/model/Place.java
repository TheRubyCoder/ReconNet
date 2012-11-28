package petrinet.model;

import java.util.ArrayList;
import java.util.List;

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
	 * list of incoming arcs
	 */
	private List<PostArc> incomingArcs;
	/**
	 * list of outgoing arcs
	 */
	private List<PreArc> outgoingArcs;

	/**
	 * Creates a new {@link Place} without name or arcs
	 * @param id
	 */
	public Place(int id) {
		this.id           = id;
		this.incomingArcs = new ArrayList<PostArc>();
		this.outgoingArcs = new ArrayList<PreArc>();
	}

	public void addIncomingArc(PostArc arc) {
		this.incomingArcs.add(arc);
	}

	public void addOutgoingArc(PreArc arc) {
		this.outgoingArcs.add(arc);
	}

	public List<PostArc> getIncomingArcs() {
		return incomingArcs;
	}

	public List<PreArc> getOutgoingArcs() {
		return outgoingArcs;
	}

	/**
	 * Removes a single arc from the outgoing arcs
	 * @param arc
	 * @return <code>false</code> if arc was not in the outgoing arcs
	 */
	public boolean removeOutgoingArc(PreArc arc) {
		return outgoingArcs.remove(arc);
	}

	/**
	 * Removes a single arc from the incoming arcs
	 * @param arc
	 * @return <code>false</code> if arc was not in the incoming arcs
	 */
	public boolean removeIncomingArc(PostArc arc) {
		return incomingArcs.remove(arc);
	}

	/**
	 * Returns the {@link Transition transitions} that are at the other end
	 * of incoming arcs
	 * @return
	 */
	public List<Transition> getIncomingTransitions() {
		List<Transition> in = new ArrayList<Transition>();
		
		for (PostArc arc : incomingArcs) {
			in.add(arc.getSource());
		}
		
		return in;
	}

	/**
	 * Returns the {@link Transition transitions} that are at the other end
	 * of outgoing arcs
	 * @return
	 */
	public List<Transition> getOutgoingTransitions() {
		List<Transition> out = new ArrayList<Transition>();
		
		for (PreArc arc : outgoingArcs) {
			out.add(arc.getTarget());
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
