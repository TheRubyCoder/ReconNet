package engine.data;

import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.Transition;
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
		DirectedGraph<INode, IArc> graph = jungData.getJungGraph();

		check(graph.getEdgeCount() == petrinet.getArcs().size(),
				"to many arcs");
		check(graph.getVertexCount() == (petrinet.getTransitions().size() + petrinet
				.getPlaces().size()), "to many nodes");

		for (IArc arc : petrinet.getArcs()) {
			check(graph.containsEdge(arc), "arc not in graph");
		}

		for (Place place : petrinet.getPlaces()) {
			check(graph.containsVertex(place), "place not in graph");
		}

		for (Transition transition : petrinet.getTransitions()) {
			check(graph.containsVertex(transition), "transition not in graph");
		}
	}
}
