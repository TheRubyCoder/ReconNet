package haw.wp.rcpn.impl;

import haw.wp.rcpn.IPlace;

/**
* Diese Klasse stellt eine Stelle in Petrinetze dar und
* bietet die dazu gehörige Methoden dafuer an.
* 
* @author Reiter, Safai
* @version 1.0
*/

public class Place implements IPlace {

	/**
	 * Die maximal mögliche Anzahl von Token.
	 */
	private int mark;

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.IPlace#getName()
	 */
	@Override
	public String getName() {
		return null;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.IPlace#getId()
	 */
	@Override
	public int getId() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.IPlace#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		
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

	
	
}
