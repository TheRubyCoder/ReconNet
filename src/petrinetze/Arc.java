package petrinetze;
/**
* Diese Klasse stellt eine Kante in Petrinetze dar und
* bietet die dazu gehörige Methoden dafuer an.
* 
* @author Reiter, Safai
* @version 1.0
*/

public class Arc extends Node {

	/**
	 *  Die maximal mögliche Kantengewichtung.
	 */
	private int mark;
	
	/**
	 *  Der Startknoten der Kante.
	 */
	private Node start;
	
	/**
	 * 	Der Endknoten der Kante.
	 */
	private Node end;
	
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setId(int id) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return Die maximal mögliche Kantengewichtung. 
	 */
	public int getMark() {
		return mark;
	}
	
	/**
	 * @param mark
	 * 			Die maximal mögliche Kantengewichtung.
	 */
	public void setMark(int mark) {
		this.mark = mark;
	}

	/**
	 * @return Den Startknoten der Kante.
	 */
	public Node getStart() {
		return start;
	}

	/**
	 * @return Den Endknoten der Kante.
	 */
	public Node getEnd() {
		return end;
	}

	/**
	 * @param start
	 * 			Den Startknoten der Kante.
	 */
	public void setStart(Node start) {
		this.start = start;
	}

	/**
	 * @param end
	 * 			Den Endknoten der Kante.
	 */
	public void setEnd(Node end) {
		this.end = end;
	}

}
