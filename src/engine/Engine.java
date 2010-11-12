package engine;

import petrinetze.Arc;
import petrinetze.IPetrinet;
import petrinetze.Node;
import transformation.IRule;
import edu.uci.ics.jung.graph.DirectedGraph;


/**
 * Die Petrinetz-Engine.
 */
public interface Engine {

    /**
     * Das aktive Petrinetz.
     *
     * @return
     */
    IPetrinet getNet();


    /**
     * Die Darstellung des Petrinetzes als gerichteter Graph.
     *
     * @return gerichteter Graph
     */
    DirectedGraph<Node, Arc> getGraph();

    /**
     * Manipulation des Petrinetzes.
     *
     * @return
     */
    GraphEditor getGraphEditor();

    /**
     * Manipulation des Layouts.
     *
     * @return
     */
    LayoutEditor getLayoutEditor();

    /**
     * Simulation des Petrinetzes.
     * 
     * @return
     */
    Simulation getSimulation();

    /**
     * Anwenden einer Transformationsregel.
     *
     * @param rule die anzuwendende Regel
     */
    void transform(IRule rule);
}
