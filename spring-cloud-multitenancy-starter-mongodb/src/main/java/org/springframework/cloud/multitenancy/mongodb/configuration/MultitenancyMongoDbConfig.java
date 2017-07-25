package org.springframework.cloud.multitenancy.mongodb.configuration;

import org.springframework.cloud.multitenancy.core.catalog.MultitenancyDataSourceCatalog;
import org.springframework.cloud.multitenancy.mongodb.datasource.MultitenancyDataSource;
import org.springframework.cloud.multitenancy.mongodb.datasource.MultitenancyDataSourceConnection;
import org.springframework.cloud.multitenancy.mongodb.datasource.MultitenancyManagementConnection;
import org.springframework.cloud.multitenancy.mongodb.datasource.MultitenancyManagementDataSource;
import org.springframework.cloud.multitenancy.mongodb.factory.MultiTenantMongoDbFactory;
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
public class MultitenancyMongoDbConfig {
	
	@Bean
    public MultitenancyDataSourceConnection multitenancyDataSourceConnection(MultitenancyDataSourceCatalog catalog){
        return new MultitenancyManagementConnection(catalog);
    }
	
	@Bean
    public MultitenancyDataSource multitenancyDataSource(MultitenancyDataSourceConnection connection){
        return new MultitenancyManagementDataSource(connection);
    }
	
	@Bean
    public MongoDbFactory mongoDbFactory(MultitenancyDataSource dataSource){
        return new MultiTenantMongoDbFactory(dataSource);
    }
	
	@Bean
    public MongoTemplate mongoTemplate(MongoDbFactory factory){
        return new MongoTemplate(factory);
    }
}
