package org.springframework.cloud.multitenancy.core.exception;

/**
 * Throw when a property has not been configured in the application.properties file
 * 
 * @author
 */
public class PropertyNotSetException  extends RuntimeException  {

	private static final long serialVersionUID = -9116854662780969807L;

	public PropertyNotSetException(String message){
		super(message);
	}
}
