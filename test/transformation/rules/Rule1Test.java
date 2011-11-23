package transformation.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import petrinet.Petrinet;
import transformation.Rule;
import transformation.TransformationComponent;
import data.Rule1Data;
import exceptions.GeneralPetrinetException;

/**
 * Testing if Rule 1 like specified in /../additional/images/Rule_1.png
 * works correctly
 *
 */
public class Rule1Test {
	
	private static Petrinet nPetrinet = Rule1Data.getnPetrinet();
	
	private static Rule rule = Rule1Data.getRule();
	
	@Test
	public void testApplyingRule(){
		try {
			//try 100 times as there are 4 possible morphisms
			for(int i = 0; i < 100; i++){
				//Because the reference stays the same after transformation
				//nPetrinet will always equals itself, no matter what happens in transformation
				//so we check the toString() of its pre-matrix instead
				String preBefore = nPetrinet.getPre().matrixStringOnly();
				String postBefore = nPetrinet.getPost().matrixStringOnly();
				TransformationComponent.transform(nPetrinet, rule);
				String preAfter = nPetrinet.getPre().matrixStringOnly();
				String postAfter = nPetrinet.getPost().matrixStringOnly();
				assertEquals(preBefore, preAfter);
				assertEquals(postBefore, postAfter);
			}
		} catch (GeneralPetrinetException e) {
			fail("Morphism should have been found");
		}
	}

}
