package engine.data;

import petrinet.Arc;
import petrinet.INode;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.DirectedGraph;

/**
 * 
 * This Class save all JUNG Data. (DirectedGraph<INode, Arc> / AbstractLayout<INode, Arc>)
 * 
 * @author alex
 *
 */

final public class JungData {
	
	private DirectedGraph<INode, Arc> jungGraph;
	private AbstractLayout<INode, Arc> jungLayout;
	
	public JungData(DirectedGraph<INode, Arc> graph, AbstractLayout<INode, Arc> layout) {
		if (!(graph instanceof DirectedGraph<?, ?>)) {
			throw new IllegalArgumentException("graph illegal type");
		}
		
		if (!(layout instanceof AbstractLayout<?, ?>)) {
			throw new IllegalArgumentException("layout illegal type");
		}		
		
		this.jungGraph  = graph;
		this.jungLayout = layout;
	}
	
	/**
	 * Gets the JungGraph representation
	 * 
	 * @return DirectedGraph<INode,Arc>
	 */
	public DirectedGraph<INode, Arc> getJungGraph() {
		return jungGraph;
	}
	
	/**
	 * Gets the JungLayout information
	 * 
	 * @return AbstractLayout<INode, Arc>
	 */
	public AbstractLayout<INode, Arc> getJungLayout() {
		return jungLayout;
	}	
}
