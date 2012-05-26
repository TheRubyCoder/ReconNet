package engine.handler;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.junit.Before;
import org.junit.Test;

import petrinet.Arc;
import petrinet.INode;
import engine.handler.petrinet.PetrinetManipulation;
import engine.handler.petrinet.PetrinetPersistence;
import engine.ihandler.IPetrinetManipulation;
import engine.ihandler.IPetrinetPersistence;
import exceptions.EngineException;
import exceptions.ShowAsWarningException;

public class PetrinetHandlerTest {

	private IPetrinetPersistence petrinetHandler = PetrinetPersistence
			.getInstance();
	private IPetrinetManipulation petrinetHandler2 = PetrinetManipulation
			.getInstance();

	// ids from all Petrinet *************************************************
	private int idPetrinet = -1;

	// Places & Places *******************************************************
	private Point2D pointPlace1 = null;
	private Point2D pointPlace2 = null;
	private Point2D pointPlace3 = null;
	private Point2D pointPlace4 = null;

	private INode place1 = null;
	private INode place2 = null;
	private INode place3 = null;
	private INode place4 = null;

	// Transitions & Places **************************************************
	private Point2D pointTransition1 = null;
	private Point2D pointTransition2 = null;
	private Point2D pointTransition3 = null;
	private Point2D pointTransition4 = null;

	private INode transition1 = null;
	private INode transition2 = null;
	private INode transition3 = null;
	private INode transition4 = null;

	// Arcs ******************************************************************
	private Arc arc1;
	private Arc arc2;
	private Arc arc3;
	private Arc arc4;
	private Arc arc5;
	private Arc arc6;
	private Arc arc7;
	private Arc arc8;
	private Arc arc9;

	@Before
	public void setUp() throws Exception {

		pointPlace1 = new Point(10, 10);
		pointPlace2 = new Point(90, 10);
		pointPlace3 = new Point(10, 90);
		pointPlace4 = new Point(90, 90);

		pointTransition1 = new Point(45, 10);
		pointTransition2 = new Point(90, 45);
		pointTransition3 = new Point(10, 45);
		pointTransition4 = new Point(45, 90);

	}

	@Test
	public void testCreatePetrinet() {

		// init
		initPetrinet();

		// test
		assertTrue(idPetrinet != -1);

	}

	@Test
	public void testCreatePlace() {

		// init all
		initPetrinet();
		initPlace();

		// test
		assertNotNull(place1);
		assertNotNull(place2);
		assertNotNull(place3);
		assertNotNull(place4);

	}

	@Test
	public void testCreateTransition() {

		// init all
		initPetrinet();
		initTransition();

		// test
		assertNotNull(transition1);
		assertNotNull(transition2);
		assertNotNull(transition3);
		assertNotNull(transition4);

		try {

			// wrong id
			petrinetHandler.createTransition(-1, pointTransition1);

			fail("something is wrong (createTransition): wrong id");

		} catch (Exception e) {
			assertTrue(true);
		}

		try {

			// null as Point
			petrinetHandler.createTransition(idPetrinet, null);

			fail("something is wrong (createTransition): null as Point");
		} catch (EngineException e) {
			fail("No engine exception expected");
		} catch (ShowAsWarningException e) {
			assertTrue(true);
		}

		try {
			// wrong Point => Point(-1, -1)
			petrinetHandler.createTransition(idPetrinet, new Point2D.Double(
					-1., -1.));

			// Fail is no longer correct here. Transitions are now allowed to be
			// created in negative positions
			// fail("something is wrong (createTransition): wrong Point => Point(-1, -1)");
		} catch (EngineException e) {
			fail("No engine exception expected");
		} catch (ShowAsWarningException e) {
			assertTrue(true);
		}

	}

	@Test
	public void testCreateArc() {

		// init all
		initPetrinet();
		initPlace();
		initTransition();
		initArc();

		assertNotNull(arc1);
		assertNotNull(arc2);
		assertNotNull(arc3);
		assertNotNull(arc4);
		assertNotNull(arc5);
		assertNotNull(arc6);
		assertNotNull(arc7);
		assertNotNull(arc8);
		assertNotNull(arc9);

		// id is wrong
		try {

			petrinetHandler.createArc(-1, transition4, place4);

			fail("testCreateArc: id is wrong");

		} catch (Exception e) {
			assertTrue(true);
		}

		// from is wrong
		try {

			petrinetHandler.createArc(idPetrinet, null, place4);

			fail("testCreateArc: from is wrong");

		} catch (EngineException e) {
			assertTrue(true);
		}

		// to is wrong
		try {

			petrinetHandler.createArc(idPetrinet, place4, null);

			fail("testCreateArc: to is wrong");

		} catch (EngineException e) {
			assertTrue(true);
		}

	}

	@Test
	public void testDeleteArc() {

		// init all
		initPetrinet();
		initPlace();
		initTransition();
		initArc();

		// test
		try {

			petrinetHandler2.deleteArc(idPetrinet, arc1);
			petrinetHandler2.deleteArc(idPetrinet, arc2);
			petrinetHandler2.deleteArc(idPetrinet, arc3);
			petrinetHandler2.deleteArc(idPetrinet, arc4);
			petrinetHandler2.deleteArc(idPetrinet, arc5);
			petrinetHandler2.deleteArc(idPetrinet, arc6);
			petrinetHandler2.deleteArc(idPetrinet, arc7);
			petrinetHandler2.deleteArc(idPetrinet, arc8);
			petrinetHandler2.deleteArc(idPetrinet, arc9);

		} catch (EngineException e) {

			// if you this test.. something is wrong..!
			fail("testDeleteArc");

		}

		// delete one item two times
		try {

			petrinetHandler2.deleteArc(idPetrinet, arc1);

			fail("testDeleteArc: delete one item two times");

		} catch (EngineException e) {
			assertTrue(true);
		}

		// wrong id
		try {

			petrinetHandler2.deleteArc(-1, arc1);

			fail("testDeleteArc: wrong id");

		} catch (Exception e) {
			assertTrue(true);
		}

		// wrong Arc
		try {

			petrinetHandler2.deleteArc(idPetrinet, null);

			fail("testDeleteArc: wrong Arc");

		} catch (EngineException e) {
			fail("No EngineException expected");
		} catch (NullPointerException e) {
			assertTrue(true);
		}

	}

	@Test
	public void testDeletePlace() {
		assertTrue(true);
	}

	@Test
	public void testDeleteTransition() {
		assertTrue(true);
	}

	@Test
	public void testGetArcAttribute() {
		assertTrue(true);
	}

	@Test
	public void testGetJungLayout() {
		assertTrue(true);
	}

	@Test
	public void testGetPlaceAttribute() {
		assertTrue(true);
	}

	@Test
	public void testGetTransitionAttribute() {
		assertTrue(true);
	}

	@Test
	public void testMoveGraph() {
		assertTrue(true);
	}

	@Test
	public void testMoveNode() {
		assertTrue(true);
	}

	@Test
	public void testSave() {
		assertTrue(true);
	}

	@Test
	public void testLoad() {
		assertTrue(true);
	}

	@Test
	public void testSetMarking() {
		assertTrue(true);
	}

	@Test
	public void testSetPname() {
		assertTrue(true);
	}

	@Test
	public void testSetTlb() {
		assertTrue(true);
	}

	@Test
	public void testSetTname() {
		assertTrue(true);
	}

	@Test
	public void testSetWeight() {
		assertTrue(true);
	}

	@Test
	public void testGetNodeType() {
		assertTrue(true);
	}

	private void initPetrinet() {
		idPetrinet = petrinetHandler.createPetrinet();
	}

	private void initPlace() {
		try {

			place1 = petrinetHandler.createPlace(idPetrinet, pointPlace1);
			place2 = petrinetHandler.createPlace(idPetrinet, pointPlace2);
			place3 = petrinetHandler.createPlace(idPetrinet, pointPlace3);
			place4 = petrinetHandler.createPlace(idPetrinet, pointPlace4);

		} catch (EngineException e) {

			// if you this test.. something is wrong..!
			fail("testCreatePlace: can not create Place");

		}
	}

	private void initTransition() {
		try {

			transition1 = petrinetHandler.createTransition(idPetrinet,
					pointTransition1);
			transition2 = petrinetHandler.createTransition(idPetrinet,
					pointTransition2);
			transition3 = petrinetHandler.createTransition(idPetrinet,
					pointTransition3);
			transition4 = petrinetHandler.createTransition(idPetrinet,
					pointTransition4);

		} catch (EngineException e) {

			// if you this test.. something is wrong..!
			fail("testCreateTransition: can not create Transitions");

		}
	}

	private void initArc() {
		try {

			arc1 = petrinetHandler.createArc(idPetrinet, place1, transition1);
			arc2 = petrinetHandler.createArc(idPetrinet, place2, transition1);
			arc3 = petrinetHandler.createArc(idPetrinet, place3, transition1);
			arc4 = petrinetHandler.createArc(idPetrinet, place4, transition1);
			arc5 = petrinetHandler.createArc(idPetrinet, transition1, place1);
			arc6 = petrinetHandler.createArc(idPetrinet, transition2, place2);
			arc7 = petrinetHandler.createArc(idPetrinet, transition3, place2);
			arc8 = petrinetHandler.createArc(idPetrinet, transition3, place3);
			arc9 = petrinetHandler.createArc(idPetrinet, transition4, place4);

		} catch (EngineException e) {

			// if you this test.. something is wrong..!
			fail("testCreateArc");

		}
	}

}
