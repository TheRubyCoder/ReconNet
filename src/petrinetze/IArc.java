package petrinetze;
/**
 * Bildet die Kante eines Petrinetzes und bietet die entsprechenden
 * Methoden an.
 * 
 * @author Reiter, Safai
 *
 */
public interface IArc extends INode {

	String getName();

	int getId();

	void setName(String name);

	/**
	 * @return Die maximal mögliche Kantengewichtung. 
	 */
	int getMark();

	/**
	 * @param mark
	 * 			Die maximal mögliche Kantengewichtung.
	 */
	void setMark(int mark);

	/**
	 * @return Den Startknoten der Kante.
	 */
	INode getStart();

	/**
	 * @return Den Endknoten der Kante.
	 */
	INode getEnd();

	/**
	 * @param start
	 * 			Den Startknoten der Kante.
	 */
	void setStart(INode start) throws IllegalArgumentException;

	/**
	 * @param end
	 * 			Den Endknoten der Kante.
	 */
	void setEnd(INode end) throws IllegalArgumentException;

}