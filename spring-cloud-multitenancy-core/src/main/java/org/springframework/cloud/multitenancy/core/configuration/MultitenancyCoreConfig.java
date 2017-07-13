package org.springframework.cloud.multitenancy.core.configuration;

import org.springframework.cloud.multitenancy.core.service.CurrentTenant;
import org.springframework.cloud.multitenancy.core.service.CurrentTenantResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultitenancyCoreConfig {
	
	@Bean
	public CurrentTenant currentTenant(){
		return new CurrentTenantResolver();
	}
}
