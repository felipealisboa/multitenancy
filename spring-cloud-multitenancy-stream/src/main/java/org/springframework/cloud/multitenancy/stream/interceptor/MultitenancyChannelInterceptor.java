package org.springframework.cloud.multitenancy.stream.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.multitenancy.core.service.CurrentTenant;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

public class MultitenancyChannelInterceptor extends ChannelInterceptorAdapter {
	
	@Autowired
	private CurrentTenant currentTenant;
	
	@Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
		currentTenant.setCurrentTenant("Wesley:");
		System.out.println("Here: " + message);
        return message;
    }
}
