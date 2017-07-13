package org.springframework.cloud.multitenancy.stream.configuration;

import org.springframework.cloud.multitenancy.stream.interceptor.MultitenancyChannelInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptor;

@Configuration
public class MultitenancyStreamConfig {
	
	@Bean
	@GlobalChannelInterceptor
	public ChannelInterceptor globalChannelInterceptor(){
		return new MultitenancyChannelInterceptor();
	}	
}
