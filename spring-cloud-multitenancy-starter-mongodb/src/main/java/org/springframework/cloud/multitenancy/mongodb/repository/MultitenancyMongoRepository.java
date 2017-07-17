package org.springframework.cloud.multitenancy.mongodb.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MultitenancyMongoRepository<T,ID extends Serializable> extends MongoRepository<T, ID> {

}
