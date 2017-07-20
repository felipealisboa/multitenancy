package org.springframework.cloud.multitenancy.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Class responsible for loading all library settings
 * 
 * @author WRP
 * 
 * */

@Configuration
@ConfigurationProperties("spring.cloud.multitenancy")
public class MultitenancyConfigLoader {
	
	private TenantConfigProperties  tenant = new TenantConfigProperties();
	private CatalogConfigProperties catalog = new CatalogConfigProperties();
	private MongoDbConfigProperties mongodb = new MongoDbConfigProperties();
	private MySQLConfigProperties mysql = new MySQLConfigProperties();
	
	public TenantConfigProperties getTenant() {
		return tenant;
	}

	public void setTenant(TenantConfigProperties tenant) {
		this.tenant = tenant;
	}

	public CatalogConfigProperties getCatalog() {
		return catalog;
	}
	
	public void setCatalog(CatalogConfigProperties catalog) {
		this.catalog = catalog;
	}
	
	public MongoDbConfigProperties getMongodb() {
		return mongodb;
	}
	
	public void setMongodb(MongoDbConfigProperties mongodb) {
		this.mongodb = mongodb;
	}
	
	public MySQLConfigProperties getMysql() {
		return mysql;
	}
	
	public void setMysql(MySQLConfigProperties mysql) {
		this.mysql = mysql;
	}	
}
