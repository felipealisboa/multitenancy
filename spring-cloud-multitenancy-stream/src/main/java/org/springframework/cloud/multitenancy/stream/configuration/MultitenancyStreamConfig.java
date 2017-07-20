package org.springframework.cloud.multitenancy.stream.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.multitenancy.core.properties.MultitenancyConfigLoader;
import org.springframework.cloud.multitenancy.stream.interceptor.MultitenancyChannelInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptor;

@Configuration
@ImportAutoConfiguration(MultitenancyConfigLoader.class)
public class MultitenancyStreamConfig {
	
	@Autowired
	private MultitenancyConfigLoader config;
	
	@Bean
	@GlobalChannelInterceptor
	public ChannelInterceptor globalChannelInterceptor(){
		return new MultitenancyChannelInterceptor(config);
	}	
}
