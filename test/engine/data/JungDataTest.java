package engine.data;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

import org.junit.*;

import static org.junit.Assert.*;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import engine.data.JungData;
import petrinet.Arc;
import petrinet.INode;
import petrinet.Petrinet;
import petrinet.PetrinetComponent;
import petrinet.Place;
import petrinet.Renews;
import petrinet.Transition;

/**
 * @author Mathias Blumreiter
 */
public class JungDataTest {
	private Petrinet p;

	private Place place1;
	private Place place2;

	private Transition transition1;
	private Transition transition2;

	private Arc arc11;
	private Arc arc12;
	private Arc arc22;

	private JungData emptyJung;
	private DirectedGraph<INode, Arc> graph;
	private AbstractLayout<INode, Arc> layout;

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
	int justFarEnoughAway = JungData.NODE_RADIUS * 2;
	int farEnoughAway = justFarEnoughAway + 1;

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

		graph = new DirectedSparseGraph<INode, Arc>();
		layout = new StaticLayout<INode, Arc>(graph);

		emptyJung = new JungData(graph, layout);

		int x = 100;
		int y = 100;

		pointPositive1 = new Point(x, y);

		pointPositive1TooCloseLeft = new Point(x - justFarEnoughAway + 1, y);
		pointPositive1TooCloseRight = new Point(x + justFarEnoughAway - 1, y);
		pointPositive1TooCloseTop = new Point(x, y + justFarEnoughAway - 1);
		pointPositive1TooCloseBottom = new Point(x, y - justFarEnoughAway + 1);

		pointPositive1JustEnoughLeft = new Point(x - justFarEnoughAway, y);
		pointPositive1JustEnoughRight = new Point(x + justFarEnoughAway, y);
		pointPositive1JustEnoughTop = new Point(x, y + justFarEnoughAway);
		pointPositive1JustEnoughBottom = new Point(x, y - justFarEnoughAway);

		pointPositive2 = new Point(x + farEnoughAway, y + farEnoughAway);

		pointNegativeX = new Point(-1, 1);
		pointNegativeY = new Point(1, -1);
		pointNegativeXY = new Point(-1, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullTest_construct_1() {
		new JungData(null, layout);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullTest_construct_2() {
		new JungData(graph, null);
	}

	@Test
	public void getJungGraph() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.createTransition(transition1, pointPositive2);
		emptyJung.createArc(arc11, place1, transition1);
		assertTrue(graph.getVertexCount() > 0 && graph.getEdgeCount() > 0);
		assertTrue((new JungData(graph, layout)).getJungGraph().equals(graph));
	}

	@Test
	public void getJungLayout() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.createTransition(transition1, pointPositive2);
		emptyJung.createArc(arc11, place1, transition1);
		assertTrue(graph.getVertexCount() > 0 && graph.getEdgeCount() > 0);
		assertTrue((new JungData(graph, layout)).getJungLayout().equals(layout));
	}

	/**
	 * Mindestentfernung 2er Nodes mindestens 1
	 */
	@Test
	public void testCreateArc_MinNodeDistanceRadiusSize() {
		assertTrue(JungData.NODE_RADIUS >= 1);
	}

	/**
	 * Arc (Place > Transition) nach hinzufügen auch enthalten
	 */
	@Test
	public void testCreateArc_PlaceToTranstion() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.createTransition(transition1, pointPositive2);

		emptyJung.createArc(arc11, place1, transition1);

		assertTrue(emptyJung.getJungGraph().containsEdge(arc11));
	}

	/**
	 * Arc (Transition > Place) nach hinzufügen auch enthalten
	 */
	@Test
	public void testCreateArc_TranstionToPlace() {
		emptyJung.createPlace(place2, pointPositive1);
		emptyJung.createTransition(transition1, pointPositive2);

		emptyJung.createArc(arc12, transition1, place2);

		assertTrue(emptyJung.getJungGraph().containsEdge(arc12));
	}

	/**
	 * Place hinzufügen
	 */
	@Test
	public void testCreatePlace() {
		emptyJung.createPlace(place1, pointPositive1);
		assertTrue(emptyJung.getJungGraph().containsVertex(place1));
		assertTrue(Double.compare(pointPositive1.getX(), emptyJung
				.getJungLayout().getX(place1)) == 0);
		assertTrue(Double.compare(pointPositive1.getY(), emptyJung
				.getJungLayout().getY(place1)) == 0);
	}

	/**
	 * Transition hinzufügen
	 */
	@Test
	public void testCreateTransition() {
		emptyJung.createTransition(transition1, pointPositive1);
		assertTrue(emptyJung.getJungGraph().containsVertex(transition1));
		assertTrue(Double.compare(pointPositive1.getX(), emptyJung
				.getJungLayout().getX(transition1)) == 0);
		assertTrue(Double.compare(pointPositive1.getY(), emptyJung
				.getJungLayout().getY(transition1)) == 0);
	}

	@Test
	public void testDelete_emptyCollections() {
		JungData jung = buildJung(p);

		Set<INode> nodesBefore = new HashSet<INode>(jung.getJungGraph()
				.getVertices());
		Set<Arc> arcsBefore = new HashSet<Arc>(jung.getJungGraph().getEdges());

		jung.delete(new HashSet<Arc>(), new HashSet<INode>());

		assertTrue(nodesBefore.equals(new HashSet<INode>(jung.getJungGraph()
				.getVertices())));
		assertTrue(arcsBefore.equals(new HashSet<Arc>(jung.getJungGraph()
				.getEdges())));
	}

	@Test
	public void testDelete() {
		// erstellen eines Petrinetzes
		// - 2 Gruppen von Nodes und Arcs
		// - es wird ein Jung Graph erstellt in dem beide entahlten sind
		// - Gruppe 2 wird aus Jung gelöscht
		// - prüfen ob Gruppe 1 noch vollständig enthalten ist, und ob sonst
		// nichts weiter enthalten ist
		Set<Place> placesToContain = new HashSet<Place>();
		Set<Place> placesToDelete = new HashSet<Place>();

		Set<Transition> transitionsToContain = new HashSet<Transition>();
		Set<Transition> transitionsToDelete = new HashSet<Transition>();

		Set<Arc> arcsToContain = new HashSet<Arc>();
		Set<Arc> arcsToDelete = new HashSet<Arc>();

		Petrinet p = PetrinetComponent.getPetrinet().createPetrinet();

		Place placeA = p.createPlace("A");
		Place placeB = p.createPlace("B");
		Place placeY = p.createPlace("Y");
		Place placeZ = p.createPlace("Z");

		Transition transition1 = p.createTransition("t1", Renews.COUNT);
		Transition transition2 = p.createTransition("t2", Renews.COUNT);
		Transition transition9 = p.createTransition("t9", Renews.COUNT);
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

		Arc arcA1 = p.createArc("xA1", placeA, transition1);
		Arc arcA2 = p.createArc("xA2", placeA, transition2);
		Arc arcA9 = p.createArc("xA9", placeA, transition9);
		Arc arcA10 = p.createArc("xA10", placeA, transition10);

		Arc arcB1 = p.createArc("xB1", placeB, transition1);
		Arc arcB2 = p.createArc("xB2", placeB, transition2);
		Arc arcB9 = p.createArc("xB9", placeB, transition9);
		Arc arcB10 = p.createArc("xB10", placeB, transition10);

		Arc arcY1 = p.createArc("xY1", placeY, transition1);
		Arc arcY2 = p.createArc("xY2", placeY, transition2);
		Arc arcY9 = p.createArc("xY9", placeY, transition9);
		Arc arcY10 = p.createArc("xY10", placeY, transition10);

		Arc arcZ1 = p.createArc("xZ1", placeZ, transition1);
		Arc arcZ2 = p.createArc("xZ2", placeZ, transition2);
		Arc arcZ9 = p.createArc("xZ9", placeZ, transition9);
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

		Arc arc1A = p.createArc("x1A", transition1, placeA);
		Arc arc1B = p.createArc("x1B", transition1, placeB);
		Arc arc1Y = p.createArc("x1Y", transition1, placeY);
		Arc arc1Z = p.createArc("x1Z", transition1, placeZ);

		Arc arc2A = p.createArc("x2A", transition2, placeA);
		Arc arc2B = p.createArc("x2B", transition2, placeB);
		Arc arc2Y = p.createArc("x2Y", transition2, placeY);
		Arc arc2Z = p.createArc("x2Z", transition2, placeZ);

		Arc arc9A = p.createArc("x9A", transition9, placeA);
		Arc arc9B = p.createArc("x9B", transition9, placeB);
		Arc arc9Y = p.createArc("x9Y", transition9, placeY);
		Arc arc9Z = p.createArc("x9Z", transition9, placeZ);

		Arc arc10A = p.createArc("x10A", transition10, placeA);
		Arc arc10B = p.createArc("x10B", transition10, placeB);
		Arc arc10Y = p.createArc("x10Y", transition10, placeY);
		Arc arc10Z = p.createArc("x10Z", transition10, placeZ);

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

		jung.delete(arcsToDelete, nodesToDelete);

		Set<Arc> remainingArcs = new HashSet<Arc>(jung.getJungGraph()
				.getEdges());
		Set<INode> remainingNodes = new HashSet<INode>(jung.getJungGraph()
				.getVertices());
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
			jung.createPlace(place, new Point(1, y));
		}

		for (Transition transition : p.getAllTransitions()) {
			y = y + farEnoughAway;
			jung.createTransition(transition, new Point(1, y));
		}

		for (Arc arc : p.getAllArcs()) {

			if (arc.getStart() instanceof Place) {
				jung.createArc(arc, (Place) arc.getStart(),
						(Transition) arc.getEnd());
			} else {
				jung.createArc(arc, (Transition) arc.getStart(),
						(Place) arc.getEnd());
			}
		}

		return jung;
	}

	@Test
	public void testMoveNode() {
		emptyJung.createTransition(transition1, pointPositive1);

		emptyJung.moveNodeWithPositionCheck(transition1, pointPositive2);

		assertTrue(Double.compare(pointPositive2.getX(), emptyJung
				.getJungLayout().getX(transition1)) == 0);
		assertTrue(Double.compare(pointPositive2.getY(), emptyJung
				.getJungLayout().getY(transition1)) == 0);
	}

	/**
	 * Keine Mindestentfernungs-Exception für das bewegen eines Nodes selbst.
	 */
	@Test
	public void testMoveNode_selfOverlay() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.moveNodeWithPositionCheck(place1, pointPositive1);
	}

	/**
	 * Keine Mindestentfernungs-Exception für das bewegen eines Nodes selbst.
	 */
	@Test
	public void testMoveNode_selfCloseLeft() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.moveNodeWithPositionCheck(place1, pointPositive1TooCloseLeft);
	}

	/**
	 * Keine Mindestentfernungs-Exception für das bewegen eines Nodes selbst.
	 */
	@Test
	public void testMoveNode_selfCloseRight() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung
				.moveNodeWithPositionCheck(place1, pointPositive1TooCloseRight);
	}

	/**
	 * Keine Mindestentfernungs-Exception für das bewegen eines Nodes selbst.
	 */
	@Test
	public void testMoveNode_selfCloseTop() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.moveNodeWithPositionCheck(place1, pointPositive1TooCloseTop);
	}

	/**
	 * Keine Mindestentfernungs-Exception für das bewegen eines Nodes selbst.
	 */
	@Test
	public void testMoveNode_selfCloseBottom() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.moveNodeWithPositionCheck(place1,
				pointPositive1TooCloseBottom);
	}

	/**
	 * Node gerade weit genug weg von einem anderen Node
	 */
	@Test
	public void testMoveNode_JustEnoughLeft() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.createPlace(place2, pointPositive2);
		emptyJung.moveNodeWithPositionCheck(place2,
				pointPositive1JustEnoughLeft);
	}

	/**
	 * Node gerade weit genug weg von einem anderen Node
	 */
	@Test
	public void testMoveNode_JustEnoughRight() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.createPlace(place2, pointPositive2);
		emptyJung.moveNodeWithPositionCheck(place2,
				pointPositive1JustEnoughRight);
	}

	/**
	 * Node gerade weit genug weg von einem anderen Node
	 */
	@Test
	public void testMoveNode_JustEnoughTop() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.createPlace(place2, pointPositive2);
		emptyJung
				.moveNodeWithPositionCheck(place2, pointPositive1JustEnoughTop);
	}

	/**
	 * Node gerade weit genug weg von einem anderen Node
	 */
	@Test
	public void testMoveNode_JustEnoughBottom() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.createPlace(place2, pointPositive2);
		emptyJung.moveNodeWithPositionCheck(place2,
				pointPositive1JustEnoughBottom);
	}

	/**
	 * Farbe nicht gesetzt -> Standardfarbe
	 */
	@Test
	public void testPlaceColor_defaultColor() {
		emptyJung.createPlace(place1, pointPositive1);

		assertEquals(emptyJung.getPlaceColor(place1),
				JungData.DEFAULT_COLOR_PLACE);
	}

	/**
	 * Farbe persistent
	 */
	@Test
	public void testPlaceColor_setColor() {
		emptyJung.createPlace(place1, pointPositive1);

		Color color = Color.getHSBColor(67, 155, 17);

		emptyJung.setPlaceColor(place1, color);

		// Farbe darf nicht Standardfarbe sein, sonst ist der Test sinnlos
		assertFalse(color.equals(JungData.DEFAULT_COLOR_PLACE));

		assertEquals(emptyJung.getPlaceColor(place1), color);
	}

	/**
	 * Farbe muss beim löschen eines Nodes mit gelöscht werden
	 */
	@Test
	public void testPlaceColor_createDeleteCreate() {
		emptyJung.createPlace(place1, pointPositive1);

		Color color = Color.getHSBColor(67, 155, 17);

		emptyJung.setPlaceColor(place1, color);

		// Farbe darf nicht Standardfarbe sein, sonst ist der Test sinnlos
		assertFalse(color.equals(JungData.DEFAULT_COLOR_PLACE));

		assertEquals(emptyJung.getPlaceColor(place1), color);

		Set<INode> nodes = new HashSet<INode>();
		Set<Arc> arcs = new HashSet<Arc>();

		nodes.add(place1);

		emptyJung.delete(arcs, nodes);

		emptyJung.createPlace(place1, pointPositive1);

		assertEquals(emptyJung.getPlaceColor(place1),
				JungData.DEFAULT_COLOR_PLACE);
	}

	/**
	 * Koordinate ist zu nah an einer Transition (Überlagerung)
	 */
	@Test
	public void test_isCreatePossibleAt_toClose_OverlayTransition() {
		emptyJung.createTransition(transition1, pointPositive1);
		assertFalse(emptyJung.isCreatePossibleAt(pointPositive1));
	}

	/**
	 * Koordinate ist zu nah an einer Transition (Innerhalb der
	 * Mindestentfernung)
	 */
	@Test
	public void test_isCreatePossibleAt_toClose_TooCloseLeftTransition() {
		emptyJung.createTransition(transition1, pointPositive1);
		assertFalse(emptyJung.isCreatePossibleAt(pointPositive1TooCloseLeft));
	}

	@Test
	public void test_isCreatePossibleAt_toClose_TooCloseRightTransition() {
		emptyJung.createTransition(transition1, pointPositive1);
		assertFalse(emptyJung.isCreatePossibleAt(pointPositive1TooCloseRight));
	}

	@Test
	public void test_isCreatePossibleAt_toClose_TooCloseTopTransition() {
		emptyJung.createTransition(transition1, pointPositive1);
		assertFalse(emptyJung.isCreatePossibleAt(pointPositive1TooCloseTop));
	}

	@Test
	public void test_isCreatePossibleAt_toClose_TooCloseBottomTransition() {
		emptyJung.createTransition(transition1, pointPositive1);
		assertFalse(emptyJung.isCreatePossibleAt(pointPositive1TooCloseBottom));
	}

	/**
	 * Koordinate gerade weit genug weg von einem anderen Node
	 */
	@Test
	public void test_isCreatePossibleAt_JustEnoughLeft() {
		emptyJung.createPlace(place1, pointPositive1);
		assertTrue(emptyJung.isCreatePossibleAt(pointPositive1JustEnoughLeft));
	}

	/**
	 * Koordinate gerade weit genug weg von einem anderen Node
	 */
	@Test
	public void test_isCreatePossibleAt_JustEnoughRight() {
		emptyJung.createPlace(place1, pointPositive1);
		assertTrue(emptyJung.isCreatePossibleAt(pointPositive1JustEnoughRight));
	}

	/**
	 * Koordinate gerade weit genug weg von einem anderen Node
	 */
	@Test
	public void test_isCreatePossibleAt_JustEnoughTop() {
		emptyJung.createPlace(place1, pointPositive1);
		assertTrue(emptyJung.isCreatePossibleAt(pointPositive1JustEnoughTop));
	}

	/**
	 * Koordinate gerade weit genug weg von einem anderen Node
	 */
	@Test
	public void test_isCreatePossibleAt_JustEnoughBottom() {
		emptyJung.createPlace(place1, pointPositive1);
		assertTrue(emptyJung.isCreatePossibleAt(pointPositive1JustEnoughBottom));
	}

	// //////////////////////////////////////////////////
	// Testen der Methoden mit Parameter null
	// //////////////////////////////////////////////////
	/**
	 * Null-Test von createArc (Place nach Transititon)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNull_createArc_PlaceToTranstion_1() {
		emptyJung.createArc(null, place1, transition1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNull_createArc_PlaceToTranstion_2() {
		emptyJung.createArc(arc11, null, transition1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNull_createArc_PlaceToTranstion_3() {
		emptyJung.createArc(arc11, place1, null);
	}

	/**
	 * Null-Test von createArc (Transititon nach Place)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNull_createArc_TranstionToPlace_1() {
		emptyJung.createArc(null, transition1, place1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNull_createArc_TranstionToPlace_2() {
		emptyJung.createArc(arc11, null, place1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNull_createArc_TranstionToPlace_3() {
		emptyJung.createArc(arc11, transition1, null);
	}

	/**
	 * restliche Null-Tests
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNull_createPlace_1() {
		emptyJung.createPlace(null, pointPositive1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNull_createPlace_2() {
		emptyJung.createPlace(place1, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNull_createTransition_1() {
		emptyJung.createTransition(null, pointPositive1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNull_createTransition_2() {
		emptyJung.createTransition(transition1, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNull_delete_1() {
		emptyJung.delete(null, new HashSet<INode>());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNull_delete_2() {
		emptyJung.delete(new HashSet<Arc>(), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNull_delete_4() {
		Set<Arc> arcs = new HashSet<Arc>();
		arcs.add(null);
		emptyJung.delete(arcs, new HashSet<INode>());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNull_delete_5() {
		Set<INode> nodes = new HashSet<INode>();
		nodes.add(null);
		emptyJung.delete(new HashSet<Arc>(), nodes);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNull_moveNode_1() {
		emptyJung.moveNodeWithPositionCheck(null, pointPositive1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNull_moveNode_2() {
		emptyJung.moveNodeWithPositionCheck(place1, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNull_getPlaceColor() {
		emptyJung.getPlaceColor(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNull_setPlaceColor_1() {
		emptyJung.setPlaceColor(null, Color.BLACK);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNull_setPlaceColor_2() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.setPlaceColor(place1, null);
	}

	@Test
	public void testNull_isCreatePossibleAt() {
		assertFalse(emptyJung.isCreatePossibleAt(null));
	}

	// ////////////////////////////////////////////////////////
	// Testen der Methoden mit fachlich ungültigen Parametern
	// ////////////////////////////////////////////////////////

	/**
	 * Test von createArc (Place nach Transititon)
	 * 
	 * Place nicht inzident zu Arc
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createArc_PlaceToTranstion_invalidPlace() {
		emptyJung.createArc(arc11, place2, transition1);
	}

	/**
	 * Transition nicht inzident zu Arc
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createArc_PlaceToTranstion_invalidTransition() {
		emptyJung.createArc(arc11, place1, transition2);
	}

	/**
	 * Place und Transition nicht inzident zu Arc
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createArc_PlaceToTranstion_bothInvalid() {
		emptyJung.createArc(arc22, place1, transition1);
	}

	/**
	 * Place ist nicht im jung Graphen enthalten
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createArc_PlaceToTranstion_unknownPlace() {
		emptyJung.createTransition(transition1, pointPositive1);
		emptyJung.createArc(arc11, place1, transition1);
	}

	/**
	 * Transition ist nicht im jung Graphen enthalten
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createArc_PlaceToTranstion_unknownTransition() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.createArc(arc11, place1, transition1);
	}

	/**
	 * beides ist nicht im jung Graphen enthalten
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createArc_PlaceToTranstion_bothUnknown() {
		emptyJung.createArc(arc11, place1, transition1);
	}

	/**
	 * Test von createArc (Transititon nach Place)
	 * 
	 * Place nicht inzident zu Arc
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createArc_TranstionToPlace_invalidPlace() {
		emptyJung.createArc(arc11, transition1, place2);
	}

	/**
	 * Transition nicht inzident zu Arc
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createArc_TranstionToPlace_invalidTransition() {
		emptyJung.createArc(arc11, transition2, place1);
	}

	/**
	 * Place und Transition nicht inzident zu Arc
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createArc_TranstionToPlace_bothInvalid() {
		emptyJung.createArc(arc22, transition1, place1);
	}

	/**
	 * Place ist nicht im jung Graphen enthalten
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createArc_TranstionToPlace_unknownPlace() {
		emptyJung.createTransition(transition1, pointPositive1);
		emptyJung.createArc(arc11, transition1, place1);
	}

	/**
	 * Transition ist nicht im jung Graphen enthalten
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createArc_TranstionToPlace_unknownTransition() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.createArc(arc11, transition1, place1);
	}

	/**
	 * beides ist nicht im jung Graphen enthalten
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createArc_TranstionToPlace_bothUnknown() {
		emptyJung.createArc(arc11, transition1, place1);
	}

	/**
	 * Place schon im Graphen enthalten
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_containsPlace() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.createPlace(place1, pointPositive1);
	}

	/**
	 * Place ist zu nah an einem anderen Place (Überlagerung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_toClose_OverlayPlace() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.createPlace(place2, pointPositive1);
	}

	/**
	 * Place ist zu nah an einem anderen Place (Innerhalb der Mindestentfernung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_toClose_TooCloseLeftPlace() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.createPlace(place2, pointPositive1TooCloseLeft);
	}

	/**
	 * Place ist zu nah an einem anderen Place (Innerhalb der Mindestentfernung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_toClose_TooCloseRightPlace() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.createPlace(place2, pointPositive1TooCloseRight);
	}

	/**
	 * Place ist zu nah an einem anderen Place (Innerhalb der Mindestentfernung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_toClose_TooCloseTopPlace() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.createPlace(place2, pointPositive1TooCloseTop);
	}

	/**
	 * Place ist zu nah an einem anderen Place (Innerhalb der Mindestentfernung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_toClose_TooCloseBottomPlace() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.createPlace(place2, pointPositive1TooCloseBottom);
	}

	/**
	 * Place ist zu nah an einem anderen Transition (Überlagerung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_toClose_OverlayTransition() {
		emptyJung.createTransition(transition1, pointPositive1);
		emptyJung.createPlace(place2, pointPositive1);
	}

	/**
	 * Place ist zu nah an einem anderen Transition (Innerhalb der
	 * Mindestentfernung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_toClose_TooCloseLeftTransition() {
		emptyJung.createTransition(transition1, pointPositive1);
		emptyJung.createPlace(place2, pointPositive1TooCloseLeft);
	}

	/**
	 * Place ist zu nah an einem anderen Transition (Innerhalb der
	 * Mindestentfernung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_toClose_TooCloseRightTransition() {
		emptyJung.createTransition(transition1, pointPositive1);
		emptyJung.createPlace(place2, pointPositive1TooCloseRight);
	}

	/**
	 * Place ist zu nah an einem anderen Transition (Innerhalb der
	 * Mindestentfernung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_toClose_TooCloseTopTransition() {
		emptyJung.createTransition(transition1, pointPositive1);
		emptyJung.createPlace(place2, pointPositive1TooCloseTop);
	}

	/**
	 * Place ist zu nah an einem anderen Transition (Innerhalb der
	 * Mindestentfernung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createPlace_toClose_TooCloseBottomTransition() {
		emptyJung.createTransition(transition1, pointPositive1);
		emptyJung.createPlace(place2, pointPositive1TooCloseBottom);
	}

	/**
	 * Transition schon im Graphen enthalten
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_containsTransition() {
		emptyJung.createTransition(transition1, pointPositive1);
		emptyJung.createTransition(transition1, pointPositive1);
	}

	/**
	 * Transition ist zu nah an einer anderen Transition (Überlagerung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_toClose_OverlayTransition() {
		emptyJung.createTransition(transition1, pointPositive1);
		emptyJung.createTransition(transition2, pointPositive1);
	}

	/**
	 * Transition ist zu nah an einer anderen Transition (Innerhalb der
	 * Mindestentfernung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_toClose_TooCloseLeftTransition() {
		emptyJung.createTransition(transition1, pointPositive1);
		emptyJung.createTransition(transition2, pointPositive1TooCloseLeft);
	}

	/**
	 * Transition ist zu nah an einer anderen Transition (Innerhalb der
	 * Mindestentfernung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_toClose_TooCloseRightTransition() {
		emptyJung.createTransition(transition1, pointPositive1);
		emptyJung.createTransition(transition2, pointPositive1TooCloseRight);
	}

	/**
	 * Transition ist zu nah an einer anderen Transition (Innerhalb der
	 * Mindestentfernung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_toClose_TooCloseTopTransition() {
		emptyJung.createTransition(transition1, pointPositive1);
		emptyJung.createTransition(transition2, pointPositive1TooCloseTop);
	}

	/**
	 * Transition ist zu nah an einer anderen Transition (Innerhalb der
	 * Mindestentfernung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_toClose_TooCloseBottomTransition() {
		emptyJung.createTransition(transition1, pointPositive1);
		emptyJung.createTransition(transition2, pointPositive1TooCloseBottom);
	}

	/**
	 * Transition ist zu nah an einem Place (Überlagerung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_toClose_OverlayPlace() {
		emptyJung.createPlace(place2, pointPositive1);
		emptyJung.createTransition(transition1, pointPositive1);
	}

	/**
	 * Transition ist zu nah an einem Place (Innerhalb der Mindestentfernung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_toClose_TooCloseLeftPlace() {
		emptyJung.createPlace(place2, pointPositive1);
		emptyJung.createTransition(transition1, pointPositive1TooCloseLeft);
	}

	/**
	 * Transition ist zu nah an einem Place (Innerhalb der Mindestentfernung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_toClose_TooCloseRightPlace() {
		emptyJung.createPlace(place2, pointPositive1);
		emptyJung.createTransition(transition1, pointPositive1TooCloseRight);
	}

	/**
	 * Transition ist zu nah an einem Place (Innerhalb der Mindestentfernung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_toClose_TooCloseTopPlace() {
		emptyJung.createPlace(place2, pointPositive1);
		emptyJung.createTransition(transition1, pointPositive1TooCloseTop);
	}

	/**
	 * Transition ist zu nah an einem Place (Innerhalb der Mindestentfernung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_createTransition_toClose_TooCloseBottomPlace() {
		emptyJung.createPlace(place2, pointPositive1);
		emptyJung.createTransition(transition1, pointPositive1TooCloseBottom);
	}

	/**
	 * Place nicht im Graphen enthalten
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_getPlaceColor_unknownPlace() {
		emptyJung.getPlaceColor(place1);
	}

	/**
	 * Place nicht im Graphen enthalten
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_setPlaceColor_1() {
		emptyJung.setPlaceColor(place1, Color.BLACK);
	}

	/**
	 * Arc nicht im Graphen enthalten
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_delete_unknownArc() {
		Set<Arc> arcs = new HashSet<Arc>();
		Set<INode> nodes = new HashSet<INode>();

		arcs.add(arc11);

		emptyJung.delete(arcs, nodes);
	}

	/**
	 * INode nicht im Graphen enthalten
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_delete_unknownINode() {
		Set<Arc> arcs = new HashSet<Arc>();
		Set<INode> nodes = new HashSet<INode>();

		nodes.add(place1);

		emptyJung.delete(arcs, nodes);
	}

	/**
	 * Beim Node löschen werden Kanten vergessen
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_delete_missingArcsToDelete() {
		Set<Arc> arcs = new HashSet<Arc>();
		Set<INode> nodes = new HashSet<INode>();

		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.createTransition(transition1, pointPositive2);
		emptyJung.createArc(arc11, place1, transition1);

		nodes.add(place1);

		emptyJung.delete(arcs, nodes);
	}

	/**
	 * Position durch negativen X-Wert ungültig
	 */
	// There is no IllegalArgumentException expected here any more as nodes can
	// be moved into negativ positions
	// @Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_moveNode_NegativeX() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.moveNodeWithPositionCheck(place1, pointNegativeX);
	}

	/**
	 * Position durch negativen Y-Wert ungültig
	 */
	// There is no IllegalArgumentException expected here any more as nodes can
	// be moved into negativ positions
	// @Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_moveNode_NegativeY() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.moveNodeWithPositionCheck(place1, pointNegativeY);
	}

	/**
	 * Position durch negative X und Y-Werte ungültig
	 */
	// There is no IllegalArgumentException expected here any more as nodes can
	// be moved into negativ positions
	// @Test(expected=IllegalArgumentException.class)
	public void testInvalidArguments_moveNode_NegativeXY() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.moveNodeWithPositionCheck(place1, pointNegativeXY);
	}

	/**
	 * Node nicht im Graphen enthalten
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_moveNode_UnknownNode() {
		emptyJung.moveNodeWithPositionCheck(place1, pointPositive1);
	}

	/**
	 * Node kommt zu nah an einen anderen Node (Überlagerung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_moveNode_Overlay() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.createPlace(place2, pointPositive2);
		emptyJung.moveNodeWithPositionCheck(place2, pointPositive1);
	}

	/**
	 * Node kommt zu nah an einen anderen Node (Innerhalb der Mindestentfernung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_moveNode_TooCloseLeft() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.createPlace(place2, pointPositive2);
		emptyJung.moveNodeWithPositionCheck(place2, pointPositive1TooCloseLeft);
	}

	/**
	 * Node kommt zu nah an einen anderen Node (Innerhalb der Mindestentfernung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_moveNode_TooCloseRight() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.createPlace(place2, pointPositive2);
		emptyJung
				.moveNodeWithPositionCheck(place2, pointPositive1TooCloseRight);
	}

	/**
	 * Node kommt zu nah an einen anderen Node (Innerhalb der Mindestentfernung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_moveNode_TooCloseTop() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.createPlace(place2, pointPositive2);
		emptyJung.moveNodeWithPositionCheck(place2, pointPositive1TooCloseTop);
	}

	/**
	 * Node kommt zu nah an einen anderen Node (Innerhalb der Mindestentfernung)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments_moveNode_TooCloseBottom() {
		emptyJung.createPlace(place1, pointPositive1);
		emptyJung.createPlace(place2, pointPositive2);
		emptyJung.moveNodeWithPositionCheck(place2,
				pointPositive1TooCloseBottom);
	}
}
