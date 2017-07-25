package org.springframework.cloud.multitenancy.mongodb.datasource;

import com.mongodb.DB;

@FunctionalInterface
public interface MultitenancyDataSourceConnection {
	
	public DB open(String tenant);
}
