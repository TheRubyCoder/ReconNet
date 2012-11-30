package petrinet.model;

public interface IArc {

	public abstract String getName();

	public abstract int getId();

	/**
	 * @see haw.wp.rcpn.impl.IArc#setName(java.lang.String)
	 */
	public abstract void setName(String name);

	/**
	 * @return Maximum weight
	 */
	public abstract int getWeight();

	public abstract void setWeight(int weight);

	/**
	 * @return Den Startknoten der Kante.
	 */
	public abstract INode getSource();

	/**
	 * @return Den Endknoten der Kante.
	 */
	public abstract INode getTarget();
}