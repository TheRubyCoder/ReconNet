package petrinet;


public class RenewId implements IRenew {

	@Override
	public String renew(String tlb) {
		return tlb;
	}

	@Override
	public boolean isTlbValid(String tlb) {
		return true;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return this == obj || (obj != null && obj.getClass() == getClass());
	}
	
}
