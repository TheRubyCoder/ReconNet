package engine.attribute;

/**
 * 
 * This Class holds all Id's (Petrinet ID) of a rule.
 *
 */

public class RuleAttribute {
	
	private int lId;
	private int kId;
	private int rId;
	
	public RuleAttribute(int lId, int kId, int rId){
		this.lId = lId;
		this.kId = kId;
		this.rId = rId;
	}

	public int getLId(){
		return lId;
	}
	
	public int getKId(){
		return kId;
	}
	
	public int getRId(){
		return rId;
	}
	
}
