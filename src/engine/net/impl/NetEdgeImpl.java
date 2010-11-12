package engine.net.impl;

import engine.net.NetEdge;
import engine.net.NetNode;
import engine.net.NetPart;

class NetEdgeImpl extends NetPartImpl implements NetEdge {

	private final int weight;
	private final NetNode target;
	private final NetNode origin;

	protected NetEdgeImpl(NetNode origin, NetNode target, int weight, String label) {
		super(label);
		this.origin = origin;
		this.target = target;
		this.weight  = weight;
	}

	@Override
	public NetPart getOrigin() {
		return origin;
	}

	@Override
	public NetPart getTarget() {
		return target;
	}

	@Override
	public int getWeight() {
		return weight;
	}
	
}
