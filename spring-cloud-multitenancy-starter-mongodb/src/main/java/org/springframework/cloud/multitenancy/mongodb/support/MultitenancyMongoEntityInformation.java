package org.springframework.cloud.multitenancy.mongodb.support;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.cloud.multitenancy.core.exchange.MultitenancyCurrentInformation;
import org.springframework.cloud.multitenancy.core.properties.MultitenancyConfigLoader;
import org.springframework.cloud.multitenancy.core.properties.MultitenancyStrategyEnum;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.repository.core.support.PersistentEntityInformation;



/**
 * Class responsible for implementing multitenancy by colletion
 * 
 * @author WRP
 * */
public class MultitenancyMongoEntityInformation<T, I extends Serializable> extends PersistentEntityInformation<T, I> implements MongoEntityInformation<T, I> {
	
	private final MongoPersistentEntity<T> entityMetadata;
	private final Class<I> fallbackIdType;
	private final MultitenancyConfigLoader config;
	
	
	public MultitenancyMongoEntityInformation(MongoPersistentEntity<T> entity, MultitenancyConfigLoader config) {
		this(entity, null, config);
	}

	@SuppressWarnings("unchecked")
	public MultitenancyMongoEntityInformation(MongoPersistentEntity<T> entity, Class<I> idType, MultitenancyConfigLoader config) {
		super(entity);

		this.entityMetadata = entity;
		this.fallbackIdType = idType != null ? idType : (Class<I>) ObjectId.class;
		this.config = config;
	}

	@Override
	public String getCollectionName() {
		
		String tenant = MultitenancyCurrentInformation.getTenant();
		String collectionName = entityMetadata.getCollection();
		
		if(config.getMongodb().getStrategy().equals(MultitenancyStrategyEnum.COLLECTION)){
			collectionName = tenant.concat(collectionName);
		}
		
		
		if(MultitenancyCurrentInformation.getPartition() != null){
			collectionName = collectionName.concat(MultitenancyCurrentInformation.getPartition());
		}
			
		return collectionName;
	}

	@Override
	public String getIdAttribute() {
		return entityMetadata.getIdProperty().getName();
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public Class<I> getIdType() {

		if (this.entityMetadata.hasIdProperty()) {
			return super.getIdType();
		}

		return fallbackIdType != null ? fallbackIdType : (Class<I>) ObjectId.class;
	}
}
