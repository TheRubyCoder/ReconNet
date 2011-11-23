package transformation.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import petrinet.Petrinet;
import petrinet.Place;

import transformation.Rule;
import transformation.Transformation;
import transformation.Transformations;
import data.Rule3Data;
import exceptions.GeneralPetrinetException;


/**
 * Testing if Rule 3 like specified in /../additional/images/Rule_3.png works
 * correctly
 * 
 */
public class Rule3Test {
	
	/** petrinet to transform */
	private static Petrinet nPetrinet = Rule3Data.getnPetrinet();
	/** rule to apply */
	private static Rule rule = Rule3Data.getRule();
	/** added place need for check equals*/
	private static Place newPlace = Rule3Data.getNewPlace();

	private static Transformation transformation;

	@BeforeClass
	public static void applyingRule() {
		try {
			transformation = Transformations.transform(nPetrinet, rule);
		} catch (GeneralPetrinetException e) {
			System.out.println(e);
			fail("Morphism should not be found, but no error should be raised.");
		}
	}
	
	@Test
	public void testRightMorphism() {
		// Has only 1 place been mapped?
		assertEquals(0, transformation.getMorphism().getPlacesMorphism().size());
		// Has 5 transitions been mapped?
		assertEquals(0, transformation.getMorphism().getTransitionsMorphism()
				.size());
		assertEquals(newPlace.getId(), transformation.getPetrinet().getPlaceById(newPlace.getId()));
		
		//the added Place has no outgoing and incoming Arcs
		Place addedPlace = transformation.getPetrinet().getPlaceById(newPlace.getId());
		
		assertTrue(addedPlace.getStartArcs().isEmpty());
		assertTrue(addedPlace.getEndArcs().isEmpty());
		
		//The count of Places with no incoming and outcoing arcs should be one
		List<Place> placesWithZeroArcs = new ArrayList<Place>();
		
		for (Place place : transformation.getPetrinet().getAllPlaces()) {
			if(place.getEndArcs().isEmpty() && place.getStartArcs().isEmpty()){
				placesWithZeroArcs.add(place);
			}
		}
		
		assertEquals(1, placesWithZeroArcs.size());
	}
	


}
