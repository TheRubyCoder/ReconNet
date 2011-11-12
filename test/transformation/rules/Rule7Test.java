package transformation.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import petrinetze.IPetrinet;
import petrinetze.IPlace;
import transformation.IRule;
import transformation.ITransformation;
import transformation.Transformations;
import data.Rule2Data;
import exceptions.GeneralPetrinetException;

public class Rule7Test {
	
	/** petrinet to transform */
	private static IPetrinet nPetrinet = Rule2Data.getnPetrinet();
	/** rule to apply */
	private static IRule rule = Rule2Data.getRule();

	private static String preBefore;
	private static String postBefore;
	private static ITransformation transformation;
	private static String preAfter;
	private static String postAfter;

	@Test
	public void testApplyingRule() {
		// Because the reference stays the same after transformation
		// nPetrinet will always equals itself, no matter what happens in
		// transformation
		// so we check the toString() of its pre-matrix instead
		preBefore = nPetrinet.getPre().matrixStringOnly();
		postBefore = nPetrinet.getPost().matrixStringOnly();
		try {
			transformation = Transformations.transform(nPetrinet, rule);
			fail("Morphism should have NOT been found");
		} catch (GeneralPetrinetException e) {
			//Transformation successfully failed :)
		}
		preAfter = nPetrinet.getPre().matrixStringOnly();
		postAfter = nPetrinet.getPost().matrixStringOnly();
		
		//Did the petrinet stayed untouched?
		assertEquals(preBefore, preAfter);
		assertEquals(postBefore, postAfter);
	}


}
