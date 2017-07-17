package org.springframework.cloud.multitenancy.mongodb.factory;

import java.util.HashMap;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.DB;


/**
 * @author WRP
 * 
 * */

/*public class MultiTenantMongoDbFactory implements MongoDbFactory  {

	HashMap<String, SimpleMongoDbFactory> database; 
	
	@Override
	public DB getDb() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DB getDb(String dbName) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersistenceExceptionTranslator getExceptionTranslator() {
		// TODO Auto-generated method stub
		return null;
	}
}*/
