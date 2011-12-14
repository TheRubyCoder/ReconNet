package engine.jungmodification;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

import org.junit.*;
import static org.junit.Assert.*;

import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import engine.data.JungData;

import petrinet.Arc;
import petrinet.INode;
import petrinet.PetrinetComponent;
import petrinet.Place;
import petrinet.Petrinet;
import petrinet.Renews;
import petrinet.Transition;

/**
 * @author Mathias Blumreiter
 */
public class JungModificationTest {
	private Petrinet p;
	
    private Place place1;
    private Place place2;
    
    private Transition transition1;
    private Transition transition2; 
    
    private Arc arc11;
    private Arc arc12;
    private Arc arc22;
    
    private JungModification jungMod;
    private JungData jungData;

    private Point2D pointPositive1;
    private Point2D pointPositive1TooCloseLeft;
    private Point2D pointPositive1TooCloseTop;
    private Point2D pointPositive1TooCloseBottom;
    private Point2D pointPositive1TooCloseRight;

    private Point2D pointPositive1JustEnoughLeft;
    private Point2D pointPositive1JustEnoughTop;
    private Point2D pointPositive1JustEnoughBottom;
    private Point2D pointPositive1JustEnoughRight;
    
    private Point2D pointPositive2;
    private Point2D pointNegativeX;
    private Point2D pointNegativeY;
    private Point2D pointNegativeXY; 
    int justFarEnoughAway = JungModification.NODE_RADIUS * 2;
    int farEnoughAway 	  = justFarEnoughAway + 1;
    
	/**
	 * Erstellen eines Netzes
	 */    
	@Before
	public void setUp() throws Exception {	
		p = PetrinetComponent.getPetrinet().createPetrinet();
		
        place1 = p.createPlace("A");
        place2 = p.createPlace("B");

        transition1 = p.createTransition("t1", Renews.COUNT);
        transition2 = p.createTransition("t2", Renews.COUNT);
        
        arc11 = p.createArc("y1", place1, transition1);

        arc12 = p.createArc("x1", transition1, place2);
        arc22 = p.createArc("x2", transition2, place2);
        
        place1.setMark(1);

        DirectedSparseGraph<INode, Arc> graph = new DirectedSparseGraph<INode, Arc>();
        		
        jungMod  = JungModification.getInstance();
        jungData = new JungData(graph, new StaticLayout<INode, Arc>(graph));
        
        int x = 100;
        int y = 100;
         
        pointPositive1  = new Point(x, y);
		
		pointPositive1TooCloseLeft   = new Point(x - justFarEnoughAway + 1, y);
		pointPositive1TooCloseRight  = new Point(x + justFarEnoughAway - 1, y);
		pointPositive1TooCloseTop    = new Point(x, y + justFarEnoughAway - 1);
		pointPositive1TooCloseBottom = new Point(x, y - justFarEnoughAway + 1);   
		
		pointPositive1JustEnoughLeft   = new Point(x - justFarEnoughAway, y);
		pointPositive1JustEnoughRight  = new Point(x + justFarEnoughAway, y);
		pointPositive1JustEnoughTop    = new Point(x, y + justFarEnoughAway);
		pointPositive1JustEnoughBottom = new Point(x, y - justFarEnoughAway);   
		
        pointPositive2  = new Point(
        	x + farEnoughAway, 
        	y + farEnoughAway
        );
        
        pointNegativeX  = new Point(-1, 1);
        pointNegativeY  = new Point(1, -1);
        pointNegativeXY = new Point(-1, -1); 
	}
	
	/**
	 * Testen der Singleton Eigenschaft
	 */
	@Test
	public void testGetInstance() {		
		assertNotNull(JungModification.getInstance());
		assertTrue(JungModification.getInstance() == JungModification.getInstance());		
	}

	/**
	 * Mindestentfernung 2er Nodes mindestens 1
	 */
	@Test
	public void testCreateArc_MinNodeDistanceRadiusSize() {		
		assertTrue(JungModification.NODE_RADIUS >= 1);
	}
		
	/**
	 * Arc (Place > Transition) nach hinzufügen auch enthalten
	 */
	@Test
	public void testCreateArc_PlaceToTranstion() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.createTransition(jungData, transition1, pointPositive2);
		
		jungMod.createArc(jungData, arc11, place1, transition1);
		
		assertTrue(jungData.getJungGraph().containsEdge(arc11));
	}

	/**
	 * Arc (Transition > Place) nach hinzufügen auch enthalten
	 */
	@Test
	public void testCreateArc_TranstionToPlace() {
		jungMod.createPlace(jungData, place2, pointPositive1);
		jungMod.createTransition(jungData, transition1, pointPositive2);
		
		jungMod.createArc(jungData, arc12, transition1, place2);
		
		assertTrue(jungData.getJungGraph().containsEdge(arc12));
	}

	/**
	 * Place hinzufügen
	 */
	@Test
	public void testCreatePlace() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		assertTrue(jungData.getJungGraph().containsVertex(place1));
		assertTrue(Double.compare(pointPositive1.getX(), jungData.getJungLayout().getX(place1)) == 0);
		assertTrue(Double.compare(pointPositive1.getY(), jungData.getJungLayout().getY(place1)) == 0);
	}

	/**
	 * Transition hinzufügen
	 */
	@Test
	public void testCreateTransition() {
		jungMod.createTransition(jungData, transition1, pointPositive1);
		assertTrue(jungData.getJungGraph().containsVertex(transition1));
		assertTrue(Double.compare(pointPositive1.getX(), jungData.getJungLayout().getX(transition1)) == 0);
		assertTrue(Double.compare(pointPositive1.getY(), jungData.getJungLayout().getY(transition1)) == 0);
	}
	

	@Test
	public void testDelete_emptyCollections() {		
		JungData jung = buildJung(p);
		
		Set<INode> nodesBefore = new HashSet<INode>(jung.getJungGraph().getVertices());
		Set<Arc> arcsBefore    = new HashSet<Arc>(jung.getJungGraph().getEdges());
		
		jungMod.delete(jung, new HashSet<Arc>(), new HashSet<INode>());

		assertTrue(nodesBefore.equals(new HashSet<INode>(jung.getJungGraph().getVertices())));
		assertTrue(arcsBefore.equals(new HashSet<Arc>(jung.getJungGraph().getEdges())));
	}

	@Test
	public void testDelete() {
		// erstellen eines Petrinetzes
		// - 2 Gruppen von Nodes und Arcs
		// - es wird ein Jung Graph erstellt in dem beide entahlten sind
		// - Gruppe 2 wird aus Jung gelöscht
		// - prüfen ob Gruppe 1 noch vollständig enthalten ist, und ob sonst nichts weiter enthalten ist
		Set<Place> placesToContain = new HashSet<Place>();
		Set<Place> placesToDelete = new HashSet<Place>();
		
		Set<Transition> transitionsToContain = new HashSet<Transition>();		
		Set<Transition> transitionsToDelete  = new HashSet<Transition>();
		
		Set<Arc> arcsToContain = new HashSet<Arc>();		
		Set<Arc> arcsToDelete  = new HashSet<Arc>();

		Petrinet p = PetrinetComponent.getPetrinet().createPetrinet();

		Place placeA = p.createPlace("A");
		Place placeB = p.createPlace("B");
		Place placeY = p.createPlace("Y");
		Place placeZ = p.createPlace("Z");

		Transition transition1  = p.createTransition("t1", Renews.COUNT);
		Transition transition2  = p.createTransition("t2", Renews.COUNT);
		Transition transition9  = p.createTransition("t9", Renews.COUNT);
		Transition transition10 = p.createTransition("t10", Renews.COUNT);

		placesToContain.add(placeA);
		placesToContain.add(placeB);
		placesToDelete.add(placeY);
		placesToDelete.add(placeZ);

		transitionsToContain.add(transition1);
		transitionsToContain.add(transition2);
		transitionsToDelete.add(transition9);
		transitionsToDelete.add(transition10);

		// Kanten von allen Places zu allen Transitions
		
		Arc arcA1  = p.createArc("xA1", placeA, transition1);
		Arc arcA2  = p.createArc("xA2", placeA, transition2);
		Arc arcA9  = p.createArc("xA9", placeA, transition9);
		Arc arcA10 = p.createArc("xA10", placeA, transition10);

		Arc arcB1  = p.createArc("xB1", placeB, transition1);
		Arc arcB2  = p.createArc("xB2", placeB, transition2);
		Arc arcB9  = p.createArc("xB9", placeB, transition9);
		Arc arcB10 = p.createArc("xB10", placeB, transition10);

		Arc arcY1  = p.createArc("xY1", placeY, transition1);
		Arc arcY2  = p.createArc("xY2", placeY, transition2);
		Arc arcY9  = p.createArc("xY9", placeY, transition9);
		Arc arcY10 = p.createArc("xY10", placeY, transition10);

		Arc arcZ1  = p.createArc("xZ1", placeZ, transition1);
		Arc arcZ2  = p.createArc("xZ2", placeZ, transition2);
		Arc arcZ9  = p.createArc("xZ9", placeZ, transition9);
		Arc arcZ10 = p.createArc("xZ10", placeZ, transition10);

		arcsToContain.add(arcA1);
		arcsToContain.add(arcA2);
		arcsToDelete.add(arcA9);
		arcsToDelete.add(arcA10);

		arcsToContain.add(arcB1);
		arcsToContain.add(arcB2);
		arcsToDelete.add(arcB9);
		arcsToDelete.add(arcB10);

		arcsToDelete.add(arcY1);
		arcsToDelete.add(arcY2);
		arcsToDelete.add(arcY9);
		arcsToDelete.add(arcY10);

		arcsToDelete.add(arcZ1);
		arcsToDelete.add(arcZ2);
		arcsToDelete.add(arcZ9);
		arcsToDelete.add(arcZ10);
		
		// Kanten von allen Trasitions zu allen Places

		Arc arc1A  = p.createArc("x1A", transition1, placeA);
		Arc arc1B  = p.createArc("x1B", transition1, placeB);
		Arc arc1Y  = p.createArc("x1Y", transition1, placeY);
		Arc arc1Z  = p.createArc("x1Z", transition1, placeZ);

		Arc arc2A  = p.createArc("x2A", transition2, placeA);
		Arc arc2B  = p.createArc("x2B", transition2, placeB);
		Arc arc2Y  = p.createArc("x2Y", transition2, placeY);
		Arc arc2Z  = p.createArc("x2Z", transition2, placeZ);

		Arc arc9A  = p.createArc("x9A", transition9, placeA);
		Arc arc9B  = p.createArc("x9B", transition9, placeB);
		Arc arc9Y  = p.createArc("x9Y", transition9, placeY);
		Arc arc9Z  = p.createArc("x9Z", transition9, placeZ);

		Arc arc10A  = p.createArc("x10A", transition10, placeA);
		Arc arc10B  = p.createArc("x10B", transition10, placeB);
		Arc arc10Y  = p.createArc("x10Y", transition10, placeY);
		Arc arc10Z  = p.createArc("x10Z", transition10, placeZ);

		// Kanten in zu löschende und zu behaltende aufteilen
		
		arcsToContain.add(arc1A);
		arcsToContain.add(arc1B);
		arcsToDelete.add(arc1Y);
		arcsToDelete.add(arc1Z);		

		arcsToContain.add(arc2A);
		arcsToContain.add(arc2B);
		arcsToDelete.add(arc2Y);
		arcsToDelete.add(arc2Z);		

		arcsToDelete.add(arc9A);
		arcsToDelete.add(arc9B);
		arcsToDelete.add(arc9Y);
		arcsToDelete.add(arc9Z);		

		arcsToDelete.add(arc10A);
		arcsToDelete.add(arc10B);
		arcsToDelete.add(arc10Y);
		arcsToDelete.add(arc10Z);
		
		JungData jung = buildJung(p);
		
		Set<INode> nodesToDelete = new HashSet<INode>(placesToDelete);
		nodesToDelete.addAll(transitionsToDelete);
		
		jungMod.delete(jung, arcsToDelete, nodesToDelete);
		
		Set<Arc>   remainingArcs  = new HashSet<Arc>(jung.getJungGraph().getEdges());
		Set<INode> remainingNodes = new HashSet<INode>(jung.getJungGraph().getVertices());		
		Set<INode> nodesToContain = new HashSet<INode>(placesToContain);
		nodesToContain.addAll(transitionsToContain);

		assertTrue(remainingArcs.equals(arcsToContain));
		assertTrue(remainingNodes.equals(nodesToContain));
	}
	
	
	private JungData buildJung(Petrinet p) {
        DirectedSparseGraph<INode, Arc> graph = new DirectedSparseGraph<INode, Arc>();
        JungData jung = new JungData(graph, new StaticLayout<INode, Arc>(graph));
        
        int y = 1;
        
        for (Place place : p.getAllPlaces()) {
        	y = y + farEnoughAway;
        	jungMod.createPlace(jung, place, new Point(1, y));
        }
        
        for (Transition transition : p.getAllTransitions()) {
        	y = y + farEnoughAway;
        	jungMod.createTransition(jung, transition, new Point(1, y));
        }
        
        for (Arc arc : p.getAllArcs()) {       	
        	
        	if (arc.getStart() instanceof Place) {
        		jungMod.createArc(jung, arc, (Place) arc.getStart(), (Transition) arc.getEnd());
        	} else {
        		jungMod.createArc(jung, arc, (Transition) arc.getStart(), (Place) arc.getEnd());
        	}
        }
        		
        return  jung;
	}

	@Test
	public void testMoveNode() {
		jungMod.createTransition(jungData, transition1, pointPositive1);
		
		jungMod.moveNode(jungData, transition1, pointPositive2);		
		
		assertTrue(Double.compare(pointPositive2.getX(), jungData.getJungLayout().getX(transition1)) == 0);
		assertTrue(Double.compare(pointPositive2.getY(), jungData.getJungLayout().getY(transition1)) == 0);
	}

	/**
	 *  Keine Mindestentfernungs-Exception für das bewegen eines Nodes selbst.
	 */
	@Test
	public void testMoveNode_selfOverlay() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.moveNode(jungData, place1, pointPositive1);
	}
	
	/**
	 *  Keine Mindestentfernungs-Exception für das bewegen eines Nodes selbst.
	 */
	@Test
	public void testMoveNode_selfCloseLeft() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.moveNode(jungData, place1, pointPositive1TooCloseLeft);
	}
	
	/**
	 *  Keine Mindestentfernungs-Exception für das bewegen eines Nodes selbst.
	 */
	@Test
	public void testMoveNode_selfCloseRight() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.moveNode(jungData, place1, pointPositive1TooCloseRight);
	}
	
	/**
	 *  Keine Mindestentfernungs-Exception für das bewegen eines Nodes selbst.
	 */
	@Test
	public void testMoveNode_selfCloseTop() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.moveNode(jungData, place1, pointPositive1TooCloseTop);
	}
	
	/**
	 *  Keine Mindestentfernungs-Exception für das bewegen eines Nodes selbst.
	 */
	@Test
	public void testMoveNode_selfCloseBottom() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.moveNode(jungData, place1, pointPositive1TooCloseBottom);
	}	/**
	 *  Node gerade weit genug weg von einem anderen Node
	 */
	@Test
	public void testMoveNode_JustEnoughLeft() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.createPlace(jungData, place2, pointPositive2);
		jungMod.moveNode(jungData, place2, pointPositive1JustEnoughLeft);
	}
	
	/**
	 *  Node gerade weit genug weg von einem anderen Node
	 */
	@Test
	public void testMoveNode_JustEnoughRight() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.createPlace(jungData, place2, pointPositive2);
		jungMod.moveNode(jungData, place2, pointPositive1JustEnoughRight);
	}
	
	/**
	 *  Node gerade weit genug weg von einem anderen Node
	 */
	@Test
	public void testMoveNode_JustEnoughTop() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.createPlace(jungData, place2, pointPositive2);
		jungMod.moveNode(jungData, place2, pointPositive1JustEnoughTop);
	}
	
	/**
	 *  Node gerade weit genug weg von einem anderen Node
	 */
	@Test
	public void testMoveNode_JustEnoughBottom() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.createPlace(jungData, place2, pointPositive2);
		jungMod.moveNode(jungData, place2, pointPositive1JustEnoughBottom);
	}

	public void testUpdateArc() {
		// TODO
	}

	public void testUpdatePlace() {
		// TODO
	}

	public void testUpdateTransition() {
		// TODO
	}
	
	
	////////////////////////////////////////////////////
	// 	Testen der Methoden mit Parameter null
	////////////////////////////////////////////////////
	/**
	 * Null-Test von createArc (Place nach Transititon)
	 */
	@Test(expected=IllegalArgumentException.class) public void testNull_createArc_PlaceToTranstion_1()  { jungMod.createArc(null, 		arc11, 	place1, 	transition1); }
	@Test(expected=IllegalArgumentException.class) public void testNull_createArc_PlaceToTranstion_2()  { jungMod.createArc(jungData, 	null, 	place1, 	transition1); }
	@Test(expected=IllegalArgumentException.class) public void testNull_createArc_PlaceToTranstion_3()  { jungMod.createArc(jungData, 	arc11,  null, 		transition1); }
	@Test(expected=IllegalArgumentException.class) public void testNull_createArc_PlaceToTranstion_4()  { jungMod.createArc(jungData, 	arc11,  place1, 	null); 		  }

	/**
	 * Null-Test von createArc (Transititon nach Place)
	 */
	@Test(expected=IllegalArgumentException.class) public void testNull_createArc_TranstionToPlace_1()  { jungMod.createArc(null, 		arc11, 	transition1, place1); }
	@Test(expected=IllegalArgumentException.class) public void testNull_createArc_TranstionToPlace_2()  { jungMod.createArc(jungData, 	null, 	transition1, place1); }
	@Test(expected=IllegalArgumentException.class) public void testNull_createArc_TranstionToPlace_3()  { jungMod.createArc(jungData, 	arc11,  null, 		 place1); }
	@Test(expected=IllegalArgumentException.class) public void testNull_createArc_TranstionToPlace_4()  { jungMod.createArc(jungData, 	arc11,  transition1, null); }
		
	/**
	 * restliche Null-Tests
	 */
	@Test(expected=IllegalArgumentException.class) public void testNull_createPlace_1() { jungMod.createPlace(null, 	place1, pointPositive1); }
	@Test(expected=IllegalArgumentException.class) public void testNull_createPlace_2() { jungMod.createPlace(jungData, null, 	pointPositive1); }
	@Test(expected=IllegalArgumentException.class) public void testNull_createPlace_3() { jungMod.createPlace(jungData, place1, null); }
	
	@Test(expected=IllegalArgumentException.class) public void testNull_createTransition_1() { jungMod.createTransition(null, 	  transition1, pointPositive1); }
	@Test(expected=IllegalArgumentException.class) public void testNull_createTransition_2() { jungMod.createTransition(jungData, null, 	   pointPositive1); }
	@Test(expected=IllegalArgumentException.class) public void testNull_createTransition_3() { jungMod.createTransition(jungData, transition1, null); }
	
	@Test(expected=IllegalArgumentException.class) public void testNull_delete_1() { jungMod.delete(null, 	  new HashSet<Arc>(), new HashSet<INode>()); }
	@Test(expected=IllegalArgumentException.class) public void testNull_delete_2() { jungMod.delete(jungData, null, 	   	 	  new HashSet<INode>()); }
	@Test(expected=IllegalArgumentException.class) public void testNull_delete_3() { jungMod.delete(jungData, new HashSet<Arc>(), null); }

	@Test(expected=IllegalArgumentException.class) 
	public void testNull_delete_4() {
		Set<Arc> arcs = new HashSet<Arc>();
		arcs.add(null);
		jungMod.delete(jungData, arcs, new HashSet<INode>()); 
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testNull_delete_5() {
		Set<INode> nodes = new HashSet<INode>();
		nodes.add(null);
		jungMod.delete(jungData, new HashSet<Arc>(), nodes); 
	}
	
	@Test(expected=IllegalArgumentException.class) public void testNull_moveNode_1() { jungMod.moveNode(null, 		place1, 	pointPositive1); }
	@Test(expected=IllegalArgumentException.class) public void testNull_moveNode_2() { jungMod.moveNode(jungData, 	null, 		pointPositive1); }
	@Test(expected=IllegalArgumentException.class) public void testNull_moveNode_3() { jungMod.moveNode(jungData, 	place1, 	null); }

	@Test(expected=IllegalArgumentException.class) public void testNull_updateArc_1() { jungMod.updateArc(null, 	arc11); }
	@Test(expected=IllegalArgumentException.class) public void testNull_updateArc_2() { jungMod.updateArc(jungData, null); }

	@Test(expected=IllegalArgumentException.class) public void testNull_updatePlace_1() { jungMod.updatePlace(null, 	place1); }
	@Test(expected=IllegalArgumentException.class) public void testNull_updatePlace_2() { jungMod.updatePlace(jungData, null); }

	@Test(expected=IllegalArgumentException.class) public void testNull_updateTransition_1() { jungMod.updateTransition(null,		transition1); }
	@Test(expected=IllegalArgumentException.class) public void testNull_updateTransition_2() { jungMod.updateTransition(jungData, 	null); }

	
	
	
	
	//////////////////////////////////////////////////////////
	// Testen der Methoden mit fachlich ungültigen Parametern
	//////////////////////////////////////////////////////////

	/**
	 * Test von createArc (Place nach Transititon)
	 * 
	 * Place nicht inzident zu Arc
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createArc_PlaceToTranstion_invalidPlace() {
		jungMod.createArc(jungData, arc11, place2, transition1);
	}

	/**
	 * Transition nicht inzident zu Arc
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createArc_PlaceToTranstion_invalidTransition() {
		jungMod.createArc(jungData, arc11, place1, transition2);
	}

	/**
	 * Place und Transition nicht inzident zu Arc
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createArc_PlaceToTranstion_bothInvalid() {
		jungMod.createArc(jungData, arc22, place1, transition1);
	}

	/**
	 * Place ist nicht im jung Graphen enthalten
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createArc_PlaceToTranstion_unknownPlace() {
		jungMod.createTransition(jungData, transition1, pointPositive1);
		jungMod.createArc(jungData, arc11, place1, transition1);
	}

	/**
	 * Transition ist nicht im jung Graphen enthalten
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createArc_PlaceToTranstion_unknownTransition() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.createArc(jungData, arc11, place1, transition1);
	}

	/**
	 * beides ist nicht im jung Graphen enthalten
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createArc_PlaceToTranstion_bothUnknown() {
		jungMod.createArc(jungData, arc11, place1, transition1);
	}
	

	/**
	 * Test von createArc (Transititon nach Place)
	 * 
	 * Place nicht inzident zu Arc
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createArc_TranstionToPlace_invalidPlace() {
		jungMod.createArc(jungData, arc11, transition1, place2);
	}

	/**
	 * Transition nicht inzident zu Arc
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createArc_TranstionToPlace_invalidTransition() {
		jungMod.createArc(jungData, arc11, transition2, place1);
	}

	/**
	 * Place und Transition nicht inzident zu Arc
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createArc_TranstionToPlace_bothInvalid() {
		jungMod.createArc(jungData, arc22, transition1, place1);
	}

	/**
	 * Place ist nicht im jung Graphen enthalten
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createArc_TranstionToPlace_unknownPlace() {
		jungMod.createTransition(jungData, transition1, pointPositive1);
		jungMod.createArc(jungData, arc11, transition1, place1);
	}

	/**
	 * Transition ist nicht im jung Graphen enthalten
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createArc_TranstionToPlace_unknownTransition() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.createArc(jungData, arc11, transition1, place1);
	}

	/**
	 * beides ist nicht im jung Graphen enthalten
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createArc_TranstionToPlace_bothUnknown() {
		jungMod.createArc(jungData, arc11, transition1, place1);
	}
	
	
	/**
	 * Place schon im Graphen enthalten
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_containsPlace() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.createPlace(jungData, place1, pointPositive1);
	}

	
	/**
	 * Place ist zu nah an einem anderen Place (Überlagerung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_toClose_OverlayPlace() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.createPlace(jungData, place2, pointPositive1);
	}
	
	/**
	 * Place ist zu nah an einem anderen Place (Innerhalb der Mindestentfernung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_toClose_TooCloseLeftPlace() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.createPlace(jungData, place2, pointPositive1TooCloseLeft);
	}
	
	/**
	 * Place ist zu nah an einem anderen Place (Innerhalb der Mindestentfernung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_toClose_TooCloseRightPlace() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.createPlace(jungData, place2, pointPositive1TooCloseRight);
	}
	
	/**
	 * Place ist zu nah an einem anderen Place (Innerhalb der Mindestentfernung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_toClose_TooCloseTopPlace() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.createPlace(jungData, place2, pointPositive1TooCloseTop);
	}
	
	/**
	 * Place ist zu nah an einem anderen Place (Innerhalb der Mindestentfernung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_toClose_TooCloseBottomPlace() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.createPlace(jungData, place2, pointPositive1TooCloseBottom);
	}

	
	/**
	 * Place ist zu nah an einem anderen Transition (Überlagerung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_toClose_OverlayTransition() {
		jungMod.createTransition(jungData, transition1, pointPositive1);
		jungMod.createPlace(jungData, place2, pointPositive1);
	}
	
	/**
	 * Place ist zu nah an einem anderen Transition (Innerhalb der Mindestentfernung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_toClose_TooCloseLeftTransition() {
		jungMod.createTransition(jungData, transition1, pointPositive1);
		jungMod.createPlace(jungData, place2, pointPositive1TooCloseLeft);
	}

	/**
	 * Place ist zu nah an einem anderen Transition (Innerhalb der Mindestentfernung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_toClose_TooCloseRightTransition() {
		jungMod.createTransition(jungData, transition1, pointPositive1);
		jungMod.createPlace(jungData, place2, pointPositive1TooCloseRight);
	}
	
	/**
	 * Place ist zu nah an einem anderen Transition (Innerhalb der Mindestentfernung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_toClose_TooCloseTopTransition() {
		jungMod.createTransition(jungData, transition1, pointPositive1);
		jungMod.createPlace(jungData, place2, pointPositive1TooCloseTop);
	}
	
	/**
	 * Place ist zu nah an einem anderen Transition (Innerhalb der Mindestentfernung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_toClose_TooCloseBottomTransition() {
		jungMod.createTransition(jungData, transition1, pointPositive1);
		jungMod.createPlace(jungData, place2, pointPositive1TooCloseBottom);
	}

	/**
	 * Transition schon im Graphen enthalten
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_containsTransition() {
		jungMod.createTransition(jungData, transition1, pointPositive1);
		jungMod.createTransition(jungData, transition1, pointPositive1);
	}
		
	/**
	 * Transition ist zu nah an einer anderen Transition (Überlagerung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_toClose_OverlayTransition() {
		jungMod.createTransition(jungData, transition1, pointPositive1);
		jungMod.createTransition(jungData, transition2, pointPositive1);
	}
	
	/**
	 * Transition ist zu nah an einer anderen Transition (Innerhalb der Mindestentfernung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_toClose_TooCloseLeftTransition() {
		jungMod.createTransition(jungData, transition1, pointPositive1);
		jungMod.createTransition(jungData, transition2, pointPositive1TooCloseLeft);
	}
	
	/**
	 * Transition ist zu nah an einer anderen Transition (Innerhalb der Mindestentfernung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_toClose_TooCloseRightTransition() {
		jungMod.createTransition(jungData, transition1, pointPositive1);
		jungMod.createTransition(jungData, transition2, pointPositive1TooCloseRight);
	}
	
	/**
	 * Transition ist zu nah an einer anderen Transition (Innerhalb der Mindestentfernung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_toClose_TooCloseTopTransition() {
		jungMod.createTransition(jungData, transition1, pointPositive1);
		jungMod.createTransition(jungData, transition2, pointPositive1TooCloseTop);
	}
	
	/**
	 * Transition ist zu nah an einer anderen Transition (Innerhalb der Mindestentfernung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_toClose_TooCloseBottomTransition() {
		jungMod.createTransition(jungData, transition1, pointPositive1);
		jungMod.createTransition(jungData, transition2, pointPositive1TooCloseBottom);
	}
	
	/**
	 * Transition ist zu nah an einem Place  (Überlagerung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_toClose_OverlayPlace() {
		jungMod.createPlace(jungData, place2, pointPositive1);
		jungMod.createTransition(jungData, transition1, pointPositive1);
	}
	
	/**
	 * Transition ist zu nah an einem Place (Innerhalb der Mindestentfernung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_toClose_TooCloseLeftPlace() {
		jungMod.createPlace(jungData, place2, pointPositive1);
		jungMod.createTransition(jungData, transition1, pointPositive1TooCloseLeft);
	}
	
	/**
	 * Transition ist zu nah an einem Place (Innerhalb der Mindestentfernung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_toClose_TooCloseRightPlace() {
		jungMod.createPlace(jungData, place2, pointPositive1);
		jungMod.createTransition(jungData, transition1, pointPositive1TooCloseRight);
	}
	
	/**
	 * Transition ist zu nah an einem Place (Innerhalb der Mindestentfernung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_toClose_TooCloseTopPlace() {
		jungMod.createPlace(jungData, place2, pointPositive1);
		jungMod.createTransition(jungData, transition1, pointPositive1TooCloseTop);
	}
	
	/**
	 * Transition ist zu nah an einem Place (Innerhalb der Mindestentfernung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_toClose_TooCloseBottomPlace() {
		jungMod.createPlace(jungData, place2, pointPositive1);
		jungMod.createTransition(jungData, transition1, pointPositive1TooCloseBottom);
	}
	

	/**
	 * Arc nicht im Graphen enthalten
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_delete_unknownArc() {
		Set<Arc> arcs 	 = new HashSet<Arc>();
		Set<INode> nodes = new HashSet<INode>();
		
		arcs.add(arc11);
		
		jungMod.delete(jungData, arcs, nodes);
	}


	/**
	 * INode nicht im Graphen enthalten
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_delete_unknownINode() {
		Set<Arc> arcs 	 = new HashSet<Arc>();
		Set<INode> nodes = new HashSet<INode>();
		
		nodes.add(place1);
		
		jungMod.delete(jungData, arcs, nodes);
	}

	/**
	 * Beim Node löschen werden Kanten vergessen
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_delete_missingArcsToDelete() {
		Set<Arc> arcs 	 = new HashSet<Arc>();
		Set<INode> nodes = new HashSet<INode>();

		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.createTransition(jungData, transition1, pointPositive2);
		jungMod.createArc(jungData, arc11, place1, transition1);		
		
		nodes.add(place1);
		
		jungMod.delete(jungData, arcs, nodes);
	}

	/**
	 * Position durch negativen X-Wert ungültig
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testInvalidArguments_moveNode_NegativeX() { 
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.moveNode(jungData, place1, pointNegativeX); 
	}

	/**
	 * Position durch negativen Y-Wert ungültig
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testInvalidArguments_moveNode_NegativeY() { 
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.moveNode(jungData, place1, pointNegativeY); 
	}

	/**
	 * Position durch negative X und Y-Werte ungültig
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testInvalidArguments_moveNode_NegativeXY() { 
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.moveNode(jungData, place1, pointNegativeXY);
	}

	/**
	 * Node nicht im Graphen enthalten
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testInvalidArguments_moveNode_UnknownNode() { 
		jungMod.moveNode(jungData, place1, pointPositive1);
	}
	
	/**
	 * Node kommt zu nah an einen anderen Node (Überlagerung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_moveNode_Overlay() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.createPlace(jungData, place2, pointPositive2);
		jungMod.moveNode(jungData, place2, pointPositive1);
	}
	
	/**
	 *  Node kommt zu nah an einen anderen Node (Innerhalb der Mindestentfernung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_moveNode_TooCloseLeft() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.createPlace(jungData, place2, pointPositive2);
		jungMod.moveNode(jungData, place2, pointPositive1TooCloseLeft);
	}
	
	/**
	 *  Node kommt zu nah an einen anderen Node (Innerhalb der Mindestentfernung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_moveNode_TooCloseRight() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.createPlace(jungData, place2, pointPositive2);
		jungMod.moveNode(jungData, place2, pointPositive1TooCloseRight);
	}
	
	/**
	 *  Node kommt zu nah an einen anderen Node (Innerhalb der Mindestentfernung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_moveNode_TooCloseTop() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.createPlace(jungData, place2, pointPositive2);
		jungMod.moveNode(jungData, place2, pointPositive1TooCloseTop);
	}
	
	/**
	 *  Node kommt zu nah an einen anderen Node (Innerhalb der Mindestentfernung)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_moveNode_TooCloseBottom() {
		jungMod.createPlace(jungData, place1, pointPositive1);
		jungMod.createPlace(jungData, place2, pointPositive2);
		jungMod.moveNode(jungData, place2, pointPositive1TooCloseBottom);
	}
	
	/**
	 * Arc nicht im Graphen enthalten
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testInvalidArguments_updateArc() {
		jungMod.updateArc(jungData, arc11);
	}

	/**
	 * Place nicht im Graphen enthalten
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testInvalidArguments_updatePlace() {
		jungMod.updatePlace(jungData, place1);
	}

	/**
	 * Transition nicht im Graphen enthalten
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testInvalidArguments_updateTransition() {
		jungMod.updateTransition(jungData, transition1);
	}
}
