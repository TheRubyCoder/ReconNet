package exceptions;

/**
 * This Exception is an unchecked Exception that should be used to make an error
 * occur as information to the user. (In an information pop up)
 */
public class ShowAsInfoException extends RuntimeException {

	private static final long serialVersionUID = -2991964878517327070L;

	public ShowAsInfoException(String message) {
		super(message);
	}

	public ShowAsInfoException(Throwable t) {
		super(t);
	}
}
