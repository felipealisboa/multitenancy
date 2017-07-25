package org.springframework.cloud.multitenancy.mongodb.factory;

import org.springframework.cloud.multitenancy.mongodb.datasource.MultitenancyDataSource;
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
public class MultiTenantMongoDbFactory implements MongoDbFactory  {

	private MultitenancyDataSource dataSource;
		
	public MultiTenantMongoDbFactory(MultitenancyDataSource dataSource){
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
