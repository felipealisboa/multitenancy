package org.springframework.cloud.multitenancy.core.properties;

/**
 * Loads the settings on how to deploy the multitenancy architecture in MongoDb
 * 
 * @author WRP
 * */
public class MongoDbConfigProperties {
	
	private MultitenancyStrategyEnum strategy;

	public MultitenancyStrategyEnum getStrategy() {
		return strategy;
	}

	public void setStrategy(MultitenancyStrategyEnum strategy) {
		this.strategy = strategy;
	}	
}
