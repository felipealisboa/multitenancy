package org.springframework.cloud.multitenancy.mongodb.entity;

import java.io.Serializable;

import org.springframework.cloud.multitenancy.core.annotation.MultitenancyEntity;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;

public class MultitenancyEntityInformation<T, ID extends Serializable> implements MongoEntityInformation<T, ID> {
	
	private MongoEntityInformation<T, ID> metadata;
	
	public MultitenancyEntityInformation(MongoEntityInformation<T, ID> metadata){
		this.metadata = metadata;
	}
	
	@Override
	public boolean isNew(T entity) {
		return metadata.isNew(entity);
	}

	@Override
	public ID getId(T entity) {
		return metadata.getId(entity);
	}

	@Override
	public Class<ID> getIdType() {
		return metadata.getIdType();
	}

	@Override
	public Class<T> getJavaType() {
		return metadata.getJavaType();
	}

	@Override
	public String getCollectionName() {
		MultitenancyEntity value = getJavaType().getAnnotation(MultitenancyEntity.class);
		
		metadata.getCollectionName();
		metadata.getIdAttribute();
		
		return "wesley_deu_certo";
	}

	@Override
	public String getIdAttribute() {
		return metadata.getIdAttribute();
	}
}
