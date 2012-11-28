package petrinet.model;

import java.util.Set;


/**
 * For accessing all nodes and arcs of a petrinet 
 */
public interface IGraphElement {
	/**
	 * Liefert alle Stellen und Transitionen
	 * @return {@link}
	 */
	public Set<INode> getAllNodes();
	/**
	 * Liefert alle Kanten
	 * @return {@link}
	 */
	public Set<IArc> getAllArcs();
}
