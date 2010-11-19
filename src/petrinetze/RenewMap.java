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

}
