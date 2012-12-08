package petrinet.model;

import java.util.Set;

/**
 * Marker interface for JUNG layouting
 */
public interface INode {
	/**
	 * @return Die ID des Knotens.
	 */
	public int getId();

	/**
	 * @return Den Namen des Knotens.
	 */
	public String getName();

	/**
	 * @param name Der Name des Knotens.
	 */
	public void setName(String name);
}