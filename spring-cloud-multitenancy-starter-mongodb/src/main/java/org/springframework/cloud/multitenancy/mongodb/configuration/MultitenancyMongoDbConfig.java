package org.springframework.cloud.multitenancy.mongodb.configuration;

import org.springframework.cloud.multitenancy.mongodb.factory.MultiTenantMongoDbFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.Mongo;

@Configuration
public class MultitenancyMongoDbConfig {
	
	
	@Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new MultiTenantMongoDbFactory();
    }
	
	@Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }
}
