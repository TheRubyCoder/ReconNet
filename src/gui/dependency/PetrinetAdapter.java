package gui.dependency;

import petrinet.Petrinet;
import petrinet.PetrinetComponent;

public class PetrinetAdapter {

	private PetrinetAdapter() {}
	
	public static Petrinet createPetrinet() {
		return PetrinetComponent.getPetrinet().createPetrinet();
	}
	
}
