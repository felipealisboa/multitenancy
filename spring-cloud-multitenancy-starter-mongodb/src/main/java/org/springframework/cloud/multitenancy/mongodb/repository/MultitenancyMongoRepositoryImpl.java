package org.springframework.cloud.multitenancy.mongodb.repository;

import java.io.Serializable;

import org.springframework.cloud.multitenancy.mongodb.entity.MultitenancyMongoEntityInformation;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

/**
 * Class responsible for changing the MongoEntityInformation
 * 
 * @author WRP
 * */
public class MultitenancyMongoRepositoryImpl<T, ID extends Serializable> extends SimpleMongoRepository<T, ID> implements MultitenancyMongoRepository<T, ID> {
		
	public MultitenancyMongoRepositoryImpl(MongoEntityInformation<T, ID> entityInformation, MongoOperations mongoOperations) {
		super(new MultitenancyMongoEntityInformation<T, ID>(entityInformation), mongoOperations);
	}
}
