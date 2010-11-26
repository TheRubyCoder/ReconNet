package petrinetze.impl;

import java.util.HashMap;
import java.util.Map;

import petrinetze.IRenew;

public class RenewMap implements IRenew {
	private final HashMap<String,String> rnw;
	
	public RenewMap(final Map<String, String> rnw) {
		this.rnw = new HashMap<String, String>(rnw);
	}
	
	@Override
	public String renew(String tlb) {
		return rnw.get(tlb);
	}

	@Override
	public boolean isTlbValid(String tlb) {
		return rnw.containsKey(tlb);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rnw == null) ? 0 : rnw.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RenewMap other = (RenewMap) obj;
		if (rnw == null) {
			if (other.rnw != null)
				return false;
		} else if (!rnw.equals(other.rnw))
			return false;
		return true;
	}
	
}
