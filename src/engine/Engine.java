package engine;

import edu.uci.ics.jung.graph.DirectedGraph;
import exceptions.GeneralPetrinetException;
import petrinet.Arc;
import petrinet.INode;
import petrinet.Petrinet;
import transformation.Rule;
import transformation.Transformations;

public class Engine{

    private final EngineContext context;

    private final GraphEditorImpl graphEditor;

    private final Simulation simulation;

    private final LayoutEditor layoutEditor;

    public Engine(EngineContext context) {
        this.context = context;
        this.graphEditor = new GraphEditorImpl(context, this);
        this.simulation = new Simulation(context);
        this.layoutEditor = new LayoutEditor(context);
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
