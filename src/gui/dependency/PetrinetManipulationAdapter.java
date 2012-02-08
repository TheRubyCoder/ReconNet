package gui.dependency;


import petrinet.Petrinet;
import transformation.Rule;
import transformation.Transformation;
import engine.dependency.PetrinetAdapter;
import engine.dependency.TransformationAdapter;

public class PetrinetManipulationAdapter {

	private PetrinetManipulationAdapter() {}
	
	public static Petrinet createPetrinet() {
		return PetrinetAdapter.createPetrinet();
	}
	
	public static Rule createRule(){
		return TransformationAdapter.createRule();
	}
	
	public static Transformation transform(Petrinet petrinet, Rule rule){
		return TransformationAdapter.transform(petrinet, rule);
	}
	
}
