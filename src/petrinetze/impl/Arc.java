package petrinetze.impl;


import petrinetze.ActionType;
import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPlace;
import petrinetze.ITransition;

/**
* Diese Klasse stellt eine Kante in Petrinetze dar und
* bietet die dazu gehoerige Methoden dafuer an.
* 
* @author Reiter, Safai
* @version 1.0
*/

class Arc implements IArc {

	/**
	 *  Die maximal moegliche Kantengewichtung.
	 */
	private int mark = 1;
	private String name;
	
	/**
	 *  Der Startknoten der Kante.
	 */
	private INode start;
	
	/**
	 * 	Der Endknoten der Kante.
	 */
	private INode end;

	private int id;
	
	private final Petrinet petrinet;
	
	public Arc(int id, Petrinet petrinet, INode start, INode end) {
		if(start.getClass().equals(end.getClass()))
			throw new IllegalArgumentException("can not create an edge from " + start.getClass().getName() + " to " + end.getClass().getName());
		this.id = id;
		this.petrinet = petrinet;
		this.start = start;
		this.end = end;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.impl.IArc#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.impl.IArc#getId()
	 */
	@Override
	public int getId() {
		return this.id;
	}

	/**
	 * @see haw.wp.rcpn.impl.IArc#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.impl.IArc#getMark()
	 */
	@Override
	public int getMark() {
		return mark;
	}
	
	/* (non-Javadoc)
	 * @see haw.wp.rcpn.impl.IArc#setMark(int)
	 */
	@Override
	public void setMark(int mark) {
		if (mark > 0) {
			this.mark = mark;
			petrinet.onEdgeChanged(this, ActionType.changed);
		}
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.impl.IArc#getStart()
	 */
	@Override
	public INode getStart() {
		return start;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.impl.IArc#getEnd()
	 */
	@Override
	public INode getEnd() {
		return end;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.impl.IArc#setStart(haw.wp.rcpn.INode)
	 */
	@Override
	public void setStart(INode start) throws IllegalArgumentException
 {
		if (!isValidPrecondition(start, end)) {
			throw new IllegalArgumentException("Start und Ende muessen unterschiedliche Knotenarten haben");
		}
		this.start = start;
		//Jede StartKante registriert sich bei dem entsprechenden
		//INode als Startkante, damit die Berechnung von Pre und
		//Post von statten gehen kann.
		if (start instanceof IPlace) {
			((IPlace) this.start).setStartArcs(this);
		} else {
			((ITransition) this.start).setStartArcs(this);
		}
		petrinet.onEdgeChanged(this, ActionType.changed);
		
	}

	private static boolean isValidPrecondition(INode start, INode end) {
		
		if (start instanceof IPlace) {
			return (end instanceof ITransition);
		}
		if (start instanceof ITransition) {
			return (end instanceof IPlace);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.impl.IArc#setEnd(haw.wp.rcpn.INode)
	 */
	@Override
	public void setEnd(INode end) throws IllegalArgumentException
{
		if (!isValidPrecondition(start, end)) {
			throw new IllegalArgumentException("Start und Ende muessen unterschiedliche Knotenarten haben");
		}
		this.end = end;
		//Jede EndKante registriert sich bei dem entsprechenden
		//INode als Endkante, damit die Berechnung von Pre und
		//Post von statten gehen kann.
		if (end instanceof IPlace) {
			((IPlace) this.end).setEndArcs(this);
		} else {
			((ITransition) this.end).setEndArcs(this);
		}
		this.end = end;
		petrinet.onEdgeChanged(this, ActionType.changed);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Arc [mark=" + mark + ", name=" + name + ", start=" + start
				+ ", end=" + end + ", id=" + id + "]";
	}
}
