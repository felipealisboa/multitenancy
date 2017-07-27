package org.springframework.cloud.multitenancy.core.catalog;

/**
 * 
 * @author WRP
 * */
public class MultitenancyTenantInformationBuilder {
	
	private String url;
	private String database;
	private Integer port;
	private String username;
	private String password;
	
	public static MultitenancyTenantInformationBuilder builder(){
		return new MultitenancyTenantInformationBuilder();
	}
	
	public MultitenancyTenantInformationBuilder url(String url) {
	      this.url = url;
	      return this;
	}
	
	public MultitenancyTenantInformationBuilder database(String database) {
	      this.database = database;
	      return this;
	}
	
	public MultitenancyTenantInformationBuilder port(Integer port) {
	      this.port = port;
	      return this;
	}
	
	public MultitenancyTenantInformationBuilder username(String username) {
	      this.username = username;
	      return this;
	}
	
	public MultitenancyTenantInformationBuilder password(String password) {
	      this.password = password;
	      return this;
	}
	
	public MultitenancyTenantInformation build() {
	      return new MultitenancyTenantInformation(url, database, port, username, password);
	}
}
