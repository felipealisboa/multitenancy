package org.springframework.cloud.multitenancy.mongodb.datasource;

import com.mongodb.DB;

/**
 * 
 * @author WRP
 * */
public interface MultitenancyDataSource {
	
	DB getDataSourceWithCurrentTenant();
}
