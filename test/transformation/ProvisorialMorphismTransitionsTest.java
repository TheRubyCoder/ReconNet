package transformation;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import petrinetze.IPetrinet;
import petrinetze.ITransition;
import data.MorphismData;

/**
 * Testing the morphism of places like specified in
 * "../additional/images/Isomorphism_transitions.png"
 */
public class ProvisorialMorphismTransitionsTest {

	private static IPetrinet transitionFromNet = MorphismData
			.getPetrinetIsomorphismTransitionsFrom();

	private static IPetrinet transitionToNet = MorphismData
			.getPetrinetIsomorphismTransitionsTo();

	private static ITransition fromTransition;
	private static Map<ITransition, Integer> counter;

	@BeforeClass
	public static void setUpOnce() throws Exception {

		System.out.println(transitionFromNet);
		System.out.println(transitionToNet);
		// Get the first (and only) transition in the "from" net
		fromTransition = transitionFromNet.getAllTransitions().iterator()
				.next();

		counter = new HashMap<ITransition, Integer>();

		ITransition targetTransition;
		// try 100 morphism and count them
		for (int i = 0; i < 20; i++) {
			IMorphism morphism = MorphismFactory.createMorphism(
					transitionFromNet, transitionToNet);
			targetTransition = morphism.getTransitionMorphism(fromTransition);

			if (counter.containsKey(targetTransition)) {
				counter.put(targetTransition, counter.get(targetTransition) + 1);
			} else {
				counter.put(targetTransition, 1);
			}
		}
	}

	@Test
	public void testPlacesMorphismCount() {

		// only 1 morphisms should have been possible
		System.out.println(counter);
		assertEquals(1, counter.size());
	}

	@Test
	public void testPlacesMorphismMatchingDistribution() {
		// since its not deterministic we check if its approximately equally
		// often machted
		int average = 20;
		int allowedDeviation = 0;
		int max = average + allowedDeviation;
		int min = average - allowedDeviation;
		for (Integer count : counter.values()) {
			boolean accepted = min <= count && count <= max;
			if (!accepted)
				System.out
						.println("Should have matched 20 times but it matched "
								+ count + " times.");
			assertEquals(true, accepted);
		}
	}

	@Test
	public void testPlacesMorphismCorrectMatches() {
		/*
		 * see if we got the right transition. 
		 * id=1 is in "from". 
		 * id=2 should be matched in "to"
		 */
		assertEquals(1, fromTransition.getId());

		Set<Integer> expectedMatches = new HashSet<Integer>();
		expectedMatches.add(2);

		Set<Integer> actualMatches = new HashSet<Integer>();
		for (ITransition transition : counter.keySet()) {
			actualMatches.add(transition.getId());
		}
		assertEquals(expectedMatches, actualMatches);

	}

}
