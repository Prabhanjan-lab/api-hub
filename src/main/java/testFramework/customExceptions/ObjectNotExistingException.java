/**
 * 
 */
package testFramework.customExceptions;

/**
 * @author Bege
 *
 */
public class ObjectNotExistingException extends Exception {

	public ObjectNotExistingException() {
		super();
	}
	
	public ObjectNotExistingException (String message) {
		super(message);
	}
	
}
