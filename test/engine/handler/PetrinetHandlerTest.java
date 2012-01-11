package engine.handler;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.junit.Before;
import org.junit.Test;

import petrinet.INode;
import engine.handler.petrinet.PetrinetPersistence;
import engine.ihandler.IPetrinetPersistence;
import exceptions.EngineException;

public class PetrinetHandlerTest {

	private IPetrinetPersistence petrinetHandler;
	
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

	@Before
	public void setUp() throws Exception {

		petrinetHandler = PetrinetPersistence.getInstance();
		
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

		idPetrinet = petrinetHandler.createPetrinet();
		assertTrue(idPetrinet != -1);

	}

	@Test
	public void testCreatePlace() {

		idPetrinet = petrinetHandler.createPetrinet();
		if(idPetrinet == -1) fail("testCreatePlace: idPetrinet == -1");
		
		try {
			
			place1 = petrinetHandler.createPlace(idPetrinet, pointPlace1);
			assertNotNull(place1);
			
			place2 = petrinetHandler.createPlace(idPetrinet, pointPlace2);
			assertNotNull(place2);
			
			place3 = petrinetHandler.createPlace(idPetrinet, pointPlace3);
			assertNotNull(place3);
			
			place4 = petrinetHandler.createPlace(idPetrinet, pointPlace4);
			assertNotNull(place4);

		} catch (EngineException e) {

			// if you this test.. something is wrong..!
			assertTrue(false);

		}

	}

	@Test
	public void testCreateTransition() {

		idPetrinet = petrinetHandler.createPetrinet();
		if(idPetrinet == -1) fail("testCreatePlace: idPetrinet == -1");
		
		try {
			
			transition1 = petrinetHandler.createTransition(idPetrinet, pointTransition1);
			assertNotNull(transition1);
			
			transition2 = petrinetHandler.createTransition(idPetrinet, pointTransition2);
			assertNotNull(transition2);
			
			transition3 = petrinetHandler.createTransition(idPetrinet, pointTransition3);
			assertNotNull(transition3);
			
			transition4 = petrinetHandler.createTransition(idPetrinet, pointTransition4);
			assertNotNull(transition4);
								
		} catch (EngineException e) {

			// if you this test.. something is wrong..!
			assertTrue(false);

		}
		
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
			assertTrue(true);
		}
		
		try {
			// wrong Point => Point(-1, -1)
			petrinetHandler.createTransition(idPetrinet, new Point2D.Double(-1., -1.));
			
			fail("something is wrong (createTransition): wrong Point => Point(-1, -1)");
			
		} catch (EngineException e) {
			assertTrue(true);
		}

	}

	@Test
	public void testCreateArc() {
		
//		petrinetManipulation.createArc(idPetrinet, from, to)
		
		assertTrue(true);
	}

	@Test
	public void testDeleteArc() {
		assertTrue(true);
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

}
