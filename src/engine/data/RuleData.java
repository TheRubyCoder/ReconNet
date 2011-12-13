package engine.data;

import transformation.Rule;

/**
 * 
 * This Class is a Datacontainer for a Rule and all Petrinet in this Rule.
 * 
 * @author alex (aas772)
 *
 */

public class RuleData {
	
	private Rule rule;
	private JungData jungDataL;
	private JungData jungDataK;
	private JungData jungDataR;
	
	public RuleData(Rule rule, JungData lJungData, JungData kJungData, JungData rJungData){
		this.rule = rule;
		this.jungDataL = lJungData;
		this.jungDataK = kJungData;
		this.jungDataR = rJungData;
	}
	
	/**
	 * Gets a Rule.  
	 * 
	 * @return Rule
	 */
	public Rule getRule(){
		return rule;
	}
	
	/**
	 * Gets the JungData of L from a Rule
	 * 
	 * @return JungData
	 */
	public JungData getLJungData(){
		return jungDataL;
	}
	
	/**
	 * Gets the JungData of K from a Rule
	 * 
	 * @return JungData
	 */
	public JungData getKJungData(){
		return jungDataK;
	}
	
	/**
	 * Gets the JungData of R from a Rule
	 * 
	 * @return JungData
	 */
	public JungData getRJungData(){
		return jungDataR;
	}

}
