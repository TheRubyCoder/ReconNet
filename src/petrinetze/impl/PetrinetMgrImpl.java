package haw.wp.rcpn.impl;

import haw.wp.rcpn.IPetrinetMgr;
import haw.wp.rcpn.ISession;

public class PetrinetMgrImpl implements IPetrinetMgr {

	public PetrinetMgrImpl() {

		
	}

	@Override
	public ISession createSession() {
		return new Session();
	}

}
