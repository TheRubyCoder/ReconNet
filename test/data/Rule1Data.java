package data;

import petrinet.Petrinet;
import petrinet.Place;
import petrinet.Transition;
import transformation.Rule;
import transformation.TransformationComponent;

/**
 * Contains test data for TestCase "Rule_1" <br/>
 * @see /..additional/images/rule_1.png
 *
 */
public class Rule1Data {
	
	public static Petrinet getnPetrinet() {
		return nPetrinet;
	}

	public static Rule getRule() {
		return rule;
	}
	
	private static Petrinet nPetrinet = MorphismData.getPetrinetIsomorphismPlacesTo();

	
	
	
	private static Rule rule = TransformationComponent.getTransformation().createRule();
	
	static {
		addSubnetToPetrinet(rule.getK());
	}
	
	private static void addSubnetToPetrinet(Petrinet petrinet){
		Place place = petrinet.createPlace("P1");
		for (int i = 0; i < 2; i++) {
			Transition transition = petrinet.createTransition("A");
			petrinet.createArc("", place, transition);
		}
		for (int i = 0; i < 3; i++) {
			Transition transition = petrinet.createTransition("A");
			petrinet.createArc("", transition, place);
		}
	}

}
