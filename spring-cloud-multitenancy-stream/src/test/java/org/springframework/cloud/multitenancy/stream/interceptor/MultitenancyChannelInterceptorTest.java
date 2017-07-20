package org.springframework.cloud.multitenancy.stream.interceptor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.cloud.multitenancy.core.exchange.MultitenancyCurrentInformation;
import org.springframework.cloud.multitenancy.core.properties.MultitenancyConfigLoader;
import org.springframework.cloud.multitenancy.core.properties.TenantConfigProperties;
import org.springframework.cloud.multitenancy.stream.exception.NotIdentifyTenantFieldException;
import org.springframework.cloud.multitenancy.stream.exception.TenantNotFoundException;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MultitenancyChannelInterceptorTest {
	
	private static final String FIELD_TENANT = "tenant";
	
	@Test(expected=NotIdentifyTenantFieldException.class)
	public void should_launch_an_exception_when_the_tenant_field_is_not_configured(){
		
		String tenant = "CLIENT_ID";
		boolean jsonMessage = true;
		SampleMessage message = new SampleMessage("Any message", tenant);
		
		MultitenancyConfigLoader multitenancyConfigLoader = Mockito.mock(MultitenancyConfigLoader.class);
		Mockito.when(multitenancyConfigLoader.getTenant()).thenReturn(new TenantConfigProperties());
		
		MultitenancyChannelInterceptor interceptor = new MultitenancyChannelInterceptor(multitenancyConfigLoader);
		interceptor.preSend(createMessage(message,jsonMessage), null);
	}
	
	@Test(expected=TenantNotFoundException.class)
	public void should_throw_an_exception_when_the_tenant_is_not_sent(){
		
		String tenant = null;
		boolean jsonMessage = true;
		SampleMessage message = new SampleMessage("Any message", tenant);
		
		TenantConfigProperties tenantConfigProperties = new TenantConfigProperties(); 
		tenantConfigProperties.setField(FIELD_TENANT);
		
		MultitenancyConfigLoader multitenancyConfigLoader = Mockito.mock(MultitenancyConfigLoader.class);
		Mockito.when(multitenancyConfigLoader.getTenant()).thenReturn(tenantConfigProperties);
		
		MultitenancyChannelInterceptor interceptor = new MultitenancyChannelInterceptor(multitenancyConfigLoader);
		interceptor.preSend(createMessage(message,jsonMessage), null);
	}
	
	@Test
	public void should_extract_the_message_tenant_and_update_the_current_tenant(){
		
		String tenant = "CLIENT_ID";
		boolean jsonMessage = true;
		SampleMessage message = new SampleMessage("Any message", tenant);
		
		TenantConfigProperties tenantConfigProperties = new TenantConfigProperties(); 
		tenantConfigProperties.setField(FIELD_TENANT);
		
		MultitenancyConfigLoader multitenancyConfigLoader = Mockito.mock(MultitenancyConfigLoader.class);
		Mockito.when(multitenancyConfigLoader.getTenant()).thenReturn(tenantConfigProperties);
		
		MultitenancyChannelInterceptor interceptor = new MultitenancyChannelInterceptor(multitenancyConfigLoader);
		interceptor.preSend(createMessage(message, jsonMessage), null);
		
		String currentTenant = MultitenancyCurrentInformation.tenant.get();
		
		assertEquals("The tenant update process did not work correctly", tenant, currentTenant);
	}
	
		
	/*
	 * Methods that help in creating the message
	 *  
	 * */
	private Message<?> createMessage(SampleMessage obj, boolean json){
		
		if(json){
			return createJsonMessage(obj);
		}else{
			return createJavaObjectMessage(obj);
		}	
	}	
			
	private Message<?> createJavaObjectMessage(SampleMessage obj){
		return MessageBuilder
				.withPayload(obj)
				.setHeader("contentType","application/x-java-object")
				.build();
	}
	
	private Message<?> createJsonMessage(SampleMessage obj){
		ObjectMapper mapper = new ObjectMapper();
		String message;
			
		try {
			message =  mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			message = null;
		}
			
		return MessageBuilder
				.withPayload(message)
				.setHeader("contentType","application/json;charset=UTF-8")
				.build();				
	}
		
	protected class SampleMessage {
		
		private String information;
		private String tenant;
		
		public SampleMessage (String information, String tenant){
			this.information = information;
			this.tenant = tenant;
		}
		
		public String getInformation(){
			return information;
		}
		
		public String getTenant(){
			return tenant;
		}
	}	
}
