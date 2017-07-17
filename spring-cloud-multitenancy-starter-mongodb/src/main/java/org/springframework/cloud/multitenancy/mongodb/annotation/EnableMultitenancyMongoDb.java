package org.springframework.cloud.multitenancy.mongodb.annotation;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.multitenancy.mongodb.configuration.MultitenancyMongoDbConfig;
import org.springframework.cloud.multitenancy.mongodb.repository.MultitenancyMongoRepositoryImpl;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration(MultitenancyMongoDbConfig.class)
@EnableMongoRepositories(repositoryBaseClass=MultitenancyMongoRepositoryImpl.class)
public @interface EnableMultitenancyMongoDb {

}
