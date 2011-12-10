package engine.jungmodification;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Place;
import petrinet.Transition;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.DirectedGraph;
import engine.data.JungData;

/**
 * @author Mathias Blumreiter
 */
final public class JungModification {
	private static JungModification jungModification;
	
	private JungModification() {}
	
	/**
	 * gets a JungModification instance (Singleton)
	 * 
	 * @return JungModification
	 */
	public static JungModification getInstance() {
		if(jungModification == null){
			jungModification = new JungModification();
		}
		
		return jungModification;
	}
	
	/**
	 * Creates an Arc in the JungRepresentation of the petrinet from a Place to a Transition.
	 * 
	 * @param jung 			JungRepresentation where arc will be created
	 * @param arc			arc to add
	 * @param fromPlace 	Source of the arc
	 * @param toTransition  Target of the arc
	 */
	public void createArc(JungData jung, Arc arc, Place fromPlace, Transition toTransition)  {
		checkJungDataInvariant(jung);
		checkPlaceInvariant(fromPlace);
		checkTransitionInvariant(toTransition);
		checkArcInvariant(arc, fromPlace, toTransition);
		
		checkContainsNode(jung, fromPlace);
		checkContainsNode(jung, toTransition);
		
		check(
			jung.getJungGraph().addEdge(arc, fromPlace, toTransition),
			"arc couldn't be added"
		);
	}
	
	/**
	 * Creates an Arc in the JungRepresentation of the petrinet from a Transition to a Place.
	 * 
	 * @param jung			 	JungRepresentation where arc will be created
	 * @param arc			 	arc to add
	 * @param fromTransition 	Source of the arc
	 * @param toPlace Target 	of the arc
	 */
	public void createArc(JungData jung, Arc arc, Transition fromTransition, Place toPlace) {
		checkJungDataInvariant(jung);
		checkPlaceInvariant(toPlace);
		checkTransitionInvariant(fromTransition);
		checkArcInvariant(arc, fromTransition, toPlace);
		
		checkContainsNode(jung, fromTransition);
		checkContainsNode(jung, toPlace);

		check(
			jung.getJungGraph().addEdge(arc, fromTransition, toPlace),
			"arc couldn't be added"
		);
	}
	
	/**
	 * Creates a Place in the JungRepresentation of the petrinet.
	 * 
	 * @param jung 			JungRepresentation where place will be created
	 * @param place 		Place to create
	 * @param coordinate 	Position of the Place
	 */
	public void createPlace(JungData jung, Place place, Point2D coordinate) {
		checkJungDataInvariant(jung);
		checkPlaceInvariant(place);
		checkPoint2DInvariant(coordinate);

		check(
			jung.getJungGraph().addVertex(place),
			"place couldn't be added"
		);
	
		jung.getJungLayout().setLocation(place, coordinate);		
	}
	
	/**
	 * Creates a Transition in the JungRepresentation of the petrinet.
	 * 
	 * @param jung 			JungRepresentation where place will be created
	 * @param transition 	Transition to create
	 * @param coordinate	Position of the Transition
	 */
	public void createTransition(JungData jung, Transition transition, Point2D coordinate) {
		checkJungDataInvariant(jung);
		checkTransitionInvariant(transition);
		checkPoint2DInvariant(coordinate);

		check(
			jung.getJungGraph().addVertex(transition),
			"transition couldn't be added"
		);
	
		jung.getJungLayout().setLocation(transition, coordinate);
	}
	
	/**
	 * Deletes Arcs and INodes from the JungRepresentation.
	 * 
	 * @param jung 		JungRepresentation where Arcs/INodes will be deleted
	 * @param arcs 		Collection of Arcs to be deleted
	 * @param nodes 	Collection of INodes to be deleted
	 */
	public void delete(JungData jung, Collection<Arc> arcs, Collection<INode> nodes) {
		checkJungDataInvariant(jung);
		checkArcsInvariant(arcs);
		checkINodesInvariant(nodes);
		
		checkContainsArcs(jung, arcs);
		checkContainsNodes(jung, nodes);

		DirectedGraph<INode, Arc> graph = jung.getJungGraph();
		
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
		}
	}
	
	/**
	 * Moves a INode.
	 * 
	 * @param jung 			JungRepresentation where INode will be moved
	 * @param node 			Node to move
	 * @param coordinate 	new Position of the INode
	 */
	public void moveNode(JungData jung, INode node, Point2D coordinate) {
		checkJungDataInvariant(jung);
		checkINodeInvariant(node);
		checkPoint2DInvariant(coordinate);		
		check(jung.getJungGraph().containsVertex(node), "unknown node");
	
		jung.getJungLayout().setLocation(node, coordinate);
	}
	
	/**
	 * Updates an Arc.
	 * 
	 * @param jung 		JungRepresentation where Arc will be updated
	 * @param arc 		Arc to update
	 */
	public void updateArc(JungData jung, Arc arc) {
		checkJungDataInvariant(jung);
		checkArcInvariant(arc);

		checkContainsArc(jung, arc);
		// TODO: updateArc
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Updates a Place.
	 * 
	 * @param jung 		JungRepresentation where Place will be updated
	 * @param place 	Place to Update
	 */
	public void updatePlace(JungData jung, Place place) {
		checkJungDataInvariant(jung);
		checkPlaceInvariant(place);

		checkContainsNode(jung, place);
		
		// TODO: updatePlace
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Updates a Transition
	 * 
	 * @param jung 			JungRepresentation where Transition will be updated
	 * @param transition 	Transition to update
	 */
	public void updateTransition(JungData jung, Transition transition) {
		checkJungDataInvariant(jung);
		checkTransitionInvariant(transition);

		checkContainsNode(jung, transition);
		
		// TODO: updateTransition
		throw new UnsupportedOperationException();
	}
	
	
	
	/////////////////////////
	//	helper methods
	/////////////////////////
	
	/**
	 * throws an exception, if check result is negative.
	 * 
	 * @param isValid	if false, exception is thrown	
	 * @param message	message of exception
	 */
	private void check(boolean isValid, String message) {
		if (!isValid) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * checks if an arc is valid
	 * 
	 * @param arc arc to check
	 */
	private void checkArcInvariant(Arc arc) {
		check(arc instanceof Arc, "arc illegal type");
	}
	

	/**
	 * checks if an arc is valid and if it connects "from" an "to"
	 * 
	 * @param arc 	arc to check
	 * @param from	node where arc starts
	 * @param to	node where arc ends
	 */
	private void checkArcInvariant(Arc arc, INode from, INode to) {
		checkArcInvariant(arc);
		check(arc.getStart().equals(from) && arc.getEnd().equals(to), "arc illegal nodes");
	}	

	/**
	 * checks if a node is valid
	 * 
	 * @param node node to check
	 */
	private void checkINodeInvariant(INode node) {
		check(node instanceof INode, "node illegal type");
	}
	
	/**
	 * checks if jung is valid
	 * 
	 * @param node jung representation to check
	 */
	private void checkJungDataInvariant(JungData jung) {
		check(jung instanceof JungData, "jungData illegal type");
		check(jung.getJungGraph() instanceof DirectedGraph<?, ?>, "jungData Graph illegal type");
		check(jung.getJungLayout() instanceof AbstractLayout<?, ?>, "jungData Layout illegal typ");	
	}

	/**
	 * checks if place is valid
	 * 
	 * @param place place to check
	 */
	private void checkPlaceInvariant(Place place) {
		check(place instanceof Place, "place illegal type");
	}

	/**
	 * checks if point is valid (not negative)
	 * 
	 * @param point  point to check
	 */
	private void checkPoint2DInvariant(Point2D point) {
		check(point instanceof Point2D, "point illegal type");
		check(point.getX() >= 0 && point.getY() >= 0, "point x or y is negative");
	}

	/**
	 * checks if transition is valid
	 * 
	 * @param transition  transition to check
	 */
	private void checkTransitionInvariant(Transition transition) {
		check(transition instanceof Transition, "transition illegal type");
	}	
	
	

	/**
	 * checks a collection of arcs
	 * 
	 * @param arcs  collection to check
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
	 * @param nodes  collection to check
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
	 * @param jung 	jung representation
	 * @param arc 	arc to check
	 * 
	 * @throws NullPointerException if jung or arc == null
	 */
	private void checkContainsArc(JungData jung, Arc arc) {
		check(jung.getJungGraph().containsEdge(arc), "unknown node");
	}
	
	/**
	 * checks if jung graph contains all arcs
	 * 
	 * @param jung 	jung representation
	 * @param arcs  collection of arc to check
	 * 
	 * @throws NullPointerException if jung or arcs == null
	 */
	private void checkContainsArcs(JungData jung, Collection<Arc> arcs) {
		for (Arc arc : arcs) {
			checkContainsArc(jung, arc);
		}
	}

	/**
	 * checks if jung graph contains node
	 * 
	 * @param jung 	jung representation
	 * @param node 	node to check
	 * 
	 * @throws NullPointerException if jung or node == null
	 */
	private void checkContainsNode(JungData jung, INode node) {
		check(jung.getJungGraph().containsVertex(node), "unknown node");
	}

	/**
	 * checks if jung graph contains all nodes
	 * 
	 * @param jung 	jung representation
	 * @param nodes collection of nodes to check
	 * 
	 * @throws NullPointerException if jung or nodes == null
	 */
	private void checkContainsNodes(JungData jung, Collection<INode> nodes) {
		for (INode node : nodes) {
			checkContainsNode(jung, node);
		}
	}
}
