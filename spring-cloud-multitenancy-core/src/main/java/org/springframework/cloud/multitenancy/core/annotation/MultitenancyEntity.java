package org.springframework.cloud.multitenancy.core.annotation;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that it is a persistence object and describes how the object should be saved in the database.
 * 
 * @author WRP
 * 
 * */
@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultitenancyEntity {
	
	/**
	 * Indicates the name of the document or table in which the information will be persisted.
	 * 
	 * */
	
	String name() default "";
}
