package engine;

import edu.uci.ics.jung.graph.DirectedGraph;
import engine.*;
import exceptions.GeneralPetrinetException;
import petrinetze.Arc;
import petrinetze.INode;
import petrinetze.Petrinet;
import transformation.Rule;
import transformation.Transformations;

public class Engine{

    private final EngineContext context;

    private final GraphEditorImpl graphEditor;

    private final SimulationImpl simulation;

    private final LayoutEditorImpl layoutEditor;

    public Engine(EngineContext context) {
        this.context = context;
        this.graphEditor = new GraphEditorImpl(context, this);
        this.simulation = new SimulationImpl(context);
        this.layoutEditor = new LayoutEditorImpl(context);
    }

    public Engine(Petrinet petrinet) {
        this(new EngineContext(petrinet));
    }

    /**
     * Das aktive Petrinetz.
     *
     * @return
     */
    public Petrinet getNet() {
        return context.getPetrinet();
    }

    /**
     * Die Darstellung des Petrinetzes als gerichteter Graph.
     *
     * @return gerichteter Graph
     */
    public DirectedGraph<INode, Arc> getGraph() {
        return context.getGraph();
    }

    /**
     * Manipulation des Petrinetzes.
     *
     * @return
     */
    public GraphEditorImpl getGraphEditor() {
        return graphEditor;
    }

    /**
     * Manipulation des Layouts.
     *
     * @return
     */
    public LayoutEditor getLayoutEditor() {
        return layoutEditor;
    }

    /**
     * Simulation des Petrinetzes.
     * 
     * @return
     */
    public Simulation getSimulation() {
        return simulation;
    }

    public UIEditor getUIEditor() {
        return context.getUIEditor();
    }

    /**
     * Anwenden einer Transformationsregel.
     *
     * @param rule die anzuwendende Regel
     * @throws Exception 
     */
    public void transform(Rule rule) throws GeneralPetrinetException {
        Transformations.transform(context.getPetrinet(), rule);
    }
}
