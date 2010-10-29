package petrinetze;
/**
* Diese Klasse stellt eine Stelle in Petrinetze dar und
* bietet die dazu gehörige Methoden dafuer an.
* 
* @author Reiter, Safai
* @version 1.0
*/

public class Place extends Node {

	/**
	 * Die maximal mögliche Anzahl von Token.
	 */
	private int mark;
	
	
	/* (non-Javadoc)
	 * @see Node#getLabel()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see Node#getId()
	 */
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see Node#setLabel(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see Node#setId(int)
	 */
	@Override
	public void setId(int id) {
		// TODO Auto-generated method stub
	}

	/**
	 * @return Die Anzahl der maximal möglichen Token.
	 */
	public int getMark() {
		return mark;
	}

	/**
	 * @param mark
	 * 			Die Anzahl der maximal möglichen Token.
	 */
	public void setMark(int mark) {
		this.mark = mark;
	}

	
	
}
