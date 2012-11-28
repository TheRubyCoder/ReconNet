package petrinet.model;

/**
 * A {@link IRenew renew object} basically consists of the renew function
 * and a {@link IRenew#renew(String) renew method} to change the label
 * of a transition when it is fired
 */
public interface IRenew {

	public String renew(String tlb);

	boolean isTlbValid(String tlb);

	String toGUIString();
}
