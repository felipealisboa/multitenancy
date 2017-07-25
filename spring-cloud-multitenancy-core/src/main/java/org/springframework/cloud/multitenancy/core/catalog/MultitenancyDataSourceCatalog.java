package org.springframework.cloud.multitenancy.core.catalog;

/**
 * Database catalog
 * 
 * @author WRP
 * */
@FunctionalInterface
public interface MultitenancyDataSourceCatalog {
	
	public MultitenancyTenantInformation getDataSourceByTenant(String tenant);	
}
