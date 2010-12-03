package petrinetze.impl;

import java.util.Set;

import petrinetze.IArc;
import petrinetze.IGraphElement;
import petrinetze.INode;

public class GraphElement implements IGraphElement {
	
	private Set<INode>nodes;
	private Set<IArc>arcs;
	
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
