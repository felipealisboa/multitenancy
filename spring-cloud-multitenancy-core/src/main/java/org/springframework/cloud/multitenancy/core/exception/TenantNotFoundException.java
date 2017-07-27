package org.springframework.cloud.multitenancy.core.exception;

/**
 *  Throw when the library did not receive the tenant
 * 
 * @author WRP
 */
public class TenantNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TenantNotFoundException(String message) {
		super(message);
	}

	public TenantNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
