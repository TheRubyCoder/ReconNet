package petrinetze.impl;

import petrinetze.IPlace;

/**
* Diese Klasse stellt eine Stelle in Petrinetze dar und
* bietet die dazu gehörige Methoden dafuer an.
* 
* @author Reiter, Safai
* @version 1.0
*/

public class Place implements IPlace {
	private String name;
	/**
	 * Die maximal mögliche Anzahl von Token.
	 */
	private int mark;
	private int id;
	
	

	public Place(int id) {
		super();
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.IPlace#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.IPlace#getId()
	 */
	@Override
	public int getId() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.IPlace#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}


	/* (non-Javadoc)
	 * @see haw.wp.rcpn.IPlace#getMark()
	 */
	@Override
	public int getMark() {
		return mark;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.IPlace#setMark(int)
	 */
	@Override
	public void setMark(int mark) {
		this.mark = mark;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Place other = (Place) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	
}
