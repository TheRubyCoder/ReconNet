package engine.dependency;

import petrinet.PetrinetComponent;
import petrinet.model.Petrinet;

/** Adapter to petrinet component */
public class PetrinetAdapter {
	
	private PetrinetAdapter() {}
	
	public static Petrinet createPetrinet() {
		return PetrinetComponent.getPetrinet().createPetrinet();
	}
	
	public static Petrinet getPetrinetById(int id){
		return PetrinetComponent.getPetrinet().getPetrinet(id);
	}

}
