package org.springframework.cloud.multitenancy.mongo.catalog;

import org.springframework.cloud.multitenancy.core.catalog.MultitenancyTenantInformation;

import com.mongodb.WriteConcern;

/**
 * 
 * @author WRP
 * */
public class MultitenancyMongoTenantInformation extends MultitenancyTenantInformation  {
	
	private WriteConcern writeConcern;
	private boolean socketKeepAlive;
	private Integer connectionsPerHost; 
	
	public MultitenancyMongoTenantInformation(String url, String database, Integer port, String username, String password, WriteConcern writeConcern, boolean socketKeepAlive, Integer connectionsPerHost) {
		super(url, database, port, username, password);
		this.writeConcern = writeConcern;
		this.socketKeepAlive = socketKeepAlive;
		this.connectionsPerHost = connectionsPerHost;
	}

	public WriteConcern getWriteConcern() {
		return writeConcern;
	}

	public boolean getSocketKeepAlive() {
		return socketKeepAlive;
	}

	public Integer getConnectionsPerHost() {
		return connectionsPerHost;
	}		
}
