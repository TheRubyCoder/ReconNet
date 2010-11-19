package engine;

import petrinetze.INode;
import petrinetze.IPetrinet;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: moritz
 * Date: 12.11.2010
 * Time: 17:20:41
 * To change this template use File | Settings | File Templates.
 */
public class SimulationEvent {

    private final Simulation simulation;

    private final IPetrinet petrinet;

    private final Set<INode> changedNodes;

    public SimulationEvent(Simulation simulation, IPetrinet petrinet, Collection<? extends INode> changedNodes) {
        this.simulation = simulation;
        this.petrinet = petrinet;
        this.changedNodes = Collections.unmodifiableSet(new HashSet<INode>(changedNodes));
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public IPetrinet getPetrinet() {
        return petrinet;
    }

    public Set<INode> getChangedNodes() {
        return changedNodes;
    }
}
