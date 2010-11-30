package petrinetze.impl;

public final class UUID {
	private static int pID = 0;
	private static int tID = 0;
	private static int nID = 0;
	private static int aID = 0;
	
	/**
	 * @return the pID
	 */
	public static synchronized int getpID() {
		return ++pID;
	}

	/**
	 * @return the tID
	 */
	public static synchronized int gettID() {
		return ++tID;
	}

	/**
	 * @return the nID
	 */
	public static synchronized int getnID() {
		return ++nID;
	}

	/**
	 * @return the aID
	 */
	public static synchronized int getaID() {
		return ++aID;
	}
}
