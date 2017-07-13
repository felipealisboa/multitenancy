package org.springframework.cloud.multitenancy.core;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

public class MultitenancyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {

	@Override
	public void initialize(ConfigurableApplicationContext arg0) {
		// TODO Auto-generated method stub
		System.out.println("Chegou aqui:");
	}

	@Override
	public int getOrder() {
		return 100;
	}
}
