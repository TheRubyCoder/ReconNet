package petrinetze.impl;

/**
* Diese Klasse stellt eine Transition in Petrinetze dar und
* bietet die dazu gehörige Methoden dafuer an.
* 
* @author Reiter, Safai
* @version 1.0
*/
import java.util.*;

import petrinetze.ActionType;
import petrinetze.IArc;
import petrinetze.IPetrinetListener;
import petrinetze.IPlace;
import petrinetze.IRenew;
import petrinetze.ITransition;


public class Transition implements ITransition {

	private int id;
	
    private String name;

    private IRenew rnw;

    private String tlb = "";

	/**
	 * Liste aller Kanten, die von dieser Transition
	 * abgehen.
	 */
	private List<IArc> startArcs;
	/**
	 * Liste aller Kanten, die in diese Transition
	 * eingehen.
	 */
	private List<IArc> endArcs;
	
	@Override
	public void setStartArcs (IArc arc) {
		this.startArcs.add(arc);
		petrinet.onNodeChanged(this, ActionType.changed);
	}
	@Override
	public void setEndArcs (IArc arc) {
		this.endArcs.add(arc);
		petrinet.onNodeChanged(this, ActionType.changed);
	}
	
	private final Petrinet petrinet;
	
	public Transition(int id, IRenew rnw, Petrinet petrinet) {
		this.id = id;
		this.rnw = rnw;
		this.endArcs = new ArrayList<IArc>();
		this.startArcs = new ArrayList<IArc>();
		this.petrinet = petrinet;
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
		return tlb;
	}

    @Override
    public void setTlb(String tlb) {
        if (!rnw.isTlbValid(tlb)) {
            throw new IllegalArgumentException("Invalid tlb: " + tlb + " for rnw " + rnw);
        }
        this.tlb = tlb;
    }

    @Override
	public String rnw() {
		this.tlb = rnw.renew(this.tlb);
        return tlb;
	}

	@Override
	public void setRnw(IRenew rnw) {
		this.rnw = rnw;
		petrinet.onNodeChanged(this, ActionType.changed);
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
		return "Transition [Label=" + tlb + ", id=" + id + ", name=" + name
				+ "]";
	}
	@Override
	public List<IPlace> getOutgoingPlaces() {
		List<IPlace> out = new ArrayList<IPlace>(startArcs.size());
		for (IArc arc : startArcs) {
			IPlace p = (IPlace) arc.getEnd();
			out.add(p);
		}
		return out;
	}
	@Override
	public List<IPlace> getIncomingPlaces() {
		List<IPlace> in = new ArrayList<IPlace>(endArcs.size());

		for (IArc arc : endArcs) {
			in.add((IPlace) arc.getStart());
		}

		return in;
	}
	
	/**
	 * @precondition getOutgoingPlaces()
	 */
	@Override
	public Hashtable<Integer, Integer> getPre() {
        final Hashtable<Integer,Integer> pre = new Hashtable<Integer,Integer>();

		for (IArc arc : endArcs) {
			IPlace p = (IPlace) arc.getStart();
			pre.put(Integer.valueOf(p.getId()), Integer.valueOf(arc.getMark()));
		}

        return pre;
	}
	
	/**
	 * @precondition getIncomingPlaces()
	 */
	@Override
	public Hashtable<Integer, Integer> getPost() {
        final Hashtable<Integer,Integer> post = new Hashtable<Integer,Integer>();

		for (IArc arc : startArcs) {
			IPlace p = (IPlace) arc.getEnd();
			post.put(Integer.valueOf(p.getId()), Integer.valueOf(arc.getMark()));
		}

        return post;
	}
	@Override
	public List<IArc> getStartArcs() {
		return startArcs;
	}
	@Override
	public List<IArc> getEndArcs() {
		return endArcs;
	}

    boolean removeStartArc(IArc arc) {
        return startArcs.remove(arc);
    }

    boolean removeEndArc(IArc arc) {
        return endArcs.remove(arc);
    }

    @Override
    public boolean isActivated() {
        // TODO aus Petrinet übernommen - prüfen!
        for (IArc a : getEndArcs()) {
			IPlace p = (IPlace) a.getStart();

			if (p.getMark() < a.getMark()) {
				return false;
			}
		}
		return true;
    }
}
