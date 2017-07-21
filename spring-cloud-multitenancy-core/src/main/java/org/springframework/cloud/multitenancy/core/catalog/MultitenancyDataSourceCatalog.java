package org.springframework.cloud.multitenancy.core.catalog;

/**
 * Database catalog
 * 
 * @author WRP
 * */
public interface MultitenancyDataSourceCatalog {
	
	public MultitenancyTenantInformation getDataSourceByTenant(String tenant);	
}
