package org.springframework.cloud.multitenancy.mysql.annotation;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.multitenancy.mysql.configuration.MultitenancyMySQLConfig;
import org.springframework.cloud.multitenancy.mysql.support.MultitenancyMySQLRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration(MultitenancyMySQLConfig.class)
@EnableJpaRepositories(repositoryFactoryBeanClass=MultitenancyMySQLRepositoryFactoryBean.class)
public @interface EnableMultitenancyMySQL {

}
