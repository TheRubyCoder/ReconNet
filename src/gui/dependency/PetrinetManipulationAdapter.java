package gui.dependency;


import petrinet.Petrinet;
import transformation.Rule;
import engine.Engine;
import engine.EngineComponent;

public class PetrinetManipulationAdapter {

	private PetrinetManipulationAdapter() {}
	
	public static Petrinet createPetrinet() {
		return engine.EngineComponent.getEngine().createPetrinet();
//		return PetrinetComponent.getPetrinet().createPetrinet();
	}
	
	public static Rule createRule(){
		return engine.EngineComponent.getEngine().createRule();
	}
	
}
