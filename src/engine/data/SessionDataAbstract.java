package engine.data;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Petrinet;
import petrinet.Place;
import petrinet.Transition;
import edu.uci.ics.jung.graph.DirectedGraph;

/**
 * Abstract super class for {@link JungData}, {@link PetrinetData} and
 * {@link RuleData}, offering various helper methods for data checking
 * 
 */
public abstract class SessionDataAbstract implements SessionData {
	protected int id;

	@Override
	public int getId() {
		return id;
	}

	/**
	 * throws an exception, if check result is negative.
	 * 
	 * @param isValid
	 *            if false, exception is thrown
	 * @param message
	 *            message of exception
	 */
	protected void check(boolean isValid, String message) {
		if (!isValid) {
			throw new IllegalArgumentException(message);
		}
	}

	protected void checkContaining(Petrinet petrinet, JungData jungData) {
		DirectedGraph<INode, Arc> graph = jungData.getJungGraph();

		check(graph.getEdgeCount() == petrinet.getAllArcs().size(),
				"to many arcs");
		check(graph.getVertexCount() == (petrinet.getAllTransitions().size() + petrinet
				.getAllPlaces().size()), "to many nodes");

		for (Arc arc : petrinet.getAllArcs()) {
			check(graph.containsEdge(arc), "arc not in graph");
		}

		for (Place place : petrinet.getAllPlaces()) {
			check(graph.containsVertex(place), "place not in graph");
		}

		for (Transition transition : petrinet.getAllTransitions()) {
			check(graph.containsVertex(transition), "transition not in graph");
		}
	}
}
