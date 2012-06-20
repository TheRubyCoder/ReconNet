package petrinet;
/**
 * Marker interface for JUNG layouting
 */
public interface INode {

	/**
	 * @return Den Namen des Knotens.
	 */
	public String getName();

	/**
	 * @return Die ID des Knotens.
	 */
	public int getId();

	/**
	 * @param name
	 * 			Der Name des Knotens.
	 */
	public void setName(String name);

}