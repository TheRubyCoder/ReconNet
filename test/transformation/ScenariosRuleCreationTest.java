package transformation;

import org.junit.Before;
import org.junit.Test;

import petrinet.model.Petrinet;
import petrinet.model.Place;

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
		getRuleScenario1().addPlaceToK("P1");
		
		Place firstInK = k.getPlaces().iterator().next();
		Place firstInL = l.getPlaces().iterator().next();
		Place firstInR = r.getPlaces().iterator().next();

		// all have "P1" ?
		assertEquals("P1", firstInK.getName());
		assertEquals("P1", firstInL.getName());
		assertEquals("P1", firstInR.getName());

		// all equal?
		assertTrue(k.equalsPetrinet(l));
		assertTrue(k.equalsPetrinet(r));
	}

	/** creating in l */
	@Test
	public void scenario2() {
		Petrinet k = getRuleScenario2().getK();
		Petrinet l = getRuleScenario2().getL();
		Petrinet r = getRuleScenario2().getR();

		// user action
		getRuleScenario2().addPlaceToL("P1");

		Place firstInK = k.getPlaces().iterator().next();
		Place firstInL = l.getPlaces().iterator().next();

		// L and K have "P1"?
		assertEquals("P1", firstInK.getName());
		assertEquals("P1", firstInL.getName());
		// L and K equal?
		assertTrue(k.equalsPetrinet(l));
		// R not equal?
		assertFalse("R should not equal K or L", k.equalsPetrinet(r));
		// R empty?
		assertTrue("R should be empty", r.getPlaces().isEmpty());
		assertTrue("R should be empty", r.getTransitions().isEmpty());
	}

	/** creating in r */
	@Test
	public void scenario3() {
		Petrinet k = getRuleScenario3().getK();
		Petrinet l = getRuleScenario3().getL();
		Petrinet r = getRuleScenario3().getR();

		// user action
		getRuleScenario3().addPlaceToR("P1");

		Place firstInK = k.getPlaces().iterator().next();
		Place firstInR = r.getPlaces().iterator().next();

		// R and K have "P1"?
		assertEquals("P1", firstInK.getName());
		assertEquals("P1", firstInR.getName());
		// R and K equal?
		assertTrue(k.equalsPetrinet(r));
		// L not equal?
		assertFalse("L should not equal K or R", k.equalsPetrinet(l));
		// L empty?
		assertTrue("L should be empty", l.getPlaces().isEmpty());
		assertTrue("L should be empty", l.getTransitions().isEmpty());
	}

	/** deleting in k */
	@Test
	public void scenario4() {
		Petrinet k = getRuleScenario4().getK();
		Petrinet l = getRuleScenario4().getL();
		Petrinet r = getRuleScenario4().getR();
		Place place = k.getPlaces().iterator().next();

		// user action
		getRuleScenario4().removePlaceFromK(place);

		// L, K and R are equal?
		assertTrue(l.equalsPetrinet(k));
		assertTrue(r.equalsPetrinet(k));

		// L, K and R are empty?
		assertTrue(l.getPlaces().isEmpty());
		assertTrue(k.getPlaces().isEmpty());
		assertTrue(r.getPlaces().isEmpty());

		assertTrue(l.getTransitions().isEmpty());
		assertTrue(k.getTransitions().isEmpty());
		assertTrue(r.getTransitions().isEmpty());
	}

	/** deleting in l */
	@Test
	public void scenario5() {
		Petrinet k = getRuleScenario5().getK();
		Petrinet l = getRuleScenario5().getL();
		Petrinet r = getRuleScenario5().getR();
		Place place = l.getPlaces().iterator().next();

		// user action
		getRuleScenario5().removePlaceFromL(place);

		// L and K are equal?
		assertTrue(l.equalsPetrinet(k));		
		assertTrue(r.equalsPetrinet(k));
		assertTrue(r.equalsPetrinet(l));

		// L and K are empty?
		assertTrue(l.getPlaces().isEmpty());
		assertTrue(k.getPlaces().isEmpty());
		assertTrue(r.getPlaces().isEmpty());
		assertTrue(l.getTransitions().isEmpty());
		assertTrue(k.getTransitions().isEmpty());
		assertTrue(r.getTransitions().isEmpty());
	}

	/** deleting in r */
	@Test
	public void scenario6() {
		Petrinet k = getRuleScenario6().getK();
		Petrinet l = getRuleScenario6().getL();
		Petrinet r = getRuleScenario6().getR();
		Place place = r.getPlaces().iterator().next();

		// user action
		getRuleScenario6().removePlaceFromR(place);
		

		// R and K are equal?
		assertTrue(r.equalsPetrinet(k));
		assertTrue(l.equalsPetrinet(k));
		assertTrue(l.equalsPetrinet(r));

		// R and K are empty?
		assertTrue(r.getPlaces().isEmpty());
		assertTrue(k.getPlaces().isEmpty());
		assertTrue(r.getPlaces().isEmpty());
		assertTrue(r.getTransitions().isEmpty());
		assertTrue(k.getTransitions().isEmpty());
		assertTrue(r.getTransitions().isEmpty());
	}

	/** deleting transition in "complex" k */
	@Test
	public void scenario7() {
		Petrinet k = getRuleScenario7().getK();
		Petrinet l = getRuleScenario7().getL();
		Petrinet r = getRuleScenario7().getR();
		Place place = k.getPlaces().iterator().next();

		// user action
		getRuleScenario7().removePlaceFromK(place);

		// L, K and R are equal?
		assertTrue(l.equalsPetrinet(k));
		assertTrue(r.equalsPetrinet(k));

		// L, K and R have no places?
		assertTrue(l.getPlaces().isEmpty());
		assertTrue(k.getPlaces().isEmpty());
		assertTrue(r.getPlaces().isEmpty());

		// L, K and R have one transition?
		assertEquals(1, l.getTransitions().size());
		assertEquals(1, k.getTransitions().size());
		assertEquals(1, r.getTransitions().size());

		// L, K and R have no arcs?
		assertTrue(l.getArcs().isEmpty());
		assertTrue(k.getArcs().isEmpty());
		assertTrue(r.getArcs().isEmpty());
	}

	/** deleting transition in "complex" l */
	@Test
	public void scenario8() {
		Petrinet k = getRuleScenario8().getK();
		Petrinet l = getRuleScenario8().getL();
		Petrinet r = getRuleScenario8().getR();
		Place place = l.getPlaces().iterator().next();

		// user action
		getRuleScenario8().removePlaceFromL(place);
		// L and K are equal?
		assertTrue(l.equalsPetrinet(k));
		assertTrue(r.equalsPetrinet(k));
		assertTrue(r.equalsPetrinet(l));

		// L, K and R have no places?
		assertTrue(l.getPlaces().isEmpty());
		assertTrue(k.getPlaces().isEmpty());
		assertTrue(r.getPlaces().isEmpty());

		// L, K and R have one transition?
		assertEquals(1, l.getTransitions().size());
		assertEquals(1, k.getTransitions().size());
		assertEquals(1, r.getTransitions().size());

		// L,K and R have no arcs?
		assertTrue(l.getArcs().isEmpty());
		assertTrue(k.getArcs().isEmpty());
		assertTrue(r.getArcs().isEmpty());
	}
	
	/** deleting transition in "complex" r */
	@Test
	public void scenario9() {
		Petrinet k = getRuleScenario9().getK();
		Petrinet l = getRuleScenario9().getL();
		Petrinet r = getRuleScenario9().getR();
		Place place = r.getPlaces().iterator().next();

		// user action
		getRuleScenario9().removePlaceFromR(place);
		
		// R and K are equal?
		assertTrue(r.equalsPetrinet(k));
		assertTrue(l.equalsPetrinet(k));
		assertTrue(l.equalsPetrinet(r));

		// L, R and K have no places?
		assertTrue(l.getPlaces().isEmpty());
		assertTrue(r.getPlaces().isEmpty());
		assertTrue(k.getPlaces().isEmpty());

		// L, K and R have one transition?
		assertEquals(1, l.getTransitions().size());
		assertEquals(1, k.getTransitions().size());
		assertEquals(1, r.getTransitions().size());

		// L, R and K have no arcs?
		assertTrue(l.getArcs().isEmpty());
		assertTrue(r.getArcs().isEmpty());
		assertTrue(k.getArcs().isEmpty());
	}
	
	/** changing mark in k */
	@Test
	public void scenario13() {
		Petrinet k = getRuleScenario13().getK();
		Petrinet l = getRuleScenario13().getL();
		Petrinet r = getRuleScenario13().getR();
		Place place =  k.getPlaces().iterator().next();

		// user action
		getRuleScenario13().setMarkInK(place, 3);
		
		Place firstInK = k.getPlaces().iterator().next();
		Place firstInL = l.getPlaces().iterator().next();
		Place firstInR = r.getPlaces().iterator().next();

		// all have "P1" ?
		assertEquals("P1", firstInK.getName());
		assertEquals("P1", firstInL.getName());
		assertEquals("P1", firstInR.getName());

		// all equal?
		assertTrue(k.equalsPetrinet(l));
		assertTrue(k.equalsPetrinet(r));
		
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
		Place place =  l.getPlaces().iterator().next();

		// user action
		getRuleScenario14().setMarkInL(place, 3);

		Place firstInK = k.getPlaces().iterator().next();
		Place firstInL = l.getPlaces().iterator().next();
		Place firstInR = r.getPlaces().iterator().next();

		// all have "P1" ?
		assertEquals("P1", firstInK.getName());
		assertEquals("P1", firstInL.getName());
		assertEquals("P1", firstInR.getName());

		// R, L and K have mark 3?
		assertEquals(3,firstInK.getMark());
		assertEquals(3,firstInL.getMark());
		assertEquals(3,firstInR.getMark());
	}

	/** changing mark in r */
	@Test
	public void scenario15() {
		Petrinet k = getRuleScenario15().getK();
		Petrinet l = getRuleScenario15().getL();
		Petrinet r = getRuleScenario15().getR();
		Place place =  r.getPlaces().iterator().next();

		// user action
		getRuleScenario15().setMarkInR(place, 3);

		Place firstInK = k.getPlaces().iterator().next();
		Place firstInL = l.getPlaces().iterator().next();
		Place firstInR = r.getPlaces().iterator().next();

		// all have "P1" ?
		assertEquals("P1", firstInK.getName());
		assertEquals("P1", firstInL.getName());
		assertEquals("P1", firstInR.getName());

		// R and K have mark 3?
		assertEquals(3,firstInK.getMark());
		assertEquals(3,firstInR.getMark());
		assertEquals(3,firstInL.getMark());
	}
}
