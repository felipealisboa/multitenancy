package org.springframework.cloud.multitenancy.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.cloud.multitenancy.core.exception.NotIdentifyTenantFieldException;
import org.springframework.cloud.multitenancy.core.exception.TenantNotFoundException;
import org.springframework.cloud.multitenancy.core.exchange.MultitenancyCurrentInformation;
import org.springframework.cloud.multitenancy.core.properties.MultitenancyConfigLoader;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * @author WRP
 * */
public class MultitenancyWebInterceptor extends HandlerInterceptorAdapter {

	private static final String FIXO = ".agilepromoter";
	
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
	 * Retrieve the tenant from the request
	 * 
	 * */
	private String getTenant(HttpServletRequest request){
		
		String fieldTenant = getFieldTenant();
	    String tenant = null;
	    
		if(request.getHeader(fieldTenant) != null){
			tenant = request.getHeader(fieldTenant);	
		}else{
			tenant = request.getServerName();
		}
		
		if(tenant.contains(FIXO)){
			tenant = tenant.substring(0, tenant.indexOf(FIXO));
		}
		
		return tenant;
	}
	

	/**
	 * Loads the name of the field containing the tenant
	 * 
	 * */
	private String getFieldTenant(){
		
		String fieldTenant = config.getTenant().getField();

		if(fieldTenant == null){
			throw new NotIdentifyTenantFieldException("The tenant field was not defined");
		}
		
		return fieldTenant;
	}
}
