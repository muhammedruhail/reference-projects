package com.ruhail.inventoryservice.repository.search;

import com.ruhail.inventoryservice.domain.OrderLine;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OrderLine entity.
 */
public interface OrderLineSearchRepository extends ElasticsearchRepository<OrderLine, Long> {
}
