package exceptions;

/**
 * Utility class for dealing with exceptions. Offers "shortcuts" for throwing
 * various exceptions. use "static import" to use them in application code
 * handily
 */
public class Exceptions {
	
	private Exceptions() {
		// utility class
	}
	
	/** Throws an {@link EngineException} with the <code>message</code> */
	public static void exception(String message) throws EngineException {
		throw new EngineException(message);
	}
	
	/** Throws an {@link ShowAsWarningException} with the <code>message</code> */
	public static void warning(String message) throws ShowAsWarningException {
		throw new ShowAsWarningException(message);
	}
	
	/** Throws an {@link ShowAsInfoException} with the <code>message</code> */
	public static void info(String message) throws ShowAsInfoException {
		throw new ShowAsInfoException(message);
	}
	
	/**
	 * Throws an {@link EngineException} with a <code>message</code> if
	 * <code>check</code> is <code>true</code>
	 */
	public static void exceptionIf(boolean check, String errorMessage)
			throws EngineException {
		if (check) {
			exception(errorMessage);
		}
	}

}
