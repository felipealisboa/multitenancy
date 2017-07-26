package org.springframework.cloud.multitenancy.web.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.multitenancy.core.properties.MultitenancyConfigLoader;
import org.springframework.cloud.multitenancy.web.interceptor.MultitenancyWebInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ImportAutoConfiguration(MultitenancyConfigLoader.class)
public class MultitenancyWebConfig  extends WebMvcConfigurerAdapter {
	
	@Autowired
	private MultitenancyConfigLoader config;
	
	@Override
    public void addInterceptors (InterceptorRegistry registry) {
        registry.addInterceptor(new MultitenancyWebInterceptor(config));
    }
}
