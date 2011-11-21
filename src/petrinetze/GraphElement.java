package petrinetze;

import java.util.Set;


public class GraphElement implements IGraphElement {
	
	private Set<INode>nodes;
	private Set<Arc>arcs;
	
	@Override
	public Set<INode> getAllNodes() {
		return nodes;
	}

	@Override
	public Set<Arc> getAllArcs() {
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
	public void setArcs(Set<Arc> arcs) {
		this.arcs = arcs;
	}
	

}
