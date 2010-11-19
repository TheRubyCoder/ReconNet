package petrinetze;

public class RenewId implements IRenew {

	@Override
	public String renew(String tlb) {
		return tlb;
	}

	@Override
	public boolean isTlbValid(String tlb) {
		return true;
	}

}
