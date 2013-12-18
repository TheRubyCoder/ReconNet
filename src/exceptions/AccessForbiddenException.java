package exceptions;

public class AccessForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 7481805579110388708L;
	
	public AccessForbiddenException(String msg) {
		super(msg);
	}
}
