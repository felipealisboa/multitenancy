package org.springframework.cloud.multitenancy.mysql.support;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.cloud.multitenancy.core.exchange.MultitenancyCurrentInformation;
import org.springframework.cloud.multitenancy.core.properties.MultitenancyConfigLoader;
import org.springframework.cloud.multitenancy.core.properties.MultitenancyStrategyEnum;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;

/**
 * 
 * @author WRP
 * */
public class MultitenancyMySQLEntityInformation <T, ID extends Serializable>  implements JpaEntityInformation<T, ID> {
	
	private final MultitenancyConfigLoader config;
	private final JpaEntityInformation<T, ID> entityInformation;
	
	@SuppressWarnings("unchecked")
	public MultitenancyMySQLEntityInformation (Class<T> domainClass, EntityManager em, MultitenancyConfigLoader config){
		entityInformation = (JpaEntityInformation<T, ID>) JpaEntityInformationSupport.getEntityInformation(domainClass, em);
		this.config = config;
	}

	@Override
	public boolean isNew(T entity) {
		return entityInformation.isNew(entity);
	}

	@Override
	public ID getId(T entity) {
		return entityInformation.getId(entity);
	}

	@Override
	public Class<ID> getIdType() {
		return entityInformation.getIdType();
	}

	@Override
	public Class<T> getJavaType() {
		return entityInformation.getJavaType();
	}

	@Override
	public String getEntityName() {
		
		String tenant = MultitenancyCurrentInformation.getTenant();
		String entityName = entityInformation.getEntityName();
		
		if(config.getMysql().getStrategy().equals(MultitenancyStrategyEnum.TABLE)){
			entityName = tenant.concat(entityName);
		}
		
		if(MultitenancyCurrentInformation.getPartition() != null){
			entityName = entityName.concat(MultitenancyCurrentInformation.getPartition());
		}
			
		return entityName;
	}

	@Override
	public SingularAttribute<? super T, ?> getIdAttribute() {
		return entityInformation.getIdAttribute();
	}

	@Override
	public boolean hasCompositeId() {
		return entityInformation.hasCompositeId();
	}

	@Override
	public Iterable<String> getIdAttributeNames() {
		return entityInformation.getIdAttributeNames();
	}

	@Override
	public Object getCompositeIdAttributeValue(Serializable id, String idAttribute) {
		return entityInformation.getCompositeIdAttributeValue(id, idAttribute);
	}	
}
