package exceptions;

public class ShowAsWarningException extends RuntimeException{

	private static final long serialVersionUID = 3135396042658845975L;

	public ShowAsWarningException(String message){
		super(message);
	}
	
	public ShowAsWarningException(Throwable ex){
		super(ex);
	}
}
