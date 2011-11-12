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

	@Test
	public void scenario1(){
		IPetrinet k = getRuleScenario1().getK();
		IPetrinet l = getRuleScenario1().getL();
		IPetrinet r = getRuleScenario1().getR();
		
		k.createPlace("P1");
		
		IPlace firstInK = k.getAllPlaces().iterator().next();
		IPlace firstInL = l.getAllPlaces().iterator().next();
		IPlace firstInR = r.getAllPlaces().iterator().next();
		
		//all have "P1" ?
		assertEquals("P1",firstInK.getName());
		assertEquals("P1",firstInL.getName());
		assertEquals("P1",firstInR.getName());
		
		//all equal?
		assertEquals(k,l);
		assertEquals(k,r);
	}
	
	@Test
	public void scenario2(){
		IPetrinet k = getRuleScenario2().getK();
		IPetrinet l = getRuleScenario2().getL();
		IPetrinet r = getRuleScenario2().getR();
		
		l.createPlace("P1");
		
		IPlace firstInK = k.getAllPlaces().iterator().next();
		IPlace firstInL = l.getAllPlaces().iterator().next();
		
		//L and K have "P1"?
		assertEquals("P1",firstInK.getName());
		assertEquals("P1",firstInL.getName());
		//L and K equal?
		assertEquals(k,l);
		//R not equal?
		assertFalse("R should not equal K or L",k.equals(r));
		//R empty?
		assertTrue("R should be empty",r.getAllPlaces().isEmpty());
		assertTrue("R should be empty",r.getAllTransitions().isEmpty());
	}
}
