package org.springframework.cloud.multitenancy.stream.exception;

/**
 * @author WRP
 * 
 */
public class TenantNotFoundException extends RuntimeException {


	private static final long serialVersionUID = 1L;
	
	public TenantNotFoundException(String message){
		super(message);
	}
}
