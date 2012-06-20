package petrinet;

/**
 * The arc of a petrinet. Holding information about its marking as well as
 * references to its source and target nodes.
 */
public class Arc implements INode {

	/**
	 * Maximal weight
	 */
	private int mark = 1;

	/**
	 * Name of the arc
	 */
	private String name;

	/**
	 * start node of arc
	 */
	private INode start;

	/**
	 * target node of arc
	 */
	private INode end;

	/**
	 * Unique id
	 */
	private int id;

	/**
	 * The petrinet of the arc
	 */
	private final Petrinet petrinet;

	/**
	 * Creates a new arc
	 * 
	 * @param id
	 *            Unique id
	 * @param petrinet
	 *            The petrinet of the arc
	 * @param start
	 *            start node of arc
	 * @param end
	 *            target node of arc
	 */
	Arc(int id, Petrinet petrinet, INode start, INode end) {
		if (start.getClass().equals(end.getClass()))
			throw new IllegalArgumentException("can not create an edge from "
					+ start.getClass().getName() + " to "
					+ end.getClass().getName());
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
	 * @return Maximum weight
	 */
	public int getMark() {
		return mark;
	}

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


	@Override
	public String toString() {
		return "Arc [mark=" + mark + ", name=" + name + ", start=" + start
				+ ", end=" + end + ", id=" + id + "]";
	}
}
