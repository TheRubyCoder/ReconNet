package persistence;

import petrinet.INode;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;

public interface IPersistance {
	public void save(String pathAndFilename, petrinet.Petrinet petrinet, AbstractLayout<INode, Arc> layout);
	
	public void load(String pathAndFilename, engine.ihandler.IPetrinetHandler handler);
}
