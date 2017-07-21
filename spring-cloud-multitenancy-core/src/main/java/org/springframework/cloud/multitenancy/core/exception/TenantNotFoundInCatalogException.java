package org.springframework.cloud.multitenancy.core.exception;

/**
 * @author WRP
 * 
 */
public class TenantNotFoundInCatalogException extends RuntimeException {


	private static final long serialVersionUID = 1L;
	
	public TenantNotFoundInCatalogException(String message){
		super(message);
	}
}
