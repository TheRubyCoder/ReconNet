package haw.wp.rcpn.impl;


import haw.wp.rcpn.INode;
import haw.wp.rcpn.IArc;

/**
* Diese Klasse stellt eine Kante in Petrinetze dar und
* bietet die dazu gehörige Methoden dafuer an.
* 
* @author Reiter, Safai
* @version 1.0
*/

public class Arc implements IArc{

	//TODO Die Invariante prüfern
	//Zu einem Arc gehoert genau 1 T und 1 P
	
	/**
	 *  Die maximal mögliche Kantengewichtung.
	 */
	private int mark;
	
	/**
	 *  Der Startknoten der Kante.
	 */
	private INode start;
	
	/**
	 * 	Der Endknoten der Kante.
	 */
	private INode end;
	
	
	/* (non-Javadoc)
	 * @see haw.wp.rcpn.impl.IArc#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.impl.IArc#getId()
	 */
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.impl.IArc#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

//	@Override
//	public void setId(int id) {
//		// TODO Auto-generated method stub
//
//	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.impl.IArc#getMark()
	 */
	@Override
	public int getMark() {
		return mark;
	}
	
	/* (non-Javadoc)
	 * @see haw.wp.rcpn.impl.IArc#setMark(int)
	 */
	@Override
	public void setMark(int mark) {
		this.mark = mark;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.impl.IArc#getStart()
	 */
	@Override
	public INode getStart() {
		return start;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.impl.IArc#getEnd()
	 */
	@Override
	public INode getEnd() {
		return end;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.impl.IArc#setStart(haw.wp.rcpn.INode)
	 */
	@Override
	public void setStart(INode start) {
		this.start = start;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.impl.IArc#setEnd(haw.wp.rcpn.INode)
	 */
	@Override
	public void setEnd(INode end) {
		this.end = end;
	}

}
