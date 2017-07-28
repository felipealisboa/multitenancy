package org.springframework.cloud.multitenancy.mongo.catalog;

/**
 * Database catalog
 * 
 * @author WRP
 * */
@FunctionalInterface
public interface MultitenancyMongoDataSourceCatalog {
	
	public MultitenancyMongoTenantInformation getDataSourceByTenant(String tenant);	
}
