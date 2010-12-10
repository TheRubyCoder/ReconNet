package engine.impl.mock;

import java.util.Hashtable;
import java.util.List;

import petrinetze.IPlace;
import petrinetze.IRenew;

public class Transition extends Node implements petrinetze.ITransition {

	@Override
	public String getTlb() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public void setTlb(String tlb) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public String rnw() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRnw(IRenew rnw) {
		// TODO Auto-generated method stub

	}

	@Override
	public IRenew getRnw() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public List<IPlace> getOutgoingPlaces() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<IPlace> getIncomingPlaces() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


}
