package petrinetze.impl;

import java.util.ArrayList;
import java.util.List;

import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

import petrinetze.IArc;
import petrinetze.IPlace;
import petrinetze.ITransition;

/**
* Diese Klasse stellt eine Stelle in Petrinetze dar und
* bietet die dazu gehörige Methoden dafuer an.
* 
* @author Reiter, Safai
* @version 1.0
*/

public class Place implements IPlace {
	private String name;
	/**
	 * Die maximal mögliche Anzahl von Token.
	 */
	private int mark;
	private int id;
	/**
	 * Liste aller Kanten, die von dieser Stelle
	 * abgehen.
	 */
	private List<IArc> startArcs;
	/**
	 * Liste aller Kanten, die in diese Stelle
	 * eingehen.
	 */
	private List<IArc> endArcs;
	
	public void setStartArcs (IArc arc) {
		this.startArcs.add(arc);
	}
	public void setEndArcs (IArc arc) {
		this.endArcs.add(arc);
	}

	public Place(int id) {
		super();
		this.id = id;
		this.endArcs = new ArrayList<IArc>();
		this.startArcs = new ArrayList<IArc>();
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.IPlace#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.IPlace#getId()
	 */
	@Override
	public int getId() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.IPlace#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}


	/* (non-Javadoc)
	 * @see haw.wp.rcpn.IPlace#getMark()
	 */
	@Override
	public int getMark() {
		return mark;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.IPlace#setMark(int)
	 */
	@Override
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
	
	@Override
	public List<ITransition> getIncomingTransitions() {
		List<ITransition> in = new ArrayList<ITransition>();
		for (IArc arc : endArcs) {
			in.add((ITransition) arc.getStart());
		}
		return in;
	}
	
	@Override
	public List<ITransition> getOutgoingTransitions() {
		List<ITransition> out = new ArrayList<ITransition>();
		for (IArc arc : startArcs) {
			out.add((ITransition) arc.getEnd());
		}
		return out;
	}

	
	
	
}
