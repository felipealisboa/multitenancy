package org.springframework.cloud.multitenancy.core.properties;

/**
 * Loads the settings of how to locate the tenant
 * 
 * @author WRP
 * */
public class TenantConfigProperties {
	
	private String field;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}	
}
