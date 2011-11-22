package engine;

import petrinet.INode;
import petrinet.Petrinet;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SimulationEvent {

    private final Simulation simulation;

    private final Petrinet petrinet;

    private final Set<INode> changedNodes;

    public SimulationEvent(Simulation simulation, Petrinet petrinet, Collection<? extends INode> changedNodes) {
        this.simulation = simulation;
        this.petrinet = petrinet;
        this.changedNodes = Collections.unmodifiableSet(new HashSet<INode>(changedNodes));
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public Petrinet getPetrinet() {
        return petrinet;
    }

    public Set<INode> getChangedNodes() {
        return changedNodes;
    }
}
