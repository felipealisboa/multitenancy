package org.springframework.cloud.multitenancy.mysql.support;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.multitenancy.core.properties.MultitenancyConfigLoader;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.core.support.TransactionalRepositoryFactoryBeanSupport;
import org.springframework.util.Assert;

/**
 * 
 * @author WRP
 * */
public class MultitenancyMySQLRepositoryFactoryBean <T extends Repository<S, ID>, S, ID extends Serializable> extends TransactionalRepositoryFactoryBeanSupport<T, S, ID> {
	
	private EntityManager entityManager;
	private MultitenancyConfigLoader config;
	
	@Autowired
	public MultitenancyMySQLRepositoryFactoryBean(MultitenancyConfigLoader config){
		this.config = config;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void setMappingContext(MappingContext<?, ?> mappingContext) {
		super.setMappingContext(mappingContext);
	}

	@Override
	protected RepositoryFactorySupport doCreateRepositoryFactory() {
		return new MultitenancyMySQLRepositoryFactory(entityManager, config);
	}

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(entityManager, "EntityManager must not be null!");
		super.afterPropertiesSet();
	}
}
