package haw.wp.rcpn;
/**
 * Ist die Oberklasse fuer Place und Transitionen
 * und bietet die gemeinsamen Methoden an.
 * 
 * @author Reiter, Safai
 *
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