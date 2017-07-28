package org.springframework.cloud.multitenancy.core.catalog;

/**
 * Object that stores the information to connect to the database
 * 
 * @author WRP
 * */
public abstract class MultitenancyTenantInformation {
	
	private String url;
	private String database;
	private Integer port;
	private String username;
	private String password;
		
	public MultitenancyTenantInformation(String url, String database, Integer port, String username, String password){
		this.url = url;
		this.database = database;
		this.port = port;
		this.username = username;
		this.password = password;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getDatabase() {
		return database;
	}
	
	public Integer getPort() {
		return port;
	}
		
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
}
