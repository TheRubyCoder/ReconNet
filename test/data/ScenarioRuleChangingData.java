package data;

import petrinetze.IPlace;
import petrinetze.ITransition;
import transformation.IRule;
import transformation.Rule;

public class ScenarioRuleChangingData {

	public static IRule getRuleScenario1() {
		return emptyRule;
	}

	public static IRule getRuleScenario2() {
		return emptyRule;
	}

	public static IRule getRuleScenario3() {
		return emptyRule;
	}

	public static IRule getRuleScenario4() {
		return ruleWithOnePlaceInLKR;
	}

	public static IRule getRuleScenario5() {
		return ruleWithOnePlaceInLKR;
	}

	public static IRule getRuleScenario6() {
		return ruleWithOnePlaceInLKR;
	}

	public static IRule getRuleScenario7() {
		return ruleWithOnePlaceAndOneTransition;
	}

	public static IRule getRuleScenario8() {
		return ruleWithOnePlaceAndOneTransition;
	}

	public static IRule getRuleScenario9() {
		return ruleWithOnePlaceAndOneTransition;
	}
	
	public static IRule getRuleScenario13() {
		return ruleWithOnePlaceInLKR;
	}
	
	public static IRule getRuleScenario14() {
		return ruleWithOnePlaceInLKR;
	}
	
	public static IRule getRuleScenario15() {
		return ruleWithOnePlaceInLKR;
	}

	private static IRule emptyRule;

	private static IRule ruleWithOnePlaceInLKR;

	private static IRule ruleWithOnePlaceAndOneTransition;

	static {
		clearAllRuleChanges();
	}

	/**
	 * Initiating all scenario rules like specified in
	 * /../additional/images/rule_changing_scenarios.png
	 */
	public static void clearAllRuleChanges() {
		emptyRule = new Rule();

		ruleWithOnePlaceInLKR = new Rule();
		ruleWithOnePlaceInLKR.getK().createPlace("P1");

		ruleWithOnePlaceAndOneTransition = new Rule();
		IPlace placeInK = ruleWithOnePlaceAndOneTransition.getK().createPlace(
				"P1");
		ITransition transitionInK = ruleWithOnePlaceAndOneTransition.getK()
				.createTransition("A");
		ruleWithOnePlaceAndOneTransition.getK().createArc("", placeInK,
				transitionInK);
	}

}
