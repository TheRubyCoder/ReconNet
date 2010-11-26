package petrinetze;
/**
 * Bildet die Stelle eines Petrinetzes und bietet die
 * entsprechenden Methooden dafuer an.
 *  
 * @author Reiter, Safai
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
	 * @return Die Anzahl der maximal möglichen Token.
	 */
	public abstract int getMark();

	/**
	 * @param mark 
	 * 			Die Anzahl der maximal möglichen Token.
	 */
	public abstract void setMark(int mark);

}