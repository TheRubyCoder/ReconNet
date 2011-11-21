package engine.impl;

import petrinetze.Arc;
import petrinetze.INode;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.graph.Graph;

/**
 * Enumeration for different Layouts.
 * 
 */
public enum Layout {
	FRLayout {
        @Override
        public AbstractLayout<INode, Arc> getInstance(Graph<INode, Arc> graph) {
            return new FRLayout<INode, Arc>(graph);
        }
    },

	KKLayout {
        @Override
        public AbstractLayout<INode, Arc> getInstance(Graph<INode, Arc> graph) {
            return new KKLayout<INode, Arc>(graph);
        }
    };

	public abstract AbstractLayout<INode, Arc> getInstance(Graph<INode, Arc> graph);
}
