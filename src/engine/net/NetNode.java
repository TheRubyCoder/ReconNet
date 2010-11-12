package engine.net;

import java.util.Collection;

public interface NetNode extends NetPart{
	
	Collection<NetEdge> getEdges();
	
	void connectTo(NetNode other, int weight, String label);
	
	void removeEdge(NetEdge edge);
}
