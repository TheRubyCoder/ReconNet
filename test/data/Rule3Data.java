package data;

import petrinet.Petrinet;
import petrinet.Place;
import transformation.Rule;
import transformation.TransformationComponent;

public class Rule3Data {
	
	
	private static Petrinet nPetrinet = MorphismData.getPetrinetIsomorphismPlacesTo();

	private static Rule rule = TransformationComponent.getTransformation().createRule();
	
	private static Place newPlace;

	public static Petrinet getnPetrinet() {
		return nPetrinet;
	}

	public static Rule getRule() {
		return rule;
	}
	
	public static Place getNewPlace(){
		return newPlace;
	}
	
	static {
		addSubnetToPetrinet(rule.getR());
	}

	private static void addSubnetToPetrinet(Petrinet petrinet) {
		newPlace = petrinet.createPlace("NewPlace");
	}


}
