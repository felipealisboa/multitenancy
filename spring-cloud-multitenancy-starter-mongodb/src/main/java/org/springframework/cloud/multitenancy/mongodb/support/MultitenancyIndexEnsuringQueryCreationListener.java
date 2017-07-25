package org.springframework.cloud.multitenancy.mongodb.support;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.repository.query.MongoEntityMetadata;
import org.springframework.data.mongodb.repository.query.PartTreeMongoQuery;
import org.springframework.data.repository.core.support.QueryCreationListener;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.data.repository.query.parser.Part.Type;
import org.springframework.data.repository.query.parser.PartTree;
import org.springframework.util.Assert;

/**
 * 
 * @author WRP
 * */
public class MultitenancyIndexEnsuringQueryCreationListener implements QueryCreationListener<PartTreeMongoQuery> {
	
	private static final Set<Type> GEOSPATIAL_TYPES = new HashSet<Type>(Arrays.asList(Type.NEAR, Type.WITHIN));
	private static final Logger LOG = LoggerFactory.getLogger(MultitenancyIndexEnsuringQueryCreationListener.class);

	private final MongoOperations operations;

	public MultitenancyIndexEnsuringQueryCreationListener(MongoOperations operations) {

		Assert.notNull(operations);
		this.operations = operations;
	}

	public void onCreation(PartTreeMongoQuery query) {

		PartTree tree = query.getTree();
		Index index = new Index();
		index.named(query.getQueryMethod().getName());
		Sort sort = tree.getSort();

		for (Part part : tree.getParts()) {
			if (GEOSPATIAL_TYPES.contains(part.getType())) {
				return;
			}
			String property = part.getProperty().toDotPath();
			Direction order = toDirection(sort, property);
			index.on(property, order);
		}

		if (sort != null) {
			for (Sort.Order order : sort) {
				index.on(order.getProperty(), order.getDirection());
			}
		}

		MongoEntityMetadata<?> metadata = query.getQueryMethod().getEntityInformation();
		operations.indexOps(metadata.getCollectionName()).ensureIndex(index);
		LOG.debug(String.format("Created %s!", index));
	}

	private static Direction toDirection(Sort sort, String property) {

		if (sort == null) {
			return Direction.DESC;
		}

		org.springframework.data.domain.Sort.Order order = sort.getOrderFor(property);
		return order == null ? Direction.DESC : order.isAscending() ? Direction.ASC : Direction.DESC;
	}
}
