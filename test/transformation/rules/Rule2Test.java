package transformation.rules;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import petrinet.Petrinet;
import petrinet.Place;
import transformation.Rule;
import transformation.Transformation;
import transformation.TransformationComponent;
import data.Rule2Data;
import exceptions.GeneralPetrinetException;

/**
 * Testing if Rule 2 like specified in /../additional/images/Rule_2.png works
 * correctly
 * 
 */
public class Rule2Test {
	/** petrinet to transform */
	private static Petrinet nPetrinet = Rule2Data.getnPetrinet();
	/** rule to apply */
	private static Rule rule = Rule2Data.getRule();

	private static String preBefore;
	private static String postBefore;
	private static Transformation transformation;
	private static String preAfter;
	private static String postAfter;

	@BeforeClass
	public static void applyingRule() {
		// Because the reference stays the same after transformation
		// nPetrinet will always equals itself, no matter what happens in
		// transformation
		// so we check the toString() of its pre-matrix instead
		preBefore = nPetrinet.getPre().matrixStringOnly();
		postBefore = nPetrinet.getPost().matrixStringOnly();
		try {
			transformation = TransformationComponent.transform(nPetrinet, rule);
		} catch (GeneralPetrinetException e) {
			System.out.println(e);
			fail("Morphism should have been found");
		}
		preAfter = nPetrinet.getPre().matrixStringOnly();
		postAfter = nPetrinet.getPost().matrixStringOnly();
	}

	@Test
	public void testEquality() {
		assertFalse("The petrinet should have changed but were equal",
				preBefore.equals(preAfter));
		assertFalse("The petrinet should have changed but were equal",
				postBefore.equals(postAfter));
	}

	@Test
	public void testRightMorphism() {
		// Has only 1 place been mapped?
		assertEquals(1, transformation.getMorphism().getPlacesMorphism().size());
		// Has 5 transitions been mapped?
		assertEquals(5, transformation.getMorphism().getTransitionsMorphism()
				.size());

		// Right place matched?
		int idOfMatchedPlace = transformation.getMorphism().getPlacesMorphism()
				.values().iterator().next().getId();
		assertEquals(Rule2Data.getIdOfMatchedPlace(), idOfMatchedPlace);
	}

	@Test
	public void testChangesApplied() {
		// Only 6 places left?
		assertEquals(6, nPetrinet.getAllPlaces().size());
		// Only 29 transitions left?
		assertEquals(29, nPetrinet.getAllTransitions().size());
		// Only 29 arcs left?
		assertEquals(29, nPetrinet.getAllArcs().size());
		// right place deleted?
		for (Place place : nPetrinet.getAllPlaces()) {
			if (place.getId() == Rule2Data.getIdOfMatchedPlace()) {
				fail("the place that should have been deleted is still in the petrinet");
			}
		}
	}

}
