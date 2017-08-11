package org.springframework.cloud.multitenancy.mysql.catalog;

/**
 * Database catalog
 * 
 * @author WRP
 * */
@FunctionalInterface
public interface MultitenancyMySQLDataSourceCatalog {
	
	public MultitenancyMySQLTenantInformation getDataSourceByTenant(String tenant);	
}
