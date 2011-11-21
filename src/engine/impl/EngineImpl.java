package engine.impl;

import edu.uci.ics.jung.graph.DirectedGraph;
import engine.*;
import exceptions.GeneralPetrinetException;
import petrinetze.Arc;
import petrinetze.INode;
import petrinetze.Petrinet;
import transformation.IRule;
import transformation.Transformations;

class EngineImpl implements Engine {

    private final EngineContext context;

    private final GraphEditorImpl graphEditor;

    private final SimulationImpl simulation;

    private final LayoutEditorImpl layoutEditor;

    public EngineImpl(EngineContext context) {
        this.context = context;
        this.graphEditor = new GraphEditorImpl(context, this);
        this.simulation = new SimulationImpl(context);
        this.layoutEditor = new LayoutEditorImpl(context);
    }

    public EngineImpl(Petrinet petrinet) {
        this(new EngineContext(petrinet));
    }

    @Override
    public Petrinet getNet() {
        return context.getPetrinet();
    }

    @Override
    public DirectedGraph<INode, Arc> getGraph() {
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
    public UIEditor getUIEditor() {
        return context.getUIEditor();
    }

    @Override
    public void transform(IRule rule) throws GeneralPetrinetException {
        Transformations.transform(context.getPetrinet(), rule);
    }
}
