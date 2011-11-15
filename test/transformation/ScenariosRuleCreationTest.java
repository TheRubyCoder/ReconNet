package transformation;

import org.junit.Before;
import org.junit.Test;

import petrinetze.IPetrinet;
import petrinetze.IPlace;

import static data.ScenarioRuleChangingData.*;
import static org.junit.Assert.*;

public class ScenariosRuleCreationTest {

	@Before
	public void resetChanges() {
		clearAllRuleChanges();
	}

	/** creating in k */
	@Test
	public void scenario1() {
		IPetrinet k = getRuleScenario1().getK();
		IPetrinet l = getRuleScenario1().getL();
		IPetrinet r = getRuleScenario1().getR();

		// user action
		k.createPlace("P1");

		IPlace firstInK = k.getAllPlaces().iterator().next();
		IPlace firstInL = l.getAllPlaces().iterator().next();
		IPlace firstInR = r.getAllPlaces().iterator().next();

		// all have "P1" ?
		assertEquals("P1", firstInK.getName());
		assertEquals("P1", firstInL.getName());
		assertEquals("P1", firstInR.getName());

		// all equal?
		assertEquals(k, l);
		assertEquals(k, r);
	}

	/** creating in l */
	@Test
	public void scenario2() {
		IPetrinet k = getRuleScenario2().getK();
		IPetrinet l = getRuleScenario2().getL();
		IPetrinet r = getRuleScenario2().getR();

		// user action
		l.createPlace("P1");

		IPlace firstInK = k.getAllPlaces().iterator().next();
		IPlace firstInL = l.getAllPlaces().iterator().next();

		// L and K have "P1"?
		assertEquals("P1", firstInK.getName());
		assertEquals("P1", firstInL.getName());
		// L and K equal?
		assertEquals(k, l);
		// R not equal?
		assertFalse("R should not equal K or L", k.equals(r));
		// R empty?
		assertTrue("R should be empty", r.getAllPlaces().isEmpty());
		assertTrue("R should be empty", r.getAllTransitions().isEmpty());
	}

	/** creating in r */
	@Test
	public void scenario3() {
		IPetrinet k = getRuleScenario3().getK();
		IPetrinet l = getRuleScenario3().getL();
		IPetrinet r = getRuleScenario3().getR();

		// user action
		r.createPlace("P1");

		IPlace firstInK = k.getAllPlaces().iterator().next();
		IPlace firstInR = r.getAllPlaces().iterator().next();

		// R and K have "P1"?
		assertEquals("P1", firstInK.getName());
		assertEquals("P1", firstInR.getName());
		// R and K equal?
		assertEquals(k, r);
		// L not equal?
		assertFalse("L should not equal K or R", k.equals(l));
		// L empty?
		assertTrue("L should be empty", l.getAllPlaces().isEmpty());
		assertTrue("L should be empty", l.getAllTransitions().isEmpty());
	}

	/** deleting in k */
	@Test
	public void scenario4() {
		IPetrinet k = getRuleScenario4().getK();
		IPetrinet l = getRuleScenario4().getL();
		IPetrinet r = getRuleScenario4().getR();
		int id = k.getAllPlaces().iterator().next().getId();

		// user action
		k.deletePlaceById(id);

		// L, K and R are equal?
		assertEquals(l, k);
		assertEquals(r, k);

		// L, K and R are empty?
		assertTrue(l.getAllPlaces().isEmpty());
		assertTrue(k.getAllPlaces().isEmpty());
		assertTrue(r.getAllPlaces().isEmpty());

		assertTrue(l.getAllTransitions().isEmpty());
		assertTrue(k.getAllTransitions().isEmpty());
		assertTrue(r.getAllTransitions().isEmpty());
	}

	/** deleting in l */
	@Test
	public void scenario5() {
		IPetrinet k = getRuleScenario5().getK();
		IPetrinet l = getRuleScenario5().getL();
		IPetrinet r = getRuleScenario5().getR();
		int id = l.getAllPlaces().iterator().next().getId();

		// user action
		l.deletePlaceById(id);

		// L and K are equal?
		assertEquals(l, k);
		// R different?
		assertFalse(r.equals(k));
		assertFalse(r.equals(l));

		// L and K are empty?
		assertTrue(l.getAllPlaces().isEmpty());
		assertTrue(k.getAllPlaces().isEmpty());
		assertTrue(l.getAllTransitions().isEmpty());
		assertTrue(k.getAllTransitions().isEmpty());

		// R not empty?
		assertEquals(1, r.getAllPlaces().size());
		assertEquals(0, r.getAllTransitions().size());
	}

	/** deleting in r */
	@Test
	public void scenario6() {
		IPetrinet k = getRuleScenario6().getK();
		IPetrinet l = getRuleScenario6().getL();
		IPetrinet r = getRuleScenario6().getR();
		int id = r.getAllPlaces().iterator().next().getId();

		// user action
		r.deletePlaceById(id);

		// R and K are equal?
		assertEquals(r, k);
		// L different?
		assertFalse(l.equals(k));
		assertFalse(l.equals(r));

		// R and K are empty?
		assertTrue(r.getAllPlaces().isEmpty());
		assertTrue(k.getAllPlaces().isEmpty());
		assertTrue(r.getAllTransitions().isEmpty());
		assertTrue(k.getAllTransitions().isEmpty());

		// L not empty?
		assertEquals(1, l.getAllPlaces().size());
		assertEquals(0, l.getAllTransitions().size());
	}

	/** deleting transition in "complex" k */
	@Test
	public void scenario7() {
		IPetrinet k = getRuleScenario7().getK();
		IPetrinet l = getRuleScenario7().getL();
		IPetrinet r = getRuleScenario7().getR();
		int id = k.getAllPlaces().iterator().next().getId();

		// user action
		k.deletePlaceById(id);

		// L, K and R are equal?
		assertEquals(l, k);
		assertEquals(r, k);

		// L, K and R have no places?
		assertTrue(l.getAllPlaces().isEmpty());
		assertTrue(k.getAllPlaces().isEmpty());
		assertTrue(r.getAllPlaces().isEmpty());

		// L, K and R have one transition?
		assertEquals(1, l.getAllTransitions().size());
		assertEquals(1, k.getAllTransitions().size());
		assertEquals(1, r.getAllTransitions().size());

		// L, K and R have no arcs?
		assertTrue(l.getAllArcs().isEmpty());
		assertTrue(k.getAllArcs().isEmpty());
		assertTrue(r.getAllArcs().isEmpty());
	}

	/** deleting transition in "complex" l */
	@Test
	public void scenario8() {
		IPetrinet k = getRuleScenario8().getK();
		IPetrinet l = getRuleScenario8().getL();
		IPetrinet r = getRuleScenario8().getR();
		int id = l.getAllPlaces().iterator().next().getId();

		// user action
		l.deletePlaceById(id);

		// L and K are equal?
		assertEquals(l, k);
		// R different?
		assertFalse(r.equals(k));
		assertFalse(r.equals(l));

		// L and K have no places?
		assertTrue(l.getAllPlaces().isEmpty());
		assertTrue(k.getAllPlaces().isEmpty());
		
		// R has 1 place?
		assertEquals(1, r.getAllPlaces().size());

		// L, K and R have one transition?
		assertEquals(1, l.getAllTransitions().size());
		assertEquals(1, k.getAllTransitions().size());
		assertEquals(1, r.getAllTransitions().size());

		// L and K have no arcs?
		assertTrue(l.getAllArcs().isEmpty());
		assertTrue(k.getAllArcs().isEmpty());
		
		// R has one arc?
		assertEquals(1,r.getAllArcs().size());
	}
	
	/** deleting transition in "complex" r */
	@Test
	public void scenario9() {
		IPetrinet k = getRuleScenario9().getK();
		IPetrinet l = getRuleScenario9().getL();
		IPetrinet r = getRuleScenario9().getR();
		int id = r.getAllPlaces().iterator().next().getId();

		// user action
		r.deletePlaceById(id);

		// R and K are equal?
		assertEquals(r, k);
		// L different?
		assertFalse(l.equals(k));
		assertFalse(l.equals(r));

		// R and K have no places?
		assertTrue(r.getAllPlaces().isEmpty());
		assertTrue(k.getAllPlaces().isEmpty());
		
		// L has 1 place?
		assertEquals(1, l.getAllPlaces().size());

		// L, K and R have one transition?
		assertEquals(1, l.getAllTransitions().size());
		assertEquals(1, k.getAllTransitions().size());
		assertEquals(1, r.getAllTransitions().size());

		// R and K have no arcs?
		assertTrue(r.getAllArcs().isEmpty());
		assertTrue(k.getAllArcs().isEmpty());
		
		// L has one arc?
		assertEquals(1,l.getAllArcs().size());
	}
	
	/** changing mark in k */
	@Test
	public void scenario13() {
		IPetrinet k = getRuleScenario13().getK();
		IPetrinet l = getRuleScenario13().getL();
		IPetrinet r = getRuleScenario13().getR();
		int id = k.getAllPlaces().iterator().next().getId();

		// user action
		k.getPlaceById(id).setMark(3);

		IPlace firstInK = k.getAllPlaces().iterator().next();
		IPlace firstInL = l.getAllPlaces().iterator().next();
		IPlace firstInR = r.getAllPlaces().iterator().next();

		// all have "P1" ?
		assertEquals("P1", firstInK.getName());
		assertEquals("P1", firstInL.getName());
		assertEquals("P1", firstInR.getName());

		// all equal?
		assertEquals(k, l);
		assertEquals(k, r);
		
		// all have mark 3?
		assertEquals(3,firstInK.getMark());
		assertEquals(3,firstInL.getMark());
		assertEquals(3,firstInR.getMark());
	}
	
	/** changing mark in l */
	@Test
	public void scenario14() {
		IPetrinet k = getRuleScenario14().getK();
		IPetrinet l = getRuleScenario14().getL();
		IPetrinet r = getRuleScenario14().getR();
		int id = l.getAllPlaces().iterator().next().getId();

		// user action
		l.getPlaceById(id).setMark(3);

		IPlace firstInK = k.getAllPlaces().iterator().next();
		IPlace firstInL = l.getAllPlaces().iterator().next();
		IPlace firstInR = r.getAllPlaces().iterator().next();

		// all have "P1" ?
		assertEquals("P1", firstInK.getName());
		assertEquals("P1", firstInL.getName());
		assertEquals("P1", firstInR.getName());

		// L and K have mark 3?
		assertEquals(3,firstInK.getMark());
		assertEquals(3,firstInL.getMark());
		
		// R has mark 0
		assertEquals(0,firstInR.getMark());
	}

	/** changing mark in r */
	@Test
	public void scenario15() {
		IPetrinet k = getRuleScenario15().getK();
		IPetrinet l = getRuleScenario15().getL();
		IPetrinet r = getRuleScenario15().getR();
		int id = r.getAllPlaces().iterator().next().getId();

		// user action
		r.getPlaceById(id).setMark(3);

		IPlace firstInK = k.getAllPlaces().iterator().next();
		IPlace firstInL = l.getAllPlaces().iterator().next();
		IPlace firstInR = r.getAllPlaces().iterator().next();

		// all have "P1" ?
		assertEquals("P1", firstInK.getName());
		assertEquals("P1", firstInL.getName());
		assertEquals("P1", firstInR.getName());

		// R and K have mark 3?
		assertEquals(3,firstInK.getMark());
		assertEquals(3,firstInR.getMark());
		
		// L has mark 0
		assertEquals(0,firstInL.getMark());
	}
}
