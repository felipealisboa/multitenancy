package org.springframework.cloud.multitenancy.web.interceptor;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.cloud.multitenancy.core.exception.PropertyNotSetException;
import org.springframework.cloud.multitenancy.core.exception.TenantNotFoundException;
import org.springframework.cloud.multitenancy.core.exchange.MultitenancyCurrentInformation;
import org.springframework.cloud.multitenancy.core.properties.MultitenancyConfigLoader;
import org.springframework.cloud.multitenancy.core.properties.TenantConfigProperties;

public class MultitenancyWebInterceptorTest {
	
	
	@Test(expected=PropertyNotSetException.class)
	public void should_throw_an_exception_when_the_domain_is_not_configured()throws Exception{
		
		String serverName = "multitenancy.com.br";
		
		MultitenancyConfigLoader config = Mockito.mock(MultitenancyConfigLoader.class);
		Mockito.when(config.getTenant()).thenReturn(new TenantConfigProperties());
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getServerName()).thenReturn(serverName);

		MultitenancyWebInterceptor interceptor = new MultitenancyWebInterceptor(config);
		interceptor.preHandle(request, null, null);
	}
	
	
	@Test(expected=TenantNotFoundException.class)
	public void should_throw_an_exception_if_it_can_not_identify_the_tenant() throws Exception{
		
		TenantConfigProperties tenantConfig = new TenantConfigProperties();
		tenantConfig.setDns(".tenant");
		
		MultitenancyConfigLoader config = Mockito.mock(MultitenancyConfigLoader.class);
		Mockito.when(config.getTenant()).thenReturn(tenantConfig);
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getServerName()).thenReturn(null);

			
		MultitenancyWebInterceptor interceptor = new MultitenancyWebInterceptor(config);
		interceptor.preHandle(request, null, null);
	}
	
	
	@Test
	public void should_retrieve_the_tenant_correctly() throws Exception{
		
		String fieldTenant = "tenant";
		String tenant = "tenant";
		String dns    = "tenant.multitenant.com";
		
		TenantConfigProperties tenantConfig = new TenantConfigProperties();
		tenantConfig.setField(fieldTenant);
		tenantConfig.setDns(dns);
		
		MultitenancyConfigLoader config = Mockito.mock(MultitenancyConfigLoader.class);
		Mockito.when(config.getTenant()).thenReturn(tenantConfig);
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getHeader(fieldTenant)).thenReturn(tenant);
		Mockito.when(request.getServerName()).thenReturn(dns);
			
		MultitenancyWebInterceptor interceptor = new MultitenancyWebInterceptor(config);
		interceptor.preHandle(request, null, null);
		
		String currentTenant = MultitenancyCurrentInformation.getTenant();
		
		assertEquals("The tenant update process did not work correctly", tenant, currentTenant);
	}
}
