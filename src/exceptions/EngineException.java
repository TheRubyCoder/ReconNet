package exceptions;

/**
 * 
 * This is a Exception for the Engine.
 * 
 * @author alex (aas772)
 *
 */
public class EngineException extends Exception {

	private static final long serialVersionUID = 11039582039523852L;
	
	/**
	 * 
	 * 
	 * 
	 * @param message
	 *  
	 */
	public EngineException(String message){
		super(message);
	}

}
