package petrinet;

class RenewToggle implements IRenew {
	

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
