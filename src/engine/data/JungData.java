package engine.data;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.Transition;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.DirectedGraph;
import engine.Positioning;
import engine.attribute.NodeLayoutAttribute;
import engine.ihandler.IPetrinetManipulation;
import gui.Style;

/**
 * This class adds JUNG relevant information to a petrinet. It holds information
 * about the <b>position of the nodes<b>:
 * <ul>
 * <li>{@link JungData#getJungLayout()}</li>
 * <li>{@link JungData#getNodeLayoutAttributes()}</li>
 * </ul>
 * the <b>size of nodes</b>
 * <ul>
 * <li>{@link JungData#getNodeSize()}</li>
 * <li> {@link JungData#setNodeSize(double)}</li>
 * </ul>
 * and <b>the color of places</b>:
 * <ul>
 * <li>{@link JungData#getPlaceColor(Place)}</li>
 * <li> {@link JungData#setPlaceColor(Place, Color)}</li>
 * </ul>
 * It also manages which nodes are known to JUNG, by <b>creating and deleting
 * graph elements</b>:
 * <ul>
 * <li>{@link JungData#createArc(Arc, Place, Transition)}</li>
 * <li>{@link JungData#createArc(Arc, Transition, Place)}</li>
 * <li>{@link JungData#createPlace(Place, Point2D)}</li>
 * <li>{@link JungData#createTransition(Transition, Point2D)}</li>
 * <li>{@link JungData#deleteDataOfMissingElements(Petrinet)}</li>
 * </ul>
 */

final public class JungData {
	/**
	 * radius to check the minimum distance between 2 nodes
	 */
	public static final Color DEFAULT_COLOR_PLACE = Color.GRAY;
	private static final Color DEFAULT_COLOR_TRANSITION = Color.WHITE;

	private DirectedGraph<INode, IArc> graph;
	private AbstractLayout<INode, IArc> layout;
	private Map<Place, Color> placeColors;
	private double nodeSize = Style.NODE_SIZE_DEFAULT;

	public JungData(DirectedGraph<INode, IArc> graph,
			AbstractLayout<INode, IArc> layout) {
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

	public double getNodeSize() {
		return nodeSize;
	}

	public void setNodeSize(double nodeSize) {
		this.nodeSize = nodeSize;
	}

	private double getMinDinstance() {
		return nodeSize / 1.5d;
	}

	/**
	 * Gets the JungGraph representation
	 * 
	 * @return DirectedGraph<INode, IArc>
	 */
	public DirectedGraph<INode, IArc> getJungGraph() {
		return graph;
	}

	/**
	 * Gets the JungLayout information
	 * 
	 * @return AbstractLayout<INode, IArc>
	 */
	public AbstractLayout<INode, IArc> getJungLayout() {
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
	public void createArc(IArc arc, Place fromPlace, Transition toTransition) {
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
	public void createArc(IArc arc, Transition fromTransition, Place toPlace) {
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
	 * Creates a place at a position that is chosen automatically
	 * 
	 * @param place
	 */
	public void createPlace(Place place) {
		createPlace(place, findPositionForNewNode());
	}

	/**
	 * Creates a transition at a position that is chosen automatically
	 * 
	 * @param place
	 */
	public void createTransition(Transition transition) {
		createTransition(transition, findPositionForNewNode());
	}

	/**
	 * Returns a position where a place or transition can be added
	 * 
	 * @return
	 */
	private Point2D findPositionForNewNode() {
		INode farestRight = findNodeWithBiggestX();
		return new Point2D.Double(layout.getX(farestRight) + getNodeDistance()
				* 2, layout.getY(farestRight));
	}

	/**
	 * Finds the {@link INode} that is the farest to the right
	 * 
	 * @return
	 */
	private INode findNodeWithBiggestX() {
		double x = Double.MIN_VALUE;
		INode farestRight = null;
		for (INode node : graph.getVertices()) {
			if (x < layout.getX(node)) {
				x = layout.getX(node);
				farestRight = node;
			}
		}
		return farestRight;
	}

	/** Returns the distance that needs to be among {@link INode nodes} */
	private double getNodeDistance() {
		return nodeSize / 1.5d;
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
	public void delete(Collection<IArc> arcs, Collection<INode> nodes) {
		checkArcsInvariant(arcs);
		checkINodesInvariant(nodes);

		checkContainsArcs(arcs);
		checkContainsNodes(nodes);

		// are all incident arcs given?
		Set<IArc> arcsHaveToBeDeleted = new HashSet<IArc>();

		for (INode node : nodes) {
			arcsHaveToBeDeleted.addAll(graph.getIncidentEdges(node));
		}

		arcsHaveToBeDeleted.removeAll(arcs);

		check(arcsHaveToBeDeleted.size() == 0, "not all incident arcs given");

		// delete arcs and nodes
		for (IArc arc : arcs) {
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
	 * Moves the graph.
	 * 
	 * @see {@link IPetrinetManipulation#moveGraph(int, Point2D)}
	 * @param relativPosition
	 */
	public void moveGraph(Point2D relativPosition) {
		Set<Entry<INode, NodeLayoutAttribute>> entrySet = getNodeLayoutAttributes()
				.entrySet();
		for (Entry<INode, NodeLayoutAttribute> entry : entrySet) {
			INode node = entry.getKey();
			Point2D currentPosition = entry.getValue().getCoordinate();
			moveNodeWithoutPositionCheck(node,
					Positioning.addPoints(currentPosition, relativPosition));
		}
	}

	/**
	 * @see {@link IPetrinetManipulation#moveGraphIntoVision(int)}
	 */
	public void moveGraphIntoVision() {
		moveGraph(getVectorToMoveIntoVision());
	}

	/**
	 * Finds the vector for that graph to move it into vision
	 * 
	 * @return
	 */
	public Point2D.Double getVectorToMoveIntoVision() {
		Point2D.Double vector = new Point2D.Double(0, 0);
		if (getNodeLayoutAttributes().isEmpty()) {
			// [0,0] is fine
		} else {
			// find smallest x and y
			double smallestX = Double.MAX_VALUE;
			double smallestY = Double.MAX_VALUE;
			for (NodeLayoutAttribute nodeLayoutAttribute : getNodeLayoutAttributes()
					.values()) {
				double currentX = nodeLayoutAttribute.getCoordinate().getX();
				smallestX = smallestX < currentX ? smallestX : currentX;
				double currentY = nodeLayoutAttribute.getCoordinate().getY();
				smallestY = smallestY < currentY ? smallestY : currentY;
			}
			// create vector
			vector = new Point2D.Double(-smallestX + nodeSize / 2, -smallestY
					+ nodeSize / 2);
		}
		return vector;
	}

	/**
	 * Moves a INode to position while checking for collisions
	 * 
	 * @param node
	 *            Node to move
	 * @param coordinate
	 *            new Position of the INode
	 */
	public void moveNodeWithPositionCheck(INode node, Point2D coordinate) {
		moveNode(node, coordinate, true);
	}

	/**
	 * Moves a INode to position without checking for collisions
	 * 
	 * @param node
	 *            Node to move
	 * @param coordinate
	 *            new Position of the INode
	 */
	public void moveNodeWithoutPositionCheck(INode node, Point2D coordinate) {
		moveNode(node, coordinate, false);
	}

	/**
	 * Moves a INode to position. Can check for collisions
	 * 
	 * @param node
	 *            Node to move
	 * @param coordinate
	 *            new Position of the INode
	 */
	private void moveNode(INode node, Point2D coordinate,
			boolean checkForCollisions) {
		checkINodeInvariant(node);
		if (checkForCollisions) {
			checkPoint2DInvariant(coordinate);
		} else {
			// nothing
		}
		check(graph.containsVertex(node), "unknown node");

		Set<INode> excludes = new HashSet<INode>();
		excludes.add(node);

		if (checkForCollisions) {
			checkPoint2DLocation(coordinate, excludes);
		} else {
			// nothing
		}

		layout.setLocation(node, coordinate);
	}

	/**
	 * Removes data of elements that are no longer in the petrinet. This may be
	 * used if the petrinet is altered from outside the engine.
	 * 
	 * @param petrinet
	 */
	public void deleteDataOfMissingElements(Petrinet petrinet) {
		List<INode> missingNodes = new LinkedList<INode>();
		List<IArc>  missingEdges = new LinkedList<IArc>();

		Set<Place>      places      = petrinet.getPlaces();
		Set<Transition> transitions = petrinet.getTransitions();		
		Set<IArc> 	    arcs        = petrinet.getArcs();
		
		for (INode node : graph.getVertices()) {
			if (node instanceof Place && !places.contains((Place) node)) {
				missingNodes.add(node);
				
			} else if (node instanceof Transition && !transitions.contains((Transition) node)) {
				missingNodes.add(node);
			}
		}
		
		for (IArc arc : graph.getEdges()) {
			if (!arcs.contains(arc)) {
				missingEdges.add(arc);
			}
		}
		
		for (INode missingNode : missingNodes) {
			graph.removeVertex(missingNode);
		}
		
		for (IArc arc : missingEdges) {
			graph.removeEdge(arc);
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
	private void checkArcInvariant(IArc arc) {
		check(arc instanceof IArc, "arc illegal type");
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
	private void checkArcInvariant(IArc arc, INode from, INode to) {
		checkArcInvariant(arc);
		check(arc.getSource().equals(from) && arc.getTarget().equals(to),
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
		// check(point.getX() >= 0 && point.getY() >= 0,
		// "point x or y is negative");
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
	private void checkArcsInvariant(Collection<IArc> arcs) {
		check(arcs instanceof Collection<?>, "arcs illegal type");

		for (IArc arc : arcs) {
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
	private void checkContainsArc(IArc arc) {
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
	private void checkContainsArcs(Collection<IArc> arcs) {
		for (IArc arc : arcs) {
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
		for (INode node : graph.getVertices()) {
			if (excludes.contains(node)) {
				continue;
			}

			double xDistance = Math.abs(layout.getX(node) - point.getX());
			double yDistance = Math.abs(layout.getY(node) - point.getY());

			check(Double.compare(xDistance, getMinDinstance()) >= 0
					|| Double.compare(yDistance, getMinDinstance()) >= 0,
					"point is too close to a node");
		}
	}

	/**
	 * @see {@link IPetrinetManipulation#moveAllNodesTo(int, float, Point)}
	 * @param factor
	 * @param point
	 */
	public void moveAllNodesTo(float factor, Point point) {
		double targetX = point.getX();
		double targetY = point.getY();

		for (INode node : graph.getVertices()) {
			double nodeX = getNodeLayoutAttributes().get(node).getCoordinate()
					.getX();
			double nodeY = getNodeLayoutAttributes().get(node).getCoordinate()
					.getY();

			double newX = nodeX - ((targetX - nodeX) * (factor - 1));
			double newY = nodeY - ((targetY - nodeY) * (factor - 1));
			moveNodeWithoutPositionCheck(node, new Point2D.Double(newX, newY));
		}
	}
}
