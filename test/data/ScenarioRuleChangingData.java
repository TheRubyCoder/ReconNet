package data;

import petrinet.model.Place;
import petrinet.model.Transition;
import transformation.Rule;
import transformation.TransformationComponent;

public class ScenarioRuleChangingData {

	public static Rule getRuleScenario1() {
		return emptyRule;
	}

	public static Rule getRuleScenario2() {
		return emptyRule;
	}

	public static Rule getRuleScenario3() {
		return emptyRule;
	}

	public static Rule getRuleScenario4() {
		return ruleWithOnePlaceInLKR;
	}

	public static Rule getRuleScenario5() {
		return ruleWithOnePlaceInLKR;
	}

	public static Rule getRuleScenario6() {
		return ruleWithOnePlaceInLKR;
	}

	public static Rule getRuleScenario7() {
		return ruleWithOnePlaceAndOneTransition;
	}

	public static Rule getRuleScenario8() {
		return ruleWithOnePlaceAndOneTransition;
	}

	public static Rule getRuleScenario9() {
		return ruleWithOnePlaceAndOneTransition;
	}
	
	public static Rule getRuleScenario13() {
		return ruleWithOnePlaceInLKR;
	}
	
	public static Rule getRuleScenario14() {
		return ruleWithOnePlaceInLKR;
	}
	
	public static Rule getRuleScenario15() {
		return ruleWithOnePlaceInLKR;
	}

	private static Rule emptyRule;

	private static Rule ruleWithOnePlaceInLKR;

	private static Rule ruleWithOnePlaceAndOneTransition;

	static {
		clearAllRuleChanges();
	}

	/**
	 * Initiating all scenario rules like specified in
	 * /../additional/images/rule_changing_scenarios.png
	 */
	public static void clearAllRuleChanges() {
		emptyRule = TransformationComponent.getTransformation().createRule();

		ruleWithOnePlaceInLKR = TransformationComponent.getTransformation().createRule();
		ruleWithOnePlaceInLKR.getK().addPlace("P1");

		ruleWithOnePlaceAndOneTransition = TransformationComponent.getTransformation().createRule();
		Place placeInK = ruleWithOnePlaceAndOneTransition.getK().addPlace(
				"P1");
		Transition transitionInK = ruleWithOnePlaceAndOneTransition.getK()
				.addTransition("A");
		ruleWithOnePlaceAndOneTransition.getK().addPreArc("", placeInK,
				transitionInK);
	}

}
