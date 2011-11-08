package petrinetze;

import java.util.List;

/**
 * Bildet die Stelle eines Petrinetzes und bietet die
 * entsprechenden Methooden dafuer an.
 *  
 * @author Reiter, Safai
 * @see INode for exlplanation
 *
 */
public interface IPlace extends INode {

	/* (non-Javadoc)
	 * @see Node#getLabel()
	 */
	public abstract String getName();

	/* (non-Javadoc)
	 * @see Node#getId()
	 */
	public abstract int getId();

	/* (non-Javadoc)
	 * @see Node#setLabel(java.lang.String)
	 */
	public abstract void setName(String name);

	/**
	 * @return Die Anzahl der Token.
	 */
	public abstract int getMark();

	/**
	 * @param mark Die Anzahl der Token.
	 */
	public abstract void setMark(int mark);
	/**
	 * Liefer alle abgehende Transitionen 
	 * @return
	 */
	public List<ITransition> getOutgoingTransitions();
	
	/**
	 * Liefert alle eingehende Transitionen
	 * @return
	 */
	public List<ITransition> getIncomingTransitions();

	/**
	 * Liefert alle ausgehende Kanten
	 * @return
	 */
	public List<IArc> getStartArcs();
	
	/**
	 * Liefert alle eingehende Kanten
	 * @return
	 */
	public List<IArc> getEndArcs();

    // TODO umbenennen in addStartArc(), warum public?
    public void setStartArcs (IArc arc);

    // TODO umbenennen in addEndArc(), warum public?
    public void setEndArcs (IArc arc);
}