package org.springframework.cloud.multitenancy.core.service;
/**
 *  Service responsible for making the information available to the other modules
 *  
 *  @author WRP
 * */
public interface MultitenancyInformation {
	
	 String getTenant();
	 
	 void setTenant(String tenant);
	 
	 String getPartition();
	 
	 void setPartition(String partition);
}
