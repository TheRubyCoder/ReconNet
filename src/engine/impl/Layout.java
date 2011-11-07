package engine.impl;

import petrinetze.IArc;
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
        public AbstractLayout<INode, IArc> getInstance(Graph<INode, IArc> graph) {
            return new FRLayout<INode, IArc>(graph);
        }
    },

	KKLayout {
        @Override
        public AbstractLayout<INode, IArc> getInstance(Graph<INode, IArc> graph) {
            return new KKLayout<INode, IArc>(graph);
        }
    };

	public abstract AbstractLayout<INode, IArc> getInstance(Graph<INode, IArc> graph);
}
