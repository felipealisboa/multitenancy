package org.springframework.cloud.multitenancy.mongo.datasource;

import com.mongodb.DB;

/**
 * 
 * @author WRP
 * */

@FunctionalInterface
public interface MultitenancyMongoDataSource {
	
	DB getDataSourceWithCurrentTenant();
}
