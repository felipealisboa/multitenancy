package org.springframework.cloud.multitenancy.mongodb.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.cloud.multitenancy.core.exception.TenantNotFoundException;
import org.springframework.cloud.multitenancy.core.exchange.MultitenancyCurrentInformation;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;

public class MultitenancyMongoEntityInformationTest {

	@Test(expected = TenantNotFoundException.class)
	public void should_throw_an_exceptionif_it_does_not_find_the_tenant() {

		MultitenancyMongoEntityInformation<?, ?> multitenancyMongoEntityInformation = new MultitenancyMongoEntityInformation<>(null);
		multitenancyMongoEntityInformation.getCollectionName();
	}

	@Test
	public void should_generate_the_collection_name_using_the_tenant() {

		String collectionName = "_my_collection";
		String tenant = "";
		String expected = tenant.concat(collectionName);

		MultitenancyCurrentInformation.setTenant(tenant);

		MongoEntityInformation<?, ?> mongoEntityInformation = Mockito.mock(MongoEntityInformation.class);
		Mockito.when(mongoEntityInformation.getCollectionName()).thenReturn(collectionName);

		MultitenancyMongoEntityInformation<?, ?> multitenancyMongoEntityInformation = new MultitenancyMongoEntityInformation<>(mongoEntityInformation);

		assertEquals("The multi-tenant component did not generate the collection name correctly", expected, multitenancyMongoEntityInformation.getCollectionName());
	}
}
