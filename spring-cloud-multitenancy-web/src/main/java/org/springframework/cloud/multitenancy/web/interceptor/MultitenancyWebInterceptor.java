package org.springframework.cloud.multitenancy.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.cloud.multitenancy.core.exception.PropertyNotSetException;
import org.springframework.cloud.multitenancy.core.exception.TenantNotFoundException;
import org.springframework.cloud.multitenancy.core.exchange.MultitenancyCurrentInformation;
import org.springframework.cloud.multitenancy.core.properties.MultitenancyConfigLoader;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Class responsible for extracting the request tenant
 * 
 * @author WRP
 * */
public class MultitenancyWebInterceptor extends HandlerInterceptorAdapter {
	
	private MultitenancyConfigLoader config;
	
	public MultitenancyWebInterceptor(MultitenancyConfigLoader config){
		this.config = config;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		
		String tenant = getTenant(request);
		
		if(tenant == null){
			throw new TenantNotFoundException("Could not extract message tenant");
		}
		
		MultitenancyCurrentInformation.setTenant(tenant);
		
		return true;
	}
	
	
	/**
	 * Retrieve the tenant through the header or DNS 
	 * */
	private String getTenant(HttpServletRequest request){
		
	    String tenant = null;
	    
	    if(config.getTenant().getField() != null){
	    	tenant = checkValueIsDNS(request.getHeader(config.getTenant().getField()));
	    }
	    
		if(tenant == null){
			tenant = checkValueIsDNS(request.getServerName());	
		}
		
		return tenant;
	}
	

	private String checkValueIsDNS(String tenant){
		
		if(tenant == null){
			return tenant; 
		}
		
		return tenant.contains(getDNSBase()) ?  tenant.substring(0, tenant.indexOf(getDNSBase())) : tenant;
	}
	
	
	private String getDNSBase(){
		String dns = config.getTenant().getDns();
		
		if(dns == null){
			throw new PropertyNotSetException("'spring.cloud.multitenancy.tenant.dns' not configured");
		}
		
		return dns;
	}
}
