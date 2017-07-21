package org.springframework.cloud.multitenancy.core.exception;

/**
 * @author WRP
 * 
 */
public class NotIdentifyTenantFieldException extends RuntimeException {


	private static final long serialVersionUID = 1L;
	
	public NotIdentifyTenantFieldException(String message){
		super(message);
	}
}
