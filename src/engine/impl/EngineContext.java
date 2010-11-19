package engine.impl;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPetrinet;

/**
 * Created by IntelliJ IDEA.
 * User: moritz
 * Date: 12.11.2010
 * Time: 16:09:26
 * To change this template use File | Settings | File Templates.
 */
class EngineContext {

    private IPetrinet petrinet;

    private DirectedGraph<INode,IArc> graph = new DirectedSparseGraph<INode,IArc>();

    private AbstractLayout<INode, IArc> layout = new KKLayout<INode,IArc>(graph);

    public EngineContext(IPetrinet petrinet) {
        this.petrinet = petrinet;

        // TODO hier Graph aufbauen!
        // Dafür muss allerdings das Petrinetz zuerst die Elemente bereitstellen...
    }

    public IPetrinet getPetrinet() {
        return petrinet;
    }

    public DirectedGraph<INode, IArc> getGraph() {
        return graph;
    }

    public AbstractLayout<INode, IArc> getLayout() {
        return layout;
    }
}
