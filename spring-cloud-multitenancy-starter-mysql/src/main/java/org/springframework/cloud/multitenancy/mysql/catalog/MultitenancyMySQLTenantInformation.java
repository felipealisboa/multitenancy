package org.springframework.cloud.multitenancy.mysql.catalog;

import org.springframework.cloud.multitenancy.core.catalog.MultitenancyTenantInformation;

/**
 * 
 * @author WRP
 * */
public class MultitenancyMySQLTenantInformation extends MultitenancyTenantInformation  {
	
	public MultitenancyMySQLTenantInformation(String url, String database, Integer port, String username, String password) {
		super(url, database, port, username, password);
	}		
}
