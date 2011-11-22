package petrinet;



/**
 * Bildet die Kante eines Petrinetzes und bietet die entsprechenden
 * Methoden an.
 */
public class Arc implements INode{

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

	
	public String getName() {
		return this.name;
	}

	
	public int getId() {
		return this.id;
	}

	/**
	 * @see haw.wp.rcpn.impl.IArc#setName(java.lang.String)
	 */
	
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Die maximal mögliche Kantengewichtung. 
	 */
	
	public int getMark() {
		return mark;
	}
	
	/**
	 * @param mark
	 * 			Die maximal mögliche Kantengewichtung.
	 */
	
	public void setMark(int mark) {
		if (mark > 0) {
			this.mark = mark;
			petrinet.onEdgeChanged(this, ActionType.changed);
		}
	}

	/**
	 * @return Den Startknoten der Kante.
	 */
	
	public INode getStart() {
		return start;
	}

	/**
	 * @return Den Endknoten der Kante.
	 */
	
	public INode getEnd() {
		return end;
	}

	
	public void setStart(INode start) throws IllegalArgumentException
 {
		if (!isValidPrecondition(start, end)) {
			throw new IllegalArgumentException("Start und Ende muessen unterschiedliche Knotenarten haben");
		}
		this.start = start;
		//Jede StartKante registriert sich bei dem entsprechenden
		//INode als Startkante, damit die Berechnung von Pre und
		//Post von statten gehen kann.
		if (start instanceof Place) {
			((Place) this.start).setStartArcs(this);
		} else {
			((Transition) this.start).setStartArcs(this);
		}
		petrinet.onEdgeChanged(this, ActionType.changed);
		
	}

	private static boolean isValidPrecondition(INode start, INode end) {
		
		if (start instanceof Place) {
			return (end instanceof Transition);
		}
		if (start instanceof Transition) {
			return (end instanceof Place);
		}
		return false;
	}

	/**
	 * @param end
	 * 			Den Endknoten der Kante.
	 */
	
	public void setEnd(INode end) throws IllegalArgumentException
{
		if (!isValidPrecondition(start, end)) {
			throw new IllegalArgumentException("Start und Ende muessen unterschiedliche Knotenarten haben");
		}
		this.end = end;
		//Jede EndKante registriert sich bei dem entsprechenden
		//INode als Endkante, damit die Berechnung von Pre und
		//Post von statten gehen kann.
		if (end instanceof Place) {
			((Place) this.end).setEndArcs(this);
		} else {
			((Transition) this.end).setEndArcs(this);
		}
		this.end = end;
		petrinet.onEdgeChanged(this, ActionType.changed);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	public String toString() {
		return "Arc [mark=" + mark + ", name=" + name + ", start=" + start
				+ ", end=" + end + ", id=" + id + "]";
	}
}

