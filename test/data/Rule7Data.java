package data;

import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.Transition;
import transformation.Rule;
import transformation.TransformationComponent;

public class Rule7Data {
	
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
	}
	
	private static void addSubnetToPetrinet(Petrinet petrinet){
		Place place = petrinet.addPlace("P1");
		place.setMark(4);
		for (int i = 0; i < 2; i++) {
			Transition transition = petrinet.addTransition("A");
			petrinet.addPreArc("", place, transition);
		}
		for (int i = 0; i < 3; i++) {
			Transition transition = petrinet.addTransition("A");
			petrinet.addPostArc("", transition, place);
		}
	}

}
