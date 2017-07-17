package org.springframework.cloud.multitenancy.core.service;
/**
 * 
 * @author WRP
 * */
public class MultitenancyCurrentInformation implements MultitenancyInformation {
	
	private final ThreadLocal<String> tenant = new ThreadLocal<String>();

	@Override
	public String getTenant() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTenant(String tenant) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPartition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPartition(String partition) {
		// TODO Auto-generated method stub
		
	}
}
