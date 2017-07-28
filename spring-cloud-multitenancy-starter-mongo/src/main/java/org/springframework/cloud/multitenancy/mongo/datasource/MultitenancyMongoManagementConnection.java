package org.springframework.cloud.multitenancy.mongo.datasource;

import java.util.Arrays;

import org.springframework.cloud.multitenancy.core.catalog.MultitenancyTenantInformation;
import org.springframework.cloud.multitenancy.core.exception.TenantNotFoundInCatalogException;
import org.springframework.cloud.multitenancy.mongo.catalog.MultitenancyMongoDataSourceCatalog;
import org.springframework.cloud.multitenancy.mongo.catalog.MultitenancyMongoTenantInformation;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

public class MultitenancyMongoManagementConnection implements MultitenancyMongoDataSourceConnection {
	
	/**
	 * Data source catalog, contains the authentication information.
	 * */
	private MultitenancyMongoDataSourceCatalog catalog;
	
	private static final Integer CONNECTION_PER_HOST = 1000;
	
	
	public MultitenancyMongoManagementConnection(MultitenancyMongoDataSourceCatalog catalog){
		this.catalog = catalog;
	}
	
	
	@Override
	public DB open(String tenant){
		
		MultitenancyMongoTenantInformation tenantInfo = getInformationOfTenant(tenant);
	    MongoClient mongo = createMongoClient(tenantInfo, getHost(tenantInfo));
	    
	    return getDataSourceInstantce(tenantInfo, mongo);
	}
	
	
	private MultitenancyMongoTenantInformation getInformationOfTenant(String tenant) {
		MultitenancyMongoTenantInformation dataSourceInfo = catalog.getDataSourceByTenant(tenant);
		
		if(dataSourceInfo == null){
			throw new TenantNotFoundInCatalogException("Tenant not located in catalog ");
		}
		return dataSourceInfo;
	}
	
	private ServerAddress getHost(MultitenancyMongoTenantInformation dataSourceInfo) {
		return  dataSourceInfo.getPort() != null ? new ServerAddress(dataSourceInfo.getUrl(), dataSourceInfo.getPort()) 
	    		: new ServerAddress(dataSourceInfo.getUrl());
	}
	
	private DB getDataSourceInstantce(MultitenancyTenantInformation dataSourceInfo, MongoClient mongo) {
		SimpleMongoDbFactory factory = new SimpleMongoDbFactory(mongo, dataSourceInfo.getDatabase());		
		return factory.getDb();
	}

	private MongoClient createMongoClient(MultitenancyMongoTenantInformation dataSourceInfo, ServerAddress host) {
		
		MongoClientOptions options = createMongoOptions(dataSourceInfo);
		
	    if(shouldAuthenticate(dataSourceInfo)){
		    return new MongoClient(host, Arrays.asList(createMongoCredential(dataSourceInfo)), options);
	    }
	     
	    return new MongoClient(host, options);
	}
	
	
	private MongoClientOptions createMongoOptions(MultitenancyMongoTenantInformation dataSourceInfo){
		
		Builder option = MongoClientOptions.builder()
				           .writeConcern(dataSourceInfo.getWriteConcern() != null ? dataSourceInfo.getWriteConcern() : WriteConcern.ACKNOWLEDGED)
				           .connectionsPerHost(dataSourceInfo.getConnectionsPerHost() != null ? dataSourceInfo.getConnectionsPerHost() : CONNECTION_PER_HOST )
				           .socketKeepAlive(dataSourceInfo.getSocketKeepAlive());
		
		return option.build();
	}

	private MongoCredential createMongoCredential(MultitenancyMongoTenantInformation dataSourceInfo) {
		return MongoCredential.createCredential(
					dataSourceInfo.getUsername(),
					dataSourceInfo.getDatabase(),
					dataSourceInfo.getPassword().toCharArray()); 
	}

	private boolean shouldAuthenticate(MultitenancyMongoTenantInformation dataSourceInfo) {
		return dataSourceInfo.getUsername() != null &&  dataSourceInfo.getPassword() != null;
	}

}
