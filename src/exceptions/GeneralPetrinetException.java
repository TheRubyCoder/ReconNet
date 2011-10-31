package exceptions;

/*
 * The purpose of this class is simply to distinguish between our and system's Exceptions 
 * 
 */

public class GeneralPetrinetException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7975950243756669720L;

	public GeneralPetrinetException() {
		// TODO Auto-generated constructor stub
	}

	public GeneralPetrinetException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public GeneralPetrinetException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public GeneralPetrinetException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * This Constructor is add-on in JAVA7
	 */
/*
	public GeneralPetrinetException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
*/
}
