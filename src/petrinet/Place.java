package petrinet;

import java.util.ArrayList;
import java.util.List;



/**
* Diese Klasse stellt eine Stelle in Petrinetze dar und
* bietet die dazu gehoerige Methoden dafuer an.
* 
* @author Reiter, Safai
* @version 1.0
*/

public class Place implements INode{
	private String name;
	/**
	 * Die maximal moegliche Anzahl von Token.
	 */
	private int mark;
	private int id;
	/**
	 * Liste aller Kanten, die von dieser Stelle
	 * abgehen.
	 */
	private List<Arc> startArcs;
	/**
	 * Liste aller Kanten, die in diese Stelle
	 * eingehen.
	 */
	private List<Arc> endArcs;
	
	public void setStartArcs (Arc arc) {
		this.startArcs.add(arc);
	}
	public void setEndArcs (Arc arc) {
		this.endArcs.add(arc);
	}

	public Place(int id) {
		super();
		this.id = id;
		this.endArcs = new ArrayList<Arc>();
		this.startArcs = new ArrayList<Arc>();
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.Place#getName()
	 */
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.Place#getId()
	 */
	public int getId() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.Place#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}


	/* (non-Javadoc)
	 * @see haw.wp.rcpn.Place#getMark()
	 */
	public int getMark() {
		return mark;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.Place#setMark(int)
	 */
	public void setMark(int mark) {
		this.mark = mark;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/* (non-Javadoc)
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Place [name=" + name + ", mark=" + mark + ", id=" + id + "]";
	}
	
	public List<Transition> getIncomingTransitions() {
		List<Transition> in = new ArrayList<Transition>();
		for (Arc arc : endArcs) {
			in.add((Transition) arc.getStart());
		}
		return in;
	}
	
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
}
