package petrinet.model.rnw;

import petrinet.model.IRenew;

/**
 * Renew that switches the label from <code>true</code> to <code>false</code>
 * and vice versa. The Label is represented as {@link String}. The guiString is
 * "toggle"
 * 
 */
final public class Toggle implements IRenew {

	@Override
	public String renew(String tlb) {
		if (tlb.equalsIgnoreCase("true")) {
			return "false";
		} else {
			return "true";
		}
	}

	@Override
	public boolean isTlbValid(String tlb) {
		return tlb.equalsIgnoreCase("true") || tlb.equalsIgnoreCase("false");
	}

	@Override
	public String toGUIString() {
		return "toggle";
	}

}
