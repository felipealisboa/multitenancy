package org.springframework.cloud.multitenancy.mongo.datasource;

import java.util.HashMap;

import org.springframework.cloud.multitenancy.core.exchange.MultitenancyCurrentInformation;

import com.mongodb.DB;

/**
 * Class responsible for managing data sources
 * 
 * @author WRP
 * */
public class MultitenancyMongoManagementDataSource implements MultitenancyMongoDataSource {
	
	/**
	 * Data source cache, contains the active databases in the microservice.
	 * */
	HashMap<String, DB> databases = new HashMap<>();
	
	/**
	 * Service responsible for opening the connection to the database
	 * */
	private MultitenancyMongoDataSourceConnection connection;
	

	public  MultitenancyMongoManagementDataSource(MultitenancyMongoDataSourceConnection connection){
	   this.connection = connection;	
	}
	
	@Override
	public DB getDataSourceWithCurrentTenant() {
		
		String tenant = MultitenancyCurrentInformation.getTenant();
		
		DB dataSource =  getDataSourceInCache(tenant);
		
		if(dataSource == null){
			dataSource = connection.open(tenant);
			savedInCache(tenant, dataSource);
		}
		
		return dataSource;
	}
	
	
	private DB getDataSourceInCache(String tenant){
		return databases.get(tenant);
	}
	
	private void savedInCache(String tenant, DB dataSource){
		databases.put(tenant, dataSource);
	}	
}
