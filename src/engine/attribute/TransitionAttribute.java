package engine.attribute;

import petrinet.IRenew;

/**
 * 
 * This Class holds all Information for a Transition.
 * 
 * @author alex (aas772)
 *
 */

public class TransitionAttribute {

	private String tlb;
	private String tname;
	private IRenew rnw;
	
	public TransitionAttribute(String tlb, String tname, IRenew rnw){
		this.tlb = tlb;
		this.tname = tname;
		this.rnw = rnw;
	}
	
	public String getTLB(){
		return tlb;
	}
	
	public String getTname(){
		return tname;
	}
	
	public IRenew getRNW(){
		return rnw;
	}
	
}
