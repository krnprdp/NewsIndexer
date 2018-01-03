/**
 * 
 */
package edu.buffalo.cse.irf14.document;

/**
 * @author Pradeep
 * Generic wrapper exception class for parsing exceptions
 */
public class ParserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4691717901217832517L;

	public ParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParserException(String message) {
		super(message);
	}
}
