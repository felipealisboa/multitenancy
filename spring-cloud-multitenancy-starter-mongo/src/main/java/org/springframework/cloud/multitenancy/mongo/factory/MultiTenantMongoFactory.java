package org.springframework.cloud.multitenancy.mongo.factory;

import org.springframework.cloud.multitenancy.mongo.datasource.MultitenancyMongoDataSource;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoExceptionTranslator;

import com.mongodb.DB;

/**
 * 
 * Class responsible for overrride access behavior to datasource
 * 
 * @author WRP
 * */
public class MultiTenantMongoFactory implements MongoDbFactory  {

	private MultitenancyMongoDataSource dataSource;
		
	public MultiTenantMongoFactory(MultitenancyMongoDataSource dataSource){
		this.dataSource = dataSource;
	}
	
	@Override
	public DB getDb(){
		return dataSource.getDataSourceWithCurrentTenant();
	}

	@Override
	public DB getDb(String dbName){
		return dataSource.getDataSourceWithCurrentTenant();
	}

	@Override
	public PersistenceExceptionTranslator getExceptionTranslator() {
		return new MongoExceptionTranslator();
	}
}
