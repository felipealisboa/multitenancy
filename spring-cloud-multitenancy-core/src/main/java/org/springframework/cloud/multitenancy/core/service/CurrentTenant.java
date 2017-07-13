package org.springframework.cloud.multitenancy.core.service;

public interface CurrentTenant {
	
	 String getCurrentTenant();
	 
	 void setCurrentTenant(String tenant);
}
