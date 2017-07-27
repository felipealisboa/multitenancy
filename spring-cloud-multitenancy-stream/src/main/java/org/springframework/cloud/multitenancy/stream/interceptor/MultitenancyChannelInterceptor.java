package org.springframework.cloud.multitenancy.stream.interceptor;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.multitenancy.core.exception.PropertyNotSetException;
import org.springframework.cloud.multitenancy.core.exception.TenantNotFoundException;
import org.springframework.cloud.multitenancy.core.exchange.MultitenancyCurrentInformation;
import org.springframework.cloud.multitenancy.core.properties.MultitenancyConfigLoader;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class responsible for extracting the tenant from a message sent to the queue
 * and storing it in MultitenancyCurrentInformation.
 * 
 * @author WRP
 * */
public class MultitenancyChannelInterceptor extends ChannelInterceptorAdapter {
	
	private static final List<String> LIST_OF_SUPPORTED_CONTENT_TYPES = Arrays.asList("application/json");
	private MultitenancyConfigLoader config;
	
	@Autowired
	public MultitenancyChannelInterceptor(MultitenancyConfigLoader config){
		this.config = config;
	}
	
	@Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
		
		if(!isValidMessage(message)){
			return message;
		}
		
        String tenantField = getFieldTenant();
		String tenant = null;
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(message.getPayload().toString());
			tenant =  jsonNode.get(tenantField).isNull() ? null : jsonNode.get(tenantField).asText();
			
		} catch (Exception ex) {
			throw new TenantNotFoundException("Could not extract message tenant",ex);
		}
		
		if(tenant == null){
			throw new TenantNotFoundException("Could not extract message tenant");
		}
		
		MultitenancyCurrentInformation.setTenant(tenant);
		
        return message;
    }
	
	/**
	 * Checks to see if the message meets the library
	 * 
	 * */
	private boolean isValidMessage(Message<?> message){
		
		String contentType = (String) message.getHeaders().get("contentType");
		
		if(contentType == null || message.getPayload() == null){
			return false;
		}
		
		boolean supported = false;
		
		for (String supportedContentType : LIST_OF_SUPPORTED_CONTENT_TYPES) {
			if(contentType.contains(supportedContentType)){
				supported = true;
				break;
			}
		}
		
		return supported;
	}
	
	/**
	 * Loads the name of the field containing the tenant
	 * 
	 * */
	private String getFieldTenant(){
		
		String fieldTenant = config.getTenant().getField();

		if(fieldTenant == null){
			throw new PropertyNotSetException("The tenant field was not defined");
		}
		
		return fieldTenant;
	}
}
