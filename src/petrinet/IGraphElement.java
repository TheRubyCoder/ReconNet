package petrinet;

import java.util.Set;


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
	public Set<Arc> getAllArcs();
}
