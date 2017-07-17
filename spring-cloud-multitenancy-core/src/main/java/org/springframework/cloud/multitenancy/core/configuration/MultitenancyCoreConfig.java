package org.springframework.cloud.multitenancy.core.configuration;

import org.springframework.cloud.multitenancy.core.service.MultitenancyInformation;
import org.springframework.cloud.multitenancy.core.service.MultitenancyCurrentInformation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultitenancyCoreConfig {
	
	@Bean
	public MultitenancyInformation information(){
		return new MultitenancyCurrentInformation();
	}
}
