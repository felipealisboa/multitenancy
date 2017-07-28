package org.springframework.cloud.multitenancy.mongo.catalog;

import com.mongodb.WriteConcern;

/**
 * 
 * @author WRP
 */
public class MultitenancyMongoTenantInformationBuilder {

	private String url;
	private String database;
	private Integer port;
	private String username;
	private String password;
	
	private WriteConcern writeConcern;
	private boolean socketKeepAlive;
	private Integer connectionsPerHost; 

	public static MultitenancyMongoTenantInformationBuilder builder(){
		return  new MultitenancyMongoTenantInformationBuilder();
	}

	public MultitenancyMongoTenantInformationBuilder url(String url) {
		this.url = url;
		return this;
	}

	public MultitenancyMongoTenantInformationBuilder database(String database) {
		this.database = database;
		return this;
	}

	public MultitenancyMongoTenantInformationBuilder port(Integer port) {
		this.port = port;
		return this;
	}

	public MultitenancyMongoTenantInformationBuilder username(String username) {
		this.username = username;
		return this;
	}

	public MultitenancyMongoTenantInformationBuilder password(String password) {
		this.password = password;
		return this;
	}
	
	public MultitenancyMongoTenantInformationBuilder writeConcern(WriteConcern writeConcern) {
		this.writeConcern = writeConcern;
		return this;
	}
	
	public MultitenancyMongoTenantInformationBuilder socketKeepAlive(boolean socketKeepAlive) {
		this.socketKeepAlive = socketKeepAlive;
		return this;
	}
	
	public MultitenancyMongoTenantInformationBuilder connectionsPerHost(Integer connectionsPerHost) {
		this.connectionsPerHost = connectionsPerHost;
		return this;
	}
	
	public MultitenancyMongoTenantInformation build(){
		return new  MultitenancyMongoTenantInformation(url, database, port, username, password, writeConcern, socketKeepAlive, connectionsPerHost );
	}
}
