package engine.net.impl;

import engine.net.NetNode;
import engine.net.Place;
import engine.net.Transition;

class TransitionImpl extends NetNodeImpl implements Transition {

	protected TransitionImpl(String label) {
		super(label);
	}
	
	@Override
	public void connectTo(NetNode other, int weight, String label) {
		if(!(other instanceof Place)) {
			throw new IllegalArgumentException("Transitions can only be connected to Places");
		}
		super.connectTo(other, weight, label);
	}
	
	

}
