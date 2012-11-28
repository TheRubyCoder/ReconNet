package transformation.dependency;

import petrinet.PetrinetComponent;
import petrinet.model.Petrinet;

public class PetrinetAdapter {
	
	private PetrinetAdapter() {}
	
	public static Petrinet createPetrinet(){
		return PetrinetComponent.getPetrinet().createPetrinet();
	}

}
