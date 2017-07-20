package org.springframework.cloud.multitenancy.core.exchange;

/**
 * Class thread safe that provides the information in real time for the other modules
 * 
 * @author WRP
 * */
public class MultitenancyCurrentInformation{
	
	/**
	 * Customer identifier
	 * */
	public static ThreadLocal<String> tenant = new ThreadLocal<String>();
	
	/**
	 * Name of the partition, which will be used to generate the entity name
	 * */
	public static ThreadLocal<String> partition = new ThreadLocal<String>();
	
}
