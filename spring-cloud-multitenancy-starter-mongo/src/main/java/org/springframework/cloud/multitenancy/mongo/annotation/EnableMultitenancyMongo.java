package org.springframework.cloud.multitenancy.mongo.annotation;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.multitenancy.mongo.configuration.MultitenancyMongoConfig;
import org.springframework.cloud.multitenancy.mongo.support.MultitenancyMongoRepositoryFactoryBean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration(MultitenancyMongoConfig.class)
@EnableMongoRepositories(repositoryFactoryBeanClass=MultitenancyMongoRepositoryFactoryBean.class)
public @interface EnableMultitenancyMongo {

}
