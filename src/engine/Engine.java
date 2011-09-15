package engine;

import petrinetze.IArc;
import petrinetze.IPetrinet;
import petrinetze.INode;
import transformation.IRule;
import edu.uci.ics.jung.graph.DirectedGraph;
import exceptions.GeneralPetrinetException;


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
    DirectedGraph<INode, IArc> getGraph();

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

    UIEditor getUIEditor();

    /**
     * Anwenden einer Transformationsregel.
     *
     * @param rule die anzuwendende Regel
     * @throws Exception 
     */
    void transform(IRule rule) throws GeneralPetrinetException;

}
