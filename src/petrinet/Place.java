package petrinet;

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
	private int id;
	/**
	 * list of outgoing arcs
	 */
	private List<Arc> startArcs;
	/**
	 * list of incoming arcs
	 */
	private List<Arc> endArcs;

	public void setStartArcs(Arc arc) {
		this.startArcs.add(arc);
	}

	public void setEndArcs(Arc arc) {
		this.endArcs.add(arc);
	}

	/**
	 * Creates a new {@link Place} without name or arcs
	 * @param id
	 */
	Place(int id) {
		this.id = id;
		this.endArcs = new ArrayList<Arc>();
		this.startArcs = new ArrayList<Arc>();
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
	 * @see haw.wp.rcpn.Place#getId()
	 */
	public int getId() {
		return this.id;
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
		Place other = (Place) obj;
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
		return "Place [name=" + name + ", mark=" + mark + ", id=" + id + "]";
	}

	/**
	 * Returns the {@link Transition transitions} that are at the other end
	 * of incoming arcs
	 * @return
	 */
	public List<Transition> getIncomingTransitions() {
		List<Transition> in = new ArrayList<Transition>();
		for (Arc arc : endArcs) {
			in.add((Transition) arc.getStart());
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
		for (Arc arc : startArcs) {
			out.add((Transition) arc.getEnd());
		}
		return out;
	}

	public List<Arc> getStartArcs() {
		return startArcs;
	}

	public List<Arc> getEndArcs() {
		return endArcs;
	}

	/**
	 * Removes a single arc from the outgoing arcs
	 * @param arc
	 * @return <code>false</code> if arc was not in the outgoing arcs
	 */
	public boolean removeStartArc(Arc arc) {
		return startArcs.remove(arc);
	}

	/**
	 * Removes a single arc from the incoming arcs
	 * @param arc
	 * @return <code>false</code> if arc was not in the incoming arcs
	 */
	public boolean removeEndArc(Arc arc) {
		return endArcs.remove(arc);
	}
}
