package org.springframework.cloud.multitenancy.core.exchange;

import org.springframework.cloud.multitenancy.core.exception.TenantNotFoundException;

/**
 * Class thread safe that provides the information in real time for the other modules
 * 
 * @author WRP
 * */
public class MultitenancyCurrentInformation{
	
	/**
	 * Customer identifier
	 * */
	private  static ThreadLocal<String> tenant = new ThreadLocal<String>();
	
	/**
	 * Name of the partition, which will be used to generate the entity name
	 * */
	private static ThreadLocal<String> partition = new ThreadLocal<String>();
	
	

	public static String getTenant() {
		String tenant = MultitenancyCurrentInformation.tenant.get();
		
		if(tenant == null){
			throw new TenantNotFoundException("");
		}
		return tenant;
	}


	public static void setTenant(String tenant) {
		MultitenancyCurrentInformation.tenant.set(tenant);
	}

	public static String getPartition() {
		return MultitenancyCurrentInformation.partition.get();
	}


	public static void setPartition(String partition) {
		MultitenancyCurrentInformation.partition.set(partition);
	}
}
