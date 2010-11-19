package engine.impl;

import edu.uci.ics.jung.graph.DirectedGraph;
import engine.Engine;
import engine.GraphEditor;
import engine.LayoutEditor;
import engine.Simulation;
import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPetrinet;
import transformation.IRule;
import transformation.Transformations;

/**
 * Created by IntelliJ IDEA.
 * User: moritz
 * Date: 12.11.2010
 * Time: 16:31:11
 * To change this template use File | Settings | File Templates.
 */
class EngineImpl implements Engine {

    private final EngineContext context;

    private final GraphEditorImpl graphEditor;

    private final SimulationImpl simulation;

    private final LayoutEditorImpl layoutEditor;

    public EngineImpl(EngineContext context) {
        this.context = context;
        this.graphEditor = new GraphEditorImpl(context);
        this.simulation = new SimulationImpl(context);
        this.layoutEditor = new LayoutEditorImpl(context);
    }

    @Override
    public IPetrinet getNet() {
        return context.getPetrinet();
    }

    @Override
    public DirectedGraph<INode, IArc> getGraph() {
        return context.getGraph();
    }

    @Override
    public GraphEditor getGraphEditor() {
        return graphEditor;
    }

    @Override
    public LayoutEditor getLayoutEditor() {
        return layoutEditor;
    }

    @Override
    public Simulation getSimulation() {
        return simulation;
    }

    @Override
    public void transform(IRule rule) {
        Transformations.transform(context.getPetrinet(), rule);
    }
}
