package petrinetze.impl;

import petrinetze.IPetrinet;
import petrinetze.IPetrinetMgr;

public class PetrinetMgrImpl implements IPetrinetMgr {

	public PetrinetMgrImpl() {

		
	}

	@Override
	public IPetrinet createPetrinet() {
		
		return new Petrinet();
	}

}
