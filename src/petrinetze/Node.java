package petrinetze;
/**
 * Ist die Oberklasse fuer Stellen und Transition in Petrinetze, 
 * und stellt die gemeinsamen Methoden zur Verfügung.
 * 
 * @author Reiter, Safai
 * @version 1.0
 */
public abstract class Node {



	private String name;
	
	private int id;
	
	/**
	 * @return Den Namen des Knotens.
	 */
	public abstract String getName();

	/**
	 * @return Die ID des Knotens.
	 */
	public abstract int getId();

	/**
	 * @param name
	 * 			Der Name des Knotens.
	 */
	public abstract void setName(String name);

	/**
	 * @param id
	 * 			Die ID des Knotens.
	 */
	public abstract void setId(int id);

	
	
	
}
