package petrinetze.impl;

/**
* Diese Klasse stellt eine Transition in Petrinetze dar und
* bietet die dazu gehörige Methoden dafuer an.
* 
* @author Reiter, Safai
* @version 1.0
*/
import java.util.ArrayList;
import java.util.List;

import petrinetze.IRenew;
import petrinetze.ITransition;


public class Transition implements ITransition {

	private int id;
	private String name;
	private IRenew rnw;
	/**
	 * Liste aller Kanten, die von dieser Transition
	 * abgehen.
	 */
	private List startArcs;
	/**
	 * Liste aller Kanten, die in diese Transition
	 * eingehen.
	 */
	private List endArcs;
	
	public void setStartArcs (int arcId) {
		this.startArcs.add(arcId);
	}
	public void setEndArcs (int arcId) {
		this.endArcs.add(arcId);
	}
	
	public Transition(int id, IRenew rnw) {
		this.id = id;
		this.rnw = rnw;
		this.endArcs = new ArrayList();
		this.startArcs = new ArrayList();
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.ITransition#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.ITransition#getId()
	 */
	@Override
	public int getId() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.ITransition#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}


	
	@Override
	public String getTlb() {
		return this.rnw.getTlb() ;
	}


//	@Override
//	public void rnwAsUserDefined() {
//		
//	}

	@Override
	public String rnw(String tlb) {
		return rnw.renew(tlb);
	}

	@Override
	public void setRnw(IRenew rnw) {
		this.rnw = rnw;
	}

	@Override
	public IRenew getRnw() {
		return this.rnw;
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
		Transition other = (Transition) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Transition [" + ", id=" + id + ", name=" + name
				+ "]";
	}
	@Override
	public List<ITransition> getOutgoingPlaces() {
		
		return null;
	}
	@Override
	public List<ITransition> getIncomingPlaces() {
		return null;
	}
	
	

}
