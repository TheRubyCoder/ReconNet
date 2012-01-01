package engine.data;

import transformation.Rule;

/**
 * This Class is a Datacontainer for a Rule and all Petrinet in this Rule.
 * 
 * @author alex (aas772)
 */

final public class RuleData implements SessionData {
	private int      id;
	private Rule     rule;
	private JungData jungDataL;
	private JungData jungDataK;
	private JungData jungDataR;
	
	private RuleData() {}
	
	public RuleData(int id, Rule rule, JungData lJungData, JungData kJungData, JungData rJungData){
		this.id      = id;
		this.rule      = rule;
		this.jungDataL = lJungData;
		this.jungDataK = kJungData;
		this.jungDataR = rJungData;
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	/**
	 * Gets the JungData of L from a Rule
	 * 
	 * @return JungData
	 */
	public JungData getLJungData() {
		return jungDataL;
	}
	
	/**
	 * Gets the JungData of K from a Rule
	 * 
	 * @return JungData
	 */
	public JungData getKJungData() {
		return jungDataK;
	}
	
	/**
	 * Gets the JungData of R from a Rule
	 * 
	 * @return JungData
	 */
	public JungData getRJungData() {
		return jungDataR;
	}
	
	/**
	 * Gets a Rule.  
	 * 
	 * @return Rule
	 */
	public Rule getRule(){
		return rule;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RuleData other = (RuleData) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
