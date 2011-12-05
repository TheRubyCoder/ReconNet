package engine.data;

import petrinet.Arc;
import petrinet.INode;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.DirectedGraph;

public class JungData {

	private DirectedGraph<INode, Arc> jungGraph;
	private AbstractLayout<INode, Arc> jungLayout;
	
	/**
	 * 
	 */
	public JungData clone(){
		return new JungData(); // TODO: !
	}
	
	/**
	 * Gets the JungGraph representation
	 * 
	 * @return DirectedGraph<INode,Arc>
	 */
	public DirectedGraph<INode, Arc> getJungGraph(){
		return jungGraph;
	}
	
	/**
	 * Gets the JungLayout information
	 * 
	 * @return AbstractLayout<INode, Arc>
	 */
	public AbstractLayout<INode, Arc> getJungLayout(){
		return jungLayout;
	}
	
}
