package org.springframework.cloud.multitenancy.mongodb.entity;

import java.io.Serializable;

import org.springframework.cloud.multitenancy.core.exchange.MultitenancyCurrentInformation;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;



/**
 * Class responsible for implementing multitenancy by colletion
 * 
 * @author WRP
 * */
public class MultitenancyMongoEntityInformation<T, ID extends Serializable> implements MongoEntityInformation<T, ID> {
	
	private MongoEntityInformation<T, ID> entityInformation;
		
	public MultitenancyMongoEntityInformation(MongoEntityInformation<T, ID> entityInformation){
		this.entityInformation = entityInformation;
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
	public String getCollectionName() {
		
		String tenant = MultitenancyCurrentInformation.getTenant();
		
		String collectionName = tenant.concat(entityInformation.getCollectionName());
		
		if(MultitenancyCurrentInformation.getPartition() != null){
			collectionName = collectionName.concat(MultitenancyCurrentInformation.getPartition());
		}
		
		return collectionName;
	}

	@Override
	public String getIdAttribute() {
		return entityInformation.getIdAttribute();
	}
}
