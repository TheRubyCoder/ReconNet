package engine.handler;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import engine.handler.petrinet.PetrinetManipulation;
import engine.ihandler.IPetrinetManipulation;

public class PetrinetHandlerTest {
	
	private static IPetrinetManipulation petrinetManipulation;
	
	// ids from all Petrinet
	private static int idPetrinet1 = -1;
	private int idPetrinet2 = -1;
	private int idPetrinet3 = -1;
	private int idPetrinet4 = -1;
	private int idPetrinet5 = -1;

	@Before
	public void setUp() throws Exception {
		
		petrinetManipulation = PetrinetManipulation.getInstance();
		
	}

	@Test
	public void testCreatePetrinet() {
		
		idPetrinet1 = petrinetManipulation.createPetrinet();
		assertTrue(idPetrinet1 != -1);
		idPetrinet2 = petrinetManipulation.createPetrinet();
		assertTrue(idPetrinet2 != -1);
		idPetrinet3 = petrinetManipulation.createPetrinet();
		assertTrue(idPetrinet3 != -1);
		idPetrinet4 = petrinetManipulation.createPetrinet();
		assertTrue(idPetrinet4 != -1);
		idPetrinet5 = petrinetManipulation.createPetrinet();
		assertTrue(idPetrinet5 != -1);
		
	}

	@Test
	public void testCreatePlace() {
		assertTrue(true);
	}

	@Test
	public void testCreateTransition() {
		assertTrue(true);
	}

	@Test
	public void testCreateArc() {
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
