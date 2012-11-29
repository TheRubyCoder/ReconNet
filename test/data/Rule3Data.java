package data;

import petrinet.model.Petrinet;
import petrinet.model.Place;
import transformation.Rule;
import transformation.TransformationComponent;

public class Rule3Data {
	
	
	private static Petrinet nPetrinet = MorphismData.getPetrinetIsomorphismPlacesTo();

	private static Rule rule = TransformationComponent.getTransformation().createRule();
	
	private static Place newPlace;

	public static Petrinet getnPetrinet() {
		nPetrinet = MorphismData.getPetrinetIsomorphismPlacesTo();
		return nPetrinet;
	}

	public static Rule getRule() {
		return rule;
	}
	
	public static Place getNewPlace(){
		return newPlace;
	}
	
	static {
		addSubnetToPetrinet(rule);
	}

	private static void addSubnetToPetrinet(Rule rule) {
		newPlace = rule.addPlaceToR("NewPlace");
	}


}
