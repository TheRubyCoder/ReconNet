package engine.net.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import engine.net.NetEdge;
import engine.net.NetNode;

class NetNodeImpl extends NetPartImpl implements NetNode {

	private final Map<Integer,NetEdge> edges;

	protected NetNodeImpl(String label) {
		super(label);
		edges = new HashMap<Integer,NetEdge>();
	}

	@Override
	public Collection<NetEdge> getEdges() {		
		return new ArrayList<NetEdge>(edges.values());
	}

	@Override
	public void connectTo(NetNode other, int weight, String label) {
		for(NetEdge edge : edges.values()) {
			if(other == edge) {
				throw new IllegalArgumentException(String.format("%s is allready connected to %s with %s", this, other, edge));
			}
		}	
		
		NetEdge edge = new NetEdgeImpl(this, other, weight, label);	
		edges.put(edge.getId(), edge);
	}


	@Override
	public void removeEdge(NetEdge edge) {
		edges.remove(edge.getId());		
	}


	

}
