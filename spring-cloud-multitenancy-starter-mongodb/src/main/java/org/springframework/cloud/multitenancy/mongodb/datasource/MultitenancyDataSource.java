package org.springframework.cloud.multitenancy.mongodb.datasource;

import com.mongodb.DB;

/**
 * 
 * @author WRP
 * */

@FunctionalInterface
public interface MultitenancyDataSource {
	
	DB getDataSourceWithCurrentTenant();
}
