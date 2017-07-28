package org.springframework.cloud.multitenancy.mongo.support;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.multitenancy.core.properties.MultitenancyConfigLoader;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.Assert;

/**
 * 
 * @author WRP
 * */
public class MultitenancyMongoRepositoryFactoryBean <T extends Repository<S, I>, S, I extends Serializable> extends RepositoryFactoryBeanSupport<T, S, I> {
	
	private MongoOperations operations;
	private boolean createIndexesForQueryMethods = false;
	private boolean mappingContextConfigured = false;
	private MultitenancyConfigLoader config;
	
	@Autowired
	public MultitenancyMongoRepositoryFactoryBean(MultitenancyConfigLoader config){
		this.config = config;
	}
	
	public void setMongoOperations(MongoOperations operations) {
		this.operations = operations;
	}

	public void setCreateIndexesForQueryMethods(boolean createIndexesForQueryMethods) {
		this.createIndexesForQueryMethods = createIndexesForQueryMethods;
	}

	@Override
	protected void setMappingContext(MappingContext<?, ?> mappingContext) {
		super.setMappingContext(mappingContext);
		this.mappingContextConfigured = true;
	}

	@Override
	protected final RepositoryFactorySupport createRepositoryFactory() {

		RepositoryFactorySupport factory = getFactoryInstance(operations);

		if (createIndexesForQueryMethods) {
			factory.addQueryCreationListener(new MultitenancyIndexEnsuringQueryCreationListener(operations));
		}

		return factory;
	}

	protected RepositoryFactorySupport getFactoryInstance(MongoOperations operations) {
		return new MultitenancyMongoRepositoryFactory(operations, config);
	}

	@Override
	public void afterPropertiesSet() {

		super.afterPropertiesSet();
		Assert.notNull(operations, "MongoTemplate must not be null!");

		if (!mappingContextConfigured) {
			setMappingContext(operations.getConverter().getMappingContext());
		}
	}
}
