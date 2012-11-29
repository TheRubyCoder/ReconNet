package data;

import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.Transition;
import transformation.Rule;
import transformation.TransformationComponent;

public class Rule2Data {
	
	public static int getIdOfMatchedPlace(){
		return MorphismData.getIdMatchesInRule2();
	}
	
	public static Petrinet getnPetrinet() {
		return nPetrinet;
	}

	public static Rule getRule() {
		return rule;
	}
	
	private static Petrinet nPetrinet = MorphismData.getPetrinetIsomorphismPlacesTo();

	private static Rule rule = TransformationComponent.getTransformation().createRule();
	
	static {
		addSubnetToPetrinet(rule.getL());
		rule.setMarkInL(rule.getL().getPlaces().iterator().next(), 3);
	}
	
	private static void addSubnetToPetrinet(Petrinet petrinet){
		Place place = petrinet.addPlace("P1");
		for (int i = 0; i < 3; i++) {
			Transition transition = petrinet.addTransition("A");
			petrinet.addPreArc("", place, transition);
		}
		for (int i = 0; i < 2; i++) {
			Transition transition = petrinet.addTransition("A");
			petrinet.addPostArc("", transition, place);
		}
	}

}
