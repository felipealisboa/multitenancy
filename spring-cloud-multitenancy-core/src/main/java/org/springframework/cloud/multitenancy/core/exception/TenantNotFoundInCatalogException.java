package org.springframework.cloud.multitenancy.core.exception;

/**
 *  Throw when the library did not find the database when consulting the catalog
 * @author WRP
 */
public class TenantNotFoundInCatalogException extends RuntimeException {


	private static final long serialVersionUID = 1L;
	
	public TenantNotFoundInCatalogException(String message){
		super(message);
	}
}
