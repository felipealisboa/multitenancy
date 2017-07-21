package org.springframework.cloud.multitenancy.core.exchange;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.cloud.multitenancy.core.exchange.MultitenancyCurrentInformation;

public class MultitenancyCurrentInformationTest {
	
	@Test
	public void should_store_the_value_correctly_in_a_multi_thred_environment() {
		int numberOfAttempts = 20;
		
		ThreadMultitenancyRunnable threadTenantOne = new ThreadMultitenancyRunnable("Tanant 1", numberOfAttempts, 1);
		ThreadMultitenancyRunnable threadTenantTwo = new ThreadMultitenancyRunnable("Tanant 2", numberOfAttempts, 2);
		
		threadTenantOne.start();
		threadTenantTwo.start();
	}
	
	protected class ThreadMultitenancyRunnable extends Thread {
		
		private Log log = LogFactory.getLog(ThreadMultitenancyRunnable.class);
		 
		private String tenant;
		private int numberOfAttempts;
		private int waitingTime;
		
		public ThreadMultitenancyRunnable(String tenant, int numberOfAttempts,int waitingTime) {
			this.tenant = tenant;
			this.numberOfAttempts = numberOfAttempts;
			this.waitingTime = waitingTime;
		}

		@Override
		public void run() {
			
			for (int i = 0; i < numberOfAttempts; i++) {
				
				MultitenancyCurrentInformation.setTenant(tenant);
				
				try {
					TimeUnit.SECONDS.sleep(waitingTime);
				} catch (InterruptedException e) {
					log.error("It was not possible to wait for the time to change the variables");
				}
				
				String value = MultitenancyCurrentInformation.getTenant();
				assertEquals("The information was not storing correctly, the object is not thred safe", tenant, value);
			}			
		}
	}
}