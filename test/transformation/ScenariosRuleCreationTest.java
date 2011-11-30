package transformation;

import org.junit.Before;
import org.junit.Test;

import petrinet.Petrinet;
import petrinet.Place;

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
		Petrinet k = getRuleScenario1().getK();
		Petrinet l = getRuleScenario1().getL();
		Petrinet r = getRuleScenario1().getR();

		// user action
		k.createPlace("P1");

		Place firstInK = k.getAllPlaces().iterator().next();
		Place firstInL = l.getAllPlaces().iterator().next();
		Place firstInR = r.getAllPlaces().iterator().next();

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
		Petrinet k = getRuleScenario2().getK();
		Petrinet l = getRuleScenario2().getL();
		Petrinet r = getRuleScenario2().getR();

		// user action
		l.createPlace("P1");

		Place firstInK = k.getAllPlaces().iterator().next();
		Place firstInL = l.getAllPlaces().iterator().next();

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
		Petrinet k = getRuleScenario3().getK();
		Petrinet l = getRuleScenario3().getL();
		Petrinet r = getRuleScenario3().getR();

		// user action
		r.createPlace("P1");

		Place firstInK = k.getAllPlaces().iterator().next();
		Place firstInR = r.getAllPlaces().iterator().next();

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
		Petrinet k = getRuleScenario4().getK();
		Petrinet l = getRuleScenario4().getL();
		Petrinet r = getRuleScenario4().getR();
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
		Petrinet k = getRuleScenario5().getK();
		Petrinet l = getRuleScenario5().getL();
		Petrinet r = getRuleScenario5().getR();
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
		Petrinet k = getRuleScenario6().getK();
		Petrinet l = getRuleScenario6().getL();
		Petrinet r = getRuleScenario6().getR();
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
		Petrinet k = getRuleScenario7().getK();
		Petrinet l = getRuleScenario7().getL();
		Petrinet r = getRuleScenario7().getR();
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
		Petrinet k = getRuleScenario8().getK();
		Petrinet l = getRuleScenario8().getL();
		Petrinet r = getRuleScenario8().getR();
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
		Petrinet k = getRuleScenario9().getK();
		Petrinet l = getRuleScenario9().getL();
		Petrinet r = getRuleScenario9().getR();
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
		Petrinet k = getRuleScenario13().getK();
		Petrinet l = getRuleScenario13().getL();
		Petrinet r = getRuleScenario13().getR();
		int id = k.getAllPlaces().iterator().next().getId();

		// user action
		TransformationComponent.getTransformation().setMark(getRuleScenario13(), id, 3);
		
		Place firstInK = k.getAllPlaces().iterator().next();
		Place firstInL = l.getAllPlaces().iterator().next();
		Place firstInR = r.getAllPlaces().iterator().next();

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
		Petrinet k = getRuleScenario14().getK();
		Petrinet l = getRuleScenario14().getL();
		Petrinet r = getRuleScenario14().getR();
		int id = l.getAllPlaces().iterator().next().getId();

		// user action
		TransformationComponent.getTransformation().setMark(getRuleScenario14(), id, 3);

		Place firstInK = k.getAllPlaces().iterator().next();
		Place firstInL = l.getAllPlaces().iterator().next();
		Place firstInR = r.getAllPlaces().iterator().next();

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
		Petrinet k = getRuleScenario15().getK();
		Petrinet l = getRuleScenario15().getL();
		Petrinet r = getRuleScenario15().getR();
		int id = r.getAllPlaces().iterator().next().getId();

		// user action
		TransformationComponent.getTransformation().setMark(getRuleScenario15(), id, 3);

		Place firstInK = k.getAllPlaces().iterator().next();
		Place firstInL = l.getAllPlaces().iterator().next();
		Place firstInR = r.getAllPlaces().iterator().next();

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
