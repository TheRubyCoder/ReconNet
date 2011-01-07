package engine.impl.mock;

import petrinetze.ITransition;

import java.util.List;

public class Place extends Node implements petrinetze.IPlace {

	public Place() {

	}


	@Override
	public int getMark() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMark(int mark) {
		// TODO Auto-generated method stub

	}

    @Override
    public List<ITransition> getOutgoingTransitions() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<ITransition> getIncomingTransitions() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
