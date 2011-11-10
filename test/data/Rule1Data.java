package data;

import petrinetze.IPetrinet;
import petrinetze.IPlace;
import petrinetze.ITransition;
import transformation.IRule;
import transformation.Rule;

/**
 * Contains test data for TestCase "Rule_1" <br/>
 * @see /..additional/images/rule_1.png
 *
 */
public class Rule1Data {
	
	public static IPetrinet getnPetrinet() {
		return nPetrinet;
	}

	public static IRule getRule() {
		return rule;
	}
	
	private static IPetrinet nPetrinet = MorphismData.getPetrinetIsomorphismPlacesTo();

	private static IRule rule = new Rule();
	
	static {
		addSubnetToPetrinet(rule.getK());
	}
	
	private static void addSubnetToPetrinet(IPetrinet petrinet){
		IPlace place = petrinet.createPlace("P1");
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
