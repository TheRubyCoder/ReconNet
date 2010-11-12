package engine.net;

public interface NetEdge extends NetPart{
	
	NetPart getOrigin();
	
	NetPart getTarget();
	
	int getWeight();
}
