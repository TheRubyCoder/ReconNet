package transformation.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import petrinetze.IPetrinet;
import transformation.IRule;
import transformation.Transformations;
import data.Rule1Data;
import exceptions.GeneralPetrinetException;

public class Rule1Test {
	
	private static IPetrinet nPetrinet = Rule1Data.getnPetrinet();
	
	private static IRule rule = Rule1Data.getRule();
	
	@Test
	public void testApplyingRule(){
		try {
			//try 100 times as there are 4 possible morphisms
			for(int i = 0; i < 100; i++){
				//Because the reference stays the same after transformation
				//nPetrinet will always equals itself, no matter what happens in transformation
				//so we check the toString() of its pre-matrix instead
				String preBefore = nPetrinet.getPre().toString();
				String postBefore = nPetrinet.getPost().toString();
				Transformations.transform(nPetrinet, rule);
				String preAfter = nPetrinet.getPre().toString();
				String postAfter = nPetrinet.getPost().toString();
				assertEquals(preBefore, preAfter);
				assertEquals(postBefore, postAfter);
			}
			System.out.println(nPetrinet.getPre().matrixStringOnly());
		} catch (GeneralPetrinetException e) {
			fail("Morphism should have been found");
		}
	}

}
