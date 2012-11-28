package petrinet.model;

/**
 * Utility class for generating unique IDs for nodes, arcs and petrinets
 * 
 */
public final class UUID {
	
	private UUID() {
		//utility class
	}
	
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
