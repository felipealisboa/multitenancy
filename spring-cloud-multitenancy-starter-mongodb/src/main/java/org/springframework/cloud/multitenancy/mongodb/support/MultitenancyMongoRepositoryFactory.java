package org.springframework.cloud.multitenancy.mongodb.support;

import static org.springframework.data.querydsl.QueryDslUtils.QUERY_DSL_PRESENT;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.springframework.cloud.multitenancy.core.properties.MultitenancyConfigLoader;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.query.MongoQueryMethod;
import org.springframework.data.mongodb.repository.query.PartTreeMongoQuery;
import org.springframework.data.mongodb.repository.query.StringBasedMongoQuery;
import org.springframework.data.mongodb.repository.support.QueryDslMongoRepository;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.EvaluationContextProvider;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.Assert;

/**
 * 
 * @author WRP
 * */
public class MultitenancyMongoRepositoryFactory extends RepositoryFactorySupport {
	
	private static final SpelExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

	private final MongoOperations operations;
	private final MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext;
    private final MultitenancyConfigLoader config;
	
	public MultitenancyMongoRepositoryFactory(MongoOperations mongoOperations, MultitenancyConfigLoader config) {

		Assert.notNull(mongoOperations);

		this.operations = mongoOperations;
		this.mappingContext = mongoOperations.getConverter().getMappingContext();
		this.config = config;
	}

	@Override
	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {

		boolean isQueryDslRepository = QUERY_DSL_PRESENT
				&& QueryDslPredicateExecutor.class.isAssignableFrom(metadata.getRepositoryInterface());

		return isQueryDslRepository ? QueryDslMongoRepository.class : SimpleMongoRepository.class;
	}

	@Override
	protected Object getTargetRepository(RepositoryInformation information) {
		MongoEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType(), information);
		return getTargetRepositoryViaReflection(information, entityInformation, operations);
	}

	
	@Override
	protected QueryLookupStrategy getQueryLookupStrategy(Key key, EvaluationContextProvider evaluationContextProvider) {
		return new MongoQueryLookupStrategy(operations, evaluationContextProvider, mappingContext);
	}
	
	@Override
	public <T, I extends Serializable> MongoEntityInformation<T, I> getEntityInformation(Class<T> domainClass) {
		return getEntityInformation(domainClass, null);
	}

	@SuppressWarnings("unchecked")
	private <T, I extends Serializable> MongoEntityInformation<T, I> getEntityInformation(Class<T> domainClass, RepositoryInformation information) {

		MongoPersistentEntity<?> entity = mappingContext.getPersistentEntity(domainClass);

		if (entity == null) {
			throw new MappingException(
					String.format("Could not lookup mapping metadata for domain class %s!", domainClass.getName()));
		}

		Class<I> classId = information != null ? (Class<I>) information.getIdType() : null;
		
		return new MultitenancyMongoEntityInformation<>((MongoPersistentEntity<T>) entity, classId, config);
	}
	

	private static class MongoQueryLookupStrategy implements QueryLookupStrategy {

		private final MongoOperations operations;
		private final EvaluationContextProvider evaluationContextProvider;
		MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext;

		public MongoQueryLookupStrategy(MongoOperations operations, EvaluationContextProvider evaluationContextProvider,
				MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext) {

			this.operations = operations;
			this.evaluationContextProvider = evaluationContextProvider;
			this.mappingContext = mappingContext;
		}
	
		@Override
		public RepositoryQuery resolveQuery(Method method, RepositoryMetadata metadata, ProjectionFactory factory,
				NamedQueries namedQueries) {

			MongoQueryMethod queryMethod = new MongoQueryMethod(method, metadata, factory, mappingContext);
			String namedQueryName = queryMethod.getNamedQueryName();

			if (namedQueries.hasQuery(namedQueryName)) {
				String namedQuery = namedQueries.getQuery(namedQueryName);
				return new StringBasedMongoQuery(namedQuery, queryMethod, operations, EXPRESSION_PARSER,
						evaluationContextProvider);
			} else if (queryMethod.hasAnnotatedQuery()) {
				return new StringBasedMongoQuery(queryMethod, operations, EXPRESSION_PARSER, evaluationContextProvider);
			} else {
				return new PartTreeMongoQuery(queryMethod, operations);
			}
		}
	}
}
