package exceptions;

/**
 * This Exception is an unchecked Exception that should be used to make an error
 * occur as warning to the user. (In an information pop up)
 */
public class ShowAsWarningException extends RuntimeException{

	private static final long serialVersionUID = 3135396042658845975L;

	public ShowAsWarningException(String message){
		super(message);
	}
	
	public ShowAsWarningException(Throwable ex){
		super(ex);
	}
}
