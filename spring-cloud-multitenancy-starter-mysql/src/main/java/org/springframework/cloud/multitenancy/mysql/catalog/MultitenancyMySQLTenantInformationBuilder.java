package org.springframework.cloud.multitenancy.mysql.catalog;

/**
 * 
 * @author WRP
 */
public class MultitenancyMySQLTenantInformationBuilder {

	private String url;
	private String database;
	private Integer port;
	private String username;
	private String password;
	 
	public static MultitenancyMySQLTenantInformationBuilder builder(){
		return  new MultitenancyMySQLTenantInformationBuilder();
	}

	public MultitenancyMySQLTenantInformationBuilder url(String url) {
		this.url = url;
		return this;
	}

	public MultitenancyMySQLTenantInformationBuilder database(String database) {
		this.database = database;
		return this;
	}

	public MultitenancyMySQLTenantInformationBuilder port(Integer port) {
		this.port = port;
		return this;
	}

	public MultitenancyMySQLTenantInformationBuilder username(String username) {
		this.username = username;
		return this;
	}

	public MultitenancyMySQLTenantInformationBuilder password(String password) {
		this.password = password;
		return this;
	}
	
	public MultitenancyMySQLTenantInformation build(){
		return new  MultitenancyMySQLTenantInformation(url, database, port, username, password);
	}
}
