package engine.net.impl;

import engine.net.NetNode;
import engine.net.Place;

class PlaceImpl extends NetNodeImpl implements Place {

	PlaceImpl(String label) {
		super(label);
	}
	
	@Override
	public void connectTo(NetNode other, int weight, String label) {
		if(!(other instanceof Place)) {
			throw new IllegalArgumentException("Places can only be connected to Transitions");
		}		
		super.connectTo(other, weight, label);
	}


}
