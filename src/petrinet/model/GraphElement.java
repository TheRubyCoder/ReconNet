package petrinet.model;

import java.util.Set;

/**
 * A class, that is used to access all nodes and arcs of a petrinet 
 */
public class GraphElement implements IGraphElement {
	
	private Set<INode> nodes;
	private Set<IArc>  arcs;
	
	@Override
	public Set<INode> getAllNodes() {
		return nodes;
	}

	@Override
	public Set<IArc> getAllArcs() {
		return arcs;
	}

	/**
	 * @param nodes the nodes to set
	 */
	public void setNodes(Set<INode> nodes) {
		this.nodes = nodes;
	}

	/**
	 * @param arcs the arcs to set
	 */
	public void setArcs(Set<IArc> arcs) {
		this.arcs = arcs;
	}
	

}
