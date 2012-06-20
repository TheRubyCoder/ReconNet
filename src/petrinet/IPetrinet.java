package petrinet;

import java.util.Collection;

/**
 * Interface for accessing petrinet component so other components do not need to
 * directly access classes within the component
 */
public interface IPetrinet {

	public Petrinet createPetrinet();

	public Petrinet getPetrinetById(int id);

	/**
	 * Deletes an element (Place, Transition, Arc) from a petrinet
	 * 
	 * @param petrinetId
	 *            id of petrinet
	 * @param elementId
	 *            id of element
	 * @return A Collection of all elements (their ids) that got deleted
	 * @example a transition (id: 1) with 4 adjacent arcs(id 2-5) is deleted.<br/>
	 *          return value: (1,2,3,4,5)
	 */
	public Collection<Integer> deleteElementInPetrinet(int petrinetId,
			int elementId);

	/**
	 * Returns the type of a node within a petrinet
	 * @param petrinetId id of the petrinet containing the node
	 * @param nodeId id of the node
	 * @return {@link ElementType#INVALID} if node is not in the petrinet
	 * @throws NullPointerException if petrinet does not exist
	 */
	public ElementType getNodeType(int petrinetId, int nodeId);

}
