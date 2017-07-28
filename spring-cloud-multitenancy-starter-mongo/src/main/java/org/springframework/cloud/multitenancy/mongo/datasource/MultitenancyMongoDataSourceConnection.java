package org.springframework.cloud.multitenancy.mongo.datasource;

import com.mongodb.DB;

@FunctionalInterface
public interface MultitenancyMongoDataSourceConnection {
	
	public DB open(String tenant);
}
