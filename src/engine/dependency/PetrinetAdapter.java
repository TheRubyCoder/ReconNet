package engine.dependency;

import petrinet.Petrinet;
import petrinet.PetrinetComponent;

/** Adapter to petrinet component */
public class PetrinetAdapter {
	
	private PetrinetAdapter() {}
	
	public static Petrinet createPetrinet() {
		return PetrinetComponent.getPetrinet().createPetrinet();
	}
	
	public static Petrinet getPetrinetById(int id){
		return PetrinetComponent.getPetrinet().getPetrinetById(id);
	}

}
