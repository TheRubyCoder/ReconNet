package transformation;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import petrinet.Arc;
import petrinet.Petrinet;
import petrinet.Place;
import data.MorphismData;

/**
 * Testing the morphism of places like specified in "../additional/images/Isomorphism_places.png"
 */
public class MorphismPlacesTest {
	
	private static int morphismCount = 2000;

	private static Petrinet placesFromNet = MorphismData
			.getPetrinetIsomorphismPlacesFrom();

	private static Petrinet placesToNet = MorphismData
			.getPetrinetIsomorphismPlacesTo();

	private static Place fromPlace;
	private static Map<Place, Integer> counter;

	@BeforeClass
	public static void setUpOnce() throws Exception {
		// Get the first (and only) place in the "from" net
		fromPlace = placesFromNet.getAllPlaces().iterator().next();

		counter = new HashMap<Place, Integer>();

		Place targetPlace;
		// try 100 morphism and count them
		for (int i = 0; i < morphismCount; i++) {
			Morphism morphism = MorphismFactory.createMorphism(placesFromNet,
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
		int average = morphismCount / 4;
		int allowedDeviation = average / 2;
		int max = average + allowedDeviation;
		int min = average - allowedDeviation;
		for (Integer count : counter.values()) {
			boolean accepted = min < count && count < max;
			if (!accepted)
				System.out
						.println("This test failed due to non determinism. Its not too bad it failed.\n" +
								"It should have been between " + min + " and " + max + " but was " + count);
			assertEquals(true, accepted);
		}
	}

	@Test
	public void testPlacesMorphismCorrectMatches() {
		assertEquals(MorphismData.getIdFromPlaces(), fromPlace.getId());

		Set<Integer> expectedMatches = MorphismData.getIdsMatchedPlaces();
		
		Set<Integer> actualMatches = new HashSet<Integer>();
		for (Place place : counter.keySet()) {
			actualMatches.add(place.getId());
		}
		assertEquals(expectedMatches, actualMatches);
	}
	
	@Test
	public void testStupidMethodToGetCodeCoverageForGetters(){
		Morphism morphism = MorphismFactory.createMorphism(placesFromNet, placesToNet);
		Arc arc = placesFromNet.getAllArcs().iterator().next();
		morphism.getArcMorphism(arc);
		
		morphism.getEdgesMorphism();
		
		morphism.getFrom();
		
		morphism.getPlacesMorphism();
		
		morphism.getTo();
		
		morphism.getTransitionsMorphism();
	}
}
