package org.springframework.cloud.multitenancy.mongo.configuration;

import org.springframework.cloud.multitenancy.mongo.catalog.MultitenancyMongoDataSourceCatalog;
import org.springframework.cloud.multitenancy.mongo.datasource.MultitenancyMongoDataSource;
import org.springframework.cloud.multitenancy.mongo.datasource.MultitenancyMongoDataSourceConnection;
import org.springframework.cloud.multitenancy.mongo.datasource.MultitenancyMongoManagementConnection;
import org.springframework.cloud.multitenancy.mongo.datasource.MultitenancyMongoManagementDataSource;
import org.springframework.cloud.multitenancy.mongo.factory.MultiTenantMongoFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Sets the bean
 * 
 * @author WRP
 * */
@Configuration
public class MultitenancyMongoConfig {
	
	@Bean
    public MultitenancyMongoDataSourceConnection multitenancyDataSourceConnection(MultitenancyMongoDataSourceCatalog catalog){
        return new MultitenancyMongoManagementConnection(catalog);
    }
	
	@Bean
    public MultitenancyMongoDataSource multitenancyDataSource(MultitenancyMongoDataSourceConnection connection){
        return new MultitenancyMongoManagementDataSource(connection);
    }
	
	@Bean
    public MongoDbFactory mongoDbFactory(MultitenancyMongoDataSource dataSource){
        return new MultiTenantMongoFactory(dataSource);
    }
	
	@Bean
    public MongoTemplate mongoTemplate(MongoDbFactory factory){
        return new MongoTemplate(factory);
    }
}
