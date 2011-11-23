package gui.dependency;


import petrinet.Petrinet;
import transformation.Rule;
import transformation.Transformation;
import engine.Engine;
import engine.EngineComponent;

public class PetrinetManipulationAdapter {

	private PetrinetManipulationAdapter() {}
	
	public static Petrinet createPetrinet() {
		return engine.EngineComponent.getPetrinetManipulation().createPetrinet();
	}
	
	public static Rule createRule(){
		return EngineComponent.getPetrinetManipulation().createRule();
	}
	
	public static Transformation transform(Petrinet petrinet, Rule rule){
		return EngineComponent.getPetrinetManipulation().transform(petrinet, rule);
	}
	
}
