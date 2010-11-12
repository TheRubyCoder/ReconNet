package engine.net;

import java.util.Collection;

public interface Net {
	
	NetPart get(int id);
	
	NetNode getNode(int id);
	
	NetEdge getEdge(int id);
	
	void addNode(NetNode n);
	
	void removeNode(NetNode n);
	
	Collection<Integer> getIds();
	
}
