package haw.wp.rcpn.impl;

/**
* Diese Klasse stellt eine Transition in Petrinetze dar und
* bietet die dazu gehörige Methoden dafuer an.
* 
* @author Reiter, Safai
* @version 1.0
*/
import haw.wp.rcpn.ITransition;

import java.util.Hashtable;
import java.util.Vector;


public class Transition implements ITransition {

	private Hashtable<String, String> labels;
	
	
	/* (non-Javadoc)
	 * @see haw.wp.rcpn.ITransition#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.ITransition#getId()
	 */
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see haw.wp.rcpn.ITransition#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}


	
	@Override
	public Hashtable<String, String> getTlb() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTlb(Hashtable<String, String> labels) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rnwAsId() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rnwAsCount() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rnwAsUserDefined() {
		// TODO Auto-generated method stub
		
	}

}
