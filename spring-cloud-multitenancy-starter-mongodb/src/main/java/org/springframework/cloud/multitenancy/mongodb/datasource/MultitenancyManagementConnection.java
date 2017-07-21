package org.springframework.cloud.multitenancy.mongodb.datasource;

import java.util.Arrays;

import org.springframework.cloud.multitenancy.core.catalog.MultitenancyDataSourceCatalog;
import org.springframework.cloud.multitenancy.core.catalog.MultitenancyTenantInformation;
import org.springframework.cloud.multitenancy.core.exception.TenantNotFoundInCatalogException;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MultitenancyManagementConnection implements MultitenancyDataSourceConnection {
	
	/**
	 * Data source catalog, contains the authentication information.
	 * */
	MultitenancyDataSourceCatalog catalog;
	
	
	public MultitenancyManagementConnection(MultitenancyDataSourceCatalog catalog){
		this.catalog = catalog;
	}
	
	
	@Override
	public DB open(String tenant){
		
		MultitenancyTenantInformation tenantInfo = getInformationOfTenant(tenant);
	    MongoClient mongo = createMongoClient(tenantInfo, getHost(tenantInfo));
	    
	    return getDataSourceInstantce(tenantInfo, mongo);
	}
	
	
	private MultitenancyTenantInformation getInformationOfTenant(String tenant) {
		MultitenancyTenantInformation dataSourceInfo = catalog.getDataSourceByTenant(tenant);
		
		if(dataSourceInfo == null){
			throw new TenantNotFoundInCatalogException("");
		}
		return dataSourceInfo;
	}
	
	private ServerAddress getHost(MultitenancyTenantInformation dataSourceInfo) {
		return  dataSourceInfo.getPort() != null ? new ServerAddress(dataSourceInfo.getUrl(), dataSourceInfo.getPort()) 
	    		: new ServerAddress(dataSourceInfo.getUrl());
	}
	
	private DB getDataSourceInstantce(MultitenancyTenantInformation dataSourceInfo, MongoClient mongo) {
		SimpleMongoDbFactory factory = new SimpleMongoDbFactory(mongo, dataSourceInfo.getDatabase());
		DB db = factory.getDb();
		return db;
	}

	private MongoClient createMongoClient(MultitenancyTenantInformation dataSourceInfo, ServerAddress host) {
		
	    if(shouldAuthenticate(dataSourceInfo)){
		    return new MongoClient(host, Arrays.asList(createMongoCredential(dataSourceInfo)));
	    }
	    
	    return new MongoClient(host);
	}

	private MongoCredential createMongoCredential(MultitenancyTenantInformation dataSourceInfo) {
		MongoCredential credential = MongoCredential.createCredential(
				dataSourceInfo.getUsername(),
				dataSourceInfo.getDatabase(),
				dataSourceInfo.getPassword().toCharArray());
		return credential;
	}

	private boolean shouldAuthenticate(MultitenancyTenantInformation dataSourceInfo) {
		return dataSourceInfo.getUsername() != null &&  dataSourceInfo.getPassword() != null;
	}

}
