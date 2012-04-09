package exceptions;

public class ShowAsInfoException extends RuntimeException{

	private static final long serialVersionUID = -2991964878517327070L;

	public ShowAsInfoException(String message) {
		super(message);
	}
	
	public ShowAsInfoException(Throwable t) {
		super(t);
	}
}
