package petrinetze;

import java.util.HashMap;
import java.util.Map;


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
		return getClass().hashCode() ^ rnw.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        return this.rnw.equals(((RenewMap)obj).rnw);
	}
}
