package transformation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import petrinetze.IPetrinet;
import petrinetze.IPlace;
import data.MorphismData;

import static org.junit.Assert.assertEquals;

/**
 * Testing the morphism of places like specified in "../additional/images/Isomorphism_places.png"
 */
public class ProvisionalMorphismPlacesTest {

	private static IPetrinet placesFromNet = MorphismData
			.getPetrinetIsomorphismPlacesFrom();

	private static IPetrinet placesToNet = MorphismData
			.getPetrinetIsomorphismPlacesTo();

	private static IPlace fromPlace;
	private static Map<IPlace, Integer> counter;

	@BeforeClass
	public static void setUpOnce() throws Exception {
		// Get the first (and only) place in the "from" net
		fromPlace = placesFromNet.getAllPlaces().iterator().next();

		counter = new HashMap<IPlace, Integer>();

		IPlace targetPlace;
		// try 100 morphism and count them
		for (int i = 0; i < 100; i++) {
			IMorphism morphism = MorphismFactory.createMorphism(placesFromNet,
					placesToNet);
			targetPlace = morphism.getPlaceMorphism(fromPlace);

			if (counter.containsKey(targetPlace)) {
				counter.put(targetPlace, counter.get(targetPlace) + 1);
			} else {
				counter.put(targetPlace, 1);
			}
		}
	}

	@Test
	public void testPlacesMorphismCount() {

		// only 4 morphisms should have been possible
		assertEquals(4, counter.size());
	}

	@Test
	public void testPlacesMorphismMatchingDistribution() {
		// since its not deterministic we check if its approximately equally
		// often machted
		int average = 25;
		int allowedDeviation = 12;
		int max = average + allowedDeviation;
		int min = average - allowedDeviation;
		for (Integer count : counter.values()) {
			boolean accepted = min < count && count < max;
			assertEquals(true, accepted);
			if (!accepted)
				System.out
						.println("This test failed due to non determinism. Its not too bad it failed.");
		}
	}

	@Test
	public void testPlacesMorphismCorrectMatches() {
		/*
		 * see if we got the right places id=1 is in "from" ids=[2,4,7,8] should
		 * be matched in "to"
		 */
		assertEquals(1, fromPlace.getId());

		Set<Integer> expectedMatches = new HashSet<Integer>();
		expectedMatches.add(2);
		expectedMatches.add(4);
		expectedMatches.add(7);
		expectedMatches.add(8);
		
		Set<Integer> actualMatches = new HashSet<Integer>();
		for (IPlace place : counter.keySet()) {
			actualMatches.add(place.getId());
		}
		assertEquals(expectedMatches, actualMatches);

	}
}
