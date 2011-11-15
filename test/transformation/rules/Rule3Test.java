package transformation.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import petrinetze.IPetrinet;
import petrinetze.IPlace;
import transformation.IRule;
import transformation.ITransformation;
import transformation.Transformations;
import data.Rule2Data;
import data.Rule3Data;
import exceptions.GeneralPetrinetException;


/**
 * Testing if Rule 3 like specified in /../additional/images/Rule_3.png works
 * correctly
 * 
 */
public class Rule3Test {
	
	/** petrinet to transform */
	private static IPetrinet nPetrinet = Rule3Data.getnPetrinet();
	/** rule to apply */
	private static IRule rule = Rule3Data.getRule();
	/** added place need for check equals*/
	private static IPlace newPlace = Rule3Data.getNewPlace();

	private static ITransformation transformation;

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
		IPlace addedPlace = transformation.getPetrinet().getPlaceById(newPlace.getId());
		
		assertTrue(addedPlace.getStartArcs().isEmpty());
		assertTrue(addedPlace.getEndArcs().isEmpty());
		
		//The count of Places with no incoming and outcoing arcs should be one
		List<IPlace> placesWithZeroArcs = new ArrayList<IPlace>();
		
		for (IPlace place : transformation.getPetrinet().getAllPlaces()) {
			if(place.getEndArcs().isEmpty() && place.getStartArcs().isEmpty()){
				placesWithZeroArcs.add(place);
			}
		}
		
		assertEquals(1, placesWithZeroArcs.size());
	}
	


}
