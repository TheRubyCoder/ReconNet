package petrinetze.impl;

import petrinetze.IPetrinet;
import petrinetze.ISession;

public class Session implements ISession {

	@Override
	public IPetrinet createLabeldPetrinet() {
		return new Petrinet();
	}

	@Override
	public void sub(IPetrinet net) {
		
	}

	@Override
	public void add(IPetrinet net) {
		
	}

	@Override
	public void setRunning(boolean state) {
		
	}

}
