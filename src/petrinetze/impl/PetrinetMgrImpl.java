package petrinetze.impl;

import petrinetze.IPetrinetMgr;
import petrinetze.ISession;

public class PetrinetMgrImpl implements IPetrinetMgr {

	public PetrinetMgrImpl() {

		
	}

	@Override
	public ISession createSession() {
		return new Session();
	}

}
