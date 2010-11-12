package haw.wp.rcpn.impl;

import haw.wp.rcpn.INode;

/**
 * Ist die Oberklasse fuer Stellen und Transition in Petrinetze, 
 * und stellt die gemeinsamen Methoden zur Verfügung.
 * 
 * @author Reiter, Safai
 * @version 1.0
 */
public abstract class Node implements INode {



	private String name;
	
	private int id;
	
	/* (non-Javadoc)
	 * @see haw.wp.rcpn.INode#getName()
	 */
	@Override
	public abstract String getName();

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.INode#getId()
	 */
	@Override
	public abstract int getId();

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.INode#setName(java.lang.String)
	 */
	@Override
	public abstract void setName(String name);


}
