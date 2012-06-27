package engine.attribute;

import petrinet.IRenew;

/**
 * 
 * This Class holds all Information for a Transition. (Which is label, name,
 * renew and whether it is active)
 * 
 * @author alex (aas772)
 * 
 */

public class TransitionAttribute {

	private String tlb;
	private String tname;
	private IRenew rnw;
	private boolean isActivated;

	public TransitionAttribute(String tlb, String tname, IRenew rnw,
			boolean isActivated) {
		this.tlb = tlb;
		this.tname = tname;
		this.rnw = rnw;
		this.isActivated = isActivated;
	}

	public String getTLB() {
		return tlb;
	}

	public String getTname() {
		return tname;
	}

	public IRenew getRNW() {
		return rnw;
	}

	public boolean getIsActivated() {
		return isActivated;
	}

}
