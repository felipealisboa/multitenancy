package org.springframework.cloud.multitenancy.core.service;

public class CurrentTenantResolver implements CurrentTenant {
	
	private final ThreadLocal<String> tenant = new ThreadLocal<String>();
	
	@Override
	public String getCurrentTenant() {
		return tenant.get();
	}

	@Override
	public void setCurrentTenant(String tenant) {
		this.tenant.set(tenant);
	}
}
