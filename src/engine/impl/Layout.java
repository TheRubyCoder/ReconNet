package engine.impl;

import petrinetze.IArc;
import petrinetze.INode;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.graph.Graph;


public enum Layout {
	FRLayout(FRLayout.class),
	KKLayout(KKLayout.class);

	@SuppressWarnings("rawtypes")
	private final Class<? extends AbstractLayout> layoutClass;

	@SuppressWarnings("rawtypes")
	private Layout(Class<? extends AbstractLayout> layoutClass) {
		this.layoutClass = layoutClass;

	}

	@SuppressWarnings("unchecked")
	public AbstractLayout<INode, IArc> getInstance(Graph<INode, IArc> graph) {
		try {
			return layoutClass.getConstructor(Graph.class).newInstance(graph);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
