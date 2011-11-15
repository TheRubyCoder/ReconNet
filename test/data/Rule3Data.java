package data;

import petrinetze.IPetrinet;
import petrinetze.IPlace;
import transformation.IRule;
import transformation.Rule;

public class Rule3Data {
	
	
	private static IPetrinet nPetrinet = MorphismData.getPetrinetIsomorphismPlacesTo();

	private static IRule rule = new Rule();
	
	private static IPlace newPlace;

	public static IPetrinet getnPetrinet() {
		return nPetrinet;
	}

	public static IRule getRule() {
		return rule;
	}
	
	public static IPlace getNewPlace(){
		return newPlace;
	}
	
	static {
		addSubnetToPetrinet(rule.getL());
	}

	private static void addSubnetToPetrinet(IPetrinet petrinet) {
		newPlace = petrinet.createPlace("NewPlace");
	}


}
