package petrinetze;

public class RenewCount implements IRenew {

	@Override
	public String renew(String tlb) {
		
		int c = Integer.parseInt(tlb);
		c++;
		return String.valueOf(c);
	}

	@Override
	public boolean isTlbValid(String tlb) {
		try {
			Integer.parseInt(tlb);
			return true;
		} catch (NumberFormatException exc) {
			return false;
		}
	}

}
