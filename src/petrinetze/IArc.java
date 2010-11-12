package petrinetze;
/**
 * Bildet die Kante eines Petrinetzes und bietet die entsprechenden
 * Methoden an.
 * 
 * @author Reiter, Safai
 *
 */
public interface IArc extends INode {

	public String getName();

	public int getId();

	public void setName(String name);

	/**
	 * @return Die maximal mögliche Kantengewichtung. 
	 */
	public int getMark();

	/**
	 * @param mark
	 * 			Die maximal mögliche Kantengewichtung.
	 */
	public void setMark(int mark);

	/**
	 * @return Den Startknoten der Kante.
	 */
	public INode getStart();

	/**
	 * @return Den Endknoten der Kante.
	 */
	public INode getEnd();

	/**
	 * @param start
	 * 			Den Startknoten der Kante.
	 */
	public void setStart(INode start);

	/**
	 * @param end
	 * 			Den Endknoten der Kante.
	 */
	public void setEnd(INode end);

}