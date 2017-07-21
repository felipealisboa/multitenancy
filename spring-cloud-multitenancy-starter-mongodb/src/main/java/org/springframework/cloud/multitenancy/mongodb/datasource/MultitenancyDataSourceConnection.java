package org.springframework.cloud.multitenancy.mongodb.datasource;

import com.mongodb.DB;

public interface MultitenancyDataSourceConnection {
	
	public DB open(String tenant);
}
