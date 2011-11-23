package petrinet;

public final class UUID {
	private static int id = 0;
	
	/**
	 * @return the pID
	 */
	public static synchronized int getpID() {
		return ++id;
	}

	/**
	 * @return the tID
	 */
	public static synchronized int gettID() {
		return ++id;
	}

	/**
	 * @return the nID
	 */
	public static synchronized int getnID() {
		return ++id;
	}

	/**
	 * @return the aID
	 */
	public static synchronized int getaID() {
		return ++id;
	}
}
