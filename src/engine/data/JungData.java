package engine.data;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Petrinet;
import petrinet.Place;
import petrinet.Transition;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.DirectedGraph;
import engine.attribute.NodeLayoutAttribute;

/**
 * @author Mathias Blumreiter
 */

final public class JungData {
	/**
	 * radius to check the minimum distance between 2 nodes
	 */
	public static final int NODE_RADIUS = 10;
	public static final Color DEFAULT_COLOR_PLACE = Color.GRAY;
	public static final Color DEFAULT_COLOR_TRANSITION = Color.WHITE;

	private DirectedGraph<INode, Arc> graph;
	private AbstractLayout<INode, Arc> layout;
	private Map<Place, Color> placeColors;

	public JungData(DirectedGraph<INode, Arc> graph,
			AbstractLayout<INode, Arc> layout) {
		if (!(graph instanceof DirectedGraph<?, ?>)) {
			throw new IllegalArgumentException("graph illegal type");
		}

		if (!(layout instanceof AbstractLayout<?, ?>)) {
			throw new IllegalArgumentException("layout illegal type");
		}

		this.graph = graph;
		this.layout = layout;
		this.placeColors = new HashMap<Place, Color>();
	}

	/**
	 * Gets the JungGraph representation
	 * 
	 * @return DirectedGraph<INode,Arc>
	 */
	public DirectedGraph<INode, Arc> getJungGraph() {
		return graph;
	}

	/**
	 * Gets the JungLayout information
	 * 
	 * @return AbstractLayout<INode, Arc>
	 */
	public AbstractLayout<INode, Arc> getJungLayout() {
		return layout;
	}

	/**
	 * Gets the LayoutAttributes of all Nodes.
	 * 
	 * @return Map<INode, NodeLayoutAttribute> map with INode as Key and his
	 *         LayoutAttribute as Value
	 */
	public Map<INode, NodeLayoutAttribute> getNodeLayoutAttributes() {
		Map<INode, NodeLayoutAttribute> attributes = new HashMap<INode, NodeLayoutAttribute>();

		for (INode node : getJungGraph().getVertices()) {
			Color color = DEFAULT_COLOR_TRANSITION;

			if (node instanceof Place) {
				color = getPlaceColor((Place) node);
			}

			attributes.put(node, new NodeLayoutAttribute(new Point2D.Double(
					getJungLayout().getX(node), getJungLayout().getY(node)),
					color));
		}

		return attributes;
	}

	/**
	 * Creates an Arc in the JungRepresentation of the petrinet from a Place to
	 * a Transition.
	 * 
	 * @param arc
	 *            arc to add
	 * @param fromPlace
	 *            Source of the arc
	 * @param toTransition
	 *            Target of the arc
	 */
	public void createArc(Arc arc, Place fromPlace, Transition toTransition) {
		checkPlaceInvariant(fromPlace);
		checkTransitionInvariant(toTransition);
		checkArcInvariant(arc, fromPlace, toTransition);

		checkContainsNode(fromPlace);
		checkContainsNode(toTransition);

		check(getJungGraph().addEdge(arc, fromPlace, toTransition),
				"arc couldn't be added");
	}

	/**
	 * Creates an Arc in the JungRepresentation of the petrinet from a
	 * Transition to a Place.
	 * 
	 * @param arc
	 *            arc to add
	 * @param fromTransition
	 *            Source of the arc
	 * @param toPlace
	 *            Target of the arc
	 */
	public void createArc(Arc arc, Transition fromTransition, Place toPlace) {
		checkPlaceInvariant(toPlace);
		checkTransitionInvariant(fromTransition);
		checkArcInvariant(arc, fromTransition, toPlace);

		checkContainsNode(fromTransition);
		checkContainsNode(toPlace);

		check(getJungGraph().addEdge(arc, fromTransition, toPlace),
				"arc couldn't be added");
	}

	/**
	 * Creates a Place in the JungRepresentation of the petrinet.
	 * 
	 * @param place
	 *            Place to create
	 * @param coordinate
	 *            Position of the Place
	 */
	public void createPlace(Place place, Point2D coordinate) {
		checkPlaceInvariant(place);
		checkPoint2DInvariant(coordinate);
		checkPoint2DLocation(coordinate, new HashSet<INode>());

		check(getJungGraph().addVertex(place), "place couldn't be added");

		getJungLayout().setLocation(place, coordinate);
	}

	/**
	 * Creates a Transition in the JungRepresentation of the petrinet.
	 * 
	 * @param transition
	 *            Transition to create
	 * @param coordinate
	 *            Position of the Transition
	 */
	public void createTransition(Transition transition, Point2D coordinate) {
		checkTransitionInvariant(transition);
		checkPoint2DInvariant(coordinate);
		checkPoint2DLocation(coordinate, new HashSet<INode>());

		check(getJungGraph().addVertex(transition),
				"transition couldn't be added");

		layout.setLocation(transition, coordinate);
	}

	/**
	 * Deletes Arcs and INodes from the JungRepresentation.
	 * 
	 * @param arcs
	 *            Collection of Arcs to be deleted
	 * @param nodes
	 *            Collection of INodes to be deleted
	 */
	public void delete(Collection<Arc> arcs, Collection<INode> nodes) {
		checkArcsInvariant(arcs);
		checkINodesInvariant(nodes);

		checkContainsArcs(arcs);
		checkContainsNodes(nodes);

		// are all incident arcs given?
		Set<Arc> arcsHaveToBeDeleted = new HashSet<Arc>();

		for (INode node : nodes) {
			arcsHaveToBeDeleted.addAll(graph.getIncidentEdges(node));
		}

		arcsHaveToBeDeleted.removeAll(arcs);

		check(arcsHaveToBeDeleted.size() == 0, "not all incident arcs given");

		// delete arcs and nodes
		for (Arc arc : arcs) {
			check(graph.removeEdge(arc), "arc couldn't be removed");
		}

		for (INode node : nodes) {
			check(graph.removeVertex(node), "node couldn't be removed");

			placeColors.remove(node);
		}
	}

	/**
	 * @param node
	 *            place
	 * @return Color is DEFAULT_COLOR_PLACE, if no specific color was set
	 */
	public Color getPlaceColor(Place place) {
		checkPlaceInvariant(place);
		checkContainsNode(place);

		Color color = placeColors.get(place);

		return null == color ? DEFAULT_COLOR_PLACE : color;
	}

	/**
	 * @param coordinate
	 *            where a place or a transition will be created
	 * @return
	 */
	public boolean isCreatePossibleAt(Point2D coordinate) {
		try {
			checkPoint2DInvariant(coordinate);
			checkPoint2DLocation(coordinate, new HashSet<INode>());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @param node
	 *            place
	 * @param color
	 *            new Color
	 */
	public void setPlaceColor(Place place, Color color) {
		checkPlaceInvariant(place);
		checkContainsNode(place);
		check(color instanceof Color, "color illegal type");

		placeColors.put(place, color);
	}

	/**
	 * Moves a INode.
	 * 
	 * @param node
	 *            Node to move
	 * @param coordinate
	 *            new Position of the INode
	 */
	public void moveNode(INode node, Point2D coordinate) {
		checkINodeInvariant(node);
		checkPoint2DInvariant(coordinate);
		check(graph.containsVertex(node), "unknown node");

		Set<INode> excludes = new HashSet<INode>();
		excludes.add(node);

		checkPoint2DLocation(coordinate, excludes);

		layout.setLocation(node, coordinate);
	}

	/**
	 * Removes data of elements that are no longer in the petrinet. This may be
	 * used if the petrinet is altered from outside the engine.
	 * @param petrinet
	 */
	public void deleteDataOfMissingElements(Petrinet petrinet) {
		List<INode> missing = new LinkedList<INode>();
		for (INode node : graph.getVertices()) {
			if(!petrinet.getAllGraphElement().getAllNodes().contains(node)) {
				missing.add(node);
			}
		}
		for (INode missingNode : missing) {
			graph.removeVertex(missingNode);
		}
	}

	// ///////////////////////
	// helper methods
	// ///////////////////////

	/**
	 * throws an exception, if check result is negative.
	 * 
	 * @param isValid
	 *            if false, exception is thrown
	 * @param message
	 *            message of exception
	 */
	private void check(boolean isValid, String message) {
		if (!isValid) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * checks if an arc is valid
	 * 
	 * @param arc
	 *            arc to check
	 */
	private void checkArcInvariant(Arc arc) {
		check(arc instanceof Arc, "arc illegal type");
	}

	/**
	 * checks if an arc is valid and if it connects "from" an "to"
	 * 
	 * @param arc
	 *            arc to check
	 * @param from
	 *            node where arc starts
	 * @param to
	 *            node where arc ends
	 */
	private void checkArcInvariant(Arc arc, INode from, INode to) {
		checkArcInvariant(arc);
		check(arc.getStart().equals(from) && arc.getEnd().equals(to),
				"arc illegal nodes");
	}

	/**
	 * checks if a node is valid
	 * 
	 * @param node
	 *            node to check
	 */
	private void checkINodeInvariant(INode node) {
		check(node instanceof INode, "node illegal type");
	}

	/**
	 * checks if place is valid
	 * 
	 * @param place
	 *            place to check
	 */
	private void checkPlaceInvariant(Place place) {
		check(place instanceof Place, "place illegal type");
	}

	/**
	 * checks if point is valid (not negative)
	 * 
	 * @param point
	 *            point to check
	 */
	private void checkPoint2DInvariant(Point2D point) {
		check(point instanceof Point2D, "point illegal type");
		check(point.getX() >= 0 && point.getY() >= 0,
				"point x or y is negative");
	}

	/**
	 * checks if transition is valid
	 * 
	 * @param transition
	 *            transition to check
	 */
	private void checkTransitionInvariant(Transition transition) {
		check(transition instanceof Transition, "transition illegal type");
	}

	/**
	 * checks a collection of arcs
	 * 
	 * @param arcs
	 *            collection to check
	 */
	private void checkArcsInvariant(Collection<Arc> arcs) {
		check(arcs instanceof Collection<?>, "arcs illegal type");

		for (Arc arc : arcs) {
			checkArcInvariant(arc);
		}
	}

	/**
	 * checks a collection of nodes
	 * 
	 * @param nodes
	 *            collection to check
	 */
	private void checkINodesInvariant(Collection<INode> nodes) {
		check(nodes instanceof Collection<?>, "nodes illegal type");

		for (INode node : nodes) {
			checkINodeInvariant(node);
		}
	}

	/**
	 * checks if jung graph contains arc
	 * 
	 * @param arc
	 *            arc to check
	 * 
	 * @throws NullPointerException
	 *             if jung or arc == null
	 */
	private void checkContainsArc(Arc arc) {
		check(graph.containsEdge(arc), "unknown node");
	}

	/**
	 * checks if jung graph contains all arcs
	 * 
	 * @param arcs
	 *            collection of arc to check
	 * 
	 * @throws NullPointerException
	 *             if jung or arcs == null
	 */
	private void checkContainsArcs(Collection<Arc> arcs) {
		for (Arc arc : arcs) {
			checkContainsArc(arc);
		}
	}

	/**
	 * checks if jung graph contains node
	 * 
	 * @param node
	 *            node to check
	 * 
	 * @throws NullPointerException
	 *             if jung or node == null
	 */
	private void checkContainsNode(INode node) {
		check(graph.containsVertex(node), "unknown node");
	}

	/**
	 * checks if jung graph contains all nodes
	 * 
	 * @param nodes
	 *            collection of nodes to check
	 * 
	 * @throws NullPointerException
	 *             if jung or nodes == null
	 */
	private void checkContainsNodes(Collection<INode> nodes) {
		for (INode node : nodes) {
			checkContainsNode(node);
		}
	}

	/**
	 * checks if a new node were too close to an other node. Checking by
	 * calculating the boundig box of an node.
	 * 
	 * @param point
	 *            point to check
	 * @param excludes
	 *            dont't check to these nodes
	 */
	private void checkPoint2DLocation(Point2D point, Collection<INode> excludes) {
		final double MIN_DISTANCE = 2 * NODE_RADIUS;

		for (INode node : graph.getVertices()) {
			if (excludes.contains(node)) {
				continue;
			}

			double xDistance = Math.abs(layout.getX(node) - point.getX());
			double yDistance = Math.abs(layout.getY(node) - point.getY());

			check(Double.compare(xDistance, MIN_DISTANCE) >= 0
					|| Double.compare(yDistance, MIN_DISTANCE) >= 0,
					"point is too close to a node");
		}
	}
}
