package data;

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

	private static IRule emptyRule;
	
	static{
		clearAllRuleChanges();
	}
	

	/** Initiating all scenario rules like specified in
	 * /../additional/images/rule_changing_scenarios.png
	 */
	public static void clearAllRuleChanges(){
		emptyRule = new Rule();
	}

}
