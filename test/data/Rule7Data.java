package data;

import petrinetze.IPetrinet;
import petrinetze.IPlace;
import petrinetze.ITransition;
import transformation.IRule;
import transformation.Rule;

public class Rule7Data {
	
	public static IPetrinet getnPetrinet() {
		return nPetrinet;
	}

	public static IRule getRule() {
		return rule;
	}
	
	private static IPetrinet nPetrinet = MorphismData.getPetrinetIsomorphismPlacesTo();

	private static IRule rule = new Rule();
	
	static {
		addSubnetToPetrinet(rule.getL());
	}
	
	private static void addSubnetToPetrinet(IPetrinet petrinet){
		IPlace place = petrinet.createPlace("P1");
		place.setMark(4);
		for (int i = 0; i < 2; i++) {
			ITransition transition = petrinet.createTransition("A");
			petrinet.createArc("", place, transition);
		}
		for (int i = 0; i < 3; i++) {
			ITransition transition = petrinet.createTransition("A");
			petrinet.createArc("", transition, place);
		}
	}

}
