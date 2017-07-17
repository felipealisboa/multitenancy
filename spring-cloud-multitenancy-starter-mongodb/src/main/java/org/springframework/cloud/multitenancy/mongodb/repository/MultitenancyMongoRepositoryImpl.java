package org.springframework.cloud.multitenancy.mongodb.repository;

import java.io.Serializable;

import org.springframework.cloud.multitenancy.mongodb.entity.MultitenancyEntityInformation;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

public class MultitenancyMongoRepositoryImpl<T, ID extends Serializable> extends SimpleMongoRepository<T, ID> implements MultitenancyMongoRepository<T, ID> {
	
	public MultitenancyMongoRepositoryImpl(MongoEntityInformation<T, ID> metadata, MongoOperations mongoOperations) {
		super(new MultitenancyEntityInformation<>(metadata), mongoOperations);
	}
}
