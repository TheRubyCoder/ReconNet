package petrinetze.impl;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashSet;

import org.junit.*;

import petrinet.Petrinet;
import petrinet.PetrinetComponent;

import data.MorphismData;

/** Testing the not void delete method */
public class DeleteTest {
	
	private static Petrinet nPetrinet;
	
	private static int toDelete;
	
	private static Collection<Integer> deleted;
	
	@Test
	public void checkReturnValue(){
		nPetrinet = MorphismData.getPetrinetIsomorphismPlacesTo();
		toDelete = MorphismData.getIdMatchesInRule2();
		System.out.println(toDelete);
		
		deleted = PetrinetComponent.getPetrinet().deleteElementInPetrinet(nPetrinet.getId(), toDelete);
		assertEquals(6,deleted.size());
		assertEquals(MorphismData.getIdsOfPlaceAndArcsOfThirdPlace(),
				new HashSet<Integer>(deleted));
	}
	
	

}
